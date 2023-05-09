package com.estore.api.estoreapi.model.Products;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Product entity; a superclass for individual cosmetic products.
 * 
 * @author hab1466 (add your username to this list if you happen to work on this file.)
 */
public class Product {

    private static final Logger LOG = Logger.getLogger(Product.class.getName());

    // Hunter: Unit Testing for the testToString function requires this to be public, not sure why
    public static final String STRING_FORMAT = "Product [id=%d, name=%s, price=%f, quantity=%d";

    @JsonProperty("id") private int id;
    /** Hunter:
    /* I suggest we use the "id" similar to how it's used in the Heroes API
    /* In doing so, we may not have to create a subclass for each unique product (Shampoo, Conditioner, etc.)
    /* We could also delete this attribute and try to use "name" as the id but that might be more difficult
     */
    @JsonProperty("name") private String name; 
    @JsonProperty("price") private double price;
    @JsonProperty("quantity") private int quantity;

    /**
     * Create a product with the given name, price, and quantity
     * 
     * @param id       The id of the product
     * @param name     The name of the product
     * @param price    The initial price of the product
     * @param quantity The initial quantity IN STOCK for this product
     */
    public Product(@JsonProperty("id") int id, @JsonProperty("name") String name
    , @JsonProperty("price") double price, @JsonProperty("quantity") int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    /**
     * Retrieves the id of the product
     * 
     * @return The id of the product
     */
    public int getId() {return id;}

    /**
     * Retrieves the name of the product
     * 
     * @return The name of the product
     */
    public String getName() {return name;}

    /**
     * Sets the name of the product
     * 
     * @param name Desired name of the product
     */
    public void setName(String name){this.name = name;}

    /**
     * Retrieves the price of the product
     * @return The price of the product
     */
    public double getPrice() {return price;}

    /**
     * Sets the price of the product
     * This method will be used in the implementation of updateProduct() in the InventoryController class
     * @param price The updated price of the product
     */
    public void setPrice(double price) {this.price = price;}

    /**
     * Retrieves the quantity in stock for the product
     * @return The quantity in stock for the product
     */
    public int getQuantity() {return quantity;}

    /**
     * Sets the quantity of the product
     * This method will be used in the implementation of updateProduct() in the InventoryController class
     * @param quantity The updated quantity in stock for the product
     */
    public void setQuantity(int quantity) {this.quantity = quantity;}

    /**
     * toString method for cURL purposes
     */
    public String toString(){
        return String.format(STRING_FORMAT, id, name, price, quantity);
    }
}