package ru.focusstart.tomsk;

import org.apache.log4j.Logger;

import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static final int N = 5;
    private static final int M = 5;
    private static final int tN = 2000;
    private static final int tM = 2000;
    private static final int S = 5;
    private static final LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>(S);

    public static void main(String[] args) {
        Producer producer = new Producer();
        Consumer consumer = new Consumer();

        for (int i = 0; i < N; i++) {
            new Thread(producer, "Producer-" + i).start();
        }

        for (int i = 0; i < M; i++) {
            new Thread(consumer, "Customer-" + i).start();
        }
    }

    static class Producer implements Runnable {
        Logger logger = Logger.getLogger("");
        AtomicInteger counter = new AtomicInteger();

        public void run() {
            try {
                while (true) {
                    Thread.sleep(tN);
                    String product = createProduct();
                    logger.info(product + " produced ");
                    deque.put(product);
                    logger.info("added " + product);
                }
            } catch (InterruptedException e) {
                logger.error(e);;
            }
        }

        String createProduct() {
            String product;
            product = "product" + counter.getAndIncrement();
            return product;
        }
    }

    static class Consumer implements Runnable {
        Logger logger = Logger.getLogger("");

        public void run() {
            try {
                while (true) {
//                    try {
                        String product = deque.takeFirst();
                        logger.info("delete " + product);
                        Thread.sleep(tM);
                        logger.info(product + " is used");
//                    } catch (NoSuchElementException ignored) {
//                    }
                }
            } catch (InterruptedException e) {
                logger.error(e);
            }
        }
    }
}