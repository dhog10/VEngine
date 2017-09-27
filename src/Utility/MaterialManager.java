package Utility;

import Main.Game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MaterialManager {

    private ArrayList<SpriteSheet> spriteSheets = new ArrayList<SpriteSheet>();
    private HashMap<String, Material> materialMap = new HashMap<>();
    private Material errorMaterial;

    public MaterialManager(){
        try {
            String error_dir = Game.config.resourceDirectory + "/" + Game.config.errorMaterial;
            BufferedImage material = ImageIO.read(Game.class.getResource(error_dir));
            errorMaterial = new Material(material, Game.config.errorMaterial);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Material getMaterialFromSpritesheets(String key){
        return null;
    }

    private Material loadMaterial(String key, boolean animatedTileSet, int fps){
        Material imaterial;
        try {
            BufferedImage material = ImageIO.read(Game.class.getResource(Game.config.resourceDirectory + "/" + key));
            imaterial = new Material(material, key, animatedTileSet);
            imaterial.setFPS(fps);
            return imaterial;
        } catch (Exception e) {
            imaterial = getMaterialFromSpritesheets(key);
            if(imaterial == null) {
                return getErrorMaterial();
            }else{
                return imaterial;
            }
        }
    }

    public Material getMaterial(String key, boolean animatedTileSet, int fps){
        Material imaterial;
        if(materialMap.containsKey(key)){
            return materialMap.get(key);
        }else{
            imaterial = loadMaterial(key, true, fps);
            materialMap.put(key, imaterial);
            return imaterial;
        }
    }

    public Material getErrorMaterial(){
        return errorMaterial;
    }
}
