/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package circuit;

import gates.IGate;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Daniel
 */
public class CircuitElement {

    CircuitElement(InternalCircuitElement internalElement, Circuit circuit) {
        if(internalElement == null)
            throw new IllegalArgumentException("Invalid internalElement");
        if(circuit == null)
            throw new IllegalArgumentException("Invalid circuit");
        
        _element = internalElement;
        _circuit = circuit;
    }

    public IGate getGate() {
        return _element.gate;
    }
    
    public int getId() {
        return _element.id;
    }
    
    public Map<Integer, Integer> getInputConnections() {
        return new HashMap<>(_element.inputConnections);
    }
    
    public String getGateName() {
        return _element.gateName;
    }
    
    public void setGateName(String name) {
        _element.gateName = name;
    }
    
    public Circuit getCircuit() {
        return _circuit;
    }
    
    private InternalCircuitElement _element;
    private Circuit _circuit;
}

class InternalCircuitElement {
    Map<Integer, Integer> inputConnections;
    IGate gate;
    int id;
    String gateName;
    
    void checkInputs() throws CircuitException {
        if(inputConnections.size() != gate.inputCount())
            throw new CircuitException("Not enough inputs for gate " + id);
    }
}