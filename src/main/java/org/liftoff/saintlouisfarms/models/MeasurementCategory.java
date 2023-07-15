package org.liftoff.saintlouisfarms.models;


import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
@Entity
public class MeasurementCategory extends AbstractEntity{

    @NotBlank
    @Size(min = 1, max = 45, message="name must be between 1 and 45 character")
    private String name;
    @ManyToOne()
    private User user;

    public MeasurementCategory(String name,User user) {

        this.name = name;
        this.user=user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MeasurementCategory() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    @Override
    public String toString() {
        return name;
    }

}