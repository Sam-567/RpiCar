
public class Turn {

	int leftMSpeed, rightMSpeed, angle;
	int forwardSpeed;
	public static void main(String[] args){
		Turn main = new Turn();
	}
	
	public Turn(){
		super();
		angle = 10;
		forwardSpeed = 80;
		
		rightMSpeed = forwardSpeed + angle;
		if(rightMSpeed > 100 ){
			System.out.println("right >100");
			int overflow  = rightMSpeed - 100;
			rightMSpeed = 100;
			leftMSpeed = -overflow;
		}
		
		leftMSpeed += forwardSpeed - angle;
		if(leftMSpeed < -100 ){
			System.out.println("left <-100");
			int overflow  = leftMSpeed + 100;
			leftMSpeed = -100;
			rightMSpeed = (rightMSpeed-overflow > 100) ? 100 : rightMSpeed-overflow;
			//rightMSpeed -= overflow;
		}
		System.out.println("ANGLE: " + angle);
		System.out.println("ForwardSpeed: " + forwardSpeed);
		System.out.println("LeftMSpeed: " + leftMSpeed);
		System.out.println("RightMSpeed: " + rightMSpeed);
	}
	
}
