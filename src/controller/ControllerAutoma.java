/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.List;
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
        List<State> statiAutoma= daControllare.getStates();
        List<Transition> transizioniOsservabili= daControllare.getObservables();
        List<Transition> transizioniGuasto= daControllare.getFaults();
        List<Transition> transizioniNonOsservabili= daControllare.getNotObservables();
        List<Transition> transizioni=new ArrayList<>();
        if(transizioniGuasto.size()!=0){
            transizioni.addAll(transizioniGuasto);
        }
        if(transizioniOsservabili.size()!=0){
            transizioni.addAll(transizioniOsservabili);
        }
        if(transizioniNonOsservabili.size()!=0){
            transizioni.addAll(transizioniNonOsservabili);
        }
        List<State> statiInizialiTransizioni=statiTransizioni(transizioni,0);
        List<State> statiFinaliTransizioni=statiTransizioni(transizioni,1);
        List<State> statiInizialiOsservabili=statiTransizioni(transizioniOsservabili,0);
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
    
    public void stampa(List l){
        for(int i=0;i<l.size();i++){
            System.out.println(l.get(i));
        }
    }
    
    public List<State> statiTransizioni(List<Transition> t,int iF){// se iF=0 ritorna stati inziiali, se iF=1 ritorna stati finali
        List<State> stati=new ArrayList<>();
        for(int i=0;i<t.size();i++){
            boolean doppio=false;
            State s;
            if(iF==0){
                s=t.get(i).getStart();
            }
            else{
                s=t.get(i).getEnd();
            }
            for(int j=0;j<stati.size();j++){
                
                if(s.getName().equals(stati.get(j).getName())){ //se ho giÃ  inserito uno stato con lo stesso nome
                    doppio=true;
                }
            }
            if(!doppio){
                stati.add(s);
            }
        }
        return stati;
    }
    
    public boolean confrontoStatiIniziali(List<State>s1,List<State>s2){
        boolean vivo=false;
        int contatore=0;
        for(int i=0;i<s1.size();i++){
            State stato=s1.get(i);
           if(s2.contains(stato)){
               contatore++;
           }
            
        }
        if(contatore==s1.size()){
            vivo=true;
        }
        return vivo;
    }
    
    public boolean raggiungoOsservabile(List<Transition> guasti,List<State> sIO){ //sIO=stati iniziali di transizioni osservabili
        boolean vivo=true;
        for(int i=0;i<guasti.size();i++){//prendo una alla volta transizioni di guasto
            if(vivo){
                boolean transizioneOK=false;
                Transition t=guasti.get(i);
                List<State> visitati=new ArrayList<>();
                visitati.add(t.getEnd());
                do{
                    List<State> nuoviStatiDaAggiungere=new ArrayList<>();
                    for(int j=0;j<visitati.size();j++){ //stati raggiunti a partire dalla transazione selezionata prima
                        if(sIO.contains(visitati.get(j))){
                            transizioneOK=true;                    
                        }
                        else{
                            for(int k=0;k<guasti.size();k++){ // faccio passare le transizioni che hanno visitati.get(j) come stato di partenza 
                                if(visitati.get(j).getName().equals(guasti.get(k).getStart().getName())){
                                    State nuovo=guasti.get(k).getEnd();
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
