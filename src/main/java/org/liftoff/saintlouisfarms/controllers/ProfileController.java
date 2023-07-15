package org.liftoff.saintlouisfarms.controllers;

import org.liftoff.saintlouisfarms.data.UserRepository;
import org.liftoff.saintlouisfarms.models.Product;
import org.liftoff.saintlouisfarms.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("farmer/profile")
public class ProfileController {

    @Autowired
    private AuthenticationController authenticationController;
    @Autowired
    private UserRepository userRepository;
    //
    @GetMapping("")
    public String displayFarmerInformation(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        model.addAttribute("UserInformation", userRepository.findById(user.getId()));
        model.addAttribute("loggedIn", session.getAttribute("user") != null);
        return "farmer/profile";
    }

    @GetMapping("edit")
    public String editFarmerInformation( Model model , HttpServletRequest request){
    HttpSession session=request.getSession();
    User user=authenticationController.getUserFromSession(session);

    User profileFarmerToEdit=userRepository.findById(user.getId());

        model.addAttribute("title", "Edit " + profileFarmerToEdit.getFirstName()+" "+ profileFarmerToEdit.getFirstName()+" Information:");
        model.addAttribute("profileFarmerToEdit", profileFarmerToEdit);
        model.addAttribute("id", profileFarmerToEdit.getId());
        return "farmer/editProfile";
    }
@PostMapping("edit")
    public String editFarmerInfoProcessing(
                                           Model model,
                                           @ModelAttribute @Valid User editUser,

                                           HttpServletRequest request) {

    HttpSession session = request.getSession();
    User user = authenticationController.getUserFromSession(session);
    User farmer=userRepository.findById(user.getId());

    farmer.setAddress(editUser.getAddress());
    farmer.setCity(editUser.getCity());
    farmer.setEmail(editUser.getEmail());
    farmer.setPhone(editUser.getPhone());
    farmer.setFarmName(editUser.getFarmName());
    farmer.setFirstName(editUser.getFirstName());
    farmer.setLastName(editUser.getLastName());
    farmer.setZip(editUser.getZip());
    userRepository.save(farmer);
    return "redirect:";
    }

    @PostMapping("{farmerToDeleteId}")
    public String deleteProductProcessing(@PathVariable int farmerToDeleteId,
                                          Model model,
                                          HttpServletRequest request,
                                          Boolean confirmation) {
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        if (confirmation) {
            userRepository.deleteById(farmerToDeleteId);
//            productRepository.deleteAll(user.getProducts());
//            productCategoryRepository.deleteAll(user.getProductCategories());
//            measurementCategoryRepository.deleteAll(user.getMeasurementCategories());
            userRepository.delete(user);
            // delete will be on the same page
            model.addAttribute("title", "delete account");
            model.addAttribute("delete", "delete");
            return "login";

        }
        model.addAttribute("title", "delete account");
        model.addAttribute("farmerToDeletedId", farmerToDeleteId);
        return "farmer/profile";
    }
}

