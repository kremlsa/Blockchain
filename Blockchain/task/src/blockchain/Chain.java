package blockchain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;

public class Chain implements Serializable {
    List<Block> chain = new ArrayList<>();
    public int numbersOfZeros = 4;
    public ExecutorService executor;
    public List<String> messages = new CopyOnWriteArrayList<>();

    public void add(Block block) {
        chain.add(block);
    }

    public Block getBlock(int id) {
        return chain.get(id);
    }

    public List<Block> getChain() {
        return chain;
    }

    public int getId() {
        return chain.size();
    }

    public void setMessage(int id, List<String> messages) {
        chain.get(id).setData(messages);
    }


}
