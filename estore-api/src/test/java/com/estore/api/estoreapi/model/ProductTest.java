package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.Products.Product;

/**
 * The unit test suite for the Product class
 * 
 * @author hab1466
 */
@Tag("Model-Tier")
public class ProductTest {
    @Test
    public void testCtor(){   
        // Setup
        int expected_id = 25;
        String expected_name = "Paw Spray";
        double expected_price = 6.99;
        int expected_quantity = 300;

        // Invoke
        Product product = new Product(expected_id, expected_name, expected_price, expected_quantity);

        // Analyze
        assertEquals(expected_id, product.getId());
        assertEquals(expected_name, product.getName());
        assertEquals(expected_price, product.getPrice());
        assertEquals(expected_quantity, product.getQuantity());
    }

    @Test
    public void testName(){
        // Setup
        int id = 25;
        String name = "Paw Spray";
        double price = 0;
        int quantity = 0;
        Product product = new Product(id, name, price, quantity);

        String expected_name = "Hair Comb";

        // Invoke
        product.setName(expected_name);

        // Analyze
        assertEquals(expected_name, product.getName());
    }

    @Test
    public void testPrice(){
        // Setup
        int id = 25;
        String name = "Paw Spray";
        double price = 0;
        int quantity = 0;
        Product product = new Product(id, name, price, quantity);

        double expected_price = 6.99;

        // Invoke
        product.setPrice(expected_price);

        // Analyze
        assertEquals(expected_price, product.getPrice());
    }

    @Test
    public void testQuantity(){
        // Setup
        int id = 25;
        String name = "Paw Spray";
        double price = 0;
        int quantity = 0;
        Product product = new Product(id, name, price, quantity);

        int expected_quantity = 300;

        // Invoke
        product.setQuantity(expected_quantity);

        // Analyze
        assertEquals(expected_quantity, product.getQuantity());
    }

    @Test
    public void testToString(){
        // Setup
        int id = 25;
        String name = "Paw Spray";
        double price = 0;
        int quantity = 0;
        String expected_String = String.format(Product.STRING_FORMAT, id, name, price, quantity);
        Product product = new Product(id, name, price, quantity);

        // Invoke
        String actual_String = product.toString();

        // Analyze
        assertEquals(expected_String, actual_String);
    }
}
