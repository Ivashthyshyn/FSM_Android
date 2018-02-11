package com.example.key.fsm_globallogic.fsm;

import com.example.key.fsm_globallogic.BuildConfig;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;


/**
 * Created by key on 10.02.18.
 *
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class FiniteStateMachineTest {
    FiniteStateMachine finiteStateMachine;

    @Test
    public void statesNumber() throws Exception {
        finiteStateMachine = new FiniteStateMachine(loadJSONFromAsset("test1.json"));
        assertEquals(2,  finiteStateMachine.getStates().size()); // state1 state2
    }
    @Test
    public void actionsNumber() throws Exception {
        finiteStateMachine = new FiniteStateMachine(loadJSONFromAsset("test1.json"));
        assertEquals(2,  finiteStateMachine.getActions().size()); // actionLock / actionUnlock
    }

    @Test
    public void transitionsNumber() throws Exception {
        finiteStateMachine = new FiniteStateMachine(loadJSONFromAsset("test1.json"));
        int transitionsNumber = 0;
        for (Action action :
                finiteStateMachine.getActions()) {
           transitionsNumber = transitionsNumber + action.getTransitions().size();
        }

        assertEquals(4,  transitionsNumber);
    }


    @Test
    public void testUpdateState_success() throws Exception {
        finiteStateMachine = new FiniteStateMachine(loadJSONFromAsset("test1.json"));
        //test

        assertEquals(finiteStateMachine.getCurrentState().getName(),
                finiteStateMachine.getStates().get(0).getName()); // state1

        finiteStateMachine.handleAction("lock");
        assertEquals(finiteStateMachine.getCurrentState().getName(),
                finiteStateMachine.getStates().get(1).getName());   // from state1 - to state2

        finiteStateMachine.handleAction("lock");
        assertEquals(finiteStateMachine.getCurrentState().getName(),
                finiteStateMachine.getStates().get(1).getName());   //from state2 - to state2

        finiteStateMachine.handleAction("unlock");
        assertEquals(finiteStateMachine.getCurrentState().getName(),
                finiteStateMachine.getStates().get(0).getName());   // from state2 - to state1

        finiteStateMachine.handleAction("unlock");
        assertEquals(finiteStateMachine.getCurrentState().getName(),
                finiteStateMachine.getStates().get(0).getName());   //from state1 - to state1
    }


    @Test
    public void isArmed() throws Exception {
        finiteStateMachine = new FiniteStateMachine(loadJSONFromAsset("test1.json"));
        assertEquals(false, finiteStateMachine.getCurrentState().isArmed()); // state1
        finiteStateMachine.handleAction("lock");
        assertEquals(true,  finiteStateMachine.getCurrentState().isArmed()); // state2
    }


    private JSONObject loadJSONFromAsset(String fileName) {
        JSONObject object;
        try {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String jsonString = new String(buffer, "UTF-8");
            object = new JSONObject(jsonString);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return object;
    }


}