package utils;


import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Daniel
 */
public class GraphicsUtils {
    public static void drawCenteredString(Graphics g, String toWrite, int startX,
                                           int startY, int endX, int endY) {
        Rectangle2D stringBounds = g.getFontMetrics()
                    .getStringBounds(toWrite, g);
        g.drawString(toWrite, (startX + endX - (int)stringBounds.getWidth()) / 2,
                     (startY + endY + (int)stringBounds.getHeight()) / 2);
    }
    
    public static void drawCenteredString(Graphics g, String toWrite, Rectangle bounds) {
        drawCenteredString(g, toWrite, bounds.x, bounds.y, 
                bounds.x + bounds.width, bounds.y + bounds.height);
    }
}
