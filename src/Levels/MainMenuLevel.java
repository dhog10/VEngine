package Levels;

import GUI.GUIButton;
import GUI.GUIElement;
import GUI.GUIFrame;

import java.awt.*;

public class MainMenuLevel extends Level{

    public MainMenuLevel() {
        super("Main Menu");
    }

    @Override
    public void onLoad() {
        // Main menu frame
        GUIElement frame = getGame().getHUD().createGUIElement(new GUIFrame(0, 0, 600, 350), true);
        frame.center();
        frame.setDraggable(true);

        GUIElement testLevelButton = getGame().getHUD().createGUIElement(new GUIButton(100, 100, 100, 30));
        testLevelButton.setParent(frame);
        testLevelButton.setDraggable(true);
        testLevelButton.setColor(new Color(100,100,100));
    }
}
