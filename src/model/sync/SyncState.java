/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.sync;

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
}
