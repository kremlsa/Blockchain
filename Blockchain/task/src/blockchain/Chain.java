package blockchain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Chain implements Serializable {
    List<Block> chain = new ArrayList<>();

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
}
