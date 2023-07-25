package org.liftoff.saintlouisfarms.models;
import net.bytebuddy.implementation.bind.annotation.Super;

import javax.persistence.CascadeType;
import javax.persistence.Entity;

import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
public  class User extends MainUser {

////farmer

    @Size(min = 3, max = 45, message = "Farm's name must be between 3 and 45 characters")
    @NotBlank(message = "Farm name is required")
    private String farmName;
    public String getFarmName() {
        return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private final List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private final List<ProductCategory>productCategories=new ArrayList<>(Arrays.asList(new ProductCategory("Featured",this),new ProductCategory("Vegetables",this),new ProductCategory("Fruit",this),new ProductCategory("Dry Goods",this),new ProductCategory("Featured",this)));
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private final List<MeasurementCategory>measurementCategories=new ArrayList<>(Arrays.asList(new MeasurementCategory("each", this),new MeasurementCategory("lbs", this),new MeasurementCategory("cs", this),new MeasurementCategory("pint", this),new MeasurementCategory("qt", this)));

    public List<MeasurementCategory> getMeasurementCategories() {
        return measurementCategories;
    }
    public List<Product> getProducts() {
        return products;

    }

    public List<ProductCategory> getProductCategories() {
        return productCategories;
    }

    public User() {

    }

    public User(String email, String pwHash, String firstName, String lastName, String address, String zip, String city, String phone, String farmName) {
        super(email, pwHash, firstName, lastName, address, zip, city, phone);
        this.farmName = farmName;
    }
}

