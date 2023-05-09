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

import com.estore.api.estoreapi.model.Products.Cart;
import com.estore.api.estoreapi.model.Products.Product;
import com.estore.api.estoreapi.model.Products.Checkout;

/**
 * Implements the functionality for JSON file-based persistence for the Shopping Cart
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 * 
 * @author rmr9535 (add your username to this list if you happen to work on this file.)
 */
@Component
public class CartFileDAO implements CartDAO {
    private static final Logger LOG = Logger.getLogger(InventoryFileDAO.class.getName()); // currently unused
    Map<String, Cart> allCarts;         // Provides a local map/cache of the product objects so
                                        // that we don't have to read from the file every time
    private ObjectMapper objectMapper;  // provides conversion between Product objects and JSON text format
                                        // written to the file
    private String filename;            // the file to be read from and to write to 
    private InventoryDAO inventoryDao; 

    /**
     * creates an Cart File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectmapper procides JSON object to/from Java Object serialization
     *                     and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public CartFileDAO(@Value("${cart.file}") String filename, ObjectMapper objectmapper, InventoryDAO inventoryDao) throws IOException {
        this.filename = filename; 
        this.objectMapper = objectmapper;
        this.inventoryDao = inventoryDao;
        allCarts = new TreeMap<>();
        load(); // loads the carts from the file into cart, the local cachex
    }

    /**
     * Saves the {@linkplain Cart carts} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@linkplain Cart carts} was written successfully
     * 
     * @throws IOException when the file cannot be accessed or written to
     */
    private boolean save() throws IOException { 
        Cart[] cartArray = getCarts(); 

        // Serialization of the java objects into JSON objects. 
        // writeValue will throw an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename), cartArray);
        return true;
    }

    /**
     * Loads {@linkplain Cart carts} from the JSON file into the map
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when the file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        allCarts = new TreeMap<>();

        // Deserialization of the JSON objects from the file into an array of carts. 
        // readValue will throw an IOException if there is an issue
        // with the file or reading from the file
        Cart[] cartArray = objectMapper.readValue(new File(filename), Cart[].class);
        
        // add each cart to the inventory
        for (Cart cart : cartArray) { 
            allCarts.put(cart.getUser(), cart);
        }

        return true;
    }

     /**
     * Retrieves all {@linkplain Cart carts}
     * 
     * @return An array of {@link cart Carts}, may be empty
     */
    public Cart[] getCarts() {
        ArrayList<Cart> cartArrayList = new ArrayList<>();

        for (Cart cart : allCarts.values()) {
            cartArrayList.add( cart ) ;
        }

        if(cartArrayList.size() == 0) {
            return new Cart[0] ;
        }

        Cart[] cartArray = new Cart[cartArrayList.size()];
        cartArrayList.toArray(cartArray) ;
        return cartArray;

    }
    

    /**
     * {@inheritDoc}}
     */
    @Override
    public Cart getCart(String username) {
        
        for (Cart cart : allCarts.values()) {
            //System.out.println( cart.getUser() );
            if( cart.getUser().strip().equals( username.strip() ) ) {
                return cart ;
            }
        }

        return null ;

    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public Product[] getItems(String username) {
        
        Cart myCart = getCart(username) ;

        if( myCart == null ) {
            return null ;
        }

        return myCart.getCart() ; 

    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public Product addToCart(String username, int productId, int quantity) throws IOException {
        
        Cart myCart = getCart(username) ;
        Product[] myItems = getItems( username ) ;
        
        if( myCart == null ) {
            return null ;
        }

        Product product = inventoryDao.getProduct( productId ) ;
        Product existingProduct = null ;
        int existingIndex = -1 ;
        
        for( int i = 0; i < myItems.length; i++ ) {
            if( myItems[ i ] != null ) {
                if( myItems[i].getId() == productId ) {
                    existingProduct = myItems[i] ;
                    existingIndex = i ;
                }
            }
        }

        if( product == null ) {
            return null ;
        }
        else if( product.getQuantity() < quantity ) {
            return null ;
        }
        else if( existingProduct != null ) { 

            int totalQuantity = quantity + existingProduct.getQuantity(); 
            if( totalQuantity > product.getQuantity()) { 
                return null; 
            } 
            else{
                Product newProduct = new Product(product.getId(), product.getName(), product.getPrice(), totalQuantity); 
                myItems[ existingIndex ] = newProduct ;
                myCart.setCart(myItems);

                allCarts.put(username, myCart) ;
                
                save(); // may throw IOException 
                return newProduct;
            }
        }
        else { 
            Product newProduct = new Product(product.getId(), product.getName(), product.getPrice(), quantity); 
            ArrayList<Product> newItems = new ArrayList<>();

            for (Product item : myItems) {
                if( item != null ) {
                    newItems.add( item ) ;
                }
            }
            newItems.add( newProduct ) ;

            Product[] productArray = new Product[newItems.size()];
            newItems.toArray(productArray) ;
            myCart.setCart(productArray);

            allCarts.put(username, myCart) ;

            save(); // may throw IOException 
            return newProduct;
        }
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public Product removeFromCart(String username, int productId, int quantity) throws IOException {

        Cart myCart = getCart(username) ;
        Product[] myItems = getItems( username ) ;

        if( myCart == null ) {
            return null ;
        }

        Product inventoryProduct = inventoryDao.getProduct( productId ) ;
        Product existingProduct = null ;
        int existingIndex = -1 ;
        
        for( int i = 0; i < myItems.length; i++ ) {
            if(myItems[ i ] != null) {
                if( myItems[i].getId() == productId ) {
                    existingProduct = myItems[i] ;
                    existingIndex = i ;
                }
            }
        }

        if( existingProduct == null ) {
            return null ;
        }
        else if( existingProduct.getQuantity() < quantity ) {
            return null ;
        }
        else if( existingProduct.getQuantity() > quantity ) {
            int newQuantity = existingProduct.getQuantity() - quantity ;
            Product newProduct = new Product(existingProduct.getId(), existingProduct.getName(), existingProduct.getPrice(), newQuantity); 

            myItems[ existingIndex ] = newProduct ;
            myCart.setCart(myItems) ;

            allCarts.put(username, myCart) ;

            save(); // may throw IOException 
            return newProduct; 
        }
        else {
            Product newProduct = new Product(existingProduct.getId(), existingProduct.getName(), existingProduct.getPrice(), 0);
            
            ArrayList<Product> newItems = new ArrayList<>();

            for (Product item : myItems) {
                if( item.getId() != existingProduct.getId() ) {
                    newItems.add( item ) ;
                }
            }

            Product[] productArray = new Product[newItems.size()]; 
            newItems.toArray(productArray) ; 
            myCart.setCart(productArray); 

            allCarts.put(username, myCart) ;

            save(); // may throw IOException 
            return newProduct; 
        }
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public Product[] emptyCart(String username) throws IOException {
        
        for (Cart cart : allCarts.values()) { 
            if( cart.getUser().strip().equals(username.strip()) ) {
                Cart emptyCart = new Cart( username ) ;
                allCarts.put(username, emptyCart) ;
            }
        }

        save(); // may throw IOException
        return getItems(username); 
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public Checkout checkout(String username) throws IOException {

        Product[] myItems = getItems( username ) ;

        if( myItems.length == 1 || myItems == null ) {
            if( myItems[ 0 ] == null || myItems.length < 1 ) {
                return new Checkout( 0.0, null ) ;
            }
        }
        ArrayList<Product> invalid = new ArrayList<>();
        for( int i = 0; i < myItems.length; i++ ) {

            Product myProduct = myItems[ i ] ;
            int productId = myProduct.getId() ; 
            Product stockProduct = inventoryDao.getProduct( productId ) ;

            if( myProduct.getQuantity() <= stockProduct.getQuantity() ) {
                int newQuantity = stockProduct.getQuantity() - myProduct.getQuantity() ;
                Product resultProduct = new Product(productId, myProduct.getName(), 
                                                    myProduct.getPrice(), newQuantity) ;
                inventoryDao.updateProduct( resultProduct ) ;
            }
            else {
                invalid.add( myProduct ) ;
                myItems[ i ] = null ;
            }
            // put in code that removes the item if the above statemenet is false: right now the
            // user will have to pay for it regardless. 
        } 

        double cost = getTotalCost( username );
        Product[] returnItems = new Product[invalid.size()]; 
        invalid.toArray(returnItems) ; 
        emptyCart(username); // may throw IOException
        return new Checkout( cost, returnItems ) ;
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public double getTotalCost(String username) {
        double cost = 0.0 ;

        Product[] myItems = getItems( username ) ;

        if( myItems.length == 1 || myItems == null ) {
            if( myItems[ 0 ] == null || myItems.length < 1 ) {
                return 0 ;
            }
        }

        for (Product product : myItems) { 
            if( product != null ) {
                double itemCost = ( product.getPrice() * product.getQuantity() ) ;
                cost += itemCost ;
            }
        } 

        return cost;
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public Cart createCart(String username) throws IOException {
        
        Cart cart = new Cart( username ) ;

        if( !allCarts.keySet().contains( username ) ) {
            allCarts.put(username, cart) ;
            save() ;
            return cart ;
        }

        return null;
    }
    
}
