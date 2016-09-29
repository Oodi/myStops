package myStops.repo;

import myStops.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Sailio kayttajia varten
 */
public interface PersonRepo extends JpaRepository<Person, Long> {
    /**
     * Etsii nimimerkkia vaastaavan kayttajan
     * @param username nimimerkki
     * @return kayttaja jos löytyy
     */
    Person findByUsername(String username);
}
