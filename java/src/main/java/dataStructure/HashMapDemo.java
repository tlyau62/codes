package dataStructure;

import java.util.HashMap;

/**
 * Created by tl on 5/26/17.
 * hashmap - compute the key without value
 */
public class HashMapDemo {

    public static void main(String[] args) {
        HashMap<Integer, Integer> square = new HashMap<>();
        square.put(4, null);
        square.put(5, 25);

        System.out.println(square);

        square.computeIfAbsent(4, integer -> integer * integer);
        System.out.println(square);
    }

}
