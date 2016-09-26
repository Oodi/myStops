package myStops.service;

import myStops.domain.Location;
import myStops.domain.Person;
import myStops.repo.LocationRepo;
import myStops.repo.PersonRepo;
import myStops.repo.StopRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MyStopService {
    @Autowired
    private PersonRepo personRepo;

    @Autowired
    private LocationRepo locationRepo;

    @Autowired
    private StopRepo stopRepo;

    @Transactional
    public String addNewLocation(String username, String locationName) {
        Person p = personRepo.findByUsername(username);
        if (p == null) {
            return "Kayttaja virhe";
        }
        Location l = new Location();
        l.setOwner(p);
        l.setName(locationName);

        p.getLocations().add(l);


        return "";
    }
}
