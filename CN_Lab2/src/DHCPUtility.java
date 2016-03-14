import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.BitSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utilities to use between classes. 
 * @author Davide Guglielmi
 * 		   Arthur Populaire
 *
 */
public class DHCPUtility {
	static byte[] macAddr = null;
	
	/**
	 * Returns the mac address "05-05-05-05-05-05" for easy usage for the assignment. 
	 * 
	 */
	public static byte[] getMacAddress() {
		macAddr = new byte[6];
		for(int i = 0; i < macAddr.length; i++) macAddr[i] = (byte)0x05;

		assert(macAddr != null);
		return macAddr;
	}

	public static byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}
	
	public static void printMacAddress() {
			System.out.print("Hardware Address for current adapter: ");
			/*
			 * Extract each array of mac address and convert it to hexa with the
			 * . * following format 08-00-27-DC-4A-9E.
			 */
			for (int i = 0; i < macAddr.length; i++) {
				System.out.format("%02X%s", macAddr[i], (i < macAddr.length - 1) ? "-"
						: "");
			}
			System.out.print("\n");
	}
	
	/**
	 * Converts a byte array to a BitSet
	 * Note: bitset format left (least sig bit) to right (most sig bit)
	 * @param byteArray - the array of bytes to convert
	 * @return the BitSet representation of a byte array
	 */
	public static BitSet bytes2Bits(byte[] byteArray) {
		BitSet bits = new BitSet(8*byteArray.length); //8*numBytes
		
		//for every byte 
		for (int i=0; i < byteArray.length; i++) {
			int temp = (byteArray[i] < 0 ? byteArray[i] + 256 : byteArray[i]);
			for (int j=7; j >= 0; j--) {
				if (temp - Math.pow(2,j) >= 0) {
					bits.flip((byteArray.length-1-i)*8+j);
					temp -= Math.pow(2,j);
				}
			}
		} 
		return bits;
	}
	
	/**
	 * Converts a number to a BitSet
	 * Note: bitset format left (least sig bit) to right (most sig bit)
	 * @param num - the number to convert
	 * @return the BitSet representation of the input number
	 */
	public static BitSet num2BitSet(Long num) {	
		int size;
		long temp = num;
		boolean done = false;
		for (size=0; !done; size++) {
			if (temp-Math.pow(2,size) < 0) done = true;
		}
		BitSet bits = new BitSet(size);
		
		//long temp = num;
		for (int j=size; j >= 0; j--) {
			if (temp - Math.pow(2,j) >= 0) {
				bits.flip(j);
				/*System.out.println("flipping bit " + j + "(" 
							+ Math.pow(2,j) +")"+ "from array element " + i
							+ "(" + temp +")");*/
				temp -= Math.pow(2,j);
			}
		}	
		return bits;
	}

	
	/**
	 * Converts a BitSet to a ByteArray
	 * Note: bitset format left (least sig bit) to right (most sig bit)
	 * @param bitset - the bitset to convert to byte array
	 * @param bytes - number of bytes to return
	 * @return the ByteArray representation of the bitset
	 */
	public static byte[] bits2Bytes(BitSet bs, int bytes) {
		byte[] ba = new byte[bytes];
		
		//System.out.println(bs);
		//System.out.println(ba.length + " bytes representation");
		
		//for every byte 
		for (int i=0; i < ba.length; i++) {
			BitSet octet = bs.get(i*8, i*8+7+1);
			byte temp = 0;
			for (int j = octet.nextSetBit(0); j >=0; j = octet.nextSetBit(j+1)) {
				temp += Math.pow(2, j);
			}
			//System.out.println(temp + " | " + octet.toString());
			ba[ba.length -1- i] = temp;
		} 
		/*for (int k=0; k < ba.length; k++) {
			System.out.println("byte " + k + ": "+ ba[k]);
		}*/
		return ba;
	}
	
	
	public static String byteToHex(byte b) {
		String str = new String(Integer.toHexString(new Integer(b & 0xff)));
		return str;
	}
	
	/**
	 * Converts the first 4 byte values of a byte array to an ip string.
	 * @param ba - a byte array of 4 bytes
	 * @return - IP String representation
	 */
	public static String printIP(byte[] ba) {
		assert(ba.length >= 4);
		return printIP(ba[0], ba[1], ba[2], ba[3]);
	}

	/**
	 * Converts 4 byte values to an ip string
	 * @param a - 1st byte value
	 * @param b - 2nd byte value
	 * @param c - 3rd byte value
	 * @param d - 4th byte value
	 * @return - IP String representation
	 */
	public static String printIP(byte a, byte b, byte c, byte d) {
		String str = "";
		str += (a & 0xff) + "." + (b & 0xff) + "." + (c & 0xff) + "." + (d & 0xff);
		return str;
	}

	public static String printString(byte[] ba) {
		return printString(ba,0);
	}
	public static String printString(byte[] ba, int startIndex) {
		String str = "";
		for (int i=startIndex; i < ba.length; i++) {
			if (ba[i] != 0) str += (char) ba[i];
		}
		return str;
	}
	
	public static byte[] stringToBytes(String str) {
		return str.getBytes();
	}
	
	public static String printMAC(byte a, byte b, byte c, byte d, byte e, byte f) {
		String str;
		//Ethernet MAC Address?
		str = DHCPUtility.byteToHex(a) + "-" +
			  DHCPUtility.byteToHex(b) + "-" +
			  DHCPUtility.byteToHex(c) + "-" +
			  DHCPUtility.byteToHex(d) + "-" +
			  DHCPUtility.byteToHex(e) + "-" +
			  DHCPUtility.byteToHex(f);
		return str;
	}

	public static String printMAC(byte[] ba) {
		assert(ba.length >= 6);
		return printMAC(ba[0], ba[1], ba[2], ba[3], ba[4], ba[5]);
	}

	public static boolean isEqual(byte[] a, byte[] b) {
		boolean isEqual = true;
		for (int i=0; i < Math.min(a.length, b.length); i++) {
			if (a[i] != b[i]) {
				isEqual = false;
			}
		}
		return isEqual;
	}

	public static byte[] strToIP(String str) {
		String[] ip = {""};
		byte[] baIP = null;
		Pattern regex =  Pattern.compile(".*?(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}).*");
		Matcher m = regex.matcher(str);
		if (m.matches()) {
			ip = m.group(1).split("\\.");
			baIP = new byte[4];
			baIP[0] = new Byte((byte) Integer.parseInt(ip[0]));
			baIP[1] = new Byte((byte) Integer.parseInt(ip[1]));
			baIP[2] = new Byte((byte) Integer.parseInt(ip[2]));
			baIP[3] = new Byte((byte) Integer.parseInt(ip[3]));
			assert(DHCPUtility.printIP(baIP).compareTo(m.group(1)) == 0) : 
				"IP conversion incorrect: " + m.group(1) + 
				" conversion: " + DHCPUtility.printIP(baIP);
		} 
		return baIP;
	}
	
	public static Long strToLong(String str) {
		Long num = null;
		Pattern regex =  Pattern.compile(".*?(\\d+).*");
		Matcher m = regex.matcher(str);
		if (m.matches()) {
			num = Long.parseLong(m.group(1));
		}
		return num;	
	}
	

	public static long BitSetToLong(BitSet bs) {
		System.out.println(bs);
		long temp = 0;
		for (int j = bs.nextSetBit(0); j >=0; j = bs.nextSetBit(j+1)) {
			temp += Math.pow(2, j);
		}
		return temp;
	}
	public static String printBitSet(BitSet bs) {
		String str = Long.toString(BitSetToLong(bs));
		System.out.println(str);
		return str;
	}	
}
