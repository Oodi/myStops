package myStops.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Location extends AbstractPersistable<Long> {

    private String name;

    @ManyToOne
    private Person owner;

    @ManyToMany
    private List<Stop> stops;

    public Location() {
        stops = new ArrayList<>();
    }

    public int stopCount() {
        return stops.size();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public List<Stop> getStops() {
        return stops;
    }

    public String getName() {
        return name;
    }
}
