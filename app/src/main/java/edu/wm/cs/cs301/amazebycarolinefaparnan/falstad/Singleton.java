package edu.wm.cs.cs301.amazebycarolinefaparnan.falstad;

import android.app.Application;
import edu.wm.cs.cs301.amazebycarolinefaparnan.falstad.MazeController;
import edu.wm.cs.cs301.amazebycarolinefaparnan.generation.MazeConfiguration;

/**
 * Created by Caroline and Aparna on 11/20/2016.
 */
public class Singleton  {

    private static  Singleton instance;
    private MazeController maze;
    private MazeConfiguration mazeConfig;
    public static Boolean DRIVING = false;
    public static Singleton Instance(){
        if(instance == null){
            instance = new Singleton();
        }
        return instance;
    }
    public MazeController getMazeController() {
        return maze;
    }
    public MazeController setMazeController(MazeController mc){return maze = mc;}

    public MazeConfiguration getMazeConfiguration(){
        return mazeConfig;
    }

    public void setMazeConfiguration(MazeConfiguration mCon){mazeConfig = mCon;}

}
