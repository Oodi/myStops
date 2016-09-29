package myStops;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;

/**
 * Luokka sovelluksen käynnistämiseen
 */
@SpringBootApplication
public class Application {

    /**
     * Käynnistää Spring sovelluksetn
     * @param args Komentoriviargumentit
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
