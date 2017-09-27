package Main;
import java.util.concurrent.atomic.AtomicInteger;

import GUI.HUD;
import Input.InputHandler;
import Utility.MaterialManager;
import javafx.scene.paint.Material;
import org.omg.Messaging.SyncScopeHelper;

import Entities.Entity;
import Entities.PhysicsCircle;
import Entities.PhysicsSquare;
import Entities.Test;
import Handling.Handler;
import Levels.Level;
import Levels.TestLevel;
import Levels.TestLevel2;

public class Game implements Runnable{

	private boolean running = false;
	
	private Thread gameThread;
	public static InputHandler input;
	private HUD hud;
	public static GameFrame frame;
	private static Level level;
	public Handler handler;
	public static Config config;
	private static MaterialManager materialManager;
	
	private int entityProcessorCount = 4;
	
	private AtomicInteger atomInt;
	private int WIDTH = 1800;
	private int HEIGHT = WIDTH / 16 * 9;
	private static int camOffsetX = 0, camOffsetY = 0;
	
	public Game() {		
		atomInt = new AtomicInteger(0);
		start();
	}

	
	public int tps = 0;
	public int fps = 0;
	
	private int targetTPS = 60;
	private int targetFPS = 120;
	private int ticks = 0;
	private long lastTick = 0;
	private long lastSecond = 0;
	private int targetTpsNano = 1000000000 / targetTPS;
	private long curtime = 0;
	public static float delta = 0;
	
	public void run() {
		
		lastTick = System.nanoTime();
		
		while(running) {
			curtime = System.nanoTime();
			if(targetTPS == -1 || curtime - lastTick >= targetTpsNano) {
				delta = (float) ((curtime - lastTick) / 1000000) / 1000;
				
				handler.tick();
				input.tick();

				lastTick = curtime;
				ticks++;

				if(curtime - lastSecond >= 1000000000) {
					lastSecond = curtime;
					tps = ticks;
					ticks = 0;
				}
			}
		}
	}
	
	public void setLevel(Level level) {
		this.level = level;
		level.setGame(this);
		handler.removeWorldEntities();
		level.spawn();
	}
	
	public Level getLevel() {
		return level;
	}

	public int getTargetTPS() {
		return targetTPS;
	}
	
	public int getTargetFPS() {
		return targetFPS;
	}
	
	public Config getConfig() {
		return config;
	}
	
	public void start() {
		config = new Config();
		materialManager = new MaterialManager();
		handler = new Handler(this, entityProcessorCount);
		input = new InputHandler(this);
		hud = new HUD(this);

		frame = new GameFrame(this, handler, "Game Title", WIDTH, HEIGHT);
		gameThread = new Thread(this);
		
		running = true;
		gameThread.start();
		
		if(config.syncTest) {
			/*for(int i = 0; i < 15; i++) {
				Entity e = handler.createEntity(new Test());
				e.setSize(50,  30);
				e.setPos(10, 80 * i);
				e.getPhysicsObject().setMass(15);
				e.getPhysicsObject().enableMotion(false);
			}*/
			
			
			setLevel(new TestLevel());
		}
	}
	
	public void stop() {
		running = false;
	}

	public void setResolution(int width){
		frame.setResolution(width, width * 19 / 9);
	}

	public void printDebug(String message) {
		if(!config.printDebug) {return;}
		System.out.println("[DEBUG] " + message);
	}

	public static int getCamOffsetX(){
		return camOffsetX;
	}

	public static int getCamOffsetY(){
		return camOffsetY;
	}

	public HUD getHUD(){
		return hud;
	}

	public static MaterialManager getMaterialManager() {
		return materialManager;
	}

	public static void main(String[] args) {
		System.setProperty("sun.java2d.opengl","True");
		Game game = new Game();
	}

}
