import java.io.IOException;


public class Test {
	//client vars
    final static String INET_ADDR = "224.0.0.3";
    final static int PORT = 8888;
    
    
    public static void main(String[] args) throws InterruptedException, IOException  {
    	MulticastSocketClient clientje = new MulticastSocketClient();
    	MulticastSocketServer servertje = new MulticastSocketServer();
    	
    	//servertje.test(INET_ADDR, PORT);
    	//clientje.test(INET_ADDR, PORT);
    	//Dhcpclient.printMacAddress();
    	
    	///
    	DHCPServer server = new DHCPServer(PORT);
    	DHCPClient client = new DHCPClient(PORT);
    	//servertje.sendDing(ding);
    	
    	
    }
    
    
}