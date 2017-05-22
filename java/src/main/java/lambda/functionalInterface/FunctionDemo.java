package lambda.functionalInterface;

import java.util.function.Function;
import java.util.stream.Stream;

/**
 * the use of apply and reduce(chain) multiple Functions into one
 */
public class FunctionDemo {

    private class Car {

        private int posX;
        private int posY;

        public Car(int posX, int posY) {
            this.posX = posX;
            this.posY = posY;
        }

        public int getPosX() {
            return posX;
        }

        public Car setPosX(int posX) {
            this.posX = posX;
            return this;
        }

        public int getPosY() {
            return posY;
        }

        public Car setPosY(int posY) {
            this.posY = posY;
            return this;
        }

        @Override
        public String toString() {
            return "Car{" +
                    "posX=" + posX +
                    ", posY=" + posY +
                    '}';
        }

        public void move(Function<Car, Car>... movements) {
            Stream.of(movements)
                    .reduce(car -> car, (movement1, movement2) -> movement1.andThen(movement2))
                    .apply(this); // mv1.andThen(mv2).andThen(mv3)...apply(this)

            /* above
            calculate sum - at first, a is 0, b is the 1st number of numbers
            Stream.of(numbers)
                  .reduce(0, (a, b) -> a + b)
                  .sum();
             */
        }


    }

    public static void main(String[] args) {
//        new FunctionDemo.Color(100, 100, 100);
        FunctionDemo functionDemo = new FunctionDemo();
        Car car = functionDemo.new Car(0, 0);

        Function<Car, Car> up = car1 -> car1.setPosY(car1.getPosY() + 10); // get a Car, return a Car
        Function<Car, Car> right = car1 -> car1.setPosX(car1.getPosX() + 10);

        car.move(up, right, right, up);
        System.out.println(car);
    }

}
