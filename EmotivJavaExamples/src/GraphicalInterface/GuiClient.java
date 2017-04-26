package GraphicalInterface;

import java.io.*;
import java.net.*;

/**
 * <p>Title:  Hercules Client</p>
 * <p>Description:  Connects to HerculesServer and recieves strings of data from it.  
 * It then lets InfoPacket parse it and other classes then get their information from InfoPacket</p>
 * <p>Copyright (c) 2008</p>
 * Slippery Rock University</p>
 *
 * 
 * 
 */
public class GuiClient {
    /**
     * Flag to indicate whether or not the server should send output to the console.  
     * Defined at Constants.HERCULES_SERVER_VERBOSE.
     */
    //public static boolean VERBOSE = Constants.HERCULES_CLIENT_VERBOSE;

    /**
     * The name of the robot host.  Defined in Constants.HOST_NAME;
     */
    //public static final String hostname ="10.1.41.56";
    public static final String hostname ="tcp://169.254.39.105";
    /**
    * The port that the client is to connect on.  Defined at Constants.HOST_PORT.
    */
    //public static final int hostport = 4312;
    public static final int hostport = 9559;

    private static Socket socket = null;
    private static PrintWriter out = null;
    private static BufferedReader in = null;

    private static boolean connected = false;

    /**
     * Starts a new GuiClient.Starter which tries to connect to the HerculesServer.
     */
    public static void connect() {
        (new Starter()).start();
        System.out.println("Connected to Server");
    }



    /**
     * Tries to connect to the server.
     * @throws IOException when it can't connect
     */
    public static void startConnect() throws IOException {
       
        try {
            socket = new Socket(hostname, hostport);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host:" + hostname +".");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: "+ hostname +".");
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        connected = true;
        String fromServer;
        out.println("client");
        in.readLine();

        while (!(fromServer = in.readLine()).equals("bye.")) {
           System.out.println(fromServer);
        	
        }

        connected = false;
   
        out.close();
        in.close();
        stdIn.close();
        socket.close();
    }

    /**
     * Is the client connected to the server?
     * @return True when connected.
     */
    public static void sendCommand(String c) {

         out.println(c);
         System.out.println("Sent "+ c);

    }
    public static boolean isConnected() {
    	return connected;
    }
    
    /**
     * This class starts a threaded socket connection and attempts to connect to the server.
     *   It runs GuiClient.startConnect().
     */
    private static class Starter extends Thread {
        public void run() {
           
            try {
            	System.out.println("Inside Starter!!!");
            	GuiClient.startConnect();
            } 
            catch (IOException ex) {
            	
            }
          
        }
    }

}
