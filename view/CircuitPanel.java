/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import circuit.Circuit;
import circuit.CircuitElement;
import gateviews.AbstractCircuitElementView;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;

class Cell {
    public Cell() { }
    public Cell(int row, int column) {
        this.column = column;
        this.row = row;
    }
    
    int column;
    int row;
}

class CellElement {
    AbstractCircuitElementView view;
    CircuitElement circuitElement;
}

/**
 *
 * @author Daniel
 */
public class CircuitPanel extends JPanel implements MouseListener, MouseMotionListener {
    private int _cellWidth;
    private int _cellHeight;
    private int _columns;
    private int _rows;
    private CellElement[][] _elements;
    private Map<Integer, Cell> _idMappings;
    private boolean _mouseIn;
    private Point _lastMousePoint;
    private AbstractCircuitElementView _toAdd;
    private Circuit _circuit;
    
    public CircuitPanel(int columns, int rows, int cellWidth, int cellHeight) {
        _mouseIn = false;
        _columns = columns;
        _rows = rows;
        _cellWidth = cellWidth;
        _cellHeight = cellHeight;
        _elements = new CellElement[_rows][_columns];
        _circuit = new Circuit();
        _idMappings = new HashMap<>();
        
        this.setPreferredSize(
                new Dimension(_cellWidth * _columns, _cellHeight * _rows));
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }
    
    public Circuit getCircuit() {
        return _circuit;
    }
    
    public void setElementToAdd(AbstractCircuitElementView toAdd) {
        _toAdd = toAdd;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        g.clearRect(0, 0, this.getWidth(), this.getHeight());
        
        CircuitElement[] allElements = _circuit.getAllElements();
        
        // Conexiones
        for (int i = 0; i < allElements.length; ++i) {
            Cell cell = _idMappings.get(allElements[i].getId());
            Rectangle cellBounds = getCellBounds(cell);

            g.setColor(Color.BLACK);

            CircuitElement circuitElement = allElements[i];

            Map<Integer, Integer> connections = circuitElement.getInputConnections();
            for (Integer source : connections.values()) {
                Cell sourceCell = _idMappings.get(source);
                if (sourceCell == null) {
                    continue;
                }
                
                Rectangle sourceBounds = getCellBounds(sourceCell);
                g.drawLine((2 * sourceBounds.x + sourceBounds.width) / 2,
                        (2 * sourceBounds.y + sourceBounds.height) / 2,
                        (2 * cellBounds.x + cellBounds.width) / 2,
                        (2 * cellBounds.y + cellBounds.height) / 2);
            }
        }
        
        // Compuertas
        for (int i = 0; i < allElements.length; ++i) {
            Cell cell = _idMappings.get(allElements[i].getId());
            Rectangle cellBounds = getCellBounds(cell);

            /*g.setColor(Color.LIGHT_GRAY);
            g.fillRect(cellBounds.x, cellBounds.y, cellBounds.width,
                    cellBounds.height);*/

            _elements[cell.row][cell.column].view.drawCircuitElement(g, 
                    cellBounds, allElements[i]);
        }
        
        
        if(_mouseIn && _lastMousePoint != null) {
            Cell overCell = getCellFromPoint(_lastMousePoint);
            
            Rectangle cellBounds = getCellBounds(overCell);
            
            // Líneas alrededor del cuadrado de la celda actual
            g.setColor(Color.BLUE);
            // Horizontales:
            g.drawLine(0, cellBounds.y, this.getWidth(), cellBounds.y);
            g.drawLine(0, cellBounds.y + cellBounds.height, this.getWidth(), 
                    cellBounds.y + cellBounds.height);
            // Verticales:
            g.drawLine(cellBounds.x, 0, cellBounds.x, this.getHeight());
            g.drawLine(cellBounds.x + cellBounds.width, 0, 
                    cellBounds.x + cellBounds.width, this.getHeight());
            
            // Rellenamos la celda
            if(_elements[overCell.row][overCell.column] == null)
                g.setColor(new Color(128, 128, 128, 200));
            else
                g.setColor(new Color(255, 0, 0, 200));
            g.fillRect(cellBounds.x, cellBounds.y, cellBounds.width,
                    cellBounds.height);
        }

    }
    
    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mousePressed(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(_toAdd == null)
            return;
        
        Cell overCell = getCellFromPoint(_lastMousePoint);
        CellElement oldElement = _elements[overCell.row][overCell.column];
        
        if(e.getButton() == MouseEvent.BUTTON1) {
            if(oldElement == null) {
                CellElement element = new CellElement();
                element.view = _toAdd;
                int id = _circuit.addGate(_toAdd.createInitialGate());
                element.circuitElement = _circuit.getCircuitElement(id);

                _elements[overCell.row][overCell.column] = element;
                _idMappings.put(id, overCell);
            }
        } else {
            if(oldElement != null)
                oldElement.view.editCircuitElement(oldElement.circuitElement);
        }
        
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        _mouseIn = true;
        repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        _mouseIn = false;
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) { }

    @Override
    public void mouseMoved(MouseEvent e) {
        _lastMousePoint = this.getMousePosition();
        repaint();
    } 
    
    private void setElement(Cell cell, CellElement e) {
        _elements[cell.row][cell.column] = e;
        repaint();
    }
    
    private Cell getCellFromPoint(Point point) {
        Cell result = new Cell();
        
        result.column = point.x / _cellWidth;
        result.row = point.y / _cellHeight;
        
        return result;
    }
    
    private Rectangle getCellBounds(Cell cell) {
        return new Rectangle(cell.column * _cellWidth, cell.row * _cellHeight, 
                _cellWidth, _cellHeight);
    }
}
