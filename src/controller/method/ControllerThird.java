/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.method;

import controller.ControllerDiagnosability;
import controller.ControllerTwin;
import java.util.HashSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;
import model.Automa;
import model.Container;
import model.Transition;
import model.sync.SyncAutoma;
import model.sync.SyncState;
import model.sync.SyncTransition;

/**
 *
 * @author Andrea
 */
public class ControllerThird {

    private ControllerTwin controllerTwin;
    private ControllerDiagnosability controllerDiagnosability;
    private Map<Integer, Set<Transition>> transizioniAggiunte = new HashMap<>();
    private Map<Integer, SyncAutoma> automiSincronizzati = new HashMap<>();

    /**
     *
     */
    public ControllerThird() {
        this.controllerTwin = new ControllerTwin();
        controllerDiagnosability = new ControllerDiagnosability();
    }

    /**
     *
     * @param container
     * @param l
     * @return
     */
    public int performThirdMethod2(Container container, int l) {
        int i = 1;
        ControllerDiagnosability cd = new ControllerDiagnosability();
        
        Automa prevBad = container.getAutoma();
        
        while (i <= l) 
        {
            int level = i;
            // Retrieve or generate the bad twin of level i
            Automa nextBad = container.getBads().computeIfAbsent(level, (a) -> (controllerTwin.getBadTwin(container.getBad(level), level)));

            if (i > 1) 
            {
                Set<Transition> tPrev = new HashSet<>(prevBad.getTransitions());
                Set<Transition> tNext = new HashSet<>(nextBad.getTransitions());
                tNext.removeAll(tPrev);//retainAll leva da tNext le transizioni che erano presenti anche in tPrev
                transizioniAggiunte.put(i, tNext);
            }
            
            if (cd.isDiagnosabilityC3(nextBad) || cd.isDiagnosabilityC2(nextBad))
            {
                i++;
                prevBad = nextBad;
                
                continue;
            } 


            SyncAutoma syncAutoma;

            if(i > 1)
            {
                syncAutoma = controllerTwin.getSyncSD(automiSincronizzati.get(i - 1), transizioniAggiunte.get(i), nextBad);//metodo2 di sincronizzazione
            }
            else
            {
                Automa nextGood = container.getGoods().computeIfAbsent(level, (a) -> (controllerTwin.getGoodTwin(nextBad)));
                syncAutoma = controllerTwin.getSyncTwin(nextBad, nextGood);//metodo1 di sincronizzazione
            }

            automiSincronizzati.put(i, syncAutoma);  
            
            if(cd.isDiagnosabilityC1(syncAutoma.getTransitions()))
            {
                i++;
                prevBad = nextBad;
                
                continue;
            }
            
            if (isFollowedByAnEndlessLoop(syncAutoma))
            {
                 return (i - 1); 
            }
            
            i++;
            prevBad = nextBad;
        }
        

        return i;
    }
    
    public int performThirdMethod(Container container, int l)
    {
        int i = 1;
        
        Map<Integer, Set<Transition>> added = new HashMap<>();
        Map<Integer, SyncAutoma> syncs = new HashMap<>();
        
        while(i <= l)
        {
           int level = i;
           // Declare the syncAutoma
           SyncAutoma syncAutoma;
           
           Automa nextBad = controllerTwin.getBadTwin(container.getBad(level - 1), level);
           container.getBads().put(level, nextBad);
           Automa nextGood = controllerTwin.getGoodTwin(nextBad);
           container.getGoods().put(level, nextGood);
           syncAutoma = controllerTwin.getSyncTwin(nextBad, nextGood);
           syncs.put(level, syncAutoma);
           
           if(i > 1)
           {
               Set<Transition> ti = new HashSet<>(nextBad.getTransitions());
               Automa prevBad = container.getBad(level - 1);
               ti.removeAll(prevBad.getTransitions()); // 20 TRANSIZIONI?? (NEXT 25, PREV 12)
               added.put(level, ti);
           }
           
           if(controllerDiagnosability.isDiagnosabilityC2(nextBad) || controllerDiagnosability.isDiagnosabilityC3(nextBad))
           {
               i++;
               continue;
           }
           
           if(i > 1)
           {
               syncAutoma = controllerTwin.getSyncSD(syncs.get(level - 1), added.get(level), nextBad);
               syncs.put(level, syncAutoma);
           }
           else
           {
               syncAutoma = controllerTwin.getSyncTwin(nextBad, nextGood);
               syncs.put(level, syncAutoma);
           }
           
           if(controllerDiagnosability.isDiagnosabilityC1(syncAutoma.getTransitions()))
           {
               i++;
               continue;
           }
           
           if(isFollowedByAnEndlessLoop(syncAutoma))
           {
               return (i - 1);
           }
           
           i++;
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
