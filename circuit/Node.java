/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package circuit;

/**
 *
 * @author Daniel
 */
public class Node {
    private int _gateId;
    private int _inputId;

    public Node(int gateId, int inputId) {
        _gateId = gateId;
        _inputId = inputId;
    }
    
    public int getGateId() {
        return _gateId;
    }

    public int getInputId() {
        return _inputId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + this._gateId;
        hash = 73 * hash + this._inputId;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Node other = (Node) obj;
        if (this._gateId != other._gateId) {
            return false;
        }
        if (this._inputId != other._inputId) {
            return false;
        }
        return true;
    }
    
    
}
