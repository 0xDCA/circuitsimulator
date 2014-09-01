/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gates;

/**
 *
 * @author Daniel
 */
public class ConstantGate extends AbstractGate {
    public boolean value;
    
    public ConstantGate() {
        super(0);
    }
    
    @Override
    public boolean getOutput(boolean[] inputs) {
        return value;
    }
    
}
