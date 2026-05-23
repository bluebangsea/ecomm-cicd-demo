package com.bluebang.ecommerce.problems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;

public class AddArrayMultiThreadClient {
    static void main(String[] args) throws ExecutionException, InterruptedException {

        Long[] arr = {2L,3L,6L,4L,6L,88L,4L,3L,45L,6L,8L,999L,7L,6L,45L,44L,57L,8L,56L,43L,2L,3L,6L,4L,6L,88L,4L,3L,45L,6L,8L};
        Long sum = Arrays.stream(arr).reduce(Long::sum).get();
        System.out.println("Sum:" + sum);

        int noOfProcessors = Runtime.getRuntime().availableProcessors();
        System.out.println("Num of processor:" + noOfProcessors);
        List<List<Long>> chunks = new ArrayList<>();

        int chunkSize = (int) Math.ceil((double) arr.length / noOfProcessors);

        for (int i = 0; i < arr.length; i += chunkSize) {
            int end = Math.min(arr.length, i + chunkSize);

            List<Long> chunk = new ArrayList<>();
            for (int j = i; j < end; j++) {
                chunk.add(arr[j]);
            }
            chunks.add(chunk);
        }

        List<Future<Long>> futures = new ArrayList<>();
        ExecutorService es = Executors.newFixedThreadPool(noOfProcessors);
        for (List<Long> chunk: chunks) {
            futures.add(es.submit(new AddArray(chunk)));
        }

        Long finalSum = 0L;
        for(Future<Long> future : futures) {
            finalSum = finalSum + future.get();
        }

        System.out.println("Final Sum:" + finalSum);

        es.shutdown();

    }
}


class AddArray implements Callable<Long> {

    List<Long> chunk;

    AddArray(List<Long> chunk) {
        this.chunk = chunk;
    }

    @Override
    public Long call() throws Exception {
        System.out.println("Thread : " + Thread.currentThread().getName());
        Optional<Long> sum = chunk.stream().reduce(Long::sum);
        return sum.get();
    }
}