package sg.edu.np.WhackAMole;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Main2Activity extends AppCompatActivity {
    int advancedScore;
    CountDownTimer myCountDown;
    TextView scoreTxtView;
    String selectedValue;

    private static final String TAG = "Whack-A-Mole 2.0";

    private void readyTimer(){
        myCountDown = new CountDownTimer(10000, 1000){
            public void onTick(long millisUntilFinished){
                int seconds = (int) millisUntilFinished/1000;
                Log.v(TAG, String.format("Ready CountDown! %d", seconds));
                Toast.makeText(getApplicationContext(), String.format("Get ready in %d seconds", seconds) ,Toast.LENGTH_SHORT).show();
            }

            public void onFinish(){
                Toast.makeText(getApplicationContext(), "GO!" ,Toast.LENGTH_SHORT).show();
                myCountDown.cancel();
                placeMoleTimer();
            }
        };
        myCountDown.start();

    }

    private void placeMoleTimer(){
        myCountDown = new CountDownTimer(10000, 1000){
            public void onTick(long millisUntilFinished){
                int seconds = (int) millisUntilFinished/1000;
                setNewMole();
                Log.v(TAG, "New Mole Location");
            }

            public void onFinish(){
                placeMoleTimer();
            }
        };
        myCountDown.start();

    }

    private static final int[] BUTTON_IDS = {
            R.id.button1,
            R.id.button2,
            R.id.button3,
            R.id.button4,
            R.id.button5,
            R.id.button6,
            R.id.button7,
            R.id.button8,
            R.id.button9,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent receivingEnd = getIntent();
        advancedScore = receivingEnd.getIntExtra("Score", 0);
        Log.v(TAG, "Current User Score: " + String.valueOf(advancedScore));

        scoreTxtView = (TextView) findViewById(R.id.advanceTextView);
        scoreTxtView.setText(String.valueOf(advancedScore));

        for(final int id : BUTTON_IDS){

            final Button button = (Button) findViewById(id);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doCheck(button);
                    setNewMole();
                }
            });
        }


    }

    @Override
    protected void onStart(){
        super.onStart();
        readyTimer();
    }

    private void doCheck(Button checkButton)
    {
        selectedValue = checkButton.getText().toString();

        if(selectedValue == "*"){
            Log.v(TAG, "Hit, score added!");
            advancedScore += 1;
            scoreTxtView.setText(String.valueOf(advancedScore));

        }
        // If Incorrect button pressed
        else{
            if (advancedScore >0){
                advancedScore -= 1;
                scoreTxtView.setText(String.valueOf(advancedScore));
            }
            Log.v(TAG, "Missed, score deducted!");
        }
    }

    public void setNewMole()
    {
        Random ran = new Random();
        int randomLocation = ran.nextInt(9);

        int index = 1;
        for (final int id : BUTTON_IDS){
            Button button = (Button) findViewById(id);
            if (randomLocation+1 == index){
                button.setText("*");
            }
            else{
                button.setText("0");
            }
            index++;
        }
        index=1;
    }
}

