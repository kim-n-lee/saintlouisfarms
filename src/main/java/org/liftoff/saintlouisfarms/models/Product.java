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


    @NotBlank
    @Size(min = 3, max = 45, message = "name must be between 3 and 45 character")
    private String name;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private ProductCategory productCategory;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "products")
    private final List<ShoppingBasket> shoppingBaskets = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @Valid
    @NotNull
    private ProductDetails productDetails;


    @ManyToOne(cascade = CascadeType.PERSIST)
    @Valid
    @NotNull
    private MeasurementCategory measurementCategory;

    public void setProductDetails(ProductDetails productDetails) {
        this.productDetails = productDetails;
    }

    @ManyToOne()
    private User user;


    public Product(@Size(min = 3, max = 45, message = "name must be between 3 and 45 character") String name, ProductCategory productCategory, ProductDetails productDetails, MeasurementCategory measurementCategory, User user) {
        this.name = name;

        this.measurementCategory = measurementCategory;
        this.productCategory = productCategory;
        this.productDetails=productDetails;
//        this.shoppingBasket=shoppingBasket;
        this.user = user;
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

    public List<ShoppingBasket> getShoppingBaskets() {
        return shoppingBaskets;
    }

    @Override
    public String toString() {
        return name;
    }
}