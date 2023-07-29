package org.liftoff.saintlouisfarms.controllers;

import org.imgscalr.Scalr;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("store")
public class StoreController {

    @Autowired
    private AuthenticationController authenticationController;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ShoppingBasketRepository shoppingBasketRepository;
    @Autowired
    private UserRepository userRepository;


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
       shoppingBasketRepository.save(shoppingBasket);

       model.addAttribute("currentShoppingBasket", shoppingBasket);
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

        if (!userRepository.existsByFarmName(farmName)){
            return "redirect:../";
        }
        HttpSession session = request.getSession(false);

        if(session == null)
//        ShoppingBasket should be saved until they login, after which client is set as the person that logged in
         {return "redirect:../login";}

        Client client = authenticationController.getClientFromSession(session);

        List<BasketItem> basketItems = shoppingBasket.getBasketItemsAvailable();
        List<BasketItem> addedItems = basketItems.stream().filter(item -> item.getQuantity()>0).collect(Collectors.toList());

        Optional<ShoppingBasket> basketOptional = shoppingBasketRepository.findById(basketId);
        if (basketOptional.isEmpty()) {
            redirectAttrs.addFlashAttribute("NotFound", "ProductOrBasketNotFound");
            return "redirect:../";
        }

        ShoppingBasket currentShoppingBasket = basketOptional.get();
        addedItems.forEach(currentShoppingBasket::addProduct);

        BigDecimal totalAmount = calculateTotalAmount (currentShoppingBasket);
        currentShoppingBasket.setTotalAmount(totalAmount);
        shoppingBasketRepository.save(currentShoppingBasket);

        model.addAttribute("loggedIn", client != null);
        model.addAttribute("currentShoppingBasket", currentShoppingBasket);
        model.addAttribute("shoppingBasket", shoppingBasket);
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

