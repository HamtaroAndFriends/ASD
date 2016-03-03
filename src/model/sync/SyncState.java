/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.sync;

import model.State;

/**
 *
 * @author Fede
 */
public class SyncState extends State
{
    private State state1;
    private State state2;
    
    public SyncState(State state1, State state2)
    {
        this.state1 = state1;
        this.state2 = state2;
    }

    public State getState1() {
        return state1;
    }

    public void setState1(State state1) {
        this.state1 = state1;
    }

    public State getState2() {
        return state2;
    }

    public void setState2(State state2) {
        this.state2 = state2;
    }
    
    
    
}
