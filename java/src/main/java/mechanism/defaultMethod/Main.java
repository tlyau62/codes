package mechanism.defaultMethod;

/**
 * Created by tl on 5/19/17.
 * note
 * - interface method with default implementations
 * - interface vs abstract class
 * -- no state (implicit final) vs has state
 */

interface MyInterface {
    default void print() {
        int value = 10;
        System.out.println("myInterface" + value);
    };
}

public class Main implements MyInterface {

    public static void main(String[] args) {
        new Main().print();
    }

}
