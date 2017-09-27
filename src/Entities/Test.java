package Entities;

import java.awt.*;

import Physics.CollisionData;
import Physics.EnumCollisionGroup;
import Physics.SquareCollider;

public class Test extends Entity{
	private boolean forward = true;
	
	public Test() {
		getPhysicsObject().setCollisionGroup(EnumCollisionGroup.COLLISION_GROUP_SOLID);
		getPhysicsObject().setCollider(new SquareCollider());
	}
	
	@Override
	public void tick() {
		if(forward) {
			moveX(50);
			if(getPosX() > 1000) {
				forward = !forward;
			}
		}else {
			moveX(-100);
			if(getPosX() < 0) {
				forward = !forward;
			}
		}
	}

	@Override
	public void think() {

	}

	@Override
	public void onCollide(CollisionData collisionData) {

	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(new Color(150,150,150));
		g.fillRect((int)getPosX(), (int)getPosY(), getWidth(), getHeight());
	}

	@Override
	public void draw(Graphics2D g) {

	}
}
