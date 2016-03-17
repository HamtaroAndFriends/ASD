/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.sync;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

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
    private Set <SyncState> states;
    
    /**
     * 
     */
    @XmlElement
    private Set <SyncTransition> transitions;
    
    /**
     * 
     */
    @XmlTransient
    private Supplier <Set <SyncTransition>> ambiguous;
            
    /**
     * 
     */
    public SyncAutoma()
    {
        this.states = new HashSet <> ();
        this.transitions = new HashSet <> ();
        this.ambiguous =  Suppliers.memoize(() -> this.transitions.stream().filter((t) -> (t.isAmbiguous())).collect(Collectors.toSet()));
    }
    
    /**
     * 
     * @param initial
     * @param states
     * @param transitions 
     */
    public SyncAutoma(SyncState initial, Set <SyncState> states, Set <SyncTransition> transitions)
    {
        this.initial = initial;
        this.states = states;
        this.transitions = transitions;
        this.ambiguous = Suppliers.memoize(() -> this.transitions.stream().filter((t) -> (t.isAmbiguous())).collect(Collectors.toSet()));
    }
    
    /**
     * 
     * @param initial
     * @param states
     * @param transitions 
     * @param ambiguous 
     * @deprecated 
     */
    public SyncAutoma(SyncState initial, Set <SyncState> states, Set <SyncTransition> transitions, Set <SyncTransition> ambiguous)
    {
        this.initial = initial;
        this.states = states;
        this.transitions = transitions;
        this.ambiguous = Suppliers.memoize(() -> this.transitions.stream().filter((t) -> (t.isAmbiguous())).collect(Collectors.toSet()));
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
    public Set<SyncState> getStates()
    {
        return states;
    }

    /**
     * 
     * @param states 
     */
    public void setStates(Set<SyncState> states) 
    {
        this.states = states;
    }

    /**
     * 
     * @return 
     */
    public Set<SyncTransition> getTransitions()
    {
        return transitions;
    }

    /**
     * 
     * @param transitions 
     */
    public void setTransitions(Set<SyncTransition> transitions) 
    {
        this.transitions = transitions;
    }

    /**
     * 
     * @return 
     */
    public Set<SyncTransition> getAmbiguous() 
    {
        return ambiguous.get();
    }

    /**
     * 
     * @param ambiguous 
     * @deprecated
     */
    public void setAmbiguous(Set<SyncTransition> ambiguous) 
    {
        //this.ambiguous = ambiguous;
    }
  
}
