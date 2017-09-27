package Math;

import Main.Game;

public class Vector2 {
	private float x, y;
	
	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return (int)x;
	}
	
	public float getXFloat() {
		return x;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public int getY() {
		return (int)y;
	}
	
	public float getYFloat() {
		return y;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public float dot(Vector2 vector) {
		return (vector.getXFloat() * getXFloat()) + (vector.getYFloat() * getYFloat());
	}
	
	public Vector2 add(Vector2 vector) {
		return new Vector2(x + vector.getXFloat(), y + vector.getYFloat());
	}
	
	public Vector2 add(float vector) {
		return new Vector2(getXFloat() + vector, getYFloat() + vector);
	}
	
	public Vector2 subtract(Vector2 vector) {
		return new Vector2(getXFloat() - vector.getXFloat(), getYFloat() - vector.getYFloat());
	}
	
	public Vector2 subtract(float vector) {
		return new Vector2(getXFloat() - vector, getYFloat() - vector);
	}
	
	public Vector2 multiply(Vector2 vector) {
		return new Vector2(getXFloat() * vector.getXFloat(), getYFloat() * vector.getYFloat());
	}
	
	public Vector2 multiply(float vector) {
		return new Vector2(getXFloat() * vector, getYFloat() * vector);
	}
	
	public Vector2 divide(Vector2 vector) {
		return new Vector2(getXFloat() / vector.getXFloat(), getYFloat() / vector.getYFloat());
	}
	
	public Vector2 divide(float vector) {
		return new Vector2(getXFloat() / vector, getYFloat() / vector);
	}

	public Vector2 relativeToCamera(){
		return new Vector2(x + Game.getCamOffsetX(), y + Game.getCamOffsetY());
	}
	
	public String getString() {
		return "Vector2(" + getXFloat() + ", " + getYFloat() + ")";
	}

}
