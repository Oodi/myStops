package myStops.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Luokka sijaintien tallentamista varten
 */
@Entity
public class Location extends AbstractPersistable<Long> {

    /**
     * Sijainnin nimi
     */
    @NotNull
    private String name;

    /**
     * Kayttaja johon sijainti liittyy
     */
    @ManyToOne
    private Person owner;

    /**
     * Sijaintiin liittyvat pysakit
     */
    @ManyToMany
    private List<Stop> stops;

    /**
     * Konstruktori, jossa luodaan tyhjalista pysakkeja varten
     */
    public Location() {
        stops = new ArrayList<>();
    }

    /**
     * Asettaa sijainnille nimen
     * @param name nimi joka asetetaan
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Asettaa sijainnille omistaja kayttajan
     * @param owner
     */
    public void setOwner(Person owner) {
        this.owner = owner;
    }

    /**
     * Palauttaa sijaintiin liittyvat pysakit listana
     * @return lista pysakeista
     */
    public List<Stop> getStops() {
        return stops;
    }

    /**
     * Palauttaa sijainnin nimen
     * @return sijainnin nimi
     */
    public String getName() {
        return name;
    }
}
