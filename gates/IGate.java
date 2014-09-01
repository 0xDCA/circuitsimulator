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
public interface IGate {
    boolean getOutput(boolean[] inputs);
    
    int inputCount();

    String getInputName(int index);
}
