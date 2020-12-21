package be.vdab.allesvoordekeuken.repositories;

import be.vdab.allesvoordekeuken.domain.Artikel;

import java.util.Optional;

public interface ArtikelRepository {
    Optional<Artikel> findById(long id);
}
