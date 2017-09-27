package Utility;

import java.util.ArrayList;
import java.util.Iterator;

public class Timer {

	private static ArrayList<TimerThread> timerPool = new ArrayList<TimerThread>();
	
	private static void startTimer(TimerThread timer) {
		timerPool.add(timer);
		Thread thread = new Thread(timer);
		thread.start();
	}
	
	public static void simple(float time, TimerCallback callback) {
		TimerThread timer = new TimerThread(time, 1, callback);
		startTimer(timer);
	}
	
	public static void create(float time, int repetitions, TimerCallback callback) {
		TimerThread timer = new TimerThread(time, repetitions, callback);
		startTimer(timer);
	}
	
	public static void create(String name, float time, int repetitions, TimerCallback callback) {
		TimerThread timer = new TimerThread(name, time, repetitions, callback);
		startTimer(timer);
	}
	
	public static void remove(String name) {
		if(name.equals("")) {return;}
		
		Iterator it = timerPool.iterator();
		while(it.hasNext()) {
			TimerThread t = (TimerThread)it.next();
			if(t.getName().equals(name)) {
				t.stop();
				it.remove();
				break;
			}
		}
	}
}
