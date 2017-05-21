package api.CompletableFuture;

import java.util.concurrent.CompletableFuture;

/**
 * Created by tl on 5/21/17.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> sleep(5000, 1))
                .whenComplete((r, ex) -> System.out.println("done: 1"));

        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> sleep(1000, 2))
                .whenComplete((r, ex) -> System.out.println("done: 2"));

        future1.get();
        future2.get();
    }

    private static void sleep(long second, int id) {
        try {
            Thread.sleep(second);
            System.out.println("wake: " + id);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
