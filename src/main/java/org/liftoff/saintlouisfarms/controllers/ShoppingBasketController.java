package org.liftoff.saintlouisfarms.controllers;
import org.liftoff.saintlouisfarms.data.ClientRepository;
import org.liftoff.saintlouisfarms.data.ProductRepository;
import org.liftoff.saintlouisfarms.data.ShoppingBasketRepository;
import org.liftoff.saintlouisfarms.models.BasketItem;
import org.liftoff.saintlouisfarms.models.Client;
import org.liftoff.saintlouisfarms.models.Product;
import org.liftoff.saintlouisfarms.models.ShoppingBasket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/shoppingBaskets")
public class ShoppingBasketController {

    @Autowired
    private ShoppingBasketRepository shoppingBasketRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AuthenticationController authenticationController;

    @Autowired
    ClientRepository clientRepository;

    /*
//
//
// Moved logic to displaySpecificFarmNameWithProduct
//
//
     */
//    @PostMapping("/createBasket/{clientId}")
//    public ShoppingBasket createBasket(@PathVariable int clientId,
//                                       Model model, HttpServletRequest request,
//                                       RedirectAttributes redirectAttrs) {
//
//        HttpSession session = request.getSession();
//        Client client = authenticationController.getClientFromSession(session);
//
//        model.addAttribute("loggedIn", client != null);
//
//        LocalDateTime currentTime = LocalDateTime.now();
//        ShoppingBasket shoppingBasket = new ShoppingBasket(client, LocalDateTime.now());
//
//        return shoppingBasketRepository.save(shoppingBasket);
//    }

        /*
//
//
// Moved logic to displaySpecificFarmNameWithProductFormHandel
//
//
     */

//    @PostMapping("/addToBasket/{basketId}/{productId}")
//    public ShoppingBasket addToBasket(
//            @PathVariable int basketId,
//            @PathVariable int productId,
//            @RequestParam int quantity, // Get the quantity from user input
//            Model model, HttpServletRequest request,
//            RedirectAttributes redirectAttrs) {
//
//        Optional<ShoppingBasket> basketOptional = shoppingBasketRepository.findById(basketId);
//        Optional<Product> productOptional = productRepository.findById(productId);
//
//        if (basketOptional.isEmpty() || productOptional.isEmpty()) {
//            redirectAttrs.addFlashAttribute("NotFound", "ProductOrBasketNotFound");
//            return null;
//        }
//
//        ShoppingBasket shoppingBasket = basketOptional.get();
//        Product product = productOptional.get();
//
//        // Check if the product is already in the basket
//        List<BasketItem> basketItems = shoppingBasket.getBasketItems();
//        Optional<BasketItem> existingItem = basketItems.stream()
//                .filter(item -> item.getProduct().getId() == productId)
//                .findFirst();
//
//        if (existingItem.isPresent()) {
//            // Product already exists in the basket, update its quantity
//            BasketItem itemToUpdate = existingItem.get();
//            itemToUpdate.setQuantity(itemToUpdate.getQuantity() + quantity);
//        } else {
//            // Product is not in the basket, add it as a new BasketItem
//            BasketItem newItem = new BasketItem(product, quantity);
//            basketItems.add(newItem);
//        }
//
//        BigDecimal totalAmount = calculateTotalAmount(shoppingBasket);
//        shoppingBasket.setTotalAmount(totalAmount);
//
//        return shoppingBasketRepository.save(shoppingBasket);
//    }


            /*
//
//
// Have not worked on it yet
//
//
     */


//    @PostMapping("/removeFromBasket/{basketId}/{productId}")
//    public ShoppingBasket removeFromBasket(
//            @PathVariable int basketId,
//            @PathVariable int productId,
//            Model model, HttpServletRequest request,
//            RedirectAttributes redirectAttrs) {
//
//        Optional<ShoppingBasket> basketOptional = shoppingBasketRepository.findById(basketId);
//        Optional<Product> productOptional = productRepository.findById(productId);
//
//        if (basketOptional.isEmpty() || productOptional.isEmpty()) {
//            redirectAttrs.addFlashAttribute("NotFound", "ProductOrBasketNotFound");
//            return null;
//        }
//
//        ShoppingBasket shoppingBasket = basketOptional.get();
//        Product productToRemove = productOptional.get();
//
//        shoppingBasket.removeProduct(productToRemove);
//
//        BigDecimal totalAmount = calculateTotalAmount(shoppingBasket);
//        shoppingBasket.setTotalAmount(totalAmount);
//
//        return shoppingBasketRepository.save(shoppingBasket);
//    }


            /*
//
//
// Moved logic to StoreController
//
//
     */

    // Helper handler or method to calculate the total amount for the shopping basket
//    private BigDecimal calculateTotalAmount(ShoppingBasket shoppingBasket) {
//        BigDecimal total = BigDecimal.ZERO;
//        for (BasketItem item : shoppingBasket.getBasketItems()) {
//            BigDecimal productPrice = item.getProduct().getProductDetails().getPrice();
//            total = total.add(productPrice.multiply(BigDecimal.valueOf(item.getQuantity())));
//        }
//        return total;
//    }
}

