package edu.wm.cs.cs301.amazebycarolinefaparnan.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import edu.wm.cs.cs301.amazebycarolinefaparnan.R;
/**
 * Class: FinishActivity
 * Responsibility: Shows the user a message based on whether the game was won or lost. Option to play again.
 * Collaborators: PlayActivity
 *
 * @author Caroline Fagan and Aparna Nagaraj
 */
public class FinishActivity extends AppCompatActivity {

    Button playAgain;
    TextView winner;
    TextView failed;
    ImageView win;
    ImageView lose;
    TextView remainingEnergy;
    TextView pathLength;

    /**
     * Show the win/lose message and create the button to return to the start screen if selected
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        Intent genIntent = getIntent();
        Boolean solved = genIntent.getBooleanExtra("Solved?", true);

        if (PlayActivity.solved){
            winner = (TextView) findViewById(R.id.solvedCongrats);
            win = (ImageView)findViewById(R.id.winner);
            winner.setVisibility(winner.VISIBLE);
            win.setVisibility(win.VISIBLE);
        }
        else{
            failed = (TextView)findViewById(R.id.notSolved);
            lose = (ImageView)findViewById(R.id.loser);
            failed.setVisibility(failed.VISIBLE);
            lose.setVisibility(lose.VISIBLE);
        }


//        winner = (TextView) findViewById(R.id.solvedCongrats);
//        win = (ImageView)findViewById(R.id.winner);
        remainingEnergy = (TextView)findViewById(R.id.energyLevel);
        remainingEnergy.setText("Remaining Energy: " + String.valueOf(PlayActivity.energy.getProgress()));

        pathLength = (TextView)findViewById(R.id.pathLength);
        pathLength.setText("Path Length: " + String.valueOf(PlayActivity.path_length));


//        winner.setVisibility(winner.VISIBLE);
//        win.setVisibility(win.VISIBLE);

        //Play again button and listener, returns to the start screen if pressed
        playAgain = (Button) findViewById(R.id.restart);
        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Going back to start screen", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(FinishActivity.this, AMazeActivity.class);

                startActivity(i);
                finish();
                Log.v("Title: revisit button", "load an old maze");

            }
        });
    }

    /**
     * Return the user to AMazeActivity
     */
    @Override
    public void onBackPressed(){
        this.startActivity(new Intent(FinishActivity.this, AMazeActivity.class));

    }

}
