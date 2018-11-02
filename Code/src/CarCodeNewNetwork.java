import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Pattern;
/*
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.SoftPwm;
*/

public class CarCodeNewNetwork {

    //final GpioController gpio;
    int M1Pin, M2Pin;
    int M1Pin2, M2Pin2;
    int Speed = 0;
    int Angle = 0; // -90 to 0 LEFT || 0 to 90 RIGHT
    int M1Speed = 0;
    int M2Speed = 0;
    //M1 = RIGHT MOTOR
    //M2 = LEFT MOTOR
    static final int PORT = 2079;
	static ServerSocket serverSocket;
	static Socket server;
	static DataInputStream in;
	static DataOutputStream out;
	
	public static void main(String[] args) throws InterruptedException, IOException{
		CarCode main =  new CarCode();
		
		/*Scanner input = new Scanner(System.in);
		while(true) {
			String test = input.nextLine();
			out.writeUTF("Received");
			main.ChangeMotorState(test);
	    }
		*/
	    
		serverSocket = new ServerSocket(PORT);
		serverSocket.setSoTimeout(100000);

        server = serverSocket.accept();
        in = new DataInputStream(server.getInputStream());
        out = new DataOutputStream(server.getOutputStream());
        
        while(true) {
        	if(in.available() > 0){
        		String input = in.readUTF();
        		out.writeUTF("received: " + input);
        		System.out.println("received: " + input);
        		main.ChangeMotorState(input);
        	}
        }
	}
	
	public CarCodeNewNetwork(){
		super();
		//gpio = GpioFactory.getInstance();
		Gpio.wiringPiSetup(); //This better not z******* fix it
		
		M1Pin = 8;
		M1Pin2 = 7;
		M2Pin = 0;
		M2Pin2 = 3;
		SoftPwm.softPwmCreate(M1Pin, 0, 100);
		SoftPwm.softPwmCreate(M2Pin, 0, 100);
		SoftPwm.softPwmCreate(M1Pin2, 0, 100);
		SoftPwm.softPwmCreate(M2Pin2, 0, 100);
		
		this.move();
		
		out.writeUTF("begin");
		
	}
	
	private void Exit(){
		SoftPwm.softPwmStop(M1Pin);
		SoftPwm.softPwmStop(M2Pin);
		SoftPwm.softPwmStop(M1Pin2);
		SoftPwm.softPwmStop(M2Pin2);
		out.writeUTF("Motors Closed");
		server.close();
		serverSocket.close();
		System.exit(0);
	}
	
	public void ChangeMotorState(String input) throws IOException{
		out.writeUTF("Loop Received");
		String numString  = input.replaceAll("([^-0-9])+", "");
		int num = (numString.isEmpty()) ? -200 : Integer.parseInt(numString);
		String Command = input.replaceAll("[^A-Za-z]+", "");
		
		switch(Command){
		case "Forward":
			if(num == -200){
				num = 100;
			}
			Speed = num;
			this.move();			
			break;
		
		case "Turn":
			out.writeUTF("ANGLE: " + num);
			Angle = num;
			this.move();
			break;
			
		case "Stop":
			Speed = 0;
			Angle = 0;
			out.writeUTF("Now Stopped");
			break;
			
		case "Exit":
			this.Exit();
			
			
		}
	}
	
	public void Turn() throws IOException{
		//M2 Left 
		//M1 Right
		M2Speed = Speed + Angle/2; out.writeUTF("a Left: " + M2Speed);
		M1Speed = Speed - Angle/2; out.writeUTF("a Right: " + M1Speed);
		Boolean Wrong = false;
		
		if(M2Speed > 100){
			M1Speed -= M2Speed -100;
			M2Speed = 100;
			Wrong = true;
		}
		if(M1Speed > 100){
			M2Speed -= M1Speed -100;
			M1Speed = 100;
			Wrong = true;			
		}
		if(M2Speed < -100){
			if(Wrong){
				M2Speed = -100;
			}
			else {
				int temp = M1Speed - (M2Speed + 100);
				M1Speed = (temp < -100) ? 100 : temp;
				M2Speed = -100;
				Wrong = true;
			}
		}
		if(M1Speed < -100){
			if(Wrong){
				M1Speed = -100;
			}
			else {
				int temp = M2Speed - (M1Speed + 100);
				M2Speed = (temp < -100) ? -100 : temp;
				M1Speed = -100;
				Wrong = true;
			}
		}
		
			
		
	}
	
	
	public void move(){
		this.Turn();

		//Reset all pins to off (Prevent Short)
		
		if(M1Speed > 0){ SoftPwm.softPwmWrite(M1Pin2, 0);	SoftPwm.softPwmWrite(M1Pin, M1Speed); 	out.writeUTF("M1Pin2 Active");}
		else if(M1Speed < 0){ SoftPwm.softPwmWrite(M1Pin, 0);	SoftPwm.softPwmWrite(M1Pin2, -M1Speed); }
		else if(M1Speed == 0){SoftPwm.softPwmWrite(M1Pin, 0); SoftPwm.softPwmWrite(M1Pin2, 0); }
		
		if(M2Speed > 0){ SoftPwm.softPwmWrite(M2Pin2, 0);	 SoftPwm.softPwmWrite(M2Pin, M2Speed); }
		else if(M2Speed < 0){ SoftPwm.softPwmWrite(M2Pin, 0);	 SoftPwm.softPwmWrite(M2Pin2, -M2Speed); }
		else if(M2Speed == 0){SoftPwm.softPwmWrite(M2Pin, 0); 	 SoftPwm.softPwmWrite(M2Pin2, 0); 	out.writeUTF("M2Pin2 Active");}
		
		
		out.writeUTF("Right Speed: " + M1Speed + " LeftSpeed: " + M2Speed );
	}
	
}
