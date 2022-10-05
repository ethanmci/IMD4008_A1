package com.example.imd4008_a1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Debug;
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
        Log.d("ENTRY", String.valueOf(entry));
        if(Character.isDigit(entry) && Character.getNumericValue(entry) < 10) {
            if (calcEntries.size() == 0) {
                calcEntries.add(Character.toString(entry));
            } else {
                String lastEntry = calcEntries.get(calcEntries.size() - 1);
                //Log.d("ENTRY", lastEntry);
                if (!Character.isDigit(lastEntry.charAt(0)) && lastEntry.length() == 1) {
                    // char is an operator
                    calcEntries.add(Character.toString(entry));
                } else {
                    // char is not an operator
                    calcEntries.set(calcEntries.size() - 1,
                            calcEntries.get(calcEntries.size() - 1) + Character.toString(entry));
                }
            }
        } else if (calcEntries.size() != 0) {
            String lastEntry = calcEntries.get(calcEntries.size() - 1);
            if (Character.isDigit(lastEntry.charAt(0)) && lastEntry.length() != 1) {
                calcEntries.add(Character.toString(entry));
            }
        }

    }

    public void delEntry() {
        if(calcEntries.size() != 0) {
            String lastEntry = calcEntries.get(calcEntries.size() - 1);
            if (!Character.isDigit(lastEntry.charAt(0)) && lastEntry.length() == 1) {
                // char is an operator
                calcEntries.remove(calcEntries.size() - 1);
            } else {
                // char is not an operator
                if (lastEntry.length() == 1) {
                    calcEntries.remove(calcEntries.size() - 1);
                } else if (lastEntry.contains(".")) {
                    // removing a decimal
                    if (lastEntry.substring(0, 2) == "0.") {
                        calcEntries.remove(calcEntries.size() - 1);
                    } else {
                        calcEntries.set(calcEntries.size() - 1,
                                lastEntry.substring(0, lastEntry.length() - 1));
                    }
                } else {
                    calcEntries.set(calcEntries.size() - 1,
                            lastEntry.substring(0, lastEntry.length()));
                }
            }
        }
    }

    public void clearEntry() {
        calcEntries = new ArrayList<String>();
    }

    public void changeSign() {

        clearEntry();
    }

    public void calculate() {}


    public void addDecimal() {
        if(calcEntries.size() == 0) {
            calcEntries.add("0.");
        } else if (!calcEntries.get(calcEntries.size() - 1).contains(".")) {
            Log.d("DECIMAL", "no decimal yet");
            String lastEntry = calcEntries.get(calcEntries.size() - 1);
            if (Character.isDigit(lastEntry.charAt(0)) && lastEntry.length() != 1) {
                calcEntries.set(calcEntries.size() - 1, lastEntry + ".");
            }
        }
    }

    public void updateCalcDisplay() {
        String out = "";
        for(String entry : calcEntries) {
            out = out + " " + entry;
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
            case R.id.deciBtn:
                addDecimal();
                break;
            case R.id.signBtn:
                changeSign();
                break;
            case R.id.delBtn:
                delEntry();
                break;
            case R.id.clearBtn:
                clearEntry();
                break;
            case R.id.equalsBtn:
                calculate();
                break;
            default:
                Log.e("BUTTON", "Unidentified button pressed");

        }
        Log.d("STATE", String.valueOf(calcEntries));
        updateCalcDisplay();
    }
}