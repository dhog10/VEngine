package Levels;

import java.awt.Color;
import java.util.ArrayList;

import Entities.Entity;
import Main.Game;

public abstract class Level {
	
	private Game game;
	private String name;
	private Color background = new Color(30,30,30);
	private boolean spawned = false;
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	
	public Level(String name) {
		this.name = name;
	}

	public void initialize(){
		entities.clear();
		onLoad();
	}

	public abstract void onLoad();
	
	public void spawn() {
		if(spawned) {return;}
		spawned = true;
		
		System.out.println("spawn level");
		for(Entity e : entities) {
			game.handler.createEntity(e);
		}
	}
	
	public void setGame(Game game) {
		this.game = game;
	}
	
	public Entity addEntity(Entity e) {
		entities.add(e);
		return e;
	}
	
	public String getName() {
		return name;
	}
	
	public Game getGame() {
		return game;
	}
	
	public void setBackgroundColor(Color background) {
		this.background = background;
		Game.frame.setBackgroundColor(background);;
	}
}
