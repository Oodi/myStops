package myStops.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class mainController {

    @RequestMapping("/hello")
    public String hello() {
        return "Hei ";
    }
}
