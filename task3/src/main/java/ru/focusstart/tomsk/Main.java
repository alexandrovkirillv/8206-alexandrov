package ru.focusstart.tomsk;

import java.util.*;
import java.util.concurrent.*;

public class Main {

    private static final int N=5;
    private static final int M=5;
    private static final int tN = 1000;
    private static final int tM = 1000;
    private static final int S = 5;
    private static Deque<Integer> deque = new LinkedBlockingDeque<>(S);
    private static int product = 0;

    public static void main(String[] args) {
        ProducersTask producersTask = new ProducersTask();
        CustomersTask customersTask = new CustomersTask();

        while (true) {
            for (int i = 0; i < N; i++) {
                new Thread(producersTask).start();
            }
            for (int i = 0; i < M; i++) {
                new Thread(customersTask).start();
            }
        }
    }

    static class ProducersTask implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(tN);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (deque.offerFirst(product)) {
                System.out.println("Add");
            } else {
                System.out.println("no more space");
                try {
                    Thread.sleep(250);
                } catch (InterruptedException ignored) {
                }
            }
        }
    }

    static class CustomersTask implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(tM);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (deque.pollFirst() != null) {
                System.out.println("delete");

            } else {
                System.out.println("no product");
                try {
                    Thread.sleep(250);
                } catch (InterruptedException ignored) {
                }
            }
        }
    }
}