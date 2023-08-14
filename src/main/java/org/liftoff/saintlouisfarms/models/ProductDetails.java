package org.liftoff.saintlouisfarms.models;

import org.springframework.validation.Errors;

import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Entity;


@Entity
public class ProductDetails  extends AbstractEntity{

    @Size(max = 500, message = "Description too long!")
    private String description;

//    Need integer amount field that will paired with MeasurementCategory to show how much we have: 1 dozen

    @NotNull(message="Price is required.")
    private BigDecimal price;
    @NotNull(message="Quantity is required.")
    private Integer quantity=null;
    //    I think it would be better to not require the status so that a farmer can create a product that is not yet avilable
    @NotNull(message="Status is required.")
    private Boolean status;


    private String picture;

    public Integer getQuantity() {
        return quantity;
    }

//    public void setQuantity(int quantity) {
//        this.quantity = quantity;
//        if (quantity < 5) {
//            this.alertMessage = "Quantity is less than 5";
//        }
//    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        if( (quantity<=0) ){
            //  Errors errors = null;
            // errors.rejectValue("status","status","status should be false");
            this.status=false;

        }

        else {
            this.status = status;}

    }

    public String getPicture() {
        return picture;
    }
//    private String alertMessage;

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public ProductDetails() {
    }

    public ProductDetails(String description, BigDecimal price, Boolean status,Integer quantity) {
        this.description = description;
        this.price = price;
        this.status = status;
        this.quantity=quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    public void checkAmount(Integer quantity, boolean stat){
        if( (quantity<=0)){
            stat=false;

        }

    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}