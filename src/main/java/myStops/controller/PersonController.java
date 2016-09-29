package myStops.controller;

import myStops.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Kontrolleri vastaanottamaan rekisteröitymiseen liittyvät pyynnöt
 */
@RestController
public class PersonController {
    /**
     * Palvelu kayttajien kasittelyyn
     */
    @Autowired
    private UserService userService;

    /**
     * Aloittaa uuden kayttajan lisaamisen annetuilla tiedoilla
     * @param person Encodattu person olio
     * @return Vastaus onnistuiko toiminto
     */
    @RequestMapping(value = "/person/newuser", method = RequestMethod.POST)
    @ResponseBody
    public String addNewUser(@RequestBody final String person) {
        return jsonResponse(userService.addUser(person));
    }

    /**
     * Tarkistaa onko kayttajanimi varattu
     * @param username kayttajanimi jonka olemassaoloa testataan
     * @return Palauttaa json vastauksen siita onko varattu vai ei
     */
    @RequestMapping(value = "/person/isUsernameTaken", method = RequestMethod.POST)
    @ResponseBody
    public String isUsernameTaken(@RequestBody final String username) {
        return jsonResponse(userService.isUsernameTaken(username));
    }

    /**
     * Aloittaa kayttajan poiston
     * @param user Principal johon liittyva kayttaja poistetaan
     * @return palauttaa toiminnon onnistumisen statuksen
     */
    @RequestMapping(value = "/person/delete/", method = RequestMethod.DELETE)
    public String deleteUser(final Principal user) {
        if(user == null) {
            return jsonResponse("NOT LOGGED IN");
        }
        return jsonResponse(userService.deluser(user.getName()));
    }

    /**
     * Aloittaa kayttajan salasanan vaihtamisen
     * @param encodedPass encodattu uusi salasana
     * @param user principal johon liittyvan kayttajan salasana vaihdetaan
     * @return Palauttaa toiminnon onnistumis statuksen
     */
    @RequestMapping(value = "/person/resetpassword/", method = RequestMethod.POST)
    public String resetPassword(
            @RequestBody final String encodedPass,
            final Principal user
    ) {
        if(user == null) {
            return jsonResponse("NOT LOGGED IN");
        }
        return jsonResponse(userService.changePassword(user.getName(), encodedPass));
    }

    /**
     * Luo json vastauksen toimminnon onnistumisesta
     * @param error virheviesti joka naytetaan jos virhe ilmenee
     * @return json muodossa oleva vastaus
     */
    private String jsonResponse(final String error) {
        if (error.isEmpty()) {
            return "{\"status\":\"OK\"}";
        }
        return "{\"error\": \"" + error + "\"}";
    }
}
