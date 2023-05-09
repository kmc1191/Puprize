package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.estore.api.estoreapi.model.Products.Product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;


/**
 * Test the Hero File DAO class
 * 
 * @author Jade Condez jbc9236@rit.edu
 * @author Paul Curcio phc6515@rit.edu
 */
@Tag("Persistence-tier")
public class InventoryFileDAOTest {
    InventoryFileDAO inventoryFileDAO;
    Product[] testProducts;
    ObjectMapper mockObjectMapper;


    @BeforeEach
    public void setupInventoryFileDAO() throws IOException{
        mockObjectMapper = mock(ObjectMapper.class);
        testProducts = new Product[3];
        testProducts[0] = new Product(99,"Dog product", 6.78, 9) ;
        testProducts[1] = new Product(100,"Dog product 2", 4.76, 5) ;
        testProducts[2] = new Product(101,"Dog Product 3", 10.95, 4);

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the hero array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"), Product[].class))
                .thenReturn(testProducts);
        inventoryFileDAO = new InventoryFileDAO("doesnt_matter.txt", mockObjectMapper);
    }

    @Test
    public void testSave() throws IOException{
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class), any(Product[].class));

        Product product = new Product(102,"Dog Product 4", 5.79, 7);

        assertThrows(IOException.class,
                        () -> inventoryFileDAO.createProduct(product),
                        "IOException not thrown");

    }

    @Test
    public void testLoad() throws IOException {
      //Setup
      ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
      doThrow(new IOException())
        .when(mockObjectMapper)
          .readValue(new File("doesnt_matter.txt"), Product[].class);
      
      //Invoke & Analyze
      assertThrows(IOException.class, 
                      () -> new InventoryFileDAO("doesnt_matter.txt", mockObjectMapper), 
                      "IOException not thrown");
    }

    @Test
    public void testGetInventory() {
        // Invoke
        Product[] products = inventoryFileDAO.getInventory();

        // Analyze
        assertEquals(products.length, testProducts.length);
        for (int i = 0; i < testProducts.length;++i)
            assertEquals(products[i], testProducts[i]);
    }

    @Test
    public void testGetProduct() {
             // Invoke
             Product product = inventoryFileDAO.getProduct(99);

             // Analzye
             assertEquals(product,testProducts[0]);
    }

    @Test
    public void testFindProducts() {
      //Invoke
      Product[] products = inventoryFileDAO.findProducts("dog");
        //Analyze

        assertEquals(products.length, 3);
        assertEquals(products[0], testProducts[0]);
        assertEquals(products[1], testProducts[1]);
        assertEquals(products[2], testProducts[2]);
    }

    @Test
    public void testCreateProduct() {
      //Setup
      Product product = new Product(102, "shampoo", 20, 1);

      //Invoke
      Product result = assertDoesNotThrow(() -> inventoryFileDAO.createProduct(product), "Unexpected exception thrown");

      //Analyze
      assertNotNull(result); 
      Product actual = inventoryFileDAO.getProduct(product.getId()); // changed to result from product
      assertEquals(actual.getId(), product.getId()); // changed to result from product
      assertEquals(actual.getName(), product.getName()); // changed to result from product
    }

    @Test
    public void testUpdateProduct() {
          //Setup
        Product product = new Product(101, "shampoo2", 15, 1);

          //Invoke
        Product result = assertDoesNotThrow(() -> inventoryFileDAO.updateProduct(product),
                                "Unexpected exception thrown");
          // Analyze
        assertNotNull(result);
        Product actual = inventoryFileDAO.getProduct(product.getId());
        assertEquals(actual,product);

    }

    @Test
    public void testDeleteProduct() {
    //Invoke
    boolean result = assertDoesNotThrow(() -> inventoryFileDAO.deleteProduct(99),
    "Unexpected exception thrown");
    // Analzye
    assertEquals(result,true);
    assertEquals(inventoryFileDAO.inventory.size(),testProducts.length-1);
    }

    @Test
    public void testGetProductNotFound() {
      //Invoke
      Product product = inventoryFileDAO.getProduct(98);

      //Analyze
      assertEquals(product, null);
    }

    @Test
    public void testDeleteProductNotFound() {
      //Invoke
      boolean result = assertDoesNotThrow(() -> inventoryFileDAO.deleteProduct(98), "Unexpected exception thrown");

      //Analyze
      assertEquals(result, false);
      assertEquals(inventoryFileDAO.inventory.size(), testProducts.length);
    }

    @Test
    public void testUpdateProductNotFound() {
      //Setup
      Product product = new Product(98, "Dog Product 7", 3.00, 2);

      //Invoke
      Product result = assertDoesNotThrow(() -> inventoryFileDAO.updateProduct(product), "Unexpected exception thrown");

      //Analyze
      assertNull(result);
    }

    @Test
    public void testChangeDisplayPrice(){
      
    }

    @Test
    public void testChangeDisplayID(){

    }

    @Test
    public void testChangeDisplayName(){

    }

    @Test
    public void testChangeDisplayQuantity(){

    }

    @Test
    public void testPrioritize() throws IOException{
  // Setup
    // when product2 is prioritized, it should appear first in the returned ArrayList
      Product[] prioritizedProducts = new Product[3];
      prioritizedProducts[0] = testProducts[2];
      prioritizedProducts[1] = testProducts[0];
      prioritizedProducts[2] = testProducts[1];

    // Invoke
      Product[] response = inventoryFileDAO.prioritize(testProducts[2].getId());  

    // Analyze 
    assertEquals(prioritizedProducts.length, response.length);

}

    @Test
    public void testPrioritizeNotFound() throws IOException{
      int id = 3;
      Product[] response = inventoryFileDAO.prioritize(id);

      assertEquals(null, response);
    }

}

