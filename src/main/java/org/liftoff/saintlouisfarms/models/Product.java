package org.liftoff.saintlouisfarms.models;


import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@SQLDelete(sql = "UPDATE Product SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Product extends AbstractEntity {

    @NotBlank
    @Size(min = 3, max = 45, message = "name must be between 3 and 45 character")
    private String name;

    private boolean deleted = Boolean.FALSE;
    @ManyToOne
    private ProductCategory productCategory;

    @OneToOne(cascade = CascadeType.ALL)
    @Valid
    @NotNull
    private ProductDetails productDetails;


    @OneToOne(cascade = CascadeType.ALL)
    @Valid
    @NotNull
    private MeasurementCategory measurementcategory;

    public void setProductDetails(ProductDetails productDetails) {
        this.productDetails = productDetails;
    }

    @ManyToOne()
    private User user;


    public Product(@Size(min = 3, max = 45, message = "name must be between 3 and 45 character") String name, ProductCategory productCategory, ProductDetails productDetails, MeasurementCategory measurementcategory, User user) {
        this.name = name;
        this.measurementcategory = measurementcategory;
        this.productCategory = productCategory;
        this.productDetails=productDetails;
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



    public MeasurementCategory getMeasurementcategory() {
        return measurementcategory;
    }
    public void setMeasurementcategory(MeasurementCategory measurementcategory) {
        this.measurementcategory = measurementcategory;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}