package Input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by u1758125 on 26/09/2017.
 */
public class InputMouseListener implements MouseListener{

    private InputHandler inputHandler;

    public InputMouseListener(InputHandler inputHandler){
        this.inputHandler = inputHandler;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        inputHandler.mouseClicked = true;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        inputHandler.mouseDown = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        inputHandler.mouseX = e.getX() - 3;
        inputHandler.mouseY = e.getY() - 26;
        inputHandler.mouseDown = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        inputHandler.mouseOnScreen = true;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        inputHandler.mouseOnScreen = false;
    }
}
