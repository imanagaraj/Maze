package edu.wm.cs.cs301.amazebycarolinefaparnan.falstad;




import edu.wm.cs.cs301.amazebycarolinefaparnan.falstad.Constants.StateGUI;
import edu.wm.cs.cs301.amazebycarolinefaparnan.falstad.Robot.Direction;
import edu.wm.cs.cs301.amazebycarolinefaparnan.falstad.Robot.Turn;
import edu.wm.cs.cs301.amazebycarolinefaparnan.generation.Distance;
import edu.wm.cs.cs301.amazebycarolinefaparnan.generation.Cells;

/**
 * Class: Explorer
 * 
 * Responsibilities: Drives the robot through the maze by choosing a path that has not yet been visited.
 * After a cell has been visited, it is marked as visited.
 * 
 * Collaborators: Robot, Cells, MazeController, BasicRobot, MazeApplication, Distance
 * 
 * @author Caroline Fagan and Aparna Nagaraj
 *
 */


public class Explorer implements RobotDriver {
	
	protected int[][] maze;
	protected BasicRobot robot;
	protected Distance distance;
	//public MazeApplication mazeApp;
	//public SimpleKeyListener robotListener;
	protected MazeController mC;
	protected int pathLength;
	public boolean reachedExit;
	
	public Explorer(){
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
		robot = (BasicRobot) r;
		robot.setMaze(this.mC);
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
	public void paused(boolean bool){}


	@Override
	public boolean drive2Exit() throws Exception {
		
		while (!robot.canSeeExit(Direction.BACKWARD)||!robot.canSeeExit(Direction.FORWARD)||!robot.canSeeExit(Direction.LEFT)||!robot.canSeeExit(Direction.RIGHT)){
			if (robot.getBatteryLevel() > 0){
				while (robot.distanceToObstacle(Direction.FORWARD) > 0){
					robot.move(1, false);
					mC.getMazeConfiguration().getMazecells().setCellAsVisited(robot.getCurrentPosition()[0], robot.getCurrentPosition()[1]);
				}
				if (robot.distanceToObstacle(Direction.LEFT) > 0 && mC.getMazeConfiguration().getMazecells().isFirstVisit(robot.getCurrentPosition()[0] - 1,robot.getCurrentPosition()[1]) == true){
					robot.rotate(Turn.LEFT);
				}
				else if (robot.distanceToObstacle(Direction.LEFT) > 0 && mC.getMazeConfiguration().getMazecells().isFirstVisit(robot.getCurrentPosition()[0],robot.getCurrentPosition()[1] + 1) == true){
					robot.rotate(Turn.RIGHT);
				}
				else{
					return false;
				}
			}}
		if(robot.isAtExit()){
			robot.mC.state = Constants.StateGUI.STATE_FINISH;
			robot.mC.notifyViewerRedraw();
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