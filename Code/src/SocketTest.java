
// File Name GreetingServer.java
import java.net.*;
import java.util.Scanner;
import java.io.*;

public class SocketTest extends Thread {
   /*private ServerSocket serverSocket;
   
   public SocketTest(int port) throws IOException {
      serverSocket = new ServerSocket(port);
      serverSocket.setSoTimeout(1000000);
   }

   public void run() {
      while(true) {
         try {
            System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
            Socket server = serverSocket.accept();
            
            System.out.println("Just connected to " + server.getRemoteSocketAddress());
            DataInputStream in = new DataInputStream(server.getInputStream());
            
            System.out.println(in.readUTF());
            DataOutputStream out = new DataOutputStream(server.getOutputStream());
            out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress()
               + "\nGoodbye!");
            server.close();
            
         } catch (SocketTimeoutException s) {
            System.out.println("Socket timed out!");
            break;
         } catch (IOException e) {
            e.printStackTrace();
            break;
         }
      }
   }
   
   public static void main(String [] args) {
      //int port = Integer.parseInt(args[0]);
	  System.out.println("SERVER");
	  System.out.println("PORT: ");
	  Scanner scanner = new Scanner(System.in);
	  int port = Integer.parseInt(scanner.next());
	  System.out.println("Proceeding");
      try {
         Thread t = new SocketTest(port);
         t.start();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }*/

    static final int PORT = 2079;
	static ServerSocket serverSocket;
	static Socket server;
	static DataInputStream in;
	static DataOutputStream out;
	public static void main(String[] args) throws InterruptedException, IOException{
		//CarCode main =  new CarCode();
		
		/*Scanner input = new Scanner(System.in);
		while(true) {
			String test = input.nextLine();
			out.writeUTF("Received");
			main.ChangeMotorState(test);
	    }
		*/
	    
		serverSocket = new ServerSocket(PORT);
		serverSocket.setSoTimeout(100000);
		while(server == null) {
	        server = serverSocket.accept();
		}
        in = new DataInputStream(server.getInputStream());
        out = new DataOutputStream(server.getOutputStream());
        System.out.println("server connected");
        while(true) {
        	if(in.available() > 0){
        		String input = in.readUTF();
        		out.writeUTF("received: " + input);
        		System.out.println("got: " + input);
        		out.flush();
        		//main.ChangeMotorState(input);
        	}
        }
	}
}