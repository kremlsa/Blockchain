package blockchain;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Menu {
    List<BChain> chain = new ArrayList<>();
    int numbersOfZeros;

    public void run() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter how many zeros the hash must start with: ");
        numbersOfZeros = Integer.parseInt(sc.nextLine());
        generateChain(2);

        for(BChain b : chain) {
            System.out.println(b.toString());
        }

    }

    public void generateChain(int size) {
        String prevHash = "0";
        for (int i = 0; i < size; i++) {
            chain.add(new BChain(i+1, prevHash, numbersOfZeros));
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
