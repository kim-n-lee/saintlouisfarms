package org.liftoff.saintlouisfarms.controllers;

import org.liftoff.saintlouisfarms.data.MeasurementCategoryRepository;
import org.liftoff.saintlouisfarms.models.MeasurementCategory;
import org.liftoff.saintlouisfarms.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("measurements")
public class MeasurmentController {

    // Corresponds to http://localhost:8080/measurements
    @Autowired
    private MeasurementCategoryRepository measurementCategoryRepository;
    @Autowired
    AuthenticationController authenticationController;
    @GetMapping("")
    public String index(Model model){
        model.addAttribute("measurements",measurementCategoryRepository.findAll());
        return "measurements/index" ;
    }
    // Corresponds to http://localhost:8080/measurements/add
    @GetMapping("add")
    public String displayAddNewMeasurementForm(Model model) {
        model.addAttribute(new MeasurementCategory());
        return "measurements/add";
    }

    @PostMapping("add")
    public String processAddNewMeasurementForm(@ModelAttribute @Valid MeasurementCategory newMeasuremenCategory,
                                               Errors errors, Model model, HttpServletRequest request) {
        HttpSession session=request.getSession();
        User user = authenticationController.getUserFromSession(session);
        if (errors.hasErrors()) {
            return "measurements/add";
        }
        measurementCategoryRepository.save(newMeasuremenCategory);
        model.addAttribute("measurement", measurementCategoryRepository.findAll());
        return "redirect:farmer";
    }

}
