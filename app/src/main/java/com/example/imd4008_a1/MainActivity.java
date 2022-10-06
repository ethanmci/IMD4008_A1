package com.example.imd4008_a1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


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
                } else if (lastEntry.charAt(0) == '0' && lastEntry.length() == 1 && entry != '0') {
                    calcEntries.set(calcEntries.size() - 1, Character.toString(entry));
                } else {
                    // char is not an operator

                    // bit messy? might want to change...
                    if(lastEntry.charAt(0) == '0' && lastEntry.length() == 1 && entry == '0' ) return;

                    calcEntries.set(calcEntries.size() - 1,
                            calcEntries.get(calcEntries.size() - 1) + Character.toString(entry));
                }
            }
        }
        else if (calcEntries.size() != 0) {
            String lastEntry = calcEntries.get(calcEntries.size() - 1);
            if (Character.isDigit(lastEntry.charAt(0)) || lastEntry.length() != 1) {
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
                } else if (lastEntry.contains("-") && lastEntry.length() == 2) {
                    // has a negative sign
                    calcEntries.remove(calcEntries.size() - 1);
                } else {
                    calcEntries.set(calcEntries.size() - 1,
                            lastEntry.substring(0, lastEntry.length() - 1));
                }
            }
        }
    }

    public void clearEntry() {
        calcEntries = new ArrayList<String>();
    }

    public void changeSign() {
        if(calcEntries.size() > 0) {
            String lastEntry = calcEntries.get(calcEntries.size() - 1);
            if (!Character.isDigit(lastEntry.charAt(0)) && lastEntry.length() == 1) {
                // char is an operator
                return;
            } else {
                // char is not an operator
                if(Character.isDigit(lastEntry.charAt(0))) {
                    // no sign, add negative
                    calcEntries.set(calcEntries.size() - 1, "-" + lastEntry);
                } else if (lastEntry.length() > 1) {
                    // there is a sign, remove the negative
                    calcEntries.set(calcEntries.size() - 1,
                            lastEntry.substring(1, lastEntry.length()));
                }
            }
        }
    }

    public void calculate() {
        float result;
        // trimming undesired operators at the end
        if(calcEntries.get(calcEntries.size() - 1).length() == 1
                && !Character.isDigit(calcEntries.get(calcEntries.size() - 1).charAt(0))) {
            calcEntries.remove(calcEntries.size() - 1);
        }

        // dealing with multiplication
        while(calcEntries.contains("*") || calcEntries.contains("/")) {
            int insertIndex = 0;
            String subResult = "";
            for (String entry : calcEntries) {
                if(entry.equals("*") || entry.equals("/")) {
                    // found an operator
                    float x = Float.parseFloat(calcEntries.get(insertIndex - 1));
                    float y = Float.parseFloat(calcEntries.get(insertIndex + 1));

                    if(entry.equals("*")) { subResult = String.valueOf(x * y); }
                    else { subResult = String.valueOf(x / y); }

                    calcEntries.subList(insertIndex - 1, insertIndex + 2).clear();
                    break;
                }
                insertIndex++;
            }

            if(!subResult.equals("")) calcEntries.add(insertIndex - 1, subResult);
        }

        // dealing w ith addition + subtraction
        while(calcEntries.contains("+") || calcEntries.contains("-")) {
            int insertIndex = 0;
            String subResult = "";
            for (String entry : calcEntries) {
                if(entry.equals("+") || entry.equals("-")) {
                    // found an operator
                    float x = Float.parseFloat(calcEntries.get(insertIndex - 1));
                    float y = Float.parseFloat(calcEntries.get(insertIndex + 1));

                    if(entry.equals("+")) { subResult = String.valueOf(x + y); }
                    else { subResult = String.valueOf(x - y); }

                    calcEntries.subList(insertIndex - 1, insertIndex + 2).clear();
                    break;
                }
                insertIndex++;
            }

            if(!subResult.equals("")) calcEntries.add(insertIndex - 1, subResult);
        }
        Log.d("CALC", String.valueOf(calcEntries));
        clearEntry();
    }


    public void addDecimal() {
        if(calcEntries.size() == 0) {
            calcEntries.add("0.");
        } else if (!calcEntries.get(calcEntries.size() - 1).contains(".")) {
            Log.d("DECIMAL", "no decimal yet");
            String lastEntry = calcEntries.get(calcEntries.size() - 1);
            if (Character.isDigit(lastEntry.charAt(0)) || lastEntry.length() != 1) {
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
        boolean equalsPressed = false;
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
            case R.id.divBtn:
                addEntry('/');
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
                equalsPressed = true;
                break;
            default:
                Log.e("BUTTON", "Unidentified button pressed");
        }
        Log.d("STATE", String.valueOf(calcEntries));
        if(!equalsPressed) updateCalcDisplay();
    }
}