package org.liftoff.saintlouisfarms.controllers;

import org.liftoff.saintlouisfarms.data.BasketItemRepository;
import org.liftoff.saintlouisfarms.data.ProductRepository;
import org.liftoff.saintlouisfarms.data.ShoppingBasketRepository;
import org.liftoff.saintlouisfarms.data.UserRepository;
import org.liftoff.saintlouisfarms.models.*;
import org.liftoff.saintlouisfarms.models.DTO.ShoppingBasketDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
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
                                                     @PathVariable String farmName
   ,@Param("info") String info){

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
       ShoppingBasketDTO shoppingBasketDTO = new ShoppingBasketDTO();

       shoppingBasketRepository.save(shoppingBasket);
       /////here
       productRepository.findByNameOfFarmName(farmName).forEach(product -> shoppingBasketDTO.addBasketItem(new BasketItem(product,0, shoppingBasket)));
       basketItemRepository.saveAll(shoppingBasketDTO.getBasketItemsAvailable());


       if(shoppingBasket.getBasketItems().isEmpty()){
           shoppingBasket.setBasketItems(shoppingBasketDTO.getBasketItemsAvailable());
           shoppingBasketRepository.save(shoppingBasket);}
       basketItemRepository.saveAll(shoppingBasket.getBasketItems());
       model.addAttribute("currentShoppingBasketItems", shoppingBasket.getBasketItems().stream().filter(item -> item.getQuantity()>0).collect(Collectors.toList()));
       model.addAttribute("currentShoppingBasket", shoppingBasket);
       model.addAttribute("fa",farmName);
       if(info!=null){
               List<ShoppingBasket> searchResult=shoppingBasketRepository.searchByFarm(info,farmName);
////////////// I tried to send the list of product in querybut the problems from the ShoopingBasketDTO
               model.addAttribute("shoppingBasket",searchResult);

       }

       else
       model.addAttribute("shoppingBasket",shoppingBasketDTO);
       model.addAttribute("title", farmName+" Store");
       return "store/clientStore";
   }

    @PostMapping("/{farmName}")
    public  String displaySpecificFarmNameWithProductFormHandel(Model model,
                                                                RedirectAttributes redirectAttrs,
                                                                HttpServletRequest request,
                                                                @PathVariable String farmName,
                                                                @RequestParam int basketId,
                                                                @ModelAttribute ShoppingBasketDTO shoppingBasketDTO){

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


//        Retrieves the current ShoppingBasket attached to the client
        Optional<ShoppingBasket> basketOptional = shoppingBasketRepository.findById(basketId);
        System.out.println("hello"+basketOptional.get().toString());
        if (basketOptional.isEmpty()) {
            redirectAttrs.addFlashAttribute("NotFound", "Shopping Basket Not Found");
            return "redirect:../";
        }

        ShoppingBasket currentShoppingBasket = basketOptional.get();

//        Need to see if there is enough stock of an item before it can be added to the cart

//        Sets quantity of cart to what is in DTO
        for(Integer i =0; i<shoppingBasketDTO.getBasketItemsAvailable().size();i++){
            currentShoppingBasket.getBasketItems().get(i).setQuantity(shoppingBasketDTO.getBasketItemsAvailable().get(i).getQuantity());
        }


        BigDecimal totalAmount = calculateTotalAmount (currentShoppingBasket);
        currentShoppingBasket.setTotalAmount(totalAmount);

        basketItemRepository.saveAll(currentShoppingBasket.getBasketItems());
        shoppingBasketRepository.save(currentShoppingBasket);


        model.addAttribute("loggedIn", client != null);
        model.addAttribute("currentShoppingBasketItems", currentShoppingBasket.getBasketItems().stream().filter(item -> item.getQuantity()>0).collect(Collectors.toList()));
//        basketItemRepository.findShoopingBasket(cl)
        //model.addAttribute("currentShoppingBasketItems", basketItemRepository.findShoopingBasket(client.getId(),farmName));


        model.addAttribute("currentShoppingBasket", currentShoppingBasket);
        model.addAttribute("shoppingBasket", shoppingBasketDTO);
        model.addAttribute("title", farmName+" Store");
        return "store/clientStore";
    }
//  Have you thought about doing this as a db query
    private BigDecimal calculateTotalAmount(ShoppingBasket shoppingBasket) {
        BigDecimal total = BigDecimal.ZERO;
        for (BasketItem item : shoppingBasket.getBasketItems()) {
            BigDecimal productPrice = item.getProduct().getProductDetails().getPrice();
            total = total.add(productPrice.multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        return total;
    }

}

