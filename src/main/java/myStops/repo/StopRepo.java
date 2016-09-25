package myStops.repo;

import myStops.domain.Stop;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StopRepo extends JpaRepository<Stop, Long> {
}
