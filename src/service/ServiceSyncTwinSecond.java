/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.HashSet;
import java.util.Set;
import java.util.Set;
import java.util.stream.Collectors;
import model.Transition;
import model.sync.SyncAutoma;
import model.sync.SyncState;
import model.sync.SyncTransition;

/**
 *
 * @author Samuele Colombo
 */
public class ServiceSyncTwinSecond 
{
    public SyncAutoma getSyncTwin2(SyncAutoma x, Set <Transition> ti)
    {
        Set <SyncTransition> ta = x.getTransitions().stream().filter((t) -> (t.isAmbiguous())).collect(Collectors.toSet());
        Set <SyncTransition> tTwo = x.getTransitions();
        Set <SyncState> sTwo = x.getStates();
        Set <SyncState> sTemp = new HashSet <> (sTwo);
        
        for(SyncState sasb : x.getStates())
        {
            Set <SyncTransition> pairs =
                    ti
                    .stream()
                    .flatMap((t1) -> (ti.stream().map((t2) -> new SyncTransition(t1, t2))))
                    .collect(Collectors.toSet())
                    .stream()
                    .filter((t) -> (
                            t.getT1().getStart().equals(sasb.getState1()) 
                            && t.getT2().getStart().equals(sasb.getState2()))
                            && t.getT1().getEvent().equals(t.getT2().getEvent()))
                    .collect(Collectors.toSet());
            
            for(SyncTransition t1t2 : pairs)
            {
                sTwo.add(t1t2.getEnd());
                tTwo.add(t1t2);
                
                if(t1t2.getT1().isFault()) // Weird
                {
                    ta.add(t1t2);
                }
            }
        }
        
        while(!sTemp.equals(sTwo))
        {
            Set <SyncState> sTempFinal = sTemp;
            Set <SyncState> diff = sTwo.stream().filter((s) -> (sTempFinal.contains(s))).collect(Collectors.toSet());
            sTemp = sTwo;
            
            for(SyncState sasb : diff)
            {
                Set <SyncTransition> pairs =
                    ti
                    .stream()
                    .flatMap((t1) -> (ti.stream().map((t2) -> new SyncTransition(t1, t2))))
                    .collect(Collectors.toSet())
                    .stream()
                    .filter((t) -> (
                            t.getT1().getStart().equals(sasb.getState1()) 
                            && t.getT2().getStart().equals(sasb.getState2()))
                            && t.getT1().getEvent().equals(t.getT2().getEvent()))
                    .collect(Collectors.toSet());
                
                for(SyncTransition t1t2 : pairs)
                {
                    sTwo.add(t1t2.getEnd());
                    tTwo.add(t1t2);

                    if(t1t2.getT1().isFault()) // Weird
                    {
                        ta.add(t1t2);
                    }
                }
            }
        }
        
        return new SyncAutoma(x.getInitial(), sTwo, tTwo);
        
    }
}
