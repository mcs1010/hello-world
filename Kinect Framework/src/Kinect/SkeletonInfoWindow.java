package Kinect;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class SkeletonInfoWindow extends MainWindow{

	//for loop for multiple text box // button creation??
	protected Shell shlSkeletonInformation;
	private Text text;
	private KinectSkeletonFrame skeletonInfo;
	private Text text_1;
	private Text text_2;
	private Text text_3;
	private Text text_4;
	private Text text_5;
	private Text text_6;
	private Text text_7;
	private Text text_8;
	private Text text_9;
	private Text text_10;
	private Text text_11;
	private Text text_12;
	private Text text_13;
	private Text text_14;
	private Text text_15;
	private Text text_16;
	private Text text_17;
	private Text text_18;
	private Text text_19;
	private Text text_20;
	private Text text_21;
	private Text text_22;
	private Text text_23;
	private Text text_24;

	public SkeletonInfoWindow(KinectSkeletonFrame skeletonInfo){
		this.skeletonInfo = skeletonInfo;
	}

	/**
	 * Launch the application.
	 * @param args
	 */
	public void newScreen() {
		try {
			SkeletonInfoWindow window = new SkeletonInfoWindow(skeletonInfo);
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlSkeletonInformation.open();
		shlSkeletonInformation.layout();
		while (!shlSkeletonInformation.isDisposed()) {
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
		shlSkeletonInformation = new Shell();
		shlSkeletonInformation.setSize(950, 720);
		shlSkeletonInformation.setText("Skeleton Information");
		shlSkeletonInformation.setLayout(null);
		
		Button btnNewButton = new Button(shlSkeletonInformation, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
					skeletonInfo.jointLocationsDisplay(0, text);
			}
		});
		btnNewButton.setBounds(94, 46, 105, 25);
		btnNewButton.setText("SPINE_MID");
		
		Button btnNeck = new Button(shlSkeletonInformation, SWT.NONE);
		btnNeck.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				skeletonInfo.jointLocationsDisplay(1, text_1);
			}
		});
		btnNeck.setText("NECK");
		btnNeck.setBounds(94, 96, 105, 25);
		
		Button btnHead = new Button(shlSkeletonInformation, SWT.NONE);
		btnHead.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				skeletonInfo.jointLocationsDisplay(2, text_2);
			}
		});
		btnHead.setText("HEAD");
		btnHead.setBounds(94, 146, 105, 25);
		
		Button btnShoulderleft = new Button(shlSkeletonInformation, SWT.NONE);
		btnShoulderleft.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				skeletonInfo.jointLocationsDisplay(3, text_3);
			}
		});
		btnShoulderleft.setText("SHOULDER_LEFT");
		btnShoulderleft.setBounds(94, 196, 105, 25);
		
		Button btnElbowleft = new Button(shlSkeletonInformation, SWT.NONE);
		btnElbowleft.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				skeletonInfo.jointLocationsDisplay(4, text_4);
			}
		});
		btnElbowleft.setText("ELBOW_LEFT");
		btnElbowleft.setBounds(94, 246, 105, 25);
		
		Button btnWristleft = new Button(shlSkeletonInformation, SWT.NONE);
		btnWristleft.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				skeletonInfo.jointLocationsDisplay(5, text_5);
			}
		});
		btnWristleft.setText("WRIST_LEFT");
		btnWristleft.setBounds(94, 296, 105, 25);
		
		Button btnHandleft = new Button(shlSkeletonInformation, SWT.NONE);
		btnHandleft.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				skeletonInfo.jointLocationsDisplay(6, text_6);
			}
		});
		btnHandleft.setText("HAND_LEFT");
		btnHandleft.setBounds(94, 346, 105, 25);
		
		Button btnShoulderright = new Button(shlSkeletonInformation, SWT.NONE);
		btnShoulderright.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				skeletonInfo.jointLocationsDisplay(7, text_7);
			}
		});
		btnShoulderright.setText("SHOULDER_RIGHT");
		btnShoulderright.setBounds(94, 396, 105, 25);
		
		Button btnElbowright = new Button(shlSkeletonInformation, SWT.NONE);
		btnElbowright.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				skeletonInfo.jointLocationsDisplay(8, text_8);
			}
		});
		btnElbowright.setText("ELBOW_RIGHT");
		btnElbowright.setBounds(94, 446, 105, 25);
		
		Button btnWristright = new Button(shlSkeletonInformation, SWT.NONE);
		btnWristright.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				skeletonInfo.jointLocationsDisplay(9, text_9);
			}
		});
		btnWristright.setText("WRIST_RIGHT");
		btnWristright.setBounds(94, 496, 105, 25);
		
		Button btnHandright = new Button(shlSkeletonInformation, SWT.NONE);
		btnHandright.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				skeletonInfo.jointLocationsDisplay(10, text_10);
			}
		});
		btnHandright.setText("HAND_RIGHT");
		btnHandright.setBounds(94, 546, 105, 25);
		
		Button btnHipleft = new Button(shlSkeletonInformation, SWT.NONE);
		btnHipleft.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				skeletonInfo.jointLocationsDisplay(11, text_11);
			}
		});
		btnHipleft.setText("HIP_LEFT");
		btnHipleft.setBounds(94, 596, 105, 25);
		
		Button btnHipright = new Button(shlSkeletonInformation, SWT.NONE);
		btnHipright.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				skeletonInfo.jointLocationsDisplay(12, text_12);
			}
		});
		btnHipright.setText("HIP_RIGHT");
		btnHipright.setBounds(94, 646, 105, 25);
		
		Button btnKneeleft = new Button(shlSkeletonInformation, SWT.NONE);
		btnKneeleft.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				skeletonInfo.jointLocationsDisplay(13, text_13);
			}
		});
		btnKneeleft.setText("KNEE_LEFT");
		btnKneeleft.setBounds(501, 46, 105, 25);
		
		Button btnAnkleleft = new Button(shlSkeletonInformation, SWT.NONE);
		btnAnkleleft.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				skeletonInfo.jointLocationsDisplay(14, text_14);
			}
		});
		btnAnkleleft.setText("ANKLE_LEFT");
		btnAnkleleft.setBounds(501, 96, 105, 25);
		
		Button btnFootleft = new Button(shlSkeletonInformation, SWT.NONE);
		btnFootleft.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				skeletonInfo.jointLocationsDisplay(15, text_15);
			}
		});
		btnFootleft.setText("FOOT_LEFT");
		btnFootleft.setBounds(501, 146, 105, 25);
		
		Button btnHipright_1 = new Button(shlSkeletonInformation, SWT.NONE);
		btnHipright_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				skeletonInfo.jointLocationsDisplay(16, text_16);
			}
		});
		btnHipright_1.setText("HIP_RIGHT");
		btnHipright_1.setBounds(501, 196, 105, 25);
		
		Button btnKneeright = new Button(shlSkeletonInformation, SWT.NONE);
		btnKneeright.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				skeletonInfo.jointLocationsDisplay(17, text_17);
			}
		});
		btnKneeright.setText("KNEE_RIGHT");
		btnKneeright.setBounds(501, 246, 105, 25);
		
		Button btnAnkleright = new Button(shlSkeletonInformation, SWT.NONE);
		btnAnkleright.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				skeletonInfo.jointLocationsDisplay(18, text_18);
			}
		});
		btnAnkleright.setText("ANKLE_RIGHT");
		btnAnkleright.setBounds(501, 296, 105, 25);
		
		Button btnFootright = new Button(shlSkeletonInformation, SWT.NONE);
		btnFootright.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				skeletonInfo.jointLocationsDisplay(19, text_19);
			}
		});
		btnFootright.setText("FOOT_RIGHT");
		btnFootright.setBounds(501, 346, 105, 25);
		
		Button btnSpineshoulder = new Button(shlSkeletonInformation, SWT.NONE);
		btnSpineshoulder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				skeletonInfo.jointLocationsDisplay(20, text_20);
			}
		});
		btnSpineshoulder.setText("SPINE_SHOULDER");
		btnSpineshoulder.setBounds(501, 396, 105, 25);
		
		Button btnHandtipleft = new Button(shlSkeletonInformation, SWT.NONE);
		btnHandtipleft.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				skeletonInfo.jointLocationsDisplay(21, text_21);
			}
		});
		btnHandtipleft.setText("HAND_TIP_LEFT");
		btnHandtipleft.setBounds(501, 446, 105, 25);
		
		Button btnThumbleft = new Button(shlSkeletonInformation, SWT.NONE);
		btnThumbleft.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				skeletonInfo.jointLocationsDisplay(22, text_22);
			}
		});
		btnThumbleft.setText("THUMB_LEFT");
		btnThumbleft.setBounds(501, 496, 105, 25);
		
		Button btnHandtipright = new Button(shlSkeletonInformation, SWT.NONE);
		btnHandtipright.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				skeletonInfo.jointLocationsDisplay(23, text_23);
			}
		});
		btnHandtipright.setText("HAND_TIP_RIGHT");
		btnHandtipright.setBounds(501, 546, 105, 25);
		
		Button btnThumbright = new Button(shlSkeletonInformation, SWT.NONE);
		btnThumbright.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				skeletonInfo.jointLocationsDisplay(24, text_24);
			}
		});
		btnThumbright.setText("THUMB_RIGHT");
		btnThumbright.setBounds(501, 596, 105, 25);
		
		text = new Text(shlSkeletonInformation, SWT.BORDER | SWT.CENTER);
		text.setEditable(false);
		text.setBounds(225, 47, 219, 23);
		
		Label lblShowPositions = new Label(shlSkeletonInformation, SWT.NONE);
		lblShowPositions.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lblShowPositions.setAlignment(SWT.CENTER);
		lblShowPositions.setBounds(94, 25, 105, 15);
		lblShowPositions.setText("Show Positions");
		
		Label lblNewLabel = new Label(shlSkeletonInformation, SWT.NONE);
		lblNewLabel.setAlignment(SWT.CENTER);
		lblNewLabel.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lblNewLabel.setBounds(225, 25, 219, 15);
		lblNewLabel.setText("Joint Positions");
		
		text_1 = new Text(shlSkeletonInformation, SWT.BORDER | SWT.CENTER);
		text_1.setEditable(false);
		text_1.setBounds(225, 96, 219, 23);
		
		text_2 = new Text(shlSkeletonInformation, SWT.BORDER | SWT.CENTER);
		text_2.setEditable(false);
		text_2.setBounds(225, 148, 219, 23);
		
		text_3 = new Text(shlSkeletonInformation, SWT.BORDER | SWT.CENTER);
		text_3.setEditable(false);
		text_3.setBounds(225, 198, 219, 23);
		
		text_4 = new Text(shlSkeletonInformation, SWT.BORDER | SWT.CENTER);
		text_4.setEditable(false);
		text_4.setBounds(225, 246, 219, 23);
		
		text_5 = new Text(shlSkeletonInformation, SWT.BORDER | SWT.CENTER);
		text_5.setEditable(false);
		text_5.setBounds(225, 296, 219, 23);
		
		text_6 = new Text(shlSkeletonInformation, SWT.BORDER | SWT.CENTER);
		text_6.setEditable(false);
		text_6.setBounds(225, 346, 219, 23);
		
		text_7 = new Text(shlSkeletonInformation, SWT.BORDER | SWT.CENTER);
		text_7.setEditable(false);
		text_7.setBounds(225, 396, 219, 23);
		
		text_8 = new Text(shlSkeletonInformation, SWT.BORDER | SWT.CENTER);
		text_8.setEditable(false);
		text_8.setBounds(225, 446, 219, 23);
		
		text_9 = new Text(shlSkeletonInformation, SWT.BORDER | SWT.CENTER);
		text_9.setEditable(false);
		text_9.setBounds(225, 496, 219, 23);
		
		text_10 = new Text(shlSkeletonInformation, SWT.BORDER | SWT.CENTER);
		text_10.setEditable(false);
		text_10.setBounds(225, 548, 219, 23);
		
		text_11 = new Text(shlSkeletonInformation, SWT.BORDER | SWT.CENTER);
		text_11.setEditable(false);
		text_11.setBounds(225, 598, 219, 23);
		
		text_12 = new Text(shlSkeletonInformation, SWT.BORDER | SWT.CENTER);
		text_12.setEditable(false);
		text_12.setBounds(225, 646, 219, 23);
		
		text_13 = new Text(shlSkeletonInformation, SWT.BORDER | SWT.CENTER);
		text_13.setEditable(false);
		text_13.setBounds(632, 46, 219, 23);
		
		text_14 = new Text(shlSkeletonInformation, SWT.BORDER | SWT.CENTER);
		text_14.setEditable(false);
		text_14.setBounds(632, 98, 219, 23);
		
		text_15 = new Text(shlSkeletonInformation, SWT.BORDER | SWT.CENTER);
		text_15.setEditable(false);
		text_15.setBounds(632, 148, 219, 23);
		
		text_16 = new Text(shlSkeletonInformation, SWT.BORDER | SWT.CENTER);
		text_16.setEditable(false);
		text_16.setBounds(632, 196, 219, 23);
		
		text_17 = new Text(shlSkeletonInformation, SWT.BORDER | SWT.CENTER);
		text_17.setEditable(false);
		text_17.setBounds(632, 246, 219, 23);
		
		text_18 = new Text(shlSkeletonInformation, SWT.BORDER | SWT.CENTER);
		text_18.setEditable(false);
		text_18.setBounds(632, 296, 219, 23);
		
		text_19 = new Text(shlSkeletonInformation, SWT.BORDER | SWT.CENTER);
		text_19.setEditable(false);
		text_19.setBounds(632, 346, 219, 23);
		
		text_20 = new Text(shlSkeletonInformation, SWT.BORDER | SWT.CENTER);
		text_20.setEditable(false);
		text_20.setBounds(632, 398, 219, 23);
		
		text_21 = new Text(shlSkeletonInformation, SWT.BORDER | SWT.CENTER);
		text_21.setEditable(false);
		text_21.setBounds(632, 446, 219, 23);
		
		text_22 = new Text(shlSkeletonInformation, SWT.BORDER | SWT.CENTER);
		text_22.setEditable(false);
		text_22.setBounds(632, 496, 219, 23);
		
		text_23 = new Text(shlSkeletonInformation, SWT.BORDER | SWT.CENTER);
		text_23.setEditable(false);
		text_23.setBounds(632, 546, 219, 23);
		
		text_24 = new Text(shlSkeletonInformation, SWT.BORDER | SWT.CENTER);
		text_24.setEditable(false);
		text_24.setBounds(632, 596, 219, 23);

	}
}
