package org.liftoff.saintlouisfarms.controllers;


import org.liftoff.saintlouisfarms.data.*;
import org.liftoff.saintlouisfarms.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.Console;
import java.util.List;

@Controller
@RequestMapping("farmer")
public class ProductController {

    @Autowired
    private AuthenticationController authenticationController;
    @Autowired
    private MeasurementCategoryRepository measurementCategoryRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private ProductDetailsRepository productDetailsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    //I think we need a method for farmer/store which displays products currently available
    //display navbar of products type and the search field
//    @RequestMapping("")
//    public String searchProduct(Model model, HttpSession session) {
//        model.addAttribute("loggedIn", session.getAttribute("user") != null);
//        model.addAttribute("productType", productCategoryRepository.findAll());
//        return "farmer/products";
//    }
    // this method return all available products (product status =1)
    @GetMapping("products")
    public String displayAllProducts(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        // model.addAttribute("title","Available Products");
        model.addAttribute("products", productRepository.findProductByStatus(user.getId()));
        return "farmer/products";
    }

    //display result of searching
    @PostMapping("results")
    public String displayProductResults(Model model, @RequestParam String searchType, @RequestParam String searchTerm) {
        Iterable<Product> products;
        if (searchTerm.equals("")) {
            products = productRepository.findAll();
        } else {
            products = ProductData.findByColumnAndValue(searchType, searchTerm, productRepository.findAll());
        }
        model.addAttribute("productType", productCategoryRepository.findAll());
        model.addAttribute("title", "Products in " + productCategoryRepository.findByName(searchType) + ": " + searchTerm);
        model.addAttribute("products", productRepository.findAll());
        return "redirect:";
    }

    // add new product
    @GetMapping("add")
    public String displayAddProductForm(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        model.addAttribute("title", "Add Product");
        model.addAttribute("productType", productCategoryRepository.findProductsTypetById(user.getId()));
        model.addAttribute("measurements", measurementCategoryRepository.findMeasurementById(user.getId()));
        model.addAttribute(new Product());
        model.addAttribute("products", productRepository.findProductById(user.getId()));
        return "farmer/add";
    }

    @PostMapping("add")
    public String processAddProductForm(@ModelAttribute @Valid Product newProduct,
                                        //  Errors errors, Model model)
                                        Errors errors, Model model, HttpServletRequest request)
    //                                        @RequestParam int productTypeId, @RequestParam int  measurmentId)
    {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Product");
            return "farmer/add";
        }
//        ProductCategory newProductType = productCategoryRepository.findById(productTypeId).orElse(new ProductCategory());
//        newProduct.setProductCategory(newProductType);
//        MeasurementCategory newMeasurment =  measurementCategoryRepository.findById(measurmentId).orElse(new MeasurementCategory());;
//        newProduct.setMeasurementcategory(newMeasurment);
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        newProduct.setUser(user);
        productRepository.save(newProduct);
//        Should also set userid to user logged in
        model.addAttribute("product", productRepository.findAll());
        return "redirect:add";
    }
};
