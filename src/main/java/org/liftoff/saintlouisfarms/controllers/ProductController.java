package org.liftoff.saintlouisfarms.controllers;


import org.liftoff.saintlouisfarms.data.*;
import org.liftoff.saintlouisfarms.models.MeasurementCategory;
import org.liftoff.saintlouisfarms.models.Product;
import org.liftoff.saintlouisfarms.models.ProductCategory;
import org.liftoff.saintlouisfarms.models.ProductData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("farmer")
public class ProductController {

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
    @RequestMapping("")
    public String searchProduct(Model model, HttpSession session) {
        model.addAttribute("loggedIn", session.getAttribute("user") != null);
        model.addAttribute("productType", productCategoryRepository.findAll());
        return "farmer/products";
    }
    //display result of searching
    @PostMapping("results")
    public String displayProductResults(Model model, @RequestParam String searchType, @RequestParam String searchTerm){
        Iterable<Product> products;
        if ( searchTerm.equals("")){
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
    public String displayAddProductForm(Model model) {
        model.addAttribute("title", "Add Product");
        model.addAttribute("productType", productCategoryRepository.findAll());
        model.addAttribute("measurements", measurementCategoryRepository.findAll());
        model.addAttribute(new Product());
        model.addAttribute("products", productRepository.findAll());
        return "farmer/add";
    }
    @PostMapping("add")
    public String processAddProductForm(@ModelAttribute @Valid Product newProduct,
                                        Errors errors, Model model)
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
        productRepository.save(newProduct);
//        Should also set userid to user logged in
        model.addAttribute("product", productRepository.findAll());
        return "redirect:add";
    }

}