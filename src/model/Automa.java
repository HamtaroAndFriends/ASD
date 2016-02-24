/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    @XmlElementWrapper
    @XmlElement
    private List <State> states; 
    
    /**
     * 
     */
    @XmlElementWrapper
    @XmlElement
    private List <Transition> transitions;
    
    /**
     * 
     */
    @XmlTransient
    private Supplier <Map <State, List <Transition>>> stateTransitions;
    
    /**
     * 
     */
    @XmlTransient
    private Supplier <List <Transition>> observables;
    
    /**
     * 
     */
    @XmlTransient
    private Supplier <List <Transition>> faults;
    
    /**
     * 
     */
    @XmlTransient
    private Supplier <List <Transition>> ambiguous;
    
    /**
     * 
     */
    public Automa()
    {
        this.states = new ArrayList <> ();
        this.transitions = new ArrayList <> ();
        
        this.observables = Suppliers.memoize(() -> transitions.stream().filter((t) -> (t.isObservable() == true)).collect(Collectors.toList()));
        this.faults = Suppliers.memoize(() -> transitions.stream().filter((t) -> (t.isFault() == true)).collect(Collectors.toList()));
        this.ambiguous = Suppliers.memoize(() -> transitions.stream().filter((t) -> (t.isAmbiguous() == true)).collect(Collectors.toList()));
        this.stateTransitions = Suppliers.memoize(() -> (transitions.stream().collect(Collectors.groupingBy(Transition::getStart))));

    }

    /**
     * 
     * @param states
     * @param transitions 
     */
    public Automa(List<State> states, List<Transition> transitions)
    {
        this();
        this.states = states;
        this.transitions = transitions;
    }

    /**
     * This method gets all the states of the automa.
     * @return 
     */
    public List<State> getStates() 
    {
        return states;
    }

    /**
     * This method sets all the states of the automa.
     * @param states 
     */
    public void setStates(List<State> states) 
    {
        this.states = states;
    }
    
    /**
     * This method gets all the transitions of the automa.
     * @return 
     */
    public List<Transition> getTransitions() 
    {
        return transitions;
    }
    
    /**
     * This method gets all the transitions of the automa that start from input state.
     * @param start
     * @return 
     */
    public List<Transition> getTransitions(State start)
    {
        return stateTransitions.get().getOrDefault(start, new ArrayList <> ());
    }

    /**
     * This method sets all the transitions of the automa.
     * @param transitions 
     */
    public void setTransitions(List<Transition> transitions) 
    {
        this.transitions = transitions;
    }
    
    /**
     * This method gets all the observable transitions of the automa.
     * This method uses a caching system provided by {@link Supplier} class.
     * 
     * @return 
     */
    public List<Transition> getObservables()
    {
        return observables.get();
    }
    
    /**
     * This method gets all the not observable transitions of the automa.
     * 
     * @return 
     */
    public List<Transition> getNotObservables()
    {
        return transitions.stream().filter((t) -> (t.isObservable() == false)).collect(Collectors.toList());
    }
    
    /**
     * This method gets all the fault transitions of the automa.
     * This method uses a caching system provided by {@link Supplier} class. 
     * @return 
     */
    public List<Transition> getFaults()
    {
        return faults.get();
    }
    
    /**
     * This method gets all the not fault transitions of the automa.
     * @return 
     */
    public List<Transition> getNotFaults()
    {
        return transitions.stream().filter((t) -> (t.isFault() == false)).collect(Collectors.toList());
    }
    
    /**
     * This method gets all the ambiguous transitions of the automa.
     * This method uses a caching system provided by {@link Supplier} class. 
     * @return 
     */
    public List<Transition> getAmbiguous()
    {
        return ambiguous.get();
    }
    
    /**
     * This method gets all the not ambiguous transitions of the automa.
     * @return 
     */
    public List<Transition> getNotAmbiguous()
    {
        return transitions.stream().filter((t) -> (t.isAmbiguous() == false)).collect(Collectors.toList());
    }
    
    /**
     * This method returns the initial state of the automa.
     * @return 
     */
    public State getInitialState()
    {
        return states.stream().filter((s) -> (s.isInitial())).findFirst().get();
    }

}
