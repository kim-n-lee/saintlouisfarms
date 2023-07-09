package org.liftoff.saintlouisfarms.models;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import javax.persistence.OneToOne;
import javax.persistence.Entity;


@Entity
public class ProductDetails  extends AbstractEntity{

    @Size(max = 500, message = "Description too long!")
    private String description;

//    Need integer amount field that will paired with MeasurementCategory to show how much we have: 1 dozen

    @NotNull(message="price is required")
    private BigDecimal price;

//    I think it would be better to not require the status so that a farmer can create a product that is not yet avilable
    @NotNull(message="status is required")
    private Boolean status;

    private Byte picture;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Byte getPicture() {
        return picture;
    }

    public void setPicture(Byte picture) {
        this.picture = picture;
    }

    public ProductDetails() {
    }

    public ProductDetails(String description, BigDecimal price, Boolean status,Byte picture ) {
        this.description = description;
        this.price = price;
        this.status = status;
        this.picture = picture;
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


}