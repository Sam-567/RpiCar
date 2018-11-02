import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.SoftPwm;

//Test

public class CarCode {

    //final GpioController gpio;
    int M1Pin, M2Pin;
    int M1Pin2, M2Pin2;
    int Speed = 0;
    int Angle = 0; // -90 to 0 LEFT || 0 to 90 RIGHT
    int M1Speed = 0;
    int M2Speed = 0;
    //M1 = RIGHT MOTOR
    //M2 = LEFT MOTOR

	
	public static void main(String[] args) throws InterruptedException{
<<<<<<< HEAD
<<<<<<< HEAD
		CarCode main =  new CarCode();
		System.out.println("X");
		Scanner input = new Scanner(System.in);
		
		long LastTime = System.currentTimeMillis();
		while(true) {
			//System.out.println("Received");
			//main.ChangeMotorState(test);
			
			if(input.hasNext()) {
				String test = input.nextLine();
				System.out.println(test);
			} else {
				System.out.println("FALSE");
			}
			System.out.println("TEST");
			TimeUnit.SECONDS.sleep(1);
	    }
	}
	
	public CarCode(){
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
		
		System.out.println("begin");
		
	}
	
	private void Exit(){
		SoftPwm.softPwmStop(M1Pin);
		SoftPwm.softPwmStop(M2Pin);
		SoftPwm.softPwmStop(M1Pin2);
		SoftPwm.softPwmStop(M2Pin2);
		System.out.println("Motors Closed");
		System.exit(0);
	}
	
	public void ChangeMotorState(String input){
		System.out.println("Loop Received");
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
			System.out.println("ANGLE: " + num);
			Angle = num;
			this.move();
			break;
			
		case "Stop":
			Speed = 0;
			Angle = 0;
			System.out.println("Now Stopped");
			break;
			
		case "Exit":
			this.Exit();
			
			
		}
	}
	
	public void Turn(){
		//M2 Left 
		//M1 Right
		M2Speed = Speed + Angle/2; System.out.println("a Left: " + M2Speed);
		M1Speed = Speed - Angle/2; System.out.println("a Right: " + M1Speed);
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
		
		if(M1Speed > 0){ SoftPwm.softPwmWrite(M1Pin2, 0);	SoftPwm.softPwmWrite(M1Pin, M1Speed); 	System.out.println("M1Pin2 Active");}
		else if(M1Speed < 0){ SoftPwm.softPwmWrite(M1Pin, 0);	SoftPwm.softPwmWrite(M1Pin2, -M1Speed); }
		else if(M1Speed == 0){SoftPwm.softPwmWrite(M1Pin, 0); SoftPwm.softPwmWrite(M1Pin2, 0); }
		
		if(M2Speed > 0){ SoftPwm.softPwmWrite(M2Pin2, 0);	 SoftPwm.softPwmWrite(M2Pin, M2Speed); }
		else if(M2Speed < 0){ SoftPwm.softPwmWrite(M2Pin, 0);	 SoftPwm.softPwmWrite(M2Pin2, -M2Speed); }
		else if(M2Speed == 0){SoftPwm.softPwmWrite(M2Pin, 0); 	 SoftPwm.softPwmWrite(M2Pin2, 0); 	System.out.println("M2Pin2 Active");}
		
		
		System.out.println("Right Speed: " + M1Speed + " LeftSpeed: " + M2Speed );
	}
	
}