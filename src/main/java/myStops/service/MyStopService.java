package myStops.service;

import com.google.gson.Gson;
import myStops.domain.Location;
import myStops.domain.Person;
import myStops.repo.LocationRepo;
import myStops.repo.PersonRepo;
import myStops.repo.StopRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
        Location l1 = locationRepo.findByOwnerAndName(p, locationName);
        if (l1 != null) {
            return "Lista on jo olemassa";
        }
        Location l = new Location();
        l.setOwner(p);
        l.setName(locationName);
        p.getLocations().add(l);
        try {
            locationRepo.save(l);
        } catch (Exception exception) {
            return "VIRHE TIETOKANNASSA";
        }
        return "";
    }

    @Transactional
    public String changeLocationName(String username, String locationName, String newLocationName) {
        Person p = personRepo.findByUsername(username);
        Location l = locationRepo.findByOwnerAndName(p, locationName);
        Location l1 = locationRepo.findByOwnerAndName(p, newLocationName);
        if (l == null || l1 != null) {
            return "Listaa ei löydy tai nimi varattu";
        }

        l.setName(newLocationName);

        try {
            locationRepo.save(l);
        } catch (Exception exception) {
            return "VIRHE TIETOKANNASSA";
        }
        return "";
    }

    @Transactional
    public String deleteLocation(String username, String locationName) {
        Person p = personRepo.findByUsername(username);
        Location l = locationRepo.findByOwnerAndName(p,locationName);
        if (l == null ) {
            return "Listaa ei löydy";
        }
        try {
            p.getLocations().remove(l);
            locationRepo.delete(l);
        } catch (Exception exception) {
            return "VIRHE TIETOKANNASSA";
        }
        return "";
    }



    @Transactional
    public String getLocations(String username) {
        List<Location> l = personRepo.findByUsername(username).getLocations();
        List<Location> deprecatedLocationList = new ArrayList<>();
        l.forEach(p->deprecatedLocationList.add(extractInfo(p)));

        Gson gson = new Gson();
        return gson.toJson(deprecatedLocationList);
    }
    private Location extractInfo(final Location l) {
        Location l1 = new Location();
        l1.setName(l.getName());
        return l1;
    }
}
