package com.example.key.fsm_globallogic;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    FiniteStateMachine fsm;
    TextView alarmBox;
    LinearLayout buttonContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonContainer = findViewById(R.id.buttonContainer);
        alarmBox = findViewById(R.id.alarmBox);
        fsm = new FiniteStateMachine(this);
        fsm.init(loadJSONFromAsset());
        updateState();
        createButtons();

    }

    private void createButtons() {
        if (fsm != null && fsm.getActions() != null && fsm.getActions().size() != 0) {
            for (Iterator<String> iterator = fsm.getActions().iterator(); iterator.hasNext(); ) {
                Button button = new Button(this);
                String action = iterator.next();
                button.setTag(action);
                button.setText(action);
                buttonContainer.addView(button);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fsm.handleAction(((String) v.getTag()));
                        updateState();
                    }
                });
            }
        }
    }


    private void updateState() {
        if (fsm.getCurrentState() != null && fsm.isArmed(fsm.getCurrentState())) {
            alarmBox.setBackgroundColor(Color.RED);
        } else {
            alarmBox.setBackgroundColor(Color.GREEN);
        }
        alarmBox.setText(fsm.getCurrentState());
    }

    private JSONObject loadJSONFromAsset() {
        JSONObject object;
        try {
            InputStream is = this.getAssets().open("fsm.json");
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
