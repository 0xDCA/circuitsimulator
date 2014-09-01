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
public class DualAndGate extends AbstractGate {

    public DualAndGate() {
        super(2);
    }

    @Override
    public boolean getOutput(boolean[] inputs) {
        for(boolean value : inputs) {
            if(!value)
                return false;
        }
        
        return true;
    }
    
}
