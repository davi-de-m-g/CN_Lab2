import java.net.UnknownHostException;

public class Main {
	//client vars
    final static String INET_ADDR = "224.0.0.3";
    final static int PORT = 8888;
    
    
    public static void main(String[] args) throws UnknownHostException, InterruptedException  {
    	MulticastSocketClient clientje = new MulticastSocketClient();
    	MulticastSocketServer servertje = new MulticastSocketServer();
    	
    	servertje.test(INET_ADDR, PORT);
    	clientje.test(INET_ADDR, PORT);
    	
    	
    	//DHCPDing ding = new DHCPDing();
    	//servertje.sendDing(ding);
    	
    }

}
