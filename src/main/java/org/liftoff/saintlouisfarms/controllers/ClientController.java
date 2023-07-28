package org.liftoff.saintlouisfarms.controllers;

import org.imgscalr.Scalr;
import org.liftoff.saintlouisfarms.data.ProductRepository;
import org.liftoff.saintlouisfarms.data.ShoppingBasketRepository;
import org.liftoff.saintlouisfarms.data.UserRepository;
import org.liftoff.saintlouisfarms.models.*;
import org.liftoff.saintlouisfarms.models.DTO.ShoppingBasketDTO;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("store")
public class ClientController {

    @Autowired
    private AuthenticationController authenticationController;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ShoppingBasketRepository shoppingBasketRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public String displayAllAvailableProductsWithFarmsName(Model model, HttpServletRequest request){

        //get client from session
        HttpSession session = request.getSession();
        Client client = authenticationController.getClientFromSession(session);

        //display all products order by farmName
        model.addAttribute("products", productRepository.findAllProduct());
        model.addAttribute("loggedIn", client != null);

        //return to available product order by farm name for all farmer
        return "availableFarms";

    }
    //display the product associated with farmName
   @GetMapping("/{farmName}")
   public  String displaySpecificFarmNameWithProduct(Model model,
                                                     HttpServletRequest request,
                                                     @PathVariable String farmName){

       if (!userRepository.existsByFarmName(farmName)){
           return "redirect:../";
       }

       ShoppingBasket shoppingBasket;
       if(request.isUserInRole("client")) {
           HttpSession session = request.getSession();
           Client client = authenticationController.getClientFromSession(session);
           model.addAttribute("loggedIn", client != null);
           LocalDateTime currentTime = LocalDateTime.now();
           shoppingBasket = new ShoppingBasket(client, LocalDateTime.now());
           shoppingBasketRepository.save(shoppingBasket);
       }else{
           shoppingBasket = new ShoppingBasket();
       }

       productRepository.findByNameOfFarmName(farmName).forEach(product -> shoppingBasket.addProductsToBuy(new BasketItem(product,0)));

       model.addAttribute("shoppingBasket", shoppingBasket.getBasketItemsAvailable());
       return "store/clientStore";
   }








}

