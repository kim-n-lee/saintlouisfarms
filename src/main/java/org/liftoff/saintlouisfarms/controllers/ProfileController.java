package org.liftoff.saintlouisfarms.controllers;

import org.imgscalr.Scalr;
import org.liftoff.saintlouisfarms.data.UserRepository;
import org.liftoff.saintlouisfarms.models.Product;
import org.liftoff.saintlouisfarms.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
        model.addAttribute("loggedIn", user != null);
        return "farmer/editProfile";
    }
    @PostMapping("edit")
    public String editFarmerInfoProcessing(
            Model model,

            @ModelAttribute("profileFarmerToEdit") @Valid User editUser,
            Errors errors,
            HttpServletRequest request,
            @RequestParam(required = false) MultipartFile newPicture) {


        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        if (errors.hasErrors()) {
            model.addAttribute("loggedIn", user != null);
            return "redirect:./edit";

        }

        User farmer=userRepository.findById(user.getId());

        if(!newPicture.getOriginalFilename().equals("")){
            try {
                if(newPicture.getSize()>2098576){throw new RuntimeException();};
                BufferedImage image = ImageIO.read(newPicture.getInputStream());
                BufferedImage scaledImage = Scalr.resize(image, Scalr.Method.BALANCED, 900, 1000);

                String filePath;
                if(farmer.getPicture()!=null) {
                    filePath = farmer.getPicture().replace(".jpg","edited.jpg");
                }else{
                    filePath = "images/" + user.getId() + farmer.getFarmName()+".jpg";
                }

                File outputfile = new File(filePath);
                ImageIO.write(scaledImage, "jpg", outputfile);
                farmer.setPicture(filePath);
            }catch(IOException | RuntimeException e){
                model.addAttribute("title", "Edit " + farmer.getFirstName()+" "+ farmer.getFirstName()+" Information:");
                model.addAttribute("profileFarmerToEdit", farmer);
                model.addAttribute("id", farmer.getId());
                model.addAttribute("pictureError", "There was something wrong with the picture you uploaded please try another smaller picture, up to 2MB");
                model.addAttribute("loggedIn", user != null);
                return "farmer/add";
            }
        }

        farmer.setAddress(editUser.getAddress());
        farmer.setCity(editUser.getCity());
        farmer.setEmail(editUser.getEmail());
        farmer.setPhone(editUser.getPhone());
        farmer.setFarmName(editUser.getFarmName());
        farmer.setFirstName(editUser.getFirstName());
        farmer.setLastName(editUser.getLastName());
        farmer.setZip(editUser.getZip());
        userRepository.save(farmer);
        model.addAttribute("loggedIn", user != null);
        return "redirect:";

    }


}