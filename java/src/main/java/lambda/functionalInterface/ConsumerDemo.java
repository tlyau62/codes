package lambda.functionalInterface;

import java.util.function.Consumer;

/**
 * Created by tl on 5/23/17.
 * application: execute-around pattern
 */

class Car {

    public Car() {
        System.out.println("creating");
    }

    public void operation1() {
        System.out.println("op1");
    }

    public void operation2() {
        System.out.println("op2");
    }

    public void close() {
        System.out.println("closing");
    }

}

public class ConsumerDemo {

    public static void main(String[] args) {

        // with lambda (execute-around pattern)
        ConsumerDemo.use(car -> {
            car.operation1(); // not immediately executed
            car.operation2();
        });

    }

    private static void use(Consumer<Car> block) {
        Car car = new Car();
        try {
            // some "always the same" codes before executing
            System.out.println("use car");

            block.accept(car); // operations are executed here

            // some "always the same" codes after executing
        } finally {
            car.close();
        }
    }

}
