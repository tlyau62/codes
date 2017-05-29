package dataStructure;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * singletonMap
 * - an immutable map with only 1 key-value pair
 */
public class SingletonMap {

    public static void main(String[] args) {
        Map<String, Integer> map = Collections.singletonMap("key1", 100);
        // map.put("key2", 200); throws UnsupportedOperationException
        // map.replace("key1", 200);
        System.out.println(map);

        HashMap<String, Integer> map2 = new HashMap<>();
        map2.put("key1", 100);
        map2.put("key2", 200);
        System.out.println(map2);
    }

}
