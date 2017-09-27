package Levels;

import java.awt.Color;

import Entities.Entity;
import Entities.PhysicsCircle;
import Entities.PhysicsSquare;
import Main.Game;

public class TestLevel2 extends Level{

	public TestLevel2() {
		super("Test Level 2");
	}
	
	@Override
	public void onLoad() {
		System.out.println("custom on load");
		setBackgroundColor(new Color(15,15,15));
		Game.config.gravity = 0;
		
		Entity e = addEntity(new PhysicsCircle());
		e.setSize(50, 50);
		e.setPos(300,  620);
		e.getPhysicsObject().setMass(2f);
		e.getPhysicsObject().setVelocity(300, 0);
		
		Entity e2 = addEntity(new PhysicsCircle());
		e2.setSize(50, 50);
		e2.setPos(410,  650);
		e2.getPhysicsObject().setMass(0.2f);
		e2.getPhysicsObject().setVelocity(0, 0);
		
		Entity square = addEntity(new PhysicsSquare());
		square.setSize(600, 100);
		square.setPos(200,  900);
		square.getPhysicsObject().setMass(15);
		square.getPhysicsObject().enableMotion(false);
		square.getPhysicsObject().setVelocity(0, 0);
		
		Entity square2 = addEntity(new PhysicsSquare());
		square2.setSize(150, 600);
		square2.setPos(800,  300);
		square2.getPhysicsObject().setMass(15);
		square2.getPhysicsObject().enableMotion(false);
		square2.getPhysicsObject().setVelocity(0, 0);
		
		Entity square3 = addEntity(new PhysicsSquare());
		square3.setSize(300, 40);
		square3.setPos(1300,  950);
		square3.getPhysicsObject().setMass(15);
		square3.getPhysicsObject().enableMotion(false);
		square3.getPhysicsObject().setVelocity(0, 0);
	}

}
