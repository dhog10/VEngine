package Physics;

import java.awt.Point;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;

import Entities.Entity;
import Entities.PhysicsCircle;
import Entities.PhysicsSquare;
import Main.Game;
import Math.MathMethods;
import Math.Vector2;

public class PhysicsObject {

	private Entity parent;
	private EnumCollisionGroup collisionGroup;
	private PhysicsCollider collider;
	private float mass = 0;
	private float flex = 1;
	private Vector2 velocity = new Vector2(0,0);
	private boolean motionEnabled = true;
	private volatile boolean collisionsChecked = false;
	private boolean movedThisTick = false;
	private ArrayList<Vector2> collidedNormals = new ArrayList<Vector2>();
	
	public PhysicsObject(Entity parent) {
		this.parent = parent;
		setCollider(new IdleCollider());
		setCollisionGroup(EnumCollisionGroup.COLLISION_GROUP_NONE);
	}
	
	public void collide(ArrayList<Entity> entities) {
		collisionsChecked = false;
		movedThisTick = false;
		collidedEnts.clear();
		collidedNormals.clear();
		
		if(collisionGroup == EnumCollisionGroup.COLLISION_GROUP_NONE) {return;}
		
		if(collisionGroup.collideWithWorld()) {
			
		}
		
		if(collisionGroup.collideWithEntities()) {
			Iterator it = entities.iterator();
			while(it.hasNext()) {
				Entity e = (Entity)it.next();
				if(!e.getPhysicsObject().hasCollided(this)) {
					collideWithTarget(e.getPhysicsObject());
				}
			}
		}
	}
	
	ArrayList<PhysicsObject> collidedEnts = new ArrayList<PhysicsObject>();
	public boolean hasCollided(PhysicsObject e) {
		return collidedEnts.contains(e);
	}
	
	public boolean collideWithTarget(PhysicsObject target) {
		if(!shouldCollide(target)) {return false;}
		PhysicsCollider targetCollider = target.getPhysicsCollider();
		
		CollisionData collisionData = null;
		
		if(collider.getEnumCollider() == EnumCollider.COLLIDER_CIRCLE) {
			if(targetCollider.getEnumCollider() == EnumCollider.COLLIDER_CIRCLE) {
				collisionData = MathMethods.CircleVsCircle(parent.getCenter(), getPhysicsCollider().getDiameter(), target.getParent().getCenter(), targetCollider.getDiameter());
			}else if(targetCollider.getEnumCollider() == EnumCollider.COLLIDER_SQUARE) {
				collisionData = MathMethods.CircleVsSquare(parent.getCenter(), getPhysicsCollider().getDiameter(), target.getParent().getCenter(), target.getParent().getSize());
			}
		}else if(collider.getEnumCollider() == EnumCollider.COLLIDER_SQUARE) {
			if(targetCollider.getEnumCollider() == EnumCollider.COLLIDER_CIRCLE) {
				collisionData = MathMethods.CircleVsSquare(target.getParent().getCenter(), target.getPhysicsCollider().getDiameter(), parent.getCenter(), parent.getSize());
				collisionData.invertNormal();
			}
		}
		
		if(collisionData == null || !collisionData.collided()) {
			return false;
		}
		
		// TODO Calculate collision force dependant on velocity DIFFERENCE not just velocity
		
		// Calculate relative velocity
		Vector2 relativeVel = new Vector2(target.getVelocity().getX() - getVelocity().getX(), target.getVelocity().getY() - getVelocity().getY());
		Vector2 normalVector = new Vector2((float)Math.cos(collisionData.normal()), (float)Math.sin(collisionData.normal()));
		
		// Relative velocity in terms of normal vector
		float normalDot = relativeVel.dot(normalVector);
		
		// Return if objects are moving apart
		if(normalDot > 0) {return true;}
		
		// Calculate restitution (flex)
		float e = Math.min(getFlex(), target.getFlex());
		
		// Calculate impulse scalar
		float j = -(1 + e) * normalDot;
		j /= 1 / getMass() + 1 / target.getMass();
		
		float mass_sum = getMass() + target.getMass();
		float ourRatio = 0;
		float targetRatio = 0;
		
		if(target.shouldPhysicsMove()) {
			ourRatio = getMass() / mass_sum;
			ourRatio = (float)Math.ceil(ourRatio * 10000) / 10000;
		}else {
			ourRatio = 1;
		}
		
		if(shouldPhysicsMove()) {
			targetRatio = target.getMass() / mass_sum;
			targetRatio = (float)Math.floor(targetRatio * 10000) / 10000;
		}else {
			targetRatio = 1;
		}
		
		if(!shouldPhysicsMove()) {
			ourRatio = 0;
		}
		
		if(!target.shouldPhysicsMove()) {
			targetRatio = 0;
		}
		
		boolean shareCorrection = true;
		
		// Correct our location
		if(shareCorrection) {
			// Correct both entities positions
			if(ourRatio != 0 && shouldPhysicsMove()) {
				Point ourCorrection = MathMethods.angleForward(0, 0, collisionData.normal() + (float)Math.toRadians(180), collisionData.depth() * ourRatio);
				parent.setPos(parent.getPosX() + ourCorrection.x, parent.getPosY() + ourCorrection.y);
				registerMoveThisTick();
			}
			
			if(targetRatio != 0 && target.shouldPhysicsMove()) {
				Point targetCorrection = MathMethods.angleForward(0, 0, collisionData.normal(), collisionData.depth() * targetRatio);
				target.getParent().setPos(target.getParent().getPosX() + targetCorrection.x, target.getParent().getPosY() + targetCorrection.y);
				target.registerMoveThisTick();
			}
		}else if(ourRatio != 0 && shouldPhysicsMove()){
			// Only correct our position
			Point ourCorrection = MathMethods.angleForward(0, 0, collisionData.normal() + (float)Math.toRadians(180), collisionData.depth());
			parent.setPos(parent.getPosX() + ourCorrection.x, parent.getPosY() + ourCorrection.y);
			registerMoveThisTick();
		}else if(targetRatio != 0 && target.shouldPhysicsMove()) {
			// Only correct target position
			Point targetCorrection = MathMethods.angleForward(0, 0, collisionData.normal(), collisionData.depth());
			target.getParent().setPos(target.getParent().getPosX() + targetCorrection.x, target.getParent().getPosY() + targetCorrection.y);
			target.registerMoveThisTick();
		}
		
		// Apply impulse
		Vector2 impulse = normalVector.multiply(j);
		if(isMotionEnabled()) {
			
			if(ourRatio != 1) {
				impulse = impulse.multiply(ourRatio);
			}
			
			Vector2 ourImpulse = impulse.multiply(1 / getMass());
			if(parent instanceof PhysicsCircle && parent.getPosY() > 815) {
				//System.out.println(ourImpulse.getString());
			}
			setVelocity(getVelocity().subtract(ourImpulse));
		}
		
		if(target.isMotionEnabled()) {
			
			if(targetRatio != 1) {
				impulse = impulse.multiply(targetRatio);
			}
			
			Vector2 targetImpulse = impulse.multiply(1 / target.getMass());
			target.setVelocity(target.getVelocity().add(targetImpulse));
		}

		parent.onCollide(collisionData);
		target.getParent().onCollide(collisionData);
		
		parent.registerCollision(collisionData);
		collidedEnts.add(target);
		collidedNormals.add(normalVector);
		//collisionsChecked = true;
		return true;
	}
	
	public boolean shouldCollide(PhysicsObject target) {
		if(target == this) {return false;}
		
		EnumCollisionGroup targetGroup = target.getCollisionGroup();
		
		// Either or are nocollided
		if(collisionGroup == EnumCollisionGroup.COLLISION_GROUP_NONE || targetGroup == EnumCollisionGroup.COLLISION_GROUP_NONE) {return false;}
		
		// Either or have no collider type
		if(collider.getEnumCollider() == EnumCollider.COLLIDER_NONE || target.getPhysicsCollider().getEnumCollider() == EnumCollider.COLLIDER_NONE) {return false;}
		// Not within AABB
		
		if(!MathMethods.AABBVsAABB(parent.getAABB(), target.parent.getAABB())) {return false;}
		
		// Collision group checks		
		if(parent.isWorld() && !targetGroup.collideWithWorld()) {
			// Self is world and target not collide with world
			return false;
		}else if(!targetGroup.collideWithEntities()) {
			// Self is not world and target not collide with entities
			return false;
		}
		
		if(target.getParent().isWorld() && !collisionGroup.collideWithWorld()) {
			// Target is world and self not collide with world
			return false;
		}else if(!collisionGroup.collideWithEntities()) {
			// Target is not world and self not collide with entities
			return false;
		}
		
		return true;
	}
	
	public void update() {
		collider.update(parent);
	}
	
	public void addVelocity(float x, float y) {
		setVelocity(getVelocity().getXFloat() + x, getVelocity().getYFloat() + y);
	}
	
	public void addVelocity(Vector2 velocity) {
		addVelocity(velocity.getXFloat(), velocity.getYFloat());
	}
	
	public void applyForce(float x, float y) {
		if(mass < 0) {return;}
		velocity.setX(velocity.getX() + (x / mass));
		velocity.setY(velocity.getY() + (y / mass));
	}
	
	public void applyForce(Vector2 force) {
		if(mass < 0) {return;}
		velocity.setX(velocity.getX() + (force.getXFloat() / mass));
		velocity.setY(velocity.getY() + (force.getYFloat() / mass));
	}
	
	public void applyForceInstant(float x, float y) {
		if(mass < 0) {return;}
		velocity.setX(velocity.getX() + (x / mass));
		velocity.setY(velocity.getY() + (y / mass));
		parent.move(x / mass, y / mass);
	}
	
	public void applyForceInstant(Vector2 force) {
		if(mass < 0) {return;}
		velocity.setX(velocity.getX() + (force.getXFloat() / mass));
		velocity.setY(velocity.getY() + (force.getYFloat() / mass));
		parent.move(force.getXFloat(), force.getYFloat());
	}
	
	public void alterVelocityInstant(float x, float y) {
		this.setVelocity(getVelocity().getXFloat() + (x * (1 / Game.delta)), getVelocity().getYFloat() + (y * (1 / Game.delta)));
		parent.setPos(parent.getPosX() + x, parent.getPosY() + y);
	}
	
	public void processPhysics() {
		if(!isMotionEnabled()) {return;}
		
		if(mass >= 0) {
			setVelocity(getVelocity().getXFloat(), getVelocity().getYFloat() + (Game.config.gravity * mass) * Game.delta);
			//setVelocity(getVelocity().getXFloat() * Game.config.airResistance, getVelocity().getYFloat() * Game.config.airResistance);
		}
		parent.move(velocity.getX() * Game.config.timeScale, velocity.getY() * Game.config.timeScale);
	}
	
	public void setCollider(PhysicsCollider collider) {
		this.collider = collider;
		collider.setParent(parent);
	}
	
	public void setCollisionGroup(EnumCollisionGroup collisionGroup) {
		this.collisionGroup = collisionGroup;
	}
	
	public void enableMotion(boolean motionEnabled) {
		this.motionEnabled = motionEnabled;
		if(!motionEnabled) {
			setVelocity(0,0);
		}
	}
	
	public EnumCollisionGroup getCollisionGroup() {
		return collisionGroup;
	}
	
	public PhysicsCollider getPhysicsCollider() {
		return collider;
	}
	
	public void setMass(float mass) {
		if(mass < 0) {mass = 0;}
		this.mass = mass;
	}
	
	public void setFlex(float flex) {
		this.flex = flex;
	}
	
	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}
	
	public void setVelocity(float x, float y) {
		velocity.setX(x);
		velocity.setY(y);
	}
	
	public void registerMoveThisTick() {
		movedThisTick = true;
	}
	
	public Entity getParent() {
		return parent;
	}
	
	public float getMass() {
		return mass;
	}
	
	public float getFlex() {
		return flex;
	}
	
	public Vector2 getVelocity() {
		return velocity;
	}
	
	public boolean isMotionEnabled() {
		return motionEnabled;
	}
	
	public boolean collisionsChecked() {
		return collisionsChecked;
	}
	
	public boolean hasMovedThisTick() {
		return movedThisTick;
	}
	
	public boolean shouldPhysicsMove() {
		return isMotionEnabled() /*&& !hasMovedThisTick()*/;
	}
}
