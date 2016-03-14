/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.List;
import java.util.Map;
import model.Automa;
import model.sync.SyncTransition;

/**
 *
 * @author Samuele Colombo
 */
public class ControllerDiagnosability 
{
    /**
     * 
     * @return 
     */
    public int getDiagnosability()
    {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * This function performs the diagnosability C1.
     * Check if a list of {@link SyncTransition} doesn't contain ambiguous one.
     * 
     * @param syncTransitions
     * @return 
     */
    public boolean isDiagnosabilityC1(List <SyncTransition> syncTransitions)
    {
        // Return true if there isn't ambiguous transitions
        return syncTransitions.stream().noneMatch((st) -> (st.isAmbiguous()));
    }
    
    /**
     * This function performs the diagnosability C2.
     * 
     * @param bads
     * @return 
     */
    public int isDiagnosabilityC2(Map <Integer, Automa> bads)
    {   
        // To do: che significa " fino al livello considerato"
        
        int level = 0;
        
        // Loop over the computed bad twins
        while(bads.containsKey(level))
        {
            // Check if the current bad twin is deterministic
            if(ControllerAlphabet.isDeterministic(bads.get(level)))
            {
                level++;
            }
            else
            {
                return level;
            }
        }
        
        return level;
    }
    
    /**
     * This function performs the diagnosability C3.
     * @return 
     */
    public boolean isDiagnosabilityC3()
    {
        // To do: che significa " fino al livello considerato"
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
