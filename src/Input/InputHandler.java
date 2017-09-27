package Input;

import Main.Game;

/**
 * Created by u1758125 on 26/09/2017.
 */
public class InputHandler {
    private Game game;

    protected boolean mouseMoved = false;
    protected boolean mouseClicked = false;
    protected boolean mouseDown = false;
    protected boolean mouseOnScreen = false;
    protected boolean mouseDragged = false;
    protected int mouseX = 0, mouseY = 0;
    protected int mouseDragX = 0, mouseDragY = 0;

    private InputMouseMotionListener mouseMotionListener;
    private InputMouseListener mouseListener;

    public InputHandler(Game game){
        this.game = game;
        mouseMotionListener = new InputMouseMotionListener(this);
        mouseListener = new InputMouseListener(this);
    }

    public void tick(){
        mouseClicked = false;
    }

    public boolean getMouseMoved(){
        return mouseMoved;
    }

    public boolean getMouseClicked(){
        return mouseClicked;
    }

    public boolean getMouseDown(){
        return mouseDown;
    }

    public boolean getMouseOnScreen(){
        return mouseOnScreen;
    }

    public boolean getMouseDragged(){
        return mouseDragged;
    }

    public int getMouseX(){
        return mouseX;
    }

    public int getMousY(){
        return mouseY;
    }

    public int getMouseDragX(){
        return mouseDragX;
    }

    public int getMouseDragY(){
        return mouseDragY;
    }

    public InputMouseMotionListener getMouseMotionListener(){
        return mouseMotionListener;
    }

    public InputMouseListener getMouseListener(){
        return mouseListener;
    }
}
