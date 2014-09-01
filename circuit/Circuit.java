/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package circuit;

import gates.IGate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Circuit {
    
    public Circuit() {
        _elements = new HashMap<>();
        _currentId = 1;
    }
    
    public int addGate(IGate gate, String name) {
        if(gate == null)
            throw new IllegalArgumentException("gate must not be null");
        
        int id = _currentId;
        
        InternalCircuitElement element = new InternalCircuitElement();
        element.gate = gate;
        element.id = id;
        element.inputConnections = new HashMap<>();
        element.gateName = name;
        
        _elements.put(id, element);
        ++_currentId;
        
        return id;
    }
    
    public int addGate(IGate gate) {
        return addGate(gate, 
                String.format("%d_%s", _currentId, gate.getClass().getName()));
    }
    
    public CircuitElement getCircuitElement(Integer gateId) {
        if(!_elements.containsKey(gateId))
            return null;
        return new CircuitElement(_elements.get(gateId), this);
    }
    
    public CircuitElement[] getAllElements() {
        CircuitElement[] result = new CircuitElement[_elements.size()];
        
        int i = 0;
        for(Integer id : _elements.keySet()) {
            result[i++] = getCircuitElement(id);
        }
        
        return result;
    }
    
    public void addConnection(int sourceId, Node node) {
        if(node == null)
            throw new IllegalArgumentException("node can't be null");
        
        InternalCircuitElement destinationElement = 
                _elements.get(node.getGateId());
        InternalCircuitElement sourceElement = _elements.get(sourceId);
        
        if(sourceElement == null)
            throw new IllegalArgumentException("Source gate does not exist");
        
        if(destinationElement == null)
            throw new IllegalArgumentException("Destination gate does not exist");
        
        int destinationInput = node.getInputId();
        
        if(destinationInput < 0 || 
                destinationInput >= destinationElement.gate.inputCount())
            throw new IllegalArgumentException("Destination input does not exist");
        
        destinationElement.inputConnections.put(destinationInput, sourceId);
    }
    
    public void removeConnection(Node node) {
        if(node == null)
            throw new IllegalArgumentException("node can't be null");
        
        InternalCircuitElement destinationElement = 
                _elements.get(node.getGateId());
        
        if(destinationElement != null)
            destinationElement.inputConnections.remove(node.getInputId());
    }
    
    public void removeAllInputs(int gateId) {
        InternalCircuitElement destinationElement = 
                _elements.get(gateId);
        
        if(destinationElement != null)
            destinationElement.inputConnections = new HashMap<>();
    }
    
    public Map<Integer, Boolean> simulate() throws CircuitException {
        checkCircuitIsNotCyclic();
        checkCircuitHasNoFreeInputs();
        
        Map<Integer, Boolean> result = new HashMap<>();
        
        for(InternalCircuitElement element : _elements.values()) {
            element.checkInputs();
            evaluateGate(element, result);
        }
        
        return result;
    }
    
    public Map<Integer, Set<Integer>> findFreeInputs() {
        Map<Integer, Set<Integer>> result = new HashMap<>();
        
        for(InternalCircuitElement element : _elements.values()) {
            IGate gate = element.gate;
            
            Set<Integer> freeInputs = 
                    new HashSet<>(gate.inputCount() - element.inputConnections.size());
            
            for(int i = 0; i < gate.inputCount(); ++i) {
                if(!element.inputConnections.containsKey(i)) {
                    freeInputs.add(i);
                }
            }
            
            if(!freeInputs.isEmpty())
                result.put(element.id, freeInputs);
        }
        
        return result;
    }
    
    private Map<Integer, Set<Integer>> findAllDependencies() {
        Map<Integer, Set<Integer>> result = new HashMap<>();
        
        for(InternalCircuitElement element : _elements.values()) {
            fillDependencies(element, result);
        }
        
        return result;
    }
    
    private void fillDependencies(InternalCircuitElement element, 
            Map<Integer, Set<Integer>> output) {
        if(output.containsKey(element.id))
            return;
        
        Set<Integer> dependencies = new HashSet<>();
        output.put(element.id, dependencies);
        
        for(Integer source : element.inputConnections.values()) {
            dependencies.add(source);
            fillDependencies(_elements.get(source), output);
            dependencies.addAll(output.get(source));
        }
    }
    
    private void checkCircuitIsNotCyclic() throws CircuitException {
        Map<Integer, Set<Integer>> dependencies = findAllDependencies();
        
        for(Entry<Integer, Set<Integer>> entry : dependencies.entrySet()) {
            if(entry.getValue().contains(entry.getKey()))
                throw new CircuitException("Circuit is cyclic");
        }
    }
    
    private void checkCircuitHasNoFreeInputs() throws CircuitException {
        Map<Integer, Set<Integer>> freeInputs = findFreeInputs();
        
        if(!freeInputs.isEmpty())
            throw new CircuitException("Circuit has empty inputs");
    }
    
    private void evaluateGate(InternalCircuitElement element, 
            Map<Integer, Boolean> result) throws CircuitException {
        if(result.containsKey(element.id))
            return;
        
        boolean[] inputs = new boolean[element.gate.inputCount()];
        
        for(Entry<Integer, Integer> connection : element.inputConnections.entrySet()) {
            int inputId = connection.getKey();
            int sourceId = connection.getValue();
            if(!_elements.containsKey(sourceId))
                throw new CircuitException("Invalid source connection for "
                    + "input " + inputId + ", gate " + element.id);
            
            evaluateGate(_elements.get(sourceId), result);
            inputs[inputId] = result.get(sourceId);
        }
        
        result.put(element.id, element.gate.getOutput(inputs));
    }
    
    private Map<Integer, InternalCircuitElement> _elements;
    private int _currentId;
}
