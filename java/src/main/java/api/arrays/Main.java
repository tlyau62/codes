package api.arrays;

import java.util.Arrays;

/**
 * Created by tl on 5/21/17.
 */
public class Main {

    public static void main(String[] args) {
        String[] names = {"peter", "john"};

        String[] strings = Arrays.asList(names).toArray(new String[names.length]);
        for (String string : strings) {
            System.out.println(string);
        }
    }

}
