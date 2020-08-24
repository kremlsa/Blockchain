package blockchain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChainBchain implements Serializable {
    List<BChain> chain = new ArrayList<>();

    public void add(BChain block) {
        chain.add(block);
    }

    public List<BChain> getChain() {
        return chain;
    }
}
