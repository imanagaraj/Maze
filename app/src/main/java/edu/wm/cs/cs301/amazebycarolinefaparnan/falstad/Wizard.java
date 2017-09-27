package edu.wm.cs.cs301.amazebycarolinefaparnan.falstad;



import android.os.Handler;

import edu.wm.cs.cs301.amazebycarolinefaparnan.falstad.Robot.Direction;
import edu.wm.cs.cs301.amazebycarolinefaparnan.falstad.Robot.Turn;
import edu.wm.cs.cs301.amazebycarolinefaparnan.generation.Distance;
import edu.wm.cs.cs301.amazebycarolinefaparnan.generation.CardinalDirection;
/**
 * Class : Wizard
 * 
 * Responsibilities: Drives the robot towards the exit using the Wizard driver given the exit exists, and that the robot has enough energy to finish the maze.
 * 
 * @return true if the driver reaches the exit
 * @throws Exception if the robot is stopped for some reason
 * 
 * Collaborators: Robot, Cells, MazeController, BasicRobot, MazeApplication, Distance
 * 
 * @author Caroline Fagan and Aparna Nagaraj
 */


public class Wizard implements RobotDriver{
	
	protected int[][] maze;
	protected BasicRobot robot;
	protected MazeController mC;
	protected Distance distance;
	protected int pathLength;
	Boolean notEnoughEnergy = false;
	public Boolean reachedExit = false;
	public Boolean paused = false;
	
	public Wizard(){
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
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		this.distance = distance;
		
	}
	@Override
	public boolean getToExit(){return reachedExit;}

	@Override
	public void paused(boolean bool){
		if (bool == true){
			paused = true;
		}
		else{paused = false;}

	}



	/**
	    * Wizard uses the wizard maze solving algorithm uses the MazeController.getNeighborClosestToExit method to find the exit.
	    * @return true if driver successfully reaches the exit, false otherwise
	    * @throws Exception if robot stopped due to some problem, e.g. lack of energy
	*/
	@Override
	public boolean drive2Exit() throws Exception {
		// Need to keep track of current position --> give current position to getNeighborClosestToExit
		robot.mC.mapMode = true;
		robot.mC.showSolution = true;
		robot.mC.showMaze = !robot.mC.showMaze;
		robot.mC.notifyViewerRedraw();

		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				//if(mC.isOutside(mC.px, mC.py) == false){
				if (paused == false) {
					if (robot.isAtExit() == false) {
						// energy
						if (robot.getBatteryLevel() > 0) {

							// Get current x and y positions
							int currX = 0;
							try {
								currX = robot.getCurrentPosition()[0];
							} catch (Exception e) {
								e.printStackTrace();
							}
							int currY = 0;
							try {
								currY = robot.getCurrentPosition()[1];
							} catch (Exception e) {
								e.printStackTrace();
							}

							int[] neighbor = robot.mC.mazeConfig.getNeighborCloserToExit(currX, currY);
							//CardinalDirection currdirection = robot.getCurrentDirection() ;

							// Find the distance between the current cell and the neighbor cell
							int xDist = currX - neighbor[0];
							int yDist = currY - neighbor[1];
							System.out.println("move forward");


							//Get the current direction of the robot and get the current direction of the neighbor
							CardinalDirection currDirection = robot.getCurrentDirection();
							CardinalDirection neighborDirection = CardinalDirection.getDirection(xDist, yDist);

							//Manually move the robot
							robot.currentPosition = neighbor; //current position from the basic robot is neighbor
							robot.mC.setCurrentPosition(neighbor[0], neighbor[1]);
							robot.setPathLength();
							mC.notifyViewerRedraw();
							try {
								drive2Exit();
							} catch (Exception e) {
								e.printStackTrace();
							}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
							//Use basic robot to move
							//Fix the direction the robot faces
							if (currDirection.oppositeDirection() == neighborDirection) {
								robot.rotate(Turn.AROUND);
								mC.notifyViewerRedraw();
								try {
									drive2Exit();
								} catch (Exception e) {
									e.printStackTrace();
								}
							} else if (currDirection.rotateClockwise() == neighborDirection) {
								robot.rotate(Turn.RIGHT);
								mC.notifyViewerRedraw();
								try {
									drive2Exit();
								} catch (Exception e) {
									e.printStackTrace();
								}
							} else if (currDirection.rotateCounterClockwise() == neighborDirection) {
								robot.rotate(Turn.LEFT);
								mC.notifyViewerRedraw();
								try {
									drive2Exit();
								} catch (Exception e) {
									e.printStackTrace();
								}
							}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

						} else {
							//if there is not enough energy or there is an obstacle
							notEnoughEnergy = true;
						}
					}

				}
			}
		},10);

		if (notEnoughEnergy == true){
			return false;
		}
		if(robot.isAtExit()){
			robot.mC.state = Constants.StateGUI.STATE_FINISH;
			reachedExit = true;
			robot.mC.notifyViewerRedraw();
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

	@Override
	public int getPathLength() {
		return this.pathLength;
	}

}
