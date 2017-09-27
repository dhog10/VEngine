package GUI;

import Main.Game;

import java.awt.*;

/**
 * Created by u1758125 on 26/09/2017.
 */
public class HUD {
    private Game game;

    public HUD(Game game){
        this.game = game;
    }

    public void tick(){

    }

    public void render(Graphics g){
        if(Game.config.showInputDebug){
            g.setColor(new Color(255,255,255));
            g.drawString("Mouse X: " + Integer.toString(game.input.getMouseX()) + " Y: " + Integer.toString(game.input.getMousY()), 10, 100);
            g.drawString("Mouse on screen: " + game.input.getMouseOnScreen(), 10, 120);
            g.drawString("Mouse clicked: " + game.input.getMouseClicked(), 10, 140);
            g.drawString("Mouse dragged: " + game.input.getMouseDragged(), 10, 160);
        }
    }
}
