package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import java.util.ArrayList;

import com.estore.api.estoreapi.model.Products.Cart;
import com.estore.api.estoreapi.model.Products.Checkout;
import com.estore.api.estoreapi.model.Products.Product;

/**
 * Defines the interface for the Shopping Cart which stores selected prouducts for purchase
 * 
 * @author rmr9535 (add your name to this list if you happen to work on this file.)
 */
public interface CartDAO {

    /**
     * Retrieves the entire {@linkplain Cart cart} for a user, including every {@linkplain Product product} in the cart
     * 
     * @param username the name of the user whose {@link Cart cart} will be retrieved
     * @return A {@link Cart cart}, may be empty, null if the user has no cart
     * 
     * @throws IOException if an issue with underlying storage
     */
    public Cart getCart(String username); 

    /**
     * Takes a {@linkplain Product product} and the amount to be added, verifies that this is a valid amount and product to
     * <br>
     * be added, and, assuming validity was proven, adds the product to the {@linkplain Cart cart}. 
     * 
     * @param productId the ID number of the {@link Product product} to be added
     * @param quantity the amount to be added
     * @return true if the {@link Product product} was successfully added to the {@linkplain Cart cart}, false otherwise
     * 
     * @throws IOException
     */
    public Product addToCart(String username, int productId, int quantity) throws IOException;

    /**
     * Takes a {@linkplain Product product} and the amount to be removed, verifies that this is a valid amount and product to
     * <br>
     * be removed, and, assuming validity was proven, removes the product from the {@linkplain Cart cart} . 
     * 
     * @param productId the ID number of the {@link Product product} to be added
     * @param quantity the amount to be added
     * @return true if the {@linkplain Product product} was successfully removed from the {@linkplain Cart cart}, false otherwise
     * 
     * @throws IOException
     */
    public Product removeFromCart(String username, int productId, int quantity) throws IOException;

    /**
     * Removes all {@linkplain Product products}  from the {@linkplain Cart cart} 
     * 
     * @return true upon successful completion, otherwise false
     * 
     * @throws IOException
     */
    public Product[] emptyCart(String username) throws IOException; 

    /**
     * Empties the {@linkplain Cart cart}  and removes the proper amount of each item from the inventory, 
     * as it has been purchased and is no longer available to other customers
     * 
     * @return true upon successful completion, otherwise false
     * 
     * @throws IOException
     */
    public Checkout checkout(String username) throws IOException; 

    /**
     * Calculates and returns the accumulated cost of all items in the {@linkplain Cart cart} 
     * 
     * @return the total price of all items in the {@link Cart cart}
     */
    public double getTotalCost(String username);

    /**
     * Creates a new {@linkplain Cart cart} object for a user
     * 
     * @param username the name of the user that requires a shopping cart
     * @return the {@link Cart cart} object for the specified username
     * 
     * @throws IOException
     */
    public Cart createCart(String username) throws IOException;


    /**
     * Retrieves the products in a {@linkplain Cart cart} for a user
     * 
     * @param username the name of the user whose shopping cart will be retrieved
     * @return An array of {@link Product products}, may be empty, null if the user has no cart
     * 
     * @throws IOException if an issue with underlying storage
     */
    public Product[] getItems(String username);
    
}
