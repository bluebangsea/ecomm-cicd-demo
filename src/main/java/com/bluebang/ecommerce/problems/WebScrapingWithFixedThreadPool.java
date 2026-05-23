package com.bluebang.ecommerce.problems;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class WebScrapingWithFixedThreadPool {
    static void main(String[] args) throws ExecutionException, InterruptedException {

        List<Future<Void>> futures = new ArrayList<>();

        ExecutorService es = Executors.newFixedThreadPool(5);
        //ExecutorService es = Executors.newCachedThreadPool();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
           futures.add(es.submit(new WebScrapingFTP()));
        }

        for (Future<Void> future : futures) {
            future.get();
        }
        long end = System.currentTimeMillis();
        System.out.println("Time taken: " + (end - start) + " ms");

        es.shutdown();
    }
}

class WebScrapingFTP implements Callable<Void> {

    @Override
    public Void call() {
        try {
            System.out.println("Scraping Website for Thread: " + Thread.currentThread().getName());
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
