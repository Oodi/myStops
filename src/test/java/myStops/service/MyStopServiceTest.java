package myStops.service;

import com.google.gson.Gson;
import myStops.domain.Location;
import myStops.repo.LocationRepo;
import myStops.repo.StopRepo;
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
public class MyStopServiceTest {

    String person;


    @Autowired
    LocationRepo locationRepo;

    @Autowired
    PersonRepo personRepo;

    @Autowired
    StopRepo stopRepo;

    @Autowired
    MyStopService myStopService;

    @Before
    public void setUp() {
        person = "user";
    }

    @Test
    @Transactional
    public void locationIsAdded() throws Exception {
        myStopService.addNewLocation(person, "lista");
        Assert.assertEquals(1, locationRepo.count());
        Assert.assertEquals(1, personRepo.findByUsername(person).getLocations().size());
        myStopService.addNewLocation(person, "lista");
        Assert.assertEquals(1, locationRepo.count());
        myStopService.addNewLocation(person, "");
        Assert.assertEquals(1, locationRepo.count());
    }

    @Test
    @Transactional
    public void changeLocationNameTest() throws Exception {
        myStopService.addNewLocation(person, "lista");
        Assert.assertEquals("lista", personRepo.findByUsername(person).getLocations().get(0).getName());
        myStopService.changeLocationName(person, "lista", "toinen");
        Assert.assertEquals("toinen", personRepo.findByUsername(person).getLocations().get(0).getName());
        Assert.assertEquals("Listaa ei löydy tai nimi varattu", myStopService.changeLocationName(person, "toinen", "toinen"));
    }

    @Test
    @Transactional
    public void deleteLocationTest() throws Exception {
        myStopService.addNewLocation(person, "lista");
        Assert.assertEquals(1, locationRepo.count());
        myStopService.deleteLocation(person, "lista");
        Assert.assertEquals(0, locationRepo.count());
    }

    @Test
    @Transactional
    public void addStopToLocatonTest() throws Exception {
        myStopService.addNewLocation(person, "lista");
        myStopService.addStopToLocation(person, "lista", "1");
        Assert.assertEquals(1, stopRepo.count());

    }

    @Test
    @Transactional
    public void getLocatonsTest() throws Exception {
        myStopService.addNewLocation(person, "lista");
        Assert.assertEquals("[{\"name\":\"lista\",\"stops\":[]}]", myStopService.getLocations(person));
    }
    @Test
    @Transactional
    public void getLocationStopsTest() throws Exception {
        myStopService.addNewLocation(person, "lista");
        myStopService.addStopToLocation(person, "lista", "1");
        Assert.assertTrue(myStopService.getStops(person, "lista").contains("[{\"gtfsId\":\"1\","));
    }

    @Test
    @Transactional
    public void removeStopTest() throws Exception {
        myStopService.addNewLocation(person, "lista");
        myStopService.addStopToLocation(person, "lista", "1");
        Assert.assertTrue(myStopService.getStops(person, "lista").contains("[{\"gtfsId\":\"1\","));
        myStopService.removeStopFromLocation(person, "lista", "1");
        Assert.assertEquals(0, locationRepo.findByOwnerAndName(personRepo.findByUsername(person), "lista").getStops().size());
    }

}