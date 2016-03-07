import java.io.IOException;
import java.net.UnknownHostException;

public class Main {
	//client vars
    final static String INET_ADDR = "224.0.0.3";
    final static int PORT = 8888;
    
    
    public static void main(String[] args) throws InterruptedException, IOException  {
    	MulticastSocketClient clientje = new MulticastSocketClient();
    	MulticastSocketServer servertje = new MulticastSocketServer();
    	DHCPClient Dhcpclient = new DHCPClient();
    	
    	//servertje.test(INET_ADDR, PORT);
    	//clientje.test(INET_ADDR, PORT);
    	//Dhcpclient.printMacAddress();
    	
    	DHCPClient ding = new DHCPClient();
    	//servertje.sendDing(ding);
    	
    }

}
