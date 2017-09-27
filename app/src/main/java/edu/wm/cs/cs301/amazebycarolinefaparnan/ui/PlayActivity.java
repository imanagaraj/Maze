package edu.wm.cs.cs301.amazebycarolinefaparnan.ui;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.ToggleButton;
import android.widget.Toast;
import android.util.Log;
import android.os.Handler;


import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;

import edu.wm.cs.cs301.amazebycarolinefaparnan.R;
import edu.wm.cs.cs301.amazebycarolinefaparnan.falstad.Constants;
import edu.wm.cs.cs301.amazebycarolinefaparnan.falstad.MazePanel;
import edu.wm.cs.cs301.amazebycarolinefaparnan.falstad.MazeController;
import edu.wm.cs.cs301.amazebycarolinefaparnan.falstad.RobotDriver;
import edu.wm.cs.cs301.amazebycarolinefaparnan.falstad.Singleton;
import edu.wm.cs.cs301.amazebycarolinefaparnan.generation.MazeConfiguration;

/**
 * Class: PlayActivity
 * Responsibility: Show the user the maze and provide controls to navigate with a manual driver or
 * pause/continue with an automatic driver. Give the options of showing a maze preview, solution, and walls.
 * Collaborators: GeneratingActivity
 *
 * @author Caroline Fagan and Aparna Nagaraj
 */

/**
 * Class: PlayActivity
 * Responsibility: Show the user the maze and provide controls to navigate with a manual driver or
 * pause/continue with an automatic driver. Give the options of showing a maze preview, solution, and walls.
 * Collaborators: GeneratingActivity
 *
 * @author Caroline Fagan and Aparna Nagaraj
 */

public class PlayActivity extends AppCompatActivity {

    Button start;
    public Button pause;
    ImageButton up;
    ImageButton down;
    ImageButton right;
    ImageButton left;
    ToggleButton showMaze;
    ToggleButton showSolution;
    ToggleButton showWalls;
    //GraphicsWrapper panel;
    MazeConfiguration mazeConfig;
    public static ProgressBar energy;
    RobotDriver robotDriver;
    public static int path_length;
    public static boolean solved;
    MediaPlayer mp;

    Singleton singleton;
    public static MazePanel view;
    // Button toFin;

    Handler runHandle = new Handler();



    /**
     * update the robot's energy
     */
//    static Handler progressHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            energy.setProgress(100);
//            //change this to update from maze controller with energy consumed
//        }
//    };

    /**
     * Create the toggle buttons, navigation buttons, and progress bar. Toggle buttons turn hints on
     * and off. Navigation buttons allow the user to manually navigate the maze. Switches to the
     * finish screen when the robot runs out of energy or the maze is finished.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        mp = MediaPlayer.create(getApplicationContext(), R.raw.pina_colada_song);
        //mp.reset();
        //mp.prepare();
        mp.start();

        energy = (ProgressBar) findViewById(R.id.energyBar);
        energy.setMax(2500);
        energy.setProgress(energy.getMax());


        final MazeController mazeController = singleton.Instance().getMazeController();

        robotDriver = mazeController.getDriver();

        mazeConfig = singleton.Instance().getMazeConfiguration();

        view = (MazePanel) findViewById(R.id.graphicsWrapper);

        mazeController.setGraphics(view);
        mazeController.setState(Constants.StateGUI.STATE_PLAY);
        mazeController.notifyViewerRedraw();


        //mazeController.deliver(mazeConfig);

        if (mazeController.getPanel() != null) {
            mazeController.getPanel().invalidate();
            //view.invalidate();
            System.out.println("panel exists");
        }

        //  final Bundle robot = getIntent().getExtras().getBundle("robot");
        Intent genIntent = getIntent();
        String robotVal = genIntent.getStringExtra("robot");
        String algorithm = genIntent.getStringExtra("alg");
        //   robot.getString("robot");

        // pause button and listener to pause automatic driver
        pause = (Button) findViewById(R.id.pause);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Pausing", Toast.LENGTH_SHORT).show();
                Log.v("Title: pause button", "pausing driver");
                robotDriver.paused(true);
                // add click listener items to pause the maze when drivers are integrated
            }
        });

        //start button and listener to start automatic listener
        start = (Button) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "Starting", Toast.LENGTH_SHORT).show();
                Log.v("Title: start button", "starting/resuming driver");

                robotDriver.paused(false);
                mazeController.setState(Constants.StateGUI.STATE_PLAY);

                if(robotDriver.getToExit() == false){
                    try {
                        robotDriver.drive2Exit();
                    } catch (Exception e) {
                        Log.v("exception", "not driving to exit");
                        e.printStackTrace();
                    }
                }
                else{
                    Log.v("outside", "outside maze in automated");
                    Intent toFinish = new Intent(PlayActivity.this, FinishActivity.class);
                    startActivity(toFinish);
                    mp.release();
                    finish();
                }
//                try {
//                    robotDriver.drive2Exit();
//                } catch (Exception e) {
//                    Log.v("exception", "not driving to exit");
//                    e.printStackTrace();
//                }
//                if(robotDriver.getToExit()){
//                    Intent toFinish = new Intent(PlayActivity.this, FinishActivity.class);
//                    startActivity(toFinish);
//                    mp.release();
//                    finish();
//                }



//                runHandle.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        try{
//                            mazeController.setState(Constants.StateGUI.STATE_PLAY);
//                            robotDriver.drive2Exit();
//
////                    new Thread(new Runnable() {
////                        @Override
////                        public void run() {
////                            try {
////                                robotDriver.drive2Exit();
////                                Thread.sleep(2000);
////                            } catch (Exception e) {
////                                e.printStackTrace();
////                            }
////                        }
////                    }).start();

                // }
//                        catch(Exception e){
//                            e.printStackTrace();
//                        }
//                    }
//                });

//                try{
//                    mazeController.setState(Constants.StateGUI.STATE_PLAY);
//                    robotDriver.drive2Exit();
//
////                    new Thread(new Runnable() {
////                        @Override
////                        public void run() {
////                            try {
////                                robotDriver.drive2Exit();
////                                Thread.sleep(2000);
////                            } catch (Exception e) {
////                                e.printStackTrace();
////                            }
////                        }
////                    }).start();
//
//                }
//                catch(Exception e){
//                    e.printStackTrace();
//                }

            }
        });

        // up button and listener to manually driver robot forward
        up = (ImageButton) findViewById(R.id.forward);
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("Title: up arrow", "moved forward");

                //set the up arrow equal to the key down method for up in the maze
                if (energy.getProgress() >= 5){
                    mazeController.keyDown('8');
                    energy.setProgress(energy.getProgress() - 5);
                    path_length += 1;
                }
                else{
                    Log.v("Title: No Energy", "Out of energy, going to finish screen");
                    Intent toFinish = new Intent(PlayActivity.this, FinishActivity.class);
                    solved = false;
                    startActivity(toFinish);
                    mp.release();
                    finish();
                }

                if (mazeController.isOutside(mazeController.px, mazeController.py)) {
                    Log.v("Title: Finished", "Outside of the maze, switching to finish screen.");
                    System.out.println("is outside the maze");
                    Intent toFinish = new Intent(PlayActivity.this, FinishActivity.class);
                    solved = true;
                    startActivity(toFinish);
                    mp.release();
                    finish();
                }
            }
        });

        //down button and listener to manually turn the robot around
        down = (ImageButton) findViewById(R.id.down);
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("Title: down arrow", "turn around"); // ?????
                //set the up arrow equal to the key down method for down in the maze

                if (energy.getProgress() >= 6){
                    mazeController.keyDown('4');
                    mazeController.keyDown('4');
                    energy.setProgress(energy.getProgress() - 6);
                }
                else{
                    Log.v("Title: ", "Out of energy, going to finish screen");
                    solved = false;
                    Intent toFinish = new Intent(PlayActivity.this, FinishActivity.class);
                    startActivity(toFinish);
                    mp.release();
                    finish();
                }
            }
        });

        // right button and listener to manually turn the robot right
        right = (ImageButton) findViewById(R.id.right);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("Title: right arrow", "turn right");
                //set the up arrow equal to the key down method for right in the maze
                //GeneratingActivity.controller.keyDown(6);

                if (energy.getProgress() >= 3){
                    mazeController.keyDown('6');
                    energy.setProgress(energy.getProgress() - 3);
                }
                else{
                    Log.v("Title: ", "Out of energy, going to finish screen");
                    solved = false;
                    Intent toFinish = new Intent(PlayActivity.this, FinishActivity.class);
                    startActivity(toFinish);
                    mp.release();
                    finish();
                }



            }
        });

        //left button and listener to manually turn the robot left
        left = (ImageButton) findViewById(R.id.left);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("Title: left arrow", "turn left");
                //set the up arrow equal to the key down method for left in the maze
                //GeneratingActivity.controller.keyDown('4');
                if (energy.getProgress() >= 3){
                    mazeController.keyDown('4');
                    energy.setProgress(energy.getProgress() - 3);
                }
                else{
                    Log.v("Title: ", "Out of energy, going to finish screen");
                    solved = false;
                    Intent toFinish = new Intent(PlayActivity.this, FinishActivity.class);
                    startActivity(toFinish);
                    mp.release();
                    finish();
                }
            }
        });

        //show the maze preview if the user selects
        showMaze = (ToggleButton) findViewById(R.id.showMaze);
        showMaze.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isSelected) {
                if (isSelected) {
                    Log.v("Title: show maze on", "show maze preview");
                    // turn preview to true in maze application
                    mazeController.keyDown('m');
                    mazeController.keyDown('z');
                    //mazeController.showMaze = true;
                    //mazeController.notifyViewerRedraw();
                } else {
                    mazeController.keyDown('z');

                    //mazeController.showMaze = false;
                    //mazeController.notifyViewerRedraw();

                }
            }
        });

        //show the solution to the maze if the user selects
        showSolution = (ToggleButton) findViewById(R.id.showSolution);
        showSolution.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isSelected) {
                if (isSelected) {
                    Log.v("Title: show solution on", "show solution ");
                    // turn solution to true in maze application
                    mazeController.keyDown('m');
                    mazeController.keyDown('s');
                    //mazeController.showSolution = true;
                    //mazeController.notifyViewerRedraw();
                } else {
                    mazeController.keyDown('s');

                    //mazeController.showSolution = false;
                    //mazeController.notifyViewerRedraw();

                }
            }
        });

        //show the maze walls in the preview if the user selects
        showWalls = (ToggleButton) findViewById(R.id.showWall);
        showWalls.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isSelected) {
                if (isSelected) {
                    Log.v("Title: show walls on", "show walls ");
                    // turn show walls to true in maze application
                    //mazeController.keyDown('m');
                    mazeController.mapMode = true;
                    mazeController.notifyViewerRedraw();

                } else {
//                    mazeController.keyDown('m');

                    mazeController.mapMode = false;
                    mazeController.notifyViewerRedraw();


                }
            }
        });

        // show the appropriate buttons based on the driver type
        if (robotVal.equals("Manual Driver")) {
            up.setVisibility(up.VISIBLE);
            down.setVisibility(down.VISIBLE);
            right.setVisibility(right.VISIBLE);
            left.setVisibility(left.VISIBLE);
        } else {
            start.setVisibility(start.VISIBLE);
            pause.setVisibility(pause.VISIBLE);

        }
    }

    /**
     * Return the user to AMazeActivity
     */
    @Override
    public void onBackPressed() {
        mp.release();
        this.startActivity(new Intent(PlayActivity.this, AMazeActivity.class));

    }


}
