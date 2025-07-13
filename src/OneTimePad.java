import java.security.SecureRandom;
import java.util.Arrays;

public class OneTimePad {
    public static void main(String[] args) {
        String plain = "MY NAME IS UNKNOWN";
        byte[] plaintext = plain.getBytes();
        byte[] key = new byte[plaintext.length];

        // Generate a secure random key
        new SecureRandom().nextBytes(key);

        // Encrypt: XOR each byte
        byte[] cipher = new byte[plaintext.length];
        for (int i = 0; i < plaintext.length; i++) {
            cipher[i] = (byte) (plaintext[i] ^ key[i]);
        }

        // Output
        System.out.println("Plain:  " + plain);
        System.out.println("Key:    " + Arrays.toString(key));
        System.out.println("Cipher: " + Arrays.toString(cipher));
        System.out.print("Cipher (hex): ");
        for (byte b : cipher) System.out.printf("%02X ", b);
        System.out.println();
    }
}
