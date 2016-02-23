/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    private List <Transition> observables;
    
    /**
     * 
     */
    @XmlElementWrapper
    @XmlElement
    private List <Transition> faults;
    
    /**
     * 
     */
    public Automa()
    {
        this.states = new ArrayList <> ();
        this.observables = new ArrayList <> ();
    }

    /**
     * 
     * @param states
     * @param transitions 
     */
    public Automa(List<State> states, List<Transition> transitions)
    {
        this.states = states;
        this.observables = transitions;
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
    public List<Transition> getObservables() 
    {
        return observables;
    }

    /**
     * 
     * @param observables 
     */
    public void setObservables(List<Transition> observables) 
    {
        this.observables = observables;
    }

    /**
     * 
     * @return 
     */
    public List<Transition> getFaults() 
    {
        return faults;
    }

    /**
     * 
     * @param faults 
     */
    public void setFaults(List<Transition> faults) 
    {
        this.faults = faults;
    }

}
