package org.liftoff.saintlouisfarms.models;


import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product extends AbstractEntity {



    @Size(min = 3, max = 45, message = "Name must be between 3 and 45 characters.")
    private String name;
    @NotNull(message="Category is required.")
    @ManyToOne(cascade = CascadeType.PERSIST)
    private ProductCategory productCategory;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "product")
    private final List<BasketItem> basketItems = new ArrayList<>();

private boolean isDeleted;
//    @ManyToOne
//    ShoppingBasket shoppingBasket;
    @OneToOne(cascade = CascadeType.ALL)
    @Valid
    @NotNull
    private ProductDetails productDetails;


    @ManyToOne(cascade = CascadeType.PERSIST)
    @NotNull(message = "Measurement is required.")
    private MeasurementCategory measurementCategory;

    public void setProductDetails(ProductDetails productDetails) {
        this.productDetails = productDetails;
    }

    @ManyToOne()
    private User user;


    public Product(@Size(min = 3, max = 45, message = "Name must be between 3 and 45 characters.") String name, ProductCategory productCategory, ProductDetails productDetails, MeasurementCategory measurementCategory, User user) {
        this.name = name;

        this.measurementCategory = measurementCategory;
        this.productCategory = productCategory;
        this.productDetails=productDetails;
//        this.shoppingBasket=shoppingBasket;
        this.user = user;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Product(@Size(min = 3, max = 45, message = "Name must be between 3 and 45 characters.") String name, ProductCategory productCategory, ProductDetails productDetails, MeasurementCategory measurementCategory, User user, Boolean isDeleted) {
        this.name = name;

        this.measurementCategory = measurementCategory;
        this.productCategory = productCategory;
        this.productDetails=productDetails;
//        this.shoppingBasket=shoppingBasket;
        this.user = user;
        this.isDeleted=isDeleted;
    }
    public Product() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }


    public ProductDetails getProductDetails() {
        return productDetails;
    }



    public MeasurementCategory getMeasurementCategory() {
        return measurementCategory;
    }
    public void setMeasurementCategory(MeasurementCategory measurementCategory) {
        this.measurementCategory = measurementCategory;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @Override
    public String toString() {
        return name;
    }

}