package Devices;

import java.util.List;

public abstract class Device {
	private String name;
	private List<Object> commandList;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Object> getCommandList() {
		return commandList;
	}
	public void setCommandList(List<Object> commandList) {
		this.commandList = commandList;
	}
	
	
}
