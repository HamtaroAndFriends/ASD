/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.method;

import controller.ControllerTwin;
import java.util.List;
import model.Automa;
import model.Container;
import model.sync.SyncTransition;

/**
 *
 * @author Samuele Colombo
 */
public class ControllerFirst 
{
    private ControllerTwin controllerTwin;
    
    /**
     * 
     */
    public ControllerFirst()
    {
        this.controllerTwin = new ControllerTwin();
    }
    
    /**
     * 
     * @param container 
     */
    public void performFirstMethod(Container container)
    {
        int i = 1;
        int l = 0;
        
        
        while(i < l)
        {
            // Retrieve the bad twin of level i-1 (if i-1 is equal to zero, then perform the bad twin
            Automa prevBad = container.getBads().computeIfAbsent(i - 1, (a) -> (controllerTwin.getBadTwin(container.getAutoma(), i - 1)));
            // Retrieve or generate the bad twin of level i
            Automa nextBad = container.getBads().computeIfAbsent(i, (a) -> (controllerTwin.getBadTwin(prevBad, i)));
            // Retrieve or generate the good twin of level i
            Automa nextGood = container.getGoods().computeIfAbsent(i, (a) -> (controllerTwin.getGoodTwin(nextBad)));
            // Syncrhonized the twins
            List <SyncTransition> syncTransitions = controllerTwin.getSyncTwin(nextBad, nextGood);
            
        }
    }
}
