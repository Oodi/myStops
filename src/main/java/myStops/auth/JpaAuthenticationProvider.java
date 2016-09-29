package myStops.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import myStops.domain.Person;
import myStops.repo.PersonRepo;

/**
 * Luokka JPA-pohjaiseen kayttajan todentamiseen.
 * Toteuttaa rajapinnan <code>AuthenticationProvider</code>.
 */
@Component
public class JpaAuthenticationProvider implements AuthenticationProvider {

    /**
     * Säiliö person-olioille
     */
    @Autowired
    private PersonRepo personRepo;

    /**
     *  Todentaa kayttajan tarkistamalla, että annettu salasana vastaa
     *  PersonRepo:ssa olevaa salasanaa.
     *
     * @param authentication Authentication-olio
     * @return Valtuus, jossa mukana Person olio
     * @throws AuthenticationException Heitettävä poikkeus,
     *  jos kirjautuminen ei onnistu annetuilla tiedoilla
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        Person person = personRepo.findByUsername(authentication.getPrincipal().toString());

        if (person == null) {
            throw new AuthenticationException(
                    "Unable to authenticate user" ) {
            };
        }
        if (!BCrypt.hashpw(authentication.getCredentials().toString(), person.getSalt())
                .equals(person.getHashOfPasswordAndSalt())) {
            throw new AuthenticationException(
                    "Unable to authenticate user") {
            };
        }
        List<GrantedAuthority> grantedAuths = new ArrayList<>();
        grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new UsernamePasswordAuthenticationToken(person.getUsername(),
                authentication.getCredentials().toString(), grantedAuths);
    }

    /**
     * Palauttaa true, jos AuthenticationProvider
     * tukee viitattua Authentication-oliota
     * @param aClass tyyppi
     * @return ture, jos viitattu olio on tuettu.
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
