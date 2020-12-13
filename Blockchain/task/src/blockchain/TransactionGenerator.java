package blockchain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TransactionGenerator {
    ExecutorService executor;
    TransactionThread user1;
    TransactionThread user2;
    TransactionThread user3;
    TransactionThread user4;


    void start(Mine mine) {
        user1 = new TransactionThread("user1", 1, mine, this);
        user2 = new TransactionThread("user2", 2, mine, this);
        user3 = new TransactionThread("user3", 3, mine, this);
        user4 = new TransactionThread("user4", 4, mine, this);

        executor = Executors.newFixedThreadPool(4);
        executor.submit(user1);
        executor.submit(user2);
        executor.submit(user3);
        executor.submit(user4);
    }

    void stop() {
        user1.stop();
        user2.stop();
        user3.stop();
        user4.stop();
        executor.shutdownNow();
    }

    public void addCredits(int minerId, int credits) {
        switch (minerId) {
            case 1:
                user1.addCredits(credits);
                break;
            case 2:
                user2.addCredits(credits);
                break;
            case 3:
                user3.addCredits(credits);
                break;
            case 4:
                user4.addCredits(credits);
                break;
        }
    }

}

class TransactionThread implements Runnable {

    String name;
    int credits;
    int id;
    Mine mine;
    boolean isRun = true;
    private Random rand = new Random();
    TransactionGenerator tg;


    public TransactionThread (String name, int id, Mine mine, TransactionGenerator tg) {
        this.name = name;
        this.mine = mine;
        this.credits = 100;
        this.id = id;
        this.tg = tg;
    }

    public void addMesage(String name) {
        int sum = rand.nextInt((50) + 5);
        int userId = 1;
        while (true) {
            userId = rand.nextInt(5);
            if (userId != 0 && userId != id) break;
        }
        if (credits > sum) {
            mine.addMesage(name + " sent " + sum + " VC to miner" + userId);
            credits -= sum;
            tg.addCredits(userId, sum);
        }
    }

    public void addCredits(int income) {
        this.credits += income;
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