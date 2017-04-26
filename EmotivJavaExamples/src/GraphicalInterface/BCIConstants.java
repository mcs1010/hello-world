package GraphicalInterface;
/**
 * This class is a container for global constants
 * @author Stephen Bierly
 *
 */
public class BCIConstants {
	public static final int IMAGE_WIDTH = 1000;
	public static final int IMAGE_HEIGHT = 600;
	public static final int PANEL_WIDTH = 1111;
	public static final int PANEL_HEIGHT = 620;
	public static final String APP_NAME = "BCI: A Brain Computer Interface";
	public static final String [] TRAIN_OPTIONS = {"NEUTRAL","FORWARD","REVERSE","LEFT","RIGHT"};
	public static enum OUTPUT_DEVICES{
		Hercules, Mouse
	}
	public static enum XML_OPTIONS{
		XML_ADD_IMAGE,XML_ADD_OBJECT,XML_ADD_PROFILE,XML_ADD_FORWARD,XML_ADD_REVERSE,
		XML_ADD_LEFT,XML_ADD_RIGHT,XML_ADD_NEUTRAL
	}


}
