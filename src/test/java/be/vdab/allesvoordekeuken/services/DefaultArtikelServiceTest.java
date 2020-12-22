package be.vdab.allesvoordekeuken.services;

import be.vdab.allesvoordekeuken.domain.Artikel;
import be.vdab.allesvoordekeuken.exceptions.ArtikelNietGevondenException;
import be.vdab.allesvoordekeuken.repositories.ArtikelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DefaultArtikelServiceTest {
    private DefaultArtikelService service;

    @Mock
    private ArtikelRepository repository;
    private Artikel artikel;

    @BeforeEach
    void beforeEach(){
        artikel = new Artikel("test", BigDecimal.ONE, BigDecimal.TEN);
        service = new DefaultArtikelService(repository);
    }

    @Test
    void verhoogVerkoopPrijs(){
        when(repository.findById(1)).thenReturn(Optional.of(artikel));
        service.verhoogVerkoopPrijs(1, BigDecimal.ONE);
        assertThat(artikel.getVerkoopprijs()).isEqualByComparingTo("11");
        verify(repository).findById(1);
    }

    void verhoogVerkoopPrijsVoorOnbestaand(){
        assertThatExceptionOfType(ArtikelNietGevondenException.class)
                .isThrownBy(() -> service.verhoogVerkoopPrijs(-1, BigDecimal.ONE));
        verify(repository).findById(-1);
    }
}
