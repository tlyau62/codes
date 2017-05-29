package concurrency;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by tl on 5/29/17.
 * - Locking can guarantee both visibility and atomicity;
 *   volatile variables can only guarantee visibility.
 * - if there's only 1 thread for writing, >1 threads for reading, volatile is useful
 */

class Volatile extends Thread {

    volatile boolean keepRunning = true;

    public void run() {
        while (keepRunning) {}

        System.out.println("Thread terminated.");
    }

    public static void main(String[] args) throws InterruptedException {
        Volatile t = new Volatile();
        t.start();
        Thread.sleep(1000);
        t.keepRunning = false; // state modified in main thread is not visible in t thread
        System.out.println("keepRunning set to false.");
    }
}
