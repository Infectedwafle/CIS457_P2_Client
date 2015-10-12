import java.io.*;
import java.net.*;
import java.util.Scanner;

class Client {
	public static void main(String args[]) throws Exception {
	  DatagramSocket clientSocket = new DatagramSocket();
	  BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
	  String ip_address, portStr;
	  Scanner input = new Scanner(System.in);
	  System.out.println("Enter an IP address, default loopback address is 127.0.0.1, enter 'd' for default");
	  ip_address = input.next();
	  if(ip_address.equals("d")){
	    ip_address = "127.0.0.1";
	  }
	  System.out.println("Enter a port, default port is 9876, enter 'd' for default");
	  portStr = input.next();	
	  if(portStr.equals("d")){
	    portStr = "9876";
	  }
	  if(checkIP(ip_address) != true || checkPort(portStr) != true){
	    System.out.print("Not a valid ip address or port.");
	    System.exit(0);
	  }
	  System.out.println("Enter a domain name: ");
	  String message = inFromUser.readLine();
	  byte[] sendData = new byte[1024];
	  sendData = message.getBytes();
	  InetAddress IPAddress = InetAddress.getByName(ip_address.toString());
	  int port = Integer.parseInt(portStr); 
	  DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
	  clientSocket.send(sendPacket);
	  byte[] receiveData = new byte[1024];
	  DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
	  clientSocket.receive(receivePacket);
	  String serverMessage = new String(receivePacket.getData());
	  System.out.println("Got from server: " + serverMessage);
	}

	public static boolean checkIP(String ip){
	  String[] tokens = ip.split("\\.");
	  if (tokens.length > 4) return false;
	  int token;
	  try{
	    for (int i = 0; i < 4; i++){
	      try{
		token = Integer.parseInt(tokens[i]);
	      } catch(NumberFormatException e){
		return false;
	      }
	      if (i != 3) {
		if(token < 0 || token > 255){
		  return false;
		}
	      }	else if (token <1 || token > 254){
		return false;
	      }
	    }
	  }catch(ArrayIndexOutOfBoundsException e){
	    e.printStackTrace(System.out);
	  }
	  return true;
	}

	public static boolean checkPort(String port){
	  int input;
	  try{
	    input = Integer.parseInt(port);
	  } catch(NumberFormatException e){
	    return false;
	  }
	  if(input < 0 || input > 65535){
	    return false;
	  }
	  return true;
	}
}