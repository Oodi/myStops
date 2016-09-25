package myStops.profiles;

import myStops.domain.Person;
import myStops.repo.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Configuration
@Profile("development")
public class DevProfile {

    @Autowired
    private PersonRepo personRepo;

    @PostConstruct
    @Transactional
    public void init() {
        Person pro = new Person();
        pro.setUsername("user");
        pro.hashPasswordWithSalt("salasana");
        personRepo.save(pro);
    }
}
