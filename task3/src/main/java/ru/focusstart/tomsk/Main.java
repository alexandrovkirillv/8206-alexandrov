package ru.focusstart.tomsk;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private final static int N = 5;
    private final static int M = 5;
    public static void main(String[] args) {

        ExecutorService producers = Executors.newFixedThreadPool(N);
        ExecutorService customers = Executors.newFixedThreadPool(M);
        Semaphore semaphore = new Semaphore(0);
        AtomicInteger storage = new AtomicInteger();

        Runnable putResource = () -> {
            try {
                semaphore.acquire();
                if(storage.get()==5){
                    Thread.sleep(1000);
                }
                Thread thread = Thread.currentThread();
                Thread.sleep(1000);
                storage.getAndIncrement();
                System.out.println(thread.getName());
                System.out.println("Storage = " + storage.get());

                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Runnable getResource = () ->{
            try {
                semaphore.acquire();
                if (storage.get() == 0){
                    Thread.sleep(3000);
                }
                Thread thread = Thread.currentThread();
                Thread.sleep(10);
                storage.getAndDecrement();
                System.out.println(thread.getName());
                System.out.println("Storage = " + storage.get());

                semaphore.release();
                System.out.println("===========");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        semaphore.release(2);
        for (int i = 0; i < 7; i++) {
            producers.submit(putResource);
            customers.submit(getResource);
        }

        for (int i = 0; i < M; i++) {

        }

        producers.shutdown();
        customers.shutdown();
    }


//    private static void createResource() throws InterruptedException {
//
//        System.out.println("Start making resource");
//        Thread.sleep(1000);
//        System.out.println("Resource done");
//
//    }
}

