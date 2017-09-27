package Input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Created by u1758125 on 26/09/2017.
 */
public class InputMouseMotionListener implements MouseMotionListener{

    private InputHandler inputHandler;

    public InputMouseMotionListener(InputHandler inputHandler){
        this.inputHandler = inputHandler;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        inputHandler.mouseMoved = true;
        inputHandler.mouseX = e.getX() - 3;
        inputHandler.mouseY = e.getY() - 26;
    }
}
