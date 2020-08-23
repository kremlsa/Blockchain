package blockchain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Random;

public class utils {

    public static String[] calchash(String input, int numbersOfZeros) {
        String[] results = new String[2];
        int magicNumber = 1;
        boolean isDone = false;
        String zeros = "";
        Random random = new Random();
        for (int i = 0; i < numbersOfZeros; i++) {
            zeros += "0";
        }
        StringBuilder hexString = new StringBuilder();
        magicNumber = random.nextInt(Integer.MAX_VALUE);
        while (!isDone) {

            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                /* Applies sha256 to our input */
                byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
                for (byte elem : hash) {
                    String hex = Integer.toHexString(0xff & elem);
                    if (hex.length() == 1) hexString.append('0');
                    hexString.append(hex);
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (hexString.toString().startsWith(zeros)) {
                isDone = true;
            }
            input += magicNumber;
            magicNumber = random.nextInt(Integer.MAX_VALUE);
        }
        results[0] = String.valueOf(magicNumber);
        results[1] = hexString.toString();
        return (results);
        }

    }
