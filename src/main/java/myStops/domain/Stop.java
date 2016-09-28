package myStops.domain;


import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Stop extends AbstractPersistable<Long> {

    @Column(unique = true)
    private String gtfsId;

    public Stop() {

    }

    public String getGtfsId() {
        return gtfsId;
    }

    public void setGtfsId(String gtfsId) {
        this.gtfsId = gtfsId;
    }
}
