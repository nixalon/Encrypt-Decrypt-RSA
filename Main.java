import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
   KeyPair publicKey;
   KeyPair privateKey;

   private Main() {
      if(!new File("Alex_pub.key").exists() || !new File("Alex_priv.key").exists()){
         int bitLength = 4096;
         Keys.generateKeys("Alex", bitLength);
      }
      publicKey = Keys.readKey("Alex_pub.key");
      privateKey = Keys.readKey("Alex_priv.key");
   }

   public static void main(String[] args) {
      Main main = new Main();

      System.out.println("Welcome to Encrypt/Decrypt! Plz choose one of the following options.");
      System.out.println("1. Encrypt normal text");
      System.out.println("2. Encrypt text file");
      System.out.println("3. Decrypt normal text");
      System.out.println("4. Decrypt text file");

      main.readingInput();
   }

   private void readingInput() {

      Scanner scanner = new Scanner(System.in);
      String input = scanner.nextLine();

      if (input.equals("1")) {
         System.out.print("Please enter the text you want to encrypt:");
         String message = scanner.nextLine();
         String encryptedMessage = Crypt.encrypt(message, publicKey);
         System.out.println("The message is now encrypted: " + encryptedMessage);
      }
      if (input.equals("2")) {
         System.out.print("Please enter the path to the file you want to encrypt:");
         String filePath = scanner.nextLine();
         System.out.println("Please enter a new name for the encrypted file:");
         String encryptedFilePath = scanner.nextLine();
         File file = new File(filePath);
         Scanner fileScanner;

         try {
            fileScanner = new Scanner(file);
         } catch (FileNotFoundException e) {
            System.out.println("File path does not exist");
            return;
         }
         fileScanner.useDelimiter("\\Z"); // Betyder "läs tills du stöter på \\Z, vilket är slutet av en fil"
         String fileContent = fileScanner.next();
         String encryptedContent = Crypt.encrypt(fileContent, publicKey);
         fileScanner.close();

         try {
            FileWriter fileWriter = new FileWriter(encryptedFilePath);
            fileWriter.write(encryptedContent);
            System.out.println("File is now encrypted. The encrypted file is saved here: " + encryptedFilePath);
            fileWriter.close();
         } catch (IOException e) {
            System.out.println("File path does not exist.");
         }
         
      }
      if (input.equals("3")){
         System.out.print("Please enter encrypted message:");
         String message = scanner.nextLine();
         String decryptedMessage = Crypt.decrypt(message, privateKey);
         System.out.println("The message is now decrypted: " + decryptedMessage);
      }
      if (input.equals("4")){
         System.out.println("Please enter the name of the file you want to decrypt:");
         String filePath = scanner.nextLine();
         System.out.println("Please enter the name of the path you want to save the decrypted file to:");
         String decryptedFilePath = scanner.nextLine();
         File file = new File(filePath);
         Scanner fileScanner;

         try {
            fileScanner = new Scanner(file);
         } catch (FileNotFoundException e) {
            System.out.println("File path does not exist");
            return;
         }
         fileScanner.useDelimiter("\\Z"); // Betyder "läs tills du stöter på \\Z, vilket är slutet av en fil"
         String fileContent = fileScanner.next();
         String decryptedContent = Crypt.decrypt(fileContent, privateKey);
         fileScanner.close();

         try {
            FileWriter fileWriter = new FileWriter(decryptedFilePath);
            fileWriter.write(decryptedContent);
            fileWriter.close();
         } catch (IOException e) {
            System.out.println("File path does not exist.");
         }
      }
      scanner.close();
   }
}