package com.example.quickreaderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements SpeedDialog.SpeedDialogListner {
    private ImageButton startButton;
    private ImageButton speedButton;
    private EditText inputText;
    private TextView readBox;

    private CountDownTimer countDownTimer;
    private int readSpeed = 300;
    private long timeRemainingMilliseconds;
    private String[] textArray;
    private int arrayIndex;

    private boolean readerRunning = false;

    private int minReadSpeed = 60;
    private int maxReadSpeed = 600;
    private int defaultReadSpeed = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //default content view
        setContentView(R.layout.activity_main);

        //using the findViewById constructor to create some objects from the gui
        startButton = findViewById(R.id.start_button);
        inputText = findViewById(R.id.input_text);
        speedButton = findViewById(R.id.speed_button);

        //creates an interrupt listener for the play button
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startReading();
            }
        });

        //creates a event that detects when the 'speed button' is pressed
        speedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //openDialog method loads dialog_layout.xml as an AlertDialog to let the user input their speed
                openDialog();
            }
        });
    }

    public void startReading()
    {
        //to avoid crashes cancels any existing countDownTimer if and only if one is running
        if (readerRunning) {countDownTimer.cancel();}

        readBox = findViewById(R.id.read_box);
        textArray = inputText.getText().toString().split("\\s+");

        //index position is reset to 0 (to start from the first word in the entry)
        arrayIndex = 0;
        //total time = length of array multiplied by (1 minute/read speed)
        long readRate = (60000/ (long) readSpeed);
        timeRemainingMilliseconds = textArray.length * readRate;

        //creates a countdown timer with total time = array length * readRate and where the time between ticks is a minute divided by the read speed
        countDownTimer = new CountDownTimer(timeRemainingMilliseconds, (60000/ (long) readSpeed)){
            @Override
            public void onTick(long l){
                //each tick the timeRemaining is updated, the text display is updated and the arrayIndex is incremented.
                timeRemainingMilliseconds = l;
                readBox.setText(textArray[arrayIndex]);
                arrayIndex += 1;}
            @Override
            public void onFinish() {}
        }.start(); //starts timer

        //readerRunning set to true
        readerRunning = true;

    }
    public void openDialog() {
        //using the custom made java class, creates a new speed dialog and shows it to the user
        SpeedDialog speedDialog = new SpeedDialog();
        speedDialog.show(getSupportFragmentManager(), "speed dialog");
    }

    @Override
    public void applyTexts(String speed) {
        //applyTexts method is called when ok button pressed in dialog
        try {
            //grabs the readspeed from the dialog variables (converting to an int)
            readSpeed = Integer.parseInt(speed);
            //setting the read speed to a minimum of 60 (variable set at top of MainActivity class)
            if (readSpeed < minReadSpeed){
                readSpeed = minReadSpeed;
            }
            //with a maximum read speed of 600
            else if (readSpeed > maxReadSpeed){
                readSpeed = maxReadSpeed;
            }
            //if the number isn't an integer or any other errors occur the readSpeed is set to a default of 300
        } catch (NumberFormatException e) {
            readSpeed = defaultReadSpeed;
        }
    }
}
