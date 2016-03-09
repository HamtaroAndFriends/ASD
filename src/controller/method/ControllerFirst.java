/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.method;

import controller.ControllerTwin;
import java.util.List;
import java.util.stream.Collectors;
import model.Automa;
import model.Container;
import model.sync.SyncAutoma;
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
     */
    public void performFirstMethod(Container container)
    {
        int i = 1;
        int l = 0;
        
        
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
            
            // Get the first amgiguous transition
            SyncTransition firstAmbiguous = getFirstAmbiguousTransition(syncAutoma);
            
            if(isFollowedByInfiniteCicle(syncAutoma))
            {
                throw new UnsupportedOperationException("Not implemented yet");
            }
            
            i++;
        }
        
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    public SyncTransition getFirstAmbiguousTransition(SyncAutoma automa)
    {
        List <SyncTransition> ambiguous = automa.getTransitions().stream().filter((p) -> (p.isAmbiguous())).collect(Collectors.toList());
        
        /**
         * Definizione.
         * Per «prima transizione ambigua di un cammino» si intende la prima che 
         * si incontra seguendo il cammino nell’automa risultante dalla sincronizzazione
         * (partendo dunque dallo stato iniziale).
         * 
         * Non è del tutto chiaro cosa si definisca con cammino dell'automa:
         * si trova con una DFS o una BFS? Quali eventi preferire?
         */
        
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    public boolean isFollowedByInfiniteCicle(SyncAutoma automa)
    {
        
        
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
