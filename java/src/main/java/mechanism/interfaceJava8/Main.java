package mechanism.interfaceJava8;

/**
 * Created by tl on 5/19/17.
 * note
 * - interface method with default implementations
 * - interface vs abstract class
 * -- no state (implicit final) vs has state
 */

interface MyInterface {
    static void staticPrint() {
        System.out.println("staticPrint");
    };

    default void defaultPrint() {
        System.out.println("defaultPrint");
    };

    default void overridePrint() {
        System.out.println("overridePrint");
        print();
    }

    void print();
}

public class Main implements MyInterface {

    public static void main(String[] args) {
        Main main = new Main();
        main.defaultPrint();
        main.overridePrint();

        MyInterface.staticPrint(); // useful in ult
    }

    @Override
    public void print() {
        System.out.println("print impl");
    }

}
