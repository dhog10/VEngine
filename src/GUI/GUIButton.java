package GUI;

import java.awt.*;

public class GUIButton extends GUIElement{

    public GUIButton(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    @Override
    public void think() {

    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(getColor());
        g.fillRect(getPosition().getX(), getPosition().getY(), getSize().getX(), getSize().getY());
    }

    @Override
    public void onRemove() {

    }
}
