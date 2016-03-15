/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.method;

import controller.ControllerDiagnosability;
import controller.ControllerTwin;
import java.util.HashSet;
import java.util.Set;
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
 * @author Andrea
 */
public class ControllerSecond {

    private ControllerTwin controllerTwin;

    /**
     *
     */
    public ControllerSecond() {
        this.controllerTwin = new ControllerTwin();
    }

    /**
     *
     * @param container
     * @return
     */
    public int performSecondMethod(Container container, int l) {
        int i = 1;

        while (i < l) {
            int level = i;
            // Retrieve the bad twin of level i-1 (if i-1 is equal to zero, then perform the bad twin
            Automa prevBad = container.getBads().computeIfAbsent(level - 1, (a) -> (controllerTwin.getBadTwin(container.getAutoma(), level - 1)));
            // Retrieve or generate the bad twin of level i
            Automa nextBad = container.getBads().computeIfAbsent(level, (a) -> (controllerTwin.getBadTwin(prevBad, level)));
            ControllerDiagnosability cd = new ControllerDiagnosability();
            if (cd.isDiagnosabilityC3(container.getBads()) || cd.isDiagnosabilityC2(container.getBads())) {
                i++;
            } else {
                // Retrieve or generate the good twin of level i
                Automa nextGood = container.getGoods().computeIfAbsent(level, (a) -> (controllerTwin.getGoodTwin(nextBad)));
                // Syncrhonized the twins
                SyncAutoma syncAutoma = controllerTwin.getSyncTwin(nextBad, nextGood); // metodo1 di sincronizzazione
                Set <SyncTransition> lst=syncAutoma.getTransitions();
                if(cd.isDiagnosabilityC1(lst)){
                    i++;
                }else{
                    if (isFollowedByAnEndlessLoop(syncAutoma)) {
                    return (i - 1);
                }

                }
                
            }

        }

        return i;
    }
    
    public boolean isFollowedByAnEndlessLoop(SyncAutoma automa)
    {
        Set <SyncTransition> ambiguous = getFirstAmiguousTransitions(automa, automa.getInitial());
        
        for(SyncTransition t : ambiguous)
        {
            if(isFollowedByAnEndlessLoop(automa, t)) return true;
        }
        
        return false;
    }
    
    public Set <SyncTransition> getFirstAmiguousTransitions(SyncAutoma automa, SyncState state)
    {
        Set <SyncTransition> ambiguous = new HashSet <> ();
        
        for(SyncTransition t : automa.getTransitions().stream().filter((s) -> (s.getStart().equals(state))).collect(Collectors.toSet()))
        {
            if(!t.isAmbiguous())
            {
                ambiguous.addAll(getFirstAmiguousTransitions(automa, t.getEnd()));
            }
            else
            {
                ambiguous.add(t);
            }
        }
        
        return ambiguous;
    }
    
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
            Set <SyncTransition> syncTransition = automa
                    .getTransitions()
                    .stream()
                    .filter((t) -> (t.getStart().equals(state)))
                    .collect(Collectors.toSet());
            
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
