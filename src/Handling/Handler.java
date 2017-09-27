package Handling;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

import Entities.Entity;
import Main.Game;

public class Handler {
	private Game game;
	private int currentUid = 0;
	private int processorCount;
	
	protected ArrayList<Entity> entities = new ArrayList<Entity>();
	private EntityProcessor[] processorArray;
	private Thread[] processorPool;
	protected ArrayList<Entity>[] entityPool;
	protected AtomicInteger threadsReady = new AtomicInteger();
	
	public Handler(Game game, int processorCount) {
		this.game = game;
		this.processorCount = processorCount;
		
		// Initialise entity processors
		processorPool = new Thread[processorCount];
		processorArray = new EntityProcessor[processorCount];
		entityPool = new ArrayList[processorCount];
		
		game.printDebug("Starting " + processorCount + " entity processors...");
		for(int i = 0; i < processorCount; i++) {
			entityPool[i] = new ArrayList<Entity>();
			
			EntityProcessor p = new EntityProcessor(this, i);
			processorArray[i] = p;
			
			Thread thread = new Thread(p);
			processorPool[i] = thread;
			
			thread.start();
			game.printDebug("Entity processor " + i + " started.");
		}
		
		threadsReady.set(processorCount);
	}
	
	private boolean ticked = false;
	private int done = 0;
	public void tick() {
		//System.out.println("tick");
		done = 0;
		
		// Wait for all threads to complete
		while(threadsReady.get() < processorArray.length) {}
		threadsReady.set(0);
		
		ticked = true;
		while(true) {
			for(EntityProcessor e : processorArray) {
				if(done > entityPool.length - 1) {return;}
				if(e.ready) {
					e.process(entityPool[done], done);
					done++;
				}
			}
		}
	}
	
	public ArrayList<Entity> getEntities() {
		synchronized(entities) {
			return entities;
		}
	}
	
	public Entity createEntity(Entity entity) {
		entity.setUID(currentUid);
		currentUid++;
		
		int min = 0;
		int size;
		synchronized(entityPool[min]) {
			size = entityPool[min].size();
		}
		
		for(int i = 1; i < entityPool.length; i++) {
			synchronized(entityPool[i]) {
				if(entityPool[i].size() < size) {
					min = i;
				}
			}
		}
		
		entity.setPoolID(min);
		synchronized(entities) {
			entities.add(entity);
		}
		synchronized(entityPool[min]) {
			entityPool[min].add(entity);
		}
		return entity;
	}
	
	public void removeWorldEntities() {
		synchronized(entities) {
			Iterator it = entities.iterator();
			while(it.hasNext()) {
				Entity e = (Entity)it.next();
				if(e.isWorld()) {
					entityPool[e.getPoolID()].remove(e);
					it.remove();
				}
			}
		}
	}
	
	public void removeEntity(Entity entity) {
		synchronized(entities) {
			entities.remove(entity);
		}
		entityPool[entity.getPoolID()].remove(entity);
	}
	
	public void removeEntity(int index) {
		Entity e;
		synchronized(entities) {
			e = entities.get(index);
			entities.remove(index);
		}
		entityPool[e.getPoolID()].remove(e);
	}
}
