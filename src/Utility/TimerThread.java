package Utility;

public class TimerThread implements Runnable{

	private String name = "";
	private float time;
	private int repetitions;
	private int executions = 0;
	private TimerCallback callback;
	private boolean stop = false;
	
	public TimerThread(float time, int repetitions, TimerCallback callback) {
		this.time = time;
		this.repetitions = repetitions;
		this.callback = callback;
	}
	
	public TimerThread(String name, float time, int repetitions, TimerCallback callback) {
		this.name = name;
		this.time = time;
		this.repetitions = repetitions;
		this.callback = callback;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(!stop && repetitions == 0 || executions < repetitions) {
			try {
				Thread.sleep((long)time * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(stop) {
				return;
			}
			
			executions++;
			callback.run();
		}
	}
	
	public void stop() {
		stop = true;
	}
	
	public String getName() {
		return name;
	}
	
	public int getExecutions() {
		return executions;
	}
	
	public float getTime() {
		return time;
	}

}
