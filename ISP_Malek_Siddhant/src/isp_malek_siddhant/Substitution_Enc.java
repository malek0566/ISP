/** ********************************************\
 * ISP Programming Project
 * By: Malek Alahmadi & Siddhant Gupta
 * \********************************************* */

/* In the first part of this assignment, you need to implement the encryption and decryption
algorithms for a shift cipher. Your encryption program should take the encryption key and the
plain text (an input file) as inputs and should produce the cipher text as an output file. Similarly,
your decryption program takes the key and the cipher text file as an input and produces the plain
text back.
 */
package isp_malek_siddhant;

import java.io.*;
import java.util.Scanner;

public class Substitution_Enc {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here

        // Read the plain text from a file
        FileReader fr = new FileReader("plain_text.txt");
        BufferedReader br = new BufferedReader(fr);
        String plain_text = br.readLine();
        br.close();
        fr.close();
        System.out.println("Plain text: " + plain_text);

        //Convert Plain text String to array of chars
        char message_chars[] = new char[plain_text.getBytes().length];
        message_chars = plain_text.toCharArray();
        // Get the Encryption key from the user
        Scanner sc = new Scanner(System.in);
        int k;
        System.out.println("Please enter a key number from 0 - 25 to encrypt your message> ");
        k = sc.nextInt();
        sc.close();

        // Here is the encryption proccess
        int x;
        for (int i = 0; i < message_chars.length; i++) {
            x = (int) message_chars[i];
            if (x >= 65 && x <= 90) {    //For captial letters
                x = Math.floorMod((x - 65) + k, 26);
                message_chars[i] = (char) (x + 65);
            } else if (x >= 97 && x <= 122) {  //For small letters
                x = Math.floorMod((x - 97) + k, 26);
                message_chars[i] = (char) (x + 97);
            }
        }

        // Convert the array of chars into String and print to the user
        String c_text = new String(message_chars);
        System.out.println("Cipher text: " + c_text);

        //Write the encrypted message into a file
        FileWriter fw = new FileWriter("cipher_text.txt");
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(c_text);
        bw.flush();
        bw.close();
    }

}
