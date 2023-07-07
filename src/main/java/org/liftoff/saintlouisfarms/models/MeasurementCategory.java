package org.liftoff.saintlouisfarms.models;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
@Entity
public class MeasurementCategory extends AbstractEntity{

    @NotBlank
    @Size(min = 3, max = 45, message="name must be between 3 and 45 character")
    private String name;


    public MeasurementCategory(String name) {
        this.name = name;
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