package be.vdab.allesvoordekeuken.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

public class ArtikelGroepTest {
    private Artikel artikel1;
    private ArtikelGroep artikelGroep1;

    @BeforeEach
    void beforeEach(){
        artikelGroep1 = new ArtikelGroep("test");
        artikel1 = new FoodArtikel("test", BigDecimal.ONE, BigDecimal.TEN, 9 ,artikelGroep1);
    }

    @Test
    void groep1IsArtikelGroepVanArtikel1(){
        assertThat(artikel1.getArtikelgroep()).isEqualTo(artikelGroep1);
        assertThat(artikelGroep1.getArtikels()).contains(artikel1);
    }

    @Test
    void nullAlsArtikelToevoegenMislukt(){
        assertThatNullPointerException().isThrownBy(() -> artikelGroep1.addArtikel(null));
    }
}
