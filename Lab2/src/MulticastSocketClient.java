import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class MulticastSocketClient {

    public static void main(String[] args) throws UnknownHostException {

    }
    
    public void test(String inetaddr, int port) throws UnknownHostException {
        InetAddress address = InetAddress.getByName(inetaddr);        // Get the address that we are going to connect to.

        
        byte[] buf = new byte[256];				// Create a buffer of bytes, which will be used to store
        										// the incoming bytes containing the information from the server.
        										// Since the message is small here, 256 bytes should be enough.
        
        try (MulticastSocket clientSocket = new MulticastSocket(port)){	// Create a new Multicast socket (that will allow other sockets/programs
        	clientSocket.joinGroup(address);							// to join it as well.
            
            while (true) {
                // Receive the information and print it.
                DatagramPacket msgPacket = new DatagramPacket(buf, buf.length);
                clientSocket.receive(msgPacket);

                String msg = new String(buf, 0, buf.length);
                System.out.println("Client receives this shit: " + msg);
            }
        } catch (IOException ex) {
          ex.printStackTrace();
        }
    }
}