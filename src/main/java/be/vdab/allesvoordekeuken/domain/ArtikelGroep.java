package be.vdab.allesvoordekeuken.domain;

import javax.persistence.*;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "artikelgroepen")
public class ArtikelGroep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String naam;
    @OneToMany(mappedBy = "artikelgroep")
    @OrderBy("naam")
    private Set<Artikel> artikels;

    public ArtikelGroep(String naam) {
        this.naam = naam;
        this.artikels = new LinkedHashSet<>();
    }

    protected ArtikelGroep() {
    }

    public boolean addArtikel(Artikel artikel){
        var toegevoegd = artikels.add(artikel);

        if (artikel.getArtikelgroep() != this){
            artikel.setArtikelgroep(this);
        }
        return toegevoegd;
    }

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public Set<Artikel> getArtikels() {
        return Collections.unmodifiableSet(artikels);
    }
}
