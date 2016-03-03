/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author Federico Danesi
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import model.Automa;
import model.Event;
import model.State;
import model.Transition;


public class ControllerReachable
{
    public List <State> getReachable(Automa evil)
    {
        List states = evil.getStates();
        
        for(Transition tf : evil.getFaults())
        {
            State s = tf.getEnd();
            boolean found = false;
            for(Transition t : evil.getNotFaults())
            {
                if(s.equals(t.getEnd()))
                {
                    found=true;
                    break;
                }
            }
            if(!found)
            {
                states.remove(s);
            }
        }
        return states;
    }
    
    
}
