package Physics;

import Entities.Entity;
import Math.Vector2;

public abstract class PhysicsCollider {
	
	Entity parent;
	private EnumCollider enumCollider;
	private int radius = 0;
			
	public PhysicsCollider(EnumCollider enumCollider) {
		setEnumCollider(enumCollider);
	}
	
	public void update(Entity parent) {
		this.parent = parent;
		
		AABB aabb = parent.getAABB();
		int circleRad = 0;
		int minx = Math.abs(aabb.getMin().getX());
		int miny = Math.abs(aabb.getMin().getY());
		int maxx = Math.abs(aabb.getMax().getX());
		int maxy = Math.abs(aabb.getMax().getY());
		int bigx = Math.max(minx, maxx);
		int smallx = Math.min(minx, maxx);
		int bigy = Math.max(miny, maxy);
		int smally = Math.min(miny, maxy);
		radius = Math.max(bigx - smallx, bigy - smally);
	}
	
	protected void setParent(Entity parent) {
		this.parent = parent;
		update(parent);
	}
	
	public void setEnumCollider(EnumCollider enumCollider) {
		this.enumCollider = enumCollider;
	}
	
	public Entity getParent() {
		return parent;
	}
	
	public EnumCollider getEnumCollider() {
		return enumCollider;
	}
	
	public String getName() {
		return enumCollider.getName();
	}
	
	public int getID() {
		return enumCollider.getID();
	}
	
	public int getDiameter() {
		return radius;
	}
}
