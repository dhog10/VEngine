package Physics;

public enum EnumCollider {
	COLLIDER_NONE(1, "COLLIDER_NONE"),
	COLLIDER_SQUARE(1, "COLLIDER_SQUARE"),
	COLLIDER_CIRCLE(2, "COLLIDER_CIRCLE");
	
	private int id;
	private String name;
	
	EnumCollider(int id, String name) {
		this.id = id;
		this.name= name;
	}
	
	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
}
