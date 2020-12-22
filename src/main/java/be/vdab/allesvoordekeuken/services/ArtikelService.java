package be.vdab.allesvoordekeuken.services;

import java.math.BigDecimal;

public interface ArtikelService {
    void verhoogVerkoopPrijs(long id, BigDecimal bedrag);
}
