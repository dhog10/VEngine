package GUI;

import Entities.ThinkCode;
import Math.Vector2;

import java.awt.*;

public abstract class GUIElement {

    private Vector2 position;
    private Vector2 size;
    private boolean shouldRemove = false;
    private boolean hovered = false;
    private ThinkCode thinkCode = () -> {};

    public GUIElement(){

    }

    public void tick(){
        think();
        thinkCode.think();
    }

    public abstract void think();

    public void render(Graphics2D g){
        draw(g);
    }

    public abstract void draw(Graphics2D g);

    public abstract void onRemove();

    public void setPosition(Vector2 position){
        this.position = position;
    }

    public void setPosition(float x, float y){
        position.setX(x);
        position.setY(y);
    }

    public void setSize(Vector2 size){
        this.size = size;
    }

    public void setSize(int w, int h){
        size.setX(w);
        size.setY(h);
    }

    public void setHovered(boolean hovered){
        this.hovered = hovered;
    }

    public void remove(){
        shouldRemove = true;
    }

    public Vector2 getPosition(){
        return position;
    }

    public Vector2 getSize(){
        return size;
    }

    public boolean getHovered(){
        return hovered;
    }

    public boolean shouldRemove(){
        return shouldRemove;
    }
}
