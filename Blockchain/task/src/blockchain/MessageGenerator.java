package blockchain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessageGenerator {
    ExecutorService executor;
    MessageThread sarah;
    MessageThread nick;
    MessageThread tom;

    void start(Mine mine) {
        sarah = new MessageThread("Sarah", mine);
        nick = new MessageThread("Nick", mine);
        tom =new MessageThread("Tom", mine);
        executor = Executors.newFixedThreadPool(4);
        executor.submit(sarah);
        executor.submit(nick);
        executor.submit(tom);
    }

    void stop() {
        sarah.stop();
        nick.stop();
        tom.stop();
        executor.shutdownNow();
    }

}

class MessageThread implements Runnable {

    String name;
    Mine mine;
    boolean isRun = true;
    private Random rand = new Random();
    List<String> messages = new ArrayList<>(
            List.of("Hi! I'm here.",

                    "What's up?!",

                    "It's not fair!",

                    "Anyway, thank you for this amazing chat.",

                    "You're welcome :)",

                    "Hey, nice chat!!"));

    public MessageThread (String name, Mine mine) {
        this.name = name;
        this.mine = mine;
    }

    public void addMesage(String name) {
        mine.addMesage(name + " : " + messages.get(rand.nextInt((5 - 1) + 1) + 1));
    }

    void stop() {
        isRun = false;
    }

    @Override
    public void run() {
        while (isRun) {
            addMesage(name);
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}