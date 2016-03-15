/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * The definition of the {@link Automa} class.
 * 
 * @author Samuele Colombo
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Automa 
{
    /**
     * 
     */
    @XmlElement
    private State initial;
    
    /**
     * 
     */
    @XmlElementWrapper
    @XmlElement
    private Set <State> states; 
    
    /**
     * 
     */
    @XmlElementWrapper
    @XmlElement
    private Set <Transition> transitions;
    
    /**
     * 
     */
    @XmlTransient
    private ConcurrentHashMap <State, Set <Transition>> stateTransitions;
    
    /**
     * 
     */
    @XmlTransient
    private Supplier <Set <Transition>> observables;
    
    /**
     * 
     */
    @XmlTransient
    private Supplier <Set <Transition>> faults;
   
    
    /**
     * 
     */
    public Automa()
    {
        this.initial = new State();
        this.states = new HashSet <> ();
        this.transitions = new HashSet <> ();
        
        this.observables = Suppliers.memoize(() -> transitions.stream().filter((t) -> (t.isObservable() == true)).collect(Collectors.toSet()));
        this.faults = Suppliers.memoize(() -> transitions.stream().filter((t) -> (t.isFault() == true)).collect(Collectors.toSet()));
        this.stateTransitions = new ConcurrentHashMap <> ();
    }

    /**
     * 
     * @param initial
     * @param states
     * @param transitions 
     */
    public Automa(State initial, Set<State> states, Set<Transition> transitions)
    {
        this();
        this.initial = initial;
        this.states = states;
        this.transitions = transitions;
    }

    /**
     * This method returns the initial state of the automa.
     * @return 
     */
    public State getInitial() 
    {
        return initial;
    }
    
    /**
     * This method sets the initial state of the automa.
     * @param initial 
     */
    public void setInitial(State initial)
    {
        this.initial = initial;
        this.initial.setFinal(false);
        this.initial.setInitial(true);
    }
    
    /**
     * This method gets all the states of the automa.
     * @return 
     */
    public Set<State> getStates() 
    {
        return states;
    }

    /**
     * This method sets all the states of the automa.
     * @param states 
     */
    public void setStates(Set<State> states) 
    {
        this.states = states;
    }
    
    /**
     * This method gets all the transitions of the automa.
     * @return 
     */
    public Set<Transition> getTransitions() 
    {
        return transitions;
    }
    
    /**
     * This method gets all the transitions of the automa that start from input state.
     * @param start
     * @return 
     */
    public Set<Transition> getTransitions(State start)
    {
        return stateTransitions.computeIfAbsent(start, (s) -> (transitions.stream().filter((t) -> (t.getStart().equals(start))).collect(Collectors.toSet())));
    }

    /**
     * This method sets all the transitions of the automa.
     * @param transitions 
     */
    public void setTransitions(Set<Transition> transitions) 
    {
        this.transitions = transitions;
    }
    
    /**
     * This method gets all the observable transitions of the automa.
     * This method uses a caching system provided by {@link Supplier} class.
     * 
     * @return 
     */
    public Set<Transition> getObservables()
    {
        return observables.get();
    }
    
    /**
     * This method gets all the not observable transitions of the automa.
     * 
     * @return 
     */
    public Set<Transition> getNotObservables()
    {
        return transitions.stream().filter((t) -> (t.isObservable() == false)).collect(Collectors.toSet());
    }
    
    /**
     * This method gets all the fault transitions of the automa.
     * This method uses a caching system provided by {@link Supplier} class. 
     * @return 
     */
    public Set<Transition> getFaults()
    {
        return faults.get();
    }
    
    /**
     * This method gets all the not fault transitions of the automa.
     * @return 
     */
    public Set<Transition> getNotFaults()
    {
        return transitions.stream().filter((t) -> (t.isFault() == false)).collect(Collectors.toSet());
    }
   
}
