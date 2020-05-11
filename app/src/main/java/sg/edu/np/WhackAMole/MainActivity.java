package sg.edu.np.WhackAMole;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public  Button ButtonLeft;
    public  Button ButtonMiddle;
    public  Button ButtonRight;
    public TextView scoreTxtView;
    private static final String TAG = "Whack-A-Mole 1.0";

    String selectedValue;
    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.v(TAG, "Finished Pre-Initialisation!");

        ButtonLeft = (Button) findViewById(R.id.button1);
        ButtonMiddle = (Button) findViewById(R.id.button2);
        ButtonRight = (Button) findViewById(R.id.button3);
        scoreTxtView = (TextView) findViewById(R.id.scoreTxtView);

        scoreTxtView.setText(String.valueOf(score));

        ButtonLeft.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.v(TAG, "ButtonLeft click");
                //Validate btn
                doCheck(ButtonLeft);
            }
        });

        ButtonMiddle.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.v(TAG, "ButtonMiddle click");
                //Validate btn
                doCheck(ButtonMiddle);
            }
        });

        ButtonRight.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.v(TAG, "ButtonRight click");
                //Validate btn
                doCheck(ButtonRight);
            }
        });

    }
    @Override
    protected void onStart(){
        super.onStart();
        setNewMole();
        Log.v(TAG, "Starting GUI!");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.v(TAG, "Paused Whack-A-Mole!");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.v(TAG, "Stopped Whack-A-Mole!");
        finish();
    }

    private void doCheck(Button checkButton) {
        selectedValue = checkButton.getText().toString();

        if(selectedValue == "*"){
            Log.v(TAG, "Hit, score added!");
            score += 1;
            scoreTxtView.setText(String.valueOf(score));
            setNewMole();

            if (score == 10){
                nextLevelQuery();
            }
        }
        // If Incorrect button pressed
        else{
            if (score >0){
                score -= 1;
                scoreTxtView.setText(String.valueOf(score));
            }
            Log.v(TAG, "Missed, score deducted!");
        }

    }

    private void nextLevelQuery(){
        Log.v(TAG, "Advance option given to user!");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Warning! Insane Wake-A-Mole Incoming!");
        builder.setMessage("Would you like to advance to advance mode?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                Log.v(TAG, "User accepts!");
                nextLevel();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                Log.v(TAG, "User decline!");
            }
        });
        builder.create().show();
    }

    private void nextLevel(){
        /* Launch advanced page */
        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
        intent.putExtra("Score", score);
        startActivity(intent);
    }

    private void setNewMole() {
        Random ran = new Random();
        int randomLocation = ran.nextInt(3)+1;

        if (randomLocation == 1){
            ButtonLeft.setText("*");
            ButtonMiddle.setText("O");
            ButtonRight.setText("O");
        }
        else if (randomLocation == 2){
            ButtonLeft.setText("O");
            ButtonMiddle.setText("*");
            ButtonRight.setText("O");
        }
        else{
            ButtonLeft.setText("O");
            ButtonMiddle.setText("O");
            ButtonRight.setText("*");
        }
    }
}