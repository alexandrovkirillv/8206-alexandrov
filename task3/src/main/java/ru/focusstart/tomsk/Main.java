package ru.focusstart.tomsk;

import org.apache.log4j.Logger;

import java.util.Deque;
import java.util.concurrent.LinkedBlockingDeque;

public class Main {

    private static final int N = 5;
    private static final int M = 5;
    private static final int tN = 2000;
    private static final int tM = 2000;
    private static final int S = 5;

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

    static class Store {
        private static Deque<String> deque = new LinkedBlockingDeque<>(S);
        int counter = 0;
        Logger logger = Logger.getLogger("");

        void put() throws InterruptedException {
            if (deque.size() < S) {
                String product = createProduct();
                logger.info(product + " produced ");
                deque.offerFirst(product);
                logger.info("add " + product);
                Thread.sleep(tN);
            } else {
                logger.info("no more space");
                Thread.sleep(tN);
            }
        }

        void get() throws InterruptedException {
            if (!deque.isEmpty()) {
                String product = deque.pollLast();
                logger.info("delete " + product);
                logger.info(product + " is used");
                Thread.sleep(tM);
            } else {
                logger.info("no product");
                Thread.sleep(tM);
            }
        }

        public String createProduct() {
            String product;
            counter++;
            product = "product" + counter;
            return product;
        }
    }

    static class Producer implements Runnable {
        Store store = new Store();

        public void run() {
            while (true) {
                try {
                    store.put();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Consumer implements Runnable {
        Store store = new Store();

        public void run() {
            while (true) {
                try {
                    store.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}