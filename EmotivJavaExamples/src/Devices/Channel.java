package Devices;

public interface Channel {
	public abstract void send(Object o);
	
	public abstract Object recieve();
}
