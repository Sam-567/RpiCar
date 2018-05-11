import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.SoftPwm;
public class TestPWM {
	private static int PIN_NUMBER = 1;
	public static void main(String[] args) throws InterruptedException {
		
		// initialize wiringPi library, this is needed for PWM
		Gpio.wiringPiSetup();
		// softPwmCreate(int pin, int value, int range)
		// the range is set like (min=0 ; max=100)
		SoftPwm.softPwmCreate(PIN_NUMBER, 0, 100);
		int counter = 0;
		SoftPwm.softPwmWrite(PIN_NUMBER, 100);

	}
}