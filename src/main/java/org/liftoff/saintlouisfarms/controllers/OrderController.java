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
import java.util.*;
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
    private OrderItemRepository orderItemRepository;

    @Autowired
    public OrderController(AuthenticationController authenticationController, ProductRepository productRepository, ShoppingBasketRepository shoppingBasketRepository, UserRepository userRepository, OrderRepository orderRepository, BasketItemRepository basketItemRepository,OrderItemRepository orderItemRepository) {
        this.authenticationController = authenticationController;
        this.productRepository = productRepository;
        this.shoppingBasketRepository = shoppingBasketRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.basketItemRepository =basketItemRepository;
        this.orderItemRepository = orderItemRepository;
    }




@PostMapping("{basketId}")
    public String handleOrders(@RequestParam int basketId,
                               HttpServletRequest request,
                               RedirectAttributes redirectAttrs,
                               Model model){
    HttpSession session = request.getSession();
//        Handling if user is not logged in, or is not client
    if(!authenticationController.clientInSession(session)){return "redirect:../login";}

    Client client = authenticationController.getClientFromSession(session);

    Optional<ShoppingBasket> basketOptional = shoppingBasketRepository.findById(basketId);
    if (basketOptional.isEmpty()) {
        redirectAttrs.addFlashAttribute("NotFound", "Shopping Basket Not Found");
        return "redirect:../";
    }

    ShoppingBasket shoppingBasket = basketOptional.get();

//    Check to see if there is enough stock
    List<BasketItem> basketItemsOnOrder = shoppingBasket.getBasketItems().stream().filter(item -> item.getQuantity()>0).collect(Collectors.toList());
    List<Product> productsToUpdate = new ArrayList<>();

    for (BasketItem basketItem : basketItemsOnOrder) {
        int ProductQuantityOnOrder = basketItem.getQuantity();
        Optional<Product> optionalProduct = productRepository.findById(basketItem.getProduct().getId());
        Product product = optionalProduct.get();
        int quantityFarmer = product.getProductDetails().getQuantity();
        if (quantityFarmer >= ProductQuantityOnOrder) {
            //    Create a FarmOrder item which also removes items from Farmer's inventory
            product.getProductDetails().setQuantity(product.getProductDetails().getQuantity() - ProductQuantityOnOrder);
            productsToUpdate.add(product);
        } else {
            redirectAttrs.addFlashAttribute("orderFail", "Some items are no longer in stock please update your order!");
            //should change the quantity
            return "redirect:../store";
        }
    }

//    Create a FarmOrder items
    FarmOrder newOrder = new FarmOrder(shoppingBasket.getBasketItems().get(0).getProduct().getUser(),
            client,
            shoppingBasket.getTotalAmount());

//  Adds Items to Order
    basketItemsOnOrder.forEach(newOrder::addOrderItems);
    orderRepository.save(newOrder);
    orderItemRepository.saveAll(newOrder.getOrderItems());


//  Removes products from inventory
    productRepository.saveAll(productsToUpdate);


//    If order is not confirmed it is deleted after 10 minutes.
    new java.util.Timer().schedule(
            new java.util.TimerTask() {
                @Override
                public void run() {
                    Optional<FarmOrder> optionalFarmOrder = orderRepository.findById(newOrder.getId());
                    if(optionalFarmOrder.isPresent()) {
                        FarmOrder farmOrder = optionalFarmOrder.get();
                        if (!farmOrder.getSent()) {

                            List<OrderItem> orderItemsToReturn = farmOrder.getOrderItems();
                            List<Product> productsToReturn = new ArrayList<>();

//                          Puts items back in inventory
                            for (OrderItem orderItem : orderItemsToReturn) {
                                int productQuantityOnOrder = orderItem.getQuantity();
                                Optional<Product> optionalProduct = productRepository.findById(orderItem.getProduct().getId());
                                Product product = optionalProduct.get();
                                int quantityFarmer = product.getProductDetails().getQuantity();
                                product.getProductDetails().setQuantity(quantityFarmer + productQuantityOnOrder);
                                productsToReturn.add(product);
                            }
                            productRepository.saveAll(productsToReturn);
                            orderItemRepository.deleteAll(farmOrder.getOrderItems());
                            orderRepository.delete(farmOrder);
                        }
                    }
                }
            },
            600000
    );


    model.addAttribute("basketId", basketId);
    model.addAttribute("loggedIn", client != null);
    model.addAttribute("newOrder", newOrder);
    model.addAttribute("client", client);
    return"order/confirm";
}

    @PostMapping("confirmed")
    public String handleOrderConfirmed(@RequestParam int basketId,
                                       @RequestParam int orderId,
                                       RedirectAttributes redirectAttrs) {


        Optional<ShoppingBasket> basketOptional = shoppingBasketRepository.findById(basketId);
        if (basketOptional.isEmpty()) {
            redirectAttrs.addFlashAttribute("NotFound", "Shopping Basket Not Found");
            return "redirect:../";
        }
        ShoppingBasket shoppingBasket = basketOptional.get();

        Optional<FarmOrder> optionalFarmOrder = orderRepository.findById(orderId);
        if (optionalFarmOrder.isEmpty()) {
            redirectAttrs.addFlashAttribute("NotFound", "Order Not Found");
            return "redirect:../";
        }

        FarmOrder order = optionalFarmOrder.get();
        order.setSent(true);
        basketItemRepository.deleteAll(shoppingBasket.getBasketItems());
        orderRepository.save(order);
        shoppingBasketRepository.deleteById(basketId);


        redirectAttrs.addFlashAttribute("orderSuccess", "Order Successfully Placed!");
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
