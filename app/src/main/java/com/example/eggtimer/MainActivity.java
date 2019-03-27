package com.example.eggtimer;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar timerSeekBar;
    TextView timerTextView;
    boolean counterIsActive = false;
    Button controllerButton;
    CountDownTimer countDownTimer;
    MediaPlayer mPlayer;
    Button shutUp;

     public void updateTimer(int secondsLeft){
        int minutes = (int) secondsLeft / 60;
        int seconds = secondsLeft - minutes * 60;

        String secondString = Integer.toString(seconds);

        if (seconds<= 9){
            secondString = "0" + secondString;
        }

        timerTextView = (TextView) findViewById(R.id.timerTextView);
        timerTextView.setText(Integer.toString(minutes)+":"+secondString);
    }

    public void resetTimer(){
        timerTextView.setText("0:30");
        timerSeekBar.setProgress(30);
        timerSeekBar.setEnabled(true);
        controllerButton.setText("Start");
        countDownTimer.cancel();
        counterIsActive = false;
        shutUp.setVisibility(View.INVISIBLE);
    }

    public void controlTimer(View view){

        if (counterIsActive == false)
        {
        counterIsActive = true;
        timerSeekBar.setEnabled(false);
        controllerButton.setText("Stop");

        countDownTimer = new CountDownTimer(timerSeekBar.getProgress()*1000+100,1000){

            @Override
            public void onTick(long millisUntilFinished) {
            updateTimer((int) millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
                resetTimer();
                mPlayer = MediaPlayer.create(getApplicationContext(),R.raw.airhorn);
                mPlayer.start();
                shutUp.setVisibility(View.VISIBLE);
            }
        }.start();
        }
        else
        {
            resetTimer();

        }
    }

    public void shutUp(View view){
         mPlayer.stop();
         shutUp.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timerSeekBar = (SeekBar) findViewById(R.id.timerSeekBar);
        controllerButton = (Button) findViewById(R.id.controllerButton);
        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);
        shutUp = (Button) findViewById(R.id.shutUpButton);

        shutUp.setVisibility(View.INVISIBLE);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
