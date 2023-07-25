package org.liftoff.saintlouisfarms.controllers;

import org.liftoff.saintlouisfarms.data.ProductRepository;
import org.liftoff.saintlouisfarms.data.ShoppingBasketRepository;
import org.liftoff.saintlouisfarms.models.Client;
import org.liftoff.saintlouisfarms.models.Product;
import org.liftoff.saintlouisfarms.models.ShoppingBasket;
import org.liftoff.saintlouisfarms.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("addToCart")
public class CartController {
    @Autowired
    private AuthenticationController authenticationController;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ShoppingBasketRepository shoppingBasketRepository;



    //show the information(description ,quantity,..etc) of the product
    @GetMapping("/{farmName}/{ProductId}")
    public String displayInformationOfProduct(@PathVariable int ProductId, @PathVariable String farmName
            , Model model, HttpServletRequest request, RedirectAttributes redirectAttrs) {
        HttpSession session = request.getSession();
        Client client = authenticationController.getClientFromSession(session);

        Optional<Product> optProductToShow = productRepository.findById(ProductId);
        //check if product we want to display not in the system!
        if (optProductToShow.isEmpty()) {
            redirectAttrs.addFlashAttribute("NotFound", "ProductNotFound");
            return "redirect:availableProducts/Products";
        }

        Product productToEdit = optProductToShow.get();
        model.addAttribute("info",productToEdit);
        return"productInfo";
    }
    //add products to cart
    @PostMapping("/{farmName}/{productId}")
    public String ProcessAddingProductToBasket(Model model, HttpServletRequest request,@PathVariable String farmName
            , @PathVariable Integer productId, @RequestParam Integer quantity){
        //get client from session
        HttpSession session = request.getSession();
        Client client = authenticationController.getClientFromSession(session);

        Integer addQuantity=quantity;
        model.addAttribute("loggedIn", client != null);
        Product product=productRepository.findById(productId).get();

        ShoppingBasket shoppingBasket =shoppingBasketRepository.findByClientAndProduct(product.getId(),client.getId());


        //the product is already in the cart
        if(shoppingBasket != null){
            addQuantity=shoppingBasket.getQuantity()+quantity;
            shoppingBasket.setQuantity(addQuantity);
        }
        else{
            shoppingBasket=new ShoppingBasket();
            shoppingBasket.setQuantity(quantity);
            shoppingBasket.setClient(client);
            shoppingBasket.setLocalDateTime(LocalDateTime.now());
            shoppingBasket.setProducts(product);
        }
        shoppingBasketRepository.save(shoppingBasket);

        return "redirect:../{farmName}";
    }


}
