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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;
import javax.xml.bind.JAXBException;
import model.Automa;
import model.Container;
import model.Transition;
import model.sync.SyncAutoma;
import model.sync.SyncState;
import model.sync.SyncTransition;
import service.ServiceSyncTwinSecond;

/**
 *
 * @author Andrea
 */
public class ControllerThird {

    private ControllerTwin controllerTwin;
    private Map<Integer, Set<Transition>> transizioniAggiunte = new HashMap<>();
    private Map<Integer, SyncAutoma> automiSincronizzati = new HashMap<>();

    /**
     *
     */
    public ControllerThird() {
        this.controllerTwin = new ControllerTwin();
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
        while (i < l) {
            int level = i;
            // Retrieve the bad twin of level i-1 (if i-1 is equal to zero, then perform the bad twin
            Automa prevBad = container.getBads().computeIfAbsent(level - 1, (a) -> (controllerTwin.getBadTwin(container.getAutoma(), level - 1)));
            // Retrieve or generate the bad twin of level i
            Automa nextBad = container.getBads().computeIfAbsent(level, (a) -> (controllerTwin.getBadTwin(prevBad, level)));

            if (i > 1) {
                Set<Transition> tPrev = prevBad.getTransitions();
                Set<Transition> tNext = nextBad.getTransitions();
                tNext.removeAll(tPrev);//removeAll leva da tNext le transizioni che erano presenti anche in tPrev
                transizioniAggiunte.put(i, tNext);
            }
            if (cd.isDiagnosabilityC3(container.getBads()) || cd.isDiagnosabilityC2(container.getBads())) {
                i++;
            } else {
                SyncAutoma syncAutoma = new SyncAutoma();
                if (i == 1) {
                    Automa nextGood = container.getGoods().computeIfAbsent(level, (a) -> (controllerTwin.getGoodTwin(nextBad)));
                    syncAutoma = controllerTwin.getSyncTwin(nextBad, nextGood);//metodo1 di sincronizzazione

                } else {
                    syncAutoma = controllerTwin.getSyncTwin2(automiSincronizzati.get(i - 1), transizioniAggiunte.get(i));//metodo2 si sincronizzazione

                }
                automiSincronizzati.put(i, syncAutoma);
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
    
    /**
     * 
     * @param container
     * @param l
     * @return 
     */
    public int performThirdMethod(Container container, int l) throws JAXBException
    {
        int i = 1;
        
        ControllerDiagnosability diagnosability = new ControllerDiagnosability();
        ServiceSyncTwinSecond serviceTwin = new ServiceSyncTwinSecond();
        
        Map <Integer, SyncAutoma> syncs = new ConcurrentHashMap <> ();
        
        while(i <= l)
        {
            int level = i;
            
            // Calculate the bad twin of level i
            Automa badi = container.getBads().computeIfAbsent(level - 1, (a) -> (controllerTwin.getBadTwin(container.getAutoma(), level - 1)));
            // Initialize ti
            Set <Transition> ti = new HashSet<>();
            // Generate the syncAutoma
            SyncAutoma sync;
            
            if(i > 1)
            {
                // Get the new transitions
                ti = badi.getTransitions().stream().filter((t) -> container.getBads().get(level - 1).getTransitions().contains(t)).collect(Collectors.toSet());
            }
            
            if(diagnosability.isDiagnosabilityC2(container.getBads()) || diagnosability.isDiagnosabilityC3(container.getBads()))
            {
                // goto INC
                i++;
                continue;
            }
            
            if(i > 1)
            {
                Automa goodi = container.getGoods().computeIfAbsent(level, (a) -> (controllerTwin.getGoodTwin(badi)));
                syncs.put(i, serviceTwin.getSyncTwin2(syncs.get(i - 1), ti, badi));
            }
            else
            {
                Automa goodi = container.getGoods().computeIfAbsent(level, (a) -> (controllerTwin.getGoodTwin(badi)));
                syncs.put(i, controllerTwin.getSyncTwin(badi, goodi));
            }
            
            if(diagnosability.isDiagnosabilityC1(syncs.get(i).getTransitions()))
            {
                //goto INC
                i++;
                continue;
            }
            
            if (isFollowedByAnEndlessLoop(syncs.get(i)))
            {
                return i - 1;
            }
            
            i++;
        }
        
        service.ServiceXML.writeSyncAutoma(syncs.get(i - 1));
        
        return -1;
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
