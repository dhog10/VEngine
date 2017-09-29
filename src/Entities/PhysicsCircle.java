package Entities;

import java.awt.*;

import Main.Game;
import Math.MathMethods;
import Math.Vector2;
import Physics.CircleCollider;
import Physics.CollisionData;
import Physics.EnumCollisionGroup;

public class PhysicsCircle extends Entity{
	
	public PhysicsCircle() {
		getPhysicsObject().setCollisionGroup(EnumCollisionGroup.COLLISION_GROUP_SOLID);
		getPhysicsObject().setCollider(new CircleCollider());
		setMaterial("test/directional_circle.png");
		setMaterialScale(1.3f);
	}

	public void think(){
		setAngle(getAngle() + 10);
	}

	public void onCollide(CollisionData collisionData){

	}
	
	public void draw(Graphics2D g) {
		if(Game.config.showCollisionDebug) {
			if(isColliding()) {
				g.setColor(new Color(30,200,30));
			}else {
				g.setColor(new Color(200,30,30));
			}
		}else {
			g.setColor(new Color(30,200,30));
		}
		
		g.drawArc((int)getDrawPosX(), (int)getDrawPosY(), getWidth(), getHeight(), 0, 360);
		
		g.setColor(new Color(0,0,255));
		g.fillRect(getAABB().getMinRelative().getX() + Game.getCamOffsetX(), getAABB().getMinRelative().getY() + Game.getCamOffsetY(), 2, 2);
		g.fillRect(getAABB().getMaxRelative().getX() + Game.getCamOffsetX(), getAABB().getMaxRelative().getY() + Game.getCamOffsetY(), 2, 2);
		
		if(Game.config.showCollisionDebug) {
			g.setColor(new Color(150,150,255));
			Vector2 pos = getCenter().relativeToCamera();
			g.drawString(Integer.toString(getCollisionData().size()), pos.getX(), pos.getY());
			g.drawString(Integer.toString(getPhysicsObject().getVelocity().getX()) + ", " + Integer.toString(getPhysicsObject().getVelocity().getY()), pos.getX(), pos.getY() - 30);
			synchronized(getCollisionData()) {
				if(getCollisionData().size() > 0) {
					g.drawString("depth: " + getCollisionData().get(0).depth(), pos.getX(), pos.getY() - 15);
				}
			}
			
			synchronized(getCollisionData()) {
				for(CollisionData c : getCollisionData()) {
					if(c != null) {
						c = c.relativeToCamera();
						g.setColor(new Color(255,0,0));
						g.fillRect(c.getOriginX() - 2, c.getOriginY() - 2, 4, 4);
						g.drawLine(getCenter().relativeToCamera().getX(), getCenter().relativeToCamera().getY(), c.getOriginX(), c.getOriginY());
						g.setColor(new Color(0,255,0));
						Point end = MathMethods.angleForward(c.getOriginX(), c.getOriginY(), c.normal(), 15);
						g.drawLine(c.getOriginX(), c.getOriginY(), end.x, end.y);
					}
				}
			}
		}
	}
}
