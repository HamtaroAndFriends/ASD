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
 * The definition of the {@link State} class.
 * 
 * @author Samuele Colombo
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class State 
{
    /**
     * 
     */
    @XmlElement
    private String name;
    
    /**
     * 
     */
    @XmlElement
    private boolean isFinal;
    
    /**
     * 
     */
    @XmlElement
    private boolean isInitial;

    /**
     * 
     */
    public State() 
    {
        
    }

    /**
     * 
     * @param name 
     * @param isFinal 
     * @param isInitial 
     */
    public State(String name, boolean isFinal, boolean isInitial)    
    {
        this.name = name;
        this.isFinal = isFinal;
        this.isInitial = isInitial;
    }
    
    /**
     * 
     * @param name 
     */
    public State(String name)
    {
        this(name, false, false);
    }

    /**
     *
     * @return 
     */
    public String getName() {
        return name;
    }
    
    /**
     * 
     * @param name 
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return 
     */
    public boolean isIsFinal()
    {
        return isFinal;
    }

    /**
     * 
     * @return 
     */
    public boolean isIsInitial() 
    {
        return isInitial;
    }

    /**
     * 
     * @param isFinal 
     */
    public void setIsFinal(boolean isFinal) 
    {
        this.isFinal = isFinal;
    }

    /**
     * 
     * @param isInitial 
     */
    public void setIsInitial(boolean isInitial) 
    {
        this.isInitial = isInitial;
    }
}
