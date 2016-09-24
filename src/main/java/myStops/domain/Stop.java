package myStops.domain;


import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Stop {

    @Id
    private long stopID;

    public Stop() {

    }
}
