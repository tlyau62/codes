package api.synchronization;

import java.util.concurrent.CompletableFuture;

/**
 * Created by tl on 5/23/17.
 * ref: https://stackoverflow.com/questions/574240/is-there-an-advantage-to-use-a-synchronized-method-instead-of-a-synchronized-blo
 */
public class Method {

    private static Long counter = 0L;

    public static void main(String[] args) {

        CompletableFuture<Void> future = CompletableFuture.runAsync(Method::addTo10000);

        CompletableFuture<Void> future2 = CompletableFuture.runAsync(Method::addTo10000);

        CompletableFuture<Void> future3 = CompletableFuture.runAsync(Method::addTo10000);

        CompletableFuture.allOf(future, future2, future3).join();

        System.out.println(counter);

        // or runAsync(...).thenRun(...).thenRun(...)
    }

    private synchronized static void addTo10000() {
        for (int i = 0; i < 10000; i++) {
            counter++;
        }
    }
}
