package blockchain;
import java.io.*;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.List;
import java.util.Random;

public class Utils {

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

    public static void saveBlockChain(BlockChain bc) {
        try {
            File file = new File("chain.db");
            FileOutputStream fs = new FileOutputStream(file);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(bc);
            os.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static BlockChain loadBlockChain(String input) {
        BlockChain bc = null;
        try {
            File file = new File(input);
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(file));
            bc = (BlockChain) is.readObject();
            is.close();
        } catch (Exception ex) {
            return null;
        }
        return bc;
    }

    public static PublicKey getPublic() throws Exception {
        String filename = "KeyPair/publicKey";
        byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    public static PrivateKey getPrivate() throws Exception {
        String filename = "KeyPair/privateKey";
        byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    public static byte[] sign(List<String> messages) {
        byte[] res = null;
        String messageToSign = "";
        for (String s : messages) {
            messageToSign += s + "\n";
        }

        try {
            PublicKey publicKey = getPublic();
            PrivateKey privateKey = getPrivate();
            Signature rsa = Signature.getInstance("SHA1withRSA");
            rsa.initSign(privateKey);
            rsa.update(messageToSign.getBytes());
            res = rsa.sign();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static void generateKeys()  {
        KeyPairGenerator keyGen = null;
        try {
            keyGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        keyGen.initialize(512);

        try {
            KeyPair pair = keyGen.generateKeyPair();
            PrivateKey privateKey = pair.getPrivate();
            PublicKey publicKey = pair.getPublic();
            writeToFile("KeyPair/publicKey", publicKey.getEncoded());
            writeToFile("KeyPair/privateKey", privateKey.getEncoded());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void writeToFile(String path, byte[] key) throws IOException {
        File f = new File(path);
        f.getParentFile().mkdirs();

        FileOutputStream fos = new FileOutputStream(f);
        fos.write(key);
        fos.flush();
        fos.close();
    }



}
