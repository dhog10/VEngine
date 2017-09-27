package Handling;

import java.util.ArrayList;
import java.util.Iterator;

import Entities.Entity;

public class EntityProcessor implements Runnable{
	private int processorID;
	private Handler handler;
	private ArrayList<Entity> items;
	private int index = 0;
	private Object lock = new Object();
	protected volatile boolean ready = true;
	
	public EntityProcessor(Handler handler, int processorID) {
		this.processorID = processorID;
		this.handler = handler;
	}
	
	public int getProcessorID() {
		return processorID;
	}
	
	public void process(ArrayList<Entity> items, int index) {
		//System.out.println("process " + index);
		ready = false;
		synchronized(lock) {
			this.index = index;
			this.items = items;
		}
	}
	
	@Override
	public void run() {
		while(true) {
			synchronized(lock) {
				if(items != null) {
					ready = false;
					synchronized(handler.entityPool[index]) {
						Iterator it = items.iterator();
						while(it.hasNext()) {
							Entity e = (Entity)it.next();
							e.prePhysics();
							e.getPhysicsObject().processPhysics();
							//if(e.hasMoved()) {
								// TODO fix infinite hanging issue
								synchronized(handler.entities) {
									e.getPhysicsObject().collide(handler.entities);
								}
							//}
							e.preTick();
							e.tick();
							e.postTick();
						}
					}
					items = null;
					handler.threadsReady.incrementAndGet();
					ready = true;
				}
			}
			
		}
	}

}
