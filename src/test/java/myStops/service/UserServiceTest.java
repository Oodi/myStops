package myStops.service;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import myStops.Application;
import myStops.domain.Person;
import myStops.repo.PersonRepo;
import org.springframework.util.Base64Utils;

import javax.transaction.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
@Transactional
@ActiveProfiles("development")
public class UserServiceTest {

    Person person;

    private Gson gson;
    private String encodedPerson;
    private String jsonPerson;

    @Autowired
    PersonRepo personRepo;

    @Autowired
    UserService userService;

    @Before
    public void setUp() {
        person = new Person();
        person.setUsername("uusi");
        person.hashPasswordWithSalt("salasana");
        gson = new Gson();
        jsonPerson = gson.toJson(person);
        encodedPerson = Base64Utils.encodeToString(jsonPerson.getBytes());
    }

    @Test
    @Transactional
    public void personIsAddedToRepo() throws Exception {
        Assert.assertEquals(1, personRepo.count());
        userService.addUser(encodedPerson);
        Assert.assertEquals(2, personRepo.count());
        person.setUsername("");
        jsonPerson = gson.toJson(person);
        encodedPerson = Base64Utils.encodeToString(jsonPerson.getBytes());
        Assert.assertEquals(2, personRepo.count());

    }

    @Test
    @Transactional
    public void personIsDeletedFromRepo() throws Exception {
        Assert.assertEquals(1, personRepo.count());
        userService.addUser(encodedPerson);
        Assert.assertEquals(2, personRepo.count());
        userService.deluser(this.person.getUsername());
        Assert.assertEquals(1, personRepo.count());
    }

    @Test
    @Transactional
    public void personsPasswordIsChanged() throws Exception {
        userService.addUser(encodedPerson);
        Assert.assertTrue(BCrypt.checkpw("salasana", person.getHashOfPasswordAndSalt()));
        userService.changePassword(this.person.getUsername(), Base64Utils.encodeToString("111111".getBytes()));
        Assert.assertTrue(BCrypt.checkpw("111111", personRepo.findByUsername(this.person.getUsername()).getHashOfPasswordAndSalt()));
        userService.changePassword(this.person.getUsername(), Base64Utils.encodeToString("11".getBytes()));
        Assert.assertFalse(BCrypt.checkpw("11", personRepo.findByUsername(this.person.getUsername()).getHashOfPasswordAndSalt()));
    }

    @Test
    @Transactional
    public void isUsernameTaken() throws Exception {
        String s = userService.isUsernameTaken("jokuvaan");
        Assert.assertEquals("", s);
        userService.addUser(encodedPerson);
        s = userService.isUsernameTaken("uusi");
        Assert.assertEquals("NIMIMERKKI VARATTU", s);
    }

}