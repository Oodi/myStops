package myStops.repo;

import myStops.domain.Location;
import myStops.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface LocationRepo extends JpaRepository<Location, Long> {
    List<Location> findByOwner(String owner);

    Location findByOwnerAndName(Person owner, String name);
}
