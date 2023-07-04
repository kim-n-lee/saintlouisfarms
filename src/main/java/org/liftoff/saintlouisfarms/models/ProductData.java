package org.liftoff.saintlouisfarms.models;


import java.util.ArrayList;
// this class for search part in product page in navbar
public class ProductData {

    //Returns the results of searching for product in product category data by field and search term.

    public static ArrayList<Product> findByColumnAndValue(String productCategory, String searchTerm, Iterable<Product> allProducts) {

        if (searchTerm.equals("")){
            return (ArrayList<Product>) allProducts;
        }
        ArrayList<Product> aValue =  findByValue(searchTerm,allProducts);

        return aValue;
    }


    // this method searching for the term in all product
    public static ArrayList<Product> findByValue(String value, Iterable<Product> allProducts) {
        String lower_val = value.toLowerCase();

        ArrayList<Product> results = new ArrayList<>();

        for (Product product : allProducts) {

            if (product.getName().toLowerCase().contains(lower_val)) {
                results.add(product);
            } else if (product.getProductDetails().getDescription().toString().toLowerCase().contains(lower_val)) {
                results.add(product);
            } else if (product.getProductDetails().getPrice().toString().toLowerCase().contains(lower_val)) {
                results.add(product);
            }

            else if (product.getProductDetails().getStatus().toString().toLowerCase().contains(lower_val)) {
                results.add(product);
            } else if (product.getMeasurementcategory().getName().toString().toLowerCase().contains(lower_val)) {
                results.add(product);
            }
            else if (product.getProductCategory().getName().toString().toLowerCase().contains(lower_val)) {
                results.add(product);
            } else if (product.toString().toLowerCase().contains(lower_val)) {
                results.add(product);
            }

        }

        return results;
    }

}