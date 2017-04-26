package GraphicalInterface;

import java.util.Date;

import com.aldebaran.qimessaging.Application;
import com.aldebaran.qimessaging.CallError;
import com.aldebaran.qimessaging.Future;
import com.aldebaran.qimessaging.Session;
import com.aldebaran.qimessaging.helpers.al.ALAnimatedSpeech;
import com.aldebaran.qimessaging.helpers.al.ALAutonomousLife;
import com.aldebaran.qimessaging.helpers.al.ALBasicAwareness;
import com.aldebaran.qimessaging.helpers.al.ALGazeAnalysis;
import com.aldebaran.qimessaging.helpers.al.ALMemory;
import com.aldebaran.qimessaging.helpers.al.ALMotion;
import com.aldebaran.qimessaging.helpers.al.ALRobotPosture;
import com.aldebaran.qimessaging.helpers.al.ALTextToSpeech;


public class NaoGuiClient{
	
	private static Application application;
    private static ALTextToSpeech tts;
    private static ALMotion motion;
    public static Session session;
    public Future<Void> future;
    
    private static ALMemory memory;
    private static ALBasicAwareness awareness;
    
    private static ALGazeAnalysis gaze;
    private static ALAutonomousLife life;
    private static ALRobotPosture posture;
    private static ALAnimatedSpeech animatedSpeech;
    
    private static Boolean isActive; //stop methods from interfering with each other
    private Date previousTime; //last time Gaze analysis called
    
    private float x,y,theta;
    private boolean shouldMove;
    
    public NaoGuiClient() throws Exception{
    	x = y = theta = 0;
    	shouldMove = false;
    }
    
    
	public static void connect() throws Exception{
		 	application = new Application();
	        Session session = new Session();
	        Future<Void> future = null;
	        try {
		        //future = session.connect("tcp://192.168.0.112");
		        future = session.connect("tcp://169.254.39.105:9559");

	            synchronized (future) {
	                future.wait(1000);
	            }
	            
	            //subscribe?
	            memory = new ALMemory(session);
	            tts = new ALTextToSpeech(session);
	            motion = new ALMotion(session);
	            awareness = new ALBasicAwareness(session);
	            
	            life = new ALAutonomousLife(session);
	            posture = new ALRobotPosture(session);
	            animatedSpeech = new ALAnimatedSpeech(session);
	            gaze= new ALGazeAnalysis(session);
				
	            //init awareness
				tts.say("Starting");
				awareness.setEngagementMode("SemiEngaged");
				awareness.setTrackingMode("Head");
				awareness.setStimulusDetectionEnabled("Sound", true);
				awareness.setStimulusDetectionEnabled("Movement", true);
				awareness.setStimulusDetectionEnabled("People", true);
				//awareness.setStimulusDetectionEnabled("Touch", true);
				awareness.startAwareness();
				motion.wakeUp();
				//someone looking at NAO
				//memory.subscribeToEvent("GazeAnalysis/PersonStartsLookingAtRobot", "onLookingAtRobot::(i)", this);
				
				//to end the program
				memory.subscribeToEvent("MiddleTactilTouched" , "onTouch::(f)", future);
				application.run();
	        } 
	        catch (Exception e) 
	        {
	            e.printStackTrace();
	        }
	}
	
	
	public static void onTouch(Float touch) throws Exception{
		if(touch == 1.0 && !isActive) 
        {
        	System.out.println("Application is Stopping");
            tts.say("Stopping");
            motion.rest();
            awareness.stopAwareness();
            application.stop();
        }
	}
	
	
	
	public static void say(String s) throws Exception{
		System.out.println(s);
        tts.say(s);
	}
	
	/*
	public static void end() throws Exception{
		motion.rest();
        awareness.stopAwareness();
        application.stop();
	}
	*/
	
	public static void moveTo(float x, float y, float theta) throws Exception{
		motion.moveInit();
		motion.moveTo(x, y, theta);
	}
	public static void move(float x, float y, float theta){
		NaoMove newMove = new NaoMove(x,y,theta);
		newMove.start();
	}
	public static void main(String args[]) throws Exception{
		NaoGuiClient.connect();
		NaoGuiClient.move(.2f,0,0);
		NaoGuiClient.move(-.2f,0,0);
		NaoGuiClient.say("Hey");
		//NaoGuiClient.end();
	}
}

class NaoMove extends Thread{
	float x,y,theta;
	NaoMove(float x, float y, float theta){
		this.x = x;
		this.y = y;
		this.theta = theta;
	}
	public void run(){
		try {
			NaoGuiClient.moveTo(x,y,theta);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class NaoSay extends Thread{
	String words;
	NaoSay(String words){
		this.words = words;
	}
	public void run(){
		try {
			NaoGuiClient.say(words);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
