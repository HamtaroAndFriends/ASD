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
import model.Transition;

/**
 *
 * @author Fede
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SyncTransition
{
    /**
     * 
     */
    @XmlElement
    private Transition t1;
    
    /**
     * 
     */
    @XmlElement
    private Transition t2;

    /**
     * This costructor is used from JAXB.
     */
    public SyncTransition() { }
    
    /**
     * 
     * @param t1
     * @param t2 
     */
    public SyncTransition(Transition t1, Transition t2)
    {
        this.t1 = t1;
        this.t2 = t2;
    }
    
    /**
     * 
     * @return 
     */
    public Transition getT1() 
    {
        return t1;
    }

    /**
     * 
     * @param t1 
     */
    public void setT1(Transition t1) 
    {
        this.t1 = t1;
    }
    
    /**
     * 
     * @return 
     */
    public SyncState getStart()
    {
        return new SyncState(t1.getStart(), t2.getStart());
    }
    
    /**
     * 
     * @return 
     */
    public Transition getT2() 
    {
        return t2;
    }

    /**
     * 
     * @param t2 
     */
    public void setT2(Transition t2)
    {
        this.t2 = t2;
    }
    
    /**
     * 
     * @return 
     */
    public SyncState getEnd()
    {
        return new SyncState(t1.getEnd(), t2.getEnd());
    }
        
    /**
     * 
     * @return 
     */
    @XmlElement
    public boolean isAmbiguous()
    {
        return (t1.isFault() && !t2.isFault()) || (!t1.isFault() && t2.isFault());
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 67 * hash + 17 * (Objects.hashCode(this.t1) * Objects.hashCode(this.t2));
        return hash;
    }

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
        
        final SyncTransition other = (SyncTransition) obj;
        
        if (!Objects.equals(this.t1, other.t1)) {
            return false;
        }
        if (!Objects.equals(this.t2, other.t2)) {
            return false;
        }
        return true;
    }
  
}
