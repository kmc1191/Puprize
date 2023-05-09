package com.estore.api.estoreapi.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.estore.api.estoreapi.persistence.CartDAO;
import com.estore.api.estoreapi.model.Products.Cart;
import com.estore.api.estoreapi.model.Products.Checkout;
import com.estore.api.estoreapi.model.Products.Product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Tag("Controller-tier")
public class CartControllerTest {
    private CartController cartController;
    private CartDAO mockCartDAO;

    @BeforeEach
    public void setupCartController() {
        mockCartDAO = mock(CartDAO.class);
        cartController = new CartController(mockCartDAO);
    }
    
    @Test
    public void testGetItems() throws IOException {
        //Setup
        Product[] products = new Product[2];
        products[0] = new Product(99, "Dog Product 1", 4.67, 8);
        products[1] = new Product(100, "Dog Product 2", 6.66, 3);
        String username = "user" ;
        Cart cart = new Cart(username); 
        cart.setCart(products); 
        when(mockCartDAO.getItems(username)).thenReturn(products);

        //Invoke
        ResponseEntity<Product[]> response = cartController.getItems(username);

        //Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(products, response.getBody());
    }

    @Test
    public void testGetItemsNullCase() throws IOException {
        //Setup
        String username = "user" ;
        when(mockCartDAO.getItems(username)).thenReturn(null);

        //Invoke
        ResponseEntity<Product[]> response = cartController.getItems(username);

        //Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetCart() throws IOException {
        //Setup
        Product[] products = new Product[2];
        products[0] = new Product(99, "Dog Product 1", 4.67, 8);
        products[1] = new Product(100, "Dog Product 2", 6.66, 3);
        String username = "user" ;
        Cart cart = new Cart(username); 
        cart.setCart(products); 
        when(mockCartDAO.getCart(username)).thenReturn(cart);

        //Invoke
        ResponseEntity<Cart> response = cartController.getCart(username);

        //Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cart, response.getBody());
    }

    @Test
    public void testGetCartNullCase() throws IOException {
        //Setup
        String username = "user" ;
        when(mockCartDAO.getCart(username)).thenReturn(null);

        //Invoke
        ResponseEntity<Cart> response = cartController.getCart(username);

        //Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testAddToCart() throws IOException { // updateHero may throw IOException
        // Setup
        Product product = new Product(99,"Dog Product 1", 5.89, 5);
        String username = "user" ;
        // when updateHero is called, return true simulating successful
        // update and save
        when(mockCartDAO.addToCart(username, product.getId(), product.getQuantity())).thenReturn(product);
        ResponseEntity<Product> response = cartController.addToCart(username, product.getId(), product.getQuantity());

        // Invoke
        //response = cartController.addToCart(product);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
    }

    @Test
    public void testaddToCartFailed() throws IOException { // updateHero may throw IOException
        // Setup
        Product product = new Product(99,"Dog Product 1", 5.89, 5);
        String username = "user" ;
        // when updateHero is called, return true simulating successful
        // update and save
        when(mockCartDAO.addToCart(username, product.getId(), product.getQuantity())).thenReturn(null);

        // Invoke
        ResponseEntity<Product> response = cartController.addToCart(username, product.getId(), product.getQuantity());

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testAddToCartHandleException() throws IOException { // updateHero may throw IOException
        // Setup
        Product product = new Product(99,"Dog Product 1", 5.89, 5);
        String username = "user" ;
        Cart cart = new Cart(username);
        // When updateHero is called on the Mock Hero DAO, throw an IOException
        doThrow(new IOException()).when(mockCartDAO).addToCart(username, product.getId(), product.getQuantity());

        // Invoke
        ResponseEntity<Product> response = cartController.addToCart(username, product.getId(), product.getQuantity());

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testRemoveFromCart() throws IOException { // deleteHero may throw IOException
        // Setup
        Product product = new Product(99,"Dog Product 1", 5.89, 5);
        String username = "user" ;
        // when deleteHero is called return true, simulating successful deletion
        when(mockCartDAO.removeFromCart(username, product.getId(), product.getQuantity())).thenReturn(product);

        // Invoke
        ResponseEntity<Product> response = cartController.removeFromCart(username, product.getId(), product.getQuantity());

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testRemoveFromCartConflict() throws IOException { // deleteHero may throw IOException
        // Setup
        Product product = new Product(99,"Dog Product 1", 5.89, 5);
        String username = "user" ;
        // when deleteHero is called return false, simulating failed deletion
        when(mockCartDAO.removeFromCart(username, product.getId(), product.getQuantity())).thenReturn(null);

        // Invoke
        ResponseEntity<Product> response = cartController.removeFromCart(username, product.getId(), product.getQuantity());

        // Analyze
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testRemoveFromCartHandleException() throws IOException { // deleteHero may throw IOException
        // Setup
        Product product = new Product(99,"Dog Product 1", 5.89, 5);
        String username = "user" ;
        // When deleteHero is called on the Mock Hero DAO, throw an IOException
        doThrow(new IOException()).when(mockCartDAO).removeFromCart(username, product.getId(), product.getQuantity());

        // Invoke
        ResponseEntity<Product> response = cartController.removeFromCart(username, product.getId(), product.getQuantity());

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testEmptyCart() throws IOException{
        //Setup
        String username = "user" ;
        Product[] products = new Product[1] ;
        products[ 0 ] = null ;
        when(mockCartDAO.emptyCart(username)).thenReturn(products);
        //Invoke
        ResponseEntity<Product[]> response = cartController.emptyCart(username);
        //Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    public void testEmptyCartConflict() throws IOException{
        //Setup
        String username = "user" ;
        when(mockCartDAO.emptyCart(username)).thenReturn(null);
        //Invoke
        ResponseEntity<Product[]> response = cartController.emptyCart(username);
        //Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }
    
    @Test
    public void testCheckoutNoInvalid() throws IOException{
        //Setup
        double cost = 1;
        String username = "user" ;
        Checkout checkout = new Checkout(cost, null) ;
        when(mockCartDAO.checkout(username)).thenReturn(checkout);
        //Invoke
        ResponseEntity<Checkout> response = cartController.checkout(username);
        //Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    public void testCheckoutAllInvalid() throws IOException{
         //Setup
        double cost = 0;
        String username = "user" ;
        Product[] products = new Product[1] ;
        products[ 0 ] = null ;
        Checkout checkout = new Checkout(cost, products) ;
        when(mockCartDAO.checkout(username)).thenReturn(checkout);
         //Invoke
        ResponseEntity<Checkout> response = cartController.checkout(username);
         //Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    public void testCheckoutException() throws IOException {
         //Setup
        double cost = 0;
        String username = "user" ;
        Checkout checkout = new Checkout(cost, null) ;
        when(mockCartDAO.checkout(username)).thenReturn(checkout);
         //Invoke
        ResponseEntity<Checkout> response = cartController.checkout(username);
         //Analyze
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());

    }

    @Test
    public void testCheckoutFailed() throws IOException{
        //Setup
        String username = "user" ;
        doThrow(new IOException()).when(mockCartDAO).checkout(username);
        //Invoke
        ResponseEntity<Checkout> response = cartController.checkout(username);
        //Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    }

    @Test
    public void testGetTotalCost(){
        //Setup
        String username = "user" ;
        Double cost = 0.0 ;
        when(mockCartDAO.getTotalCost(username)).thenReturn(cost);
        //Invoke
        ResponseEntity<Double> response = cartController.getTotalCost(username);
        //Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cost, response.getBody());

    }

}
