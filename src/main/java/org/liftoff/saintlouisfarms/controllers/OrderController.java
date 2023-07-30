package org.liftoff.saintlouisfarms.controllers;

import org.liftoff.saintlouisfarms.data.*;
import org.liftoff.saintlouisfarms.models.Client;
import org.liftoff.saintlouisfarms.models.FarmOrder;
import org.liftoff.saintlouisfarms.models.ShoppingBasket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    HttpSession session = request.getSession(false);
    Client client = authenticationController.getClientFromSession(session);

    Optional<ShoppingBasket> basketOptional = shoppingBasketRepository.findById(basketId);
    if (basketOptional.isEmpty()) {
        redirectAttrs.addFlashAttribute("NotFound", "Shopping Basket Not Found");
        return "redirect:../";
    }
//    Check to see if there is enough stock

//    Create a FarmOrder item which also removes items from Farmer's inventory
    ShoppingBasket shoppingBasket = basketOptional.get();

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
                               RedirectAttributes redirectAttrs){
        HttpSession session = request.getSession(false);
        Client client = authenticationController.getClientFromSession(session);

        Optional<FarmOrder> optionalFarmOrder = orderRepository.findById(orderId);
        if (optionalFarmOrder.isEmpty()) {
            redirectAttrs.addFlashAttribute("NotFound", "Order Not Found");
            return "redirect:../";
        }
//    Check to see if there is enough stock

//    Create a FarmOrder item which also removes items from Farmer's inventory
        FarmOrder order = optionalFarmOrder.get();
        order.setSent(true);
        orderRepository.save(order);

        redirectAttrs.addFlashAttribute("orderSuccess", "Order Successfully Placed!");
        return "redirect:../store";
    }
}
