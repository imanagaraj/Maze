package edu.wm.cs.cs301.amazebycarolinefaparnan.falstad;




import android.os.Handler;

import edu.wm.cs.cs301.amazebycarolinefaparnan.generation.Distance;
import edu.wm.cs.cs301.amazebycarolinefaparnan.falstad.Robot.Direction;
import edu.wm.cs.cs301.amazebycarolinefaparnan.falstad.Robot.Turn;

/**
 *  Class: Pledge
 *  
 *  Responsibilities: Pledge modifies the wall follower algorithm by following the right wall in a random direction as its main direction 
 *  when it hits an obstacle. It uses a counter that decrements (-1) when the robot turns left and increments (+1) when the robot turns right.
 *  When the counter gets to zero, it is able to leave an obstacle following its main direction again.
 *  
 *  @return true if the driver reaches the exit
 *  @throws Exception if the robot is stopped for some reason
 * 
 *  Collaborators: Robot, Cells, MazeController, BasicRobot, MazeApplication, Distance
 *  
 * @author Caroline Fagan and Aparna Nagaraj
 *
 */


public class Pledge implements RobotDriver{
	
	protected int[][] maze;
	protected BasicRobot robot;
	protected MazeController mC;
	protected Distance distance;
	protected int pathLength;
	Boolean notEnoughEnergy = false;
	public Boolean reachedExit = false;
	public Boolean paused = false;
	
	public Pledge(){
		this.mC = new MazeController();
		this.maze = null;
		this.robot = new BasicRobot();
		this.distance = null;
		setRobot(robot);
		pathLength = robot.getPathLength();
	}

	/**
   	 * Assigns a robot platform to the driver. 
   	 * The driver uses a robot to perform, this method provides it with this necessary information.
   	 * @param r robot to operate
   	 */
	@Override
	public void setRobot(Robot r) {
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
	public void setDimensions(int width, int height) {
		this.maze = new int[width][height];
	}

	/**
   	 * Provides the robot driver with information on the distance to the exit.
   	 * Only some drivers such as the wizard rely on this information to find the exit.
   	 * @param distance gives the length of path from current position to the exit.
   	 * @precondition null != distance, a full functional distance object for the current maze.
   	 */
	@Override
	public void setDistance(Distance distance) {
		this.distance = distance;
	}

	@Override
	public boolean getToExit(){
		return reachedExit;
	}

	@Override
	public void paused(boolean bool){
		if (bool == true) {
			;
			paused = true;
		}
		else{
			paused = false;
		}
	}

	/**
	 * Pledge uses the pledge maze solving algorithm to follow the wall on its
	 * right side with rotations and movements until it reaches the exit.
	 * @return true if driver successfully reaches the exit, false otherwise
	 * @throws Exception if robot stopped due to some problem, e.g. lack of energy
	 *
	 */
	@Override

	public boolean drive2Exit() throws Exception{
		
		robot.mC.mapMode = true;
		robot.mC.showSolution = true;
		robot.mC.showMaze = !robot.mC.showMaze;
		robot.mC.notifyViewerRedraw();

		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				if (paused == false) {
					if (!robot.canSeeExit(Direction.BACKWARD) || !robot.canSeeExit(Direction.FORWARD) || !robot.canSeeExit(Direction.LEFT) || !robot.canSeeExit(Direction.RIGHT)) {

						int counter = 0;
						if (robot.getBatteryLevel() > 0) {

							if (robot.distanceToObstacle(Direction.FORWARD) == 0 && robot.distanceToObstacle(Direction.LEFT) == 0) {
								robot.rotate(Turn.LEFT);
								counter -= 1;
								robot.move(1, false);
								try {
									drive2Exit();
								} catch (Exception e) {
									e.printStackTrace();
								}
							} else if (robot.distanceToObstacle(Direction.LEFT) > 0) {
								robot.rotate(Turn.RIGHT);
								counter += 1;
								robot.move(1, false);
								try {
									drive2Exit();
								} catch (Exception e) {
									e.printStackTrace();
								}
							} else {
								robot.move(1, false);
								try {
									drive2Exit();
								} catch (Exception e) {
									e.printStackTrace();
								}
							}


						} // is at exit
						else {
							// if there is not enough energy or there is an obstacle
							notEnoughEnergy = true;
						}
					}
				}
			}
		},100);


		int[] currPosition = robot.getCurrentPosition();
		if (robot.mC.mazeConfig.getMazecells().isExitPosition(currPosition[0], currPosition[1])) {
			robot.mC.state = Constants.StateGUI.STATE_FINISH;
			reachedExit = true;
			robot.mC.notifyViewerRedraw();
			return true;
		}
		if (notEnoughEnergy == true){
			return false;
		}
		return true;
	}

				
	


	/**
	*  Returns the total energy consumption of the operations
	*/
	@Override
	public float getEnergyConsumption() {
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
