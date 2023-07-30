package org.liftoff.saintlouisfarms.controllers;

import org.imgscalr.Scalr;
import org.liftoff.saintlouisfarms.data.BasketItemRepository;
import org.liftoff.saintlouisfarms.data.ProductRepository;
import org.liftoff.saintlouisfarms.data.ShoppingBasketRepository;
import org.liftoff.saintlouisfarms.data.UserRepository;
import org.liftoff.saintlouisfarms.models.*;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("store")
public class StoreController {

    private AuthenticationController authenticationController;
    private ProductRepository productRepository;
    private ShoppingBasketRepository shoppingBasketRepository;
    private UserRepository userRepository;
    private BasketItemRepository basketItemRepository;
@Autowired
    public StoreController(AuthenticationController authenticationController, ProductRepository productRepository, ShoppingBasketRepository shoppingBasketRepository, UserRepository userRepository, BasketItemRepository basketItemRepository) {
        this.authenticationController = authenticationController;
        this.productRepository = productRepository;
        this.shoppingBasketRepository = shoppingBasketRepository;
        this.userRepository = userRepository;
        this.basketItemRepository = basketItemRepository;
    }



    /*
    //
    //
    // I actually think we should put this on the home page instead
    //
    //
     */
    @GetMapping("")
    public String displayAllAvailableProductsWithFarmsName(Model model,
                                                           HttpServletRequest request){
        //get client from session
        HttpSession session = request.getSession();
        Client client = authenticationController.getClientFromSession(session);

        //display all products order by farmName
//          I displayed all farms with inks to their pages instead
        model.addAttribute("user", userRepository.findAll());
        model.addAttribute("loggedIn", client != null);

        //return to available product order by farm name for all farmer
        return "farms";

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
       HttpSession session = request.getSession(false);
       if(session != null){
//          Should see if they have an active shopping basket
           Client client = authenticationController.getClientFromSession(session);
           model.addAttribute("loggedIn", client != null);
           shoppingBasket = new ShoppingBasket(client, LocalDateTime.now());
       }else{
           shoppingBasket = new ShoppingBasket();
       }

       productRepository.findByNameOfFarmName(farmName).forEach(product -> shoppingBasket.addProductsToBuy(new BasketItem(product,0)));

       basketItemRepository.saveAll(shoppingBasket.getBasketItemsAvailable());
       shoppingBasketRepository.save(shoppingBasket);


       model.addAttribute("shoppingBasket",shoppingBasket);
       model.addAttribute("basketId", shoppingBasket.getId());
       model.addAttribute("title", farmName+" Store");
       return "store/clientStore";
   }

    @PostMapping("/{farmName}")
    public  String displaySpecificFarmNameWithProductFormHandel(Model model,
                                                                RedirectAttributes redirectAttrs,
                                                                HttpServletRequest request,
                                                                @PathVariable String farmName,
                                                                @RequestParam int basketId,
                                                                @ModelAttribute ShoppingBasket shoppingBasket){

//        Make sure farm exists
        if (!userRepository.existsByFarmName(farmName)){
            return "redirect:../";
        }

        HttpSession session = request.getSession(false);

//        Handling if user is not logged in
        if(session == null)
//        ShoppingBasket should be saved until they login, after which client is set as the person that logged in
         {return "redirect:../login";}

        Client client = authenticationController.getClientFromSession(session);

//        Gets values that have been set on ShoppingBasket passed in
        List<BasketItem> basketItems = shoppingBasket.getBasketItemsAvailable();
        List<BasketItem> addedItems = basketItems.stream().filter(item -> item.getQuantity()>0).collect(Collectors.toList());

//        Retrives the current ShoppingBasket attached to the client
        Optional<ShoppingBasket> basketOptional = shoppingBasketRepository.findById(basketId);
        if (basketOptional.isEmpty()) {
            redirectAttrs.addFlashAttribute("NotFound", "Shopping Basket Not Found");
            return "redirect:../";
        }

        ShoppingBasket currentShoppingBasket = basketOptional.get();
        System.out.println(currentShoppingBasket.getBasketItems().size());

//        Need to see if there is enough stock of an item before it can be added to the cart
//        Clears current Basket and adds products to the user's basket
        currentShoppingBasket.getBasketItems().removeAll(currentShoppingBasket.getBasketItems());
        addedItems.forEach(currentShoppingBasket::addProduct);


        BigDecimal totalAmount = calculateTotalAmount (currentShoppingBasket);
        currentShoppingBasket.setTotalAmount(totalAmount);

        basketItemRepository.saveAll(currentShoppingBasket.getBasketItems());
        shoppingBasketRepository.save(currentShoppingBasket);


        model.addAttribute("loggedIn", client != null);
        model.addAttribute("shoppingBasket", currentShoppingBasket);
        model.addAttribute("basketId", basketId);
        model.addAttribute("title", farmName+" Store");
        return "store/clientStore";
    }

    private BigDecimal calculateTotalAmount(ShoppingBasket shoppingBasket) {
        BigDecimal total = BigDecimal.ZERO;
        for (BasketItem item : shoppingBasket.getBasketItems()) {
            BigDecimal productPrice = item.getProduct().getProductDetails().getPrice();
            total = total.add(productPrice.multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        return total;
    }

}

