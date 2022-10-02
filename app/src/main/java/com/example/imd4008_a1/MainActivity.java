package com.example.imd4008_a1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

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
    Button equalBtn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}