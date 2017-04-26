package Devices;

import com.sun.jna.platform.win32.WinDef.*;
import com.sun.jna.platform.win32.User32;

import java.awt.Desktop;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.net.URI;

public class BrowserOutput {
	private Robot browserEventHandler;
	private String browserName;
	private Desktop browser;
	private HWND browserWindow;
	
	public BrowserOutput(){
		try {
			if(Desktop.isDesktopSupported()){
    			browser = Desktop.getDesktop();
    			//browser.browse(new URI("http://www.google.com"));
			}
			//browserWindow = User32.INSTANCE.FindWindow(arg0, arg1)
			browserEventHandler = new Robot();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
