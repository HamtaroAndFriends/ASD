/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import model.Automa;
import model.Event;
import model.State;
import model.Transition;

/**
 *
 * @author Samuele Colombo
 */
public class ControllerAlphabet
{
    /**
     * 
     * @param automa
     * @return 
     */
    public boolean isDeterministic(Automa automa)
    {
        for(State s : automa.getStates())
        {
            List <Transition> trs = automa.getTransitions(s);
            
            List <Event> events = automa.getTransitions(s).stream().map((t) -> (t.getEvent())).collect(Collectors.toList());
            if(Sets.newHashSet(events).size() != events.size()) return false;
        }
        
        return true;
        
    }
}
