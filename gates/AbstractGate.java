/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gates;

import circuit.Circuit;

/**
 *
 * @author Daniel
 */
public abstract class AbstractGate implements IGate {
    public AbstractGate(int inputCount) {
        if(inputCount < 0)
            throw new IllegalArgumentException("inputCount must be >= 0");
        _inputCount = inputCount;
    }
    
    @Override
    public int inputCount() {
        return _inputCount;
    }
    
    @Override
    public String getInputName(int index) {
        checkIndex(index);
        return String.valueOf(index);
    }
    
    @Override
    public abstract boolean getOutput(boolean[] inputs);
    
    private int _inputCount;
    
    private void checkIndex(int index) {
        if(index < 0 || index > inputCount())
            throw new IndexOutOfBoundsException("Invalid index: must be 0 and " + (inputCount() - 1));
    }
}
