package GUI;

import Entities.ThinkCode;
import Main.Game;
import Math.Vector2;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class GUIElement {

    private HUD hud;
    private Vector2 position;
    private Vector2 size;
    private boolean shouldRemove = false;
    private boolean hovered = false;
    private boolean wasHovered = false;
    private boolean worldElement = false;
    private boolean draggable = false;
    private Vector2 dragStart;
    private ThinkCode thinkCode = () -> {};
    private ThinkCode onClickedCode = () -> {};
    private Color color = new Color(0,0,0);
    private Color textColor = new Color(255,255,255);
    private String text = "";
    private boolean centerText = false;
    private GUIElement parent;

    public GUIElement(int x, int y, int w, int h){
        position = new Vector2(x, y);
        size = new Vector2(w, h);
    }

    public void tick(){
        if(hovered && Game.input.getMouseClicked()){
            onClicked();
        }

        if(draggable && wasHovered && Game.input.getMouseDragged() && hud.draggedElement == null){
            hud.draggedElement = this;
            if(dragStart == null){
                dragStart = new Vector2(position.getX(), position.getY());
            }
            int diffx = Game.input.getMouseX() - Game.input.getMouseDragX();
            int diffy = Game.input.getMouseY() - Game.input.getMouseDragY();
            position.setX(dragStart.getX() + diffx);
            position.setY(dragStart.getY() + diffy);
        }else{
            dragStart = null;
        }

        think();
        thinkCode.think();
    }

    public abstract void think();

    public void render(Graphics2D g){
        draw(g);

        FontMetrics metrics = g.getFontMetrics();
        Rectangle2D strBounds = metrics.getStringBounds(text, g);

        g.setColor(textColor);
        if(centerText){
            int strW = g.getFontMetrics().stringWidth(text);
            g.drawString(text, position.getX() + (size.getX() / 2) - (float)(strW / 2), position.getY() + (size.getY() / 2) - (float)(strBounds.getY() / 2));
        }else{
            g.drawString(text, position.getX(), position.getY() + (size.getY() / 2) + (float)(-strBounds.getY() / 2));
        }

        if(Game.config.showGUIElementDebug){
            if(hovered){
                g.setColor(new Color(0,255,0));
            }else{
                g.setColor(new Color(255,0,0));
            }
            g.drawRect(getPosition().getX(), getPosition().getY(), size.getX(), size.getY());
        }
    }

    public abstract void draw(Graphics2D g);

    public abstract void onRemove();

    public void onClicked(){
        onClickedCode.think();
    }

    public void center(){
        int scrW = (int)Game.frame.getResolution().getWidth();
        int scrH = (int)Game.frame.getResolution().getHeight();

        position.setX((scrW / 2) - (size.getX() / 2));
        position.setY((scrH / 2) - (size.getY() / 2));
    }

    public void setHUD(HUD hud){
        this.hud = hud;
    }

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

    public void setWasHovered(boolean wasHovered){
        this.wasHovered = wasHovered;
    }

    public void setWorldElement(boolean worldElement){
        this.worldElement = worldElement;
    }

    public void remove(){
        shouldRemove = true;
    }

    public void setThinkCode(ThinkCode thinkCode){
        this.thinkCode = thinkCode;
    }

    public void setOnClickedCode(ThinkCode onClickedCode){
        this.onClickedCode = onClickedCode;
    }

    public void setColor(Color color){
        this.color = color;
    }

    public void setTextColor(Color textColor){
        this.textColor = textColor;
    }

    public void setText(String text){
        this.text = text;
    }

    public void setDraggable(boolean draggable){
        this.draggable = draggable;
    }

    public void setCenterText(boolean centerText){
        this.centerText = centerText;
    }

    public void setParent(GUIElement parent){
        this.parent = parent;
    }

    public HUD getHUD(){
        return hud;
    }
    public Vector2 getPosition(){
        if(parent == null){return position;}
        int offx = parent.getPosition().getX();
        int offy = parent.getPosition().getY();
        return new Vector2(position.getX() + offx, position.getY() + offy);
    }

    public Vector2 getSize(){
        return size;
    }

    public boolean getHovered(){
        return hovered;
    }

    public boolean wasHovered(){
        return wasHovered;
    }

    public boolean worldElement(){
        return worldElement;
    }

    public boolean getDraggable(){
        return draggable;
    }

    public boolean shouldRemove(){
        return shouldRemove;
    }

    public Color getColor(){
        return color;
    }

    public Color getTextColor(){
        return textColor;
    }

    public String getText(){
        return text;
    }

    public boolean getCenterText(){
        return centerText;
    }

    public GUIElement getParent(){
        return parent;
    }
}
