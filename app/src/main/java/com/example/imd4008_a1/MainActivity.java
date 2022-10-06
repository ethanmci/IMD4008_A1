package com.example.imd4008_a1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // displays
    TextView calcDisplay;
    TextView historyView;

    // arraylists to store data
    List<String> calcEntries;
    List<String> history;

    String answer;
    boolean isDisplayingAnswer;

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
    Button signBtn;
    Button deciBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isDisplayingAnswer = false;

        // setting up calculator display
        calcDisplay = findViewById(R.id.calcDisplay);
        calcEntries = new ArrayList<>();

        // setting up the history view
        historyView = findViewById(R.id.historyView);
        history = new ArrayList<>();

        // getting buttons and setting on click listener
        addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(this);
        subBtn = findViewById(R.id.subBtn);
        subBtn.setOnClickListener(this);
        multiBtn = findViewById(R.id.multiBtn);
        multiBtn.setOnClickListener(this);
        divBtn = findViewById(R.id.divBtn);
        divBtn.setOnClickListener(this);

        nineBtn = findViewById(R.id.nineBtn);
        nineBtn.setOnClickListener(this);
        eightBtn = findViewById(R.id.eightBtn);
        eightBtn.setOnClickListener(this);
        sevenBtn = findViewById(R.id.sevenBtn);
        sevenBtn.setOnClickListener(this);
        sixBtn = findViewById(R.id.sixBtn);
        sixBtn.setOnClickListener(this);
        fiveBtn = findViewById(R.id.fiveBtn);
        fiveBtn.setOnClickListener(this);
        fourBtn = findViewById(R.id.fourBtn);
        fourBtn.setOnClickListener(this);
        threeBtn = findViewById(R.id.threeBtn);
        threeBtn.setOnClickListener(this);
        twoBtn = findViewById(R.id.twoBtn);
        twoBtn.setOnClickListener(this);
        oneBtn = findViewById(R.id.oneBtn);
        oneBtn.setOnClickListener(this);
        zeroBtn = findViewById(R.id.zeroBtn);
        zeroBtn.setOnClickListener(this);

        clearBtn = findViewById(R.id.clearBtn);
        clearBtn.setOnClickListener(this);
        deleteBtn = findViewById(R.id.delBtn);
        deleteBtn.setOnClickListener(this);
        equalsBtn = findViewById(R.id.equalsBtn);
        equalsBtn.setOnClickListener(this);
        signBtn = findViewById(R.id.signBtn);
        signBtn.setOnClickListener(this);
        deciBtn = findViewById(R.id.deciBtn);
        deciBtn.setOnClickListener(this);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putStringArrayList("history", (ArrayList<String>) history);
        savedInstanceState.putStringArrayList("calcEntries", (ArrayList<String>) calcEntries);
        savedInstanceState.putBoolean("isDisplayingAnswer", isDisplayingAnswer);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        history = savedInstanceState.getStringArrayList("history");
        calcEntries = savedInstanceState.getStringArrayList("calcEntries");
        isDisplayingAnswer = savedInstanceState.getBoolean("isDisplayingAnswer");
        updateHistoryDisplay();
        if(isDisplayingAnswer) calcDisplay.setText(answer);
        else updateCalcDisplay();
    }

    @Override
    public void onClick(View v) {
        // using haptic feedback for every button
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            v.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_PRESS);
        }
        isDisplayingAnswer = false;
        int id = v.getId();
        if (id == R.id.nineBtn) {
            addEntry('9');
        } else if (id == R.id.eightBtn) {
            addEntry('8');
        } else if (id == R.id.sevenBtn) {
            addEntry('7');
        } else if (id == R.id.sixBtn) {
            addEntry('6');
        } else if (id == R.id.fiveBtn) {
            addEntry('5');
        } else if (id == R.id.fourBtn) {
            addEntry('4');
        } else if (id == R.id.threeBtn) {
            addEntry('3');
        } else if (id == R.id.twoBtn) {
            addEntry('2');
        } else if (id == R.id.oneBtn) {
            addEntry('1');
        } else if (id == R.id.zeroBtn) {
            addEntry('0');
        } else if (id == R.id.addBtn) {
            addEntry('+');
        } else if (id == R.id.subBtn) {
            addEntry('-');
        } else if (id == R.id.multiBtn) {
            addEntry('*');
        } else if (id == R.id.divBtn) {
            addEntry('/');
        } else if (id == R.id.deciBtn) {
            addDecimal();
        } else if (id == R.id.signBtn) {
            changeSign();
        } else if (id == R.id.delBtn) {
            delEntry();
        } else if (id == R.id.clearBtn) {
            clearEntry();
        } else if (id == R.id.equalsBtn) {
            calculate();
            isDisplayingAnswer = true;
        } else {
            Log.e("BUTTON", "Unidentified button pressed");
        }
        if(isDisplayingAnswer) updateCalcDisplay();
    }

    public void addEntry(char entry) {
        if(Character.isDigit(entry) && Character.getNumericValue(entry) < 10) {
            if (calcEntries.size() == 0) {
                calcEntries.add(Character.toString(entry));
            } else {
                // getting the last entry in the calcEntries list
                String lastEntry = calcEntries.get(calcEntries.size() - 1);
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
                            calcEntries.get(calcEntries.size() - 1) +entry);
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
        calcEntries = new ArrayList<>();
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
                            lastEntry.substring(1));
                }
            }
        }
    }

    public void calculate() {
        if(calcEntries.size() <= 0) return;

        String fullEquation = "";
        for(String entry : calcEntries) {
            fullEquation = fullEquation.concat(entry);
        }

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

        answer = String.valueOf(calcEntries.get(0));
        Log.d("ANSWER", answer);
        calcDisplay.setText(answer);
        history.add(0, fullEquation + " = " + calcEntries.get(0));

        updateHistoryDisplay();
        clearEntry();
    }


    public void addDecimal() {
        if(calcEntries.size() == 0) {
            calcEntries.add("0.");
        } else if (!calcEntries.get(calcEntries.size() - 1).contains(".")) {
            String lastEntry = calcEntries.get(calcEntries.size() - 1);
            if (Character.isDigit(lastEntry.charAt(0)) || lastEntry.length() != 1) {
                calcEntries.set(calcEntries.size() - 1, lastEntry + ".");
            }
        }
    }

    public void updateCalcDisplay() {
        String out = "";
        for(String entry : calcEntries) {
            out = out.concat(" " + entry);
        }
        calcDisplay.setText(out);
    }

    public void updateHistoryDisplay() {
        String out = "";
        for(String item : history) {
            out = out.concat(item + "\n");
        }
        historyView.setText(out);
    }
}