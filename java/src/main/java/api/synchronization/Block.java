package api.synchronization;

import java.util.concurrent.CompletableFuture;

/**
 * Created by tl on 5/24/17.
 */
public class Block {

    private static Long counter = 0L;

    private static final Object monitor = new Object();

    public static void main(String[] args) {

        // sync within one method
        CompletableFuture<Void> future = CompletableFuture.runAsync(Block::addTo10000),
                future2 = CompletableFuture.runAsync(Block::addTo10000),
                future3 = CompletableFuture.runAsync(Block::addTo10000);

        CompletableFuture.allOf(future, future2, future3).join();

        System.out.println(counter);

        // sync within >1 methods
        counter = 0L;

        future = CompletableFuture.runAsync(Block::addTo10000);
        future2 = CompletableFuture.runAsync(Block::addTo10000B);
        future3 = CompletableFuture.runAsync(Block::addTo10000C);

        CompletableFuture.allOf(future, future2, future3).join();

        System.out.println(counter);

    }

    private static void addTo10000() {
        synchronized (monitor) {
            for (int i = 0; i < 10000; i++) {
                counter++;
            }
        }
    }

    private static void addTo10000B() {
        synchronized (monitor) {
            for (int i = 0; i < 10000; i++) {
                counter++;
            }
        }
    }

    private static void addTo10000C() {
        synchronized (monitor) {
            for (int i = 0; i < 10000; i++) {
                counter++;
            }
        }
    }
}
