package blockchain;

import java.io.Serializable;
import java.util.Date;

public class BChain implements Serializable {
    String name = "Block:";
    int id;
    long timeStamp;
    String hashPrev;
    String hash;
    String magicNumber;
    long genTime;

   public BChain (int id, long timeStamp, String hashPrev, String hash,
                   String magicNumber, long genTime) {
        this.id = id;
        this.timeStamp = timeStamp;
        this.hashPrev = hashPrev;
        this.magicNumber = magicNumber;
        this.hash = hash;
        this.genTime = genTime;
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
                "Timestamp:" +  timeStamp +"\n" +
                "Magic number: " + magicNumber + "\n" +
                "Hash of the previous block:\n" + hashPrev + "\n" +
                "Hash of the block:\n" + hash + "\n" +
                "Block was generating for " + genTime + " seconds\n";
    }
}
