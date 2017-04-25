package Kinect;

import javax.swing.JOptionPane;

import org.eclipse.swt.widgets.Text;

import com.ibm.icu.text.DecimalFormat;

public class KinectSkeletonFrame {
	private Kinect myKinect;	
	public KinectSkeletonFrame(Kinect myKinect){
		this.myKinect = myKinect;
	}
	
	public void trackedDisplay(Text text){
		//displays the skeleton ID that is being tracked, the number of times it's been drawn,
		//the number of joints, whether they're being tracked or inferred, and the current tracking states
		byte[] tempAnswer3 = new byte[myKinect.getJointCount()];
		for(int i = 0; i<myKinect.getMaxNumberOfSkeletons(); i++){
			if(myKinect.getSkeletonInfo()[i].isTracked()){
				//prints the ID of the skeleton being tracked
				text.append("Skeleton ID:	" + myKinect.getSkeletonInfo()[i].getPlayerID()+ "\n");
				
				//prints the number of times the skeleton being tracked has been drawn
				text.append("Times Drawn:	" + myKinect.getSkeletonInfo()[i].getTimesDrawn() + "\n");	
				
				for(int m = 0; m<myKinect.getJointCount();m++){
					//boolean true if tracked, false if not tracked
					text.append("Is tracked: " + myKinect.getSkeletonInfo()[i].isJointTracked(m)+ " " + m + "\n");
					
					//boolean true or false, (true if either inferred OR tracked)
					//seems to return true no matter what??
					//maybe doesn't work with Kinect_2, only Kinect_1?
					text.append("Is tracked (true) or inferred (false): " 
							+ myKinect.getSkeletonInfo()[i].isJointTrackedOrInferred(m)+ " " + m + "\n");
				}
				//prints current tracking states (2 for true, 1 for false)
				//byte array
				tempAnswer3 = myKinect.getSkeletonInfo()[i].getJointTrackingStates();
				for(int y = 0; y<tempAnswer3.length; y++)
					text.append("Joint " + y + " Tracking State:	" + (tempAnswer3[y]) + "\n");
			}
		}
		
	}
	
public void jointDisplay(Text text){
	//displays current joint locations, as well as names of the joints
		String jointArray[]={"SPINE_BASE","SPINE_MID", "NECK", "HEAD", "SHOULDER_LEFT", "ELBOW_LEFT",
				"WRIST_LEFT", "HAND_LEFT", "SHOULDER_RIGHT", "ELBOW_RIGHT", "WRIST_RIGHT", "HAND_RIGHT", "HIP_LEFT",
				"KNEE_LEFT", "ANKLE_LEFT", "FOOT_LEFT", "HIP_RIGHT", "KNEE_RIGHT", "ANKLE_RIGHT", "FOOT_RIGHT", "SPINE_SHOULDER",
				"HAND_TIP_LEFT", "THUMB_LEFT", "HAND_TIP_RIGHT", "THUMB_RIGHT"};
		//prints number of joints
		for(int i = 0; i<myKinect.getMaxNumberOfSkeletons(); i++){
			if(myKinect.getSkeletonInfo()[i].isTracked()){
				for(int j =0; j<myKinect.getJointCount();j++){	
					text.append(jointArray[j] + ":	X Axis:	" + myKinect.getSkeletonInfo()[i].get3DJointX(j)+ 
							"	Y Axis:	" +myKinect.getSkeletonInfo()[i].get3DJointY(j)+ 
							"	Z Axis:	" +myKinect.getSkeletonInfo()[i].get3DJointZ(j)+"\n");
				}
			}
		}	
	}
	
public void orientationDisplay(Text text){
	//prints body orientations, as well as the relative NUI coordinates
	//uses double array to dereference some numbers.
	double[] tempAnswer = new double[myKinect.getJointCount()];
	//float[] tempAnswer2 = new float[myKinect.getJointCount()];
	for(int i = 0; i<myKinect.getMaxNumberOfSkeletons(); i++){
		if(myKinect.getSkeletonInfo()[i].isTracked()){
			//prints the mathematics behind the body orientation
			text.append("Body Orientation: " + myKinect.getSkeletonInfo()[i].getBodyOrientation()+"\n");
			
			//prints out the joint orientations (100 values, 72 without 0.0's?)
			//float array
			//text.append("Joint Orientation: " + "\n");
			/*tempAnswer2 = myKinect.getSkeletonInfo()[i].getJointOrientations();
			for(int y = 0; y<tempAnswer2.length; y++)
				text.append(tempAnswer2[y] + "\n");
			*/
			
			//prints the mathematics behind the torso orientation in X,Y,Z form
			//double array
			tempAnswer = myKinect.getSkeletonInfo()[i].getTorsoOrientation();
			text.append("Torso Orientation:	" + "X:	" + tempAnswer[0] + "	Y:	" + tempAnswer[1] + "	Z:	" + tempAnswer[2] + "\n");
			
			//prints relative NUI coordinates for skeletons
			//double array
			tempAnswer = myKinect.getSkeletonInfo()[i].getRelativeNUICoords();
			text.append("Relative NUI Coordinates:	");
			for(int y = 0; y<tempAnswer.length; y++){
				text.append("\n");
				text.setText(text.getText() + tempAnswer[y]);
			}
		}
	}
}

	public void jointLocationsDisplay(int jointNum, Text text){
		//displays specific joint locations when called
		//joint # to be tracked is specified in calling of method
		//rounds to 6 decimal places
		DecimalFormat df = new DecimalFormat("#.######");
		try{
		for(int i = 0; i<myKinect.getMaxNumberOfSkeletons(); i++){
			if(myKinect.getSkeletonInfo()[i].isTracked()){
					text.setText("X: " + df.format(myKinect.getSkeletonInfo()[i].get3DJointX(jointNum))+ 
							"  Y: " +df.format(myKinect.getSkeletonInfo()[i].get3DJointY(jointNum))+ 
							"  Z: " +df.format(myKinect.getSkeletonInfo()[i].get3DJointZ(jointNum)));
			}
		}
		} catch (NullPointerException e1) {
			JOptionPane.showMessageDialog(null, "Skeleton not detected yet.", "Error",
                    JOptionPane.ERROR_MESSAGE);
		}		
	}
}

