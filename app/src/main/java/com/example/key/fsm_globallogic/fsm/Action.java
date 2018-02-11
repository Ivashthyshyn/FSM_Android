package com.example.key.fsm_globallogic.fsm;

import java.util.ArrayList;

/**
 * Created by key on 10.02.18.
 */
public class Action {
    private String name;
    private ArrayList<Transition> transitions;

    Action(String name, ArrayList<Transition> transitions) {
        this.name = name;
        this.transitions = transitions;
    }

    public String getName() {
        return name;
    }

   public ArrayList<Transition> getTransitions() {
        return transitions;
    }
}
