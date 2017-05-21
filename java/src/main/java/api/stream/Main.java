package api.stream;

import java.util.stream.Stream;

/**
 * Created by tl on 5/18/17.
 * note
 * 1. avoid mechanism.loop (foreach), use reductions
 */
public class Main {

    private static void splitStrings() {
        Stream.of("user1,pw1", "user2,pw2", "user3,pw3") // a stream of strings
                .map(tpl -> tpl.split(",")) // map each string to array of size 2; a stream of tuples of size 2
                .forEach(tpl -> System.out.printf("username: %s, password: %s\n", tpl[0], tpl[1]));
    }

    public static void main(String[] args) {
        splitStrings();


    }

}
