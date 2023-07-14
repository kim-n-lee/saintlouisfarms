package org.liftoff.saintlouisfarms.controllers;

import org.liftoff.saintlouisfarms.data.ProductCategoryRepository;
import org.liftoff.saintlouisfarms.models.ProductCategory;
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
@RequestMapping("productType")
public class ProductCategoryController {
    // this controller for add a new type of product
    // Corresponds to http://localhost:8080/productType
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired AuthenticationController authenticationController;
    @GetMapping("")
    public String index(Model model ,HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        model.addAttribute("productTypes", productCategoryRepository.findProductsTypetById(user.getId()));
        model.addAttribute("loggedIn", session.getAttribute("user") != null);
        return "productType/index" ;
    }
    // Corresponds to http://localhost:8080/productType/add
    @GetMapping("add")
    public String displayAddProductTypeForm(Model model, HttpSession session) {
        model.addAttribute(new ProductCategory());
        model.addAttribute("loggedIn", session.getAttribute("user") != null);
        return "productType/add";
    }

    @PostMapping("add")
    public String processAddEmployerForm(@ModelAttribute @Valid ProductCategory newProductCategory,
                                         Errors errors, Model model,HttpServletRequest request) {
        HttpSession session=request.getSession();
        if (errors.hasErrors()) {
            model.addAttribute("loggedIn", session.getAttribute("user") != null);
            return "productType/add";
        }
        User user = authenticationController.getUserFromSession(session);
        newProductCategory.setUser(user);
        productCategoryRepository.save(newProductCategory);
        model.addAttribute("productType", productCategoryRepository.findProductsTypetById(user.getId()));
        return  "redirect:../farmer/add";
    }



}

