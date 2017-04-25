package Kinect;

import java.util.Arrays;

import org.eclipse.swt.widgets.Text;

public class KinectDepthFrame {
	private Kinect myKinect;	
	public KinectDepthFrame(Kinect myKinect){
		this.myKinect = myKinect;
	}
	public void getAverage(Text text){
		//gets average values of depth frames
		int counterVal = (myKinect.getDepthMap().getHeight() * myKinect.getDepthMap().getWidth());	//used for a for loop
		double counter = 0;	//used to find average of real axis (# of times numbers put into array)
		double realXAvg, realYAvg, realZAvg; 
		realXAvg = realYAvg = realZAvg = 0;
		
		for(int m = 0 ; m<counterVal; m++){
			if(myKinect.getDepthMap().realX[m]!=0 && myKinect.getDepthMap().realY[m]!=0 
					&& myKinect.getDepthMap().realZ[m]!=0){
				counter++;
				realXAvg += (myKinect.getDepthMap().realX[m]);
				realYAvg += myKinect.getDepthMap().realY[m];
				realZAvg += myKinect.getDepthMap().realZ[m];

			}
		}
		text.append("Depth Averages: " + "\n" + "	X: " + (realXAvg/counter) + "\n" + 
		"	Y: " + (realYAvg/counter) + "\n" + "	Z: " + (realZAvg/counter) + "\n");
	}
	
	public void sortDepth(){
		//sorts the depth frame arrays to find min/max easier
		Arrays.sort(myKinect.getDepthMap().realX);
		Arrays.sort(myKinect.getDepthMap().realY);
		Arrays.sort(myKinect.getDepthMap().realZ);
	}
	
	public void getMinimum(Text text){
		//gets minimum values of depth frames
		double realXMin, realYMin, realZMin;
		sortDepth();
		realXMin = myKinect.getDepthMap().realX[0];
		realYMin = myKinect.getDepthMap().realY[0];
		realZMin = myKinect.getDepthMap().realZ[0];
		text.append("Minimum Depth: 	" + "\n" + "	X:	" + realXMin  + "\n	Y : 	" + realYMin + 	"\n	Z:	" + realZMin + "\n");
	}
	
	public void getMaximum(Text text){
		//gets maximum values of depth frames
		double realXMax, realYMax, realZMax;
		sortDepth();
		realXMax = myKinect.getDepthMap().realX[myKinect.getDepthMap().realX.length-1];
		realYMax = myKinect.getDepthMap().realY[myKinect.getDepthMap().realY.length-1];
		realZMax = myKinect.getDepthMap().realZ[myKinect.getDepthMap().realZ.length-1];
		text.append("Maximum Depth: 	" + "\n" + "	X:	" + realXMax  + "\n	Y : 	" + realYMax + 	"\n	Z:	" + realZMax + "\n");
	}
	
	public void printTransformation(Text text){
		//prints the remaining information on the depth frames
		
		text.append("Maximum allowed delta:	" + myKinect.getDepthMap().getMaximumAllowedDeltaZ() + "\n");
		
		//depth height is printed in ColorFrame class
		//System.out.println("Depth height:	" + myKinect2.getDepthMap().getHeight() + "	Depth Width:	" + myKinect2.getDepthMap().getWidth());
		
		//player prints out a bunch of 0's
		//mask = nullpointer
		for(int i =0; i <myKinect.getDepthMap().transformation.length;i++)
			text.append("Transformation: " + myKinect.getDepthMap().transformation[i] + "\n");
	}

}
