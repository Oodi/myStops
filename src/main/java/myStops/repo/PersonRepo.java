package myStops.repo;

import myStops.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PersonRepo extends JpaRepository<Person, Long> {
    Person findByUsername(String username);
}
