package GUI;

import Main.Game;
import Utility.GraphicsUtilities;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by u1758125 on 26/09/2017.
 */
public class HUD {

    private Game game;
    private ArrayList<GUIElement> hudElements = new ArrayList<GUIElement>();
    protected GUIElement draggedElement;

    public HUD(Game game){
        this.game = game;
    }

    public void tick(){
        Iterator it = hudElements.iterator();
        draggedElement = null;
        while(it.hasNext()){
            synchronized(hudElements){
                GUIElement e = (GUIElement)it.next();
                if(e.shouldRemove()){
                    e.onRemove();
                    it.remove();
                }else{
                    e.setHovered(game.input.getMouseX() > e.getPosition().getX()
                            && game.input.getMouseX() < e.getPosition().getX() + e.getSize().getX()
                            && game.input.getMouseY() > e.getPosition().getY()
                            && game.input.getMouseY() < e.getPosition().getY() + e.getSize().getY());

                    e.setWasHovered(game.input.getLastMouseX() > e.getPosition().getX()
                            && game.input.getLastMouseX() < e.getPosition().getX() + e.getSize().getX()
                            && game.input.getLastMouseY() > e.getPosition().getY()
                            && game.input.getLastMouseY() < e.getPosition().getY() + e.getSize().getY());

                    e.tick();
                }
            }
        }
    }

    public void render(Graphics2D g){
        Iterator it = hudElements.iterator();
        while(it.hasNext()){
            synchronized(hudElements) {
                GUIElement e = (GUIElement) it.next();
                e.render(g);
            }
        }
        if(Game.config.showInputDebug){
            g.setColor(new Color(255,255,255));
            g.drawString("Mouse X: " + Integer.toString(game.input.getMouseX()) + " Y: " + Integer.toString(game.input.getMouseY()), 10, 100);
            g.drawString("Mouse on screen: " + game.input.getMouseOnScreen(), 10, 120);
            g.drawString("Mouse clicked: " + game.input.getMouseClicked(), 10, 140);
            g.drawString("Mouse dragged: " + game.input.getMouseDragged(), 10, 160);
            g.drawString("Mouse down: " + game.input.getMouseDown(), 10, 180);
            GraphicsUtilities.drawCross(game.input.getMouseX(), game.input.getMouseY(), 10, 10, g);
            if(game.input.getMouseDragged()) {
                g.setColor(new Color(0, 255, 100));
                GraphicsUtilities.drawCross(game.input.getMouseDragX(), game.input.getMouseDragY(), 10, 10, g);
                g.drawLine(game.input.getMouseDragX(), game.input.getMouseDragY(), game.input.getMouseX(), game.input.getMouseY());
            }
        }
    }

    public void removeGUIElement(GUIElement element){
        element.remove();
    }

    public void removeGUIElements(){
        synchronized(hudElements){
            for(GUIElement e : hudElements){
                e.remove();
            }
        }
    }

    public void removeWorldElements(){
        synchronized(hudElements){
            for(GUIElement e : hudElements){
                e.remove();
            }
        }
    }

    public GUIElement createGUIElement(GUIElement element, boolean worldElement){
        element.setWorldElement(worldElement);
        element.setHUD(this);
        hudElements.add(element);
        return element;
    }

    public GUIElement createGUIElement(GUIElement element){
        return createGUIElement(element, false);
    }
}
