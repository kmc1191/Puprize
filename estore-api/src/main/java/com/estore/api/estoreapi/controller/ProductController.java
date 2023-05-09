package com.estore.api.estoreapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.estore.api.estoreapi.model.Products.*;
import com.estore.api.estoreapi.persistence.InventoryDAO;
import com.estore.api.estoreapi.persistence.InventoryFileDAO;

/*
 * Handles the REST API Requests for the Product resources 
 * 
 * @author rmr9535 phc6515 jbc9236 kmc119
 */
@RestController
@RequestMapping("products")
public class ProductController {

    private static final Logger LOG = Logger.getLogger(ProductController.class.getName());
    private InventoryDAO inventoryDao; 

    public ProductController(InventoryDAO inventoryDao) {
        this.inventoryDao = inventoryDao;
    }
    
    @PutMapping("")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        LOG.info("PUT /products " + product);
        try {
            Product uProduct = inventoryDao.updateProduct(product);
            if (uProduct != null) {
                return new ResponseEntity<Product>(uProduct, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
/**
     * Responds to the GET request for a {@linkplain Product product} for the given id
     * 
     * @param id The id used to locate the {@link Product product}
     * 
     * @return ResponseEntity with {@link Product product} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id) {
        //Jade
        LOG.info("GET /products/" +id);
        try{
            Product product = inventoryDao.getProduct(id);
            if (product != null){
                return new ResponseEntity<Product>(product, HttpStatus.OK);
            } else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch(IOException e){
                LOG.log(Level.SEVERE, e.getLocalizedMessage());
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

    /**
     * Responds to the GET request for all {@linkplain Product product}
     * 
     * @return ResponseEntity with ArrayList of {@link Product product} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<Product[]> getInventory() {
        //Jade
        LOG.info("GET /inventory");
        try{
            Product[] inventory = inventoryDao.getInventory();
            return new ResponseEntity<Product[]>(inventory, HttpStatus.OK);
        } catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Product product} whose name contains
     * the text in name
     * 
     * @param name The name parameter which contains the text used to find the {@link Hero heroes}
     * 
     * @return ResponseEntity with ArrayList of {@link Product products} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/")
    public ResponseEntity<Product[]> findProducts(@RequestParam String name){
        //Jade
        LOG.info("GET /product/?name="+name);
        try{
            Product[] foundProducts = inventoryDao.findProducts(name);
            return new ResponseEntity<Product[]>(foundProducts, HttpStatus.OK);
        } catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Product product} sorted in a specified
     * order by the method String
     * 
     * @param method The word parameter which will denote the order in which to sort
     * the {@link Product products}
     * 
     * @return ResponseEntity with ArrayList of {@link Product products} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/sort")
    public ResponseEntity<Product[]> changeDisplay(@RequestParam String method){
        //Rafael
        String officialMethod ;
        if( method.toUpperCase().equals( InventoryFileDAO.BY_PRICE ) ) { officialMethod = InventoryFileDAO.BY_PRICE ; }
        else if( method.toUpperCase().equals( InventoryFileDAO.BY_QUANTITY ) ) { officialMethod = InventoryFileDAO.BY_QUANTITY ; }
        else if( method.toUpperCase().equals( InventoryFileDAO.BY_NAME ) ) { officialMethod = InventoryFileDAO.BY_NAME ; }
        else { officialMethod = InventoryFileDAO.BY_ID ; }
        
        LOG.info("GET /product/sort?method="+officialMethod);
        try{
            Product[] sortedProducts = inventoryDao.changeDisplay(officialMethod); 
            return new ResponseEntity<Product[]>(sortedProducts, HttpStatus.OK); 
        } catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage()); 
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Product product} with a product
     * of a specified ID prioritized (placed at the top of the list/display)
     * 
     * @param id The id number of the product to be prioritized {@link Product products}
     * 
     * @return ResponseEntity with ArrayList of {@link Product products} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/*")
    public ResponseEntity<Product[]> prioritize(@RequestParam int id) {
        //Rafael
        
        LOG.info("GET /product/*?id="+id); 
        try{
            Product[] priorityProducts = inventoryDao.prioritize(id); 
            if (priorityProducts != null){
                return new ResponseEntity<Product[]>(priorityProducts, HttpStatus.OK); 
            } else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            
        } catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage()); 
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }


    /**
     * Creates a {@linkplain Product product} with the provided product object
     * 
     * @param product - The {@link Product product} to create
     * 
     * @return ResponseEntity with created {@link Product product} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link Product product} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        //Kaitlynn

        LOG.info("POST /products " + product);

        try{

            //int idProduct = product.getId();
            String name = product.getName();
            //boolean nameNotFound = !( inventoryDao.findProducts(name) == null); //CHECK THIS
            //nameFound has to be false in order to create
            //if(inventoryDao.getProduct(idProduct) == null && nameNotFound){
            if(inventoryDao.findProducts(name).length == 0){
                Product product1 = inventoryDao.createProduct(product);
                return new ResponseEntity<Product>(product1, HttpStatus.CREATED);
            }
            else{
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Deletes a {@linkplain Product product} with the given id
     * 
     * @param id The id of the {@link Product product} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable int id) {
        //Kaitlynn

        LOG.info("DELETE /products/" + id);

        try{

            if(inventoryDao.deleteProduct(id)){
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }


        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
