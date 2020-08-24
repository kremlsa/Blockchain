package blockchain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

class Menu {
    List<BChain> chain = new ArrayList<>();
    int numbersOfZeros;

    public void run() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter how many zeros the hash must start with: ");
        numbersOfZeros = Integer.parseInt(sc.nextLine());
        generateChain(5);

        for(BChain b : chain) {
            System.out.println(b.toString());
        }

    }

    public BChain chainBlock (int idBlock, String prevHash) {
        long startTime = System.currentTimeMillis();
        long timeStamp = new Date().getTime();
        String forhash = idBlock + " " + timeStamp + " " + prevHash;
        String[] calc = utils.calcHashMagic(forhash, numbersOfZeros);
        String magicNumber = calc[0];
        String hash = calc[1];
        long endTime = System.currentTimeMillis();
        long genTime = (endTime - startTime) ;
        return new BChain(idBlock, timeStamp, prevHash, hash, magicNumber, genTime);
    }

    public void generateChain(int size) {
        String prevHash = "0";
        for (int i = 0; i < size; i++) {
            chain.add(chainBlock(i+1, prevHash));
            prevHash = chain.get(i).getHash();
        }
    }
}


public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.run();
    }
}
