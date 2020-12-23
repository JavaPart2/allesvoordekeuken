package be.vdab.allesvoordekeuken.repositories;

import be.vdab.allesvoordekeuken.domain.Artikel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ArtikelRepository {
    Optional<Artikel> findById(long id);
    void create(Artikel artikel);
    List<Artikel> findByNaamContains(String woord);
    int verhoogAlleVPrijzen(BigDecimal percentage);
}
