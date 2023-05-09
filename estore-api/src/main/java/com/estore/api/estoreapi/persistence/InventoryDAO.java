package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import java.util.ArrayList;
import com.estore.api.estoreapi.model.Products.Product;

/**
 * Defines the interface for the Inventory which tracks Product object persistence
 * 
 * @author hab1466 (add your name to this list if you happen to work on this file.)
 */
public interface InventoryDAO {
    /**
     * Retrieves the overall inventory, including all {@linkplain Product products}
     * 
     * @return An Array of {@link Product products}, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Product[] getInventory() throws IOException;

    /**
     * Finds all {@linkplain Product products} whose name contains the given text
     * 
     * @param containsText The text to match against
     * 
     * @return An array of {@link Product products} whose nemes contains the given text, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Product[] findProducts(String containsText) throws IOException;


    /**
     * Retrieves the {@linkplain Product product} with the given name
     * 
     * @param productId The id of the {@link Product product} to get
     * 
     * @return A {@link Product product} with the matching name, null if no {@link Product product} is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Product getProduct(int productId) throws IOException;

    /**
     * Creates and saves a {@linkplain Product product}
     * 
     * @param product {@linkplain Product product} object to be created and saved
     * 
     * The name of the given product object is assigned to the new product object.
     * The id of the given product object is ignored and a new unique id is assigned.
     *
     * @return new {@link Product product} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    Product createProduct(Product product) throws IOException;

    /**
     * Updates and saves a {@linkplain Product product}
     * 
     * @param product {@link Product product} object to be updated and saved
     * 
     * @return updated {@link Product product} if successful, null if {@link Product product} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Product updateProduct(Product product) throws IOException;

    /**
     * Deletes a {@linkplain Product product} with the given id
     * 
     * @param productId The id of the {@link Product product}
     * 
     * @return true if the {@link Product product} was deleted
     * <br>
     * false if product with the given name does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteProduct(int productId) throws IOException; 

    /**
     * Changes the way the inventory is sorted and displayed. By default,
     * the inventory is sorted by ID. This can be changed to be sorted by
     * the name of the products, the price of the products, or the quantity
     * 
     * @param method a String that defines which way the inventory will be 
     * sorted. This should use pre-defined global Strings
     * 
     * @return an array representing the current product display
     * 
     * @throws IOException
     */
    Product[] changeDisplay(String method) throws IOException ; 

    /**
     * Allows for the prioritization of a single product, moving it to the top of
     * the display list. Note that creating, updating or deleting a product will
     * remove this prioritization, as the inventory must be re-sorted. 
     * 
     * @param productId the ID of the product to be moved to the top of the display
     * 
     * @return an arraylist representing the current product display
     * 
     * @throws IOException 
     */
    Product[] prioritize(int productId) throws IOException ; 

}
