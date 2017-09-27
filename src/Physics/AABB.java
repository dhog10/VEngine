package Physics;

import Entities.Entity;
import Math.Vector2;

public class AABB {

	private Entity parent;
	private Vector2 min, max;
	
	public AABB(Entity parent, Vector2 min, Vector2 max) {
		this.parent = parent;
		this.min = min;
		this.max = max;
	}
	
	public void setMin(int x, int y) {
		min.setX(x);
		min.setY(y);
	}
	
	public void setMin(Vector2 min) {
		this.min = min;
	}
	
	public void setMax(int x, int y) {
		max.setX(x);
		max.setY(y);
	}
	
	public void setMax(Vector2 max) {
		this.max = max;
	}
	
	public Entity getParent() {
		return parent;
	}
	
	public Vector2 getMin() {
		return min;
	}
	
	public Vector2 getMax() {
		return max;
	}
	
	public Vector2 getMinRelative() {
		return new Vector2(min.getX() + (int)parent.getPosX(), min.getY() + (int)parent.getPosY());
	}
	
	public Vector2 getMaxRelative() {
		return new Vector2(max.getX() + (int) parent.getPosX(), max.getY() + (int) parent.getPosY());
	}
}
