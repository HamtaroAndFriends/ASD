/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import model.Automa;
import model.Event;
import model.State;

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
    public static boolean isDeterministic(Automa automa)
    {
        for(State s : automa.getStates())
        {
            List <Event> eventsWithDuplicate = automa
                    .getTransitions(s)
                    .stream()
                    .map((t) -> (t.getEvent()))
                    .collect(Collectors.toList());
            
            Set <Event> eventsWithoutDuplicates = new HashSet<>(eventsWithDuplicate);
                                    
            if(eventsWithoutDuplicates.size() != eventsWithDuplicate.size()) return false;
        }
        
        return true;
        
    }
}
