package mechanism.loop;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by tl on 5/19/17.
 * 3 types of loops to find max
 */

interface Code {
    void execute();
}

public class Main {

    // not thread safe
    private static void externLoop(List<Integer> nums) {
        if (nums.size() == 0) {
            System.out.println("empty list");
            return;
        }

        Integer max = nums.get(0);

        for (int i = 1; i < nums.size(); i++) {
            if (nums.get(i) > max)
                max = nums.get(i);
        }

        System.out.println(max);
    }

    // cause stack overflow if the list is too large
    private static void recursion(Integer max, Iterator<Integer> iterator) {

        if (!iterator.hasNext()) {
            System.out.println(max == null ? "empty list" : max);
            return;
        }

        Integer next = iterator.next();
        recursion(max == null ? next : Math.max(max, next), iterator);

    }

    private static void stream(List<Integer> nums) { // internal mechanism.loop
//        optional<Integer> max = nums.stream().reduce(Integer::max); // java.util.optional<T> reduce(java.util.function.BinaryOperator<T> binaryOperator);

        Optional<Integer> max = nums.stream().max(Comparator.naturalOrder());

//        OptionalInt max = nums.stream().mapToInt(Integer::intValue).max(); // convert to intStream

        // :: - method reference

        System.out.println(max.isPresent() ? max.get(): "empty list");
    }

    private static void parallelStream(List<Integer> nums) {
        Optional<Integer> max = nums.parallelStream()
                .peek(integer -> System.out.println(integer + ": " + Thread.currentThread()))
                .max(Comparator.naturalOrder());

        System.out.println(max.isPresent() ? max.get(): "empty list");
    }

    private static void timer(Code code) {
        Instant start = Instant.now();
        code.execute();
        Instant end = Instant.now();
        System.out.println(Duration.between(start, end));
    }

    public static void main(String[] args) {
        Random rand = new Random();
        List<Integer> list = IntStream.rangeClosed(1, 10000)
                .boxed()
                .map(i -> rand.nextInt())
                .collect(Collectors.toList());

        timer(() -> stream(list));
        timer(() -> parallelStream(list));
//        timer(() -> externLoop(list));
//        timer(() -> recursion(null, list.iterator()));
    }

}
