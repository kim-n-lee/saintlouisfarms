package org.liftoff.saintlouisfarms.controllers;
import org.liftoff.saintlouisfarms.data.MeasurementCategoryRepository;
import org.liftoff.saintlouisfarms.data.ProductRepository;
import org.liftoff.saintlouisfarms.models.DTO.MultiProductDTO;
import org.liftoff.saintlouisfarms.models.MeasurementCategory;
import org.liftoff.saintlouisfarms.models.Product;
import org.liftoff.saintlouisfarms.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("measurements")
public class MeasurmentController {
    // Corresponds to http://localhost:8080/measurements
    @Autowired
    private MeasurementCategoryRepository measurementCategoryRepository;
    @Autowired
    AuthenticationController authenticationController;
    @Autowired
    ProductRepository productRepository;


    @GetMapping("add")
    public String displayAddNewMeasurementForm(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        model.addAttribute(new MeasurementCategory());
        model.addAttribute("currentMeasurements", measurementCategoryRepository.findMeasurementById(user.getId()));
        model.addAttribute("loggedIn", session.getAttribute("user") != null);
        return "measurements/add";
    }
    @PostMapping("add")
    public String processAddNewMeasurementForm(@ModelAttribute @Valid MeasurementCategory newMeasuremenCategory,
                                               Errors errors, Model model, HttpServletRequest request, RedirectAttributes redirectAttrs) {
        HttpSession session=request.getSession();
        User user = authenticationController.getUserFromSession(session);
        if (errors.hasErrors()) {
            model.addAttribute("currentMeasurements", measurementCategoryRepository.findMeasurementById(user.getId()));
            model.addAttribute("loggedIn", session.getAttribute("user") != null);
            return "measurements/add";
        }
        redirectAttrs.addFlashAttribute("measurementAdded", newMeasuremenCategory.getName());
        newMeasuremenCategory.setUser(user);
        measurementCategoryRepository.save(newMeasuremenCategory);
        return "redirect:../farmer/add";
    }


    @PostMapping("edit/{id}")
    public String processEditMeasurementForm(@PathVariable int id,
                                             @ModelAttribute @Valid MeasurementCategory MeasurementCategory,
                                             Errors errors,
                                             RedirectAttributes redirectAttrs) {
        Optional<MeasurementCategory> optmeasurementCategory = measurementCategoryRepository.findById(id);

        if (errors.hasErrors()) {
            redirectAttrs.addFlashAttribute("errors", "Measurement name must be between 1 and 45 characters long");
            return "redirect:../add";
        }

        if (optmeasurementCategory.isEmpty()) {
            redirectAttrs.addFlashAttribute("isEmpty", "Cannot find that measurement.");
            return "redirect:../add";
        }

        MeasurementCategory measurementToEdit = optmeasurementCategory.get();
        measurementToEdit.setName(MeasurementCategory.getName());
        redirectAttrs.addFlashAttribute("edited", measurementToEdit.getName());
        measurementCategoryRepository.save(measurementToEdit);
        return "redirect:../add";
    }


    @RequestMapping(value="delete/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String deleteMeasurementCategory(@PathVariable int id,
                                            Model model,
                                            HttpServletRequest request,
                                            RedirectAttributes redirectAttrs) {
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);
        Optional<MeasurementCategory> optMeasurementCategory = measurementCategoryRepository.findById(id);

        if (optMeasurementCategory.isEmpty()) {
            redirectAttrs.addFlashAttribute("isEmpty", "Cannot find that Measurement Category.");
            return "redirect:../add";
        }

        MeasurementCategory measurementToDelete = optMeasurementCategory.get();
        List<Product> productsWithMeasurementCategory = measurementToDelete.getProducts();

        if (productsWithMeasurementCategory.isEmpty()) {
            redirectAttrs.addFlashAttribute("deleted", measurementToDelete.getName());
            measurementCategoryRepository.delete(measurementToDelete);
            return "redirect:../add";
        } else {
//            If there are products in the category they are put in MultiProductDTO to have their categories reassigned en mass
            MultiProductDTO multiProductDTO = new MultiProductDTO();
            for(Product product: productsWithMeasurementCategory){
                multiProductDTO.getProductsToReassign().add(product);
            }


            model.addAttribute(id);
            model.addAttribute("measurements", measurementCategoryRepository.findMeasurementById(user.getId()));
            model.addAttribute("multiProductDTO", multiProductDTO);
            model.addAttribute("loggedIn", session.getAttribute("user") != null);
            return "measurements/delete";
        }

    }

    @PostMapping("delete/reassign/{id}")
    public String deleteMeasurementCategoryAfterReassign(@ModelAttribute MultiProductDTO multiProductDTO,
                                                         @PathVariable int id,
                                                         Model model,
                                                         HttpServletRequest request,
                                                         RedirectAttributes redirectAttrs) {
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        Optional<MeasurementCategory> optionalMeasurementCategory = measurementCategoryRepository.findById(id);
        if (optionalMeasurementCategory.isEmpty()) {
            redirectAttrs.addFlashAttribute("isEmpty", "Cannot find that category.");
            return "redirect:../add";
        }

        MeasurementCategory measurementCategoryToDelete = optionalMeasurementCategory.get();
        List<Product> products = measurementCategoryToDelete.getProducts();

        MultiProductDTO newMultiProductDTO = new MultiProductDTO();

//        Goes through each product in MultiProductDTO and if it has been reassigned
//        it is put saved to the dB if it is not it is put into a new DTO to have user reassign
        for(int i=0; i < products.size();i++){
            Product current = products.get(i);
            MeasurementCategory toSet = multiProductDTO.getProductsToReassign().get(i).getMeasurementCategory();
            if(current.getProductCategory().equals(toSet)){
                newMultiProductDTO.getProductsToReassign().add(current);
            }
            else{
                current.setMeasurementCategory(toSet);
                productRepository.save(products.get(i));
            }
        }


        if (newMultiProductDTO.getProductsToReassign().isEmpty()) {
            return "redirect:../"+id;
        } else {
            model.addAttribute(id);
            model.addAttribute("measurements", measurementCategoryRepository.findMeasurementById(user.getId()));
            model.addAttribute("multiProductDTO", newMultiProductDTO);
            model.addAttribute("loggedIn", session.getAttribute("user") != null);
            return "measurements/delete";
        }
    }


}