package Entities;

import java.awt.*;
import java.util.ArrayList;
import Utility.Material;

import Main.Game;
import Math.Vector2;
import Physics.AABB;
import Physics.CollisionData;
import Physics.PhysicsObject;

public abstract class Entity {
	private int UID;
	private int poolID;
	private boolean poolIDSet = false;
	private boolean uidSet = false;
	private boolean world = false;
	private boolean colliding = false;
	private int collideCount = 0;
	private ArrayList<CollisionData> collisionData = new ArrayList<CollisionData>();
	private float angle;

	// Material stuff
	private Material material;
	private boolean drawMaterial = true;
	private float materialScale = 1;
	private boolean centerMaterial = true;
	
	// Physics stuff
	private AABB aabb;
	private PhysicsObject physicsObject;
	private Vector2 position = new Vector2(0,0);
	private Vector2 size = new Vector2(10, 10);
	
	private ThinkCode thinkCode = () -> {};
	
	public Entity() {
		aabb = new AABB(this, new Vector2(0, 0), new Vector2(size.getX(), size.getY()));
		physicsObject = new PhysicsObject(this);
	}
	
	private boolean moved = false;
	public void tick() {
		think();
		thinkCode.think();
	}
	
	public void prePhysics() {
		synchronized(getCollisionData()) {
			collisionData.clear();
		}
		colliding = false;
		collideCount = 0;
	}
	
	public void preTick() {
		moved = false;
	}
	
	public void postTick() {
		
	}
	
	public abstract void think();

	public abstract void onCollide(CollisionData collisionData);
	
	public void render(Graphics2D g) {
		if(drawMaterial && material != null){
			if(centerMaterial) {
				material.draw((int) getDrawPosX() - (int)(((getWidth() * materialScale) - getWidth()) / 2), (int) getDrawPosY() - (int)(((getHeight() * materialScale) - getHeight()) / 2), (int) (getWidth() * materialScale), (int) (getHeight() * materialScale), g);
			}else{
				material.draw((int) getDrawPosX(), (int) getDrawPosY(), (int) (getWidth() * materialScale), (int) (getHeight() * materialScale), g);
			}
		}
		draw(g);
	}

	public abstract void draw(Graphics2D g);

	private void registerMove() {
		moved = true;
	}

	private void updatePhysicsObject() {
		aabb.getMax().setX(getWidth());
		aabb.getMax().setY(getHeight());
		physicsObject.update();
	}

	public void setUID(int uid) {
		if(uidSet) {return;}
		UID = uid;
		uidSet = true;
	}

	public void setPoolID(int uid) {
		if(poolIDSet) {return;}
		poolID = uid;
		poolIDSet = true;
	}

	public void registerCollision() {
		colliding = true;
		collideCount++;
	}
	
	public void registerCollision(CollisionData data) {
		synchronized(getCollisionData()) {
			collisionData.add(data);
		}
		colliding = true;
		collideCount++;
	}
	
	public void move(float x, float y) {
		position.setX(position.getXFloat() + x * Game.delta);
		position.setY(position.getYFloat() + y * Game.delta);
		registerMove();
	}
	
	public void moveX(float x) {
		position.setX(position.getXFloat() + x * Game.delta);
		registerMove();
	}
	
	public void moveY(float y) {
		position.setY(position.getYFloat() + y * Game.delta);
		registerMove();
	}
	
	public void setPos(float x, float y) {
		position.setX(x);
		position.setY(y);
		registerMove();
	}
	
	public void setPosX(float x) {
		position.setX(x);
		registerMove();
	}
	
	public void setPosY(float y) {
		position.setY(y);
		registerMove();
	}

	public void setAngle(float angle){
		this.angle = (float)Math.toRadians(angle);
		if(material != null){
			material.setAngleRadians(this.angle);
		}
	}
	
	public void setSize(int sizeX, int sizeY) {
		size.setX(sizeX);
		size.setY(sizeY);
		registerMove();
		updatePhysicsObject();
	}
	
	public void setSize(int sizeX, int sizeY, boolean autoMass) {
		if(autoMass) {
			getPhysicsObject().setMass(((float)sizeX + (float)sizeY) / (float)200);
		}
		setSize(sizeX, sizeY);
	}
	
	public void setWidth(int sizeX) {
		size.setX(sizeX);
		registerMove();
	}
	
	public void setHeight(int sizeY) {
		size.setY(sizeY);
		registerMove();
	}
	
	public void setThinkCode(ThinkCode thinkCode) {
		this.thinkCode = thinkCode;
	}

	public void setMaterial(String key){
		setMaterial(key, false);
	}

	public void setMaterial(String key, boolean animatedTileSet){
		setMaterial(key, animatedTileSet, Game.config.defaultMaterialFPS);
	}

	public void setMaterial(String key, boolean animatedTileSet, int fps){
		if(key == null || key.equals("")){
			material = null;
		}
		material = Game.getMaterialManager().getMaterial(key, animatedTileSet, fps);
	}

	public void setDrawMaterial(boolean drawMaterial){
		this.drawMaterial = drawMaterial;
	}

	public void setMaterialScale(float materialScale){
		this.materialScale = materialScale;
	}
	
	public boolean hasMoved() {
		return moved;
	}
	
	public int getPoolID() {
		return poolID;
	}
	
	public int getUID() {
		return UID;
	}
	
	public boolean isWorld() {
		return world;
	}
	
	public boolean isColliding() {
		return colliding;
	}
	
	public int getCollidingCount() {
		return collideCount;
	}
	
	public ArrayList<CollisionData> getCollisionData(){
		return collisionData;
	}
	
	public Vector2 getPosition() {
		return position;
	}
	
	public Vector2 getCenter() {
		return new Vector2(position.getX() + (aabb.getMax().getX() - aabb.getMin().getX()) / 2, position.getY() + (aabb.getMax().getY() - aabb.getMin().getY()) / 2);
	}
	
	public float getPosX() {
		return position.getXFloat();
	}
	
	public float getPosY() {
		return position.getYFloat();
	}

	public float getAngle(){return (float)Math.toDegrees(angle); }

	public float getAngleRadians(){ return angle; }

	public float getDrawPosX(){
		return position.getXFloat() + Game.getCamOffsetX();
	}

	public float getDrawPosY(){
		return position.getYFloat() + Game.getCamOffsetY();
	}
	
	public Vector2 getSize() {
		return size;
	}
	
	public int getWidth() {
		return size.getX();
	}
	
	public int getHeight() {
		return size.getY();
	}
	
	public AABB getAABB() {
		return aabb;
	}
	
	public PhysicsObject getPhysicsObject() {
		return physicsObject;
	}

	public String getMaterial(){
		return material.getMaterialString();
	}

	public Material getMaterialObject(){
		return material;
	}

	public boolean getDrawMaterial(){
		return drawMaterial;
	}

	public float getMaterialScale(){
		return materialScale;
	}
}
