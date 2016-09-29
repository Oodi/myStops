package myStops.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import myStops.service.MyStopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 *  Controller luokka, vastaa sijaintien ja pysakkien
 *  lisäys ja muokkaus pyyntoihin ja valittaa ne palvelulle
 */
@RestController
public class MyStopController {

    /**
     * Palvelu, jolle tiedot välitetään
     */
    @Autowired
    private MyStopService myStopService;

    /**
     *  Palauttaa kirjautuneen käytäjän sijainnit
     *  Vastaa tyhjällä viestillä, jos ei kirjautunut kysyy
     * @param user Kayttajan principal
     * @return Sijainnit, jos kirjautunut
     */
    @RequestMapping(value = "/mystop/location", method = RequestMethod.GET)
    @ResponseBody
    public String getLocations(final Principal user) {
        if (user == null) {
            return "";
        }
        return myStopService.getLocations(user.getName());
    }

    /**
     * Lisää käyttäjälle uuden sijainnin annettujen tietojen perusteella
     * @param location sijainnin nimi
     * @param user Principal
     * @return onnistumisilmoitus tai virheilmoitus
     */
    @RequestMapping(value = "/mystop/location", method = RequestMethod.POST)
    @ResponseBody
    public String addNewLocation(@RequestBody final String location,
                             final Principal user) {
        return jsonResponse(myStopService.addNewLocation(user.getName(), location));
    }

    /**
     * Muuttaa sijainnin nimen halutuksi
     * @param names Json, joka sisältää vanhan ja uuden nimen
     * @param user Principal
     * @return onnistumisilmoitus tai virheilmoitus
     */
    @RequestMapping(value = "/mystop/locationName", method = RequestMethod.POST, consumes = {"application/json"})
    @ResponseBody
    public String changeLocationName(@RequestBody final ChangeLocation names,
                                 final Principal user) {
        return jsonResponse(myStopService.changeLocationName(user.getName(), names.getOldName(), names.getNewName()));
    }

    /**
     *  Poistaa halutun sijainnin käyttäjältä
     * @param location sijainti joka poistetaan
     * @param user Principal
     * @return onnistumisilmoitus tai virheilmoitus
     */
    @RequestMapping(value = "/mystop/location", method = RequestMethod.DELETE)
    @ResponseBody
    public String removeLocation(@RequestBody final String location,
                                 final Principal user) {
        return jsonResponse(myStopService.deleteLocation(user.getName(), location));
    }

    /**
     * Palauttaa sijaintiin liittyvat pysakit
     * @param location sijainti, jonka pysakit halutaan
     * @param user Principal
     * @return onnistumisilmoitus tai virheilmoitus
     */
    @RequestMapping(value = "/mystop/stopOfLocation", method = RequestMethod.POST)
    @ResponseBody
    public String getStopsOfLocation(@RequestBody final String location,
                                    final Principal user) {
        if (user == null) {
            return "";
        }
        return myStopService.getStops(user.getName(), location);
    }

    /**
     * Lisaa pyaakin haluttuun sijaintiin
     * @param stopLocation Json olio, joka sisältää sijainninnimen ja pysakin id:n
     * @param user Principal
     * @return onnistumisilmoitus tai virheilmoitus
     */
    @RequestMapping(value = "/mystop/stop", method = RequestMethod.POST, consumes = {"application/json"})
    @ResponseBody
    public String addStopToLocation(@RequestBody final StopLocation stopLocation,
                             final Principal user) {
        return jsonResponse(myStopService.addStopToLocation(user.getName(), stopLocation.getLocation(), stopLocation.getStopID()));
    }

    /**
     * Poistaa pysakin sijainnista
     * @param stopLocation JSSON joka sisältää sijainnin  nimen ja pysakin id:n
     * @param user Principal
     * @return onnistumisilmoitus tai virheilmoitus
     */
    @RequestMapping(value = "/mystop/stop", method = RequestMethod.DELETE, consumes = {"application/json"})
    @ResponseBody
    public String removeStopFromLocation(@RequestBody final StopLocation stopLocation,
                                    final Principal user) {
        return jsonResponse(myStopService.removeStopFromLocation(user.getName(), stopLocation.getLocation(), stopLocation.getStopID()));
    }


    /**
     * Tekee JSON vastauksen pyyntoihin, joilla tehdaan muutoksia
     * @param error Virhe viesti joka laheteteaan kayttajalle
     * @return Status ok, jos toiminto onnistuu, virheviesti muuten
     */
    private String jsonResponse(final String error) {
        if (error.isEmpty()) {
            return "{\"status\":\"OK\"}";
        }
        return "{\"error\": \"" + error + "\"}";
    }


}

/**
 * JSON olion vastaanottamista varten sopivilla muuttujilla
 */
 class StopLocation {

    /**
     * Sijainnin nimi
     */
    @JsonProperty("location")
    private String location;

    /**
     * PysakinID
     */
    @JsonProperty("stopID")
    private String stopID;

    /**
     * Konstruktori sijaintipysakki oliolle
     * @param location sijainninnimi json oliosta
     * @param stopID pysakinid json oliosta
     */
     @JsonCreator
    public StopLocation(@JsonProperty("location") String location, @JsonProperty("stopID") String stopID) {
        this.location = location;
        this.stopID = stopID;
    }

    /**
     * Palauttaa pysakinid:n
     * @return pysakin id
     */
    public String getStopID() {
        return stopID;
    }

    /**
     * Sijainnin nimi
     * @return palauttaa sijainnin nimen
     */
    public String getLocation() {
        return location;
    }

    /**
     * asettaa olion sijainninnimeksi
     * @param location nimi joka asetetaan
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Asettaa pysakin idn annetun paremetrin mukaan
     * @param stopID Asettaa pysakin id:n
     */
    public void setStopID(String stopID) {
        this.stopID = stopID;
    }
}

/**
 * Json olio siejainnin nimen vaihtamiseen
 */
class ChangeLocation {
    /**
     * Vanhanimi json oliosta
     */
    @JsonProperty("oldName")
    private String oldName;

    /**
     *  Uuusi nimi json oliosta
     */
    @JsonProperty("newName")
    private String newName;

    /**
     *  Konstruktori asettamaan jsonista taksi olioksi
     * @param oldName
     * @param newName
     */
    @JsonCreator
    public ChangeLocation(@JsonProperty("oldName") String oldName, @JsonProperty("newName") String newName) {
        this.oldName = oldName;
        this.newName = newName;
    }

    /**
     * Palauttaa uuden nimen
     * @return uusinimi
     */
    public String getNewName() {
        return newName;
    }

    /**
     * Palauttaa vanhan nimi.
     * @return vanhanimi
     */
    public String getOldName() {
        return oldName;
    }
}
