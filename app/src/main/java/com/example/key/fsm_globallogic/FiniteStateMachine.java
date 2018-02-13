package com.example.key.fsm_globallogic;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by key on 08.02.18.
 */

public class FiniteStateMachine {
    private final Context context;
    Map<String, Map<String, String>> actions = new HashMap<>();
    Map<String, Boolean> states = new HashMap<>();
    String currentState;

    public FiniteStateMachine(Context context) {
        this.context = context;
    }


    public void handleAction(String actionStr) {
        currentState = actions.get(actionStr).get(currentState);
    }

    public Set<String> getActions() {
        return actions.keySet();
    }

    public String getCurrentState() {
        return currentState;
    }

    public boolean isArmed(String currentState) {

        return states.get(currentState);
    }


    public void init(JSONObject jsonObject) {
        try {
            if (jsonObject == null) {
                throw new IllegalArgumentException("The json object cannot be null!");
            }
            JSONArray actionsJSON = jsonObject.getJSONArray("actions");
            JSONArray statesJSON = jsonObject.getJSONArray("states");
            for (int i = 0; i < actionsJSON.length(); i++) {
                JSONObject jsonActionObject = actionsJSON.getJSONObject(i);
                JSONArray jsonTransitionsArray = jsonActionObject.getJSONArray("transitions");
                Map<String, String> transitions = new HashMap();
                for (int j = 0; j < jsonTransitionsArray.length(); j++) {
                    transitions.put(jsonTransitionsArray.getJSONObject(j).getString("from"),
                            jsonTransitionsArray.getJSONObject(j).getString("to"));
                }
                actions.put(jsonActionObject.getString("name"), transitions);
            }
            for (int i = 0; i < statesJSON.length(); i++) {
                JSONObject statesJSONJSONObject = statesJSON.getJSONObject(i);
                states.put(statesJSONJSONObject.getString("name"),
                        statesJSONJSONObject.getBoolean("alarm"));
            }

            currentState = jsonObject.getString("startState");
        } catch (IllegalArgumentException exception) {
            exception.printStackTrace();
            Toast.makeText(context, exception.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

        }

    }


}