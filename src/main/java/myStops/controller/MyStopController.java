package myStops.controller;

import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class MyStopController {

    @RequestMapping(value = "/mystop/location", method = RequestMethod.GET)
    @ResponseBody
    public String addNewLocation(final Principal user) {
        return jsonResponse("");
    }

    @RequestMapping(value = "/mystop/location", method = RequestMethod.POST)
    @ResponseBody
    public String addNewLocation(@RequestBody final String location,
                             final Principal user) {
        return jsonResponse("");
    }

    @RequestMapping(value = "/mystop/locationName", method = RequestMethod.POST)
    @ResponseBody
    public String changeLocationName(@RequestBody final String location,
                                     @RequestBody final String locationNewName,
                                 final Principal user) {
        return jsonResponse("");
    }

    @RequestMapping(value = "/mystop/location", method = RequestMethod.DELETE)
    @ResponseBody
    public String removeLocation(@RequestBody final String location,
                                 final Principal user) {
        return jsonResponse("");
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
