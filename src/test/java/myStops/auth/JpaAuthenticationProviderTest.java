package myStops.auth;

import myStops.Application;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import myStops.repo.PersonRepo;
import myStops.util.MockAuthentication;

import javax.transaction.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
@ActiveProfiles("development")
public class JpaAuthenticationProviderTest {


    @Autowired
    private PersonRepo personRepo;

    @Autowired
    JpaAuthenticationProvider jpaAuthProv;


    @Test(expected = AuthenticationException.class)
    public void testi2() {
        Authentication mockAuthentication = new MockAuthentication("user", "0001");
        jpaAuthProv.authenticate(mockAuthentication);
    }

    @Test(expected = AuthenticationException.class)
    public void testi3() {
        Authentication mockAuthentication = new MockAuthentication("joku", "0000");
        jpaAuthProv.authenticate(mockAuthentication);
    }

    @Test
    public void testi4() {
        Authentication mockAuthentication = new MockAuthentication("user", "salasana");
        Assert.assertNotNull(jpaAuthProv.authenticate(mockAuthentication));
    }

}
