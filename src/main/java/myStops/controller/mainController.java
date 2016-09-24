package myStops.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Simo on 24.9.2016.
 */
@Controller
@ResponseBody
public class mainController {

    @RequestMapping("/")
    public String hello() {
        return "Hei ";
    }
}
