/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.HashSet;
import java.util.Set;
import model.Automa;
import model.State;
import model.Transition;

/**
 *
 * @author Andrea
 */
public class ControllerAutoma {
    
    public boolean isVivo(Automa daControllare){
        boolean vivo=false;
        Set<State> statiAutoma= daControllare.getStates();
        Set<Transition> transizioniOsservabili= daControllare.getObservables();
        Set<Transition> transizioniGuasto= daControllare.getFaults();
        Set<Transition> transizioniNonOsservabili= daControllare.getNotObservables();
        Set<Transition> transizioni=new HashSet<>();
        if(transizioniGuasto.size()!=0){
            transizioni.addAll(transizioniGuasto);
        }
        if(transizioniOsservabili.size()!=0){
            transizioni.addAll(transizioniOsservabili);
        }
        if(transizioniNonOsservabili.size()!=0){
            transizioni.addAll(transizioniNonOsservabili);
        }
        Set<State> statiInizialiTransizioni=statiTransizioni(transizioni,0);
        Set<State> statiFinaliTransizioni=statiTransizioni(transizioni,1);
        Set<State> statiInizialiOsservabili=statiTransizioni(transizioniOsservabili,0);
        /*System.out.println("\nstatiautoma\n");
        stampa(statiAutoma);
        System.out.println("\ntransizioniGiuste\n");
        stampa(transizioniOsservabili);
        System.out.println("\ntransizioniGuasto\n");
        stampa(transizioniGuasto);
        System.out.println("\ntransizioni\n");
        stampa(transizioni);
        System.out.println("\nstatiiniziali\n");
        stampa(statiInizialiTransizioni);
        System.out.println("\nfinali\n");
        stampa(statiFinaliTransizioni);
        System.out.println("\nosservabili\n");
        stampa(statiInizialiOsservabili);*/
        vivo=confrontoStatiIniziali(statiAutoma,statiInizialiTransizioni);
        if(vivo){
            vivo=raggiungoOsservabile(transizioniGuasto,statiInizialiOsservabili);
        }
        return vivo;
    }
    
    public void stampa(Set l){
        for(Object i : l){
            System.out.println(i.toString());
        }
    }
    
    public Set<State> statiTransizioni(Set<Transition> t,int iF){// se iF=0 ritorna stati inziiali, se iF=1 ritorna stati finali
        Set<State> stati=new HashSet<>();
        for(Transition i : t){
            boolean doppio=false;
            State s;
            if(iF==0){
                s=i.getStart();
            }
            else{
                s=i.getEnd();
            }
            for(State j : stati){
                
                if(s.getName().equals(j.getName())){ //se ho giÃ  inserito uno stato con lo stesso nome
                    doppio=true;
                }
            }
            if(!doppio){
                stati.add(s);
            }
        }
        return stati;
    }
    
    public boolean confrontoStatiIniziali(Set<State>s1,Set<State>s2){
        boolean vivo=false;
        int contatore=0;
        for(State i : s1){
            State stato=i;
           if(s2.contains(stato)){
               contatore++;
           }
            
        }
        if(contatore==s1.size()){
            vivo=true;
        }
        return vivo;
    }
    
    public boolean raggiungoOsservabile(Set<Transition> guasti,Set<State> sIO){ //sIO=stati iniziali di transizioni osservabili
        boolean vivo=true;
        for(Transition i : guasti){//prendo una alla volta transizioni di guasto
            if(vivo){
                boolean transizioneOK=false;
                Transition t=i;
                Set<State> visitati=new HashSet<>();
                visitati.add(t.getEnd());
                do{
                    Set<State> nuoviStatiDaAggiungere=new HashSet<>();
                    for(State j : visitati){ //stati raggiunti a partire dalla transazione selezionata prima
                        if(sIO.contains(j)){
                            transizioneOK=true;                    
                        }
                        else{
                            for(Transition k : guasti){ // faccio passare le transizioni che hanno visitati.get(j) come stato di partenza 
                                if(j.getName().equals(k.getStart().getName())){
                                    State nuovo=k.getEnd();
                                    if(!visitati.contains(nuovo)){
                                        nuoviStatiDaAggiungere.add(nuovo);
                                    }
                                }
                            }
                        }
                    }
                    if(nuoviStatiDaAggiungere.size()==0 && !transizioneOK){
                        vivo=false;

                    }
                    else if(nuoviStatiDaAggiungere.size()!=0 && !transizioneOK){
                        visitati.addAll(nuoviStatiDaAggiungere);
                    }
                }
                while(!transizioneOK && vivo);
            }
        }
        return vivo;
    }
    
}
