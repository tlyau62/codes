package lambda.optional;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.function.Function;

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


        System.out.println(Optional.ofNullable(search(integers, 4))
                .orElse(-1));

        System.out.println(Optional.ofNullable((Integer)null)
                .map(integer -> "A")
                .orElse("B")); // output B (Optional.empty())

        System.out.println(Optional.ofNullable((Integer)null)
                .map(integer -> "A")
                .orElseGet(() -> {
                    System.out.println("or else");
                    return "B";
                }));
    }

}
