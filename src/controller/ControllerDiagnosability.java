/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.List;
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
            List<Transition> t=new ArrayList<>();//tutte le transizioni
            List<Transition> tGuasto=twin.getFaults();
            List<Transition> tOsservabili=twin.getObservables();
            List<Transition> tNonOsservabili=twin.getNotObservables();
            t.addAll(tOsservabili);
            t.addAll(tNonOsservabili);
            List<Event> eGuasti= new ArrayList<>(); //eventi associati a transizioni di guasto
            for(int i=0;i<tGuasto.size();i++){
                if(!eGuasti.contains(tGuasto.get(i).getEvent())){
                    eGuasti.add(tGuasto.get(i).getEvent());
                }
            }
            for(int i=0;i<t.size();i++){
                Transition transizione=t.get(i);
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
