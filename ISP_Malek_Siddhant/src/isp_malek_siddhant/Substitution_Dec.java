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


public class Substitution_Dec {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
         
                // Read the cipher text from a file
                FileReader fr = new FileReader("cipher_text.txt");
                BufferedReader br = new BufferedReader(fr);
                String cipher_text = br.readLine();
                br.close();
                fr.close();
                System.out.println("Cipher text: "+cipher_text);  // print the cihper text

                //Convert cipher text String to array of chars                
                char message_chars[] = new char [cipher_text.getBytes().length]; 
                message_chars= cipher_text.toCharArray();
                
                // Get the Decryption key from the user 
                Scanner sc = new Scanner (System.in);
                int k;
                System.out.println("Please enter a key number from 0 - 25 to decrypt your message> ");
                k = sc.nextInt();
                sc.close();

                int x;
                // Here is the decryption proccess
                for(int i = 0; i<message_chars.length; i++){
                    x = (int) message_chars[i];
                  if(x >= 65 && x <= 90){
                      x = Math.floorMod((x-65) - k,26);
                      message_chars[i] = (char) (x+65); 
                  }
                 else if(x >= 97 && x <= 122){
                      x = Math.floorMod((x-97) - k,26);
                  message_chars[i] = (char) (x+97); 
                }
                }
                // Convert the array of chars into String and print to the user
                  String p_text = new String(message_chars);
                  System.out.println("Plain text: "+p_text);
                
                 //Write the decrypted message into a file  
                FileWriter fw = new FileWriter("Decrypted_cipher_text.txt");
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(p_text);
                bw.flush();
                bw.close();
    }
    
}
