package myStops.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Luokka kayttajien tallentamista varten
 */
@Entity
public class Person extends AbstractPersistable<Long> {

    /**
     * Kayttajanimi
     */
    @Column(unique = true)
    private String username;

    /**
     * suolattu salasana
     */
    private String authenticationHash;

    /**
     * Suola
     */
    private String salt;

    /**
     * Kayttajan sijainnit
     */
    @OneToMany
    private List<Location> locations;

    /**
     * Konstruktori luo listan sijainteja varten
     */
    public Person() {
        this.locations = new ArrayList<>();
    }

    /**
     * Palauttaa kayttajan kayttajanimen
     * @return kayttajanimi
     */
    public final String getUsername() {
        return username;
    }

    /**
     * Asettaa kayttajan kayttajanimen
     * @param pName Nimi joka asetetetaan
     */
    public final void setUsername(final String pName) {
        this.username = pName;
    }

    /**
     * Palauttaa kayttajan sijaintilistat
     * @return lista sijainneista
     */
    public List<Location> getLocations() {
        return locations;
    }

    /**
     * Palauttaa suolatunsalasanan
     * @return suolattusalasana
     */
    public final String getHashOfPasswordAndSalt() {
        return authenticationHash;
    }

    /**
     * Suolaa annetun selvakielisen salasanan kayttajalle
     * @param plainTextPassword selvakielinen salasana
     */
    public final void hashPasswordWithSalt(final String plainTextPassword) {
        this.salt = BCrypt.gensalt();
        this.authenticationHash = BCrypt.hashpw(plainTextPassword, this.salt);
    }

    /**
     * Palauttaa kaytajan suolan
     * @return suola
     */
    public final String getSalt() {return salt;
    }
}
