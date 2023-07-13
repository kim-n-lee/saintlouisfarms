package org.liftoff.saintlouisfarms.controllers;


import org.imgscalr.Scalr;
import org.liftoff.saintlouisfarms.data.*;
import org.liftoff.saintlouisfarms.models.*;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
    Errors errors, Model model, HttpServletRequest request, @RequestParam(required = false) MultipartFile picture) throws IOException
    {
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Product");
            model.addAttribute("productType", productCategoryRepository.findAll());
            model.addAttribute("measurements", measurementCategoryRepository.findAll());
            model.addAttribute("products", productRepository.findProductById(user.getId()));
            return "farmer/add";
        }

        newProduct.setUser(user);
        if(!picture.getOriginalFilename().equals("")){
            try {
                if(picture.getSize()>2098576){throw new RuntimeException();};
                BufferedImage image = ImageIO.read(picture.getInputStream());
                BufferedImage scaledImage = Scalr.resize(image, Scalr.Method.BALANCED, 900, 1000);
                String filePath = "images/" + user.getId() + newProduct.getName() + newProduct.getId() + ".jpg";
                File outputfile = new File(filePath);
                ImageIO.write(scaledImage, "jpg", outputfile);
                newProduct.getProductDetails().setPicture(filePath);
            }catch(IOException | RuntimeException e){
                model.addAttribute("title", "Add Product");
                model.addAttribute("productType", productCategoryRepository.findAll());
                model.addAttribute("measurements", measurementCategoryRepository.findAll());
                model.addAttribute("products", productRepository.findProductById(user.getId()));
                model.addAttribute("pictureError", "There was something wrong with the picture you uploaded please try another smaller picture, up to 2MB");
                return "farmer/add";
            }
        }


        productRepository.save(newProduct);
//        Should also set userid to user logged in
        model.addAttribute("product", productRepository.findAll());
        return "redirect:add";
    }
    @PostMapping("{productToDeleteId}")
    public String deleteProductProcessing(@PathVariable int productToDeleteId,
                                          Model model,
                                          HttpServletRequest request,
                                          Boolean confirmation) {
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        Optional<Product> optProductToDelete = productRepository.findById(productToDeleteId);
        if (optProductToDelete.isEmpty()) {
            model.addAttribute("title", "Current Employees");
            model.addAttribute("currentEmployees", productRepository.findProductById(user.getId()));
            model.addAttribute("cannotFindEmployee", "ProductNotFound");
            return "farmer/add";

        }
        Product productToDelete = optProductToDelete.get();
        if (confirmation) {
            productRepository.deleteById(productToDeleteId);
            productCategoryRepository.deleteById(productToDelete.getProductCategory().getId());
            measurementCategoryRepository.deleteById(productToDelete.getMeasurementcategory().getId());
            productDetailsRepository.deleteById(productToDelete.getProductDetails().getId());
            // delete will be on the same page

            return "farmer/add";
        }
        return "farmer/add";


    }
};
