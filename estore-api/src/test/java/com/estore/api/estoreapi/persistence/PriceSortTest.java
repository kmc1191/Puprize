package com.estore.api.estoreapi.persistence;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.persistence.PriceSort; 
import com.estore.api.estoreapi.model.Products.Product;

/**
 * The unit test suite for the PriceSort class
 * 
 * @author hab1466
 */
@Tag("Persistence-tier")
public class PriceSortTest {
    
    @Test
    public void testRandomOrder(){
        // Setup 
        Product p1 = new Product(1, "a", 3.69, 1);
        Product p2 = new Product(2, "b", 6.89, 1);
        Product p3 = new Product(3, "c", 2.00, 1);
        Product p4 = new Product(4, "d", 10.99, 1);
        
        Map<Integer, Product> unsorted = new TreeMap<Integer, Product>(); 
        
        unsorted.put(p1.getId(), p1);
        unsorted.put(p2.getId(), p2);
        unsorted.put(p3.getId(), p3);
        unsorted.put(p4.getId(), p4);
        
        Set<Integer> expected_Order = new HashSet<>(Arrays.asList(3, 1, 2, 4));

        // Invoke
        Map<Integer, Product> sorted = PriceSort.sortByPrice(unsorted) ;

        // Analyze
        assertEquals(expected_Order, sorted.keySet());
    }

    @Test
    public void testSortedOrder(){
        // Setup 
        Product p1 = new Product(1, "a", 1.69, 1);
        Product p2 = new Product(2, "b", 2.89, 1);
        Product p3 = new Product(3, "c", 3.00, 1);
        Product p4 = new Product(4, "d", 4.99, 1);
        
        Map<Integer, Product> unsorted = new TreeMap<Integer, Product>(); 
        
        unsorted.put(p1.getId(), p1);
        unsorted.put(p2.getId(), p2);
        unsorted.put(p3.getId(), p3);
        unsorted.put(p4.getId(), p4);
        
        Set<Integer> expected_Order = new HashSet<>(Arrays.asList(1, 2, 3, 4));

        // Invoke
        Map<Integer, Product> sorted = PriceSort.sortByPrice(unsorted) ;

        // Analyze
        assertEquals(expected_Order, sorted.keySet());
    }

    @Test
    public void testOrderWithDuplicates(){
        // Setup 
        Product p1 = new Product(1, "a", 1.69, 1);
        Product p2 = new Product(2, "b", 2.89, 1);
        Product p3 = new Product(3, "c", 1.69, 1);
        Product p4 = new Product(4, "d", 4.99, 1);
        Product p5 = new Product(5, "e", 2.89, 1);


        Map<Integer, Product> unsorted = new TreeMap<Integer, Product>(); 
        
        unsorted.put(p1.getId(), p1);
        unsorted.put(p2.getId(), p2);
        unsorted.put(p3.getId(), p3);
        unsorted.put(p4.getId(), p4);
        unsorted.put(p5.getId(), p5);


        Set<Integer> expected_Order = new HashSet<>(Arrays.asList(1, 3, 2, 5, 4));

        // Invoke
        Map<Integer, Product> sorted = PriceSort.sortByPrice(unsorted) ;

        // Analyze
        assertEquals(expected_Order, sorted.keySet());
    }
}
