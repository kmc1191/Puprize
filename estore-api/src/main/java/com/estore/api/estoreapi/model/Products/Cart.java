package com.estore.api.estoreapi.model.Products;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Cart entity; a superclass for an individual's shopping cart
 * 
 * @author rmr9535 (add your username to this list if you happen to work on this file.)
 */
public class Cart {

    private static final Logger LOG = Logger.getLogger(Product.class.getName()); 

    public static final String STRING_FORMAT = "Cart [user=%s, cart: ";

    @JsonProperty("user") private String user; // the username associated with each specific cart
    @JsonProperty("cart") private Product[] cart; // the collection of items in the user's shopping cart

    /**
     * constructor for the shopping cart: takes in a username and creates a shopping cart object to hold
     * that user's specific product items
     * 
     * @param user the username of the user that requires a shipping cart
     */
    public Cart(@JsonProperty("user") String user) {
        this.user = user ;
        Product[] items = new Product[1] ;
        items[ 0 ] = null ;
        this.cart = items ;
    }

    /**
     * accessor method for the username associated with the cart obejct
     * 
     * @return the username associated with the cart object
     */
    public String getUser() {
        return this.user ;
    }

    /**
     * accessor method for the list of products contained in the cart
     * 
     * @return the list of products contained in the cart
     */
    public Product[] getCart() {
        return this.cart ;
    }

    /**
     * changes the user of the shopping cart to the inputted username
     * 
     * @param user the username of the new user of the cart object
     */
    public void setUser(String user) {
        this.user = user ;
    }

    /**
     * changes the cart of the shopping cart to the inputted list of product items
     * 
     * @param cart the new list of items to represent the shopping cart
     */
    public void setCart(Product[] cart) {
        this.cart = cart ;
    }

    /**
     * to-String method for testing and cURL purposes only
     */
    public String toString(){
        String result = String.format(STRING_FORMAT, user, cart);
        for( int i = 0; i < cart.length; i++ ) {
            result += cart[ i ] + " " ;
        }
        result += "]" ;
        return result ;
    }
    
}
