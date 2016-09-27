package myStops.service;

import com.google.gson.Gson;
import myStops.domain.Person;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import myStops.repo.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class UserService {

    @Autowired
    private PersonRepo personRepo;

    @Transactional
    public String addUser(final String encodedPersonJson) {
        Person person = makePersonFrom(encodedPersonJson);
        String valid = validateNewUser(person);
        if (!valid.isEmpty()) {
            return valid;
        }
        try {
            personRepo.save(person);
        } catch (Exception databaseException) {
            return "Tietokantavirhe!";
        }
        return "";
    }

    @Transactional
    public String deluser(final String username) {
        try {
            personRepo.delete(personRepo.findByUsername(username));
        } catch (Exception databaseException) {
            return "Tietokantavirhe!";
        }
        return "";
    }

    @Transactional
    public String changePassword(
            final String username,
            final String encodedPassword
    ) {
        try {
            String decodedPassword = decode(encodedPassword);
            if (decodedPassword == null || decodedPassword.length() < 5) {
                return "Salasana liian lyhyt";
            }
            Person person = personRepo.findByUsername(username);
            if (person == null) {
                return "Käyttäjää ei löydy.";
            }
            person.hashPasswordWithSalt(decodedPassword);
            return "";
        } catch (Exception exception) {
            return "Virhe salasanan kääntämisessä selkokieliseen muotoon"
                    + "tai hakiessa henkilöä tietokannasta!";
        }

    }

    @Transactional
    public String isUsernameTaken(final String username) {
        if (personRepo.findByUsername(username) != null) {
            return "NIMIMERKKI VARATTU";
        }
        return "";
    }

    private String validateNewUser(Person person) {
        if (person == null) {
            return "Virheellinen muotoilu";
        }
        String userName = person.getUsername();
        System.out.println(person.getUsername());
        System.out.println(person.getHashOfPasswordAndSalt());
        if (person.getHashOfPasswordAndSalt() == null
                || person.getHashOfPasswordAndSalt().isEmpty()
                || userName == null
                || userName.isEmpty()
                || personRepo.findByUsername(userName) != null) {
            return "Käyttäjää ei voitu lisätä";
        }

        return "";
    }

    private Person makePersonFrom(final String encodedPersonJson) {
        try {
            String decodedPersonJson = decode(encodedPersonJson);
            System.out.println(decodedPersonJson);
            decodedPersonJson = decodedPersonJson.replaceFirst("password",
                    "authenticationHash");
            Gson gson = new Gson();
            Person person = gson.fromJson(decodedPersonJson, Person.class);
            person.hashPasswordWithSalt(person.getHashOfPasswordAndSalt());
            return person;
        } catch (Exception ex) {
            return null;
        }
    }

    private static String decode(
            final String encodedData) throws UnsupportedEncodingException {
        return new String(Base64Utils.decodeFromString(encodedData), "UTF-8");
    }
}
