package com.estore.api.estoreapi.persistence;
import java.util.* ;
import com.estore.api.estoreapi.model.Products.Product;

/**
 * This class is used as a means to sort a map by an attribute of the value, not the key. 
 * In this case, the Value is an object of class Product. This class implements a method 
 * that will allow the inventory to be sorted by the Name attribute of the {@link Product products}
 * 
 * @author rmr9535 (add your username to this list if you happen to work on this file) 
 */
public class NameSort { 

    /**
     * sortByName will sort the {@link Product products} in a map by name, placing them in a new TreeMap. 
     * This had to be created as a separate function in order to sort the map by an attribute
     * of the value, as opposed to the key
     * 
     * @param map the map to be sorted by name
     * @return a new map, sorted by the name attribtue of the Product values
     */
    public static <K, V extends Comparable<V>> Map<Integer, Product> sortByName(Map<Integer, Product> map) { 
        
        Comparator<Integer> valueComparator = new Comparator<Integer>() { 
            
            /** 
             * This function implements compare in order to be passed to the created map and override the natural ordering
             */
            public int compare(Integer k1, Integer k2) { 

                int compare = map.get(k1).getName().compareTo(map.get(k2).getName()) ;  

                // if two Products have the same name, they will be listed by price
                // Note: it is not technically possible, by the creation of Products through cURL, for
                // multiple products to have the same name
                if (compare == 0) { return (int) Math.ceil ( ( map.get(k1).getPrice() - map.get(k2).getPrice() ) ); } 
                else { return compare ; } 
                
            }
        }; 
 
    Map<Integer, Product> sortedByName = new TreeMap<Integer, Product>(valueComparator); 

    sortedByName.putAll(map); 

    return sortedByName; 
  }
    
}
