/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.sync;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import model.State;

/**
 *
 * @author Fede
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SyncState
{
    /**
     * @deprecated
     */
    @XmlTransient
    private State state1;
    
    /**
     * @deprecated
     */
    @XmlTransient
    private State state2;
    
    /**
     * 
     */
    @XmlElement
    private State [] state;
    
    /**
     * This costructor is used from JAXB.
     */
    public SyncState() 
    {
        state = new State[2];
    }
    
    /**
     * 
     * @param state1
     * @param state2 
     */
    public SyncState(State state1, State state2)
    {
        this();
        this.state[0] = state1;
        this.state[1] = state2;
    }

    /**
     * 
     * @return 
     */
    public State getState1() 
    {
        return state[0];
    }

    /**
     * 
     * @param state1 
     */
    public void setState1(State state1)
    {
        this.state[0] = state1;
    }

    /**
     * 
     * @return 
     */
    public State getState2()
    {
        return state[1];
    }

    /**
     * 
     * @param state2 
     */
    public void setState2(State state2) 
    {
        this.state[1] = state2;
    }
    
    /**
     * 
     * @return 
     */
    public String getName()
    {
        return this.state[0].getName() + this.state[1].getName();
    }

    /**
     * 
     * @return 
     */
    @Override
    public int hashCode() 
    {
        int hash = 7;
        Arrays.sort(this.state, (State s1, State s2) -> s1.getName().compareTo(s2.getName()));
        hash = 19 * hash + Arrays.deepHashCode(this.state);
        return hash;
    }

    /**
     * 
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SyncState other = (SyncState) obj;
        if (!Arrays.deepEquals(this.state, other.state)) {
            return false;
        }
        return true;
    }

    
    
    
}
