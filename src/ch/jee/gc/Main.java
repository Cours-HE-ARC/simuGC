package ch.jee.gc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Application destinée à observer l'évolution du travail du GC de Java
 */
public class Main {

	static final List<TestObject> objects = new ArrayList<>();
	private static final int UN_MILLION = 1000000;
	private static long MAX_TIME_EXECUTION = 1000 * 120;
	private static boolean CLEAR_MODE = Boolean.TRUE;
	private final static String NO_CLEAR_MODE_ARGS_VALUE = "noclear";
	
	
	public static void main(String[] args) throws InterruptedException {
		
		System.out.println("Starting object generation, wait 5 seconds before running");
		
		checkAndApplyProgrammArguments(args);
		
		Thread.sleep(5000);
		
		start();

	}
	
	static void start() {
		
		System.out.println("Starting object generation...");
		
		Date startDate = new Date();
		
		while(true) {
			TestObject to = new TestObject(1, 1);
			objects.add(to);
			
			if(CLEAR_MODE && objects.size() == UN_MILLION) {
				objects.clear();
			}
		
			checkTimeToKill(startDate);
		}
		
		
	}

	private static void checkTimeToKill(Date startDate) {
		if(new Date().getTime() - startDate.getTime() > MAX_TIME_EXECUTION) {
			System.out.println("Ending object generation. Programm will exit now");
			System.exit(0);
		}
	}

	
	private static void checkAndApplyProgrammArguments(String arguments[]) {
		
		if(arguments.length == 2) {
			String clearMode = arguments[0];
			Integer duration = Integer.parseInt(arguments[1]);
			
			System.out.println("Program argument found, clearMode: "  +clearMode);
			System.out.println("Program argument found, duration: "  + duration + " seconds");
			
			MAX_TIME_EXECUTION = duration * 1000;
			
			CLEAR_MODE = (clearMode.equals(NO_CLEAR_MODE_ARGS_VALUE)) ? Boolean.FALSE : Boolean.TRUE;
			
		}else {
			System.out.println("No program arguments found, or not property configured");
		}
	}
	
	
}

