package eeg;

import java.awt.Color;

import eeg.EmoState.EE_CognitivAction_t;
import eeg.EmoState.EE_EEG_ContactQuality_t;
//import gui.BCITrainingGUI;
//import gui.ContactQuality;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;


public class EEGSignal{
	
	public static int[] cognitivActionList = {
		EE_CognitivAction_t.COG_NEUTRAL.ToInt(),
		EE_CognitivAction_t.COG_PUSH.ToInt(),
		EE_CognitivAction_t.COG_PULL.ToInt(),
		EE_CognitivAction_t.COG_LEFT.ToInt(),
		EE_CognitivAction_t.COG_RIGHT.ToInt()
	};	
	/*public static EE_CognitivAction_t[] cognitivActions = {
		EE_CognitivAction_t.COG_NEUTRAL,
		EE_CognitivAction_t.COG_PUSH,
		EE_CognitivAction_t.COG_PULL,
		EE_CognitivAction_t.COG_LEFT,
		EE_CognitivAction_t.COG_RIGHT
	};*/
	public static  Boolean[] cognitivActionsEnabled = new Boolean[cognitivActionList.length];
	
	private static String [] BatteryPower = {"None","Critical","Low","Medium","High"};
	private static String [] WirelessSignal = {"Poor", "Fair", "Good", "Great"};
	private static Color [] contactStr = {Color.BLACK,Color.RED, Color.ORANGE,Color.YELLOW,Color.GREEN};
	private IntByReference chargelevel = new IntByReference();
	private IntByReference maxCharge = new IntByReference(5);
	private  boolean done =false;
	private int state = 0;
	private IntByReference userID = new IntByReference(0);
	private int count=0;
	

	private  Pointer eState = Edk.INSTANCE.EE_EmoStateCreate();;
	private Pointer emoState = EmoState.INSTANCE.ES_Create();;
	private Pointer eEvent= Edk.INSTANCE.EE_EmoEngineEventCreate();;
	
	public EEGSignal(String ProfileName){
	
		cognitivActionsEnabled[0] = true;
        for (int i = 1; i < cognitivActionList.length; i++)
        {
            cognitivActionsEnabled[i] = true;
        }
	}
	
	public void run(){

		if(Edk.INSTANCE.EE_EngineConnect("Emotiv Systems-5")!=EdkErrorCode.EDK_OK.ToInt()){
			System.out.println("ErrorEmotiv Engine Failed to start up, now exiting");
			System.exit(-1);
		}

		while(true){
			state = Edk.INSTANCE.EE_EngineGetNextEvent(eEvent);
			// New event needs to be handled
			if (state == EdkErrorCode.EDK_OK.ToInt()) {
			
				int eventType = Edk.INSTANCE.EE_EmoEngineEventGetType(eEvent);
				//Edk.INSTANCE.EE_EmoEngineEventGetUserId(eEvent, userID);
				Edk.INSTANCE.EE_GetUserProfile(0, eEvent);
				if(eventType == Edk.EE_Event_t.EE_CognitivEvent.ToInt()) {
					int cogType = Edk.INSTANCE.EE_CognitivEventGetType(eEvent);
					
					if(cogType ==Edk.EE_CognitivEvent_t.EE_CognitivTrainingStarted.getType()) {
						//BCITrainingGUI.updateStatusText("Training started.");
					}
					if(cogType == Edk.EE_CognitivEvent_t.EE_CognitivTrainingCompleted.getType()) {
						//BCITrainingGUI.updateStatusText("Training Completed.");
					}
					if(cogType == Edk.EE_CognitivEvent_t.EE_CognitivTrainingSucceeded.getType()) {

						//BCITrainingGUI.updateStatusText("Training Succeeded.");
					}
					if(cogType == Edk.EE_CognitivEvent_t.EE_CognitivTrainingFailed.getType()) {
						//BCITrainingGUI.updateStatusText("Training Failed.");
					}
					if(cogType == Edk.EE_CognitivEvent_t.EE_CognitivTrainingRejected.getType()) {
						//BCITrainingGUI.updateStatusText("Training Rejected.");
					}
				}
				if(eventType == Edk.EE_Event_t.EE_EmoStateUpdated.ToInt()) {
					Edk.INSTANCE.EE_EmoEngineEventGetEmoState(eEvent, eState);
					
					
						int action = EmoState.INSTANCE.ES_CognitivGetCurrentAction(eState);
						double power = EmoState.INSTANCE.ES_CognitivGetCurrentActionPower(eState);
						if(power!=0)
						{
								if(action==EmoState.EE_CognitivAction_t.COG_PUSH.ToInt()){
									//BCITrainingGUI.updateStatusText("Thought Forward.");				
								}
								if(action==EmoState.EE_CognitivAction_t.COG_PULL.ToInt()){
									//BCITrainingGUI.updateStatusText("Thought Reverse.");
								}
								if(action==EmoState.EE_CognitivAction_t.COG_LEFT.ToInt()){
									//BCITrainingGUI.updateStatusText("Thought Left.");
								}
								if(action==EmoState.EE_CognitivAction_t.COG_RIGHT.ToInt()){
									//BCITrainingGUI.updateStatusText("Thought Right.");
								}
				
						}
						if(count%10==0){
							updateValues();
						}
						count++;
			}
		}
		else if (state != EdkErrorCode.EDK_NO_EVENT.ToInt()) {
				System.out.println("Internal error in Emotiv Engine!");
				break;
			}
    	
		}
		Edk.INSTANCE.EE_EngineDisconnect();
	}//end of run
	
	public void setDone(){
		done=true;
	}
	private void updateValues(){
		
		
		Edk.INSTANCE.EE_EmoEngineEventGetEmoState(eEvent, emoState);
		int quality;
	
		for(int i = 0; i<17; i++){
			if(i==2){
				i++;
			}
			if(i<2){
			quality  = EmoState.INSTANCE.ES_GetContactQuality(emoState, i);
			//BCITrainingGUI.updateSensorData(i, contactStr[quality]);
			}
			else{
				quality  = EmoState.INSTANCE.ES_GetContactQuality(emoState, i-1);
				//BCITrainingGUI.updateSensorData(i-1, contactStr[quality]);
			}
			
			
		}
		//BCITrainingGUI.repaintSensorData();
		EmoState.INSTANCE.ES_GetBatteryChargeLevel(emoState, chargelevel, maxCharge);
		if(chargelevel.getValue()==-1){
			//BCITrainingGUI.updateBatteryPower("None");
		}
		else{
			//BCITrainingGUI.updateBatteryPower(BatteryPower[chargelevel.getValue()]);
		}
		int temp=EmoState.INSTANCE.ES_GetWirelessSignalStatus(emoState);
		//BCITrainingGUI.updateWirelessSignal(WirelessSignal[temp]);
	
	
		
	}
	
	
	public static void StartTrainingCognitiv(EmoState.EE_CognitivAction_t cognitivAction) {
        if (cognitivAction == EmoState.EE_CognitivAction_t.COG_NEUTRAL) {
        	Edk.INSTANCE.EE_CognitivSetTrainingAction(0,EmoState.EE_CognitivAction_t.COG_NEUTRAL.ToInt());
			Edk.INSTANCE.EE_CognitivSetTrainingControl(0, Edk.EE_CognitivTrainingControl_t.COG_START.getType());
        }
        else
            for (int i = 1; i < cognitivActionList.length; i++) {
                if (cognitivAction.ToInt() == cognitivActionList[i]) {
                    
                    if (cognitivActionsEnabled[i]) {
                    	Edk.INSTANCE.EE_CognitivSetTrainingAction(0, cognitivAction.ToInt());
                    	Edk.INSTANCE.EE_CognitivSetTrainingControl(0, Edk.EE_CognitivTrainingControl_t.COG_START.getType());
                    }
                    
                }
            }

    }
	
	 public static void EnableCognitivAction(EmoState.EE_CognitivAction_t cognitivAction, Boolean iBool) {
	        for (int i = 1; i < cognitivActionList.length; i++) {
	            if (cognitivAction.ToInt() == cognitivActionList[i]) {
	                cognitivActionsEnabled[i] = iBool;
	            }
	        }
	    }
	  public static void EnableCognitivActionsList() {
	        long cognitivActions = 0x0000;
	        for (int i = 1; i < cognitivActionList.length; i++) {
	            if (cognitivActionsEnabled[i]) {
	                cognitivActions = cognitivActions | ((long)cognitivActionList[i]);     
	            }
	        }
	        Edk.INSTANCE.EE_CognitivSetActiveActions(0, cognitivActions);
	        
	    }

	public void trainCmd(int OptionsIndex){
		

		if(OptionsIndex ==1) {
			try
			{
				//BCITrainingGUI.updateStatusText("Training Push Command");
				EnableCognitivAction(EmoState.EE_CognitivAction_t.COG_PUSH, true);
				EnableCognitivActionsList();
				StartTrainingCognitiv(EmoState.EE_CognitivAction_t.COG_PUSH);
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}
		if(OptionsIndex == 2)
		{
			try
			{
				//BCITrainingGUI.updateStatusText("Training Pull Command");
				EnableCognitivAction(EmoState.EE_CognitivAction_t.COG_PULL, true);
				EnableCognitivActionsList();
				StartTrainingCognitiv(EmoState.EE_CognitivAction_t.COG_PULL);
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}
		if(OptionsIndex == 3)
		{
			try
			{
				//BCITrainingGUI.updateStatusText("Training Left Command");
				EnableCognitivAction(EmoState.EE_CognitivAction_t.COG_LEFT, true);
				EnableCognitivActionsList();
				StartTrainingCognitiv(EmoState.EE_CognitivAction_t.COG_LEFT);
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}
		if(OptionsIndex == 4)
		{
			try
			{
				//BCITrainingGUI.updateStatusText("Training Right Command");
				EnableCognitivAction(EmoState.EE_CognitivAction_t.COG_RIGHT, true);
				EnableCognitivActionsList();
				StartTrainingCognitiv(EmoState.EE_CognitivAction_t.COG_RIGHT);
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}

	}
	
	public void trainNeutral(){
		
		Edk.INSTANCE.EE_CognitivSetTrainingAction(0,EmoState.EE_CognitivAction_t.COG_NEUTRAL.ToInt());
		Edk.INSTANCE.EE_CognitivSetTrainingControl(0, Edk.EE_CognitivTrainingControl_t.COG_START.getType());
		

	}
}
