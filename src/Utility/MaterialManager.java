package Utility;

import Main.Game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MaterialManager {

    private ArrayList<SpriteSheet> spriteSheets = new ArrayList<SpriteSheet>();
    private HashMap<String, IMaterial> materialMap = new HashMap<>();
    private IMaterial errorMaterial;

    public MaterialManager(){
        try {
            String error_dir = Game.config.resourceDirectory + "/" + Game.config.errorMaterial;
            BufferedImage material = ImageIO.read(Game.class.getResource(error_dir));
            errorMaterial = new IMaterial(material, Game.config.errorMaterial);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private IMaterial getMaterialFromSpritesheets(String key){
        return null;
    }

    private IMaterial loadMaterial(String key, boolean animatedTileSet, int fps){
        IMaterial imaterial;
        try {
            BufferedImage material = ImageIO.read(Game.class.getResource(Game.config.resourceDirectory + "/" + key));
            imaterial = new IMaterial(material, key, animatedTileSet);
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

    public IMaterial getMaterial(String key, boolean animatedTileSet, int fps){
        IMaterial imaterial;
        if(materialMap.containsKey(key)){
            return materialMap.get(key);
        }else{
            imaterial = loadMaterial(key, true, fps);
            materialMap.put(key, imaterial);
            return imaterial;
        }
    }

    public IMaterial getErrorMaterial(){
        return errorMaterial;
    }
}
