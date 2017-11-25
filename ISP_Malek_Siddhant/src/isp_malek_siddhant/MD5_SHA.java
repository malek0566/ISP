/** ********************************************\
 * ISP Programming Project
 * By: Malek Alahmadi & Siddhant Gupta
 * \********************************************* */

/* Write a program to demonstrate the use of hashing using MD5 and SHA scheme and the
Message_Digest class. Your program should allow a user to enter a string; the program then hashes it and outputs the result.
 */
package isp_malek_siddhant;

import java.math.BigInteger;
import java.security.*;
import java.util.Scanner;

public class MD5_SHA {

    public static void main(String[] args) throws Exception {

        // Takes input from the user
        Scanner in = new Scanner(System.in);
        System.out.println("please enter a message to encrypt> ");
        String s = in.nextLine();

        // MD5 Encryption
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.reset();
        md.update(s.getBytes());
        String hashtext_md5 = new BigInteger(1, md.digest()).toString(16);
        // Add zeros to the left
        while (hashtext_md5.length() < 32) {
            hashtext_md5 = "0" + hashtext_md5;
        }
        // Print the MD5 hash to the user
        System.out.println("MD5: " + hashtext_md5);

        // SHA-1 Encryption
        MessageDigest sha1 = MessageDigest.getInstance("SHA");
        sha1.reset();
        sha1.update(s.getBytes());
        String hashtext_sha1 = new BigInteger(1, sha1.digest()).toString(16);

        // Print the SHA hash to the user
        System.out.println("SHA: " + hashtext_sha1);

    }
}
