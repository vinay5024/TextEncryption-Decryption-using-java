package vinay;

import java.io.*;
import java.util.Scanner;

public class TextEncrypto {

    // Encrypt a single character
    public static char encryptChar(char ch, int key) {
        if (Character.isUpperCase(ch)) {
            return (char) ((ch - 'A' + key) % 26 + 'A');
        } else if (Character.isLowerCase(ch)) {
            return (char) ((ch - 'a' + key) % 26 + 'a');
        } else {
            return ch; // Don't encrypt digits, spaces, or symbols
        }
    }

    // Decrypt a single character
    public static char decryptChar(char ch, int key) {
        if (Character.isUpperCase(ch)) {
            return (char) ((ch - 'A' - key + 26) % 26 + 'A');
        } else if (Character.isLowerCase(ch)) {
            return (char) ((ch - 'a' - key + 26) % 26 + 'a');
        } else {
            return ch;
        }
    }

    // Encrypt file
    public static void encryptFile(String inputPath, String outputPath, int key) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputPath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {

            int ch;
            while ((ch = reader.read()) != -1) {
                writer.write(encryptChar((char) ch, key));
            }
        }
        System.out.println("✅ File encrypted and saved to: " + outputPath);
    }

    // Decrypt file
    public static void decryptFile(String inputPath, String outputPath, int key) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputPath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {

            int ch;
            while ((ch = reader.read()) != -1) {
                writer.write(decryptChar((char) ch, key));
            }
        }
        System.out.println("✅ File decrypted and saved to: " + outputPath);
    }

    // Get Downloads folder path
    public static String getDownloadsPath() {
        String home = System.getProperty("user.home");
        return home + File.separator + "Downloads";
    }

    // Main program
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== 🔐 Text File Encryption/Decryption Program ===");

        try {
            System.out.print("🔑 Enter Key (numeric): ");
            int key = sc.nextInt();
            sc.nextLine(); // Consume newline

            System.out.println("📋 Choose option:\n1. Encrypt File\n2. Decrypt File");
            int option = sc.nextInt();
            sc.nextLine(); // Consume newline

            System.out.print("📁 Enter input file path: ");
            String inputPath = sc.nextLine().trim().replace("\"", ""); // Fix: Remove quotes
            File inputFile = new File(inputPath);
            if (!inputFile.exists()) {
                System.out.println("❌ Input file does not exist. Please check the path.");
                return;
            }

            System.out.print("📄 Enter name for output file (e.g., encrypted.txt): ");
            String fileName = sc.nextLine().trim();
            String outputPath = getDownloadsPath() + File.separator + fileName;

            File outputFile = new File(outputPath);
            if (outputFile.exists()) {
                System.out.print("⚠️ Output file already exists. Overwrite? (yes/no): ");
                String response = sc.nextLine().trim().toLowerCase();
                if (!response.equals("yes")) {
                    System.out.println("❌ Operation canceled by user.");
                    return;
                }
            }

            if (option == 1) {
                encryptFile(inputPath, outputPath, key);
            } else if (option == 2) {
                decryptFile(inputPath, outputPath, key);
            } else {
                System.out.println("❌ Invalid option. Please enter 1 or 2.");
            }

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
}
