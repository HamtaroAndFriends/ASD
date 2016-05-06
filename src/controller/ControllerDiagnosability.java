/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import model.Automa;
import model.Event;
import model.Transition;
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
    public boolean isDiagnosabilityC1(Set <SyncTransition> syncTransitions)
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
    public boolean isDiagnosabilityC2(Automa cattivo)
    {   
        return ControllerAlphabet.isDeterministic(cattivo);
    }
    
    /**
     * This function performs the diagnosability C3.
     * @return 
     */
    public boolean isDiagnosabilityC3(Automa cattivo)
    {
        // To do: che significa " fino al livello considerato"
       Set <Transition> TF = cattivo.getFaults();
       Set <Transition> TnonF = cattivo.getNotFaults();
       
       for(Transition t1 : TF){
           
           Event e = t1.getEvent();
           
           for(Transition t2 : TnonF){
               
               if(t2.getEvent().equals(e)){
                   return false;
               }
               
           }
           
       }
       
        return true;
    }
}
