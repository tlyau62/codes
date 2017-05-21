package mechanism.anonymousClass;

import java.util.ArrayList;

/**
 * Created by tl on 5/20/17.
 * anonymousClass: declare and instantiate a class at the same time
 * http://stackoverflow.com/questions/924285/efficiency-of-java-double-brace-initialization
 */
public class Main {

    public static void main(String[] args) {
        ArrayList<Integer> integers = new ArrayList<Integer>() { // create a new class without name and ini it
            @Override
            public int size() {
                return 1;
            }

            public int size2() {
                return 2;
            }
        };

        System.out.println(integers.size()); // return 1
//        integers.size2(); size2() can't be recalled, since integers is of ArrayList type, but size2() is of unnamed class

        ArrayList<Integer> integers1 = new ArrayList<Integer>() {{ add(1); add(2); }}; // inner brace is the instance initialization block
        for (Integer integer : integers1) {
            System.out.println(integer);
        }

//        new String() {...} final class can't be derived
    }

}
