package lambda.optional;

import java.util.*;

/**
 * Created by tl on 5/23/17.
 * Optional class
 */
public class Demo {

    // not using Optional
    private static Integer search(List<Integer> list, int target) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(target)) {
                return i;
            }
        }
        return null;
    }

    // with Optional
    private static Optional<Integer> searchOptional(List<Integer> list, int target) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(target)) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }

    public static void main(String[] args) {
        List<Integer> integers = Arrays.asList(1, 2, 4, 6, 10);

        System.out.println(searchOptional(integers, 4).orElse(-1)); // 2
        System.out.println(searchOptional(integers, 3).orElse(-1)); // -1
        System.out.println(searchOptional(integers, 3)
                .map((integer -> "index: " + integer))
                .orElse("not found")); // type changes


        // not use in this way
        System.out.println(Optional.ofNullable(search(integers, 4))
                .orElse(-1));
    }

}
