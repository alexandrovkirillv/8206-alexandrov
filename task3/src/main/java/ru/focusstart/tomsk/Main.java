package ru.focusstart.tomsk;

import org.apache.log4j.Logger;

public class Main {

    private static final int N = 5;
    private static final int M = 5;
    private static final int tN = 100;
    private static final int tM = 100;
    private static final int S = 5;

    public static void main(String[] args) {
        Store store = new Store();
        Producer producer = new Producer(store);
        Consumer consumer = new Consumer(store);

        for (int i = 0; i < N; i++) {
            new Thread(producer, "Producer-" + i).start();
        }

        for (int i = 0; i < M; i++) {
            new Thread(consumer, "Customer-" + i).start();
        }
    }

    static class Store {
        private int product = 0;
        Logger logger = Logger.getLogger("");

        synchronized void get() throws InterruptedException {
            while (product < 1) {
                try {
                    wait();
                } catch (InterruptedException ignored) {
                }
            }
            Thread.sleep(tM);
            product--;
            logger.info("delete product #" + product);
            notifyAll();
        }

        synchronized void put() throws InterruptedException {
            while (product >= S) {
                try {
                    wait();
                } catch (InterruptedException ignored) {
                }
            }
            Thread.sleep(tN);
            product++;
            logger.info("add product #" + product);
            notifyAll();
        }
    }

    static class Producer implements Runnable {
        Store store;

        Producer(Store store) {
            this.store = store;
        }

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
        Store store;

        Consumer(Store store) {
            this.store = store;
        }

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