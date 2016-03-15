/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import model.Automa;
import model.Event;
import model.State;
import model.Transition;
import model.sync.SyncAutoma;
import model.sync.SyncState;
import model.sync.SyncTransition;

/**
 *
 * @author Samuele Colombo
 */
public class ControllerTwin 
{       
    /**
     * This method finds the bad twin at level 1 that has only observable transitions.
     * Be careful to use this method on massive automatons, because the complexity
     * could be very high.
     * 
     * @param automa
     * @return 
     * 
     */
    public Automa getBadTwinOne(Automa automa)
    {
        // Get the list of the state of the automa
        List <State> s1 = automa.getStates();
        // Get the initial state of the automa
        State so1 = automa.getInitial();
        // Get the list of all observable transitions
        List <Transition> to = automa.getObservables();
        // Get the list of all fault transitions
        List <Transition> tf = new ArrayList <> ();
        // Fault parameter
        boolean fault;

        // Foreach state of the automa
        for(State s : automa.getStates())
        {
            
            // Foreach transition in the fault list that starts from s and reaches sd
            for(Transition t : automa.getTransitions(s).stream().filter((p) -> (p.isObservable() == false)).collect(Collectors.toList()))
            {
                // Set fault if the transition is a fault one
                fault = automa.getFaults().contains(t);

                // Define the list of the tuples
                List <List <Object>> tuples = find(automa, t.getEnd(), 1, fault, new Event());

                // Foreach triple in the list
                for(List <Object> tuple : tuples)
                {
                    // Create a new transition from s to s' with event o
                    Transition t1 = new Transition(s, (State) tuple.get(1), (Event) tuple.get(0), (boolean) tuple.get(2), true);
                    // Add this transition to observable list
                    to.add(t1);

                    // Check if the third parameter (fault') is true
                    if(((Boolean)tuple.get(2)) == true)
                    {
                        // Add this transition to fault list
                        tf.add(t1);
                    }
                } 
            }
        }
        
        // Define the complete list of the transitions
        List <Transition> ta = new ArrayList <> ();
        // Add all observable transitions
        ta.addAll(to);
        // Add all fault transitions
        ta.addAll(tf);
        
        //To do: remove all not reacheable states
        
        //Note: to is used insted of ta (it generate duplicates, but you could use it in the cache)
        return new Automa(so1, s1, to);
    }
    
    /**
     * 
     * @param automa
     * @param n
     * @param s
     * @param fault
     * @param ot
     * @return 
     */
    public List <List<Object>> find(Automa automa, State s, int n, boolean fault, Event ot)
    {
        // Define the list of the tuples
        List <List <Object>> tuples = new ArrayList <> ();
        // Define the variabile fault'
        boolean fault1;
        
        // Foreach transition that stars from state s
        for(Transition t : automa.getTransitions(s))
        {
            // Check if t is a fault transition
            if(automa.getFaults().contains(t))
            {
                fault1 = true;
            }
            else
            {
                fault1 = fault;
            }
            
            // Get the event of the transition
            Event o = t.getEvent();
            
            // Check if the transition is observable and the cardinality of the event is less equal than n
            if(automa.getObservables().contains(t) && o.getEvents().size() <= n)
            {
                // Create the complex event oot
                Event oot = new Event();
                oot.getEvents().addAll(o.getEvents());
                oot.getEvents().addAll(ot.getEvents());
                
                // Check if the cardinality of the event is equal to n
                if(o.getEvents().size() == n)
                {         
                    List <Object> tuple = new ArrayList <> ();

                    tuple.add(0, oot);
                    tuple.add(1, t.getEnd());
                    tuple.add(2, fault1);
                    
                    tuples.add(tuple);
                }
                else
                {
                    List <List <Object>> result = find(automa, t.getEnd(), n - o.getEvents().size(), fault1, oot);
                    tuples.addAll(result);
                }
            }
            
            if(!automa.getObservables().contains(t))
            {
                List <List <Object>> result = find(automa, t.getEnd(), n, fault1, ot);
                tuples.addAll(result);
            }
        }
        
        return tuples;
    }
    
    /**
     * 
     * @param bad
     * @param i
     * @return 
     */
    public Automa getBadTwinI(Automa bad, int i)
    {
        // The list of the transitions of the previous bad twin
        List <Transition> t1 = bad.getTransitions();
        // The list of the fault transitions of the previous bad twin
        List <Transition> tf = bad.getFaults();
        
        boolean fault;
        
        for(State s : bad.getStates())
        {
            for(Transition t : bad.getTransitions(s))
            {
                // Is true if t is a fault transition, false if not
                fault = tf.contains(t);
                
                Event ot = t.getEvent();
                
                List <List <Object>> tuples = find(bad, t.getEnd(), i - ot.getEvents().size() , fault, ot);
                
                // Foreach triple in the list
                for(List <Object> tuple : tuples)
                {
                    Event o = (Event) tuple.get(0);
                    
                    // Check if the simple event inside ot are not identical
                    if(!containsIdenticalEvent(o))
                    {
                        // Create a new transition
                        Transition tn = new  Transition(s, (State) tuple.get(1), o, (boolean) tuple.get(2), true);
                        
                        // Add the transition to the list
                        t1.add(tn);
                        
                        if(((Boolean)tuple.get(2)) == true)
                        {
                            // Add this transition to fault list
                            tf.add(tn);
                        }
                    }
                } 
            }
        }
        
        // To do: check if Java requires to merge the transitions with the fault ones
        
        return new Automa(bad.getInitial(), bad.getStates(), t1);
    }
    
    
    /**
     * This method returns true if the simple event inside the given one are 
     * identical.
     * 
     * @param event
     * @return 
     */
    public boolean containsIdenticalEvent(Event event)
    {
        if(event.getEvents().size() <= 1) 
        {
            return false;
        }
        else 
        {
            return Sets.newHashSet(event.getEvents()).size() == 1;
        }
    }
    
    /**
     * 
     * @param automa
     * @param level
     * @return 
     */
    public Automa getBadTwin(Automa automa, int level)
    {
        if(level == 1) 
        {
            return getBadTwinOne(automa);
        }
        else
        {
            return getBadTwinI(automa, level);
        }
    }
    
    /**
     * 
     * @param bad
     * @return 
     */
    public Automa getGoodTwin(Automa bad)
    {
        // The initial state of the bad twin
        State so1 = bad.getInitial();
        // The states of the bad twin except the unreacheable ones given only T\Tf transitions
        ControllerReachable controllerReachable = new ControllerReachable();
        List <State> s1 = controllerReachable.getReachable(bad);
        // The transitions of the bad twin except the fault ones
        List <Transition> t1 = bad.getNotFaults();
        
        return new Automa(so1, s1, t1);
    }
    
    
    /**
     * 
     * @param bad
     * @param good
     * @return 
     */
    public SyncAutoma getSyncTwin2(SyncAutoma sa,List<Transition> ti){
        return sa;
    }
    
    
    public SyncAutoma getSyncTwin(Automa bad, Automa good)
    {
        List <SyncTransition> ta = new ArrayList();
        List <SyncTransition> tDue = new ArrayList();
        List <SyncState> sDue = new ArrayList();
        SyncState so;
        
        for(State s: good.getStates())
        {
            SyncState coppia = new SyncState(s, s);
            sDue.add(coppia);
        }
        
        for(Transition t: good.getTransitions())
        {
            SyncTransition coppiaT = new SyncTransition(t,t);
            tDue.add(coppiaT);
        }
        
        Set <SyncTransition> taset = new HashSet();
        Set <SyncTransition> tDueset = new HashSet();
        Set <SyncState> sDueset = new HashSet(); 
        
        taset.addAll(ta);
        tDueset.addAll(tDue);
        sDueset.addAll(sDue);
        ta.clear();
        tDue.clear();
        sDue.clear();
        ta.addAll(taset);
        tDue.addAll(tDueset);
        sDue.addAll(sDueset);
        
        // **************************************
        // da 304 a 316 per rimuovere i duplicati
        // **************************************
        
        so = sDue
                .stream()
                .filter((w) -> (w.getState1().isInitial() && w.getState2().isInitial()))
                .findFirst()
                .get();
        
        if(ControllerAlphabet.isDeterministic(bad))
        {
            return new SyncAutoma(so, sDue, tDue, ta);
        }
        else
        {
           List <SyncState> sPrev = new ArrayList(sDue);
           for(State s: good.getStates())
           {
              List <Transition> allT = bad.getTransitions(s);
              List <Transition> notFaultT = bad
                      .getTransitions(s)
                      .stream()
                      .filter((a) -> (!a.isFault()))
                      .collect(Collectors.toList());
              
              
              for(Transition t1: allT)
              {
               System.out.println();
                  
                  for(Transition t2: notFaultT)
                  {
                      if(t1.getStart().equals(t2.getStart()) && t1.getEnd().equals(t2.getEnd()) && t1.isFault())
                          System.out.println();
                      if(t1.getEvent().equals(t2.getEvent()) && t1.isObservable() && !t1.equals(t2))
                      {
                          SyncTransition t12 = new SyncTransition(t1,t2);
                          SyncState s12 = new SyncState(t1.getEnd(),t2.getEnd());
                          sDue.add(s12);
                          tDue.add(t12);
                          if(t1.isFault())
                          {
                              ta.add(t12);
                          }
                      }
                  }
              }
              
           }
           
           
           
           while(!sDue.equals(sPrev))
           {
               List <SyncState> sDiff = new ArrayList(sDue);
               for(SyncState s: sPrev)
               {
                   sDiff.remove(s);
               }
               sPrev = new ArrayList(sDue);
               
               for(SyncState s12: sDiff)
               {
                   List <Transition> allT = bad.getTransitions();
                   List <Transition> notFaultT=bad.getNotFaults();
                   for(Transition t1: allT)
                   {
                       for(Transition t2: notFaultT)
                       {
                           if(t1.getEvent().equals(t2.getEvent()) && t1.isObservable() && !t1.equals(t2))
                           {
                               SyncTransition t12 = new SyncTransition(t1,t2);
                               SyncState sAB = new SyncState(t1.getEnd(),t2.getEnd());
                               sDue.add(sAB);
                               tDue.add(t12);
                               if(t1.isFault())
                               {
                                   ta.add(t12);
                               }
                               
                           }
                       }
                   }
               }
           }
        }
        System.out.println();
        return new SyncAutoma(so, sDue, tDue, ta);
    }
    
}
