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
import java.util.Objects;
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

    @GetMapping("{farmName}/{orderKind}")
    public String currentOrders(
            @PathVariable String farmName,
            @PathVariable String orderKind,
            HttpServletRequest request,
            Model model,
            RedirectAttributes redirectAttrs) {
        HttpSession session = request.getSession(false);
        User farmer = authenticationController.getUserFromSession(session);

        Optional<List<FarmOrder>> optionalFarmOrders;
        List<FarmOrder> orders;
        String title;

        switch (orderKind){
            case "Fulfilled":
                title = "Fulfilled Orders";
                optionalFarmOrders = orderRepository.findByFarmerAndSentTrueAndFulfilledTrueAndConfirmedTrue(farmer);
                if (optionalFarmOrders.isEmpty()) {
                    redirectAttrs.addFlashAttribute("NotFound", "Order Not Found");
                    return "redirect:..farmer/dashboard";
                }
                orders = optionalFarmOrders.get();
                break;
            case "Open":
                title = "Open Orders";
                optionalFarmOrders = orderRepository.findByFarmerAndSentTrueAndFulfilledFalse(farmer);
                if (optionalFarmOrders.isEmpty()) {
                    redirectAttrs.addFlashAttribute("NotFound", "Order Not Found");
                    return "redirect:..farmer/dashboard";
                }
                orders = optionalFarmOrders.get();
                model.addAttribute("openActive", "openActive");
                break;
            case "Confirmed":
                optionalFarmOrders = orderRepository.findByFarmerAndSentTrueAndConfirmedTrueAndFulfilledFalse(farmer);
                if (optionalFarmOrders.isEmpty()) {
                    redirectAttrs.addFlashAttribute("NotFound", "Order Not Found");
                    return "redirect:..farmer/dashboard";
                }
                orders = optionalFarmOrders.get();
                title ="Confirmed Orders";
                break;
            default:
                title ="All Orders";
                optionalFarmOrders = orderRepository.findByFarmerAndSentTrue(farmer);
                if (optionalFarmOrders.isEmpty()) {
                    redirectAttrs.addFlashAttribute("NotFound", "Order Not Found");
                    return "redirect:..farmer/dashboard";
                }
                orders = optionalFarmOrders.get();

        }

        model.addAttribute("orders", orders);
        model.addAttribute("title", title);
        model.addAttribute("farmName", farmName);
        model.addAttribute("loggedIn", farmer != null);
        return "farmer/orders";
    }

    @PostMapping("confirm")
    public String confirmOrder(@RequestParam int id,
                               @RequestParam String farmName,
                               HttpServletRequest request,
                               RedirectAttributes redirectAttrs) {
        HttpSession session = request.getSession(false);
        User farmer = authenticationController.getUserFromSession(session);

        Optional<FarmOrder> optionalFarmOrder = orderRepository.findById(id);
        if (optionalFarmOrder.isEmpty()) {
            redirectAttrs.addFlashAttribute("NotFound", "Order Not Found");
            return "redirect:..farmer/dashboard";
        }

        FarmOrder order = optionalFarmOrder.get();
        order.setConfirmed(true);
        orderRepository.save(order);
        redirectAttrs.addFlashAttribute("orderInfo", "Order #"+order.getId()+" Confirmed");
        return "redirect:" + farmName + "/Open";
    }

    @PostMapping("fulfilled")
    public String fulfillOrder(@RequestParam int id,
                               @RequestParam String farmName,
                               HttpServletRequest request,
                               RedirectAttributes redirectAttrs) {
        HttpSession session = request.getSession(false);
        User farmer = authenticationController.getUserFromSession(session);

        Optional<FarmOrder> optionalFarmOrder = orderRepository.findById(id);
        if (optionalFarmOrder.isEmpty()) {
            redirectAttrs.addFlashAttribute("NotFound", "Order Not Found");
            return "redirect:..farmer/dashboard";
        }

        FarmOrder order = optionalFarmOrder.get();
        order.setFulfilled(!order.getFulfilled());
        orderRepository.save(order);
        redirectAttrs.addFlashAttribute("orderInfo", "Order #"+order.getId()+" status changed");
        return "redirect:" + farmName + "/Open";
    }

}
