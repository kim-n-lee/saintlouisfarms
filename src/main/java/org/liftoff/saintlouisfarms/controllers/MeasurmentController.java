package org.liftoff.saintlouisfarms.controllers;
import org.liftoff.saintlouisfarms.data.MeasurementCategoryRepository;
import org.liftoff.saintlouisfarms.models.MeasurementCategory;
import org.liftoff.saintlouisfarms.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

import static java.time.temporal.TemporalAdjusters.previous;
@Controller
//@RequestMapping("")
public class MeasurmentController {
    // Corresponds to http://localhost:8080/measurements
    @Autowired
    private MeasurementCategoryRepository measurementCategoryRepository;
    @Autowired
    AuthenticationController authenticationController;
    @GetMapping("measurements")
    public String index(Model model,HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        model.addAttribute("measurements",measurementCategoryRepository.findMeasurementById(user.getId()));
        model.addAttribute("loggedIn", session.getAttribute("user") != null);
        return "measurements/index" ;
    }
    // Corresponds to http://localhost:8080/measurements/add
    @GetMapping("measurements/add")
    public String displayAddNewMeasurementForm(Model model, HttpSession session) {
        model.addAttribute(new MeasurementCategory());
        model.addAttribute("loggedIn", session.getAttribute("user") != null);
        return "measurements/add";
    }
    @PostMapping("measurements/add")
    public String processAddNewMeasurementForm(@ModelAttribute @Valid MeasurementCategory newMeasuremenCategory,
                                               Errors errors, Model model, HttpServletRequest request) {
        HttpSession session=request.getSession();
        User user = authenticationController.getUserFromSession(session);
        if (errors.hasErrors()) {
            model.addAttribute("loggedIn", session.getAttribute("user") != null);
            return "measurements/add";
        }
        newMeasuremenCategory.setUser(user);
        measurementCategoryRepository.save(newMeasuremenCategory);
        model.addAttribute("measurement", measurementCategoryRepository.findMeasurementById(user.getId()));
        model.addAttribute("loggedIn", session.getAttribute("user") != null);
        return "redirect:../farmer/add";
    }
// ...

    // Corresponds to http://localhost:8080/measurements/edit/{id}
    @GetMapping("measurements/edit/{id}")
    public String displayEditMeasurementForm(@PathVariable int editMeasurementCategory, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        Optional<MeasurementCategory> measurementCategory = measurementCategoryRepository.findById(editMeasurementCategory);
        if (measurementCategory.isEmpty()) {
            model.addAttribute("measurementCategory", measurementCategoryRepository.findById(user.getId()));
            model.addAttribute("loggedIn", session.getAttribute("user") != null);
            return "redirect:";
        }
        MeasurementCategory measurementToEdit = measurementCategory.get();
        model.addAttribute("editMeasurementEntity", measurementToEdit );
        model.addAttribute("editMeasurementId", editMeasurementCategory);
        model.addAttribute("loggedIn", session.getAttribute("user") != null);
        return "measurements/edit";
    }

    @PostMapping("measurements/edit/{id}")
    public String processEditMeasurementForm(@PathVariable int id,
                                             @ModelAttribute @Valid MeasurementCategory MeasurementCategory,
                                             Errors errors, Model model, HttpServletRequest request) {

        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        Optional<MeasurementCategory> optmeasurementCategory = measurementCategoryRepository.findById(id);

        if (errors.hasErrors()) {
            model.addAttribute("measurementCategory",measurementCategoryRepository.findMeasurementById(id));
            model.addAttribute("measurementCategory",measurementCategoryRepository.findById(id));
            model.addAttribute("loggedIn", session.getAttribute("user") != null);
            return "measurements/edit";
        }

        if (optmeasurementCategory.isEmpty()) {
            model.addAttribute("measurementCategory", measurementCategoryRepository.findById(user.getId()));
            model.addAttribute("measurementCategoryNotThere", "measurementNotFound");
            model.addAttribute("loggedIn", session.getAttribute("user") != null);
            return "redirect:";
        }

         MeasurementCategory measurementToEdit = optmeasurementCategory.get();

        measurementToEdit.setName(MeasurementCategory.getName());

        measurementCategoryRepository.save(measurementToEdit);

        model.addAttribute("measurementToEdit", measurementToEdit);
        model.addAttribute("measurementToEditId", id);
        model.addAttribute("loggedIn", session.getAttribute("user") != null);

        return "measurements/index";
    }


    @GetMapping("measurements/delete/{id}")
    public String deleteMeasurementCategory(@PathVariable int id, Model model,
                                            HttpServletRequest request) {

        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        Optional<MeasurementCategory> optmeasurementCategory = measurementCategoryRepository.findById(id);
        if (optmeasurementCategory.isEmpty()) {
            model.addAttribute("title", "Current measurements");
            model.addAttribute("currentProducts", measurementCategoryRepository.findMeasurementById(user.getId()));
            model.addAttribute("loggedIn", user != null);
        }

       MeasurementCategory measurementToDelete = optmeasurementCategory.get();

        measurementCategoryRepository.delete(measurementToDelete);
//        measurementCategoryRepository.deleteById(id);
        return "measurements/index";
    }



}




