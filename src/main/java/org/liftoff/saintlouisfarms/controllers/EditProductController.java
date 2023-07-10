package org.liftoff.saintlouisfarms.controllers;

import org.liftoff.saintlouisfarms.data.MeasurementCategoryRepository;
import org.liftoff.saintlouisfarms.data.ProductCategoryRepository;
import org.liftoff.saintlouisfarms.data.ProductRepository;
import org.liftoff.saintlouisfarms.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    @GetMapping("/{editProductId}")
    public String editProduct(@PathVariable int editProductId, Model model, HttpServletRequest request){
        HttpSession session=request.getSession();
        User user = authenticationController.getUserFromSession(session);

        Optional<Product> optProductToEdit = productRepository.findById(editProductId);
        //check if product we want to edit not in the system!
        if(optProductToEdit.isEmpty()){
            model.addAttribute("title", "Available Products");
            model.addAttribute("products", productRepository.findProductByStatus(user.getId()));
            model.addAttribute("availability","ProductNotFound");
            return "redirect:farmer/products";
        }
        Product productToEdit = optProductToEdit.get();

        model.addAttribute("title",
                "Edit "+productToEdit.getName());
        model.addAttribute("productType", productCategoryRepository.findAll());
        model.addAttribute("measurements", measurementCategoryRepository.findAll());
        model.addAttribute("productToEdit", productToEdit);
        model.addAttribute("editProductId", editProductId);
        return "farmer/edit";
    }
    @PostMapping("/{productToEditId}")
    public String editEmployeeProcessing(@PathVariable int productToEditId,
                                         Model model,
                                         String name,
                                         MeasurementCategory measurementCategory,
                                         ProductDetails productDetails,
                                         ProductCategory productCategory,

                                         HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        Optional<Product> optProductToEdit = productRepository.findById(productToEditId);

        if (optProductToEdit.isEmpty()) {
            model.addAttribute("title", "Available Products");
            model.addAttribute("products", productRepository.findProductByStatus(user.getId()));
            model.addAttribute("availability","ProductNotFound");

            return "redirect:";
        }

        Product productToEdit = optProductToEdit.get();


        productToEdit.setName(name);
        productToEdit.setProductDetails(productDetails);
        productToEdit.setProductCategory(productCategory);
        productToEdit.setMeasurementcategory(measurementCategory);
        model.addAttribute("Done","DoneEdit");


        productRepository.save(productToEdit);


        model.addAttribute("title", "Edit "+productToEdit);
        model.addAttribute("productToEdit", productToEdit);
        model.addAttribute("productToEditId", productToEditId);
        return "redirect:farmer/products";
    }



}
