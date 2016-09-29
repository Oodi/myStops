package myStops.repo;

import myStops.domain.Stop;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Sailio pysakkien tallentamista varten
 */
public interface StopRepo extends JpaRepository<Stop, Long> {
    /**
     * Etsii pyakin id:n perusteella
     * @param gtfsId pysakin HSL id
     * @return palauttaa pysakin jos loytyy
     */
    Stop findByGtfsId(String gtfsId);
}
