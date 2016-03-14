/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.sync;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Samuele Colombo
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SyncAutoma 
{
    /**
     * 
     */
    @XmlElement
    private SyncState initial;
    
    /**
     * 
     */
    @XmlElement
    private List <SyncState> states;
    
    /**
     * 
     */
    @XmlElement
    private List <SyncTransition> transitions;
    
    /**
     * 
     */
    @XmlElement
    private List <SyncTransition> ambiguous;
            
    /**
     * 
     */
    public SyncAutoma()
    {
        this.states = new ArrayList <> ();
        this.transitions = new ArrayList <> ();
        this.ambiguous = new ArrayList <> ();
    }
    
    /**
     * 
     * @param initial
     * @param states
     * @param transitions 
     */
    public SyncAutoma(SyncState initial, List <SyncState> states, List <SyncTransition> transitions, List <SyncTransition> ambiguous)
    {
        this.initial = initial;
        this.states = states;
        this.transitions = transitions;
        this.ambiguous = ambiguous;
    }

    /**
     * 
     * @return 
     */
    public SyncState getInitial() 
    {
        return initial;
    }

    /**
     * 
     * @param initial 
     */
    public void setInitial(SyncState initial) 
    {
        this.initial = initial;
    }

    /**
     * 
     * @return 
     */
    public List<SyncState> getStates()
    {
        return states;
    }

    /**
     * 
     * @param states 
     */
    public void setStates(List<SyncState> states) 
    {
        this.states = states;
    }

    /**
     * 
     * @return 
     */
    public List<SyncTransition> getTransitions()
    {
        return transitions;
    }

    /**
     * 
     * @param transitions 
     */
    public void setTransitions(List<SyncTransition> transitions) 
    {
        this.transitions = transitions;
    }

    /**
     * 
     * @return 
     */
    public List<SyncTransition> getAmbiguous() {
        return ambiguous;
    }

    /**
     * 
     * @param ambiguous 
     */
    public void setAmbiguous(List<SyncTransition> ambiguous) {
        this.ambiguous = ambiguous;
    }
    
    
    
    
}
