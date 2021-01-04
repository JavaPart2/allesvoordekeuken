package be.vdab.allesvoordekeuken.repositories;

import be.vdab.allesvoordekeuken.domain.ArtikelGroep;
import be.vdab.allesvoordekeuken.domain.FoodArtikel;
import be.vdab.allesvoordekeuken.domain.Korting;
import be.vdab.allesvoordekeuken.domain.NonFoodArtikel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaArtikelRepository.class)
@Sql({"/insertArtikelGroep.sql", "/insertArtikel.sql"})
public class JpaArtikelRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private final JpaArtikelRepository repository;
//    private Artikel artikel;
    private final EntityManager manager;

    public JpaArtikelRepositoryTest(JpaArtikelRepository repository, EntityManager manager) {
        this.repository = repository;
        this.manager = manager;
    }

    private long idVanTestFoodArtikel() {
        return super.jdbcTemplate.queryForObject(
                "select id from artikels where naam='testfood'", Long.class);
    }

    private long idVanTestNonFoodArtikel() {
        return super.jdbcTemplate.queryForObject(
                "select id from artikels where naam='testnonfood'", Long.class);
    }

    private long idVanTestArtikel(){
        return super.jdbcTemplate.queryForObject(
                "select id from artikels where naam = 'test'", long.class
        );
    }

/*
    @BeforeEach
    void beforeEach(){
        artikel = new FoodArtikel("test2", BigDecimal.ONE, BigDecimal.TEN, 7);
    }
*/

    @Test
    void findFById(){
        var artikel = repository.findById(idVanTestFoodArtikel()).get();
        assertThat(artikel).isInstanceOf(FoodArtikel.class);
        assertThat(artikel.getNaam()).isEqualTo("testfood");
    }

    @Test
    void findNFById(){
        var artikel = repository.findById(idVanTestNonFoodArtikel()).get();
        assertThat(artikel).isInstanceOf(NonFoodArtikel.class);
        assertThat(artikel.getNaam()).isEqualTo("testnonfood");
    }

    @Test
    void findByOnbestaandeId(){
        assertThat(repository.findById(-1)).isNotPresent();
    }

    @Test
    void createFood(){
        var artikelGroep = new ArtikelGroep("test");
        manager.persist(artikelGroep);
        var artikel = new FoodArtikel("testfood2", BigDecimal.ONE, BigDecimal.TEN, 7, artikelGroep);
        repository.create(artikel);
        assertThat(artikel.getId()).isPositive();
        assertThat(super.countRowsInTableWhere("artikels", "id = " + artikel.getId())).isOne();
    }

    @Test
    void createNonFood(){
        var artikelGroep = new ArtikelGroep("test");
        manager.persist(artikelGroep);
        var artikel = new NonFoodArtikel("testfood2", BigDecimal.ONE, BigDecimal.TEN, 30, artikelGroep);
        repository.create(artikel);
        assertThat(artikel.getId()).isPositive();
        assertThat(super.countRowsInTableWhere("artikels", "id = " + artikel.getId())).isOne();
    }

    @Test
    void findByNaamContains(){
        assertThat(repository.findByNaamContains("es"))
                .hasSize(super.jdbcTemplate.queryForObject(
                        "select count(*) from artikels where naam like '%es%'", Integer.class
                ))
                .extracting(artikel -> artikel.getNaam().toLowerCase())
                .allSatisfy(naam -> assertThat(naam).contains("es"))
                .isSorted();
    }

    @Test
    void verhoogAlleVPrijzen(){
        assertThat(repository.verhoogAlleVPrijzen(BigDecimal.TEN))
                .isEqualTo(super.countRowsInTable("artikels"));
        assertThat(super.jdbcTemplate.queryForObject(
                "select verkoopprijs from artikels where id = ?", BigDecimal.class, idVanTestFoodArtikel()))
                .isEqualByComparingTo("132");
    }

    @Test
    void kortingenLezen(){
        assertThat(repository.findById(idVanTestFoodArtikel()).get().getKortingen())
                .containsOnly(new Korting(1, BigDecimal.TEN));
    }

    @Test
    void artikelGroepLazyLoaded(){
        assertThat(repository.findById(idVanTestFoodArtikel()).get().getNaam()).isEqualTo("testfood");
    }
}
