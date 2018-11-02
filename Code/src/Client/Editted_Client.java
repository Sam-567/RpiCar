package Client;
// File Name GreetingClient.javaa
import java.net.*;
import java.util.Scanner;
import java.io.*;

public class Editted_Client {

   public static void main(String [] args) {
      //String serverName = args[0];
      //int port = Integer.parseInt(args[1]);
	  System.out.println("CLIENT");
	  System.out.println("PORT: ");
	  Scanner scanner = new Scanner(System.in);
	  int port = Integer.parseInt(scanner.next());
	  System.out.println("SERVER NAME: ");
	  String serverName = scanner.next();
      try {
         Socket client = new Socket(serverName, port);
         
         OutputStream outToServer = client.getOutputStream();
         DataOutputStream ToServer = new DataOutputStream(outToServer);
         
         
         InputStream inFromServer = client.getInputStream();
         //DataInputStream FromServer = new DataInputStream(inFromServer);
         BufferedReader FromServer = new BufferedReader( new InputStreamReader(inFromServer));
         System.out.println("Connected");
         while(true) {
        	 while(FromServer.ready()) {
        		 //System.out.println("Message available from server");
        		 System.out.println(FromServer.readLine());
        		 //FromServer.readLine();
        		 //System.out.println("PAssed");
        	 }
        		 
        	
        	 if(scanner.hasNext()) {
        		 ToServer.writeUTF(scanner.next()+"\n \n");
        	 }
        	 
         }
         //client.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}