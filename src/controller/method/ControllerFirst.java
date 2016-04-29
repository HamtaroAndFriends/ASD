/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.method;

import controller.ControllerTwin;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;
import model.Automa;
import model.Container;
import model.sync.SyncAutoma;
import model.sync.SyncState;
import model.sync.SyncTransition;

/**
 *
 * @author Samuele Colombo
 */
public class ControllerFirst 
{
    private ControllerTwin controllerTwin;
    
    /**
     * 
     */
    public ControllerFirst()
    {
        this.controllerTwin = new ControllerTwin();
    }
    
    /**
     * 
     * @param container 
     * @param l 
     * @return  
     */
    public int performFirstMethod(Container container, int l)
    {
        // RIGA 53 CONTAINER.GETAUTOMA è DA CORREGGERE, BISOGNA PASSARGLI L'AUTOMA DEL LIV PRECEDENTE, NON IL
        int i = 1;
        
        while(i < l)
        {
            int level = i;
            // Retrieve the bad twin of level i-1 (if i-1 is equal to zero, then perform the bad twin
            Automa prevBad = container.getBads().computeIfAbsent(level - 1, (a) -> (controllerTwin.getBadTwin(container.getAutoma(), level - 1)));
            // Retrieve or generate the bad twin of level i
            Automa nextBad = container.getBads().computeIfAbsent(level, (a) -> (controllerTwin.getBadTwin(prevBad, level)));
            // Retrieve or generate the good twin of level i
            Automa nextGood = container.getGoods().computeIfAbsent(level, (a) -> (controllerTwin.getGoodTwin(nextBad)));
            // Syncrhonized the twins
            SyncAutoma syncAutoma = controllerTwin.getSyncTwin(nextBad, nextGood);

            if(isFollowedByAnEndlessLoop(syncAutoma))
            {
                return (i - 1);
            }
            
            i++;
        }
        
        return i;
    }
    
    /**
     * 
     * @param automa
     * @return 
     */
    public boolean isFollowedByAnEndlessLoop(SyncAutoma automa)
    {
        //Set <SyncTransition> ambiguous = getFirstAmbiguousTransitions(automa, automa.getInitial());
        Set <SyncTransition> ambiguous = getFirstAmbiguousTransitions(automa);
        
        for(SyncTransition t : ambiguous)
        {
            if(isFollowedByAnEndlessLoop(automa, t)) return true;
        }
        
        return false;
    }
    
    /**
     * 
     * @param automa
     * @param state
     * @return 
     * @deprecated 
     */
    public Set <SyncTransition> getFirstAmbiguousTransitions(SyncAutoma automa, SyncState state)
    {
        Set <SyncTransition> ambiguous = new HashSet <> ();
        
        for(SyncTransition t : automa.getTransitions().stream().filter((s) -> (s.getStart().equals(state))).collect(Collectors.toSet()))
        {
            if(!t.isAmbiguous())
            {
                ambiguous.addAll(ControllerFirst.this.getFirstAmbiguousTransitions(automa, t.getEnd()));
            }
            else
            {
                ambiguous.add(t);
            }
        }
        
        return ambiguous;
    }
    
    public Set <SyncTransition> getFirstAmbiguousTransitions(SyncAutoma automa)
    {
        Queue <SyncState> queue = new ConcurrentLinkedQueue<>() ;
        Set <SyncState> visited = new HashSet <> ();
        Set <SyncTransition> ambiguous = new HashSet <> ();
        
        queue.add(automa.getInitial());
        
        while(!queue.isEmpty())
        {
            // Pop the element on head
            SyncState current = queue.poll();
            
            // If the element was already visited skip to the next loop
            if(visited.contains(current)) continue;
            
            // Save the set of all ambiguos transition from the current state
            ambiguous.addAll(automa.getAmbiguous().stream().filter((t) -> (t.getStart().equals(current))).collect(Collectors.toSet()));
            
            // Add to the queue the end state of each transition the stars from the current state (and is not ambiguous)
            queue.addAll(automa.getTransitions().stream().filter((t) -> (t.getStart().equals(current) && !t.isAmbiguous() && !visited.contains(t.getEnd()))).map((t) -> (t.getEnd())).collect(Collectors.toSet()));
            
            // Mark the current state as visited
            visited.add(current);  
        }
        
        
        return ambiguous;
    }
    
    /**
     * This method checks if the {@link SyncTransition} is follwed by an endless 
     * loop.
     * Worst case O(n^2), usually is O(nk) with k the number of transition linked
     * by each state.
     * 
     * Ogni ciclo presente in qualsivoglia automa considerato (automa di 
     * partenza, twin, automa risultante dalla sincronizzazione di twin) 
     * è «infinito»  nel senso che, nell’ambito di un cammino, può essere 
     * percorso un numero illimitato di volte 
     * @param automa
     * @param st
     * @return 
     */
    public boolean isFollowedByAnEndlessLoop(SyncAutoma automa, SyncTransition st)
    {
        Queue <SyncState> queue = new ConcurrentLinkedQueue <> ();     
              
        // To do: check if the start have to been added (in the last one iteration can occur troubles)
        queue.add(new SyncState(st.getT1().getStart(), st.getT2().getStart()));
        queue.add(new SyncState(st.getT1().getEnd(), st.getT2().getEnd()));
        
        // This SyncTransition is in a loop
        if(queue.size() == 2) return true;
        
        // Until the queue is not empty
        while(!queue.isEmpty())
        {
            // Get the last one pushed element
            SyncState state = queue.poll();
            
            // Get all transitions that start with {@link SyncState} state.
            List <SyncTransition> syncTransition = automa
                    .getTransitions()
                    .stream()
                    .filter((t) -> (t.getStart().equals(state)))
                    .collect(Collectors.toList());
            
            // Loop on all transition from the current state
            for(SyncTransition t : syncTransition)
            {
                if(queue.contains(t.getEnd()))
                {
                    return true;
                }
                else
                {
                    // Otherwise add the element to the queue
                    queue.add(t.getEnd());
                }
            }
        }
        
        return false;
    }
    

}
