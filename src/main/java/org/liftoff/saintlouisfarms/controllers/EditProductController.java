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
import javax.validation.Valid;
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


//    @PutMapping("/{productToEditId}")
//    public Product editProductsProcessing(@RequestBody Product editProduct
//            ,@PathVariable int productToEditId, HttpServletRequest request,Model model){
//Optional<Product> productToedit=productRepository.findById(productToEditId);
//if(productToedit.isPresent()) {
//    Product productData=productToedit.get();
//    productData.setName(editProduct.getName());
//    productData.setProductCategory(editProduct.getProductCategory());
//    productData.setMeasurementcategory(editProduct.getMeasurementcategory());
//    productData.setProductDetails(editProduct.getProductDetails());
//    return productRepository.save(productData);
//}
//else {
//    return  productRepository.save(editProduct);
//}
//        model.addAttribute("title", "Edit "+productData);
//        model.addAttribute("productToEdit", productToEdit);


    @PostMapping("/{productToEditId}")
    public String editEmployeeProcessing(@PathVariable int productToEditId,
                                         Model model,
                                         @ModelAttribute @Valid Product productEdit,

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

}

