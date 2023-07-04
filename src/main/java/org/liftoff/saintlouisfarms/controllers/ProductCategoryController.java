package org.liftoff.saintlouisfarms.controllers;

import org.liftoff.saintlouisfarms.data.ProductCategoryRepository;
import org.liftoff.saintlouisfarms.models.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("productType")
public class ProductCategoryController {
    // this controller for add a new type of product
    // Corresponds to http://localhost:8080/productType
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @GetMapping("")
    public String index(Model model){
        model.addAttribute("productTypes",productCategoryRepository.findAll());
        return "productType/index" ;
    }
    // Corresponds to http://localhost:8080/productType/add
    @GetMapping("add")
    public String displayAddProductTypeForm(Model model) {
        model.addAttribute(new ProductCategory());
        return "productType/add";
    }

    @PostMapping("add")
    public String processAddEmployerForm(@ModelAttribute @Valid ProductCategory newProductCategory,
                                         Errors errors, Model model) {

        if (errors.hasErrors()) {
            return "productType/add";
        }
        productCategoryRepository.save(newProductCategory);
        model.addAttribute("productType", productCategoryRepository.findAll());
        return "redirect:";
    }



}

