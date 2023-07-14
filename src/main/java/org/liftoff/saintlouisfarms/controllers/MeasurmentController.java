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
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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
}




//    @RequestMapping(value = "/rate", method = RequestMethod.POST)
//    public String rateHandler(HttpServletRequest request) {
//        //your controller code
//        String referer = request.getHeader("Referer");
//        return "redirect:"+ referer;
//    }