package GraphicalInterface;

import java.io.IOException;

import Devices.Command.Commands;

/**
 * Starts the Hercules gui client and handles the low level control of hercules
 * 
 * 
 */
public class HerculesControl {
	private static String APP_NAME ="Hercules: The Story of a Mighty Man";//Hercules' app name "Hercules: The Story of a Mighty Man"
	private BCIRobotCmd robot;
	public HerculesControl(){
		robot = new BCIRobotCmd(APP_NAME);
		startHerculesGui();
		
	}
	/**
	 * @return Returns the application name for the hercules gui client
	 */
	public static String getAppName() {
		return APP_NAME;
	}
	/**
	 * Starts the hercules gui client application
	 */
	private static void startHerculesGui(){//starts the hercules gui client
		try {
			Runtime.getRuntime().exec("java -jar herculesClient.jar");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Takes a command and executes that command
	 * @param command
	 */
	public void executeCommand(Commands command){
		switch (command){
		case MoveForward:
			robot.forward();
			break;
		case MoveBack:
			robot.back();
			break;
		case MoveLeft:
			robot.left();
			break;
		case MoveRight:
			robot.right();
			break;
		case Stop:
			robot.stop();
			break;
			
		case None:
			break;
		default:
				System.err.println("Invalid command being sent to Hercules Control.");
			break;
		}
	}

}
