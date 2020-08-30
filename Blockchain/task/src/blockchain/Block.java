package blockchain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Block implements Serializable {
    String name = "Block:";
    int id;
    long timeStamp;
    String hashPrev;
    String hash;
    String magicNumber;
    long genTime;
    String minerId = "";
    List<String> data = new ArrayList<>();

   public Block(int id, long timeStamp, String hashPrev, String hash,
                String magicNumber, long genTime) {
        this.id = id;
        this.timeStamp = timeStamp;
        this.hashPrev = hashPrev;
        this.magicNumber = magicNumber;
        this.hash = hash;
        this.genTime = genTime;
        data.add("no message\n");
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

    public void setData(List<String> data) {
        this.data = data;
    }

    public List<String> getData() {
        return data;
    }

    public String toString() {
        Random rand = new Random();
        int randomNum = rand.nextInt((5 - 1) + 1) + 1;

        return name + "\n" +
                "Created by miner # " + minerId + "\n" +
                "Id: " + id + "\n" +
                "Timestamp:" +  timeStamp +"\n" +
                "Magic number: " + magicNumber + "\n" +
                "Hash of the previous block:\n" + hashPrev + "\n" +
                "Hash of the block:\n" + hash + "\n" +
                "Block data: " + data +
                "Block was generating for " + genTime + " seconds\n" +
                "N was increased to " + randomNum + "\n";
    }
}
