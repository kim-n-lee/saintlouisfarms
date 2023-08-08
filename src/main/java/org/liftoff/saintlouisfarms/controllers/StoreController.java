package org.liftoff.saintlouisfarms.controllers;

import org.liftoff.saintlouisfarms.data.BasketItemRepository;
import org.liftoff.saintlouisfarms.data.ProductRepository;
import org.liftoff.saintlouisfarms.data.ShoppingBasketRepository;
import org.liftoff.saintlouisfarms.data.UserRepository;
import org.liftoff.saintlouisfarms.models.*;
import org.liftoff.saintlouisfarms.models.DTO.ShoppingBasketDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@Controller
@RequestMapping("store")
public class StoreController {
    private static final int PAGE_SIZE = 20;
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
                                                      //@RequestParam(defaultValue = "0") int page,
                                                      HttpServletRequest request,
                                                      @PathVariable String farmName
            ,@Param("info") String info){

        if (!userRepository.existsByFarmName(farmName)){
            return "redirect:../";
        }

        ShoppingBasket shoppingBasket;
        HttpSession session = request.getSession(false);
        // if a client (not a guest)
        if(session != null){
//          Should see if they have an active shopping basket
            Client client= authenticationController.getClientFromSession(session);
            model.addAttribute("loggedIn", client != null);
            //check if the this is the first time for the client and he doesnt have a cart

            if(shoppingBasketRepository.findAboutClientCart(client.getId(),farmName) == null){
                shoppingBasket = new ShoppingBasket(client, LocalDateTime.now());

            }
            else{
                shoppingBasket=shoppingBasketRepository.findAboutClientCart(client.getId(),farmName);

            }
            ShoppingBasketDTO shoppingBasketDTO = new ShoppingBasketDTO();
            shoppingBasketRepository.save(shoppingBasket);
            model.addAttribute("fa", farmName);
            if(info!=null){

                List<Product> product=productRepository.searchByFarm(info, farmName);

                for(int i=0;i<product.size();i++){
                    if(basketItemRepository.findBasketForProduct(product.get(i).getId(),client.getId())==null){
                        BasketItem basketItem=  new BasketItem(product.get(i), 0, shoppingBasket);
                        shoppingBasketDTO.addBasketItem(basketItem);
                    }
                    else{
                        BasketItem basketItem=basketItemRepository.findBasketForProduct(product.get(i).getId(),client.getId());
                        shoppingBasketDTO.addBasketItem(basketItem);
                    }

                }

                basketItemRepository.saveAll(shoppingBasketDTO.getBasketItemsAvailable());


                if (shoppingBasket.getBasketItems().isEmpty()) {
                    shoppingBasket.setBasketItems(shoppingBasketDTO.getBasketItemsAvailable());
                    shoppingBasketRepository.save(shoppingBasket);
                }
                basketItemRepository.saveAll(shoppingBasket.getBasketItems());
//           model.addAttribute("currentShoppingBasketItems", shoppingBasketRepository.getAllshopingCart());
                List<BasketItem> shopTest=basketItemRepository.findTheCart(client.getId(),farmName);
                model.addAttribute("currentShoppingBasketItems", shopTest);
                //model.addAttribute("currentShoppingBasketItems", shoppingBasket.getBasketItems().stream().filter(item -> item.getQuantity() > 0).collect(Collectors.toList()));

                model.addAttribute("currentShoppingBasket", shoppingBasket);
                model.addAttribute("shoppingBasket",shoppingBasketDTO);


            }
            /////here
            else {

//                Pageable pageable = PageRequest.of(page, PAGE_SIZE);
//                Page<Product> productsPage = productRepository.findByNameOfFarmName(farmName, pageable);
//                List<Product> products = productsPage.getContent();

                List<Product> product=productRepository.findByNameOfFarmNames(farmName);

                for(int i=0;i<product.size();i++){
                    if(basketItemRepository.findBasketForProduct(product.get(i).getId(),client.getId())==null){
                        BasketItem basketItem=  new BasketItem(product.get(i), 0, shoppingBasket);
                        shoppingBasketDTO.addBasketItem(basketItem);
                    }
                    else{
                        BasketItem basketItem=basketItemRepository.findBasketForProduct(product.get(i).getId(),client.getId());
                        shoppingBasketDTO.addBasketItem(basketItem);
                    }

                }
                basketItemRepository.saveAll(shoppingBasketDTO.getBasketItemsAvailable());


                if (shoppingBasket.getBasketItems().isEmpty()) {
                    shoppingBasket.setBasketItems(shoppingBasketDTO.getBasketItemsAvailable());
                    shoppingBasketRepository.save(shoppingBasket);
                }
                basketItemRepository.saveAll(shoppingBasket.getBasketItems());
                List<BasketItem> shopTest=basketItemRepository.findTheCart(client.getId(),farmName);
                model.addAttribute("currentShoppingBasketItems", shopTest);
                //model.addAttribute("currentShoppingBasketItems", shoppingBasket.getBasketItems().stream().filter(item -> item.getQuantity() > 0).collect(Collectors.toList()));

                model.addAttribute("currentShoppingBasket", shoppingBasket);

//                model.addAttribute("page", productsPage);
//                String baseUrl = "/store/" + farmName;
//                model.addAttribute("baseUrl", baseUrl);

                model.addAttribute("shoppingBasket", shoppingBasketDTO);
            }
            model.addAttribute("title", farmName+" Store");
        }
////// if a guest // I think if the guest can add to the cart so, it needs a work to make a special cart for everyone
        else
        {
            shoppingBasket = new ShoppingBasket();
            ShoppingBasketDTO shoppingBasketDTO = new ShoppingBasketDTO();

            shoppingBasketRepository.save(shoppingBasket);
            model.addAttribute("fa", farmName);
            if(info!=null){

                //productRepository.searchByFarm(info,farmName).forEach(product -> shoppingBasketDTO.addBasketItem(new BasketItem(product, 0, shoppingBasket)));

                List<Product> product=productRepository.searchByFarm(info, farmName);

                for(int i=0;i<product.size();i++){
                    if(basketItemRepository.findBasketForProductguest(product.get(i).getId())==null){
                        BasketItem basketItem=  new BasketItem(product.get(i), 0, shoppingBasket);
                        shoppingBasketDTO.addBasketItem(basketItem);
                    }
                    else{
                        BasketItem basketItem=basketItemRepository.findBasketForProductguest(product.get(i).getId());
                        shoppingBasketDTO.addBasketItem(basketItem);
                    }

                }
                // productRepository.searchByFarm(info,farmName).forEach(product -> shoppingBasketDTO.addBasketItem(new BasketItem(product,0, shoppingBasket)));
                basketItemRepository.saveAll(shoppingBasketDTO.getBasketItemsAvailable());


                if (shoppingBasket.getBasketItems().isEmpty()) {
                    shoppingBasket.setBasketItems(shoppingBasketDTO.getBasketItemsAvailable());
                    shoppingBasketRepository.save(shoppingBasket);
                }
                basketItemRepository.saveAll(shoppingBasket.getBasketItems());
//           model.addAttribute("currentShoppingBasketItems", shoppingBasketRepository.getAllshopingCart());

                model.addAttribute("currentShoppingBasketItems", shoppingBasket.getBasketItems().stream().filter(item -> item.getQuantity() > 0).collect(Collectors.toList()));
                model.addAttribute("currentShoppingBasket", shoppingBasket);
                model.addAttribute("shoppingBasket",shoppingBasketDTO);


            }
            /////here
            else {
//                Pageable pageable = PageRequest.of(page, PAGE_SIZE);
//                Page<Product> productsPage = productRepository.findByNameOfFarmName(farmName, pageable);
//                List<Product> products = productsPage.getContent();

                List<Product> product=productRepository.findByNameOfFarmNames(farmName);

                for(int i=0;i<product.size();i++){
                    if(basketItemRepository.findBasketForProductguest(product.get(i).getId())==null){
                        BasketItem basketItem=  new BasketItem(product.get(i), 0, shoppingBasket);
                        shoppingBasketDTO.addBasketItem(basketItem);
                    }
                    else{
                        BasketItem basketItem=basketItemRepository.findBasketForProductguest(product.get(i).getId());
                        shoppingBasketDTO.addBasketItem(basketItem);
                    }

                }

                //productRepository.findByNameOfFarmName(farmName).forEach(product -> shoppingBasketDTO.addBasketItem(new BasketItem(product, 0, shoppingBasket)));


                //productRepository.findByNameOfFarmName(farmName).forEach(product -> shoppingBasketDTO.addBasketItem(new BasketItem(product, 0, shoppingBasket)));
                basketItemRepository.saveAll(shoppingBasketDTO.getBasketItemsAvailable());


                if (shoppingBasket.getBasketItems().isEmpty()) {
                    shoppingBasket.setBasketItems(shoppingBasketDTO.getBasketItemsAvailable());
                    shoppingBasketRepository.save(shoppingBasket);
                }
                basketItemRepository.saveAll(shoppingBasket.getBasketItems());
                model.addAttribute("currentShoppingBasketItems", shoppingBasket.getBasketItems().stream().filter(item -> item.getQuantity() > 0).collect(Collectors.toList()));
                model.addAttribute("currentShoppingBasket", shoppingBasket);

//                model.addAttribute("page", productsPage);
//                String baseUrl = "/store/" + farmName;
//                model.addAttribute("baseUrl", baseUrl);

                model.addAttribute("shoppingBasket", shoppingBasketDTO);
            }
            model.addAttribute("title", farmName+" Store");


        }

        return "store/clientStore";
    }

    @PostMapping("/{farmName}")
    public  String displaySpecificFarmNameWithProductFormHandel(Model model,
                                                                RedirectAttributes redirectAttrs,
                                                                //@RequestParam(defaultValue = "0") int page,
                                                                HttpServletRequest request,
                                                                @PathVariable String farmName,
                                                                @RequestParam int basketId,
//                                                                @RequestParam int productName,
                                                                @ModelAttribute ShoppingBasketDTO shoppingBasketDTO
            ,@Param("info") String info){
//System.out.println(productName);
//        Make sure farm exists
        if (!userRepository.existsByFarmName(farmName)){
            return "redirect:../";
        }

        HttpSession session = request.getSession();

//        Handling if user is not logged in, or is not client
        if(!authenticationController.clientInSession(session))
//        ShoppingBasket should be saved until they login, after which client is set as the person that logged in
        {return "redirect:../login";}

        Client client = authenticationController.getClientFromSession(session);


//        Retrieves the current ShoppingBasket attached to the client
        Optional<ShoppingBasket> basketOptional = shoppingBasketRepository.findById(basketId);
        //.findByIdAndClient(basketId,client.getId(),productName);

        System.out.println("hello ggg"+basketId);
        if (basketOptional.isEmpty()) {
            redirectAttrs.addFlashAttribute("NotFound", "Shopping Basket Not Found");
            return "redirect:../";
        }

        ShoppingBasket currentShoppingBasket = basketOptional.get();

        List<String> insufficientQuantity = new ArrayList<>();

        //Looks to see if there is enough stock of an item before it can be added to the cart
        for (int i = 0; i < shoppingBasketDTO.getBasketItemsAvailable().size(); i++) {
            int requestedQuantity = shoppingBasketDTO.getBasketItemsAvailable().get(i).getQuantity();

            BasketItem currentBasketItem = currentShoppingBasket.getBasketItems().get(i);
            int availableQuantityFromFarmer = currentBasketItem.getProduct().getProductDetails().getQuantity();


            if (requestedQuantity > availableQuantityFromFarmer ) {
                String errorMessage = "This quantity is not available for " + currentBasketItem.getProduct().getName();
                insufficientQuantity.add(errorMessage);
            } else {
                //if(currentBasketItem.getProduct().getName().equals(productName)) {
                currentBasketItem.setQuantity(requestedQuantity);

                //}
                currentShoppingBasket.getBasketItems().get(i).setQuantity(requestedQuantity);
                shoppingBasketDTO.updateBasketItemQuantity(i, requestedQuantity);


            }
        }

//        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
//        Page<Product> productsPage = productRepository.findByNameOfFarmName(farmName, pageable);
//        List<Product> products = productsPage.getContent();
        model.addAttribute("fa", farmName);
        BigDecimal totalAmount = calculateTotalAmount (currentShoppingBasket);
        currentShoppingBasket.setTotalAmount(totalAmount);

        basketItemRepository.saveAll(currentShoppingBasket.getBasketItems());
        shoppingBasketRepository.save(currentShoppingBasket);


        model.addAttribute("loggedIn", client != null);
//        List<BasketItem> shopTest=basketItemRepository.findTheCart(client.getId(),farmName);
//        model.addAttribute("currentShoppingBasketItems", shopTest);

        model.addAttribute("currentShoppingBasketItems", currentShoppingBasket.getBasketItems().stream().filter(item -> item.getQuantity()>0).collect(Collectors.toList()));

        model.addAttribute("insufficientQuantity", insufficientQuantity);
        model.addAttribute("currentShoppingBasket", currentShoppingBasket);
        model.addAttribute("shoppingBasket", shoppingBasketDTO);
        model.addAttribute("title", farmName+" Store");
//        model.addAttribute("page", productsPage);
//        String baseUrl = "/store/" + farmName;
//        model.addAttribute("baseUrl", baseUrl);
        return "store/clientStore";
    }

    //  Have you thought about doing this as a db query
    private BigDecimal calculateTotalAmount(ShoppingBasket shoppingBasket) {
        BigDecimal total = BigDecimal.ZERO;
        //List<BasketItem> shopTest=basketItemRepository.findTheCart(client.getId(),farmName);
        for (BasketItem item : shoppingBasket.getBasketItems()) {
            BigDecimal productPrice = item.getProduct().getProductDetails().getPrice();
            total = total.add(productPrice.multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        return total;
    }

}

