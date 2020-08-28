package blockchain;

import java.io.Serializable;
import java.util.Date;

public class Block implements Serializable {
    String name = "Block:";
    int id;
    long timeStamp;
    String hashPrev;
    String hash;
    String magicNumber;
    long genTime;
    String minerId = "";

   public Block(int id, long timeStamp, String hashPrev, String hash,
                String magicNumber, long genTime) {
        this.id = id;
        this.timeStamp = timeStamp;
        this.hashPrev = hashPrev;
        this.magicNumber = magicNumber;
        this.hash = hash;
        this.genTime = genTime;
    }

    public void setMinerId(String minerId) {
        this.minerId = minerId;
    }

    public String getHashPrev() {
        return hashPrev;
    }

    public String getHash() {
        return hash;
    }

    public String toString() {
        return name + "\n" +
                "Created by miner # " + minerId + "\n" +
                "Id: " + id + "\n" +
                "Timestamp:" +  timeStamp +"\n" +
                "Magic number: " + magicNumber + "\n" +
                "Hash of the previous block:\n" + hashPrev + "\n" +
                "Hash of the block:\n" + hash + "\n" +
                "Block was generating for " + genTime + " seconds\n";
    }
}
