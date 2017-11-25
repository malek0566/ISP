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
import java.security.*;
import java.math.BigInteger;

public class ElGamalAlice
{
	private static BigInteger computeY(BigInteger p, BigInteger a, BigInteger x)
	{
	    // IMPLEMENT THIS FUNCTION;
            //y = a^x mod p
            return a.modPow(x, p);
	}

	private static BigInteger computeK(BigInteger p, BigInteger a, BigInteger x, int mStrength,SecureRandom  mSecureRandom)
	{
		// IMPLEMENT THIS FUNCTION;
                boolean flag = true;
                BigInteger k;
                do{
               k =  new BigInteger(mStrength-1, mSecureRandom);//k
                if(!k.equals(a) && !k.equals(x) && k.gcd(p.subtract(BigInteger.valueOf(1))).equals(BigInteger.valueOf(1)))
                    flag = false;
                }while(flag);
                
                return k;
	}
	private static BigInteger computeR(BigInteger p, BigInteger a, BigInteger k)
	{
		// IMPLEMENT THIS FUNCTION;
            //r = a^k mod p
             return a.modPow(k, p);
	}

	private static BigInteger computeB(String message, BigInteger x, BigInteger r, BigInteger k, BigInteger p)
	{
		// IMPLEMENT THIS FUNCTION;
               // b = ((m-xr)*H) mod (p-1),
		// H= k.modInverse(p-1).
		return (new BigInteger(message.getBytes()).subtract(x.multiply(r))
                        .multiply(k.modInverse(p.subtract(BigInteger.ONE))).mod(p.subtract(BigInteger.ONE)));        
                                  
	}

	public static void main(String[] args) throws Exception 
	{
		String message = "The quick brown fox jumps over the lazy dog.";
                // Open Socket
		int port = 7654;
		Socket s = new Socket(InetAddress.getLoopbackAddress(), port);
		ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
 
		// You should consult BigInteger class in Java API documentation to find out what it is.
		BigInteger y, a, p; // public key
		BigInteger x; // private key

		int mStrength = 1024; // key bit length
		SecureRandom mSecureRandom = new SecureRandom(); // a cryptographically strong pseudo-random number

		// Create a BigInterger with mStrength bit length that is highly likely to be prime.
		// (The '16' determines the probability that p is prime. Refer to BigInteger documentation.)
		p = new BigInteger(mStrength, 16, mSecureRandom); //p
		// Create a randomly generated BigInteger of length mStrength-1
		a = new BigInteger(mStrength-1, mSecureRandom); // g
		x = new BigInteger(mStrength-1, mSecureRandom);  //d
                //compute Y
		y = computeY(p, a, x);  //y = a^x mod p

		// At this point, you have both the public key and the private key. Now compute the signature.
               
                // Alternative method for computing K
               /* boolean flag = true;
                BigInteger k;
                do{
               k =  new BigInteger(mStrength-1, mSecureRandom);//k
                if(!k.equals(a) && !k.equals(x) && k.gcd(p.subtract(BigInteger.valueOf(1))).equals(BigInteger.valueOf(1)))
                    flag = false;
                }while(flag);*/
                
                //compute k, r, and b 
                BigInteger k = computeK(p, a, x, mStrength, mSecureRandom);  
		BigInteger r = computeR(p, a, k);  //r = a^k mod p
		BigInteger b = computeB(message, x, r, k, p); //b = ((m-xr)/k) mod (p-1)

		// send public key
		os.writeObject(y);
		os.writeObject(a);
		os.writeObject(p);

		// send message
		os.writeObject(message);
		
		// send signature
		os.writeObject(r);
		os.writeObject(b);
		
		s.close();
	}
}