package Devices;

import java.util.ArrayList;
import java.util.List;

import eeg.EmoState;

public class BCICommand extends Device{
	private List<Object> emotivCommands;
	public BCICommand() {
		emotivCommands = new ArrayList<Object>();
		emotivCommands.add(EmoState.EE_CognitivAction_t.COG_PUSH.ToInt());
		emotivCommands.add(EmoState.EE_CognitivAction_t.COG_PULL.ToInt());
		emotivCommands.add(EmoState.EE_CognitivAction_t.COG_LEFT.ToInt());
		emotivCommands.add(EmoState.EE_CognitivAction_t.COG_RIGHT.ToInt());
		super.setCommandList(emotivCommands);
	}
	
}
