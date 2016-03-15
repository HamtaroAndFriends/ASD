/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.sync;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
    private Supplier <List <SyncTransition>> ambiguous;
            
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
    public SyncAutoma(SyncState initial, List <SyncState> states, List <SyncTransition> transitions)
    {
        this.initial = initial;
        this.states = states;
        this.transitions = transitions;
        this.ambiguous = Suppliers.memoize(() -> transitions.stream().filter((t) -> (t.isAmbiguous())).collect(Collectors.toList()));
    }
    
    /**
     * 
     * @param initial
     * @param states
     * @param transitions 
     * @param ambiguous 
     * @deprecated 
     */
    public SyncAutoma(SyncState initial, List <SyncState> states, List <SyncTransition> transitions, List <SyncTransition> ambiguous)
    {
        this.initial = initial;
        this.states = states;
        this.transitions = transitions;
        this.ambiguous = Suppliers.memoize(() -> ambiguous));
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
    public List<SyncTransition> getAmbiguous() 
    {
        return ambiguous.get();
    }

    /**
     * 
     * @param ambiguous 
     * @deprecated
     */
    public void setAmbiguous(List<SyncTransition> ambiguous) 
    {
        this.ambiguous = ambiguous;
    }
    
    
    
    
}
