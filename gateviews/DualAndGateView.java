/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gateviews;

import circuit.CircuitElement;
import gates.ConstantGate;
import gates.DualAndGate;
import gates.IGate;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import utils.GraphicsUtils;
import view.StatelessMultipleInputGateEditor;

/**
 *
 * @author Daniel
 */
public class DualAndGateView extends AbstractCircuitElementView {
    private static final int SquareSize = 50;
    
    @Override
    public IGate createInitialGate() {
        return new DualAndGate();
    }

    @Override
    public void drawCircuitElement(Graphics g, Rectangle bounds, CircuitElement element) {
        Graphics graphics = g.create();
        
        int x = (2 *bounds.x + bounds.width - SquareSize) / 2,
            y = (2 * bounds.y + bounds.height - SquareSize) / 2;
        
        graphics.setColor(Color.WHITE);
        graphics.fillRect(x, y, SquareSize, SquareSize);
        
        graphics.setColor(Color.BLACK);
        graphics.drawRect(x, y, SquareSize, SquareSize);
        
        GraphicsUtils.drawCenteredString(g, "AND", 
                bounds);
    }

    @Override
    public void editCircuitElement(CircuitElement element) {
        new StatelessMultipleInputGateEditor(element).setVisible(true);
    }
}
