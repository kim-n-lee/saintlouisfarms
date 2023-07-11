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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
public class User extends AbstractEntity {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email. Try again.")
    @Size(min = 3, max = 45, message = "Email must be between 3 and 45 characters")
    private String email;


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
    @NotBlank(message = "City name is required")
    private String city;

    @NotBlank(message = "Zip code  is required")
    private String zip ;
//    String regex = "^[0-9]{5}(?:-[0-9]{4})?$";
//
//    Pattern pattern = Pattern.compile(regex);
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
       // Matcher matcher = pattern.matcher(zip);
       // if(matcher.equals(true)){
            this.zip = zip;
        //}
//        else {
//            //not Valid zip code
//            this.zip="notValid";
//        }
    }




    @NotBlank(message = "Phone number is required")
    private String phone;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private final List<Product> products = new ArrayList<>();

  @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
  private final List<ProductCategory>productCategories=new ArrayList<>();
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private final List<MeasurementCategory>measurementCategories=new ArrayList<>();

    public List<MeasurementCategory> getMeasurementCategories() {
        return measurementCategories;
    }

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }

    public User(){
    }
    public User(String email, String pwHash, String firstName, String lastName, String address, String farmName, String zip,String city,String phone) {
        this.email = email;
        this.pwHash = encoder.encode(pwHash);
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.farmName = farmName;
        this.zip=zip;
        this.city=city;
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

    public List<ProductCategory> getProductCategories() {
        return productCategories;
    }
}