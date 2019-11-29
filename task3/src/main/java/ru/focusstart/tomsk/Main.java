package ru.focusstart.tomsk;

import java.util.*;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {

        Store store = new Store();
        Producer producer = new Producer(store);
        Consumer consumer = new Consumer(store);
        new Thread(producer).start();
        new Thread(producer).start();
        new Thread(producer).start();

        new Thread(consumer).start();
        new Thread(consumer).start();
        new Thread(consumer).start();
    }
}

// Класс Магазин, хранящий произведенные товары
class Store {
    private int product = 0;

    public synchronized void get() {


        System.out.println(Thread.currentThread().getName());
        while (product < 1) {
            try {
                wait();
                System.out.println("wait");
            } catch (InterruptedException e) {
            }
        }
        product--;
        System.out.println("Покупатель купил 1 товар");
        System.out.println("Товаров на складе: " + product);
        notifyAll();
    }


    public synchronized void put() {


        System.out.println(Thread.currentThread().getName());
        while (product >= 3) {
            try {
                wait();
                System.out.println("VAI");
            } catch (InterruptedException e) {
            }
        }
        product++;
        System.out.println("Производитель добавил 1 товар");
        System.out.println("Товаров на складе: " + product);
        notifyAll();
    }
}


// класс Производитель
class Producer implements Runnable {

    Store store;

    Producer(Store store) {
        this.store = store;
    }

    public void run() {
        for (int i = 1; i < 6; i++) {
            store.put();
        }
    }
}

// Класс Потребитель
class Consumer implements Runnable {

    Store store;

    Consumer(Store store) {
        this.store = store;
    }

    public void run() {
        for (int i = 1; i < 6; i++) {
            store.get();
        }
    }
}


//    private static final int N = 5;
//    private static final int M = 5;
//    private static final int tN = 100;
//    private static final int tM = 1000;
//    private static final int S = 5;
//    private static Deque<Integer> deque = new LinkedBlockingDeque<>(S);
//    private static int product = 0;
//
//    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        ProducersTask producersTask = new ProducersTask();
//        CustomersTask customersTask = new CustomersTask();
//
////
//        Thread thread0 = new Thread(producersTask,"Producer0");
//        Thread thread1 = new Thread(producersTask,"Producer1");
//        Thread thread2 = new Thread(customersTask,"Customer2");
//        Thread thread3 = new Thread(customersTask,"Customer3");
//
//        thread0.start();
//        thread1.start();
//        thread2.start();
//        thread3.start();
//
////        ExecutorService producers = Executors.newFixedThreadPool(N);
////        ExecutorService customers = Executors.newFixedThreadPool(M);
////
////
////        for (int i = 0; i <N ; i++) {
////            producers.submit(producersTask);
////        }
////        for (int i = 0; i <M ; i++) {
////            customers.submit(customersTask);
////        }
////
////
////
////        producers.shutdown();
////        customers.shutdown();
//
//    }
//
//    static class ProducersTask implements Runnable {
//
//        @Override
//        public void run() {
//
//            while (true) {
//                try {
//                    Thread.sleep(tN);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                if (deque.offerFirst(product)) {
//                    System.out.println("Add" + Thread.currentThread().getName());
//                } else {
//                    System.out.println("no more space");
//                    try {
//                        Thread.currentThread().notifyAll();
//                        Thread.currentThread().wait();
//                        System.out.println("Produc wait");
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//    }
//
//    static class CustomersTask implements Runnable {
//        @Override
//        public void run() {
//            while (true) {
//                try {
//                    Thread.sleep(tM);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                if (deque.pollFirst() != null) {
//                    System.out.println("delete" + Thread.currentThread().getName());
//
//                } else {
//                    System.out.println("no product");
//                    Thread.currentThread().notifyAll();
//                    System.out.println("CUST wait");
//                }
//            }
//        }
//    }
