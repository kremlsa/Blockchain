package blockchain;

import java.util.ArrayList;

import java.util.List;

import java.util.Random;

import java.util.concurrent.ExecutorService;

import java.util.concurrent.Executors;

public class MessageGenerator implements Runnable {

    Main.Menu menu;

    List<String> messages = new ArrayList<>(

            List.of("Hi! I'm here.",

                    "What's up?!",

                    "It's not fair!",

                    "Anyway, thank you for this amazing chat.",

                    "You're welcome :)",

                    "Hey, nice chat!!"));

    ExecutorService executor;

    private volatile boolean running = true;

    private Random rand = new Random();

    public MessageGenerator (Main.Menu menu) {

        this.menu = menu;

        executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    }

    @Override

    public void run() {

        menu.addMesage("Tom: I'm first!");

        while (running) {

//executor.submit(() -> Menu.addMesage(messages.get(rand.nextInt((5 - 1) + 1) + 1)));

            executor.submit(() -> addMesage("Sarah"));
            executor.submit(() -> addMesage("Nick"));
            executor.submit(() -> addMesage("Tom"));

        }

        executor.shutdownNow();

    }

    public void addMesage(String name) {

        try {

//menu.addMesage(name + messages.get(rand.nextInt((5 - 1) + 1) + 1));

            menu.addMesage(name + " : " + messages.get(rand.nextInt((5 - 1) + 1) + 1));

            Thread.sleep(rand.nextInt(1));

        } catch (Exception e) {

// empty

        }

    }

    public void shutdown() {

        running = false;

    }

}