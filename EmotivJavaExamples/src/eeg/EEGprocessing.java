package eeg;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

import Devices.SignalAnalyzer;
import GraphicalInterface.ContactQualityPanel;
import GraphicalInterface.GuiClient;
import GraphicalInterface.HerculesControl;
import GraphicalInterface.MainBciGui;
import GraphicalInterface.NaoGuiClient;
import GraphicalInterface.SignalCanvas;
import Iedk.EdkErrorCode;
import Iedk.EmotivCloudClient;
import eeg.EmoState.EE_CognitivAction_t;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

/**
 * This class contains the methods and attributes related to the 
 * processing of the EEG Data retreived from the Emotiv EEG 
 * headset.
 * 
 *
 *
 */
public class EEGprocessing {
	
	
	/**
	 * Pointer to the current state of the Emotiv Device
	 */
	
	/**
	 * An array that represents the available actions to be taken
	 */
	public static int[] cognitivActionList = {
			EE_CognitivAction_t.COG_NEUTRAL.ToInt(),
			EE_CognitivAction_t.COG_PUSH.ToInt(),
			EE_CognitivAction_t.COG_PULL.ToInt(),
			EE_CognitivAction_t.COG_LEFT.ToInt(),
			EE_CognitivAction_t.COG_RIGHT.ToInt() 
	};
	/**
	 * An array that holds the information regarding the status of an action
	 */
	public static Boolean[] cognitivActionsEnabled = new Boolean[cognitivActionList.length];
	/**
	 * String array holding human readable forms of the battery power
	 */
	private String[] BatteryPower = { "None", "Critical", "Low", "Medium","High" };
	/**
	 * String array holding human readable forms of the wireless signal
	 */
	private String[] WirelessSignal = { "Poor", "Fair", "Good", "Great" };
	/**
	 * String representing the human readable battery power
	 */
	private String currentBatteryPower;
	/**
	 * String representing the human readable wireless signal
	 */
	private String currentWirelessSignal;
	/**
	 * An array of Colors representing the various states of contact
	 */
	private static Color[] contactStr = { Color.BLACK, Color.RED, Color.ORANGE,Color.YELLOW, Color.GREEN };
	/**
	 * Int representing the current charge level
	 */
	private IntByReference chargeLevel = new IntByReference();
	/**
	 * Int representing the max charge level
	 */
	private IntByReference maxCharge = new IntByReference(5);
	private boolean done = false;
	private int state = 0;
	boolean hasProfile = false;
	
	//private IntByReference userID = new IntByReference(0);
	//private static IntByReference engineUserID = new IntByReference(0);
	//private static IntByReference userCloudID = new IntByReference(0);
	//private static IntByReference profileID = null;
	//private static String profileName = "daddy";
	
	public static IntByReference UserID = null;
	public static IntByReference userCloudID = null;
	public static IntByReference profileID = null;
	public static String profileName = "daddy";
	
	private Pointer eState;
	//private Pointer emoState;
	private Pointer eEvent;
	
	
	private int count = 0;
	private Pointer hData;
	private IntByReference nSamplesTaken = new IntByReference(0);
	private int array[] = new int[5];
	private boolean readyToCollect = false;
	private SignalAnalyzer sA = new SignalAnalyzer();

	/**
	 * The default constructor for EEGprocessing. It will initialize 
	 * the states of the EEG headset, enable the nessecary actions, 
	 * and connect to the headset
	 */
	
	
	public EEGprocessing() {
		
		String userName = "0104826"; //"0104826" "TESTING" "daddy"
		String password = "Soccer20";
		String profileName = "daddy";
		
		UserID = new IntByReference(0);
		userCloudID  = new IntByReference(0);
		profileID 	 = new IntByReference(-1);
		
		eState = Edk.INSTANCE.EE_EmoStateCreate();
		//emoState = EmoState.INSTANCE.ES_Create();
		eEvent = Edk.INSTANCE.EE_EmoEngineEventCreate();
	
		cognitivActionsEnabled[0] = true;
		for (int i = 1; i < cognitivActionList.length; i++) {
			cognitivActionsEnabled[i] = true;
		}
		
		if (Edk.INSTANCE.EE_EngineConnect("Emotiv Systems-5") != EdkErrorCode.EDK_OK.ToInt()) {
			System.out.println("ErrorEmotiv Engine Failed to start up, now exiting");
			System.exit(-1);
		}
		if (EmotivCloudClient.INSTANCE.EC_Connect()!= EdkErrorCode.EDK_OK.ToInt()) {
			System.out.println("Cannot connect to Emotiv Cloud");
			return;
		}

		if (EmotivCloudClient.INSTANCE.EC_Login(userName, password) != EdkErrorCode.EDK_OK.ToInt()) {
			System.out.println("Your login attempt has failed. The username or password may be incorrect");
			return;
		}

		System.out.println("Logged in as " + userName);

		if (EmotivCloudClient.INSTANCE.EC_GetUserDetail(userCloudID) != EdkErrorCode.EDK_OK.ToInt()) {
			return;
		}
	
		while(!hasProfile) {
			EmoProfileManagement.LoadProfilesFromFile();
			if(EmoProfileManagement.AddNewProfile(userName)) {
				System.out.println("Adding new profile with name: " + userName);
				hasProfile = true;
				EmoProfileManagement.LoadProfilesFromFile();
				EmoProfileManagement.SaveCurrentProfile();
				//System.out.println(EmoProfileManagement.CheckCurrentProfile());
				//System.out.println(EmoProfileManagement.GetProfileName(0)); //Returns profile name	
			} 
			else if(!EmoProfileManagement.AddNewProfile(userName)){
				System.out.println("profile with the username  " + userName + " already exists");
			}
			else { 
				System.out.println("error");
				return;
			}
			
		}
		
	}

	/**
	 * Initializes a new thread instantiated with a new runnable 
	 * that will set in motion the processing of EEG data 
	 */
	public void startEegProcessing() {
		Thread t = new Thread(new processingHandler());
		t.start();
	}

	/**
	 * This function handles the updating of the GUI based upon the 
	 * current state of the EEG Headset
	 */
	public void updateHeadsetStatus() {
		Edk.INSTANCE.EE_EmoEngineEventGetEmoState(eEvent, eState);
		int quality;

		for (int i = 0; i < 17; i++) {
			if (i == 2) {
				i++;
			}
			if (i < 2) {
				quality = EmoState.INSTANCE.ES_GetContactQuality(eState, i);
				ContactQualityPanel.updateSensorContactQuality(i,contactStr[quality]);
			} else {
				quality = EmoState.INSTANCE.ES_GetContactQuality(eState,i - 1);
				ContactQualityPanel.updateSensorContactQuality(i - 1,contactStr[quality]);
			}
		}

		EmoState.INSTANCE.ES_GetBatteryChargeLevel(eState, chargeLevel,maxCharge);
		if (chargeLevel.getValue() == -1) {
			this.setCurrentBatteryPower(BatteryPower[0]);
		} else {
			this.setCurrentBatteryPower(BatteryPower[chargeLevel.getValue()]);
		}

		this.setCurrentWirelessSignal(WirelessSignal[EmoState.INSTANCE.ES_GetWirelessSignalStatus(eState)]);
	}

	public static void StartTrainingCognitiv(EmoState.EE_CognitivAction_t cognitivAction) {
		if (cognitivAction == EmoState.EE_CognitivAction_t.COG_NEUTRAL) {
			Edk.INSTANCE.EE_CognitivSetTrainingAction(0, EmoState.EE_CognitivAction_t.COG_NEUTRAL.ToInt());
			Edk.INSTANCE.EE_CognitivSetTrainingControl(0, Edk.EE_CognitivTrainingControl_t.COG_START.getType());
		} else {
			for (int i = 1; i < cognitivActionList.length; i++) {
				if (cognitivAction.ToInt() == cognitivActionList[i]) {

					if (cognitivActionsEnabled[i]) {
						Edk.INSTANCE.EE_CognitivSetTrainingAction(0,cognitivAction.ToInt()); // 1,2,32,64
						Edk.INSTANCE.EE_CognitivSetTrainingControl(0,Edk.EE_CognitivTrainingControl_t.COG_START.getType());
						//System.out.println("ENABLING:");
					}
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
				cognitivActions = cognitivActions | ((long) cognitivActionList[i]);
			}
		}
		Edk.INSTANCE.EE_CognitivSetActiveActions(0, cognitivActions);

	}

	public void trainCmd(int OptionsIndex) {
		
		Edk.INSTANCE.EE_CognitivSetTrainingControl(0,Edk.EE_CognitivTrainingControl_t.COG_START.getType());
		
		if (OptionsIndex == 0) {
			
			EnableCognitivAction(EmoState.EE_CognitivAction_t.COG_NEUTRAL, true);
			EnableCognitivActionsList();
			StartTrainingCognitiv(EmoState.EE_CognitivAction_t.COG_NEUTRAL);
		}
		if (OptionsIndex == 1) {
			try {
				EnableCognitivAction(EmoState.EE_CognitivAction_t.COG_PUSH,true);
				EnableCognitivActionsList();
				StartTrainingCognitiv(EmoState.EE_CognitivAction_t.COG_PUSH);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		if (OptionsIndex == 2) {
			try {
				EnableCognitivAction(EmoState.EE_CognitivAction_t.COG_PULL,true);
				EnableCognitivActionsList();
				StartTrainingCognitiv(EmoState.EE_CognitivAction_t.COG_PULL);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		if (OptionsIndex == 3) {
			try {
				EnableCognitivAction(EmoState.EE_CognitivAction_t.COG_LEFT,true);
				EnableCognitivActionsList();
				StartTrainingCognitiv(EmoState.EE_CognitivAction_t.COG_LEFT);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		if (OptionsIndex == 4) {
			try {
				EnableCognitivAction(EmoState.EE_CognitivAction_t.COG_RIGHT,true);
				EnableCognitivActionsList();
				StartTrainingCognitiv(EmoState.EE_CognitivAction_t.COG_RIGHT);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

	}

	public void trainNeutral() {
		Edk.INSTANCE.EE_CognitivSetTrainingAction(0,EmoState.EE_CognitivAction_t.COG_NEUTRAL.ToInt()); // set which action to train
		Edk.INSTANCE.EE_CognitivSetTrainingControl(0,Edk.EE_CognitivTrainingControl_t.COG_START.getType()); //start the training
	}
	
	private static void SavingLoadingFunction(int userCloudID, int UserID, boolean save, String profileName) {
		int getNumberProfile = EmotivCloudClient.INSTANCE.EC_GetAllProfileName(userCloudID);

		int result = EmotivCloudClient.INSTANCE.EC_GetProfileId(userCloudID, profileName, profileID);

		if (save) {

			if (profileID.getValue() >= 0) {
				//System.out.println("Profile with " + profileName + " exists.");
				if (EmotivCloudClient.INSTANCE.EC_UpdateUserProfile(userCloudID, UserID, profileID.getValue()) == EdkErrorCode.EDK_OK.ToInt()) {
					System.out.println("Updating action to profile: " + UserID);
				} else {
					System.out.println("updating failed");
				}
			} else if (EmotivCloudClient.INSTANCE.EC_SaveUserProfile(userCloudID, UserID, profileName,EmotivCloudClient.profileType_t.TRAINING.toInt()) == EdkErrorCode.EDK_OK.ToInt())
				System.out.println("Saving finished");
			else
				System.out.println("Saving failed");
			return;
		} else {
			if (getNumberProfile > 0) {
				if (EmotivCloudClient.INSTANCE.EC_LoadUserProfile(userCloudID, UserID, profileID.getValue(),-1) == EdkErrorCode.EDK_OK.ToInt())
					System.out.println("Loading finished");
				else
					System.out.println("Loading failed");
			}
			return;
		}
	}

	public String getCurrentWirelessSignal() {
		return currentWirelessSignal;
	}

	public void setCurrentWirelessSignal(String currentWirelessSignal) {
		this.currentWirelessSignal = currentWirelessSignal;
	}

	public String getCurrentBatteryPower() {
		return currentBatteryPower;
	}

	public void setCurrentBatteryPower(String currentBatteryPower) {
		this.currentBatteryPower = currentBatteryPower;
	}

	class processingHandler implements Runnable {
		@Override
		public void run() {
			long lastTime = 0;	
			int lastCommandId = 0;
			hData = Edk.INSTANCE.EE_DataCreate();
			Edk.INSTANCE.EE_DataSetBufferSizeInSec(1);
			//HerculesControl herc = new HerculesControl();
			//NaoGuiClient Robot = new NaoGuiClient(); 
			EmoState.INSTANCE.ES_GetBatteryChargeLevel(eState, chargeLevel,maxCharge);
			
			while (true) {
				state = Edk.INSTANCE.EE_EngineGetNextEvent(eEvent);
				
				if (state == EdkErrorCode.EDK_OK.ToInt()) { //if state = 0. Lit

					int eventType = Edk.INSTANCE.EE_EmoEngineEventGetType(eEvent);
					Edk.INSTANCE.EE_EmoEngineEventGetUserId(eEvent, UserID);
					
					if (UserID != null) {
						Edk.INSTANCE.EE_DataAcquisitionEnable(UserID.getValue(), true);
						readyToCollect = true;
						
					}
					if (eventType == Edk.EE_Event_t.EE_CognitivEvent.ToInt()) { //if eventType == 256
						
						int cogType = Edk.INSTANCE.EE_CognitivEventGetType(eEvent);
						
						if (cogType == Edk.EE_CognitivEvent_t.EE_CognitivTrainingStarted.getType()) { //If Cog == 1
							MainBciGui.writeToWindow("Training started");
							System.out.println("Training started");
						}
						if (cogType == Edk.EE_CognitivEvent_t.EE_CognitivTrainingSucceeded.getType()) { // If Cog = 2
							Edk.INSTANCE.EE_CognitivSetTrainingControl(UserID.getValue(),Edk.EE_CognitivTrainingControl_t.COG_ACCEPT.getType());
							EmoProfileManagement.SaveCurrentProfile(); //save data to profile
							SavingLoadingFunction(userCloudID.getValue(), UserID.getValue(), true, profileName);
							MainBciGui.writeToWindow("Ready to train next Action");
							System.out.println("Ready to train next Action");
						}
						if (cogType == Edk.EE_CognitivEvent_t.EE_CognitivTrainingFailed.getType()) { //if cog = 3
							MainBciGui.writeToWindow("Training failed");
							System.out.println("Training failed");
						}
						if (cogType == Edk.EE_CognitivEvent_t.EE_CognitivTrainingCompleted.getType()) { // If cog = 4
							MainBciGui.writeToWindow("Training completed");
							System.out.println("Training completed");
						}
						if (cogType == Edk.EE_CognitivEvent_t.EE_CognitivTrainingRejected.getType()) { //if cog = 6
							MainBciGui.writeToWindow("Training rejected");
							System.out.println("Training rejected");
						}
					}
					if (eventType == Edk.EE_Event_t.EE_EmoStateUpdated.ToInt()) { //if eventTpe == 64
						
						Edk.INSTANCE.EE_EmoEngineEventGetEmoState(eEvent, eState);
						
						int action = EmoState.INSTANCE.ES_CognitivGetCurrentAction(eState);
						double power = EmoState.INSTANCE.ES_CognitivGetCurrentActionPower(eState);

						//Query whether the signal is too noisy for Cognitiv detection to be active
						//return detection state (0: Not Active, 1: Active)
						
						if (power != 0) {
							System.out.println("POWER LOOP!");
							if (action == EmoState.EE_CognitivAction_t.COG_PUSH.ToInt()) { //2
								array[0]++;
							}
							if (action == EmoState.EE_CognitivAction_t.COG_PULL.ToInt()) { //4
								array[1]++;
							}
							if (action == EmoState.EE_CognitivAction_t.COG_LEFT.ToInt()) { //32
								array[2]++;
							}
							if (action == EmoState.EE_CognitivAction_t.COG_RIGHT.ToInt()) { //64
								array[3]++;
							}
						}// end power
						
						//Active
						for (int i = 0; i < 5; i++) {
							if (array[i] >= 20) { // >=20 
								if (i == 0) {
									if(System.currentTimeMillis() - lastTime > 3000){
										MainBciGui.writeToWindow("User thought forward");
										//GuiClient.sendCommand("Forward");
										try {
											NaoGuiClient.say("You thought forward");
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										NaoGuiClient.move(.3f, 0, 0);
										lastCommandId = i;
									}
									if(lastCommandId == i){
										lastTime = System.currentTimeMillis();
									}
								}
								if (i == 1) {
									if(System.currentTimeMillis() - lastTime > 3000){
										MainBciGui.writeToWindow("User thought reverse");
										try {
											NaoGuiClient.say("You thought reverse");
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										//GuiClient.sendCommand("Backward");
										NaoGuiClient.move(-.3f, 0, 0);
										lastCommandId = i;
									}
									if(lastCommandId == i){
										lastTime = System.currentTimeMillis();
									}
								}
								if (i == 2) {
									if(System.currentTimeMillis() - lastTime > 3000){
										MainBciGui.writeToWindow("User thought left");
										//	GuiClient.sendCommand("Left");
										try {
											NaoGuiClient.say("You thought left");
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										NaoGuiClient.move(0, 0, .5f);
										lastCommandId = i;
									}
									if(lastCommandId == i){
										lastTime = System.currentTimeMillis();
									}
								}
								if (i == 3) {
									if(System.currentTimeMillis() - lastTime > 3000){
										MainBciGui.writeToWindow("User thought right");
										//GuiClient.sendCommand("Right");
										try {
											NaoGuiClient.say("You thought right");
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										NaoGuiClient.move(0, 0, -.5f);
										lastCommandId = i;
									}
									if(lastCommandId == i){
										lastTime = System.currentTimeMillis();
									}
								}
								Arrays.fill(array, 0);
							}
						}
						if (count % 10 == 0) {
							updateHeadsetStatus();
						}// end count
						count++;
					}// end emostateUpdated
					readyToCollect = true;
				}// end EDK_OK;
				
				
				else if (state != EdkErrorCode.EDK_NO_EVENT.ToInt()) {
					System.out.println("Can not detect headset. Restart & try again!");
					break;
				}			
				if (readyToCollect) {

					Edk.INSTANCE.EE_DataUpdateHandle(0, hData);

					Edk.INSTANCE.EE_DataGetNumberOfSample(hData, nSamplesTaken);

					if (nSamplesTaken.getValue() != 0) {

						double[] data = new double[nSamplesTaken.getValue()];
						ArrayList<Double> dataSignal = new ArrayList<>();

						Edk.INSTANCE.EE_DataGet(hData,Edk.EE_DataChannels_t.ED_AF3.ordinal(), data,nSamplesTaken.getValue());
						SignalCanvas.updateData(0, data[0]);
						dataSignal.add(data[0]);
						System.out.println("\nAF3: " + data[0]);
						// row.createCell(cellNum++).setCellValue(data[0]);

						Edk.INSTANCE.EE_DataGet(hData,Edk.EE_DataChannels_t.ED_F7.ordinal(), data,nSamplesTaken.getValue());
						SignalCanvas.updateData(1, data[0]);
						dataSignal.add(data[0]);
						System.out.println("F7: " + data[0]);
						// row.createCell(cellNum++).setCellValue(data[0]);

						Edk.INSTANCE.EE_DataGet(hData,Edk.EE_DataChannels_t.ED_F3.ordinal(), data,nSamplesTaken.getValue());
						SignalCanvas.updateData(2, data[0]);
						dataSignal.add(data[0]);
						System.out.println("F3: " + data[0]);
						// row.createCell(cellNum++).setCellValue(data[0]);

						Edk.INSTANCE.EE_DataGet(hData,Edk.EE_DataChannels_t.ED_FC5.ordinal(), data,nSamplesTaken.getValue());
						SignalCanvas.updateData(3, data[0]);
						dataSignal.add(data[0]);
						System.out.println("FC5: " + data[0]);
						// row.createCell(cellNum++).setCellValue(data[0]);

						Edk.INSTANCE.EE_DataGet(hData,Edk.EE_DataChannels_t.ED_T7.ordinal(), data,nSamplesTaken.getValue());
						SignalCanvas.updateData(4, data[0]);
						dataSignal.add(data[0]);
						System.out.println("T7: " + data[0]);
						// row.createCell(cellNum++).setCellValue(data[0]);

						Edk.INSTANCE.EE_DataGet(hData,Edk.EE_DataChannels_t.ED_P7.ordinal(), data,nSamplesTaken.getValue());
						SignalCanvas.updateData(5, data[0]);
						dataSignal.add(data[0]);
						System.out.println("P7: " + data[0]);
						// row.createCell(cellNum++).setCellValue(data[0]);

						Edk.INSTANCE.EE_DataGet(hData,Edk.EE_DataChannels_t.ED_O1.ordinal(), data,nSamplesTaken.getValue());
						SignalCanvas.updateData(6, data[0]);
						dataSignal.add(data[0]);
						System.out.println("O1: " + data[0]);
						// row.createCell(cellNum++).setCellValue(data[0]);

						Edk.INSTANCE.EE_DataGet(hData,Edk.EE_DataChannels_t.ED_O2.ordinal(), data,nSamplesTaken.getValue());
						SignalCanvas.updateData(7, data[0]);
						dataSignal.add(data[0]);
						System.out.println("O2: " + data[0]);
						// row.createCell(cellNum++).setCellValue(data[0]);

						Edk.INSTANCE.EE_DataGet(hData,Edk.EE_DataChannels_t.ED_P8.ordinal(), data,nSamplesTaken.getValue());
						SignalCanvas.updateData(8, data[0]);
						dataSignal.add(data[0]);
						System.out.println("P8: " + data[0]);
						// row.createCell(cellNum++).setCellValue(data[0]);

						Edk.INSTANCE.EE_DataGet(hData,Edk.EE_DataChannels_t.ED_T8.ordinal(), data,nSamplesTaken.getValue());
						SignalCanvas.updateData(9, data[0]);
						dataSignal.add(data[0]);
						System.out.println("T8: " + data[0]);
						// row.createCell(cellNum++).setCellValue(data[0]);

						Edk.INSTANCE.EE_DataGet(hData,Edk.EE_DataChannels_t.ED_FC6.ordinal(), data,nSamplesTaken.getValue());
						SignalCanvas.updateData(10, data[0]);
						dataSignal.add(data[0]);
						System.out.println("FC6: " + data[0]);
						// row.createCell(cellNum++).setCellValue(data[0]);

						Edk.INSTANCE.EE_DataGet(hData,Edk.EE_DataChannels_t.ED_F4.ordinal(), data,nSamplesTaken.getValue());
						SignalCanvas.updateData(11, data[0]);
						dataSignal.add(data[0]);
						System.out.println("F4: " + data[0]);
						// row.createCell(cellNum++).setCellValue(data[0]);

						Edk.INSTANCE.EE_DataGet(hData,Edk.EE_DataChannels_t.ED_F8.ordinal(), data,nSamplesTaken.getValue());
						SignalCanvas.updateData(12, data[0]);
						dataSignal.add(data[0]);
						System.out.println("F8: " + data[0]);
						// row.createCell(cellNum++).setCellValue(data[0]);

						Edk.INSTANCE.EE_DataGet(hData,Edk.EE_DataChannels_t.ED_AF4.ordinal(), data,nSamplesTaken.getValue());
						SignalCanvas.updateData(13, data[0]);
						dataSignal.add(data[0]);
						System.out.println("AF4: " + data[0]);
						// row.createCell(cellNum++).setCellValue(data[0]);
					}
				}
			}// end while
			Edk.INSTANCE.EE_EngineDisconnect();
		}
	}// end class;
}
