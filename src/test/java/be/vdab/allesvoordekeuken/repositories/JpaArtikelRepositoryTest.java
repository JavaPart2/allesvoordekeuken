package be.vdab.allesvoordekeuken.repositories;

import be.vdab.allesvoordekeuken.domain.Artikel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaArtikelRepository.class)
@Sql("/insertArtikel.sql")
public class JpaArtikelRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private final JpaArtikelRepository repository;
    private Artikel artikel;


    public JpaArtikelRepositoryTest(JpaArtikelRepository repository) {
        this.repository = repository;
    }

    private long idVanTestArtikel(){
        return super.jdbcTemplate.queryForObject(
                "select id from artikels where naam = 'test'", long.class
        );
    }

    @BeforeEach
    void beforeEach(){
        artikel = new Artikel("test2", BigDecimal.ONE, BigDecimal.TEN);
    }

    @Test
    void findById(){
        assertThat(repository.findById(idVanTestArtikel()).get().getNaam())
        .isEqualTo("test");
    }

    @Test
    void findByOnbestaandeId(){
        assertThat(repository.findById(-1)).isNotPresent();
    }

    @Test
    void create(){
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
}
