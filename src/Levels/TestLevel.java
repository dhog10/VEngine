package Levels;

import java.awt.Color;

import Entities.Entity;
import Entities.PhysicsCircle;
import Entities.PhysicsSquare;
import GUI.GUIButton;
import GUI.GUIElement;
import Main.Game;
import Utility.Timer;

public class TestLevel extends Level{

	public TestLevel() {
		super("Test Level");
	}
	
	@Override
	public void onLoad() {
		System.out.println("custom on load");
		setBackgroundColor(new Color(15,15,15));
		Game.config.gravity = 600;

		// Test HUD elements
		GUIElement button = getGame().getHUD().createGUIElement(new GUIButton(1000, 300, 100, 30), true);
		button.setText("Test button");
		button.setCenterText(true);
		button.setDraggable(true);
		button.setOnClickedCode(() -> {
			button.setText("Clicked");
			Timer.simple(1, () -> {
				button.setText("Test button");
			});
		});
		// Test entities
		Entity e = addEntity(new PhysicsCircle());
		e.setSize(50, 50, true);
		e.setPos(400,  300);
		e.getPhysicsObject().setVelocity(0, 0);
		
		Timer.create(1, 100, () -> {
			Entity et = getGame().handler.createEntity(new PhysicsCircle());
			et.setSize(50, 50, true);
			et.setPos(400,  300);
			et.getPhysicsObject().setVelocity(30, 0);
		});
		
		Entity e2 = addEntity(new PhysicsCircle());
		e2.setSize(50, 50, true);
		e2.setPos(410,  570);
		e2.getPhysicsObject().setVelocity(100, 0);
		
		for(int i = 1; i < 15; i++) {
			Entity ec = addEntity(new PhysicsCircle());
			ec.setSize(50, 50, true);
			ec.setPos(55 * i,  10);
			ec.getPhysicsObject().setVelocity(100, 0);
		}
		
		Entity e4 = addEntity(new PhysicsCircle());
		e4.setSize(110, 110, true);
		e4.setPos(410,  410);
		e4.getPhysicsObject().setVelocity(100, 0);
		
		Entity e3 = addEntity(new PhysicsCircle());
		e3.setSize(200, 200, true);
		e3.setPos(510,  200);
		e3.getPhysicsObject().setFlex(10);
		e3.getPhysicsObject().setVelocity(0, 100);
		
		Entity square0 = addEntity(new PhysicsSquare());
		square0.setSize(50, 700);
		square0.setPos(150,  200);
		square0.getPhysicsObject().setMass(5);
		square0.getPhysicsObject().enableMotion(false);
		square0.getPhysicsObject().setVelocity(0, 0);
		
		Entity square01 = addEntity(new PhysicsSquare());
		square01.setSize(50, 700);
		square01.setPos(800,  200);
		square01.getPhysicsObject().setMass(5);
		square01.getPhysicsObject().enableMotion(false);
		square01.getPhysicsObject().setVelocity(0, 0);
		
		Entity square = addEntity(new PhysicsSquare());
		square.setSize(600, 100);
		square.setPos(200,  900);
		square.getPhysicsObject().setMass(5);
		square.getPhysicsObject().enableMotion(false);
		square.getPhysicsObject().setVelocity(0, 0);
		
		Entity square2 = addEntity(new PhysicsSquare());
		square2.setSize(150, 40);
		square2.setPos(800,  950);
		square2.getPhysicsObject().setMass(5);
		square2.getPhysicsObject().enableMotion(false);
		square2.getPhysicsObject().setVelocity(0, 0);
		
		Entity square3 = addEntity(new PhysicsSquare());
		square3.setSize(300, 40);
		square3.setPos(1300,  950);
		square3.getPhysicsObject().setMass(5);
		square3.getPhysicsObject().enableMotion(false);
		square3.getPhysicsObject().setVelocity(0, 0);

		Entity square4 = addEntity(new PhysicsSquare());
		square4.setSize(100, 100);
		square4.setPos(1500,  400);
		square4.getPhysicsObject().setMass(5);
		square4.getPhysicsObject().enableMotion(false);
		square4.getPhysicsObject().setVelocity(0, 0);
	}

}
