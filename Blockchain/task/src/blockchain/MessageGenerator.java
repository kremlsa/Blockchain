package blockchain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessageGenerator implements Runnable {
    List<String> messages = new ArrayList<>(
            List.of("when he was taken gravely ill,",
                    "he forced one to respect him",
                    "and nothing better could invent.",
                    "To others his example is a lesson;",
                    "but, good God, what a bore to sit",
                    "by a sick person day and night, not stirring",
                    "a step away!"));
    ExecutorService executor = Executors.newFixedThreadPool(2);
    private volatile boolean running = true;

    public void run() {
        Menu.addMesage("Pushkin: “My uncle has most honest principles:");
        System.out.println("Pushkin: “My uncle has most honest principles:");
        while (running) {
            //executor.submit(() -> Menu.addMesage(messages.get(rand.nextInt((5 - 1) + 1) + 1)));
            executor.submit(() -> addMesage("1"));
            executor.submit(() -> addMesage("2"));
        }
        executor.shutdownNow();
    }

    public void addMesage(String name) {
        Random rand = new Random();
        try {
            Menu.addMesage(name + messages.get(rand.nextInt((5 - 1) + 1) + 1));
            Thread.sleep(rand.nextInt(2));
        } catch (Exception e) {
            // empty
        }
    }

    public void shutdown() {
        running = false;
    }


}
