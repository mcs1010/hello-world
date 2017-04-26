/*
 * Copyright 2011-2014, Digital Worlds Institute, University of 
 * Florida, Angelos Barmpoutis.
 * All rights reserved.
 *
 * When this program is used for academic or research purposes, 
 * please cite the following article that introduced this Java library: 
 * 
 * A. Barmpoutis. "Tensor Body: Real-time Reconstruction of the Human Body 
 * and Avatar Synthesis from RGB-D', IEEE Transactions on Cybernetics, 
 * October 2013, Vol. 43(5), Pages: 1347-1356. 
 */

package Kinect;

import edu.ufl.digitalworlds.j4k.J4KSDK; 
import edu.ufl.digitalworlds.j4k.DepthMap; 
import edu.ufl.digitalworlds.j4k.Skeleton; 
import edu.ufl.digitalworlds.j4k.VideoFrame; 

public class Kinect extends J4KSDK{
	/*videoTexture will hold the current video frame received from  
    the Kinect video camera.*/  
	private VideoFrame videoTexture; 
	private Skeleton skeletons[]=new Skeleton[getMaxNumberOfSkeletons()]; 
	private DepthMap map;
	
  //DepthMap map;
 
  /*The constructor of the class initializes the native Kinect 
    SKD libraries and creates a new VideoFrame object.*/ 
	public Kinect() { 
		super(); 
		videoTexture=new VideoFrame(); 
	}

	@Override 
	public void onDepthFrameEvent(short[] depth_frame, byte[] player_index, float[] XYZ, float[] UV) { 
		//XYZ placed into realX, realY, and realZ with -infinities taken out (every third #)
		//UV means making a 3D object painted with color from an ordinary image
		map=new DepthMap(getDepthWidth(),getDepthHeight(),XYZ); 
		if(UV!=null) map.setUV(UV); 
	} 

	/*The following method will run every time a new skeleton frame 
  	is received from the Kinect sensor. The skeletons are converted 
  	into Skeleton objects.*/  
	@Override 
	public void onSkeletonFrameEvent(boolean[] skeleton_tracked, float[] joint_position, float[] joint_orientation, byte[] joint_status) { 
		/*Joint Constants :   SPINE_BASE = 0(HIP CENTER), SPINE_MID=1, NECK=2, HEAD=3, SHOULDER_LEFT=4,  ELBOW_LEFT=5, WRIST_LEFT=6, HAND_LEFT=7, 
		SHOULDER_RIGHT=8, ELBOW_RIGHT=9, WRIST_RIGHT=10, HAND_RIGHT=11, HIP_LEFT=12, KNEE_LEFT=13, ANKLE_LEFT=14, FOOT_LEFT=15, HIP_RIGHT=16,
		//KNEE_RIGHT=17, ANKLE_RIGHT=18, FOOT_RIGHT=19, SPINE_SHOULDER= 20, HAND_TIP_LEFT=21, THUMB_LEFT=22, HAND_TIP_RIGHT=23
		//THUMB_RIGHT=24, 
		JOINT_COUNT = 25*/
		for(int i=0;i<skeletons.length;i++) {
			skeletons[i]=Skeleton.getSkeleton(i, skeleton_tracked, joint_position, joint_orientation, joint_status, this); 
		}
    
	} 

	/*The following method will run every time a new color frame 
  	is received from the Kinect video camera. The incoming frame 
  	is passed to the videoTexture object to update its content.*/     
	@Override 
	public void onColorFrameEvent(byte[] data) {     
		videoTexture.update(getColorWidth(), getColorHeight(), data); 
	} 

	public Skeleton[] getSkeletonInfo(){
		//returns skeleton object
		return skeletons;
	}

	public DepthMap getDepthMap(){
		//returns depthmap object
		return map;
	}

	public int getJointCount(){
		//returns # of joints of skeletons
		return Skeleton.JOINT_COUNT;
	}
} 

