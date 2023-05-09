package com.estore.api.estoreapi.model.Products;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Checkout entity; holds a list of items that are no longer in stock as well
 * as the cost of the items that were in stock
 * 
 * @author rmr9535 (add your username to this list if you happen to work on this file.)
 */
public class Checkout {

    private static final Logger LOG = Logger.getLogger(Product.class.getName());

    // Hunter: Unit Testing for the testToString function requires this to be public, not sure why
    public static final String STRING_FORMAT = "Checkout [cost=%f, products: ";

    @JsonProperty("cost") private Double cost; 
    @JsonProperty("invalidItems") private Product[] invalidItems;

    /**
     * Constructor for the checkout class: Initializes a checkout object with the given cost and "Out of Stock" items
     * @param cost The total cost of the checkout order
     * @param invalidItems "Out of Stock" items that cannot be purchased by the customer
     */
    public Checkout(@JsonProperty("cost") Double cost, @JsonProperty("invalidItems") Product[] invalidItems) {
        this.cost = cost ;
        this.invalidItems = invalidItems ;
    }

    /**
     * Accessor method for the cost of a checkout object
     * @return The cost of a checkout object
     */
    public double getCost() {
        return this.cost ;
    }

    /**
     * Accessor method for the list of "Out of Stock" items of a checkout object
     * @return An array of "Out of Stock" Product objects
     */
    public Product[] getInvalidItems() {
        if( this.invalidItems == null ) {
            return null ;
        }
        else if( this.invalidItems.length == 0 ) {
            return null ;
        }
        else {
            return this.invalidItems ;
        }
    }
    
}
