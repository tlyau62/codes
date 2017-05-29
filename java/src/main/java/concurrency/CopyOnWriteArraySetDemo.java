package concurrency;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by tl on 5/28/17.
 * note:
 * - thread-safe, compare to hashset
 * - fast on reading, slow on writing(copy whole set when adding each time)
 *
 * ref:
 * - http://ifeve.com/java-copy-on-write/
 * - https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/CopyOnWriteArraySet.html
 */
public class CopyOnWriteArraySetDemo {

    public static void main(String[] args) {
        Set<String> set = new CopyOnWriteArraySet<>();

        set.add("a");
        set.add("b");

        for (String s : set) {
            System.out.println(s);
        }
    }

}
