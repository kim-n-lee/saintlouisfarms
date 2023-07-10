package org.liftoff.saintlouisfarms.controllers;


import org.liftoff.saintlouisfarms.data.UserRepository;
import org.liftoff.saintlouisfarms.models.DTO.LoginFormDTO;
import org.liftoff.saintlouisfarms.models.DTO.RegisterFormDTO;
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


    // The key to store user IDs
    private static final String userSessionKey = "user";

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
        // Send user back to form if email already exists
        if (existingUser != null) {
            errors.rejectValue("email", "email.alreadyexists", "A user with that email already exists");
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
        User newUser = new User(registerFormDTO.getEmail(), registerFormDTO.getPassword(),registerFormDTO.getFirstName(),
                registerFormDTO.getLastName(),registerFormDTO.getAddress(),registerFormDTO.getFarmName(),registerFormDTO.getPhone());

        userRepository.save(newUser);
        setUserInSession(request.getSession(), newUser);
        

        return "redirect:farmer/add";
    }
    // Handlers for login form
    @GetMapping("/login")
    public String displayLoginForm(Model model, HttpSession session) {
        model.addAttribute(new LoginFormDTO());
        model.addAttribute("title", "Log In");
        model.addAttribute("loggedIn", session.getAttribute("user") != null);
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

        if (theUser == null) {
            errors.rejectValue("email", "email.invalid", "The given email does not exist");
            model.addAttribute("title", "Log In");
            return "login";
        }

        String password = loginFormDTO.getPassword();

        if (!theUser.isMatchingPassword(password)) {
            errors.rejectValue("password", "password.invalid", "Invalid password");
            model.addAttribute("title", "Log In");
            return "login";
        }

        // OTHERWISE, create a new session for the user and take them to the home page
        setUserInSession(request.getSession(), theUser);

        return "redirect:farmer/add";
    }
    // Handler for logout
    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();
        return "redirect:/login";
    }

}
