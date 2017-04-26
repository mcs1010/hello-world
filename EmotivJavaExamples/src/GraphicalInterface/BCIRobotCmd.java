package GraphicalInterface;


import java.awt.AWTException;
import java.awt.Robot;



import com.sun.jna.platform.win32.WinDef.*;
import com.sun.jna.platform.win32.User32;
import java.awt.event.KeyEvent;
/**
 * Class to connect to various robotics clients using the windows API to send keystrokes to the application
 * @author Stephen Bierly
 *
 */

public class BCIRobotCmd{
	private HWND mmi;
	private HWND robotClient;
	private Robot key;
	private String robotAppName;
	private int Forward=KeyEvent.VK_UP ;
	private int Back = KeyEvent.VK_DOWN;
	private int Left = KeyEvent.VK_LEFT;
	private int Right = KeyEvent.VK_RIGHT;
	private int Stop = KeyEvent.VK_SPACE;
	private final String nullString = null;
	/**
	 * BCIRobotCmd constructor
	 * @param appName The application name of the robot client 
	 */
	public BCIRobotCmd(String appName){

			robotAppName = appName;
			robotClient = User32.INSTANCE.FindWindow(nullString, robotAppName);
			mmi = User32.INSTANCE.FindWindow(nullString,BCIConstants.APP_NAME);
			try {
				key = new Robot();
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				System.err.println("Failed to create virtual key simulator.");
				e.printStackTrace();
			}

	}
	
	/**
	 * Sends the Forward key value to the specified application
	 */
	public void forward(){
			User32.INSTANCE.SetForegroundWindow(robotClient);
			key.keyPress(Forward);
			key.keyRelease(Forward);
			User32.INSTANCE.SetForegroundWindow(mmi);
			
	}
	/**
	 * Sends the Back key value to the specified application
	 */
	public  void back(){
	
			User32.INSTANCE.SetForegroundWindow(robotClient);
			key.keyPress(Back);
			key.keyRelease(Back);
			User32.INSTANCE.SetForegroundWindow(mmi);

	}
	/**
	 * Sends the left key value to the specified application
	 */
	public  void left(){

		User32.INSTANCE.SetForegroundWindow(robotClient);
		key.keyPress(Left);
		key.keyRelease(Left);
		User32.INSTANCE.SetForegroundWindow(mmi);

		
	}
	/**
	 * Sends the right key value to the specified application
	 */
	public  void right(){

			User32.INSTANCE.SetForegroundWindow(robotClient);
			key.keyPress(Right);
			key.keyRelease(Right);
			User32.INSTANCE.SetForegroundWindow(mmi);
					
	}
	/**
	 * Sends the stop key value to the specified application
	 */
	public void stop(){
		User32.INSTANCE.SetForegroundWindow(robotClient);
		key.keyPress(Stop);
		key.keyRelease(Stop);
		User32.INSTANCE.SetForegroundWindow(mmi);
				
	}
	/**
	 * Set the Robot client's Application name
	 * @param name 
	 */
	public  void setRobotAppName(String name){
		
		robotAppName= name;
	}

	


}
