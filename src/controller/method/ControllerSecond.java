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

        while (i <= l) {
            int level = i;
            // Retrieve the bad twin of level i-1 (if i-1 is equal to zero, then perform the bad twin
            Automa prevBad = container.getBads().computeIfAbsent(level - 1, (a) -> (controllerTwin.getBadTwin(container.getAutoma(), level - 1)));
            // Retrieve or generate the bad twin of level i
            Automa nextBad = container.getBads().computeIfAbsent(level, (a) -> (controllerTwin.getBadTwin(prevBad, level)));
            ControllerDiagnosability cd = new ControllerDiagnosability();
            if (cd.isDiagnosabilityC3(nextBad) || cd.isDiagnosabilityC2(nextBad)) {
                System.out.println("diagnosticabile");
                i++;
            } else {
                // Retrieve or generate the good twin of level i
               //System.out.println("COntrolloDiagnosability fallita");
                Automa nextGood = container.getGoods().computeIfAbsent(level, (a) -> (controllerTwin.getGoodTwin(nextBad)));
                // Syncrhonized the twins
               System.out.println("passo1");
                SyncAutoma syncAutoma = controllerTwin.getSyncTwin(nextBad, nextGood); // metodo1 di sincronizzazione
                System.out.println("passo2");
                Set <SyncTransition> lst=syncAutoma.getTransitions();
               System.out.println("passo3");
                if(cd.isDiagnosabilityC1(lst)){
                 //   System.out.println("COntrolloDiagnosability finale");
                 System.out.println("passo4");   
                 i++;
                }else{
                    System.out.println("passo5");      
                    if (isFollowedByAnEndlessLoop(syncAutoma)) {
                  //  System.out.println("endlessSummer");
                  System.out.println("passo6");      
                  return (i - 1);
                }

                }
                
            }

        }
        System.out.println("passoFuori");
        return i-1;
    }
    
    public boolean isFollowedByAnEndlessLoop(SyncAutoma automa)
    {
        Set <SyncTransition> ambiguous = getFirstAmbiguousTransitions(automa, automa.getInitial()); //qua c'è un problema
        
        for(SyncTransition t : ambiguous)
        {
            System.out.println("passo10"); 
            if(isFollowedByAnEndlessLoop(automa, t)) return true;
        }
        
        return false;
    }
    
    public Set <SyncTransition> getFirstAmbiguousTransitions(SyncAutoma automa, SyncState state)
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
    
    public boolean isFollowedByAnEndlessLoop(SyncAutoma automa, SyncTransition st)
    {
        Queue <SyncState> queue = new ConcurrentLinkedQueue <> (); 
        Set <SyncState> visited = new HashSet<>();
        
        SyncState start = st.getStart();
        SyncState end = st.getEnd();
        
        if(start.equals(end)) return true;
              
        // To do: check if the start have to been added (in the last one iteration can occur troubles)
        visited.add(start);
        queue.add(end);
        
        
        // Until the queue is not empty
        while(!queue.isEmpty())
        {
            // Get the head
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
                if(visited.contains(t.getEnd()))
                {
                   
                    return true;
                }
                else
                {
                    // Otherwise add the element to the queue
                    queue.add(t.getEnd());
                }
            }
            
            visited.add(state);
        }
        
        return false;
    }

    
    


}
