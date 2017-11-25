/** ********************************************\
 * ISP Programming Project
 * By: Malek Alahmadi & Siddhant Gupta
 * \********************************************* */

/* For the second part of this lab assignment, you will implement a cryptanalysis tool to break the
shift cipher that you implemented in the first part. Concretely, your program should a ciphertext
as input, find the frequency of distribution of the letters in the cipher-text and compute its
correlation with the English alphabets for various possible values of the encryption keys. Your
program should display the encryption keys and plain texts corresponding to top 7 correlations
with their correlation values.
 */
package isp_malek_siddhant;

import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;

public class cryptanalysis {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {

        // Read the cipher text from a file
        FileReader fr = new FileReader("cipher_text.txt");
        BufferedReader br = new BufferedReader(fr);
        String cipher_text = br.readLine();
        br.close();
        fr.close();
        System.out.println("Cipher text: " + cipher_text);

        //Convert cipher text String to array of chars
        char message_chars[] = new char[cipher_text.getBytes().length];
        message_chars = cipher_text.toCharArray();

        // English letters frequency
        double Freq_in_En[] = {0.080, 0.015, 0.030, 0.040, 0.130, 0.020, 0.015, 0.060, 0.065, 0.005, 0.005,
            0.035, 0.030, 0.070, 0.080, 0.020, 0.002, 0.065, 0.060, 0.090, 0.030, 0.010, 0.015, 0.005, 0.020, 0.002};

        int count[] = new int[26];  // count how many a char is repeated in a ciipher text
        int x;
        int RealLength = 0;   // String length without spaces
        // This method calculates the String length without spaces
        for (int i = 0; i < message_chars.length; i++) {
            x = (int) message_chars[i];
            if (x >= 65 && x <= 90) {
                count[x - 65]++;
                RealLength++;
            } else if (x >= 97 && x <= 122) {
                count[x - 97]++;
                RealLength++;
            }
        }

        double freq_in_c[] = new double[26];  // letters frequency in cipher text

        // This method calculates letters frequency in the cipher text
        for (int i = 0; i < 26; i++) {
            freq_in_c[i] = (double) count[i] / RealLength;
        }

        double Key_Freq[] = new double[26];  // ϕ(i)

        /* This method calculates: ϕ(i) = Σ0 ≤ c ≤ 25 f(c) * p(c – i)
        1- First loop for every  ϕ(i) value.
        2- Second loop calculates ϕ(i) = Σ0 ≤ c ≤ 25 f(c) * p(c – i) 
         */
        for (int i = 0; i < 26; i++) {
            boolean flag[] = new boolean[26];
            for (int j = 0; j < message_chars.length; j++) {
                x = (int) message_chars[j];
                if (x >= 65 && x <= 90) {  // Captial letters
                    if (!flag[x - 65]) {
                        flag[x - 65] = true;
                        Key_Freq[i] = (freq_in_c[x - 65] * Freq_in_En[Math.floorMod((x - 65 - i), 26)]) + Key_Freq[i];
                    }
                }
                if (x >= 97 && x <= 122) {  // Small letters
                    if (!flag[x - 97]) {
                        flag[x - 97] = true;
                        Key_Freq[i] = (freq_in_c[x - 97] * Freq_in_En[Math.floorMod((x - 97 - i), 26)]) + Key_Freq[i];
                    }
                }

            }

        }
        
        // Sorting the ϕ(i) values
        double Sorted_Key_Freq[] = new double[26];
        Sorted_Key_Freq = Key_Freq.clone();
        Arrays.sort(Sorted_Key_Freq);

        NumberFormat formatter = new DecimalFormat("#0.0000"); 
        // This method decrypted the message using the top 7 ϕ(i) values
        for (int i = 25; i > 18; i--) {
            outerloop:
            for (int j = 0; j < Key_Freq.length; j++) {
                if (Sorted_Key_Freq[i] == Key_Freq[j]) {
                    int s;
                    char temp_message_chars[] = new char[message_chars.length];
                    temp_message_chars = message_chars.clone();

                    for (int r = 0; r < temp_message_chars.length; r++) {

                        s = (int) temp_message_chars[r];
                        if (s >= 65 && s <= 90) {
                            s = Math.floorMod((s - 65) - j, 26);
                            temp_message_chars[r] = (char) (s + 65);
                        } else if (s >= 97 && s <= 122) {
                            s = Math.floorMod((s - 97) - j, 26);
                            temp_message_chars[r] = (char) (s + 97);
                        }
                    }
                    String p_text = new String(temp_message_chars); // Array of chars to string
                    
                    // Display to the user
                    System.out.print("Key= " + j);  
                    System.out.print(", ϕ(i)= "+formatter.format(Sorted_Key_Freq[i]));
                    System.out.println(", Probable plain text: " + p_text);
                    break outerloop;

                }

            }

        }
    }

}
