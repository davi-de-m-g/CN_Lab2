import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
//http://www.java2s.com/Tutorials/Java/Java_Network/0050__Java_Network_UDP_Multicast.htm
//http://www.nakov.com/inetjava/lectures/part-1-sockets/InetJava-1.5-UDP-and-Multicast-Sockets.html

public class MulticastSocketServer {
    
    final static String INET_ADDR = "224.0.0.3";
    final static int PORT = 8888;

    public static void main(String[] args) throws UnknownHostException, InterruptedException {

    }
    
    public static void test(String inetaddr, int port) throws UnknownHostException, InterruptedException {
        // Get the address that we are going to connect to.ta
        InetAddress addr = InetAddress.getByName(inetaddr);
     
        // Open a new DatagramSocket, which will be used to send the data.
        try (DatagramSocket serverSocket = new DatagramSocket()) {
            for (int i = 0; i < 5; i++) {
                String msg = "Sent message no " + i;

                // Create a packet that will contain the data
                // (in the form of bytes) and send it.
                DatagramPacket msgPacket = new DatagramPacket(msg.getBytes(), msg.getBytes().length, addr, port);
                serverSocket.send(msgPacket);
     
                System.out.println("Server sents: " + msg);
                Thread.sleep(500);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}