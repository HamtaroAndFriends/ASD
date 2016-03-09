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
import model.Transition;

/**
 *
 * @author Fede
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SyncTransition
{
    @XmlElement
    private Transition t1;
    @XmlElement
    private Transition t2;
    
    public SyncTransition(Transition t1, Transition t2)
    {
        this.t1 = t1;
        this.t2 = t2;
    }

    public Transition getT1() 
    {
        return t1;
    }

    public void setT1(Transition t1) 
    {
        this.t1 = t1;
    }

    public Transition getT2() 
    {
        return t2;
    }

    public void setT2(Transition t2)
    {
        this.t2 = t2;
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
    
    
}
