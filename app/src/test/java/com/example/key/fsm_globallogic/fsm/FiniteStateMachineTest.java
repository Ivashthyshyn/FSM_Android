package com.example.key.fsm_globallogic.fsm;

import android.os.Build;

import com.example.key.fsm_globallogic.BuildConfig;
import com.example.key.fsm_globallogic.FiniteStateMachine;
import com.example.key.fsm_globallogic.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


/**
 * Created by key on 10.02.18.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP_MR1)
public class FiniteStateMachineTest {
    MainActivity activity;
    FiniteStateMachine finiteStateMachine;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.setupActivity(MainActivity.class);
        finiteStateMachine = new FiniteStateMachine(activity);

    }

    @Test
    public void initialFSM() throws Exception {
        //test
        finiteStateMachine.init(loadJSONFromAsset("test1.json"));
        assertNotNull(finiteStateMachine);
        assertNotNull(finiteStateMachine.getActions());
        assertNotNull(finiteStateMachine.getCurrentState());
    }


    @Test
    public void initialToNull() {
        //test
        finiteStateMachine.init(null);
        assertNotNull(finiteStateMachine);
        assertEquals(0, finiteStateMachine.getActions().size());
        assertNull(finiteStateMachine.getCurrentState());
        assertEquals("The json object cannot be null!", ShadowToast.getTextOfLatestToast());


    }

    @Test
    public void actionsNumber() throws Exception {
        //test
        finiteStateMachine.init(loadJSONFromAsset("test1.json"));
        assertEquals(2, finiteStateMachine.getActions().size()); // actionLock / actionUnlock
    }


    @Test
    public void testUpdateState_success() throws Exception {
        finiteStateMachine.init(loadJSONFromAsset("test1.json"));

        //test
        assertEquals("State1", finiteStateMachine.getCurrentState());
        finiteStateMachine.handleAction("lock");
        assertEquals("State2", finiteStateMachine.getCurrentState());
        finiteStateMachine.handleAction("lock");
        assertEquals("State2", finiteStateMachine.getCurrentState());
        finiteStateMachine.handleAction("unlock");
        assertEquals("State1", finiteStateMachine.getCurrentState());
        finiteStateMachine.handleAction("unlock");
        assertEquals("State1", finiteStateMachine.getCurrentState());
    }


    @Test
    public void isArmed() throws Exception {
        //test
        finiteStateMachine.init(loadJSONFromAsset("test1.json"));
        assertEquals(false, finiteStateMachine.isArmed(finiteStateMachine.getCurrentState()));
        finiteStateMachine.handleAction("lock");
        assertEquals(true, finiteStateMachine.isArmed(finiteStateMachine.getCurrentState()));
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