package blockchain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Mine {
    ExecutorService executor;
    TransactionGenerator tg;



    public List<String> messages = new CopyOnWriteArrayList<>();

    public void addMesage (String message) {
        messages.add(message);
    }

    public void setTg(TransactionGenerator tg) {
        this.tg = tg;
    }

    public BlockChain generateChainMT(BlockChain bc, int size, int numberOfZeros) {
        BlockChain candidate = null;
        BlockChain temp = bc;
        numberOfZeros++;
        int topLimit = 4;

        for (int i = 0; i <= size; i++) {
            candidate = mining(temp, numberOfZeros);
            if (candidate.generationTime > 500 || numberOfZeros > topLimit) numberOfZeros--;
            else numberOfZeros++;
            synchronized (this) {
                List<String> mess = new ArrayList<String>(messages.size());
                for (String s : messages) mess.add(s);
                candidate.setData(mess);
                messages.clear();
                tg.addCredits(candidate.getMinerId(), 100);
            }
            temp = candidate;

        }
        return candidate;
    }


    public BlockChain mining(BlockChain block, int numberOfZeros) {
        List<Future<BlockChain>> futures = new ArrayList<>();
        executor = Executors.newFixedThreadPool(4);
        BlockChain candidate = null;

        for (int i = 1; i < 5; i++) {
            futures.add(executor.submit(new MinerThread(block, numberOfZeros, i)));
        }
        boolean isDone = false;
        while (!isDone) {
            for (Future<BlockChain> future : futures) {
                if (future.isDone()) {
                    try {
                        candidate = future.get();
                        isDone = true;
                        break;
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        for (Future<BlockChain> future : futures) {
            future.cancel(true);
        }
        executor.shutdownNow();
        return candidate;
    }

}

class MinerThread implements Callable<BlockChain> {
    BlockChain block;
    int numbersOfZeros;
    int minerId;

    public MinerThread(BlockChain block, int numbersOfZeros, int minerId){
        this.block = block;
        this.numbersOfZeros = numbersOfZeros;
        this.minerId = minerId;

    }
    @Override
    public BlockChain call() {
        BlockChain result = block.addNext(numbersOfZeros);
        result.setMinerId(minerId);
        return result;
    }

}
