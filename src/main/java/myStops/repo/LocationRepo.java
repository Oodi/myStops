package myStops.repo;

import myStops.domain.Location;
import myStops.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Sailio sijaintien tallentamista varten
 */
public interface LocationRepo extends JpaRepository<Location, Long> {

    /**
     * Palauttaa sijainnin kayttajan ja sijainninnimen perusteella
     * @param owner kayttaja-olio johon liittyvaa sijaintia etsitaan
     * @param name sijainnin nimi
     * @return palauttaa sijainnin
     */
    Location findByOwnerAndName(Person owner, String name);
}
