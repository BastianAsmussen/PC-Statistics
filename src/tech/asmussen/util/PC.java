package tech.asmussen.util;

import com.sun.management.OperatingSystemMXBean;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;

public class PC {
	
	private static final OperatingSystemMXBean OS = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
	
	private static long byteToGigabyte(long bytes) {
		
		return bytes / 1_000_000_000;
	}
	
	public static String getOSName() {
		
		return OS.getName();
	}
	
	public static String getOSVersion() {
		
		return OS.getVersion();
	}
	
	public static String getOSArch() {
		
		return OS.getArch();
	}
	
	public static int getCPULoad() {
		
		return (int) Math.round(OS.getSystemCpuLoad() * 100);
	}
	
	public static int getCPUCores() {
		
		return OS.getAvailableProcessors();
	}
	
	public static long getTotalMemory() {
		
		return byteToGigabyte(OS.getTotalPhysicalMemorySize());
	}
	
	public static long getUsedMemory() {
		
		return byteToGigabyte(OS.getTotalPhysicalMemorySize() - OS.getFreePhysicalMemorySize());
	}
	
	public static long getFreeMemory() {
		
		return byteToGigabyte(OS.getFreePhysicalMemorySize());
	}
	
	public static long getTotalSwap() {
		
		return byteToGigabyte(OS.getTotalSwapSpaceSize());
	}
	
	public static long getUsedSwap() {
		
		return byteToGigabyte(OS.getTotalSwapSpaceSize() - OS.getFreeSwapSpaceSize());
	}
	
	public static long getFreeSwap() {
		
		return byteToGigabyte(OS.getFreeSwapSpaceSize());
	}
	
	public static int getNumberOfDrives() {
		
		return File.listRoots().length;
	}
	
	public static File[] getDrives() {
		
		return File.listRoots();
	}
	
	public static long getTotalDriveSize() {
		
		long totalSize = 0;
		
		for (File drive : File.listRoots()) {
			
			totalSize += byteToGigabyte(drive.getTotalSpace());
		}
		
		return totalSize;
	}
	
	public static long getUsedDriveSize() {
		
		long usedSize = 0;
		
		for (File drive : File.listRoots()) {
			
			usedSize += byteToGigabyte(drive.getTotalSpace() - drive.getFreeSpace());
		}
		
		return usedSize;
	}
	
	public static long getFreeDriveSize() {
		
		long freeSize = 0;
		
		for (File drive : File.listRoots()) {
			
			freeSize += byteToGigabyte(drive.getFreeSpace());
		}
		
		return freeSize;
	}
	
	public static long getTotalDriveSize(File drive) {
		
		return byteToGigabyte(drive.getTotalSpace());
	}
	
	public static long getUsedDriveSize(File drive) {
		
		return byteToGigabyte(drive.getTotalSpace() - drive.getFreeSpace());
	}
	
	public static long getFreeDriveSize(File drive) {
		
		return byteToGigabyte(drive.getFreeSpace());
	}
	
	public static int getGPUUsage() {
		
		try {
			
			Process process = Runtime.getRuntime().exec("nvidia-smi --query-gpu=utilization.gpu --format=csv");
			
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
			
			stdInput.readLine();
			
			return Integer.parseInt(stdInput.readLine().replace(" %", ""));
			
		} catch (Exception e) {
			
			return -1;
		}
	}
}
