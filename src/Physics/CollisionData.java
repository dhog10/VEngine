package Physics;

import Main.Game;
import Math.Vector2;

public class CollisionData {
	private boolean collided = false;
	private float normal = 0;
	private float depth = 0;
	private int originX = 0;
	private int originY = 0;
	
	public CollisionData() {}
	
	public CollisionData(float normal, float depth, int originX, int originY) {
		collided = true;
		this.normal = normal;
		this.depth = depth;
		this.originX = originX;
		this.originY = originY;
	}
	
	public void invertNormal() {
		normal += (float)Math.toRadians(180);
	}
	
	public boolean collided() {
		return collided;
	}
	
	public float normal() {
		return normal;
	}
	
	public float depth() {
		return depth;
	}
	
	public int getOriginX() {
		return originX;
	}
	
	public int getOriginY(){
		return originY;
	}

	public CollisionData relativeToCamera(){
		return new CollisionData(normal, depth, originX + Game.getCamOffsetX(), originY + Game.getCamOffsetY());
	}
}
