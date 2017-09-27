package Main;
import java.util.HashMap;

public class Config {
	public String errorMaterial = "error_material.png";
	public String resourceDirectory = "resources";
	public int defaultMaterialFPS = 10;

	public float timeScale = 1;
	public float gravity = 0;
	public float airResistance = 0.995f;

	public boolean syncTest = true;

	// Debug config variables
	public boolean printDebug = true;
	public boolean showCollisionDebug = true;
	public boolean showInputDebug = true;
	public boolean showGUIElementDebug = true;
	public boolean showFPS = true;
	public boolean showTPS = true;
}
