package blockchain;

import java.io.Serializable;
import java.util.*;

public class BlockChain implements Serializable {
    private String name = "Block:";
    private int id;
    long timeStamp;
    long generationTime;
    String magicNumber;
    String hashPrev;
    String hash;
    BlockChain prev = null;
    BlockChain next = null;
    int numbersOfZeros;
    int minerId = 0;
    boolean isRoot = false;
    List<String> data;
    byte[] sign = null;
    byte[] publicKey = null;

    public BlockChain (int id, String hashPrev, int numbersOfZeros, boolean isRoot) {
        this.id = id;
        this.data =  new ArrayList<>();
        this.numbersOfZeros = numbersOfZeros;
        this.timeStamp = new Date().getTime();
        this.hashPrev = hashPrev;
        String forHash = this.name + " " + this.id + " " + this.timeStamp + " " + this.hashPrev;
        long startTime = System.currentTimeMillis();
        this.isRoot = isRoot;
        if (!isRoot) {
            String[] calc = Utils.calcHashMagic(forHash, numbersOfZeros);
            this.magicNumber = calc[0];
            this.hash = calc[1];
        } else {
            this.magicNumber = "0";
            this.hash = "0";
        }
        long endTime = System.currentTimeMillis();
        this.generationTime = (endTime - startTime);
    }

    public void setData(List<String> data) {
        this.data = data;
        this.sign = Utils.sign(data);
        try {
            this.publicKey = Utils.getPublic().getEncoded();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getMinerId() {
        return minerId;
    }

    public List<String> getData() {
        return data;
    }

    public void setMinerId(int minerId) {
        this.minerId = minerId;
    }

    public int getId() {
        return id;
    }

    public void setPrev(BlockChain prev) {
        this.prev = prev;
    }

    public BlockChain addNext(int numbersOfZeros) {
        BlockChain temp = new BlockChain(id + 1, hash, numbersOfZeros, false);
        temp.setPrev(this);
        this.next = temp;
        return this.next;
    }

    public String deltaZero() {
        String res = "";
        int delta = this.numbersOfZeros - (hasPrev() ? getPrev().numbersOfZeros : this.numbersOfZeros);
        if (delta == 0) {
            res += "N stays the same";
        } else if (delta > 0) {
            res += "N was increased to " + numbersOfZeros;
        } else {
            res += "N was decreased by 1";
        }
        return res;
    }

    public String printBlock() {
        String dataString;
        if (data.isEmpty()) {
            dataString = " no transactions\n";
        } else {
            dataString = "\n";
            for (String d : data) {
                dataString += d;
                dataString += "\n";
            }
            dataString += signature();
            dataString += publicKey();
        }
        return name + "\n" +
                "Created by miner # " + minerId + "\n" +
                "miner " + minerId + " gets 100 VC" + "\n" +
                "Id: " + id + "\n" +
                "Timestamp:" +  timeStamp +"\n" +
                "Magic number: " + magicNumber + "\n" +
                "Hash of the previous block:\n" + hashPrev + "\n" +
                "Hash of the block:\n" + hash + "\n" +
                "Block data: " + dataString +
                "Block was generating for " + generationTime + " seconds\n" +
                deltaZero() + "\n";
    }

    public String printFive() {
        String res = "";
        ArrayList<String> arr = new ArrayList<>();
        BlockChain temp = this;
        if (hasPrev()) {
        for (int i = 0; i < 15; i++) {
                if (temp.hasPrev()) {
                    temp = temp.getPrev();
                    arr.add(temp.printBlock());
                }
            }
        }
        Collections.reverse(arr);
        for (String s : arr) {
            res += s + "\n";
        }
        return res;
    }

    public String signature() {
        if (sign != null) {
            return "Signature is " + Base64.getEncoder().encodeToString(sign) + "\n";
        } else return "";
    }

    public String publicKey() {
        if (publicKey != null) {
            return "PublicKey is " + Base64.getEncoder().encodeToString(publicKey) + "\n";
        } else return "";
    }

    public String path() {
        String res = "" + this.id;
        if (hasPrev()) {
            BlockChain temp = getPrev();
            while (temp.hasPrev()) {
                res += " " + temp.getId();
                temp = temp.getPrev();
            }
            res += " " + temp.getId();
        }
        return res;
    }

    public BlockChain getPrev() {
        return prev;
    }

    public boolean hasNext() {
        return next != null ? true : false;
    }

    public boolean hasPrev() {
        return prev != null ? true : false;
    }

    public boolean validate() {
        return false;
    }

    public BlockChain getNext() {
        return next;
    }
}
