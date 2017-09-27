package Utility;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Random;

import Math.Vector2;

public class SpriteSheet {

    private Random rand = new Random();
    private BufferedImage material;
    private BufferedImage[][] sections;
    private HashMap<String, Vector2> keyPointers = new HashMap<>();
    private int resolution;
    private String name;

    public SpriteSheet(BufferedImage material, int resolution, String name){
        this.material = material;
        this.resolution = resolution;
        this.name = name;

        int sectionsX = material.getWidth() / resolution;
        int sectionsY = material.getHeight() / resolution;
        sections = new BufferedImage[sectionsX][sectionsY];

        for(int i = 0; i < material.getWidth() / resolution; i++){
            for(int j = 0; j < material.getHeight() / resolution; j++){
                BufferedImage section;
                try {
                    section = material.getSubimage(i * resolution, j * resolution, resolution, resolution);
                }catch(Exception e){

                }
            }
        }
    }

    public void nameSection(int x, int y, String name){
        keyPointers.put(name, new Vector2(x, y));
    }

    public BufferedImage getMaterial(){
        return material;
    }

    public BufferedImage getSection(int x, int y){
        try {
            return sections[x][y];
        }catch(Exception e){
            return null;
        }
    }

    public BufferedImage randomSection(){
        int x = rand.nextInt(sections.length);
        return sections[x][rand.nextInt(sections[x].length)];
    }

    public BufferedImage getSection(String key){
        if(keyPointers.containsKey(key)){
            Vector2 location = keyPointers.get(key);
            return getSection(location.getX(), location.getY());
        }
        return null;
    }

    public int getResolution(){
        return resolution;
    }

    public String getName(){
        return name;
    }
}
