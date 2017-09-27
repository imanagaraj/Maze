package edu.wm.cs.cs301.amazebycarolinefaparnan.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import edu.wm.cs.cs301.amazebycarolinefaparnan.R;
import edu.wm.cs.cs301.amazebycarolinefaparnan.falstad.MazeController;


/**
 * Class: AMazeActivity
 * Responsibility: Collect user input for maze generation and preferred driver method.
 * Collaborators: None, this is the main parent activity.
 *
 * @author Caroline Fagan and Aparna Nagaraj
 */


/**
 * Class: AMazeActivity
 * Responsibility: Collect user input for maze generation and preferred driver method.
 * Collaborators: None, this is the main parent activity.
 *
 * @author Caroline Fagan and Aparna Nagaraj
 */


public class AMazeActivity extends AppCompatActivity {

    SeekBar seekbar;
    Spinner spinnerAlg;
    Spinner spinnerRobot;
    Button explore;
    Button revisit;

    public static int globalProg;
    public static int genMethod;

    private ArrayAdapter<CharSequence> adapter;
    private ArrayAdapter<CharSequence> adapter2;

    TextView textSkill;
    int skillLevel;

    String load;
    String generate;

    /**
     * Creates the main activity screen with a seekbar, spinners, and buttons to select the skill level,
     * driver, generation algorithm, and explore/revisit option. Also adds listeners for each and switches
     * to the generating state when either explore or revisit is selected.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        globalProg = 0;
        GeneratingActivity.mazeConfig = null;

        setContentView(R.layout.activity_amaze);

        seekbar = (SeekBar) findViewById(R.id.seekBar);
        spinnerAlg = (Spinner) findViewById(R.id.spinner) ;
        spinnerRobot = (Spinner) findViewById(R.id.spinner2);
        textSkill = (TextView) findViewById(R.id.textView);



        // SeekBar listener updates the level selected for user to see
        seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            int skillReturn = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int skill, boolean b) {
                skillLevel = skill;
                skillReturn = skill;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                Toast.makeText(getApplicationContext(), "Level Selected: " + skillReturn, Toast.LENGTH_SHORT).show();
                Log.v("Title: level", "level selected");
                textSkill.setText("Level: " + skillReturn);
            }

        });

        // create spinner for robot selection
        adapter = ArrayAdapter.createFromResource(this, R.array.robot_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRobot.setAdapter(adapter);

//        spinnerRobot.setOnItemSelectedListener(new OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(adapterView.getContext(), adapterView.getItemAtPosition(i).toString(),Toast.LENGTH_SHORT).show();
//                Log.v("Title: robot spinner", "bot selected");
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                Toast.makeText(adapterView.getContext(), adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
//
//            }
//
//        });

        //create spinner for generation algorithm selection
        adapter2 = ArrayAdapter.createFromResource(this, R.array.alg, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAlg.setAdapter(adapter2);
//        spinnerAlg.setOnItemSelectedListener(new OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(adapterView.getContext(), adapterView.getItemAtPosition(i).toString(),Toast.LENGTH_SHORT).show();
//                Log.v("Title: alg spinner", "alg selected");
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                Toast.makeText(adapterView.getContext(), adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
//
//            }
//
//        });
        // create the revisit button and the listener to change to the generating screen
        revisit = (Button) findViewById(R.id.button);
        revisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Revisiting the last Maze",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(AMazeActivity.this,GeneratingActivity.class);
                i.putExtra("robot", String.valueOf(spinnerRobot.getSelectedItem()));
                i.putExtra("alg", String.valueOf(spinnerAlg.getSelectedItem()));
                i.putExtra("level", skillLevel);
                i.putExtra("method", load);
                startActivity(i);
                finish();
                Log.v("Title: revisit button", "load an old maze");

            }
        });

        // create the explore button and listener to switch to the generating screen when pressed
        explore = (Button) findViewById(R.id.button2);
        explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Building a new Maze to explore", Toast.LENGTH_SHORT).show();
                //Intent i = new Intent(view.getContext(),GeneratingActivity.class);
                Intent i = new Intent(AMazeActivity.this,GeneratingActivity.class);
                i.putExtra("robot", String.valueOf(spinnerRobot.getSelectedItem()));
                i.putExtra("alg", String.valueOf(spinnerAlg.getSelectedItem()));
                i.putExtra("level", skillLevel);
                i.putExtra("method", generate);
                startActivity(i);
                finish();
                Log.v("Title: explore button", "build a new maze");

            }
        });



    }}
