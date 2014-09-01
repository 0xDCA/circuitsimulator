/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gateviews;

import circuit.CircuitElement;
import gates.IGate;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Daniel
 */
public abstract class AbstractCircuitElementView {
    public abstract IGate createInitialGate();
    public abstract void drawCircuitElement(Graphics g, Rectangle bounds, CircuitElement element);
    public abstract void editCircuitElement(CircuitElement element);
}
