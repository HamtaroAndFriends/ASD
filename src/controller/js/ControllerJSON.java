/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.js;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
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
public class ControllerJSON 
{
    private final static String OPEN_STATE = "\"nodes\" : ";
    private final static String OPEN_TRANSITION = "\"edges\" : ";
    private final static String OPEN_SQUARE= "[";
    private final static String CLOSE_SQUARE = "]";
    private final static String OPEN_BRACKET= "{";
    private final static String CLOSE_BRACKET = "}";
    private final static String COMMA = ",";
    private final static String DOT = ".";
    private final static String JSON_STATE = " \"%s\" ";
    private final static String JSON_TRANSITION = "[\"%s\" , \"%s\", { \"label\": \"%s\" , \"color\": \"%s\"}]\n";
    private final static String JSON_LOOP = "%s.";
    private final static String JSON_CODE = "<!DOCTYPE html>\n" +
        "<html>\n" +
        "    <head>\n" +
        "        <meta charset=\"UTF-8\">\n" +
        "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
        "        <script src=\"http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js\"></script>\n" +
        "        <script src=\"../../../src/view/js/springy.js\" type=\"text/javascript\"></script>\n" +
        "        <script src=\"../../../src/view/js/springyui.js\" type=\"text/javascript\"></script>\n" +
        "    </head>\n" +
        "    <body>\n" +
        "        <script>\n" +
        "            \n" +
        "            var graphJSON = %s;\n" +
        "            jQuery(function () {\n" +
        "                var graph = new Springy.Graph();\n" +
        "                graph.loadJSON(graphJSON);\n" +
        "                var springy = jQuery('#springydemo').springy({\n" +
        "                    graph: graph\n" +
        "                });\n" +
        "            });\n" +
        "        </script>\n" +
        "        <canvas id=\"springydemo\" width=\"640\" height=\"480\"></canvas>\n" +
        "    </body>\n" +
        "</html>\n";
    
    /**
     * 
     * @param automa
     * @return 
     */
    public String getStateJSON(Automa automa)
    {
        StringBuilder json = new StringBuilder();
        
        json.append(OPEN_STATE + OPEN_SQUARE);
        
        for(State s : automa.getStates())
        {
            json.append(String.format(JSON_STATE, s.getName())).append(COMMA);
        }

        return json.toString();
    }
    
    /**
     * 
     * @param automa
     * @return 
     */
    public String getStateJSON(SyncAutoma automa)
    {
        StringBuilder json = new StringBuilder();
        
        json.append(OPEN_STATE + OPEN_SQUARE);
        
        for(SyncState s : automa.getStates())
        {
            json.append(String.format(JSON_STATE, s.getState1().getName() + s.getState2().getName())).append(COMMA);
        }

        return json.toString();
    }
    
    /**
     * 
     * @param automa
     * @return 
     */
    public String getTransitionJSON(Automa automa)
    {
        StringBuilder json = new StringBuilder();
        
        json.append(OPEN_TRANSITION + OPEN_SQUARE);
        
        for(Transition t : automa.getTransitions())
        {
            json.append(String.format(JSON_TRANSITION, t.getStart().getName(), t.getEnd().getName(), Arrays.toString(t.getEvent().getEvents().toArray()), (t.isFault() ? "red" : "blue"))).append(COMMA);
        }
        
        return json.toString();

    }
    
    /**
     * 
     * @param automa
     * @return 
     */
    public String getTransitionJSON(SyncAutoma automa)
    {
        StringBuilder json = new StringBuilder();
        
        json.append(OPEN_TRANSITION + OPEN_SQUARE);
        
        for(SyncTransition t : automa.getTransitions())
        {
            json.append(String.format(JSON_TRANSITION, 
                    t.getStart().getState1().getName() + t.getStart().getState2().getName(), 
                    t.getEnd().getState1().getName() + t.getEnd().getState2().getName(),
                    Arrays.toString(t.getT1().getEvent().getEvents().toArray()), (t.isAmbiguous() ? "red" : "blue"))).append(COMMA);
        }
        
        return json.toString();

    }
    
    /**
     * 
     * @param automa
     * @return 
     */
    public String getAutomaJSON(Automa automa)
    {
        StringBuilder sb = new StringBuilder();
        StringBuilder states = new StringBuilder(getStateJSON(automa));
        StringBuilder transitions = new StringBuilder(getTransitionJSON(automa));
        
        for(Transition t : automa.getTransitions().stream().filter((t) -> (t.getStart().equals(t.getEnd()))).collect(Collectors.toList()))
        {
            states.append(String.format(JSON_STATE, String.format(JSON_LOOP, t.getStart().getName()))).append(COMMA);
            transitions.append(String.format(JSON_TRANSITION, t.getStart().getName(), String.format(JSON_LOOP, t.getEnd().getName()), Arrays.toString(t.getEvent().getEvents().toArray()), (t.isFault() ? "red" : "blue"))).append(COMMA);
            transitions.append(String.format(JSON_TRANSITION, String.format(JSON_LOOP, t.getStart().getName()), t.getEnd().getName(), Arrays.toString(t.getEvent().getEvents().toArray()), (t.isFault() ? "red" : "blue"))).append(COMMA);
        }
        states.deleteCharAt(states.length() - 1);
        states.append(CLOSE_SQUARE);
        
        transitions.deleteCharAt(transitions.length() - 1);
        transitions.append(CLOSE_SQUARE);
        
        sb.append(OPEN_BRACKET)
            .append(states)
            .append(COMMA)  
            .append(transitions)
            .append(CLOSE_BRACKET);
        
        return sb.toString();
        
    }
    
    /**
     * 
     * @param automa
     * @return 
     */
    public String getAutomaJSON(SyncAutoma automa)
    {
        StringBuilder sb = new StringBuilder();
        StringBuilder states = new StringBuilder(getStateJSON(automa));
        StringBuilder transitions = new StringBuilder(getTransitionJSON(automa));
        
        for(SyncTransition t : automa.getTransitions().stream().filter((t) -> (t.getStart().equals(t.getEnd()))).collect(Collectors.toList()))
        {
            states.append(String.format(JSON_STATE, String.format(JSON_LOOP, t.getStart().getState1().getName() + t.getStart().getState2().getName()))).append(COMMA);
            transitions.append(String.format(JSON_TRANSITION, t.getStart().getState1().getName() + t.getStart().getState2().getName(), String.format(JSON_LOOP, t.getEnd().getState1().getName() + t.getEnd().getState2().getName()), Arrays.toString(t.getT1().getEvent().getEvents().toArray()), (t.isAmbiguous()? "red" : "blue"))).append(COMMA);
            transitions.append(String.format(JSON_TRANSITION, String.format(JSON_LOOP, t.getEnd().getState1().getName() + t.getEnd().getState2().getName()), t.getStart().getState1().getName() + t.getStart().getState2().getName(), Arrays.toString(t.getT1().getEvent().getEvents().toArray()), (t.isAmbiguous()? "red" : "blue"))).append(COMMA);
            
        }
        states.deleteCharAt(states.length() - 1);
        states.append(CLOSE_SQUARE);
        
        transitions.deleteCharAt(transitions.length() - 1);
        transitions.append(CLOSE_SQUARE);
        
        sb.append(OPEN_BRACKET)
            .append(states)
            .append(COMMA)  
            .append(transitions)
            .append(CLOSE_BRACKET);
        
        return sb.toString();
        
    }
    
    /**
     * 
     * @param automa
     * @param path
     * @throws FileNotFoundException 
     */
    public void createAutomaView(Automa automa, String path) throws FileNotFoundException
    {
        File file = new File(path + "\\ControllerAutoma.html");
        String data = getAutomaJSON(automa);
        PrintWriter pw = new PrintWriter(file);
        pw.print(String.format(JSON_CODE, data));
        pw.flush();
        
    }
    
    /**
     * 
     * @param automa
     * @param path
     * @throws FileNotFoundException 
     */
    public void createAutomaView(SyncAutoma automa, String path)throws FileNotFoundException
    {
        File file = new File(path + "\\ControllerAutoma.html");
        String data = getAutomaJSON(automa);
        PrintWriter pw = new PrintWriter(file);
        pw.print(String.format(JSON_CODE, data));
        pw.flush();
        
    }
}
