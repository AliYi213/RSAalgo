import java.util.Scanner;

public class VigenereCipher {
    public static String encrypt(String text, String key) {
        StringBuilder result = new StringBuilder();
        int keyIndex = 0;
        key = key.toUpperCase();
        for (int i = 0; i < text.length(); i++) {
            char character = text.charAt(i);
            if (Character.isLetter(character)) {
                int shift = key.charAt(keyIndex % key.length()) - 'A';
                result.append(shiftCharacter(character, shift));
                keyIndex++;
            } else {
                result.append(character);
            }
        }

        return result.toString();
    }

    public static String decrypt(String text, String key) {
        StringBuilder result = new StringBuilder();
        int keyIndex = 0;
        key = key.toUpperCase();

        for (int i = 0; i < text.length(); i++) {
            char character = text.charAt(i);
            if (Character.isLetter(character)) {
                int shift = key.charAt(keyIndex % key.length()) - 'A';
                result.append(shiftCharacter(character, -shift));
                keyIndex++;
            } else {
                result.append(character);
            }
        }

        return result.toString();
    }

    private static char shiftCharacter(char character, int shift) {
        char base = Character.isUpperCase(character) ? 'A' : 'a';
        return (char) ((character - base + shift + 26) % 26 + base);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the initial text: ");
        String initialText = scanner.nextLine();

        System.out.print("Enter the key: ");
        String key = scanner.nextLine();

        String encryptedText = encrypt(initialText, key);
        System.out.println("Encrypted Text: " + encryptedText);

        String decryptedText = decrypt(encryptedText, key);
        System.out.println("Decrypted Text: " + decryptedText);
    }
}