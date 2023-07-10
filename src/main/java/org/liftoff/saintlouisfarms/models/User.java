package org.liftoff.saintlouisfarms.models;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;

import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User extends AbstractEntity {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email. Try again.")
    @Size(min = 3, max = 45, message = "Email must be between 3 and 45 characters")
    private String email;


    @NotBlank(message = "Password is required")
    private String pwHash;

    @NotBlank(message = "First name is required")
    @Size(min = 3, max = 45, message = "First name must be between 3 and 45 characters")
    private String firstName;

    @Size(min = 3, max = 45, message = "Last name must be between 3 and 45 characters")
    @NotBlank(message = "Last name is required")
    private String lastName;

    @Size(min = 3, max = 45, message = "Address must be between 3 and 45 characters")
    @NotBlank(message = "Address is required")
    private String address;

    @Size(min = 3, max = 45, message = "Farm's name must be between 3 and 45 characters")
    @NotBlank(message = "Farm name is required")
    private String farmName;


    @NotBlank(message = "Phone number is required")
    private String phone;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private final List<Product> products = new ArrayList<>();


    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }

    public User(){
    }
    public User(String email, String pwHash, String firstName, String lastName, String address, String farmName, String phone) {
        this.email = email;
        this.pwHash = encoder.encode(pwHash);
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.farmName = farmName;
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwHash() {
        return pwHash;
    }

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

    public String getFarmName() {
        return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Product> getProducts() {
        return products;
    }
}