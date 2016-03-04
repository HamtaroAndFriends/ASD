/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.sync;

import java.util.ArrayList;
import java.util.List;
import model.State;

/**
 *
 * @author Samuele Colombo
 */
public class SyncAutoma 
{
    /**
     * 
     */
    private State initial;
    
    /**
     * 
     */
    private List <SyncState> states;
    
    /**
     * 
     */
    private List <SyncTransition> transitions;
    
    /**
     * 
     */
    public SyncAutoma()
    {
        this.states = new ArrayList <> ();
        this.transitions = new ArrayList <> ();
    }
    
    /**
     * 
     * @param initial
     * @param states
     * @param transitions 
     */
    public SyncAutoma(State initial, List <SyncState> states, List <SyncTransition> transitions)
    {
        this.initial = initial;
        this.states = states;
        this.transitions = transitions;
    }

    /**
     * 
     * @return 
     */
    public State getInitial() 
    {
        return initial;
    }

    /**
     * 
     * @param initial 
     */
    public void setInitial(State initial) 
    {
        this.initial = initial;
    }

    /**
     * 
     * @return 
     */
    public List<SyncState> getStates()
    {
        return states;
    }

    /**
     * 
     * @param states 
     */
    public void setStates(List<SyncState> states) 
    {
        this.states = states;
    }

    /**
     * 
     * @return 
     */
    public List<SyncTransition> getTransitions()
    {
        return transitions;
    }

    /**
     * 
     * @param transitions 
     */
    public void setTransitions(List<SyncTransition> transitions) 
    {
        this.transitions = transitions;
    }
    
    
}
