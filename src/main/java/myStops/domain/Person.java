package myStops.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Person extends AbstractPersistable<Long> {

    @Column(unique = true)
    private String username;

    private String authenticationHash;

    private String salt;

    @OneToMany
    private List<Location> locations;

    public Person() {
        this.locations = new ArrayList<>();
    }

    public final String getUsername() {
        return username;
    }

    public final void setUsername(final String pName) {
        this.username = pName;
    }

    public final int locationCount() {
        return this.locations.size();
    }

    public List<Location> getLocations() {
        return locations;
    }

    public final String getHashOfPasswordAndSalt() {
        return authenticationHash;
    }

    public final void hashPasswordWithSalt(final String plainTextPassword) {
        this.salt = BCrypt.gensalt();
        this.authenticationHash = BCrypt.hashpw(plainTextPassword, this.salt);
    }

    public final String getSalt() {
        return salt;
    }
}
