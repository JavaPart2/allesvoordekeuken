package be.vdab.allesvoordekeuken.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

public class ArtikelTest {
    private final static BigDecimal WEDDE = BigDecimal.valueOf(200);
    private Artikel artikel;
    private ArtikelGroep artikelGroep1;
    private ArtikelGroep artikelGroep2;

    @BeforeEach
    void beforeEach(){
        artikelGroep1 = new ArtikelGroep("test");
        artikelGroep2 = new ArtikelGroep("groep2");
        artikel = new FoodArtikel("test", BigDecimal.ONE, BigDecimal.TEN, 9, artikelGroep1);
    }

    @Test
    void groep1EnArtikelZijnGekoppeld(){
        assertThat(artikelGroep1.getArtikels()).contains(artikel);
        assertThat(artikel.getArtikelgroep()).isEqualTo(artikelGroep1);
        assertThat(artikelGroep2.getArtikels()).doesNotContain(artikel);
    }

    @Test
    void nullAlsArtikelGroepMislukt(){
        assertThatNullPointerException().isThrownBy(() -> artikel.setArtikelgroep(null));
    }
}
