/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.sync;

import java.util.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
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
     * 
     */
    @XmlElement
    private State state1;
    
    /**
     * 
     */
    @XmlElement
    private State state2;
    
    /**
     * This costructor is used from JAXB.
     */
    public SyncState() {}
    
    /**
     * 
     * @param state1
     * @param state2 
     */
    public SyncState(State state1, State state2)
    {
        this.state1 = state1;
        this.state2 = state2;
    }

    /**
     * 
     * @return 
     */
    public State getState1() 
    {
        return state1;
    }

    /**
     * 
     * @param state1 
     */
    public void setState1(State state1)
    {
        this.state1 = state1;
    }

    /**
     * 
     * @return 
     */
    public State getState2()
    {
        return state2;
    }

    /**
     * 
     * @param state2 
     */
    public void setState2(State state2) 
    {
        this.state2 = state2;
    }
    
    /**
     * 
     * @return 
     */
    public String getName()
    {
        return this.state1.getName() + this.state2.getName();
    }

    /**
     * 
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + 17 * (Objects.hashCode(this.state1) +  Objects.hashCode(this.state2));
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
        
        if (Objects.equals(this.state1, other.state1) && Objects.equals(this.state2, other.state2)) {
            return true;
        }
        if (Objects.equals(this.state2, other.state1) && Objects.equals(this.state1, other.state2)) {
            return true;
        }
        return false;
    }
    
    
}
