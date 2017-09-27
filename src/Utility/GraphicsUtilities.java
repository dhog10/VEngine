package Utility;

import java.awt.*;

public class GraphicsUtilities {
    public static void drawCross(int x, int y, int w, int h, Graphics g){
        g.drawLine(x - w/2, y, x + w/2, y);
        g.drawLine(x, y - h/2, x, y + h/2);
    }
}
