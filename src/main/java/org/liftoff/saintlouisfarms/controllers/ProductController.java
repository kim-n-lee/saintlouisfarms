package org.liftoff.saintlouisfarms.controllers;


import org.imgscalr.Scalr;
import org.liftoff.saintlouisfarms.data.*;
import org.liftoff.saintlouisfarms.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("farmer")
public class ProductController {
    private static final int PAGE_SIZE = 10;

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


    //display result of searching
    // this method search for specific products

@RequestMapping(value = "/search",method = {RequestMethod.GET,RequestMethod.POST})
public String searchProduct(@Param("info") String info
        , HttpServletRequest request ,Model model){
    HttpSession session = request.getSession();
    User user = authenticationController.getUserFromSession(session);
    model.addAttribute("title","Search");
    model.addAttribute("loggedIn", user != null);

    List<Product> searchResult=productRepository.searchByInfo(info,user.getId());
    System.out.println(searchResult);
    if(info!=null)
    model.addAttribute("searchResult",searchResult);
    else
        model.addAttribute("searchResult",productRepository.findProductById(user.getId()));
    return "farmer/search";
}
//    @GetMapping("products")

    //display all available products
//    @RequestMapping(value = "/products",method = {RequestMethod.GET,RequestMethod.POST})
//    public String displayAllProducts(@RequestParam(defaultValue = "0") int page,Model model, HttpServletRequest request,@Param("info") String info) {
//        HttpSession session = request.getSession();
//        User user = authenticationController.getUserFromSession(session);
//
//
//        if(info!=null){
//            List<Product> searchResult=productRepository.searchOnAvailableProducts(info,user.getId());
//
//            model.addAttribute("products",searchResult);}
//        else
//        model.addAttribute("products", productRepository.findProductByStatus(user.getId()));
//
//        model.addAttribute("loggedIn", user != null);
//        return "farmer/products";
//    }
    @RequestMapping(value = "/products", method = {RequestMethod.GET, RequestMethod.POST})
    public String displayAllProducts(@RequestParam(defaultValue = "0") int page, Model model, HttpServletRequest request, @Param("info") String info) {
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        Pageable pageable = PageRequest.of(page, PAGE_SIZE);

        if(info!=null){
          List<Product> searchResult=productRepository.searchOnAvailableProducts(info,user.getId());

            model.addAttribute("products",searchResult);}
        else {
            Page<Product> products = productRepository.findProductByStatus(user.getId(), pageable);
            model.addAttribute("products", products);
        }

        model.addAttribute("loggedIn", user != null);
        return "farmer/products";
    }

    // add new product
    @GetMapping("add")
    public String displayAddProductForm(Model model, HttpServletRequest request,@Param("info") String info) {
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        model.addAttribute("title", "Add Product");
        model.addAttribute("productType", productCategoryRepository.findProductsTypetById(user.getId()));
        model.addAttribute("measurements", measurementCategoryRepository.findMeasurementById(user.getId()));
        model.addAttribute(new Product());
        if(info!=null){
            List<Product> searchResult=productRepository.searchByInfo(info,user.getId());

            model.addAttribute("products",searchResult);
        }
        else
            model.addAttribute("products", productRepository.findProductById(user.getId()));

        model.addAttribute("loggedIn", user != null);
        return "farmer/add";

    }

    @PostMapping("add")
    public String processAddProductForm(@ModelAttribute @Valid Product newProduct,
                                        Errors errors, Model model, HttpServletRequest request, @RequestParam(required = false) MultipartFile picture, RedirectAttributes redirectAttrs) throws IOException
    {
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Product");
            model.addAttribute("productType", productCategoryRepository.findAll());
            model.addAttribute("measurements", measurementCategoryRepository.findAll());
            model.addAttribute("products", productRepository.findProductById(user.getId()));
            model.addAttribute("loggedIn", user != null);
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
                model.addAttribute("loggedIn", user != null);
                return "farmer/add";
            }
        }

        redirectAttrs.addFlashAttribute("productAdded", newProduct.getName());

        productRepository.save(newProduct);
//        Should also set userid to user logged in
        model.addAttribute("product", productRepository.findAll());
        model.addAttribute("loggedIn", user != null);
        return "redirect:add";
    }
    @RequestMapping(value = "{productToDeleteId}",method = {RequestMethod.POST, RequestMethod.GET})
    public String deleteProductProcessing(@PathVariable int productToDeleteId,
                                          Model model,
                                          HttpServletRequest request ) {
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        Optional<Product> optProductToDelete = productRepository.findById(productToDeleteId);
        if (optProductToDelete.isEmpty()) {
            model.addAttribute("title", "Current Products");
            model.addAttribute("currentProducts", productRepository.findProductById(user.getId()));
            model.addAttribute("loggedIn", user != null);
            return "farmer/add";
        }
        Product productToDelete = optProductToDelete.get();
        productRepository.delete(productToDelete);
        return "redirect:add";
    }





}