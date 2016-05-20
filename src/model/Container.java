/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Samuele Colombo
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Container 
{
    /**
     * 
     */
    @XmlElement
    private Automa automa;
    
    /**
     * 
     */
    @XmlElementWrapper
    @XmlElement
    private Map <Integer, Automa> bads;
    
    /**
     * 
     */
    @XmlElementWrapper
    @XmlElement
    private Map <Integer, Automa> goods;

    /**
     * 
     */
    public Container() 
    {
        bads = new HashMap <> ();
        goods = new HashMap <> ();   
    }

    /**
     * 
     * @param automa
     * @param bads
     * @param goods 
     */
    public Container(Automa automa, Map<Integer, Automa> bads, Map<Integer, Automa> goods) 
    {
        this.automa = automa;
        this.bads = bads;
        this.goods = goods;
    }

    /**
     * 
     * @return 
     */
    public Automa getAutoma() {
        return automa;
    }

    /**
     * 
     * @param automa 
     */
    public void setAutoma(Automa automa) 
    {
        this.automa = automa;
    }

    /**
     * 
     * @return 
     */
    public Map<Integer, Automa> getBads() 
    {
        return bads;
    }
    
    public Automa getBad(int level)
    {
        return bads.getOrDefault(level, automa);
    }

    /**
     * 
     * @param bads 
     */
    public void setBads(Map<Integer, Automa> bads) 
    {
        this.bads = bads;
    }

    /**
     * 
     * @return 
     */
    public Map<Integer, Automa> getGoods() 
    {
        return goods;
    }

    /**
     * 
     * @param goods 
     */
    public void setGoods(Map<Integer, Automa> goods) 
    {
        this.goods = goods;
    }
}
