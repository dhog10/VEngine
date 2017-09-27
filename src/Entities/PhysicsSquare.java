package Entities;

import java.awt.*;

import Main.Game;
import Math.MathMethods;
import Math.Vector2;
import Physics.CircleCollider;
import Physics.CollisionData;
import Physics.EnumCollisionGroup;
import Physics.SquareCollider;

public class PhysicsSquare extends Entity{
	public PhysicsSquare() {
		getPhysicsObject().setCollisionGroup(EnumCollisionGroup.COLLISION_GROUP_SOLID);
		getPhysicsObject().setCollider(new SquareCollider());
		setMaterial("test/test_animated_tileset.png", true, 2);
	}

	public void think(){

	}

	public void onCollide(CollisionData collisionData){

	}
	
	public void draw(Graphics2D g) {
		if (Game.config.showCollisionDebug) {
			if (isColliding()) {
				g.setColor(new Color(30, 200, 30));
			} else {
				g.setColor(new Color(200, 30, 30));
			}
		} else {
			g.setColor(new Color(30, 200, 30));
		}

		g.drawRect((int) getDrawPosX(), (int) getDrawPosY(), getWidth(), getHeight());

		g.setColor(new Color(0, 0, 255));
		g.fillRect(getAABB().getMinRelative().relativeToCamera().getX(), getAABB().getMinRelative().relativeToCamera().getY(), 2, 2);
		g.fillRect(getAABB().getMaxRelative().relativeToCamera().getX(), getAABB().getMaxRelative().relativeToCamera().getY(), 2, 2);

		if (Game.config.showCollisionDebug) {
			g.setColor(new Color(60, 60, 255));
			Vector2 pos = getCenter().relativeToCamera();
			g.drawString(Integer.toString(getCollisionData().size()), pos.getX(), pos.getY());

			synchronized (getCollisionData()) {
				for (CollisionData c : getCollisionData()) {
					if (c != null) {
						c = c.relativeToCamera();
						g.setColor(new Color(255, 0, 0));
						g.fillRect(c.getOriginX() - 2, c.getOriginY() - 2, 4, 4);
						g.drawLine(getCenter().relativeToCamera().getX(), getCenter().relativeToCamera().getY(), c.getOriginX(), c.getOriginY());
						g.setColor(new Color(0, 255, 0));
						Point end = MathMethods.angleForward(c.getOriginX(), c.getOriginY(), c.normal(), 15);
						g.drawLine(c.getOriginX(), c.getOriginY(), end.x, end.y);
					}
				}
			}
		}
	}
}
