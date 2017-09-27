package edu.wm.cs.cs301.amazebycarolinefaparnan.falstad;

import edu.wm.cs.cs301.amazebycarolinefaparnan.falstad.Constants.StateGUI;
import edu.wm.cs.cs301.amazebycarolinefaparnan.generation.Distance;
import edu.wm.cs.cs301.amazebycarolinefaparnan.generation.Order;

import edu.wm.cs.cs301.amazebycarolinefaparnan.falstad.Constants.StateGUI;
import edu.wm.cs.cs301.amazebycarolinefaparnan.falstad.Robot.Direction;
import edu.wm.cs.cs301.amazebycarolinefaparnan.falstad.Robot.Turn;


public class ManualDriver implements RobotDriver{
	
	protected int[][] maze;
	protected BasicRobot robot;
	protected Distance distance;
	//public MazeApplication mazeApp;
	//public SimpleKeyListener robotListener;
	protected MazeController mC;
	protected int pathLength;
	public boolean reachedExit;
	public boolean paused = false;

	public ManualDriver(){
		this.maze = null;
		robot  = new BasicRobot();
		this.distance = null;
		// this.mC = robot.mC;
		//this.mC = new MazeController();		 
//		setRobot(robot);
//		pathLength = robot.getPathLength();
		//mC.getRobot();
	}
	 public ManualDriver(MazeController controller){
		 this.mC = controller;
	 }
	@Override
	public void paused(boolean bool){
		if (bool == true){
			paused = true;
		}
		else{paused = false;}

	}
	/**
     * Defining the different robot events for the arrow keys
     * @param key
     */


	public void keyDown(int key){
		
		switch(key){
			case 'k': case '8':
			robot.move(1, true);
			pathLength += 1;
			break;
			case 'j': case '2':
			robot.rotate(Turn.AROUND);
			//pathLength += 1;
			break;
		 case 'h': case '6':
			robot.rotate(Turn.LEFT);
			//pathLength += 1;
			break;
		 case 'l': case '4':
			robot.rotate(Turn.RIGHT);
			//pathLength += 1;
			break;
		}
		
		if(robot.isAtExit() == true){
			((BasicRobot) robot).mC.state = Constants.StateGUI.STATE_FINISH;
			((BasicRobot) robot).mC.notifyViewerRedraw();
			
		}
	
		}
	@Override
	public boolean getToExit(){
		return reachedExit;
	}
		
	/**
   	 * Assigns a robot platform to the driver. 
   	 * The driver uses a robot to perform, this method provides it with this necessary information.
   	 * @param r robot to operate
   	 */
	@Override
	public void setRobot(Robot r){
		robot = (BasicRobot)(r);
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
	
	/**
     * returns false in all cases for now since the robot is being operated manually
     * will drive the robot towards the exit given that there is enough energy and the exit is reachable
     * @return true if driver successfully reaches the exit, false otherwise
     * @throws exception if robot stopped due to some problem, e.g. lack of energy            */
	@Override
	public boolean drive2Exit() throws Exception{
		
		robot.mC.mapMode = true;
		robot.mC.showSolution = true;
		robot.mC.showMaze = !robot.mC.showMaze;
		robot.mC.notifyViewerRedraw();
		
		// return false;
		
			return false;
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
