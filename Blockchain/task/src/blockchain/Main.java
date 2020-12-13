package blockchain;


import java.io.File;

class Menu {
    int startId = 0;
    String initHash = "0";
    BlockChain bc;
    int numbersOfZeros = 1;

    public void run(Mine mine) {

        bc = Utils.loadBlockChain("chain.db");

        //MessageGenerator mg = new MessageGenerator();
        //mg.start(mine);
        //mine.setMg(mg);
        TransactionGenerator tg = new TransactionGenerator();
        tg.start(mine);
        mine.setTg(tg);
        if (bc == null) {
            BlockChain bcMT = new BlockChain(startId, initHash, numbersOfZeros, true);
            bc = mine.generateChainMT(bcMT, 15, numbersOfZeros);
            Utils.saveBlockChain(bc);
        } else {
            bc = mine.generateChainMT(bc, 15, numbersOfZeros);
            Utils.saveBlockChain(bc);
        }
        //mg.stop();
        tg.stop();
        System.out.println(bc.printFive());

    }
}


public class Main {
    public static void main(String[] args)  {
        checkKeys();
        Mine mine = new Mine();
        Menu menu = new Menu();
        menu.run(mine);
    }

    static void checkKeys() {

        File pubKey = new File("KeyPair/publicKey");
        File privKey = new File("KeyPair/privateKey");

        if (!pubKey.exists() || !privKey.exists()) {
            Utils.generateKeys();
        }
    }

}
