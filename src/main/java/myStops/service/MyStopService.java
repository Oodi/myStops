package myStops.service;

import com.google.gson.Gson;
import myStops.domain.Location;
import myStops.domain.Person;
import myStops.domain.Stop;
import myStops.repo.LocationRepo;
import myStops.repo.PersonRepo;
import myStops.repo.StopRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Palvelu Sijainteihin ja pysakkeihin liittyvia toiminnallisuuksia varten
 */
@Service
public class MyStopService {

    /**
     * Kayttaja sailio
     */
    @Autowired
    private PersonRepo personRepo;

    /**
     * Sijainti sailio
     */
    @Autowired
    private LocationRepo locationRepo;

    /**
     * Pysakki salilio
     */
    @Autowired
    private StopRepo stopRepo;

    /**
     * Lisaa kayttajalle uuden sijainnin
     * @param username nimimerkki, jolle sijainti lisätään
     * @param locationName sijainnille annettava nimi
     * @return virheviesti jos ei onnistu
     */
    @Transactional
    public String addNewLocation(String username, String locationName) {
        Person p = personRepo.findByUsername(username);
        Location l1 = locationRepo.findByOwnerAndName(p, locationName);
        if (locationName == null || locationName.isEmpty() || l1 != null) {
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

    /**
     * Vaihtaa sijainnin nimen toiseen
     * @param username Sijainnin omistaja
     * @param locationName vanha sijainnin nimi
     * @param newLocationName uusi sijainnin nimi
     * @return virheviesti jos ei onnistu
     */
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

    /**
     * Poistaa sijainnin kayttajalta
     * @param username kayttajanimi jonka sijaintia kasitellaan
     * @param locationName sijainninnimi joka poistetaan
     * @return virheviesti, jos ei onnistu
     */
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

    /**
     * Lisa apysakin sijaintiin
     * @param username sijainnin omistavam kayttajan nmimerkki
     * @param locationName sijainninnimi, johon lisataan
     * @param stopID pysakinid, joka lisataan
     * @return virheviesti jos ei onnistu
     */
    @Transactional
    public String addStopToLocation(String username, String locationName, String stopID) {
        Person p = personRepo.findByUsername(username);
        Location l = locationRepo.findByOwnerAndName(p,locationName);
        Stop s = stopRepo.findByGtfsId(stopID);
        if (l == null ) {
            return "Listaa ei löydy";
        }
        try {
            if (s == null) {
                s = new Stop();
                s.setGtfsId(stopID);
                stopRepo.save(s);
            }

            l.getStops().add(s);
        } catch (Exception exception) {
            return "VIRHE TIETOKANNASSA";
        }
        return "";
    }

    /**
     *  Poistaa pysakin sijainnista. Itse sijaintia ei poisteta.
     * @param username nimimerkki, jonka sijaintia kasitellaan
     * @param locationName sijainnin nimi, jossa pysakki on
     * @param stopID pysakin nimi, joka poistetaan
     * @return virheviesti jos ei onnistu
     */
    @Transactional
    public String removeStopFromLocation(String username, String locationName, String stopID) {
        Person p = personRepo.findByUsername(username);
        Location l = locationRepo.findByOwnerAndName(p,locationName);
        Stop s = stopRepo.findByGtfsId(stopID);
        if (l == null ) {
            return "Listaa ei löydy";
        }
        try {
            l.getStops().remove(s);
        } catch (Exception exception) {
            return "VIRHE TIETOKANNASSA";
        }
        return "";
    }

    /**
     * Palauttaa sijaintiin liittety pysakit
     * @param username Kayttaja jonka sijaintia kasitellaan
     * @param location sijainninnimet, jonka pysakit haetaan
     * @return Pysakit, jotka liittyy sijaintiin
     */
    @Transactional
    public String getStops(String username, String location) {
        Person p = personRepo.findByUsername(username);
        Location l = locationRepo.findByOwnerAndName(p,location);
        if (l == null) {
            return "ERROR LOCATION";
        }
        List<Stop> stops = l.getStops();
        Gson gson = new Gson();
        return gson.toJson(stops);
    }

    /**
     * Hakee ja palauttaa kayttajan sijainnit
     * @param username kayttaja jonka sijainnit haetaan
     * @return Sijainnit
     */
    @Transactional
    public String getLocations(String username) {
        List<Location> l = personRepo.findByUsername(username).getLocations();
        List<Location> deprecatedLocationList = new ArrayList<>();
        l.forEach(p->deprecatedLocationList.add(extractInfo(p)));

        Gson gson = new Gson();
        return gson.toJson(deprecatedLocationList);
    }

    /**
     * Luo kopion sijainnista ilman pysakkitietoja
     * @param location sijainti joka kopioidaan
     * @return kopioitu sijainti
     */
    private Location extractInfo(final Location l) {
        Location l1 = new Location();
        l1.setName(l.getName());
        return l1;
    }
}
