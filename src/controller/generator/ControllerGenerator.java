/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.generator;

import com.google.common.collect.Sets;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import model.Automa;
import model.Event;
import model.State;
import model.Transition;

/**
 *
 * @author Samuele Colombo
 */
public class ControllerGenerator 
{
    /**
     * 
     * @param numberStates
     * @param numberEvents
     * @param numberTransitions
     * @param faultRate
     * @param observableRate
     * @return 
     */
    public Automa generateAutoma(int numberStates, int numberEvents, int numberTransitions, double faultRate, double observableRate)
    {
        Random rand = new Random();
        
        List <State> states = generateStates(numberStates);
        List <Event> events = generateEvents(numberEvents);
        List <Transition> transitions = generateTransitions(states, events, numberTransitions, faultRate, observableRate);
        State s0 = states.get(rand.nextInt(states.size()));
        s0.setInitial(true);
        
        return new Automa(s0, Sets.newHashSet(states), Sets.newHashSet(transitions));
    }
    
    /**
     * 
     * @param number
     * @return 
     */
    public List <State> generateStates(int number)
    {
        return IntStream
                .range(1, number)
                .mapToObj((i) -> new State("s" + i))
                .collect(Collectors.toList());
    }
    
    /**
     * 
     * @param states
     * @param events
     * @param number
     * @param faultRate
     * @param observableRate
     * @return 
     */
    public List <Transition> generateTransitions(List <State> states, List <Event> events, int number, double faultRate, double observableRate)
    {
        Random rand = new Random();
        return IntStream
                .range(1, number)
                .mapToObj((i) -> new Transition(
                        states.get(rand.nextInt(states.size())), 
                        states.get(rand.nextInt(states.size())), 
                        events.get(rand.nextInt(events.size())), 
                        rand.nextDouble() > faultRate, 
                        rand.nextDouble() < observableRate)
                    )
                .collect(Collectors.toList());
    }
    
    /**
     * 
     * @param number
     * @return 
     */
    public List <Event> generateEvents(int number)
    {
        Random rand = new Random();
        
        return IntStream
                .range(1, number)
                .mapToObj((i) -> new Event("e" + i))
                .collect(Collectors.toList());
    }
}
