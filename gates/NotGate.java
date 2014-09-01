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
public class NotGate extends AbstractGate {

    public NotGate() {
        super(1);
    }

    @Override
    public boolean getOutput(boolean[] inputs) {
        return !inputs[0];
    }
}
