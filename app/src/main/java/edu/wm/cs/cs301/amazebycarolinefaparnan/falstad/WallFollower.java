package edu.wm.cs.cs301.amazebycarolinefaparnan.falstad;

/**
  Class : WallFollower

 * 
 * Responsibilities: Drives the robot to the exit by following the left wall through the maze.
 * 
 * @return true if the driver reaches the exit
 * @throws exception if the robot is stopped for some reason
 * 
 * Collaborators: Robot, Cells, MazeController, BasicRobot, MazeApplication, Distance
 * 
 * @author Caroline Fagan and Aparna Nagaraj
 */


import android.os.Handler;
import android.util.Log;

import java.util.logging.LogRecord;

import edu.wm.cs.cs301.amazebycarolinefaparnan.falstad.Constants.StateGUI;
import edu.wm.cs.cs301.amazebycarolinefaparnan.falstad.Robot.Direction;
import edu.wm.cs.cs301.amazebycarolinefaparnan.falstad.Robot.Turn;
import edu.wm.cs.cs301.amazebycarolinefaparnan.generation.Distance;
import edu.wm.cs.cs301.amazebycarolinefaparnan.ui.PlayActivity;


public class WallFollower implements RobotDriver {

		protected int[][] maze;
		protected BasicRobot robot;
		protected Distance distance;
//		public MazeApplication mazeApp;
//		public SimpleKeyListener robotListener;
		protected MazeController mC;
		protected int pathLength;
		Boolean notEnoughEnergy = false;
	public Boolean reachedExit = false;
	public Boolean paused = false;

		public WallFollower(){
			//init();
			this.maze = null;
			this.robot = new BasicRobot();
			this.distance = null;
			this.mC = new MazeController();
			setRobot(robot);
			pathLength = robot.getPathLength();
			//mC.getRobot();
		}
		

			
		/**
	   	 * Assigns a robot platform to the driver. 
	   	 * The driver uses a robot to perform, this method provides it with this necessary information.
	   	 * @param r robot to operate
	   	 */
		@Override
		public void setRobot(Robot r){
			System.out.println("setRobot visited");
			robot = (BasicRobot) r;
			//robot.setMaze(this.mC);
		}
		
		
		/**
	   	 * Provides the robot driver with information on the dimensions of the 2D maze
	   	 * measured in the number of cells in each direction.
	   	 * @param width of the maze
	   	 * @param height of the maze
	   	 * @precondition 0 <= width, 0 <= height of the maze. 
	   	 */

		@Override
		public void setDimensions(int width, int height){
			this.maze = new int[width][height];
		}
		
		/**
	   	 * Provides the robot driver with information on the distance to the exit.
	   	 * Only some drivers such as the wizard rely on this information to find the exit.
	   	 * @param distance gives the length of path from current position to the exit.
	   	 * @precondition null != distance, a full functional distance object for the current maze.
	   	 */
		@Override
		public void setDistance(Distance distance){
			this.distance = distance;
		}
	@Override
	public boolean getToExit(){
		return reachedExit;
	}

	@Override
	public void paused(boolean bool){
		if (bool == true){
			paused = true;
		}
		else{paused = false;}

	}

		
		/**
	    * Wall Follower uses the wall follower maze solving algorithm to follow the wall on its
	    * left side until it reaches the exit
	    * @return true if driver successfully reaches the exit, false otherwise
	    * @throws Exception if robot stopped due to some problem, e.g. lack of energy            */
		@Override
		public boolean drive2Exit() throws Exception{
			
//			robot.mC.mapMode = true;
//			robot.mC.showSolution = true;
//			robot.mC.showMaze = !robot.mC.showMaze;
//			robot.mC.notifyViewerRedraw();
			//Boolean notEnoughEnergy = false;

			Log.v("Wallfollower", "drive2Exit Wallfollower");

		Handler handler = new Handler();
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					if (paused == false) {
						if (!robot.canSeeExit(Direction.BACKWARD) || !robot.canSeeExit(Direction.FORWARD) || !robot.canSeeExit(Direction.LEFT) || !robot.canSeeExit(Direction.RIGHT)) {
							if (robot.getBatteryLevel() > 0) {
								System.out.println("Battery is greater  than 0");

								if (robot.distanceToObstacle(Direction.FORWARD) == 0 && robot.distanceToObstacle(Direction.RIGHT) == 0) {
									robot.rotate(Turn.RIGHT);
									robot.move(1, false);
									mC.notifyViewerRedraw();
								}

								else if (robot.distanceToObstacle(Direction.RIGHT) > 0) {
									robot.rotate(Turn.LEFT);
									robot.move(1, false);
									mC.notifyViewerRedraw();
								}

								else {
									robot.move(1, false);
									mC.notifyViewerRedraw();
								}


								if(robot.isAtExit() == true){
									System.out.println("REACHED THE EXIT IN WALLFOLLOWER");
									robot.mC.state = Constants.StateGUI.STATE_FINISH;
									reachedExit = true;
									robot.mC.notifyViewerRedraw();
								}
								try {
									drive2Exit();
								} catch (Exception e) {
									e.printStackTrace();
								}
							} else {
								// Energy level is not high enough
								notEnoughEnergy = true;
							}
						}
					}
				}
			},10);

			if (reachedExit == true){
				return true;
			}

			if(notEnoughEnergy==true){
				return false;
			}
			return true;
		}
			

		
		/**
	    *  Returns the total energy consumption of the operations
	    */
		@Override
		public float getEnergyConsumption(){
			return 2500 - robot.getBatteryLevel();
		}
		
	    /**
	    *Returns the total length of the path based on the number of cells traversed with the initial cell being 0.
	    */
		@Override
		public int getPathLength() {
			return this.pathLength;
		}
	}

	

