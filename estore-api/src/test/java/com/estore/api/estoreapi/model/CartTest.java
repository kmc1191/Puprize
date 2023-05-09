package com.estore.api.estoreapi.model;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
 
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.Products.Cart;
import com.estore.api.estoreapi.model.Products.Product;

@Tag("Model-tier")
public class CartTest {

    @Test
    public void testCtor(){
        //Setup
        String expected_username = "scubaby";
        Product[] expected_cart = new Product[1] ;
        expected_cart[ 0 ] = null ;
  
        //Invoke
        Cart cart = new Cart( expected_username ) ;
  
        //cart.setCart(expected_cart);
  
        //Analyze
        assertEquals(expected_username, cart.getUser());
        assertEquals(expected_cart.length, cart.getCart().length);
        assertEquals(expected_cart[0], cart.getCart()[0]);
  
    }

    @Test
    public void testGetUser() {
        // Setup
        String expected_username = "scubaby";

        //Invoke
        Cart cart = new Cart( expected_username ) ;

        //Analyze
        assertEquals(expected_username, cart.getUser());

    }

    @Test
    public void testGetCart() {
        // Setup
        String expected_username = "scubaby";
        Product[] expected_cart = new Product[1] ;
        expected_cart[ 0 ] = null ;

        //Invoke
        Cart cart = new Cart( expected_username ) ;

        //Analyze
        assertEquals(expected_cart.length, cart.getCart().length);
        assertEquals(expected_cart[0], cart.getCart()[0]);

    }

    @Test
    public void testSetUser() {
        // Setup
        String startUser = "scubaby";
        String expected_username = "user";
        Cart cart = new Cart( startUser ) ;

        //Invoke
        cart.setUser(expected_username);

        //Analyze
        assertEquals(expected_username, cart.getUser());

    }

    @Test
    public void testSetCart() {
        // Setup
        String expected_username = "user";
        Cart cart = new Cart( expected_username ) ;
        Product[] products = new Product[2];
        products[0] = new Product(99, "Dog Product 1", 4.67, 8);
        products[1] = new Product(100, "Dog Product 2", 6.66, 3);

        //Invoke
        cart.setCart(products) ;

        //Analyze
        assertEquals(products, cart.getCart());
        assertEquals(products[0], cart.getCart()[0]);
        assertEquals(products[1], cart.getCart()[1]);

    }

    @Test
    public void testToString(){
        // Setup
        String expected_username = "user";
        Cart cart = new Cart( expected_username ) ;
        
        String expected_String = String.format(Cart.STRING_FORMAT, expected_username);
        String expectedpart2 = "null ]";

        // Invoke
        String actual_String = cart.toString();

        // Analyze
        assertEquals(expected_String + expectedpart2, actual_String);
    }


    
}
