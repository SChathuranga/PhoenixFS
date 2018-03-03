package phoenix.usb.encrypt;

public class MemoryManager {
	
	public static int idleSplitSize()
	{
		int intRAM=0;
		Long ramSize = Runtime.getRuntime().totalMemory();
		Long inKB = ramSize / (1024);
		
		intRAM = inKB.intValue();
		int idleSize = intRAM/3;
		
		System.out.println("RAM Size = " + String.valueOf(intRAM) + " KB");
		System.out.println("Idle Split Size = " + String.valueOf(idleSize/1024) + "MB");
		return intRAM;
	}

}
