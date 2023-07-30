package org.liftoff.saintlouisfarms.controllers;

import org.liftoff.saintlouisfarms.data.OrderRepository;
import org.liftoff.saintlouisfarms.data.ProductRepository;
import org.liftoff.saintlouisfarms.data.ShoppingBasketRepository;
import org.liftoff.saintlouisfarms.data.UserRepository;
import org.liftoff.saintlouisfarms.models.Client;
import org.liftoff.saintlouisfarms.models.FarmOrder;
import org.liftoff.saintlouisfarms.models.ShoppingBasket;
import org.liftoff.saintlouisfarms.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("orders")
public class FarmerOrdersController {
    private AuthenticationController authenticationController;
    private ProductRepository productRepository;

    private ShoppingBasketRepository shoppingBasketRepository;
    private UserRepository userRepository;
    private OrderRepository orderRepository;

    @Autowired
    public FarmerOrdersController(AuthenticationController authenticationController, ProductRepository productRepository, ShoppingBasketRepository shoppingBasketRepository, UserRepository userRepository, OrderRepository orderRepository) {
        this.authenticationController = authenticationController;
        this.productRepository = productRepository;
        this.shoppingBasketRepository = shoppingBasketRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }



//
//@GetMapping("{farmName}")
//    public String handleOrders(@RequestParam int basketId,
//                               HttpServletRequest request,
//                               RedirectAttributes redirectAttrs,
//                               Model model){
//    HttpSession session = request.getSession(false);
//    Client client = authenticationController.getClientFromSession(session);
//
//    Optional<ShoppingBasket> basketOptional = shoppingBasketRepository.findById(basketId);
//    if (basketOptional.isEmpty()) {
//        redirectAttrs.addFlashAttribute("NotFound", "Shopping Basket Not Found");
//        return "redirect:../";
//    }
////    Check to see if there is enough stock
//
////    Create a FarmOrder item which also removes items from Farmer's inventory
//    ShoppingBasket shoppingBasket = basketOptional.get();
//
//    FarmOrder newOrder = new FarmOrder(shoppingBasket.getBasketItems().get(0).getProduct().getUser(),
//                                        client,
//                                        shoppingBasket.getBasketItems().stream().filter(item -> item.getQuantity()>0).collect(Collectors.toList()),
//                                        shoppingBasket.getTotalAmount());
//
//    orderRepository.save(newOrder);
//    model.addAttribute("basketId", basketId);
//    model.addAttribute("loggedIn", client != null);
//    model.addAttribute("newOrder", newOrder);
//    model.addAttribute("client", client);
//    return"order/confirm";
//}

    @GetMapping("{farmName}")
    public String handleOrderConfirmed(
                                @PathVariable String farmName,
                                HttpServletRequest request,
                                Model model,
                                RedirectAttributes redirectAttrs){
        HttpSession session = request.getSession(false);
        User farmer = authenticationController.getUserFromSession(session);

        Optional<List<FarmOrder>> optionalFarmOrders = orderRepository.findByFarmerAndSentTrue(farmer);
        if (optionalFarmOrders.isEmpty()) {
            redirectAttrs.addFlashAttribute("NotFound", "Order Not Found");
            return "redirect:../";
        }
//    Check to see if there is enough stock

//    Create a FarmOrder item which also removes items from Farmer's inventory
        List<FarmOrder> orders = optionalFarmOrders.get();

        System.out.println(orders.size());
        model.addAttribute("orders", orders);
        model.addAttribute("loggedIn", farmer != null);
        return "farmer/orders";
    }
}
