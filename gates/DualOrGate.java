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
public class DualOrGate extends AbstractGate {

    public DualOrGate() {
        super(2);
    }

    @Override
    public boolean getOutput(boolean[] inputs) {
        for(boolean value : inputs) {
            if(value)
                return true;
        }
        
        return false;
    }
    
}
