/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.sync;

import model.Transition;

/**
 *
 * @author Fede
 */
public class SyncTransition extends Transition
{
    private Transition t1;
    private Transition t2;
    
    public SyncTransition(Transition t1, Transition t2)
    {
        this.t1 = t1;
        this.t2 = t2;
    }

    public Transition getT1() {
        return t1;
    }

    public void setT1(Transition t1) {
        this.t1 = t1;
    }

    public Transition getT2() {
        return t2;
    }

    public void setT2(Transition t2) {
        this.t2 = t2;
    }
    
    
}
