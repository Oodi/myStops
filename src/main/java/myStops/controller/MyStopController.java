package myStops.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @RequestMapping(value = "/mystop/locationName", method = RequestMethod.POST, consumes = {"application/json"})
    @ResponseBody
    public String changeLocationName(@RequestBody final ChangeLocation names,
                                 final Principal user) {
        return jsonResponse(myStopService.changeLocationName(user.getName(), names.getOldName(), names.getNewName()));
    }

    @RequestMapping(value = "/mystop/location", method = RequestMethod.DELETE)
    @ResponseBody
    public String removeLocation(@RequestBody final String location,
                                 final Principal user) {
        return jsonResponse(myStopService.deleteLocation(user.getName(), location));
    }

    @RequestMapping(value = "/mystop/stopOfLocation", method = RequestMethod.POST)
    @ResponseBody
    public String getStopsOfLocation(@RequestBody final String location,
                                    final Principal user) {
        if (user == null) {
            return "";
        }
        return myStopService.getStops(user.getName(), location);
    }

    @RequestMapping(value = "/mystop/stop", method = RequestMethod.POST, consumes = {"application/json"})
    @ResponseBody
    public String addStopToLocation(@RequestBody final StopLocation stopLocation,
                             final Principal user) {
        return jsonResponse(myStopService.addStopToLocation(user.getName(), stopLocation.getLocation(), stopLocation.getStopID()));
    }

    @RequestMapping(value = "/mystop/stop", method = RequestMethod.DELETE, consumes = {"application/json"})
    @ResponseBody
    public String removeStopFromLocation(@RequestBody final StopLocation stopLocation,
                                    final Principal user) {
        return jsonResponse(myStopService.removeStopFromLocation(user.getName(), stopLocation.getLocation(), stopLocation.getStopID()));
    }


    private String jsonResponse(final String error) {
        if (error.isEmpty()) {
            return "{\"status\":\"OK\"}";
        }
        return "{\"error\": \"" + error + "\"}";
    }


}

 class StopLocation {
     @JsonProperty("location")
    private String location;
     @JsonProperty("stopID")
    private String stopID;

     @JsonCreator
    public StopLocation(@JsonProperty("location") String location, @JsonProperty("stopID") String stopID) {
        this.location = location;
        this.stopID = stopID;
    }


    public String getStopID() {
        return stopID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStopID(String stopID) {
        this.stopID = stopID;
    }
}

class ChangeLocation {
    @JsonProperty("oldName")
    private String oldName;
    @JsonProperty("newName")
    private String newName;

    @JsonCreator
    public ChangeLocation(@JsonProperty("oldName") String oldName, @JsonProperty("newName") String newName) {
        this.oldName = oldName;
        this.newName = newName;
    }

    public String getNewName() {
        return newName;
    }

    public String getOldName() {
        return oldName;
    }
}
