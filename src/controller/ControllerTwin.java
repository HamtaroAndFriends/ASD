/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import model.Automa;
import model.Event;
import model.State;
import model.Transition;

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
        // Get the list of all observable transitions
        List <Transition> to = automa.getObservables();
        // Get the list of all fault transitions
        List <Transition> tf = new ArrayList <> ();
        // Fault parameter
        boolean fault;

        // Foreach state of the automa
        for(State s : automa.getStates())
        {
            List <Transition> nob = automa.getNotObservables();
            // Foreach transition in the fault list that starts from s and reaches sd
            for(Transition t : automa.getNotObservables().stream().filter((p) -> (p.getStart().equals(s))).collect(Collectors.toList()))
            {
                // Set fault if the transition is a fault one
                fault = automa.getFaults().contains(t);

                // Define the list of the tuples
                List <List <Object>> tuples = find(automa, 1, t.getEnd(), fault, new Event());

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
        
        return new Automa(s1, ta);
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
    public List <List<Object>> find(Automa automa, int n, State s, boolean fault, Event ot)
    {
        // Define the list of the tuples
        List <List <Object>> tuples = new ArrayList <> ();
        // Define the variabile fault'
        boolean fault1;
        
        // Foreach transition that stars from state s
        for(Transition t : automa.getTransitions().stream().filter((t) -> (t.getStart().equals(s))).collect(Collectors.toList()))
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
                    List <List <Object>> result = find(automa, n - o.getEvents().size(), t.getEnd(), fault1, oot);
                    tuples.addAll(result);
                }
            }
            
            if(!automa.getObservables().contains(t))
            {
                List <List <Object>> result = find(automa, n, t.getEnd(), fault1, ot);
                tuples.addAll(result);
            }
        }
        
        return tuples;
    }
    

}
