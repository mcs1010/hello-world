package Kinect;

import org.eclipse.swt.widgets.Text;

public class KinectColorFrame {
	private Kinect myKinect;
	public KinectColorFrame(Kinect myKinect){
		this.myKinect = myKinect;
	}
		//Don't work on MICROSOFT_KINECT_2 (only MICROSOFT_KINECT_1)
		//System.out.println(myKinect3.getAccelerometerReading()[0] + " " + myKinect3.getAccelerometerReading()[1] + " " + myKinect3.getAccelerometerReading()[2]);
		//System.out.println("Elevation angle:	" + myKinect3.getElevationAngle());
		//System.out.println("Infrared Height:	" + myKinect3.getInfraredHeight() + "	Infrared Width:	" + myKinect3.getInfraredWidth());
		//System.out.println("Long Exposure Infrared Height:	" + myKinect3.getLongExposureInfraredHeight() 
			//	+ "	Long Exposure Infrared Width:	" + myKinect3.getLongExposureInfraredWidth());

	public void radialDistortionOrder(Text text){
		text.append("Radial Distortion Order 2:	" + myKinect.getRadialDistortionOrder2() + "\n");
		text.append("Radial Distortion Order 4:	" + myKinect.getRadialDistortionOrder4() + "\n");
		text.append("Radial Distortion Order 6:	" + myKinect.getRadialDistortionOrder6() + "\n");
	}
	public void colorResolution(Text text){
		text.append("Color Height:	" + myKinect.getColorHeight() + "	Color Width:	" + myKinect.getColorWidth() + "\n");
	}
	public void depthResolution(Text text){
		text.append("Depth Height:	" + myKinect.getDepthHeight() + "	Depth Width:	" + myKinect.getDepthWidth() + "\n");
	}
	
	public void focalResolution(Text text){
		text.append("Focal Length X:	" + myKinect.getFocalLengthX() + "	Y:	" + myKinect.getFocalLengthY() + "\n");
	}
	
	public void principalPointResolution(Text text){
		text.append("Principal Point X:	" + myKinect.getPrincipalPointX() 
				+ "	Principal Point Y:	" + myKinect.getPrincipalPointY() + "\n");
	}
	public void skeletonsLimit(Text text){
		text.append("Max Number of Skeletons:	" + myKinect.getMaxNumberOfSkeletons() + "\n");
		text.append("Skeleton Count Limit:	" + myKinect.getSkeletonCountLimit() + "\n");
	}
}

