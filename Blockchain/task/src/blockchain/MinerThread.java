package blockchain;

import java.util.concurrent.Callable;

public class MinerThread implements Callable<String[]> {
    String input;
    int numbersOfZeros;
    String[] result;
    int minerId;

    public MinerThread(String input, int numbersOfZeros, int minerId){
        this.input = input;
        this.numbersOfZeros = numbersOfZeros;
        this.minerId = minerId;

    }
    @Override
    public String[] call() {
        String[] result = utils.calcHashMagic(input, numbersOfZeros);
        String[] res = new String[3];
        res[0] = result[0];
        res[1] = result[1];
        res[2] = String.valueOf(minerId);
        return res;
    }

    public String[] getResult() {
        return result;
    }

    public int getMinerId() {
        return minerId;
    }
}
