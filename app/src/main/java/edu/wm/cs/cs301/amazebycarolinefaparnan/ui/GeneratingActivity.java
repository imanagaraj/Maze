package edu.wm.cs.cs301.amazebycarolinefaparnan.ui;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import edu.wm.cs.cs301.amazebycarolinefaparnan.R;
import edu.wm.cs.cs301.amazebycarolinefaparnan.falstad.MazePanel;
import edu.wm.cs.cs301.amazebycarolinefaparnan.falstad.MazeController;
import edu.wm.cs.cs301.amazebycarolinefaparnan.falstad.ManualDriver;
import edu.wm.cs.cs301.amazebycarolinefaparnan.falstad.Pledge;
import edu.wm.cs.cs301.amazebycarolinefaparnan.falstad.RobotDriver;
import edu.wm.cs.cs301.amazebycarolinefaparnan.falstad.WallFollower;
import edu.wm.cs.cs301.amazebycarolinefaparnan.falstad.Wizard;
import edu.wm.cs.cs301.amazebycarolinefaparnan.generation.MazeConfiguration;
import edu.wm.cs.cs301.amazebycarolinefaparnan.generation.MazeFactory;
import edu.wm.cs.cs301.amazebycarolinefaparnan.generation.Order;
import edu.wm.cs.cs301.amazebycarolinefaparnan.falstad.Singleton;


import android.widget.ProgressBar;
import android.widget.TextView;
import android.util.Log;
import android.os.Handler;

/**
 * Class: GeneratingActivity
 * Responsibility: Show this screen while the maze is being generated and display the generation progress on screen
 * Collaborators: AMazeActivity
 *
 * @author Caroline Fagan and Aparna Nagaraj
 */


public class GeneratingActivity extends AppCompatActivity {

    private ProgressBar progress;
    private int progressVal = 0;
    private TextView percentage;
    private boolean increasing;
    public static Handler handlerProg;
    /*{
        @Override
        public void handleMessage(Message msg){
            switch(msg.what){

            }
        }

    };*/
    private int status;
    //private int percentage;

    public String algorithm;
    public int level;
    public String robot;

    public Singleton singleton;

    public static MazeConfiguration mazeConfig;
    //
    // Context context = getApplicationContext();
    public static MazeController controller;
    RobotDriver driver;
    MazeFactory factory;
    public static MazePanel wrapper;

    /**
     * Creates the progress bar to inform the user of how much of the maze has been generated
     * and switches to the generating screen when finished.
     * @param circle
     */
    @Override
    protected void onCreate(Bundle circle) {
        super.onCreate(circle);
        setContentView(R.layout.activity_generating);

        progress = (ProgressBar) findViewById(R.id.generatingprog);
        assert progress != null;
        percentage = (TextView) findViewById(R.id.percentage);
        percentage.setText("Progress: ");

        status = 0;

        controller = new MazeController();
        mazeConfig = controller.getMazeConfiguration();
//        wrapper = new GraphicsWrapper(this);
//        controller.setGraphics(wrapper);
        System.out.print("GENACT PANEL");
        //controller.init();

        Intent past = getIntent();
        final String ALGORITHM = past.getStringExtra("alg");

        //set generation algorithm in maze controller
        //setAlg(ALGORITHM);

        final String DRIVER = past.getStringExtra("robot");
        //set robot driver algorithm in maze controller;
        //setDriver(DRIVER);

        final Integer SKILL = past.getIntExtra("level", 0);
        //set skill level in maze controller;
        //setSkill(SKILL);

        setMaze(ALGORITHM, DRIVER, SKILL);

        singleton.Instance().setMazeController(controller);
        singleton.Instance().setMazeConfiguration(mazeConfig);

        final Intent toPlay = new Intent(this, PlayActivity.class);
        toPlay.putExtra("alg", ALGORITHM);
        toPlay.putExtra("robot", DRIVER);
        toPlay.putExtra("level", SKILL);
        //toPlay.putExtra("mazeControler", controller);


        handlerProg = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what < 100){
                    progress.setProgress(msg.what);
                    percentage.setText("Progress: " + msg.what + "% completed");
                }

                else{
                    startActivity(toPlay);
                }
            }
        };}

    public void setMaze(String alg, String driver, int skill){
        setAlg(alg);
        setDriver(driver);
        setSkill(skill);
        Log.v("algorithm", alg);
        Log.v("driver", driver);
        Log.v("skill", String.valueOf(skill));


        controller.setPerfect(false);

        controller.mFactory.order(this.controller);

    }

    public void setAlg(String alg){
        if(alg.equals("Eller")){
            controller.setBuilder(Order.Builder.Eller);
        }
        else if(alg.equals("Default")){
            controller.setBuilder(Order.Builder.DFS);
        }
        else if(alg.equals("Prim")){
            controller.setBuilder(Order.Builder.Prim);
        }
    }

    public void setDriver(String driver){
        if(driver.equals("Manual Driver")){
            RobotDriver manual = new ManualDriver();
            controller.updateDriver(manual);
        }
        else if(driver.equals("Wall Follower")){
            RobotDriver wallfollower = new WallFollower();
            controller.updateDriver(wallfollower);
        }
        else if(driver.equals("Wizard")){
            RobotDriver wizard = new Wizard();
            controller.updateDriver(wizard);
        }

        else if(driver.equals("Pledge")){
            RobotDriver pledge = new Pledge();
            controller.updateDriver(pledge);
        }
    }

    public void setSkill(int skill){
        controller.setSkillLevel(skill);
    }


    /**
     * Return the user to AMazeActivity
     */
    @Override
    public void onBackPressed(){
        this.startActivity(new Intent(GeneratingActivity.this, AMazeActivity.class));

    }
}
