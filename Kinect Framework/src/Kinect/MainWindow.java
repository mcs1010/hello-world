package Kinect;

import javax.swing.JOptionPane;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

import edu.ufl.digitalworlds.j4k.J4KSDK;
import edu.ufl.digitalworlds.gui.DWApp;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;

public class MainWindow{
	protected Shell shlKinectSensorInformation;
	private Text text;
	private Kinect myKinect;
	//create the KinectSkeletonFrame, KinectDepthFrame, KinectColorFrame objects
	private KinectSkeletonFrame skeletonInfo;
	private KinectDepthFrame depthInfo;
	private KinectColorFrame colorInfo;
	/**
	 * Launch the application.
	 * @param args
	 * @wbp.parser.entryPoint
	 */
	
	public static void main(String[] args) {	
		try {
			MainWindow window = new MainWindow();
			window.open();	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 * @wbp.parser.entryPoint
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlKinectSensorInformation.open();
		shlKinectSensorInformation.layout();
		while (!shlKinectSensorInformation.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}	
	}

	/**
	 * Create contents of the window.
	 * @wbp.parser.entryPoint
	 */
	protected void createContents() {
		// Instantiates Kinect, skeletonInfo, depthInfo, and colorInfo objects to be used
		instantiateObjects();
		//
		shlKinectSensorInformation = new Shell();
		shlKinectSensorInformation.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				myKinect.stop();
				System.gc();
			}
		});
		shlKinectSensorInformation.setSize(804, 478);
		shlKinectSensorInformation.setText("Kinect Sensor Information");
		shlKinectSensorInformation.setLayout(new GridLayout(2, false));
		Button btnNewButton = new Button(shlKinectSensorInformation, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			//creates new window for video display
			@Override
			public void widgetSelected(SelectionEvent e) {
				//KinectViewerWindow nw2 = new KinectViewerWindow(myKinect);
				//nw2.newScreen();
				try{
				myKinect.showViewerDialog(false);
				} catch (NullPointerException e1) {
					JOptionPane.showMessageDialog(null, "Sensors not detected.", "Error",
		                    JOptionPane.ERROR_MESSAGE);
				}		
			}
		});
		GridData gd_btnNewButton = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnNewButton.heightHint = 65;
		gd_btnNewButton.widthHint = 136;
		btnNewButton.setLayoutData(gd_btnNewButton);
		btnNewButton.setText("Show Kinect Viewer");
		
		text = new Text(shlKinectSensorInformation, SWT.BORDER | SWT.V_SCROLL);
		text.setEditable(false);
		GridData gd_text = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 9);
		gd_text.heightHint = 419;
		text.setLayoutData(gd_text);
		new Label(shlKinectSensorInformation, SWT.NONE);
		
		Button btnNewButton_1 = new Button(shlKinectSensorInformation, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			//creates new window for interractive skeleton information
			public void widgetSelected(SelectionEvent e) {
				try{
					skeletonInfo.trackedDisplay(text);
					skeletonInfo.jointDisplay(text);
					skeletonInfo.orientationDisplay(text);
					SkeletonInfoWindow nw = new SkeletonInfoWindow(skeletonInfo);
					nw.newScreen();
				} catch (NullPointerException e1) {
					JOptionPane.showMessageDialog(null, "Skeleton not detected yet.", "Error",
		                    JOptionPane.ERROR_MESSAGE);
				}	
			}
		});
		GridData gd_btnNewButton_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnNewButton_1.widthHint = 136;
		gd_btnNewButton_1.heightHint = 65;
		btnNewButton_1.setLayoutData(gd_btnNewButton_1);
		btnNewButton_1.setText("Show Skeleton Info");
		new Label(shlKinectSensorInformation, SWT.NONE);
		
		Button btnNewButton_2 = new Button(shlKinectSensorInformation, SWT.NONE);
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			//print depth information from sensors
			public void mouseDown(MouseEvent e) {
				try{
					depthInfo.printTransformation(text);
					depthInfo.getMinimum(text);
					depthInfo.getMaximum(text);
					depthInfo.getAverage(text);
				} catch (NullPointerException e1) {
					JOptionPane.showMessageDialog(null, "Depth frames not detected yet.", "Error",
		                    JOptionPane.ERROR_MESSAGE);
				}	
			}
		});
		GridData gd_btnNewButton_2 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnNewButton_2.heightHint = 65;
		gd_btnNewButton_2.widthHint = 136;
		btnNewButton_2.setLayoutData(gd_btnNewButton_2);
		btnNewButton_2.setText("Show Depth Info");
		new Label(shlKinectSensorInformation, SWT.NONE);
		
		Button btnNewButton_3 = new Button(shlKinectSensorInformation, SWT.NONE);
		btnNewButton_3.addMouseListener(new MouseAdapter() {
			//print color information from sensors
			@Override
			public void mouseDown(MouseEvent e) {
				try{
					colorInfo.colorResolution(text);
					colorInfo.depthResolution(text);
					colorInfo.focalResolution(text);
					colorInfo.principalPointResolution(text);
					colorInfo.radialDistortionOrder(text);
					colorInfo.skeletonsLimit(text);
				} catch (NullPointerException e1) {
					JOptionPane.showMessageDialog(null, "Color frames not detected yet.", "Error",
		                    JOptionPane.ERROR_MESSAGE);
				}	
			}
		});
		GridData gd_btnNewButton_3 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnNewButton_3.heightHint = 65;
		gd_btnNewButton_3.widthHint = 136;
		btnNewButton_3.setLayoutData(gd_btnNewButton_3);
		btnNewButton_3.setText("Show Color Info");
		new Label(shlKinectSensorInformation, SWT.NONE);
		
		Button btnNewButton_4 = new Button(shlKinectSensorInformation, SWT.NONE);
		btnNewButton_4.addMouseListener(new MouseAdapter() {
			//clears the textbox 
			@Override
			public void mouseDown(MouseEvent e) {
				text.setText("");
			}
		});
		GridData gd_btnNewButton_4 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnNewButton_4.heightHint = 65;
		gd_btnNewButton_4.widthHint = 136;
		btnNewButton_4.setLayoutData(gd_btnNewButton_4);
		btnNewButton_4.setText("Clear Information");

	}

	public void instantiateObjects(){
		myKinect = new Kinect();
		if(!myKinect.start(J4KSDK.COLOR|J4KSDK.DEPTH|J4KSDK.UV|J4KSDK.XYZ|J4KSDK.SKELETON))
		{
			DWApp.showErrorDialog("ERROR", "<html><center><br>ERROR: The Kinect device could not be"
					+ " initialized.<br><br>1. Check if the Microsoft's Kinect SDK was succesfully installed "
					+ "on this computer.<br> 2. Check if the Kinect is plugged into a power outlet.<br>3. "
					+ "Check if the Kinect is connected to a USB port of this computer.</center>");
			//System.exit(0); 
		}
		skeletonInfo = new KinectSkeletonFrame(myKinect);	//instantiating skeletonInfo using myKinect for information
		depthInfo = new KinectDepthFrame(myKinect);			//instantiating depthInfo using myKinect for information
		colorInfo = new KinectColorFrame(myKinect);			//instantiating colorInfo using myKinect for information
	}
}
