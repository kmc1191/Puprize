package com.estore.api.estoreapi.controller; 

import static org.junit.jupiter.api.Assertions.assertEquals; 
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;

import com.estore.api.estoreapi.controller.ProductController; 
import com.estore.api.estoreapi.model.Products.Product;
import com.estore.api.estoreapi.persistence.InventoryDAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; 

/**
 * Test the Product Controller class
 * 
 * @author rmr9535 (jade, replace this with your username when you edit!)
 */
@Tag("Controller-tier")
public class ProductControllerTest {

    private ProductController productController ;
    private InventoryDAO mockInventoryDAO ;

    /**
     * Before each test, create a new ProductController object and inject
     * a mock Inventory DAO 
     */
    @BeforeEach
    public void setupProductController() {
        mockInventoryDAO = mock(InventoryDAO.class); 
        productController = new ProductController(mockInventoryDAO); 
    }

    @Test
    public void testGetProduct() throws IOException {
        // Setup
        Product product = new Product(9, "Shampoodle", 10.0, 5);
        when(mockInventoryDAO.getProduct(product.getId())).thenReturn(product); 

        // Invoke 
        ResponseEntity<Product> response = productController.getProduct(product.getId()); 

        // Analyze 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());

    }

    @Test
    public void testGetProductNotFound() throws IOException {
        // Setup
        int productId = 9 ;
        when(mockInventoryDAO.getProduct(productId)).thenReturn(null); 

        // Invoke 
        // when no hero is found for the id, null is returned with an error code of
        // "not found"
        ResponseEntity<Product> response = productController.getProduct(productId); 

        // Analyze 
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test
    public void testGetProductHandleException() throws Exception {
        // Setup
        int productId = 9 ;
        doThrow(new IOException()).when(mockInventoryDAO).getProduct(productId); 

        // Invoke 
        // testing the statement that throws an IOException given a file error
        ResponseEntity<Product> response = productController.getProduct(productId); 

        // Analyze 
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    }

    @Test
    public void testPrioritize() throws IOException {
        // Setup
        Product product1 = new Product(9, "Collie-ditioner", 10.0, 3);
        Product product2 = new Product(10, "Shampoodle", 80.0, 5);
        Product[] prioritizedProducts = new Product[2] ;
        prioritizedProducts[0] = product2;
        prioritizedProducts[1] = product1;
        // when product2 is prioritized, it should appear first in the returned ArrayList
        when(mockInventoryDAO.prioritize(product2.getId())).thenReturn(prioritizedProducts); 

        // Invoke
        ResponseEntity<Product[]> response = productController.prioritize(product2.getId());  

        // Analyze 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(prioritizedProducts, response.getBody());

    }

    @Test
    public void testPrioritizeNotFound() throws IOException {
        // Setup
        int productId = 2 ;
        when(mockInventoryDAO.prioritize(productId)).thenReturn(null); 

        // Invoke 
        // when no hero is found for the id, an error code of NOT_FOUND is given
        ResponseEntity<Product[]> response = productController.prioritize(productId); 

        // Analyze 
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test
    public void testPrioritizeHandleException() throws IOException {
        // Setup
        int productId = 9 ;
        doThrow(new IOException()).when(mockInventoryDAO).prioritize(productId); 

        // Invoke 
        // testing the statement that throws an IOException given a file error
        ResponseEntity<Product[]> response = productController.prioritize(productId);

        // Analyze 
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    }

    @Test
    public void testGetInventory() throws IOException {
        // Setup
        Product product1 = new Product(9, "Collie-ditioner", 10.0, 3);
        Product product2 = new Product(10, "Shampoodle", 80.0, 5);
        Product[] prioritizedProducts = new Product[2] ;
        prioritizedProducts[0] = product2;
        prioritizedProducts[1] = product1;
        when(mockInventoryDAO.getInventory()).thenReturn(prioritizedProducts); 

        // Invoke
        ResponseEntity<Product[]> response = productController.getInventory();  

        // Analyze 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(prioritizedProducts, response.getBody());

    }

    @Test
    public void testGetInventoryHandleException() throws IOException {
        // Setup
        doThrow(new IOException()).when(mockInventoryDAO).getInventory(); 

        // Invoke
        ResponseEntity<Product[]> response = productController.getInventory();  

        // Analyze 
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    }

    @Test
    public void testChangeDisplayPrice() throws IOException {
        // Setup
        Product product1 = new Product(9, "Collie-ditioner", 80.0, 3);
        Product product2 = new Product(10, "Shampoodle", 10.0, 5);
        Product[] sortedProducts = new Product[2] ;
        sortedProducts[0] = product2;
        sortedProducts[1] = product1;
        String method = "PRICE"; 
        when(mockInventoryDAO.changeDisplay(method)).thenReturn(sortedProducts); 

        // Invoke
        ResponseEntity<Product[]> response = productController.changeDisplay(method); 

        // Analyze 
        assertEquals(sortedProducts, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    public void testChangeDisplayQuantity() throws IOException {
        // Setup
        Product product1 = new Product(9, "Collie-ditioner", 80.0, 8);
        Product product2 = new Product(10, "Shampoodle", 10.0, 5);
        Product[] sortedProducts = new Product[2] ;
        sortedProducts[0] = product2;
        sortedProducts[1] = product1;
        String method = "QUANTITY"; 
        when(mockInventoryDAO.changeDisplay(method)).thenReturn(sortedProducts); 

        // Invoke
        ResponseEntity<Product[]> response = productController.changeDisplay(method); 

        // Analyze 
        assertEquals(sortedProducts, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    public void testChangeDisplayName() throws IOException {
        // Setup
        Product product1 = new Product(9, "Shampoodle", 80.0, 3);
        Product product2 = new Product(10, "Collie-ditioner", 10.0, 5);
        Product[] sortedProducts = new Product[2] ;
        sortedProducts[0] = product2;
        sortedProducts[1] = product1;
        String method = "NAME"; 
        when(mockInventoryDAO.changeDisplay(method)).thenReturn(sortedProducts); 

        // Invoke
        ResponseEntity<Product[]> response = productController.changeDisplay(method); 

        // Analyze 
        assertEquals(sortedProducts, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    public void testChangeDisplayID() throws IOException {
        // Setup
        Product product1 = new Product(9, "Shampoodle", 80.0, 3);
        Product product2 = new Product(8, "Collie-ditioner", 10.0, 5);
        Product[] sortedProducts = new Product[2] ;
        sortedProducts[0] = product2;
        sortedProducts[1] = product1;
        String method = "ID"; 
        when(mockInventoryDAO.changeDisplay(method)).thenReturn(sortedProducts); 

        // Invoke
        ResponseEntity<Product[]> response = productController.changeDisplay(method); 

        // Analyze 
        assertEquals(sortedProducts, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    public void testChangeDisplayHandleException() throws IOException {
        // Setup
        doThrow(new IOException()).when(mockInventoryDAO).changeDisplay("PRICE"); 

        // Invoke
        ResponseEntity<Product[]> response = productController.changeDisplay("PRICE");  

        // Analyze 
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    }

    @Test
    public void testCreateProduct() throws IOException {
        // Setup
        Product product = new Product(10, "Shampoo3", 50.0, 6);
        when(mockInventoryDAO.createProduct(product)).thenReturn(product);
        when(mockInventoryDAO.findProducts(product.getName())).thenReturn(new Product[0]);

        //Invoke
        ResponseEntity<Product> response = productController.createProduct(product);
        //Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(product,response.getBody());

    }

    @Test
    public void testCreateProductFailed() throws IOException {
        // Setup
        Product product = new Product(9,"Shampoodle", 80.0, 3);

        //Invoke
        when(mockInventoryDAO.getProduct(product.getId())).thenReturn(product);
        when(mockInventoryDAO.findProducts(product.getName())).thenReturn(new Product[1]);
        ResponseEntity<Product> response = productController.createProduct(product);
        //Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
        
    }

    @Test
    public void testCreateProductHandleException() throws IOException {
        // Setup
        Product product= new Product(9,"shampoo3", 80.0, 3);
        when(mockInventoryDAO.findProducts(product.getName())).thenReturn(new Product[0]);
        doThrow(new IOException()).when(mockInventoryDAO).createProduct(product);
        //Invoke
        ResponseEntity<Product> response = productController.createProduct(product);
        //Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());

        
    }

    @Test
    public void testUpdateProduct() throws IOException {
        // Setup
        Product product= new Product(9,"Shampoodle", 85.0, 3);
        when(mockInventoryDAO.updateProduct(product)).thenReturn(product);
        ResponseEntity<Product> response = productController.updateProduct(product);
        product.setName("Shampoodle 2: Electric Poodledoo");
        //Invoke
        response = productController.updateProduct(product);
        //Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(product,response.getBody());

    }

    @Test
    public void testUpdateProductFailed() throws IOException {
        // Setup
        Product product = new Product(9,"Shampoodloo", 85.0, 4);
        when(mockInventoryDAO.updateProduct(product)).thenReturn(null);
        //Invoke
        ResponseEntity<Product> response = productController.updateProduct(product);

        //Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());


    }

    @Test
    public void testUpdateProductHandleException() throws IOException {
        // Setup
        Product product = new Product(9,"Shampoodloo", 85.0, 4);
        doThrow(new IOException()).when(mockInventoryDAO).updateProduct(product);
        //Invoke
        ResponseEntity<Product> response = productController.updateProduct(product);

        //Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());

    }

    @Test
    public void testFindProducts() throws IOException {
        // Setup
        String searchString = "sham";
        Product[] products = new Product[2] ;
        products[0] = new Product(9, "Shampoo", 80.0, 2);
        products[1] = new Product(10, "Shampoodle", 80.0, 2);
        when(mockInventoryDAO.findProducts(searchString)).thenReturn(products);
        //Invoke
        ResponseEntity<Product[]> response = productController.findProducts(searchString);

        //Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(products,response.getBody());


    }

    @Test
    public void testFindProductsHandleException() throws IOException {
        // Setup
        String searchString = "an";
        doThrow(new IOException()).when(mockInventoryDAO).findProducts(searchString);
        //Invoke
        ResponseEntity<Product[]> response = productController.findProducts(searchString);

        //Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());


}

    @Test
    public void testDeleteProduct() throws IOException {
        // Setup
        int productId = 9;
        when(mockInventoryDAO.deleteProduct(productId)).thenReturn(true);
        //Invoke
        ResponseEntity<Product> response = productController.deleteProduct(productId);

        //Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());

    }

    @Test
    public void testDeleteProductNotFound() throws IOException {
        // Setup
        int productId = 9;
        when(mockInventoryDAO.deleteProduct(productId)).thenReturn(false);

        //Invoke
        ResponseEntity<Product> response = productController.deleteProduct(productId);

        //Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());

    }

    @Test
    public void testDeleteProductHandleException() throws IOException {
        // Setup
        int productId = 9;
        doThrow(new IOException()).when(mockInventoryDAO).deleteProduct(productId);

        //Invoke
        ResponseEntity<Product> response = productController.deleteProduct(productId);


        //Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());

    }

}