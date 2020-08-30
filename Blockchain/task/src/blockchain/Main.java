package blockchain;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.*;

class Menu {
    Chain chain = new Chain();
    int numbersOfZeros = 5;
    ExecutorService executor;
    static List<String> messages = new CopyOnWriteArrayList<>();
    MessageGenerator generator = new MessageGenerator();

    public void run() {
        File f = new File("chain.db");
        if (f.isFile() && f.canRead()) {
            loadChain("chain.db");
            validate();
            //System.out.println(validate());
        }
        //Scanner sc = new Scanner(System.in);
        //System.out.print("Enter how many zeros the hash must start with: ");
        //numbersOfZeros = Integer.parseInt(sc.nextLine());
        //generateChain(10);
        //MessageGenerator generator = new MessageGenerator();
        new Thread(generator).start();


        generateChainMT(10);
        int id = chain.getId();
        generator.shutdown();
        executor.shutdown();
        for (int i = 0; i < 5; i++) {
            System.out.println(chain.getBlock(i).toString());
        }
        saveChain();

    }

    public static void addMesage (String message) {
            messages.add(message);

    }

    public Block chainBlock (int idBlock, String prevHash) {
        long startTime = System.currentTimeMillis();
        long timeStamp = new Date().getTime();
        String forhash = idBlock + " " + timeStamp + " " + prevHash;
        String[] calc = utils.calcHashMagic(forhash, numbersOfZeros);
        String magicNumber = calc[0];
        String hash = calc[1];
        long endTime = System.currentTimeMillis();
        long genTime = (endTime - startTime) ;
        return new Block(idBlock, timeStamp, prevHash, hash, magicNumber, genTime);
    }


    public void generateChain(int size) {
        int id = chain.getId();
        String prevHash = id == 0 ? "0" : chain.getBlock(id - 1).getHash();
        for (int i = 0; i < size; i++) {
            chain.add(chainBlock(id+1, prevHash));
            prevHash = chain.getBlock(id).getHash();
            id++;
        }
    }

    public Block chainBlockMT (int idBlock, String prevHash) {
        long startTime = System.currentTimeMillis();
        long timeStamp = new Date().getTime();
        String forhash = idBlock + " " + timeStamp + " " + prevHash;
        //String[] calc = utils.calcHashMagic(forhash, numbersOfZeros);
        String calc[] = multiThreading(forhash);
        String magicNumber = calc[0];
        String hash = calc[1];
        long endTime = System.currentTimeMillis();
        long genTime = (endTime - startTime) ;
        Block block = new Block(idBlock, timeStamp, prevHash, hash, magicNumber, genTime);
        block.setMinerId(calc[2]);
        if (idBlock != 1) {
            synchronized (this) {
                block.setData(messages);
                messages.clear();
            }
        }
        executor.shutdown();
        return block;
    }

    public void generateChainMT(int size) {
        int id = chain.getId();
        String prevHash = id == 0 ? "0" : chain.getBlock(id - 1).getHash();
        for (int i = 0; i < size; i++) {
            chain.add(chainBlockMT(id+1, prevHash));
            prevHash = chain.getBlock(id).getHash();
            id++;
        }

    }

    public String[] multiThreading(String forhash) {
        List<Future<String[]>> futures = new ArrayList<>();
        executor = Executors.newFixedThreadPool(4);
        String[] magic = new String[3];

            for (int i = 1; i < 5; i++) {
                futures.add(executor.submit(new MinerThread(forhash, numbersOfZeros, i)));
            }
            boolean isDone = false;
            while (!isDone) {
                for (Future<String[]> future : futures) {
                    if (future.isDone()) {
                        try {
                            magic = future.get();
                            isDone = true;
                            break;
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        for (Future<String[]> future : futures) {
            future.cancel(true);
        }
        return magic;
    }

    public boolean validate() {
        String prevHash = chain.getBlock(0).getHash();
        for(int i = 1; i < chain.getId(); i++) {
            if(!chain.getBlock(i).getHashPrev().equals(prevHash)) {
                System.out.println(prevHash + " " + chain.getBlock(i).toString());
                return false;
            }
            prevHash = chain.getBlock(i).getHash();
        }
        return true;
    }

    public void saveChain() {
        try {
            File file = new File("chain.db");
            FileOutputStream fs = new FileOutputStream(file);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(this.chain);
            os.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void loadChain(String input) {
        try {
            File file = new File(input);
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(file));
            this.chain = (Chain) is.readObject();
            is.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}


public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.run();
    }
}
