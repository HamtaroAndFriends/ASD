    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Objects;
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
    public boolean isFinal()
    {
        return isFinal;
    }

    /**
     * 
     * @return 
     */
    public boolean isInitial() 
    {
        return isInitial;
    }

    /**
     * 
     * @param isFinal 
     */
    public void setFinal(boolean isFinal) 
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
    
    /**
     * 
     * @return 
     */
    @Override
    public int hashCode() 
    {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.name);
        return hash;
    }

    /**
     * 
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj) 
    {
        if (this == obj) 
        {
            return true;
        }
        
        if (obj == null) 
        {
            return false;
        }
        
        if (getClass() != obj.getClass()) 
        {
            return false;
        }
        
        final State other = (State) obj;
        
        if (!Objects.equals(this.name, other.name))
        {
            return false;
        }
        
        return true;
    }


    
    
}
