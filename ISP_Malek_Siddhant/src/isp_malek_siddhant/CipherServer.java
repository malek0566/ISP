/** ********************************************\
 * ISP Programming Project
 * By: Malek Alahmadi & Siddhant Gupta
 * \********************************************* */

/* In this part of the assignment, the client program CipherClient should (1) generate a DES key
and stores the key in a file, (2) encrypts the given String object using that key and sends the
encrypted object over the socket to the server. The server program CipherServer then uses
the key that was previously generated by the client to decrypt the incoming object. The server
obtains the key simply by reading it from the same file that the client previously generated.
The server should then print out the decrypted message.
 */

package isp_malek_siddhant;
import java.io.*;
import java.net.*;
import java.security.*;
import javax.crypto.*;

public class CipherServer
{
	public static void main(String[] args) throws Exception 
	{
	// open socket	
            int port = 7653;
		ServerSocket server = new ServerSocket(port);
		Socket s = server.accept();
               

		// YOU NEED TO DO THESE STEPS:
		// -Read the key from the file generated by the client.
                FileInputStream file_in = new FileInputStream("Key.dat");
		ObjectInputStream in = new ObjectInputStream(file_in);
		Key myDesKey = (Key) in.readObject();
		in.close();
		file_in.close();
                 
		// -Use the key to decrypt the incoming message from socket s.	
                		// maximum length of the message is 1024 bytes
		byte b_message[] = new byte[1024];
		byte temp;
		Cipher c = Cipher.getInstance("DES/ECB/PKCS5Padding");
		c.init(Cipher.DECRYPT_MODE, myDesKey);

		CipherInputStream cipher_in = new CipherInputStream(s.getInputStream(),
				c);
		// read stream byte by byte
		for(int i =0;(temp = (byte) cipher_in.read()) > 0;i++ ){
                                      b_message[i++] = temp;  
                }
		cipher_in.close();
		s.close();

		// -Print out the decrypt String to see if it matches the orignal message.
                // output message
		String message = new String(b_message, "UTF-8");
		System.out.println("Decrypted message : "+message);
	}
}