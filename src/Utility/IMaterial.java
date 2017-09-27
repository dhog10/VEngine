package Utility;

import Main.Game;
import com.sun.imageio.plugins.gif.GIFImageReader;
import com.sun.imageio.plugins.gif.GIFImageReaderSpi;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class IMaterial {

    private BufferedImage[] frames;
    private String materialString;
    private String materialExtension;
    private boolean frameAdvance = true;
    private int fps = 30;
    private int currentFrame = 0;
    private long lastFrameAdvance = 0;

    public IMaterial(BufferedImage material, String materialString){
        String extension = (materialString.substring(materialString.indexOf('.') + 1,materialString.length()));
        this.materialExtension = extension;

        setupBufferedImages(material, materialString, false);
    }

    public IMaterial(BufferedImage material, String materialString, boolean animatedTileSet){
        String extension = (materialString.substring(materialString.indexOf('.') + 1,materialString.length()));
        this.materialExtension = extension;

        setupBufferedImages(material, materialString, animatedTileSet);
    }

    private void setupBufferedImages(BufferedImage material, String materialString, boolean animatedTileSet){
        boolean animated = false;

        if(materialExtension.equals("gif") || animatedTileSet){
            animated = true;
        }

        if(animated){
            if(animatedTileSet) {
                int segmentSize = material.getHeight();
                int segments = (int)Math.floor(material.getWidth() / segmentSize);
                frames = new BufferedImage[segments];

                for(int i = 0; i < segments; i++){
                    BufferedImage frame = material.getSubimage(segmentSize * i, 0, segmentSize, segmentSize);
                    frames[i] = frame;
                }
            }else if(materialExtension.equals("gif")){
                File file = new File(Game.class.getResource(Game.config.resourceDirectory + "/" + materialString).getFile());
                ImageReader reader = new GIFImageReader(new GIFImageReaderSpi());
                BufferedImage master = new BufferedImage(material.getWidth(), material.getHeight(), BufferedImage.TYPE_INT_ARGB);
                try {
                    reader.setInput(ImageIO.createImageInputStream(file));
                    frames = new BufferedImage[reader.getNumImages(true)];
                    for(int i = 0; i < reader.getNumImages(true); i++){
                        frames[i] = reader.read(i);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else {
            frames = new BufferedImage[1];
            frames[0] = material;
        }

        this.materialString = materialString;
    }

    public void draw(int x, int y, int w, int h, Graphics g){
        g.drawImage(frames[currentFrame], x, y, w, h, null);
        if(System.currentTimeMillis() - lastFrameAdvance > 1000 / fps) {
            lastFrameAdvance = System.currentTimeMillis();
            currentFrame++;
            if(currentFrame > frames.length - 1){
                currentFrame = 0;
            }
        }
    }

    public void setFrameAdvance(boolean frameAdvance){
        this.frameAdvance = frameAdvance;
    }

    public void setFPS(int fps){
        this.fps = fps;
    }

    public String getMaterialString() {
        return materialString;
    }

    public String getMaterialExtension(){
        return materialExtension;
    }

    public boolean getFrameAdvance(){
        return frameAdvance;
    }

    public int getFPS(){
        return fps;
    }
}
