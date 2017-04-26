
package Devices;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Date;
/**
* CPSC 488 Software Engineering
* <p>Semester : Spring 2014            
* <p>This the the Command class. This will be the command that is     
* given to the output devices. 
* <p>
* <p>Author: Jordan Schiller
* <p>Advisor: Dr.Sam Thangiah
*/
public class Command {
    
    //CONSTANTS
    /**
     * These are the constants for the Command class
     * @param HIGHEST_PRIORITY_LEVEL This is the maximum level of the priority
     * @param LOWEST_PRIORITY_LEVEL This is the lowest level of the priority
     */
    static final int HIGHEST_PRIORITY_LEVEL = 10;
    static final int LOWEST_PRIORITY_LEVEL = 0;
    
    //VARIABLES
    /**
     * This is an enumeration of the different commands that can be given to the device
     */
    public static enum Commands {

        MoveLeft("MoveLeft"),
        MoveRight("MoveRight"),
        MoveForward("MoveForward"),
        MoveBack("MoveBack"),
        Stop("Stop"),
        None("None"),
        MouseUp("MouseUp"),
        MouseDown("MouseDown"),
        MouseLeft("MouseLeft"),
        MouseRight("MouseRight"),
        MouseClick("MouseClick");
        private String command;
        
        /**
         * This contructor will allow the creation of new commands
         * @param s This is the text of the new command enumeration
         */ 
        Commands(String s) {
            command = s;
        }//End of Commands constructor
        
        /**
         * This overridden toString function will return the enumeration as a 
         * string
         * @return Returns the text for the command enumeration
         */
        @Override
        public String toString() {
            return command;
        }//End of toString()
    }//End of Commands enumeration
    
    /**
     * This is the active command that was sent to the device
     */
    private Commands currentCommand;
    
    /**
     * This is the timestamp that will track the time that the command was made
     */
    private Date creationTime;
    
    /**
     * This is the current analyzer cycle
     */
    private long analyzerCycle;
    
    /**
     * This is the priority of the Command
     */
    private int priority;
    
    /**
     * This is the boolean that track is a command is valid or not
     */
    private boolean isValid;
    
    //METHODS
    
    /**
     * This is the default command constructor
     * <p>This function will set the following :
     * <p>currentCommand to None
     * <p>creationTime = new Date()
     * <p>analyzerCycle = 0
     * <p>priority = 0
     * <p>isValid to true;
     */
    public Command(){
        currentCommand = Commands.None;
        creationTime = new Date();
        analyzerCycle = 0;
        priority = 0;
        isValid = true;
    }//End of devault Command() constructor
    
    /**
     * This is the argumented command constructor
     * @param newCommand This is the new command enumeration
     * @param time This is the creation time of the Command
     * @param cycle This is the creation cycle of the Command
     * @param setPriority This is the priority of the Command
     */
    public Command(Commands newCommand, Date time, int cycle, int setPriority){
        currentCommand = newCommand;
        creationTime = time;
        analyzerCycle = cycle;
        priority = setPriority;
        isValid = true;
    }//End of Command(Commands newCommand, Date time, int cycle, int setPriority)
    //constructor
    
    /**
     * This function will set the current Command enumeration to a new command
     * @param newCommand Sets the current command enumeration to the newCommand
     */
    public void setCommand(Commands newCommand){
        //I dont know if I want to even allow this switching of commands
        //if a command much change, then it might be better to just
        //mark the old command as invalid and make a new command
        currentCommand = newCommand;
    }//End of setCommand(Commands newCommand)
    
    /**
     * This function will return the current Command enumeration
     * @return Returns the current value to the command
     */
    public Commands getCommand(){
        return currentCommand;
    }//End of getCommand()
    
    /**
     * This function will set the creation time
     * @param newTime This the is new creation time for the Command
     */
    public void setCreationTime(Date newTime){
        //Again I dont know if I want to be able to manipulate a command
        //for now I will leave in this ability but may remove it in the future
        creationTime = newTime;
    }//End of setCreationTime(Date newTime)
    
    /**
     * This function will return the creation time
     * @return Returns the creation time of the command
     */
    public Date getCreationTime(){
        return creationTime;
    }//End of getCreationTime()
    
    /**
     * This function will set the analyzer cycle
     * @param newCycle This is the new creation cycle to set the command to
     */
    public void setCycle(int newCycle){
        //Error checking
        if(newCycle < 0){
            System.out.println("Error occured. The new cycle number for the command"
                    + " was set too low, so it was corrected to 0.");
            newCycle = 0;
        }//End of error checking if
        analyzerCycle = newCycle;
    }//End of setCycle(int newCycle)
    
    /**
     * This function will return the current analyzer cycle
     * @return Returns the cycle that the command was created in
     */
    public long getCycle(){
        return analyzerCycle;
    }//End of getCycle()
    
    /**
     * This function will set the command's priority
     * @param newPriority This is the new priority of the command
     */
    public void setPriority(int newPriority){
        if(newPriority < Command.LOWEST_PRIORITY_LEVEL){
            System.out.println("The new priority value was set too low, it was"
                    + " set to " + Command.LOWEST_PRIORITY_LEVEL);
            newPriority = Command.LOWEST_PRIORITY_LEVEL;
        }
        if(newPriority > Command.HIGHEST_PRIORITY_LEVEL){
            System.out.println("The new priority value was set too high, it was"
                    + " set to " + Command.HIGHEST_PRIORITY_LEVEL);
            newPriority = Command.HIGHEST_PRIORITY_LEVEL;
        }
        priority = newPriority;
    }//End of setPriority(int priority)
    
    /**
     * This function will return the command's current priority
     * @return Returns the priority of this command
     */
    public int getPriority(){
        return priority;
    }//End of getPriority()
    
    /**
     * This function will set the command to valid
     */
    public void setCommandValid(){
        isValid = true;
    }//End of setCommandValid()
    
    /**
     * This function will set the command as invalid
     */
    public void setCommandInvalid(){
        isValid = false;
    }//End of setCommandInvalid()
    
    /**
     * This function will return the validity of the command
     * @return Returns if the command is valid or not
     */
    public boolean isValid(){
        return isValid;
    }//End of isValid()
    
    /**
     * This function will load the command from the given filePath
     * @param filePath This is the file path to load the command from
     * @throws Exception A generic exception if the file access is broken
     * @return Returns a boolean indicating if the load succeeded or failed
     */
    public boolean loadCommand(String filePath){
        //Create a new Bufferedreader to grab the information from the
        //file
        try{
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            //Until I figure out how I want the command information stored
            //in a file we will just assume we can read it all in as one 
            //line and the split it and plug the data through each setter
            //function. This will do its error checking and make sure the 
            //data is clean.
            String line = reader.readLine();
            String[] inputData = line.split(",");
            
            //First we will load the creation time
            //THIS IS WRONG AND NEEDS TO FIGURE OUT HOW TO GET DATES INTO
            //TIMESTAMPS
            
            this.creationTime = new Date(inputData[0]);
            
            //Set this command to valid
            this.isValid = true;
            
            //Load the command's priority
            this.priority = Integer.valueOf(inputData[1]);
            
            //Load the analyzer cycle
            this.analyzerCycle = Long.valueOf(inputData[2]);
            
            //Load the commands enumeration value
            this.currentCommand = Command.Commands.valueOf(inputData[3]);
            
            reader.close();
            }//End of try block
        catch(Exception ex){
           //Print out the error
           System.out.println("Error: " + ex.getMessage()); 
           return false;
        }//End of catch block
        return true;
    }//End of loadCommand(String filePath)
    
    /**
     * This function will save the current Command to the given filePath
     * @param filePath This is the file path that the command information will be saved to
     * @throws Exception A generic exception if the file access is broken
     * @return Returns a boolean indicating if the command saved successfully or not
     */
    public boolean saveCommand(String filePath){
    //Create a new Bufferedwriter to grab the information from the
        //file
        try{
            BufferedWriter writer = new 
                    BufferedWriter(new FileWriter(filePath));
            //Now write the various components of the command out to
            //the file
            //Note: need to add a date and time component too, perhaps
            //we can just append the saves so the history of the device
            //can be tracked
            writer.write(String.valueOf(this.creationTime));
            writer.write(",");
            writer.write(String.valueOf(this.priority));
            writer.write(",");
            writer.write(String.valueOf(this.analyzerCycle));
            writer.write(",");
            writer.write(this.currentCommand.toString());
            writer.close();
        }//End of try block
        catch(Exception ex){
            System.out.println("Error: " + ex.getMessage());
            return false;
        }//End of catch block
        return true;
    }//End of saveCommand(string filePath)
    
}//End of the Command class

