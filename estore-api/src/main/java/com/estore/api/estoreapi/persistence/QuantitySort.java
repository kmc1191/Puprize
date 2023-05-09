package com.estore.api.estoreapi.persistence;
import java.util.* ;
import com.estore.api.estoreapi.model.Products.Product;

/**
 * This class is used as a means to sort a map by an attribute of the value, not the key. 
 * In this case, the Value is an object of class Product. This class implements a method 
 * that will allow the inventory to be sorted by the Quantity attribute of the Products
 * 
 * @author rmr9535 (add your username to this list if you happen to work on this file) 
 */
public class QuantitySort { 

    /**
     * sortByQuantity will sort the Products in a map by quantity, placing them in a new TreeMap. 
     * This had to be created as a separate function in order to sort the map by an attribute
     * of the value, as opposed to the key
     * 
     * @param map the map to be sorted by quantity
     * @return a new map, sorted by the quantity attribtue of the Product values
     */
    public static <K, V extends Comparable<V>> Map<Integer, Product> sortByQuantity(final Map<Integer, Product> map) { 
        
        Comparator<Integer> valueComparator = new Comparator<Integer>() { 

            /** 
             * This function implements compare in order to be passed to the created map and override the natural ordering
             */
            public int compare(Integer k1, Integer k2) { 

                int compare = (int) ( map.get(k1).getQuantity() - map.get(k2).getQuantity() ); 

                // if two Products have the same quantity, they will be listed alphabetically
                if (compare == 0) { return map.get(k1).getName().compareTo(map.get(k2).getName()) ; } 
                else { return compare ; } 
                
            }
        }; 
 
    Map<Integer, Product> sortedByQuantity = new TreeMap<Integer, Product>(valueComparator); 

    sortedByQuantity.putAll(map); 

    return sortedByQuantity; 
  }
    
}
