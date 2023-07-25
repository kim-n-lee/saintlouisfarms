package org.liftoff.saintlouisfarms.models.DTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RegisterFormClientDTO extends LoginFormDTO{
    @NotBlank(message = "First name is required")
    @Size(min = 3, max = 45, message = "First name must be between 3 and 45 characters")
    private String firstName;

    @Size(min = 3, max = 45, message = "Last name must be between 3 and 45 characters")
    @NotBlank(message = "Last name is required")
    private String lastName;

    @Size(min = 3, max = 45, message = "Address must be between 3 and 45 characters")
    @NotBlank(message = "Address is required")
    private String address;
    @NotBlank(message = "City name is required")
    private String city;

    @NotBlank(message = "Zip code  is required")
    private String zip ;
    @NotBlank(message = "Phone number is required")
    private String phone;

    @NotBlank(message = " verifyPassword is required")
    private String verifyPassword;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }

}

