package org.liftoff.saintlouisfarms.controllers;

import org.imgscalr.Scalr;
import org.liftoff.saintlouisfarms.data.MeasurementCategoryRepository;
import org.liftoff.saintlouisfarms.data.ProductCategoryRepository;
import org.liftoff.saintlouisfarms.data.ProductDetailsRepository;
import org.liftoff.saintlouisfarms.data.ProductRepository;
import org.liftoff.saintlouisfarms.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequestMapping("farmer/edit")
public class EditProductController {

    // edit product name,Category,Measurement ,and all details of it .
    @Autowired
    private AuthenticationController authenticationController;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MeasurementCategoryRepository measurementCategoryRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private ProductDetailsRepository productDetailsRepository;
    @GetMapping("/{editProductId}")
    public String editProduct(@PathVariable int editProductId, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        Optional<Product> optProductToEdit = productRepository.findById(editProductId);
        //check if product we want to edit not in the system!
        if (optProductToEdit.isEmpty()) {
            model.addAttribute("title", "Available Products");
            model.addAttribute("products", productRepository.findProductByStatus(user.getId()));
            model.addAttribute("availability", "ProductNotFound");
            model.addAttribute("loggedIn", session.getAttribute("user") != null);
            return "redirect:farmer/products";
        }

        Product productToEdit = optProductToEdit.get();

        model.addAttribute("title",
                "Edit " + productToEdit.getName());
        model.addAttribute("productType", productCategoryRepository.findProductsTypetById(user.getId()));
        model.addAttribute("measurements", measurementCategoryRepository.findMeasurementById(user.getId()));
        model.addAttribute("productToEdit", productToEdit);
        model.addAttribute("editProductToId", editProductId);
        model.addAttribute("loggedIn", session.getAttribute("user") != null);
        return "farmer/edit";
    }

    @PostMapping("/{productToEditId}")
    public String editProductProcessing(@PathVariable int productToEditId,
                                        Model model,
                                        @ModelAttribute("productToEdit") @Valid Product productEdit,
                                        Errors errors,
                                        HttpServletRequest request,
                                        @RequestParam(required = false, value = "picture") MultipartFile picture) {
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);



        if (errors.hasErrors()) {
            model.addAttribute("title",
                    "Edit " + productEdit.getName());
            model.addAttribute("productType", productCategoryRepository.findAll());
            model.addAttribute("measurements", measurementCategoryRepository.findAll());
            model.addAttribute("products", productRepository.findProductById(user.getId()));
            model.addAttribute("loggedIn", user != null);
            model.addAttribute("productToEdit", productEdit);
            return "farmer/edit";
        }

        Optional<Product> optProductToEdit = productRepository.findById(productToEditId);

        if (optProductToEdit.isEmpty()) {
            model.addAttribute("title", "Available Products");
            model.addAttribute("products", productRepository.findProductByStatus(user.getId()));
            model.addAttribute("availability", "ProductNotFound");
            model.addAttribute("loggedIn", session.getAttribute("user") != null);
            return "redirect:";
        }

        productEdit.setUser(user);
        Product productToEdit = optProductToEdit.get();

        System.out.println(picture);
        if(!picture.getOriginalFilename().equals("")){
            try {
                if(picture.getSize()>2098576){throw new RuntimeException();};
                BufferedImage image = ImageIO.read(picture.getInputStream());
                BufferedImage scaledImage = Scalr.resize(image, Scalr.Method.BALANCED, 900, 1000);

                String filePath;
                if(productToEdit.getProductDetails().getPicture()!=null) {
                     filePath = productToEdit.getProductDetails().getPicture()+1;
                }else{
                     filePath = "images/" + user.getId() + productEdit.getName() + productToEdit.getId() + ".jpg";
                }

                File outputfile = new File(filePath);
                ImageIO.write(scaledImage, "jpg", outputfile);
                productToEdit.getProductDetails().setPicture(filePath);
            }catch(IOException | RuntimeException e){
                model.addAttribute("title",
                        "Edit " + productEdit.getName());
                model.addAttribute("productType", productCategoryRepository.findProductsTypetById(user.getId()));
                model.addAttribute("measurements", measurementCategoryRepository.findMeasurementById(user.getId()));
                model.addAttribute("productToEdit", productEdit);
                model.addAttribute("editProductToId", productToEditId);
                model.addAttribute("loggedIn", session.getAttribute("user") != null);
                model.addAttribute("pictureError", "There was something wrong with the picture you uploaded please try another smaller picture, up to 2MB");
                model.addAttribute("title", "Add Product");
                model.addAttribute("productType", productCategoryRepository.findAll());
                model.addAttribute("measurements", measurementCategoryRepository.findAll());
                model.addAttribute("products", productRepository.findProductById(user.getId()));
                model.addAttribute("pictureError", "There was something wrong with the picture you uploaded please try another smaller picture, up to 2MB");
                model.addAttribute("loggedIn", user != null);
                return "farmer/edit";
            }
        }





        productToEdit.setName(productEdit.getName());
        productToEdit.getProductDetails().setPrice(productEdit.getProductDetails().getPrice());
        productToEdit.getProductDetails().setDescription(productEdit.getProductDetails().getDescription());
        productToEdit.getProductDetails().setQuantity(productEdit.getProductDetails().getQuantity());
        productToEdit.getProductDetails().setStatus(productEdit.getProductDetails().getStatus());

        productToEdit.getMeasurementcategory().setName(productEdit.getMeasurementcategory().getName());
        productToEdit.getProductCategory().setName(productEdit.getProductCategory().getName());

        productRepository.save(productToEdit);

        model.addAttribute("Done", "DoneEdit");

        model.addAttribute("title", "Edit " + productToEdit);
        model.addAttribute("productToEdit", productToEdit);
        model.addAttribute("productToEditId", productToEditId);
        model.addAttribute("loggedIn", session.getAttribute("user") != null);
        return "redirect:../add";
    }

    @PostMapping(value="quickedit")
    public String editQuick( Model model,
                             @RequestParam int productId,
                             @RequestParam BigDecimal price,
                             @RequestParam Integer quantity,
                             @RequestParam Optional<Boolean> status,
                             HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        Optional<Product> optProductToEdit = productRepository.findById(productId);

        if (optProductToEdit.isEmpty()) {
//            model.addAttribute("title", "Available Products");
//            model.addAttribute("products", productRepository.findProductByStatus(user.getId()));
            model.addAttribute("availability", "ProductNotFound");
//            model.addAttribute("loggedIn", session.getAttribute("user") != null);
            return "redirect:../add";
        }

        Product productToEdit = optProductToEdit.get();

        Optional<ProductDetails> optProductDetailsToEdit = productDetailsRepository.findById(productToEdit.getProductDetails().getId());
        ProductDetails productDetailsToEdit = optProductDetailsToEdit.get();


        productDetailsToEdit.setPrice(price);
        productDetailsToEdit.setQuantity(quantity);
        if(status.isPresent()) {
            productDetailsToEdit.setStatus(status.get());
        } else {
            productDetailsToEdit.setStatus(false);
        }


        productDetailsRepository.save(productDetailsToEdit);

        model.addAttribute("Done", "DoneEdit");
        return "redirect:../add";
    }

}