package org.liftoff.saintlouisfarms.controllers;

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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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
        model.addAttribute("editProductId", editProductId);
        model.addAttribute("loggedIn", session.getAttribute("user") != null);
        return "farmer/edit";
    }

    @PostMapping("/{productToEditId}")
    public String editProductProcessing(@PathVariable int productToEditId,
                                        Model model,
                                        @ModelAttribute @Valid Product productEdit, Errors errors,

                                        HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        Optional<Product> optProductToEdit = productRepository.findById(productToEditId);

        if (optProductToEdit.isEmpty()) {
            model.addAttribute("title", "Available Products");
            model.addAttribute("products", productRepository.findProductByStatus(user.getId()));
            model.addAttribute("availability", "ProductNotFound");
            model.addAttribute("loggedIn", session.getAttribute("user") != null);
            return "redirect:";
        }

        Product productToEdit = optProductToEdit.get();

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

