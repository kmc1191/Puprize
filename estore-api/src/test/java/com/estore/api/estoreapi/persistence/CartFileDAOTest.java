package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.estore.api.estoreapi.model.Products.Cart;
import com.estore.api.estoreapi.model.Products.Checkout;
import com.estore.api.estoreapi.model.Products.Product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Io;

import java.util.ArrayList;

/**
 * The unit test suite for the NameSort class
 * 
 * @author rmr9535
 */
@Tag("Persistence-tier")
public class CartFileDAOTest {
    InventoryDAO mockInventoryDAO;
    CartFileDAO cartFileDAO;
    ObjectMapper mockObjectMapper;
    Product[] testProducts;
    Cart[] testCarts ;
    String username ;
    Cart cart ;

    @BeforeEach
    public void setupInventoryFileDAO() throws IOException{
        mockObjectMapper = mock(ObjectMapper.class);
        username = "user" ;
        testProducts = new Product[3];
        testProducts[0] = new Product(99,"Dog product", 6.78, 9) ;
        testProducts[1] = new Product(100,"Dog product 2", 4.76, 5) ;
        testProducts[2] = new Product(101,"Dog Product 3", 10.95, 4);
        cart = new Cart(username) ;
        cart.setCart( testProducts ) ;
        testCarts = new Cart[1] ;
        testCarts[0] = cart ;

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the hero array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"), Product[].class))
                .thenReturn(testProducts);
        mockInventoryDAO = mock(InventoryDAO.class); ;

        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"), Cart[].class))
                .thenReturn(testCarts);
        mockInventoryDAO = mock(InventoryDAO.class); ;
        
        when(mockInventoryDAO.getProduct(99)).thenReturn(testProducts[0]); 
        when(mockInventoryDAO.getProduct(100)).thenReturn(testProducts[1]); 
        when(mockInventoryDAO.getProduct(101)).thenReturn(testProducts[2]); 

        cartFileDAO = new CartFileDAO("doesnt_matter.txt", mockObjectMapper, mockInventoryDAO); 
    }

    @Test
    public void testSave() throws IOException{
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class), any(Cart[].class));

        assertThrows(IOException.class,
                        () -> cartFileDAO.emptyCart(username),
                        "IOException not thrown");
    }

    @Test
    public void testGetCarts(){
        // Invoke
        Cart[] carts = cartFileDAO.getCarts(); 

        // Analyze
        assertEquals(carts.length, testCarts.length);
    }

    @Test
    public void testGetCart(){

        Cart cart = cartFileDAO.getCart(username) ;

        assertEquals( cart, testCarts[0] ) ;

    }

    @Test
    public void testGetCartNull(){

        Cart cart = cartFileDAO.getCart("fake_user") ;

        assertEquals( cart, null ) ;

    }

    @Test
    public void testGetItems(){

        Product[] cart = cartFileDAO.getItems(username) ;

        assertEquals( cart, testProducts ) ;

    }

    @Test
    public void testGetItemsNull(){

        Product[] cart = cartFileDAO.getItems("fake_user") ;

        assertEquals( cart, null ) ;

    }

    @Test
    public void testAddToCart() throws IOException {

        Product[] miniCart = new Product[ 1 ] ;
        miniCart[ 0 ] = null ;

        cart.setCart(miniCart);

        int amount = 1;
        Product result = cartFileDAO.addToCart(username, testProducts[0].getId(), testProducts[0].getQuantity() - amount ); 

        assertEquals(testProducts[0].getId(), result.getId());
        assertEquals(testProducts[0].getPrice(), result.getPrice());
        assertEquals(testProducts[0].getQuantity() - amount, result.getQuantity());
        assertEquals(testProducts[0].getName(), result.getName());

    }

    @Test
    public void testAddToCartNull() throws IOException {

        Product[] miniCart = new Product[ 1 ] ;
        miniCart[ 0 ] = null ;

        cart.setCart(miniCart);

        when(mockInventoryDAO.getProduct(testProducts[0].getId())).thenReturn(null); 

        Product result = cartFileDAO.addToCart(username, testProducts[0].getId(), testProducts[0].getQuantity() - 1); 

        assertNull(result); 

    }

    @Test
    public void testAddToCartTooMuch() throws IOException {

        Product[] miniCart = new Product[ 1 ] ;
        miniCart[ 0 ] = null ;

        cart.setCart(miniCart);

        int amount = 1;
        Product result = cartFileDAO.addToCart(username, testProducts[0].getId(), testProducts[0].getQuantity() + amount );

        assertNull(result); 

    }

    @Test 
    public void testAddToCartPreexistsValid() throws IOException {

        Product[] miniCart = new Product[ 1 ] ;
        miniCart[ 0 ] = null ;

        cart.setCart(miniCart);

        int amountExisting = testProducts[0].getQuantity() - 1 ;
        int amountToAdd = 1 ;
        Product testing = new Product(testProducts[0].getId(), testProducts[0].getName(), testProducts[0].getPrice(), testProducts[0].getQuantity() - amountExisting); 

        assertNotNull(cartFileDAO.addToCart(username, testing.getId(), testing.getQuantity())) ;

        Product result = cartFileDAO.addToCart(username, testProducts[0].getId(), testProducts[0].getQuantity() - amountToAdd );

        assertEquals(testing.getId(), result.getId());
        assertEquals(testing.getPrice(), result.getPrice());
        assertEquals(testProducts[0].getQuantity(), result.getQuantity());
        assertEquals(testing.getName(), result.getName());

    }

    @Test 
    public void testAddToCartPreexistsinvalid() throws IOException {

        Product[] miniCart = new Product[ 1 ] ;
        miniCart[ 0 ] = null ;

        cart.setCart(miniCart);

        int amountExisting = testProducts[0].getQuantity() - 1 ;
        Product testing = new Product(testProducts[0].getId(), testProducts[0].getName(), testProducts[0].getPrice(), testProducts[0].getQuantity() - amountExisting); 

        assertNotNull(cartFileDAO.addToCart(username, testing.getId(), testing.getQuantity())) ;

        Product result = cartFileDAO.addToCart(username, testProducts[0].getId(), testProducts[0].getQuantity());

        assertNull(result); 
        
    }

    @Test
    public void testAddToCartException() throws IOException {

        Product[] miniCart = new Product[ 1 ] ;
        miniCart[ 0 ] = null ;

        cart.setCart(miniCart);

        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class), any(Cart[].class));

        assertThrows(IOException.class,
                        () -> cartFileDAO.addToCart(username, testProducts[0].getId(), testProducts[0].getQuantity()),
                        "IOException not thrown");
    }

    @Test
    public void testRemoveFromCartEqualAmount() throws IOException {

        Product[] miniCart = new Product[ 1 ] ;
        miniCart[ 0 ] = null ;

        cart.setCart(miniCart);

        Product testing = new Product(testProducts[0].getId(), testProducts[0].getName(), testProducts[0].getPrice(), testProducts[0].getQuantity()); 

        assertNotNull(cartFileDAO.addToCart(username, testing.getId(), testing.getQuantity())) ;

        Product result = cartFileDAO.removeFromCart(username, testing.getId(), testing.getQuantity()); 

        assertEquals(testing.getId(), result.getId());
        assertEquals(testing.getPrice(), result.getPrice());
        assertEquals(0, result.getQuantity());
        assertEquals(testing.getName(), result.getName());

    }

    @Test
    public void testRemoveFromCartLessAmount() throws IOException {

        Product[] miniCart = new Product[ 1 ] ;
        miniCart[ 0 ] = null ;

        cart.setCart(miniCart);

        Product testing = new Product(testProducts[0].getId(), testProducts[0].getName(), testProducts[0].getPrice(), testProducts[0].getQuantity()); 

        assertNotNull(cartFileDAO.addToCart(username, testing.getId(), testing.getQuantity())) ;

        int amount = 1 ;
        Product result = cartFileDAO.removeFromCart(username, testing.getId(), testing.getQuantity() - amount ); 

        assertEquals(testing.getId(), result.getId());
        assertEquals(testing.getPrice(), result.getPrice());
        assertEquals(amount, result.getQuantity());
        assertEquals(testing.getName(), result.getName());

    }

    @Test 
    public void testRemoveFromCartTooMuch() throws IOException {

        Product[] miniCart = new Product[ 1 ] ;
        miniCart[ 0 ] = null ;

        cart.setCart(miniCart);

        Product testing = new Product(testProducts[0].getId(), testProducts[0].getName(), testProducts[0].getPrice(), testProducts[0].getQuantity()); 

        assertNotNull(cartFileDAO.addToCart(username, testing.getId(), testing.getQuantity())) ;

        int amount = 1 ;
        Product result = cartFileDAO.removeFromCart(username, testing.getId(), testing.getQuantity() + amount ); 

        assertNull(result); 
    }

    @Test 
    public void testRemoveFromCartNotInCart() throws IOException {

        Product[] miniCart = new Product[ 1 ] ;
        miniCart[ 0 ] = null ;

        cart.setCart(miniCart);

        Product result = cartFileDAO.removeFromCart(username, testProducts[0].getId(), testProducts[0].getQuantity()); 

        assertNull(result); 
    }

    @Test
    public void testRemoveFromCartException() throws IOException {

        Product[] miniCart = new Product[ 1 ] ;
        miniCart[ 0 ] = null ;

        cart.setCart(miniCart);

        Product testing = new Product(testProducts[0].getId(), testProducts[0].getName(), testProducts[0].getPrice(), testProducts[0].getQuantity()); 

        assertNotNull(cartFileDAO.addToCart(username, testing.getId(), testing.getQuantity())) ;

        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class), any(Cart[].class));

        int amount = 1 ;

        assertThrows(IOException.class,
                        () -> cartFileDAO.removeFromCart(username, testing.getId(), testing.getQuantity() - amount ),
                        "IOException not thrown");
    }

    @Test
    public void testEmptyCart() throws IOException { 

        Product[] miniCart = new Product[ 1 ] ;
        miniCart[ 0 ] = null ;

        cart.setCart(miniCart);

        Product testing = new Product(testProducts[0].getId(), testProducts[0].getName(), testProducts[0].getPrice(), testProducts[0].getQuantity()); 

        assertNotNull(cartFileDAO.addToCart(username, testing.getId(), testing.getQuantity())) ;

        Product[] result = cartFileDAO.emptyCart(username); 

        assertEquals(1, result.length); 
        assertNull(result[0]); 
    }

    @Test 
    public void testEmptyCartException() throws IOException {

        Product[] miniCart = new Product[ 1 ] ;
        miniCart[ 0 ] = null ;

        cart.setCart(miniCart);
        
        Product testing = new Product(testProducts[0].getId(), testProducts[0].getName(), testProducts[0].getPrice(), testProducts[0].getQuantity()); 

        assertNotNull(cartFileDAO.addToCart(username, testing.getId(), testing.getQuantity())) ;

        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class), any(Cart[].class));

        assertThrows(IOException.class,
                        () -> cartFileDAO.emptyCart(username),
                        "IOException not thrown");

    }

    @Test
    public void testGetTotalCost() throws IOException {

        Product[] miniCart = new Product[ 1 ] ;
        miniCart[ 0 ] = null ;

        cart.setCart(miniCart);

        Product testing = new Product(testProducts[0].getId(), testProducts[0].getName(), testProducts[0].getPrice(), testProducts[0].getQuantity()); 

        assertNotNull(cartFileDAO.addToCart(username, testing.getId(), testing.getQuantity())) ; 

        double result = testing.getPrice() * testing.getQuantity(); 

        assertTrue(result == cartFileDAO.getTotalCost(username));

    }

    @Test
    public void testGetTotalCostNullCart() throws IOException {

        Product[] miniCart = new Product[ 1 ] ;
        miniCart[ 0 ] = null ;

        cart.setCart(miniCart);

        
        assertEquals(0.0, cartFileDAO.getTotalCost(username));

    }

    @Test
    public void testCheckout() throws IOException {

        Product[] miniCart = new Product[ 1 ] ;
        miniCart[ 0 ] = null ;

        cart.setCart(miniCart);

        Product testing = new Product(testProducts[0].getId(), testProducts[0].getName(), testProducts[0].getPrice(), testProducts[0].getQuantity()); 

        assertNotNull(cartFileDAO.addToCart(username, testing.getId(), testing.getQuantity())) ; 

        double cost = testing.getPrice() * testing.getQuantity(); 

        Checkout checkout = new Checkout(cost, miniCart) ;
        Checkout result = cartFileDAO.checkout(username) ;

        assertEquals(checkout.getCost(), result.getCost()); 
        assertNull(result.getInvalidItems()); 

    }

    @Test 
    public void testCheckoutEmpty() throws IOException {

        Product[] miniCart = new Product[ 1 ] ;
        miniCart[ 0 ] = null ;

        cart.setCart(miniCart);

        assertEquals(0, cartFileDAO.checkout(username).getCost()); 
        assertEquals(null, cartFileDAO.checkout(username).getInvalidItems()) ;

    }

    @Test
    public void testCreateCart() throws IOException {

        Cart cart = new Cart( "new" ) ;

        Cart result = cartFileDAO.createCart("new") ;

        assertEquals( cart.getUser(), result.getUser() ) ;
        assertEquals( cart.getCart().length, result.getCart().length ) ;

    }

    @Test
    public void testCreateCartNull() throws IOException {

        Cart result = cartFileDAO.createCart(username) ;

        assertNull( result ) ;

    }
    
}
