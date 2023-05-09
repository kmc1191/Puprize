package com.estore.api.estoreapi.persistence;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.persistence.QuantitySort; 
import com.estore.api.estoreapi.model.Products.Product;

/**
 * The unit test suite for the QuantitySort class
 * 
 * @author rmr9535
 * @author phc6515
 */
@Tag("Persistence-tier")
public class QuantitySortTest {

    @Test
    public void testWithNonspecificOrder() {

        // Setup 
        Product p1 = new Product(1, "a", 1, 1);
        Product p2 = new Product(2, "b", 1, 3);
        Product p3 = new Product(3, "c", 1, 2);
        Product p4 = new Product(4, "d", 1, 10);
        Product p5 = new Product(5, "e", 1, 9);
        Product p6 = new Product(6, "f", 1, 5);

        Map<Integer, Product> unsorted = new TreeMap<Integer, Product>(); 

        unsorted.put( p1.getId(), p1 ) ;
        unsorted.put( p2.getId(), p2 ) ;
        unsorted.put( p3.getId(), p3 ) ;
        unsorted.put( p4.getId(), p4 ) ;
        unsorted.put( p5.getId(), p5 ) ;
        unsorted.put( p6.getId(), p6 ) ;

        // The proper sorted order will have the products listed in ascending order, as follows
        Set<Integer> solutionKeys = new HashSet<>(Arrays.asList(1, 3, 2, 6, 5, 4)) ;

        // Invoke
        Map<Integer, Product> sorted = QuantitySort.sortByQuantity(unsorted) ;

        // Analyze
        assertEquals( solutionKeys, sorted.keySet() ) ;

    }

    @Test
    public void testAlreadySorted() {

        // Setup 
        Product p1 = new Product(1, "a", 1, 1) ;
        Product p2 = new Product(2, "b", 1, 2) ;
        Product p3 = new Product(3, "c", 1, 3) ;
        Product p4 = new Product(4, "d", 1, 4) ;
        Product p5 = new Product(5, "e", 1, 5) ;
        Product p6 = new Product(6, "f", 1, 6) ;

        Map<Integer, Product> unsorted = new TreeMap<Integer, Product>(); 

        unsorted.put( p1.getId(), p1 ) ;
        unsorted.put( p2.getId(), p2 ) ;
        unsorted.put( p3.getId(), p3 ) ;
        unsorted.put( p4.getId(), p4 ) ;
        unsorted.put( p5.getId(), p5 ) ;
        unsorted.put( p6.getId(), p6 ) ;

        Set<Integer> solutionKeys = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6)) ;

        // Invoke
        Map<Integer, Product> sorted = QuantitySort.sortByQuantity(unsorted);

        // Analyze
        assertEquals( solutionKeys, sorted.keySet() ) ;

    }

    @Test
    public void testSameQuantity() {

        // Setup 
        Product p1 = new Product(1, "b", 5.0, 1) ;
        Product p2 = new Product(2, "a", 100, 1) ;
        Product p3 = new Product(3, "c", 10, 1) ;
        Product p4 = new Product(4, "d", 5.5, 1) ;
        Product p5 = new Product(5, "e", 1, 1) ;

        Map<Integer, Product> unsorted = new TreeMap<Integer, Product>(); 

        unsorted.put( p1.getId(), p1 ) ;
        unsorted.put( p2.getId(), p2 ) ;
        unsorted.put( p3.getId(), p3 ) ;
        unsorted.put( p4.getId(), p4 ) ;
        unsorted.put( p5.getId(), p5 ) ;

        Set<Integer> solutionKeys = new HashSet<>(Arrays.asList(2, 1, 3, 4, 5));

        // Invoke
        Map<Integer, Product> sorted = QuantitySort.sortByQuantity(unsorted);

        // Analyze
        assertEquals( solutionKeys, sorted.keySet() ) ;

    }

    
    
}

