import java.io.*;
import java.security.*;
import javax.crypto.Cipher;
import java.util.Base64;
import java.util.Scanner;

public class RSA {
    private static final String ALGORITHM = "AES";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose an option:");
        System.out.println("1. Encrypt and save to file");
        System.out.println("2. Decrypt from file");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                encryptandSave(scanner);
                break;
            case 2:
                decryptFromFile();
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }

        scanner.close();
    }

    private static void encryptandSave(Scanner scanner) {
        System.out.println("Enter plaintext:");
        String plaintext = scanner.nextLine();

        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());
            String encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);
            System.out.println("Encrypted Text: " + encryptedText);

            saveCiphertext(encryptedText, "cipher.txt");
            savePublicKey(publicKey, "publickey.txt");
            savePrivateKey(privateKey, "privatekey.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void decryptFromFile() {

        PublicKey publicKey = readPublicKeyFromFile("publickey.txt");
        if (publicKey == null) {
            System.out.println("Public key not found.");
            return;
        }
        PrivateKey privateKey = readPrivateKeyFromFile("privatekey.txt");
        if (privateKey == null) {
            System.out.println("Private key not found.");
            return;
        }

        String ciphertext = readCiphertextFromFile("cipher.txt");
        if (ciphertext.isEmpty()) {
            System.out.println("Ciphertext not found.");
            return;
        }

        try {
            String decryptedText = decrypt(ciphertext, privateKey);
            System.out.println("Decrypted Text: " + decryptedText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void saveCiphertext(String ciphertext, String filename) {
        try (PrintWriter writer = new PrintWriter(filename)) {
            writer.println(ciphertext);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void savePublicKey(PublicKey publicKey, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(publicKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static PublicKey readPublicKeyFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (PublicKey) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void savePrivateKey(PrivateKey privateKey, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(privateKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static PrivateKey readPrivateKeyFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (PrivateKey) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
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

    public static String decrypt(String ciphertext, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(ciphertext));
        return new String(decryptedBytes);
    }
}
