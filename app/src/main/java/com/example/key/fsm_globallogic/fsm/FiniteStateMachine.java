package com.example.key.fsm_globallogic.fsm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by key on 08.02.18.
 */

public class FiniteStateMachine {
    ArrayList<Action> actions = new ArrayList<>();
    ArrayList<State> states = new ArrayList<>();
    State currentState;


    public FiniteStateMachine(JSONObject jsonObject) {

        initialFSM(jsonObject);
    }


   public void handleAction(String actionStr) {
        for (Transition transition : getActionFromName( actionStr).getTransitions()) {
            if (transition.getStartState().equals(currentState.getName())) {
                currentState = getStateForName(transition.getEndState());
                break;
            }
        }
    }

    public ArrayList<State> getStates() {
        return states;
    }

    private State getStateForName(String endState) {
        for (State state : states) {
            if (state.getName().equals(endState)) {
                return state;
            }
        }
        return null;
    }
    private Action getActionFromName(String name){
        for (Action action : actions) {
            if (action.getName().equals(name)) {
                return action;
            }
        }
        return null;
    }

    public ArrayList<Action> getActions() {
        return actions;
    }


    public State getCurrentState() {
        return currentState;
    }

    private void initialFSM(JSONObject jsonObject) {
        try {
            JSONArray actionsJSON = jsonObject.getJSONArray("actions");
            JSONArray statesJSON = jsonObject.getJSONArray("states");
            for (int i = 0; i < actionsJSON.length(); i++) {
                JSONObject jsonActionObject = actionsJSON.getJSONObject(i);
                JSONArray jsonTransitionsArray = jsonActionObject.getJSONArray("transitions");
                ArrayList<Transition> transitions = new ArrayList<>();
                for (int j = 0; j < jsonTransitionsArray.length(); j++) {
                    Transition transition = new Transition(
                            jsonTransitionsArray.getJSONObject(j).getString("from"),
                            jsonTransitionsArray.getJSONObject(j).getString("to"));
                    transitions.add(transition);
                }
                Action action = new Action(jsonActionObject.getString("name"), transitions);
                actions.add(action);
            }
            for (int i = 0; i < statesJSON.length(); i++) {
                JSONObject jo_inside = statesJSON.getJSONObject(i);
                State state = new State(
                        jo_inside.getString("name"),
                        jo_inside.getBoolean("alarm"));
                states.add(state);
            }
            currentState = states.get(jsonObject.getInt("startState"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}