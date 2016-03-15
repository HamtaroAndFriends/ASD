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
    public boolean isDiagnosabilityC2(Map <Integer, Automa> bads)
    {   
        // To do: che significa " fino al livello considerato"
        boolean isC2=true;
        int level = 0;
        
        // Loop over the computed bad twins
        while(bads.containsKey(level) && isC2)
        {
            // Check if the current bad twin is deterministic
            if(ControllerAlphabet.isDeterministic(bads.get(level)))
            {
                level++;
            }
            else
            {
                isC2=false;
            }
        }
        
        return isC2;
    }
    
    /**
     * This function performs the diagnosability C3.
     * @return 
     */
    public boolean isDiagnosabilityC3(Map <Integer, Automa> bads)
    {
        // To do: che significa " fino al livello considerato"
        
        int level = 0;
        boolean isC3=true;
        // Loop over the computed bad twins
        while(bads.containsKey(level) && isC3){
            Automa twin=bads.get(level);
            Set<Transition> t=new HashSet<>();//tutte le transizioni
            Set<Transition> tGuasto=twin.getFaults();
            Set<Transition> tOsservabili=twin.getObservables();
            Set<Transition> tNonOsservabili=twin.getNotObservables();
            t.addAll(tOsservabili);
            t.addAll(tNonOsservabili);
            Set<Event> eGuasti= new HashSet<>(); //eventi associati a transizioni di guasto
            
            for(Transition i : tGuasto){
                if(!eGuasti.contains(i.getEvent())){
                    eGuasti.add(i.getEvent());
                }
            }
            
            for(Transition i : t){
                Transition transizione= i;
                if(!transizione.isFault()){ // prendo solo le transizioni non di guasto
                   Event e=transizione.getEvent();
                   if(eGuasti.contains(e)){
                       isC3=false;
                   }
                }
            }
            
            level++;
        }
        
        
        return isC3;
    }
}
