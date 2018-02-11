package com.example.key.fsm_globallogic;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.key.fsm_globallogic.fsm.Action;
import com.example.key.fsm_globallogic.fsm.FiniteStateMachine;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

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
        fsm = new FiniteStateMachine(loadJSONFromAsset());

        updateState(fsm);
        createButtons();

    }

    private void createButtons() {
        for (int i = 0; i < fsm.getActions().size(); i++) {
            Button button = new Button(this);
            Action action = fsm.getActions().get(i);
            button.setTag(action);
            button.setText(action.getName());
            buttonContainer.addView(button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fsm.handleAction(((Action)v.getTag()).getName());
                    updateState(fsm);
                }
            });
        }
    }


    private void updateState(FiniteStateMachine fsm) {
       if (fsm.getCurrentState().isArmed()){
           alarmBox.setBackgroundColor(Color.RED);
        }else {
           alarmBox.setBackgroundColor(Color.GREEN);
       }
       alarmBox.setText(fsm.getCurrentState().getName());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


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
