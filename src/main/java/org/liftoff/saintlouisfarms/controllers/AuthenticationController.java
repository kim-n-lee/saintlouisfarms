package org.liftoff.saintlouisfarms.controllers;


import org.liftoff.saintlouisfarms.data.ClientRepository;

import org.liftoff.saintlouisfarms.data.UserRepository;
import org.liftoff.saintlouisfarms.models.Client;
import org.liftoff.saintlouisfarms.models.DTO.LoginFormDTO;
import org.liftoff.saintlouisfarms.models.DTO.RegisterFormClientDTO;
import org.liftoff.saintlouisfarms.models.DTO.RegisterFormDTO;
import org.liftoff.saintlouisfarms.models.MainUser;
import org.liftoff.saintlouisfarms.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class AuthenticationController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ClientRepository clientRepository;


    // The key to store user IDs
    private static final String userSessionKey = "user";
    private static final String clientSessionKey = "client";

    // Look up user with key
    public User getUserFromSession(HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        if (userId == null) {
            return null;
        }

        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            return null;
        }

        return user.get();
    }
    // Stores key/value pair with session key and user ID

    private static void setUserInSession(HttpSession session, User user) {
        session.setAttribute(userSessionKey, user.getId());
    }




    public Client getClientFromSession(HttpSession session) {
        Integer userId = (Integer) session.getAttribute(clientSessionKey);
        if (userId == null) {
            return null;
        }

        Optional<Client> client = clientRepository.findById(userId);

        if (client.isEmpty()) {
            return null;
        }

        return client.get();
    }
    // Stores key/value pair with session key and user ID

    private static void setClientInSession(HttpSession session, Client client) {
        session.setAttribute(clientSessionKey, client.getId());
    }



//registerClient


    @GetMapping("/registerClient")
    public String displayRegistrationFormClient(Model model,HttpSession session) {
        model.addAttribute(new RegisterFormClientDTO());
        model.addAttribute("title", "Client Register");
        model.addAttribute("loggedIn", session.getAttribute("client") != null);

        return "registerClient";
    }

    @PostMapping("/registerClient")
    public String processRegistrationFormClient(@ModelAttribute @Valid RegisterFormClientDTO registerFormClientDTO,

                                                Errors errors, HttpServletRequest request,
                                                Model model) {

        // Send user back to form if errors are found
        if (errors.hasErrors()) {
            model.addAttribute("title", "Client Register");
            return "registerClient";
        }
// Look up user in database using email they provided in the form
        Client existingUser = clientRepository.findByEmail(registerFormClientDTO.getEmail());
        // Send user back to form if email already exists
        if (existingUser != null) {
            errors.rejectValue("email", "email.alreadyexists", "A user with that email already exists");
            model.addAttribute("title", "Client Register");
            return "registerClient";
        }
// Send user back to form if passwords didn't match
        String password = registerFormClientDTO.getPassword();
        String verifyPassword = registerFormClientDTO.getVerifyPassword();
        if (!password.equals(verifyPassword)) {
            errors.rejectValue("password", "passwords.mismatch", "Passwords do not match");
            model.addAttribute("title", "Client Register");
            return "registerClient";
        }

        // OTHERWISE, save new email and hashed password in database, start a new session, and redirect to home page
        Client newClient = new Client(registerFormClientDTO.getEmail(), registerFormClientDTO.getPassword(), registerFormClientDTO.getFirstName(),
                registerFormClientDTO.getLastName(), registerFormClientDTO.getAddress(), registerFormClientDTO.getZip(), registerFormClientDTO.getCity(), registerFormClientDTO.getPhone());
        clientRepository.save(newClient);
        setClientInSession(request.getSession(), newClient);

        return "redirect:farmer/products";
    }



    //farmer  Part


    @GetMapping("/register")
    public String displayRegistrationForm(Model model,HttpSession session) {
        model.addAttribute(new RegisterFormDTO());
        model.addAttribute("title", "Register");
        model.addAttribute("loggedIn", session.getAttribute("user") != null);

        return "register";
    }

    @PostMapping("/register")
    public String processRegistrationForm(@ModelAttribute @Valid RegisterFormDTO registerFormDTO,
                                          Errors errors, HttpServletRequest request,
                                          Model model) {
        // Send user back to form if errors are found
        if (errors.hasErrors()) {
            model.addAttribute("title", "Register");
            return "register";
        }
// Look up user in database using email they provided in the form
        User existingUser = userRepository.findByEmail(registerFormDTO.getEmail());
        User existingFarm= userRepository.findByFarmName(registerFormDTO.getFarmName());

        // Send user back to form if email already exists
        if (existingUser != null ) {
            errors.rejectValue("email", "email.alreadyexists", "A user with that email already exists");
            model.addAttribute("title", "Register");
            return "register";
        }
        //check  duplicates
        if (existingFarm != null ) {
            errors.rejectValue("farmName", "farmName.alreadyexists", "This Farm Name is already exists");
            model.addAttribute("title", "Register");
            return "register";
        }
// Send user back to form if passwords didn't match
        String password = registerFormDTO.getPassword();
        String verifyPassword = registerFormDTO.getVerifyPassword();
        if (!password.equals(verifyPassword)) {
            errors.rejectValue("password", "passwords.mismatch", "Passwords do not match");
            model.addAttribute("title", "Register");
            return "register";
        }


        // OTHERWISE, save new email and hashed password in database, start a new session, and redirect to home page

        User newUser = new User(registerFormDTO.getEmail(), registerFormDTO.getPassword(), registerFormDTO.getFirstName(),
                registerFormDTO.getLastName(), registerFormDTO.getAddress(), registerFormDTO.getZip(), registerFormDTO.getCity(), registerFormDTO.getPhone(),registerFormDTO.getFarmName());

        userRepository.save(newUser);

        setUserInSession(request.getSession(), newUser);

        return "redirect:farmer/dashboard";
    }
    // Handlers for login form
    @GetMapping("/login")
    public String displayLoginForm(Model model, HttpSession session) {
        model.addAttribute(new LoginFormDTO());
        model.addAttribute("title", "Log In");
        if(session.getAttribute("user") != null){

            model.addAttribute("loggedIn", session.getAttribute("user") != null);
        }
        else{
            model.addAttribute("loggedIn", session.getAttribute("client") != null);}


        return "login";
    }

    @PostMapping("/login")
    public String processLoginForm(@ModelAttribute @Valid LoginFormDTO loginFormDTO,
                                   Errors errors, HttpServletRequest request,
                                   Model model) {
// Send user back to form if errors are found
        if (errors.hasErrors()) {
            model.addAttribute("title", "Log In");
            return "login";
        }
        // Look up user in database using email they provided in the form
        User theUser = userRepository.findByEmail(loginFormDTO.getEmail());
        Client theClient=clientRepository.findByEmail((loginFormDTO.getEmail()));
        if (theUser == null && theClient==null) {
            errors.rejectValue("email", "email.invalid", "The given email does not exist");
            model.addAttribute("title", "Log In");
            return "login";
        }

        //if farmer
        if(theUser!=null && theClient==null){

            String password = loginFormDTO.getPassword();


            if (!theUser.isMatchingPassword(password)) {
                errors.rejectValue("password", "password.invalid", "Invalid password");
                model.addAttribute("title", "Log In");
                return "login";
            }

            // OTHERWISE, create a new session for the user and take them to the home page
            setUserInSession(request.getSession(), theUser);


            return "redirect:farmer/dashboard";

        }
        //if client
        else {
            String password = loginFormDTO.getPassword();

            if (!theClient.isMatchingPassword(password)) {
                errors.rejectValue("password", "password.invalid", "Invalid password");
                model.addAttribute("title", "Log In");
                return "login";
            }

            // OTHERWISE, create a new session for the user and take them to the home page
            setClientInSession(request.getSession(), theClient);

// go to the page of available products of farms
            return "redirect:farmer/availableProducts";

        }
    }
    // Handler for logout
    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();
        return "redirect:../index"; // tried changing the return value from "redirect:/login" to "redirect:/index" but it
    }                   // wouldn't work for some reason. This achieves functionality but the address bar shows "logout"

}