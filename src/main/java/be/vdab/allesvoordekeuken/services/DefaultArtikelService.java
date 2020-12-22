package be.vdab.allesvoordekeuken.services;

import be.vdab.allesvoordekeuken.exceptions.ArtikelNietGevondenException;
import be.vdab.allesvoordekeuken.repositories.ArtikelRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DefaultArtikelService implements ArtikelService{
    private final ArtikelRepository repository;

    public DefaultArtikelService(ArtikelRepository repository) {
        this.repository = repository;
    }

    @Override
    public void verhoogVerkoopPrijs(long id, BigDecimal bedrag) {
        repository.findById(id).orElseThrow(() -> new ArtikelNietGevondenException())
                .verhoogVerkoopPrijs(bedrag);
    }
}
