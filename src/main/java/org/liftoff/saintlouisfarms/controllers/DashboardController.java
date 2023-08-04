package org.liftoff.saintlouisfarms.controllers;

import org.liftoff.saintlouisfarms.data.*;
import org.liftoff.saintlouisfarms.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("farmer")
public class DashboardController {
//landingPage
// provide description for each part of website


    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MeasurementCategoryRepository measurementCategoryRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductDetailsRepository productDetailsRepository;
    @Autowired
    private AuthenticationController authenticationController;
    @GetMapping("dashboard")
    public String viewDashboard(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        model.addAttribute("farmName", user.getFarmName());
        model.addAttribute("title","Dashboard");
        model.addAttribute("loggedIn", user != null);


      return "farmer/dashboard";
    }

    @GetMapping("settings")
    public String viewSettings(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        model.addAttribute("farmName", user.getFarmName());
        model.addAttribute("title","Dashboard");
        model.addAttribute("loggedIn", user != null);


        return "farmer/settings";
    }


}
