package com.estore.api.estoreapi.persistence;
import java.util.* ;
import com.estore.api.estoreapi.model.Products.Product;

/**
 * This class is used as a means to sort a map by an attribute of the value, not the key. 
 * In this case, the Value is an object of class Product. This class implements a method 
 * that will allow the inventory to be sorted by the Price attribute of the Products
 * 
 * @author rmr9535 (add your username to this list if you happen to work on this file) 
 */
public class PriceSort { 

    /**
     * sortByPrice will sort the Products in a map by price, placing them in a new TreeMap. 
     * This had to be created as a separate function in order to sort the map by an attribute
     * of the value, as opposed to the key
     * 
     * @param map the map to be sorted by price
     * @return a new map, sorted by the price attribtue of the Product values
     */
    public static <K, V extends Comparable<V>> Map<Integer, Product> sortByPrice(Map<Integer, Product> map) { 
        
        Comparator<Integer> valueComparator = new Comparator<Integer>() { 

            /** 
             * This function implements compare in order to be passed to the created map and override the natural ordering
             */
            public int compare(Integer k1, Integer k2) { 

                if( map.get(k1).getPrice() < map.get(k2).getPrice() ) { return -1 ; }
                else if( map.get(k1).getPrice() > map.get(k2).getPrice() ) { return 1 ; }
                else {
                    // if two Products have the same price, they will be listed alphabetically
                    return map.get(k1).getName().compareTo(map.get(k2).getName()) ; 
                }
            }
        }; 
 
    Map<Integer, Product> sortedByPrice = new TreeMap<Integer, Product>(valueComparator); 

    sortedByPrice.putAll(map); 

    return sortedByPrice; 
  }
    
}
