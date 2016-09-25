package myStops.repo;

import myStops.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LocationRepo extends JpaRepository<Location, Long> {
}
