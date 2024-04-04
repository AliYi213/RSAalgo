import java.io.*;
import java.security.*;
import javax.crypto.Cipher;
import java.util.Base64;
import java.util.Scanner;

public class RSA {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter plaintext:");
        String plaintext = scanner.nextLine();

        try {

            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            // Encryption
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());
            String encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);
            System.out.println("Encrypted Text: " + encryptedText);

            saveCiphertextToFile(encryptedText, "cipher.txt");
            savePublicKeyToFile(publicKey, "publickey.txt");

            System.out.println("Do you want to display the ciphertext from the file? (y/n)");
            String displayChoice = scanner.nextLine();
            if (displayChoice.equalsIgnoreCase("y")) {
                System.out.println("Cipher from the file: " + encryptedText);
                System.out.println("PublicKey from the file: " + publicKey);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    private static void saveCiphertextToFile(String ciphertext, String filename) {
        try (PrintWriter writer = new PrintWriter(filename)) {
            writer.println(ciphertext);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static String readCiphertextFromFile(String filename) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private static void savePublicKeyToFile(PublicKey publicKey, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(publicKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
