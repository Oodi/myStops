package myStops.auth;


import myStops.domain.Person;
import myStops.repo.PersonRepo;
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

@Component
public class JpaAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private PersonRepo personRepo;

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
                authentication.getCredentials().toString(),  grantedAuths);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
