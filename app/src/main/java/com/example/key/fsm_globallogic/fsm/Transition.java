package com.example.key.fsm_globallogic.fsm;

/**
 * Created by key on 10.02.18.
 */
class Transition {
   private String startState;
   private String endState;

    Transition(String startState, String endState) {

        this.startState = startState;
        this.endState = endState;
    }

    String getStartState() {
        return startState;
    }


    String getEndState() {
        return endState;
    }

}
