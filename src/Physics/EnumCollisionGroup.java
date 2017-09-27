package Physics;

public enum EnumCollisionGroup {
	COLLISION_GROUP_NONE(1, "COLLISION_GROUP_NONE"),
	COLLISION_GROUP_SOLID(2, "COLLISION_GROUP_SOLID"),
	COLLISION_GROUP_WORLD(3, "COLLISION_GROUP_WORLD"),
	COLLISION_GROUP_ENTITIES(4, "COLLISION_GROUP_ENTITIES");
	
	private int id;
	private String name;
	
	EnumCollisionGroup(int id, String name){
		this.id = id;
		this.name= name;
	}
	
	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean collideWithEntities() {
		return this != EnumCollisionGroup.COLLISION_GROUP_NONE
		&& this != EnumCollisionGroup.COLLISION_GROUP_WORLD;
	}
	
	public boolean collideWithWorld() {
		return this != EnumCollisionGroup.COLLISION_GROUP_NONE
		&& this != EnumCollisionGroup.COLLISION_GROUP_ENTITIES;
	}
}
