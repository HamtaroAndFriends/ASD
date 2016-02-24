/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

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
    public Automa()
    {
        this.states = new ArrayList <> ();
        this.transitions = new ArrayList <> ();
    }

    /**
     * 
     * @param states
     * @param transitions 
     */
    public Automa(List<State> states, List<Transition> transitions)
    {
        this.states = states;
        this.transitions = transitions;
    }

    /**
     * 
     * @return 
     */
    public List<State> getStates() 
    {
        return states;
    }

    /**
     * 
     * @param states 
     */
    public void setStates(List<State> states) 
    {
        this.states = states;
    }
    
    /**
     * 
     * @return 
     */
    public List<Transition> getTransitions() 
    {
        return transitions;
    }

    /**
     * 
     * @param transitions 
     */
    public void setTransitions(List<Transition> transitions) 
    {
        this.transitions = transitions;
    }
    
    /**
     * 
     * @return 
     */
    public List<Transition> getObservables()
    {
        return transitions.stream().filter((t) -> (t.isObservable() == true)).collect(Collectors.toList());
    }
    
    /**
     * 
     * @return 
     */
    public List<Transition> getNotObservables()
    {
        return transitions.stream().filter((t) -> (t.isObservable() == false)).collect(Collectors.toList());
    }
    
    /**
     * 
     * @return 
     */
    public List<Transition> getFaults()
    {
        return transitions.stream().filter((t) -> (t.isFault() == true)).collect(Collectors.toList());
    }
    
    /**
     * 
     * @return 
     */
    public List<Transition> getNotFaults()
    {
        return transitions.stream().filter((t) -> (t.isFault() == false)).collect(Collectors.toList());
    }
    
    /**
     * 
     * @return 
     */
    public State getInitialState()
    {
        return states.stream().filter((s) -> (s.isInitial())).findFirst().get();
    }

}
