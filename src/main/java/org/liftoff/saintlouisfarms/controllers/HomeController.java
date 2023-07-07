package org.liftoff.saintlouisfarms.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    // Corresponds to http://localhost:8080
    @GetMapping
    public String displayHomePage(Model model, HttpSession session) {
        model.addAttribute("loggedIn", session.getAttribute("user") != null);
        return "index";
    }

}
