package Client;
// File Name GreetingClient.java
import java.net.*;
import java.util.Scanner;
import java.io.*;

public class ClientTest {

   public static void main(String [] args) {
      //String serverName = args[0];
      //int port = Integer.parseInt(args[1]);
	  System.out.println("CLINET");
	  System.out.println("PORT: ");
	  Scanner scanner = new Scanner(System.in);
	  int port = Integer.parseInt(scanner.next());
	  System.out.println("SERVER NAME: ");
	  String serverName = scanner.next();
	  System.out.println("Proceeding");
      try {
         System.out.println("Connecting to " + serverName + " on port " + port);
         Socket client = new Socket(serverName, port);
         
         System.out.println("Just connected to " + client.getRemoteSocketAddress());
         OutputStream outToServer = client.getOutputStream();
         DataOutputStream out = new DataOutputStream(outToServer);
         
         out.writeUTF("Hello from " + client.getLocalSocketAddress());
         InputStream inFromServer = client.getInputStream();
         DataInputStream in = new DataInputStream(inFromServer);
         
         System.out.println("Server says " + in.readUTF());
         client.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}