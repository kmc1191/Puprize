package com.estore.api.estoreapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.estore.api.estoreapi.model.Products.*;
import com.estore.api.estoreapi.persistence.CartDAO;

/*
 * Handles the REST API Requests for the Cart resources 
 * 
 * @author rmr9535 jbc9236 (for docs)
 */
@RestController
@RequestMapping("cart")
public class CartController {

    private static final Logger LOG = Logger.getLogger(CartController.class.getName());
    /*The cartDAO to access methods in the user's cart*/
    private CartDAO cartDao; 

    /**
     * Constructs the CartController
     * @param cartDao the cartDAO that will access the user's cart
     */
    public CartController(CartDAO cartDao) {
        this.cartDao = cartDao;

    }
    
    /**
     * Responds to the GET request for all {@linkplain Product product} in the cart
     * 
     * @param user The name of the user that owns the cart 
     * 
     * @return ResponseEntity with ArrayList of {@link Product product} objects (may be empty) and
     * HTTP status of OK<br> 
     */
    @GetMapping("/items")
    public ResponseEntity<Product[]> getItems(@RequestParam String user) { 
        LOG.info("GET /cart items: " + user); 
        Product[] cart = cartDao.getItems(user); 
        if( cart == null ) {
            return new ResponseEntity<Product[]>(HttpStatus.NOT_FOUND); 
        }
        else {
            return new ResponseEntity<Product[]>(cart, HttpStatus.OK); 
        }
    } 

    /**
     * Responds to the GET request for {@linkplain Cart cart} object
     * 
     * @param user The name of the user that owns the cart 
     * 
     * @return ResponseEntity with ArrayList of {@link Product product} objects (may be empty) and
     * HTTP status of OK<br> 
     */
    @GetMapping("/")
    public ResponseEntity<Cart> getCart(@RequestParam String user) { 
        LOG.info("GET /cart: " + user ); 
        Cart cart = cartDao.getCart(user); 
        if( cart == null ) {
            return new ResponseEntity<Cart>(HttpStatus.NOT_FOUND); 
        }
        else {
            return new ResponseEntity<Cart>(cart, HttpStatus.OK); 
        }
    } 

    /**
     * Creates a {@linkplain Product product} and adds it to the cart
     * 
     * @param id the id of the product to be added to the cart
     * @param quantity the amount of the product to be added to the cart
     * @param user The name of the user that owns the cart
     * 
     * @return ResponseEntity with created {@link Product product} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of NOT_FOUND if {@link Product product} object does not exist<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("/")
    public ResponseEntity<Product> addToCart(@RequestParam String user, @RequestParam int id, @RequestParam int quantity) {

        LOG.info("POST /cart " + user + ": " + id + ":" + quantity);

        try{
            Product newProduct = cartDao.addToCart(user, id, quantity) ;
            if( newProduct != null ){
                return new ResponseEntity<Product>(newProduct, HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
            }
        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage()); 
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }

    /**
     * Removes a {@linkplain Product product} from the shopping cart
     * 
     * @param id the id of the product to be removed
     * @param quantity the amount of product to be removed 
     * @param user the name of the user taht owns the cart 
     * 
     * @return ResponseEntity with created {@link Product product} object and HTTP status of CREATED<br>,
     *      where the object represents the amount of product remaining in the cart
     * ResponseEntity with HTTP status of CONFLICT if {@link Product product} object does not exist<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/")
    public ResponseEntity<Product> removeFromCart(@RequestParam String user, @RequestParam int id, @RequestParam int quantity ) {

        LOG.info("DELETE /cart/" + id + "-" + quantity + ": " + user);

        try{

            Product remainingProduct = cartDao.removeFromCart(user, id, quantity); 
            if( remainingProduct != null ){
                return new ResponseEntity<Product>(remainingProduct, HttpStatus.OK); 
            }
            else{
                return new ResponseEntity<>(HttpStatus.CONFLICT); 
            }

        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Removes all {@linkplain Product products} from the shopping cart 
     * 
     * @param user The name pf the user that owns the cart 
     * 
     * @return ResponseEntity with HTTP status of CREATED<br> upon successful completion
     * ResponseEntity with HTTP status of CONFLICT if unsuccessful<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR in case of file error
     */
    @DeleteMapping("/*")
    public ResponseEntity<Product[]> emptyCart(@RequestParam String user) { 

        LOG.info("DELETE /cart " + user); 

        try{
            Product[] cart = cartDao.emptyCart(user) ;
            if( cart == null ) {
                return new ResponseEntity<Product[]>(HttpStatus.NOT_FOUND); 
            }
            else {
                return new ResponseEntity<Product[]>(cart, HttpStatus.OK); 
            }
        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * removes all {@linkplain Product products} from the shopping cart, removes
     * the appropriate amounts from the inventory, and returns the cost of all
     * items in the cart 
     * 
     * @param user The name of the user that owns the cart
     * 
     * @return ResponseEntity with HTTP status of CREATED<br> upon successful completion
     * ResponseEntity with HTTP status of CONFLICT if unsuccessful<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR in case of file error
     */
    @GetMapping("/*")
    public ResponseEntity<Checkout> checkout(@RequestParam String user) { 

        LOG.info("GET /cart/* " + user );
        try{
            Checkout checkedOut = cartDao.checkout(user) ;
            if( checkedOut.getCost() == 0 && checkedOut.getInvalidItems() != null ) {
                return new ResponseEntity<Checkout>(checkedOut, HttpStatus.OK);
            }
            if( checkedOut.getCost() > 0 ) {
                return new ResponseEntity<Checkout>(checkedOut, HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * returns the total cost of all {@linkplain Product products} in the cart
     * 
     * @param user The name of the user that owns the cart
     * 
     * @return ResponseEntity with the total cost and an HTTP status of CREATED<br> 
     */
    @GetMapping("/**")
    public ResponseEntity<Double> getTotalCost(@RequestParam String user) { 
        LOG.info("GET /cart/$ " + user );

        double cost = cartDao.getTotalCost( user ) ;
        return new ResponseEntity<Double>(cost, HttpStatus.OK);

    }
    
}