package myStops.controller;

import myStops.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
public class PersonController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/person/newuser", method = RequestMethod.POST)
    @ResponseBody
    public String addNewUser(@RequestBody final String person) {
        return jsonResponse(userService.addUser(person));
    }

    @RequestMapping(value = "/person/isUsernameTaken", method = RequestMethod.POST)
    @ResponseBody
    public String isUsernameTaken(@RequestBody final String username) {
        return jsonResponse(userService.isUsernameTaken(username));
    }

    @RequestMapping(value = "/person/delete/", method = RequestMethod.DELETE)
    public String deleteUser(final Principal user) {
        if(user == null) {
            return jsonResponse("NOT LOGGED IN");
        }
        return jsonResponse(userService.deluser(user.getName()));
    }

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

    private String jsonResponse(final String error) {
        if (error.isEmpty()) {
            return "{\"status\":\"OK\"}";
        }
        return "{\"error\": \"" + error + "\"}";
    }
}
