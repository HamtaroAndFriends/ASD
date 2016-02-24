/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The definition of the {@link Transition} class.
 * 
 * @author Samuele Colombo
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Transition 
{
    /**
     * 
     */
    @XmlElement
    private State start;
    
    /**
     * 
     */
    @XmlElement
    private State end;
    
    /**
     * 
     */
    @XmlElement
    private Event event;
    
    /**
     * 
     */
    @XmlElement
    private boolean isFault;
    
    /**
     * 
     */
    @XmlElement
    private boolean isObservable;

    /**
     * 
     */
    public Transition() 
    {
        
    }
    
    /**
     * 
     * @param start
     * @param end
     * @param event 
     * @param isFault 
     * @param isObservable 
     */
    public Transition(State start, State end, Event event, boolean isFault, boolean isObservable)
    {
        this.start = start;
        this.end = end;
        this.event = event;
        this.isFault = isFault;
        this.isObservable = isObservable;
    }

    /**
     * Get the initial {@link State} of the transition.
     * @return 
     */
    public State getStart() 
    {
        return start;
    }

    /**
     * Set the initial {@link State} of the transition.
     * @param start 
     */
    public void setStart(State start) 
    {
        this.start = start;
    }

    /**
     * Get the final {@link State} of the transition.
     * @return 
     */
    public State getEnd() 
    {
        return end;
    }

    /**
     * Set the final {@link State} of the transition
     * @param end 
     */
    public void setEnd(State end) 
    {
        this.end = end;
    }

    /**
     * Get the {@link Event} that fires this transition.
     * @return 
     */
    public Event getEvent() 
    {
        return event;
    }

    /**
     * Set the {@link Event} that fires this transition.
     * @param event 
     */
    public void setEvent(Event event) 
    {
        this.event = event;
    }

    /**
     * 
     * @return 
     */
    public boolean isFault() 
    {
        return isFault;
    }

    /**
     * 
     * @return 
     */
    public boolean isObservable() 
    {
        return isObservable;
    }

    /**
     * 
     * @param isFault 
     */
    public void setFault(boolean isFault) 
    {
        this.isFault = isFault;
    }

    /**
     * 
     * @param isObservable 
     */
    public void setObservable(boolean isObservable) 
    {
        this.isObservable = isObservable;
    }
    
    
    
    
}