package org.liftoff.saintlouisfarms.controllers;

import org.liftoff.saintlouisfarms.data.*;
import org.liftoff.saintlouisfarms.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("order")
public class OrderController {
    private AuthenticationController authenticationController;
    private ProductRepository productRepository;
    private ShoppingBasketRepository shoppingBasketRepository;
    private UserRepository userRepository;
    private OrderRepository orderRepository;
    private BasketItemRepository basketItemRepository;

    @Autowired
    public OrderController(AuthenticationController authenticationController, ProductRepository productRepository, ShoppingBasketRepository shoppingBasketRepository, UserRepository userRepository, OrderRepository orderRepository, BasketItemRepository basketItemRepository) {
        this.authenticationController = authenticationController;
        this.productRepository = productRepository;
        this.shoppingBasketRepository = shoppingBasketRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.basketItemRepository =basketItemRepository;
    }




@PostMapping("{basketId}")
    public String handleOrders(@RequestParam int basketId,
                               HttpServletRequest request,
                               RedirectAttributes redirectAttrs,
                               Model model){
    HttpSession session = request.getSession();
    //        Handling if user is not logged in, or is not client
    if(!authenticationController.clientInSession(session))
    {return "redirect:../login";}
    Client client = authenticationController.getClientFromSession(session);

    Optional<ShoppingBasket> basketOptional = shoppingBasketRepository.findById(basketId);
    if (basketOptional.isEmpty()) {
        redirectAttrs.addFlashAttribute("NotFound", "Shopping Basket Not Found");
        return "redirect:../";
    }
//    Check to see if there is enough stock

    ShoppingBasket shoppingBasket = basketOptional.get();

    //    Create a FarmOrder items which should also removes items from Farmer's inventory
    FarmOrder newOrder = new FarmOrder(shoppingBasket.getBasketItems().get(0).getProduct().getUser(),
                                        client,
                                        shoppingBasket.getBasketItems().stream().filter(item -> item.getQuantity()>0).collect(Collectors.toList()),
                                        shoppingBasket.getTotalAmount());
    basketItemRepository.saveAll(newOrder.getOrderItems());
    orderRepository.save(newOrder);

    newOrder.getOrderItems().stream().forEach(item-> item.setFarmOrderItem(newOrder));
    basketItemRepository.saveAll(newOrder.getOrderItems());

    model.addAttribute("basketId", basketId);
    model.addAttribute("loggedIn", client != null);
    model.addAttribute("newOrder", newOrder);
    model.addAttribute("client", client);
    return"order/confirm";
}

    @PostMapping("confirmed")
    public String handleOrderConfirmed(@RequestParam int basketId,
                                       @RequestParam int orderId,
                               HttpServletRequest request,
                               RedirectAttributes redirectAttrs) {
        HttpSession session = request.getSession(false);
        Client client = authenticationController.getClientFromSession(session);

        Optional<FarmOrder> optionalFarmOrder = orderRepository.findById(orderId);
        if (optionalFarmOrder.isEmpty()) {
            redirectAttrs.addFlashAttribute("NotFound", "Order Not Found");
            return "redirect:../";
        }
//    Check to see if there is enough stock
        List<BasketItem> allBasketINOrder = basketItemRepository.findAllBasketAsoociatedWithOrder(orderId, client.getId());

        for (int i = 0; i < allBasketINOrder.size(); i++) {
            int ProductQuantityOnOrder = allBasketINOrder.get(i).getQuantity();
            Optional<Product> product = productRepository.findById(allBasketINOrder.get(i).getProduct().getId());
            int quantityinFarmer = product.get().getId();
            if (quantityinFarmer > ProductQuantityOnOrder) {
                //    Create a FarmOrder item which also removes items from Farmer's inventory
                FarmOrder order = optionalFarmOrder.get();
                order.setSent(true);
                orderRepository.save(order);

                redirectAttrs.addFlashAttribute("orderSuccess", "Order Successfully Placed!");
                product.get().getProductDetails().setQuantity(product.get().getProductDetails().getQuantity() - ProductQuantityOnOrder);


            } else {
                //should change the quantity
                return "redirect:../store";

            }
        }


        return "redirect:../store";
    }

    @GetMapping("all")
    public String allOrders(
            HttpServletRequest request,
            Model model,
            RedirectAttributes redirectAttrs) {
        HttpSession session = request.getSession(false);
        Client client = authenticationController.getClientFromSession(session);

        Optional<List<FarmOrder>> optionalFarmOrders = orderRepository.findByClientAndSentTrue(client);
        if (optionalFarmOrders.isEmpty()) {
            redirectAttrs.addFlashAttribute("NotFound", "Order Not Found");
            return "redirect:../store";
        }

        List<FarmOrder> orders = optionalFarmOrders.get();

        model.addAttribute("title", "Past Orders");
        model.addAttribute("orders", orders);
        model.addAttribute("loggedIn", client != null);
        return "order/allOrders";
    }

    @GetMapping("details/{orderId}")
    public String orderDetails(
            @PathVariable int orderId,
            HttpServletRequest request,
            Model model,
            RedirectAttributes redirectAttrs) {
        HttpSession session = request.getSession();
        Client client = authenticationController.getClientFromSession(session);

        Optional<FarmOrder> optionalFarmOrder = orderRepository.findById(orderId);
        if (optionalFarmOrder.isEmpty()) {
            redirectAttrs.addFlashAttribute("NotFound", "Order Not Found");
            return "redirect:../store";
        }

        FarmOrder order = optionalFarmOrder.get();

        if (order.getClient() != client) {
            redirectAttrs.addFlashAttribute("NotFound", "Order Not Found");
            return "redirect:../store";
        }

        model.addAttribute("title", "Order #"+orderId+" Details");
        model.addAttribute("order", order);
        model.addAttribute("loggedIn", client != null);
        return "order/order";
    }
}
