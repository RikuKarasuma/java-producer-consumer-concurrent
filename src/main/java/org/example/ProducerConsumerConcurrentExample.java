package org.example;

import java.util.PriorityQueue;
import java.util.Queue;

public class ProducerConsumerConcurrentExample {

    private static final Queue<String> produced = new PriorityQueue<>();

    private static final Object lock = new Object();

    public static void main(String[] args) {
        System.out.println("Hello world!");

        final Runnable producer = () -> {

            int amount_so_far = 0;
            while (true) {
                synchronized (lock) {
                    produced.add("# " + amount_so_far);
                }
                amount_so_far ++;
            }
        };

        final Runnable consumer = () -> {
            while (true) {
                synchronized (lock) {
                    if (!produced.isEmpty()) {
                        final var producedStr = produced.poll();
                        System.out.println("Produced String was: " + producedStr);
                    }
                }
            }
        };

        Thread one = new Thread(producer);
        Thread two = new Thread(consumer);
        one.setName("Producer");
        one.start();
        one.setName("Consumer");
        two.start();
    }
}