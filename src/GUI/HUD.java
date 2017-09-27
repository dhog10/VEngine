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

    public HUD(Game game){
        this.game = game;
    }

    public void tick(){
        Iterator it = hudElements.iterator();
        while(it.hasNext()){
            synchronized(it){
                GUIElement e = (GUIElement)it.next();
                if(e.shouldRemove()){
                    e.onRemove();
                    it.remove();
                }else{
                    e.setHovered(game.input.getMouseX() > e.getPosition().getX()
                            && game.input.getMouseX() < e.getPosition().getX() + e.getSize().getX()
                            && game.input.getMouseY() > e.getPosition().getY()
                            && game.input.getMouseY() < e.getPosition().getY() + e.getSize().getY());

                    e.tick();
                }
            }
        }
    }

    public void render(Graphics2D g){
        Iterator it = hudElements.iterator();
        while(it.hasNext()){
            synchronized(it) {
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
            GraphicsUtilities.drawCross(game.input.getMouseX(), game.input.getMouseY(), 10, 10, g);
        }
    }
}
