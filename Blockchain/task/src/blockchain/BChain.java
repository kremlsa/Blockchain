package blockchain;

import java.util.Date;

public class BChain {
    private final String name = "Block:";
    private final int id;
    long timeStamp;
    String hashPrev;
    String hash;
    int numbersOfZeros;
    String magicNumber;

    public BChain (int id, String hashPrev, int numbersOfZeros) {
        this.id = id;
        this.timeStamp = new Date().getTime();
        this.hashPrev = hashPrev;
        this.numbersOfZeros = numbersOfZeros;
        String forhash = this.name + " " + this.id + " " + this.timeStamp + " " + this.hashPrev;
        this.magicNumber = utils.calchash(forhash, numbersOfZeros)[0];
        this.hash = utils.calchash(forhash, numbersOfZeros)[1];
    }

    public String getHashPrev() {
        return hashPrev;
    }

    public String getHash() {
        return hash;
    }

    public String toString() {
        return name + "\n" +
                "Id: " + id + "\n" +
                "Timestamp: " +  timeStamp +"\n" +
                "Magic number: " + hashPrev + "\n" +
                "Hash of the previous block:\n" + hashPrev + "\n" +
                "Hash of the block:\n" + hash + "\n";
    }
}
