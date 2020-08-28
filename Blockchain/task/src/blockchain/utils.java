package blockchain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Random;

public class utils {

    public static String[] calcHashMagic(String input, int numbersOfZeros) {
        String[] results = new String[2];
        int magicNumber = 0;
        boolean isDone = false;
        String zeros = "";
        Random random = new Random();
        String hash ="";
        for (int i = 0; i < numbersOfZeros; i++) {
            zeros += "0";
        }
            while (!isDone) {
                magicNumber = random.nextInt(Integer.MAX_VALUE);
                hash = calchash(input + magicNumber);
                if (hash.startsWith(zeros)) {
                    isDone = true;
                }
            }

        results[0] = String.valueOf(magicNumber);
        results[1] = hash;
        return (results);
        }

    public static String calchash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            /* Applies sha256 to our input */
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte elem: hash) {
                String hex = Integer.toHexString(0xff & elem);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }



    }
