package myStops.controller;

import myStops.service.MyStopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class MyStopController {

    @Autowired
    private MyStopService myStopService;

    @RequestMapping(value = "/mystop/location", method = RequestMethod.GET)
    @ResponseBody
    public String getLocations(final Principal user) {
        if (user == null) {
            return "";
        }
        return myStopService.getLocations(user.getName());
    }

    @RequestMapping(value = "/mystop/location", method = RequestMethod.POST)
    @ResponseBody
    public String addNewLocation(@RequestBody final String location,
                             final Principal user) {
        return jsonResponse(myStopService.addNewLocation(user.getName(), location));
    }

    @RequestMapping(value = "/mystop/locationName", method = RequestMethod.POST)
    @ResponseBody
    public String changeLocationName(@RequestBody final String location,
                                     @RequestBody final String locationNewName,
                                 final Principal user) {
        return jsonResponse(myStopService.changeLocationName(user.getName(), location, locationNewName));
    }

    @RequestMapping(value = "/mystop/location", method = RequestMethod.DELETE)
    @ResponseBody
    public String removeLocation(@RequestBody final String location,
                                 final Principal user) {
        return jsonResponse(myStopService.deleteLocation(user.getName(), location));
    }

    @RequestMapping(value = "/mystop/stop", method = RequestMethod.GET)
    @ResponseBody
    public String getStopsOfLocaton(@RequestBody final String location,
                                    final Principal user) {
        return jsonResponse("");
    }

    @RequestMapping(value = "/mystop/stop", method = RequestMethod.POST)
    @ResponseBody
    public String addStopToLocation(@RequestBody final String location,
                                    @RequestBody final String stopID,
                             final Principal user) {
        return jsonResponse("");
    }

    @RequestMapping(value = "/mystop/stop", method = RequestMethod.DELETE)
    @ResponseBody
    public String removeStopFromLocation(@RequestBody final String location,
                                    @RequestBody final String stopID,
                                    final Principal user) {
        return jsonResponse("");
    }


    private String jsonResponse(final String error) {
        if (error.isEmpty()) {
            return "{\"status\":\"OK\"}";
        }
        return "{\"error\": \"" + error + "\"}";
    }
}
