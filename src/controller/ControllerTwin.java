/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.List;
import model.Automa;
import model.Container;
import model.State;
import model.Transition;

/**
 *
 * @author Samuele Colombo
 */
public class ControllerTwin 
{
    public Automa getBadTwin(Container container, Integer level)
    {
        // Get the states of the automa
        List <State> s1 = container.getAutoma().getStates();
        // Get the initial state
        State s0 = getInitialState(container.getAutoma());
        //Get the observable transitions of the automa
        List <Transition> to = container.getAutoma().getObservables();
        // Initialize the array of the fault transitions
        List <Transition> tf = new ArrayList <> ();
        
        // Check if the bad twin is already performed
        if(container.getBads().containsKey(level))
        {
            return container.getBads().get(level);
        }
        
        if(level == 0)
        {
            // Foreach state in the automa
            for(State s : container.getAutoma().getStates())
            {
                // Foreach transition that is not observable (fault)
                for(Transition t : container.getAutoma().getFaults())
                {
                    
                }
            }
        }

    }
    
    public State getInitialState(Automa automa)
    {
        for(State state : automa.getStates())
        {
            if(state.isIsInitial()) return state;
        }
        
        return null;
    }
    

}
