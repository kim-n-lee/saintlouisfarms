package org.liftoff.saintlouisfarms.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Entity(name = "FarmOrder")
public class FarmOrder extends AbstractEntity {

    @ManyToOne()
    private User farmer;
    @ManyToOne()
    private Client client;
    @OneToMany(mappedBy = "farmOrderItem")
    private List<BasketItem> orderItems = new ArrayList<>();
    private BigDecimal totalAmount = BigDecimal.valueOf(0);
    private LocalDateTime localDateTime = LocalDateTime.now();
    private Boolean sent = false;
    private Boolean confirmed = false;
    private Boolean fulfilled = false;

    public FarmOrder() {
    }

    public User getFarmer() {
        return farmer;
    }

    public void setFarmer(User farmer) {
        this.farmer = farmer;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public FarmOrder(User farmer, Client client, List<BasketItem> orderItems, BigDecimal totalAmount) {
        this.farmer = farmer;
        this.client = client;
        this.orderItems = orderItems;
        this.totalAmount = totalAmount;
    }

    public List<BasketItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<BasketItem> orderItems) {
        this.orderItems = orderItems;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Boolean getFulfilled() {
        return fulfilled;
    }

    public void setFulfilled(Boolean fulfilled) {
        this.confirmed = true;
        this.fulfilled = fulfilled;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Boolean getSent() {
        return sent;
    }

    public void setSent(Boolean sent) {
        this.sent = sent;
    }
}
