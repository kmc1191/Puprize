package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component; 

import com.estore.api.estoreapi.model.Products.Product;

/**
 * Implements the functionality for JSON file-based persistence for Products
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 * 
 * @author rmr9535 phc6515 (add your username to this list if you happen to work on this file.)
 */
@Component
public class InventoryFileDAO implements InventoryDAO {
    private static final Logger LOG = Logger.getLogger(InventoryFileDAO.class.getName()); // currently unused
    Map<Integer, Product> inventory;    // Provides a local map/cache of the product objects so
                                        // that we don't have to read from the file every time
    private ObjectMapper objectMapper;  // provides conversion between Product objects and JSON text format
                                        // written to the file
    private static int nextId;          // the next id that will be given to a newly created hero
    private String filename;            // the file to be read from and to write to

    public static final String BY_PRICE = "PRICE" ;
    public static final String BY_NAME = "NAME" ;
    public static final String BY_QUANTITY = "QUANTITY" ;
    public static final String BY_ID = "ID" ;

    private static String currentDisplay ; 

    /**
     * creates an Inventory File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectmapper procides JSON object to/from Java Object serialization
     *                     and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public InventoryFileDAO(@Value("${products.file}") String filename, ObjectMapper objectmapper) throws IOException {
        this.filename = filename; 
        this.objectMapper = objectmapper;
        currentDisplay = BY_ID ;
        load(); // loads the product from the file into inventory, the local cache
    }

    /**
     * Generates the next id for a new {@linkplain Product product}
     * 
     * @return The next ID
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Saves the {@linkplain Product products} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@linkplain Product products} were written successfully
     * 
     * @throws IOException when the file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Product[] productArray = getInventory();

        // Serialization of the java objects into JSON objects. 
        // writeValue will throw an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename), productArray);
        return true;
    }

    /**
     * Loads {@linkplain Product products} from the JSON file into the map
     * <br>
     * Also sets nextId to be equal to one more than the largest idea found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when the file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        inventory = new TreeMap<>();
        nextId = 0;

        // Deserialization of the JSON objects from the file into an array of products. 
        // readValue will throw an IOException if there is an issue
        // with the file or reading from the file
        Product[] productArray = objectMapper.readValue(new File(filename), Product[].class);
        
        // add each product to the inventory and keep track of the greatest id
        for (Product product : productArray) {
            inventory.put(product.getId(), product);
            if (product.getId() > nextId) {
                nextId = product.getId();
            }
        }
        // make the next id one greater than the maximum id seen in the file
        ++nextId;
        return true;
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public Product[] getInventory(){ 
        // with null as an argument, findProducts will return
        // a list of all the products in the inventory
        return findProducts(null);
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public Product[] findProducts(String containsText){ 
        ArrayList<Product> productArrayList = new ArrayList<>();

        for (Product product : inventory.values()) {
            String productName = product.getName();
            //Change name of product to all lower case
            productName = productName.toLowerCase();

            // if the text is null, then the returned array should contain all
            // products that are in the inventory. 
            // if the text is not null, then a product should be added to the list
            // to be returned only if the name contains the desired text
            if (containsText == null || productName.contains(containsText.toLowerCase())) {
                productArrayList.add(product);
            }
        }

        if(productArrayList.size() == 0) {
            return new Product[0] ;
        }
        Product[] productArray = new Product[productArrayList.size()];
        productArrayList.toArray(productArray) ;
        return productArray;
    }
    /**
     * {@inheritDoc}}
     */
    @Override
    public Product getProduct(int productId){
        synchronized(inventory) {
            // checks to see if the inventory contains the desired product, by id
            if (inventory.containsKey(productId)) {
                return inventory.get(productId);
            }
            else {
                // if the inventory does not contain the product, then getProduct fails:
                // null is returned instead of a Product object
                return null;
            }
        } 
    }  

    /**
     * {@inheritDoc}}
     */
    @Override
    public Product createProduct(Product product) throws IOException{ 
        synchronized(inventory) {
            // Create a new Product because the ID field cannot be changed, but we need to get the next unique
            // ID from our static counter. Then, map the new product with its ID. 
            Product newProduct = new Product(nextId(), product.getName(), product.getPrice(), product.getQuantity());
            inventory.put(newProduct.getId(), newProduct); 
            save(); // may throw IOException
            return newProduct; 
        }
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public Product updateProduct(Product product) throws IOException{ 
        synchronized(inventory) {
            if(inventory.containsKey(product.getId()) == false){
                return null; // product does not exist and therefore cannot be updated
            }
            inventory.put(product.getId(), product);
            save(); // may throw an IOException
            return product; 
        } 
    } 

    /**
     * {@inheritDoc}}
     */
    @Override
    public boolean deleteProduct(int productId) throws IOException{ 
        synchronized(inventory) {
            if(inventory.containsKey(productId)) {
                // if the product exists in the inventory, remove it
                // and save the modified inventory
                inventory.remove(productId);
                // if save is successful, this will return true. Otherwise,
                // it will return false
                return save();
            }
            else{
                // if the product did not exist, deleteProduct did not perform
                // a deletion: reutrn false
                return false; 
            }
        } 
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public Product[] changeDisplay(String method) throws IOException {
        synchronized(inventory) { 
            if( method.equals( BY_PRICE ) ) { 
                this.inventory = PriceSort.sortByPrice(inventory) ; 
                currentDisplay = BY_PRICE ;
            } 
            else if( method.equals( BY_ID ) ) { 

                Map<Integer, Product> sortedID = new TreeMap<Integer, Product>(); 
                sortedID.putAll( inventory ); 
                inventory = sortedID ; 
                currentDisplay = BY_ID ;

            } 
            else if( method.equals( BY_NAME ) ) { 
                this.inventory = NameSort.sortByName(inventory) ; 
                currentDisplay = BY_NAME ;
            }
            else if( method.equals( BY_QUANTITY ) ) { 
                this.inventory = QuantitySort.sortByQuantity(inventory) ;
                currentDisplay = BY_QUANTITY ;
            }
            
            save() ; // can throw an IOException
            return getInventory() ;
        }
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public Product[] prioritize(int productId) throws IOException {

        Product[] productArray = getInventory();
        ArrayList<Product> productArrayList = new ArrayList<>();
        for( int i = 0; i < productArray.length; i++) {
            productArrayList.add(productArray[i]);
        }

        // find the index of the product to be prioritized. When found, move 
        // it to the front of the list
        boolean found = false ;
        for( int i = 0; i < productArrayList.size(); i++ ) {
            
            if( productArrayList.get( i ).getId() == productId ) {
                Product temporary = productArrayList.remove( i ) ;
                productArrayList.add( 0, temporary ) ;
                found = true ;
            }

        }

        // if the ID does not exist, return a null list
        if( !found ) {
            return null ;
        }

        Product[] newProductArray = new Product[productArrayList.size()];
        productArrayList.toArray(newProductArray) ;

        // Serialization of the java objects into JSON objects. 
        // writeValue will throw an IOException if there is an issue
        // with the file or reading from the file

        // NOTE: this implementation is copied from save(). save() cannot
        // be called here because it would undo the prioritization
        objectMapper.writeValue(new File(filename), newProductArray);
        return newProductArray ; 
    }

}
