package org.liftoff.saintlouisfarms.controllers;

import org.liftoff.saintlouisfarms.data.ProductCategoryRepository;
import org.liftoff.saintlouisfarms.models.MeasurementCategory;
import org.liftoff.saintlouisfarms.models.ProductCategory;
import org.liftoff.saintlouisfarms.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

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

    @PostMapping("/edit/{id}")
    public String processEditMeasurementForm(@PathVariable int id,
                                             @ModelAttribute @Valid ProductCategory productCategory,
                                             Errors errors, Model model, HttpServletRequest request) {

        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        Optional<ProductCategory> optproductCategory = productCategoryRepository.findById(id);

        if (errors.hasErrors()) {
            model.addAttribute("productCategory",productCategoryRepository.findProductsTypetById(id));
            model.addAttribute("productCategory",productCategoryRepository.findById(id));
            model.addAttribute("loggedIn", session.getAttribute("user") != null);
            return "/edit";
        }

        if (optproductCategory.isEmpty()) {
            model.addAttribute("productCategory", productCategoryRepository.findById(user.getId()));
            model.addAttribute("productCategoryNotThere", "productTypeNotFound");
            model.addAttribute("loggedIn", session.getAttribute("user") != null);
            return "redirect:";
        }

      ProductCategory productTypeToEdit = optproductCategory.get();

        productTypeToEdit.setName(productCategory.getName());

        productCategoryRepository.save(productTypeToEdit);

        model.addAttribute("productTypeToEdit)", productTypeToEdit);
        model.addAttribute("productTypeToEditId", id);
        model.addAttribute("loggedIn", session.getAttribute("user") != null);

        return "/index";
    }


    @GetMapping("delete/{id}")
    public String deleteProductCategory(@PathVariable int id, Model model,
                                            HttpServletRequest request) {

        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        Optional<ProductCategory> optproductCategory = productCategoryRepository.findById(id);
        if (optproductCategory.isEmpty()) {
            model.addAttribute("title", "Current measurements");
            model.addAttribute("currentProducts", productCategoryRepository.findProductsTypetById(user.getId()));
            model.addAttribute("loggedIn", user != null);
        }

        ProductCategory productToDelete = optproductCategory.get();

      productCategoryRepository.delete(productToDelete);

        return "/index";
    }

}

