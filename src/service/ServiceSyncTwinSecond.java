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
import model.Automa;
import model.State;
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
    /**
     * 
     * @param x
     * @param ti
     * @param bad
     * @return 
     */
    public SyncAutoma getSyncTwin2(SyncAutoma x, Set <Transition> ti, Automa bad)
    {
        Set <SyncTransition> ta = x.getTransitions().stream().filter((t) -> (t.isAmbiguous())).collect(Collectors.toSet());
        Set <SyncTransition> tTwo = x.getTransitions();
        Set <SyncState> sTwo = new HashSet <> (x.getStates());
        Set <SyncState> sTemp = new HashSet <> (sTwo);
        Set <Transition> transitions = new HashSet <> (bad.getTransitions());
        
        for(SyncState sasb : x.getStates())
        {
            Set <SyncTransition> pairs =
                    ti
                    .stream()
                    .flatMap((t1) -> 
                            (ti.stream().map((t2) -> new SyncTransition(t1, t2))))
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
                    transitions
                    .stream()
                    .flatMap((t1) -> (
                            transitions.stream().map((t2) -> (
                                    new SyncTransition(t1, t2)))
                        )
                    )
                    .filter((t1t2) -> (
                            t1t2.getStart().equals(sasb)
                            && bad.getNotFaults().contains(t1t2.getT2())
                            && t1t2.getT1().getEvent().equals(t1t2.getT2().getEvent())
                            && t1t2.getT1().isObservable()
                        )
                    )
                    .collect(Collectors.toSet());
                    /*.filter((t) -> (
                            t.getT1().getStart().equals(sasb.getState1()) 
                            && t.getT2().getStart().equals(sasb.getState2()))
                            && t.getT1().getEvent().equals(t.getT2().getEvent()))
                    .collect(Collectors.toSet());*/
                
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
        
        return new SyncAutoma(x.getInitial(), sTwo, ta);
        
    }
    
    
    public SyncAutoma getSyncSD(SyncAutoma x, Set <Transition> ti, Automa cattivo){
        
        Set <SyncTransition> ta = new HashSet();
        Set <SyncTransition> tDue = new HashSet();
        Set <SyncState> sDue = new HashSet();
        Set <Transition> T = cattivo.getTransitions();
        Set <Transition> tNonF= new HashSet();
        Set <SyncState> sTemp = new HashSet();
        //SyncState so;
        ta.addAll(x.getAmbiguous());
        sDue.addAll(x.getStates());
        tDue.addAll(x.getTransitions());
        sTemp.addAll(sDue);
        
        for(Transition t1 : ti){//becco le transizioni di Ti non di fault
            
            if(!t1.isFault()){
                tNonF.add(t1);
            }
        }
        
        for(SyncState s : x.getStates()){
            
            State sa = s.getState1();
            State sb = s.getState2();
            
            for(Transition t1 : ti){
                
                for(Transition t2 : tNonF){
                    
                    if(t1.getEvent().equals(t2.getEvent()) && t1.isObservable() && t1.getStart().equals(sa) && t2.getStart().equals(sb)){
                        
                        SyncTransition t12 = new SyncTransition(t1,t2);
                        SyncState s12 = new SyncState(t1.getEnd(),t2.getEnd());
                        sDue.add(s12);
                        tDue.add(t12);
                        
                        if(t1.isFault())
                          {
                              ta.add(t12);
                          }
                    }
                }
            }
        }
        
        while(!sDue.equals(sTemp)){ // possibili problemi con equals sugli Hash
            
            Set <Transition> TnonF = new HashSet();
            Set <SyncState> sDiff = new HashSet();
            sDiff.addAll(sDue);
            sDiff.removeAll(sTemp);
            sTemp = new HashSet();
            sTemp.addAll(sDue);
            
            for(Transition t1 : T){//becco le transizioni di T non di fault
            
                if(!t1.isFault()){
                    TnonF.add(t1);
                }
            }
            
            for(SyncState s : sDiff){
                
                for(Transition t1 : T){
                    
                    for(Transition t2 : TnonF){
                        
                        if(t1.getEvent().equals(t2.getEvent()) && t1.isObservable() && t1.getStart().equals(s.getState1()) && t2.getStart().equals(s.getState2())){
                            
                            SyncTransition t12 = new SyncTransition(t1,t2);
                            tDue.add(t12);
                            SyncState s12 = new SyncState(t1.getEnd(), t2.getEnd());
                            sDue.add(s12);
                            if(t1.isFault()){
                                
                                ta.add(t12);
                                
                            }
                            
                        }
                        
                    }
                    
                }
                
            }
            
        }
        
        SyncAutoma y = new SyncAutoma(x.getInitial(), sDue, tDue);
        return y;
    }

}