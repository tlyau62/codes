package lambda.functionalInterface;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * Created by tl on 5/23/17.
 * application: execute-around pattern for create new object
 */

class Food {

    private int quantity;

    public Food(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Food{" +
                "quantity=" + quantity +
                '}';
    }
}

public class SupplierDemo {

    public static void main(String[] args) {

        // given new Food() requires lots of time to create

        give2(false, new Food(10)); // slow, not acceptable

        give3(false, 10);

        give(false, () -> {
            System.out.println("creating food"); // some actions must be performed every time Food needs to be created
            return new Food(10);
        });
    }

    private static void give2(boolean enough, Food food) {
        if (!enough) {
            System.out.println(food);
        }
    }

    private static void give(boolean enough, Supplier<Food> foodSupplier) {
        Food food1 = null, food2 = null;
        if (!enough) {
            System.out.println(food1 = foodSupplier.get());
        }

        System.out.println("send food fail...");
        System.out.println("resend...");

        if (!enough) {
            System.out.println(food2 = foodSupplier.get());
        }

        System.out.println("food1 and food2 refer to same object? " + (food1 == food2)); // no
    }

    private static void give3(boolean enough, int quantity) {
        if (!enough) {
            System.out.println("creating food");
            System.out.println(new Food(quantity));
        }

        System.out.println("send food fail...");
        System.out.println("resend...");

        if (!enough) {
            System.out.println("creating food");
            System.out.println(new Food(quantity));
        }
    }
}
