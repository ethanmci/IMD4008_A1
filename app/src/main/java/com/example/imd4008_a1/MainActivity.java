package com.example.imd4008_a1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class calcEntry {

}

public class MainActivity extends AppCompatActivity {

    // operator buttons
    Button addBtn;
    Button subBtn;
    Button multiBtn;
    Button divBtn;

    // number buttons
    Button nineBtn;
    Button eightBtn;
    Button sevenBtn;
    Button sixBtn;
    Button fiveBtn;
    Button fourBtn;
    Button threeBtn;
    Button twoBtn;
    Button oneBtn;
    Button zeroBtn;

    // command buttons
    Button clearBtn;
    Button deleteBtn;
    Button equalsBtn;

    // displays
    TextView calcDisplay;
    TextView historyView;

    List<String> calcEntries;
    List<String> history;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calcDisplay = findViewById(R.id.calcDisplay);

        // setting up where calculator entries will be stored
        calcEntries = new ArrayList<String>();


        // getting views
        addBtn = findViewById(R.id.addBtn);
        subBtn = findViewById(R.id.subBtn);
        multiBtn = findViewById(R.id.multiBtn);
        divBtn = findViewById(R.id.divBtn);

        nineBtn = findViewById(R.id.nineBtn);
        eightBtn = findViewById(R.id.eightBtn);
        sevenBtn = findViewById(R.id.sevenBtn);
        sixBtn = findViewById(R.id.sixBtn);
        fiveBtn = findViewById(R.id.fiveBtn);
        fourBtn = findViewById(R.id.fourBtn);
        threeBtn = findViewById(R.id.threeBtn);
        twoBtn = findViewById(R.id.twoBtn);
        oneBtn = findViewById(R.id.oneBtn);
        zeroBtn = findViewById(R.id.zeroBtn);

        clearBtn = findViewById(R.id.clearBtn);
        deleteBtn = findViewById(R.id.delBtn);
        equalsBtn = findViewById(R.id.equalsBtn);
    }

    public void addEntry(char entry) {
        if(Character.isDigit(entry)
                || (calcEntries.size() != 0
                    && Character.isDigit((char) calcEntries.get(calcEntries.size() - 1).charAt(0)))) {
            calcEntries.add(String.valueOf(entry));
        }
    }

    public void delEntry() {

    }

    public void clearEntry() {
        calcEntries = new ArrayList<String>();
    }

    public void updateCalcDisplay() {
        String out = "";
        Log.d("STATE", "reached!");
        for(String entry : calcEntries) {
            if(Character.isDigit(entry.charAt(0)) && entry.length() == 1) {
                Log.d("STATE", "it's a digit:" + entry.charAt(0));
                out = out + entry;
            } else {
                out = out + " " + entry + " ";
            }
        }
        calcDisplay.setText(out);
    }

    public void buttonClicked(View v) {
        // using haptic feedback for every button
        v.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_PRESS);
        switch (v.getId()) {
            case R.id.nineBtn:
                addEntry('9');
                break;
            case R.id.eightBtn:
                addEntry('8');
                break;
            case R.id.sevenBtn:
                addEntry('7');
                break;
            case R.id.sixBtn:
                addEntry('6');
                break;
            case R.id.fiveBtn:
                addEntry('5');
                break;
            case R.id.fourBtn:
                addEntry('4');
                break;
            case R.id.threeBtn:
                addEntry('3');
                break;
            case R.id.twoBtn:
                addEntry('2');
                break;
            case R.id.oneBtn:
                addEntry('1');
                break;
            case R.id.zeroBtn:
                addEntry('0');
                break;
            case R.id.addBtn:
                addEntry('+');
                break;
            case R.id.subBtn:
                addEntry('-');
                break;
            case R.id.multiBtn:
                addEntry('*');
                break;
            case R.id.delBtn:
                delEntry();
                break;
            case R.id.clearBtn:
                clearEntry();
                break;
            default:
                Log.e("BUTTON", "Unidentified button pressed");

        }
        Log.d("STATE", String.valueOf(calcEntries));
        updateCalcDisplay();
    }
}