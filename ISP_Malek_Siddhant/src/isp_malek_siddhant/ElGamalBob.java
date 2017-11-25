/** ********************************************\
 * ISP Programming Project
 * By: Malek Alahmadi & Siddhant Gupta
 * \********************************************* */

/* In this part of the assignment, you are to implement the ElGamal Signature scheme described in the
textbook in section 10.6.2.2.
There are two classes in this assignment, ElGamalAlice and ElGamalBob, corresponding to the
sender (Alice) and the receiver (Bob). The main functions for both the classes have been written for
you. Your assignment is to write various functions that implement ElGamal key generation and
signature creation algorithms (for Alice), and signature verification algorithm (for Bob). The
functions you have to implement are indicated in the source files.
 */
package isp_malek_siddhant;

import java.io.*;
import java.net.*;
import java.math.BigInteger;

public class ElGamalBob {

    private static boolean verifySignature(BigInteger y, BigInteger a, BigInteger p, BigInteger r, BigInteger b, String message) {
        // IMPLEMENT THIS FUNCTION;
        //y^r r^s mod p == a^m mod p
        BigInteger v1 = (y.modPow(r, p).multiply(r.modPow(b, p))).mod(p);;
        BigInteger v2 = a.modPow(new BigInteger(message.getBytes()), p);
        return v1.equals(v2);

    }

    public static void main(String[] args) throws Exception {
        // open Socket
        int port = 7654;
        ServerSocket s = new ServerSocket(port);
        Socket client = s.accept();
        ObjectInputStream is = new ObjectInputStream(client.getInputStream());

        // read public key
        BigInteger y = (BigInteger) is.readObject(); //y

        BigInteger a = (BigInteger) is.readObject(); //a

        BigInteger p = (BigInteger) is.readObject(); //p

        // read message
        String message = (String) is.readObject();

        // read signature
        BigInteger r = (BigInteger) is.readObject(); //r
        BigInteger b = (BigInteger) is.readObject(); //s

        // verify signature
        boolean result = verifySignature(y, a, p, r, b, message); //y^r r^s mod p == a^m mod p

        System.out.println(message);

        if (result == true) {
            System.out.println("Signature verified.");
        } else {
            System.out.println("Signature verification failed.");
        }

        s.close();
    }
}
