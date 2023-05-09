package com.estore.api.estoreapi.persistence;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.persistence.NameSort; 
import com.estore.api.estoreapi.model.Products.Product;

/**
 * The unit test suite for the NameSort class
 * 
 * @author rmr9535
 */
@Tag("Persistence-tier")
public class NameSortTest {

    /**
     * This test function will test to see if the sortByName function can properly sort
     * elements with different beginning letters, no duplications, in no specific order
     * (example: already sorted, inversely sorted, etc.)
     * 
     * Note: this test function relies on Product's constructor and certain accessor
     * methods functioning correctly. Please run the jUnit set for Product.java before
     * assuming correctness in this test file. 
     */
    @Test
    public void testWithNonspecificOrder() {

        // Setup 
        Product p1 = new Product(1, "Z-letter", 1, 1) ;
        Product p2 = new Product(2, "C-letter", 1, 1) ;
        Product p3 = new Product(3, "A-letter", 1, 1) ;
        Product p4 = new Product(4, "Y-letter", 1, 1) ;
        Product p5 = new Product(5, "X-letter", 1, 1) ;
        Product p6 = new Product(6, "B-letter", 1, 1) ;

        Map<Integer, Product> unsorted = new TreeMap<Integer, Product>(); 

        unsorted.put( p1.getId(), p1 ) ;
        unsorted.put( p2.getId(), p2 ) ;
        unsorted.put( p3.getId(), p3 ) ;
        unsorted.put( p4.getId(), p4 ) ;
        unsorted.put( p5.getId(), p5 ) ;
        unsorted.put( p6.getId(), p6 ) ;

        // The proper sorted order will have the products listed alphabetically, as follows
        Set<Integer> solutionKeys = new HashSet<>(Arrays.asList(3, 6, 2, 5, 4, 1)) ;

        // Invoke
        Map<Integer, Product> sorted = NameSort.sortByName(unsorted) ;

        // Analyze
        assertEquals( solutionKeys, sorted.keySet() ) ;

    }

    /**
     * This test function will test to see if the sortByName function can properly sort
     * elements that have the same letters but occuring in different cases. Example: when
     * sorted, an uppercase A should precede a lowercase a, and both should precede an 
     * uppercase B
     * 
     * Note: this test function relies on Product's constructor and certain accessor
     * methods functioning correctly. Please run the jUnit set for Product.java before
     * assuming correctness in this test file. 
     */
    @Test
    public void testWithCaseSensitivity() {

        // Setup 
        Product p1 = new Product(1, "A-letter", 1, 1) ;
        Product p2 = new Product(2, "b-letter", 1, 1) ;
        Product p3 = new Product(3, "c-letter", 1, 1) ;
        Product p4 = new Product(4, "C-letter", 1, 1) ;
        Product p5 = new Product(5, "B-letter", 1, 1) ;
        Product p6 = new Product(6, "a-letter", 1, 1) ;

        Map<Integer, Product> unsorted = new TreeMap<Integer, Product>(); 

        unsorted.put( p1.getId(), p1 ) ;
        unsorted.put( p2.getId(), p2 ) ;
        unsorted.put( p3.getId(), p3 ) ;
        unsorted.put( p4.getId(), p4 ) ;
        unsorted.put( p5.getId(), p5 ) ;
        unsorted.put( p6.getId(), p6 ) ;

        // The proper sorted order will have the products listed alphabetically, as follows. 
        // Capital letters shoulder compe before lowercase letters, but everything should still
        // proceed alphabetically
        Set<Integer> solutionKeys = new HashSet<>(Arrays.asList(1, 6, 5, 2, 4, 3)) ;

        // Invoke
        Map<Integer, Product> sorted = NameSort.sortByName(unsorted) ;

        // Analyze
        assertEquals( solutionKeys, sorted.keySet() ) ;

    }

    /**
     * This test function will test to see if the sortByName function can properly handle
     * the case where the inputted map is already sorted
     * 
     * Note: this test function relies on Product's constructor and certain accessor
     * methods functioning correctly. Please run the jUnit set for Product.java before
     * assuming correctness in this test file. 
     */
    @Test
    public void testAlreadySorted() {

        // Setup 
        Product p1 = new Product(1, "A-letter", 1, 1) ;
        Product p2 = new Product(2, "a-letter", 1, 1) ;
        Product p3 = new Product(3, "B-letter", 1, 1) ;
        Product p4 = new Product(4, "b-letter", 1, 1) ;
        Product p5 = new Product(5, "C-letter", 1, 1) ;
        Product p6 = new Product(6, "c-letter", 1, 1) ;

        Map<Integer, Product> unsorted = new TreeMap<Integer, Product>(); 

        unsorted.put( p1.getId(), p1 ) ;
        unsorted.put( p2.getId(), p2 ) ;
        unsorted.put( p3.getId(), p3 ) ;
        unsorted.put( p4.getId(), p4 ) ;
        unsorted.put( p5.getId(), p5 ) ;
        unsorted.put( p6.getId(), p6 ) ;

        // The proper sorted order will have the products listed alphabetically, as follows. 
        // Capital letters shoulder compe before lowercase letters, but everything should still
        // proceed alphabetically
        Set<Integer> solutionKeys = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6)) ;

        // Invoke
        Map<Integer, Product> sorted = NameSort.sortByName(unsorted) ;

        // Analyze
        assertEquals( solutionKeys, sorted.keySet() ) ;

    }

    /**
     * This test function will test to see if the sortByName function can properly sort
     * objects with the same name: in this case, duplicately named objects
     * will be sorted by price
     * 
     * Note: by the cURL commands for Products, it is not technically possible to create
     * two Products with the same name, but in the interest of coverage NameSort does
     * provide a handle for this scenario. 
     * 
     * Note: this test function relies on Product's constructor and certain accessor
     * methods functioning correctly. Please run the jUnit set for Product.java before
     * assuming correctness in this test file. 
     */
    @Test
    public void testSameName() {

        // Setup 
        Product p1 = new Product(1, "A-letter", 5.0, 1) ;
        Product p2 = new Product(2, "B-letter", 100, 1) ;
        Product p3 = new Product(3, "B-letter", 10, 1) ;
        Product p4 = new Product(4, "A-letter", 5.5, 1) ;
        Product p5 = new Product(5, "B-letter", 1, 1) ;

        Map<Integer, Product> unsorted = new TreeMap<Integer, Product>(); 

        unsorted.put( p1.getId(), p1 ) ;
        unsorted.put( p2.getId(), p2 ) ;
        unsorted.put( p3.getId(), p3 ) ;
        unsorted.put( p4.getId(), p4 ) ;
        unsorted.put( p5.getId(), p5 ) ;

        // The proper sorted order will have the products listed alphabetically, as follows. 
        // Capital letters shoulder compe before lowercase letters, but everything should still
        // proceed alphabetically. In addition, entries with the same name should be sorted by
        // ascending price
        Set<Integer> solutionKeys = new HashSet<>(Arrays.asList(1, 4, 5, 3, 2)) ;

        // Invoke
        Map<Integer, Product> sorted = NameSort.sortByName(unsorted) ;

        // Analyze
        assertEquals( solutionKeys, sorted.keySet() ) ;

    }

    
    
}
