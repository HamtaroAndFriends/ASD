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


import java.util.HashSet;
import java.util.Set;
import model.Automa;
import model.State;
import model.Transition;


public class ControllerReachable
{
    public Set <State> getReachable(Automa evil)
    {
        Set states = new HashSet(evil.getStates());
        
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
