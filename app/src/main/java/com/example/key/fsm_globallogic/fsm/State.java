package com.example.key.fsm_globallogic.fsm;

/**
 * Created by key on 10.02.18.
 */
public class State {
   private String name;
   private boolean isArmed;

    State(String name, boolean isArmed) {
        this.name = name;
        this.isArmed = isArmed;

    }

    public String getName() {
        return name;
    }

    public boolean isArmed() {
        return isArmed;
    }
}
