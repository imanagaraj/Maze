package edu.wm.cs.cs301.amazebycarolinefaparnan.falstad;

/**
 * Created by Caroline and Aparna on 11/17/16.

 */


import edu.wm.cs.cs301.amazebycarolinefaparnan.generation.CardinalDirection;
import edu.wm.cs.cs301.amazebycarolinefaparnan.generation.Cells;
import edu.wm.cs.cs301.amazebycarolinefaparnan.generation.MazeConfiguration;
import edu.wm.cs.cs301.amazebycarolinefaparnan.falstad.Robot.Turn;
import edu.wm.cs.cs301.amazebycarolinefaparnan.falstad.Robot.Direction;
import edu.wm.cs.cs301.amazebycarolinefaparnan.ui.PlayActivity;


public class BasicRobot implements Robot {
    /**
     *
     * Class: Basic Robot
     *
     * Implements the Robot class, which rotates and moves a robot inside of a maze. It also looks ahead to where it can go, manages energy levels,
     * and recognizes where it is in the maze (inside room, etc.).
     *
     * Collaborators: Robot, Cells, MazeController
     *
     * @author Aparna Nagaraj and Caroline Fagan
     *
     */


        protected int[] currentPosition ;
        protected int[] currentDirection ;
        protected CardinalDirection direction ;
        protected MazeController mC;
        protected Cells cells;
        protected boolean hasStopped ;
        protected int dx;
        protected int dy;
        protected static float energy ;
        protected static int pathLength = 0;

        public boolean LSensor = true;
        public boolean RSensor = true;
        public boolean FSensor = true;
        public boolean BSensor = true;

        protected MazeConfiguration mazeConfig;

        /**
         * Constructs a BasicRobot
         */

        public BasicRobot() {
            this.currentPosition = new int[2] ;
            this.currentPosition[0] = 0 ;
            this.currentPosition[1] = 0 ;
            energy = 2500 ;
            this.hasStopped = false ;
            pathLength = getPathLength();
            dx = 1;
            dy = 1;
            this.LSensor = true;
            this.RSensor = true;
            this.FSensor = true;
            this.BSensor = true;


        }

        /**
         * Rotates the robot 90 degrees right or left or 180 degrees (around) using type of turn as the parameter.
         */
        @Override
        public void rotate(Turn turn){

            switch(turn){
                case LEFT:
                    if(energy >= 3){
                        mC.keyDown('4');
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        energy -= 3 ;
                        this.setBatteryLevel(energy);
                        PlayActivity.energy.setProgress((int)energy);

                        System.out.println("rotated left");
                    }
                    else{
                        this.hasStopped = true;
                    }
                    break;

                case RIGHT:
                    if(energy >= 3){
                        // Make the robot face 90 degrees right and decrement energy

                        mC.keyDown('6');

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        energy -= 3 ;
                        this.setBatteryLevel(energy);
                        PlayActivity.energy.setProgress((int)energy);

                        System.out.println("rotated right");

                    }
                    else{
                        this.hasStopped = true;
                    }
                    break;

                case AROUND:

                    if(energy >= 6){
                        // Make the robot face the opposite direction (180 degrees) and decrement energy
//                        mC.keyDown(Event.LEFT);
//                        mC.keyDown(Event.LEFT);
                        energy -= 6;
                        this.setBatteryLevel(energy);
                        PlayActivity.energy.setProgress((int)energy);
                        System.out.println("rotated around");
                    }
                    else{
                        this.hasStopped = true;
                    }
                    break;
            }
        }


        /**
         * Moves the robot a given distance, if possible. Uses either manual or automatic driving.
         */
        @Override
        public void move(int distance, boolean manual){

            if(manual == true){


                int j = 1;
                while(hasStopped() == false && energy >= 5 && j <= distance){

                    mC.keyDown('8');
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    energy = energy  - 5;
                    PlayActivity.energy.setProgress((int)energy);
                    j += 1;
                    setPathLength();
                    //pathLength += 1;
                    System.out.println("move forward");

                    //mC.notifyViewerRedraw();
                }


            }
            if(manual == false){
                int j =1;
                while(hasStopped() == false && energy >= 5 && j <= distance){
                    mC.keyDown('8');
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    energy = energy -5;
                    PlayActivity.energy.setProgress((int)energy);

                    j += 1;
                    setPathLength();
                    //pathLength += 1;
                    System.out.println("move forward");
                }
            }
        }

        /**
         * Returns the current position of the robot.
         */
        @Override
        public int[] getCurrentPosition() throws Exception{
            return currentPosition;
        }

        /**
         * Sets the maze for the robot by initializing necessary variables.
         */
        @Override
        public void setMaze(MazeController maze){
            System.out.println("Maze is set");
            this.mC = maze;
            this.currentPosition = this.mC.getCurrentPosition();
        }

        /**
         * Tells if the robot is at the exit position of the maze.
         * @returns true if at the exit
         * @returns false if not at the exit
         */
        @Override
        public boolean isAtExit() {

            return this.mC.mazeConfig.getMazecells().isExitPosition(this.currentPosition[0], this.currentPosition[1]);
        }

        /**
         * Tells if the robot is at a position where the exit is within sight, i.e. in the same row/column as the robot with no walls in the way.
         * @returns true if the robot can see the exit
         * @returns false if the robot cannot see the exit
         */
        @Override
        public boolean canSeeExit(Direction direction) throws UnsupportedOperationException {

            if (this.hasDistanceSensor(direction) == true){
                // If the distance that the robot can move forward is infinite (i.e. distance = Integer.MaxValue), then the exit is in
                // view.
                int obstacleDistance = this.distanceToObstacle(direction) ;
                if (obstacleDistance == Integer.MAX_VALUE){
                    return true;
                }
                else{
                    return false;
                }
            }
            else{
                throw new UnsupportedOperationException() ;
            }

        }

        /**
         * Tells if the robot is currently inside of a room.
         * @returns true if inside a room
         * @returns false if not inside a room
         */
        @Override
        public boolean isInsideRoom() throws UnsupportedOperationException {
            // Use isInRoom method from cells to determine if the robot is inside a room
            return this.mC.mazeConfig.getMazecells().isInRoom(this.currentPosition[0], this.currentPosition[1]);
        }

        /**
         * Tells if the robot has a room sensor
         */
        @Override
        public boolean hasRoomSensor() {
            return true;
        }

        /**
         * Tells the current direction the that the robot is facing
         * @returns current direction of the robot
         */
        @Override
        public CardinalDirection getCurrentDirection() {
            // Use MazeController's getCurrentDirection method to get the current direction of the robot
            return mC.getCurrentDirection();
        }

        public int[] getCurrentDirection2(){
            int[] currentDirection = {this.mC.dx, this.mC.dy};
            return currentDirection;
        }
        /**
         * @returns the energy level of the robot
         */
        @Override
        public float getBatteryLevel() {
            return energy;
        }
        /**
         * Sets the energy level to a given number
         */
        @Override
        public void setBatteryLevel(float level){
            energy = level;
        }

        /**
         * Tells the amount of energy for a robot to complete a full rotation.
         * @returns 12
         */
        @Override
        public float getEnergyForFullRotation() {
            // Energy to rotate 90 degrees = 3
            // Energy to rotate 360 degrees = 3*4 = 12
            return 12;
        }

        /**
         * Tells the amount of energy for a robot to move one position.
         * @returns 5
         */
        @Override
        public float getEnergyForStepForward() {
            // Energy to move one step is 5
            return 5;
        }

        /**
         * Tells if the robot has stopped
         */
        @Override
        public boolean hasStopped() {
            return this.hasStopped;
        }

        /**
         * Tells how far away the robot is from an obstacle in the given direction.
         */



        @Override
        public int distanceToObstacle(Direction direction) throws UnsupportedOperationException {
            if(hasDistanceSensor(direction)==true){
                setBatteryLevel(energy-1);

                int dx = getCurrentDirection2()[0];
                int dy = getCurrentDirection2()[1];

                int width = this.mC.getMazeConfiguration().getHeight();
                int height = this.mC.getMazeConfiguration().getWidth();

                if (direction == Direction.BACKWARD){
                    dx = -dx;
                    dy = -dy;
                }

                if (direction == Direction.LEFT){
                    int temp = dx;
                    dx = dy;
                    dy = -temp;
                }

                if (direction == Direction.RIGHT){
                    int temp = dx;
                    dx = -dy;
                    dy = temp;
                }

                int px = mC.px;
                int py = mC.py;

                int distance = 0;

                while (true){
                    if (px < 0 || px >= width || py < 0 || py >= height){
                        return Integer.MAX_VALUE;
                    }

                    if(dx == 0 && dy == -1){
                        if(this.mC.getMazeConfiguration().hasWall(px,py,CardinalDirection.North)){
                            return distance;
                        }
                        py -= 1;
                    }

                    if(dx == 0 && dy == 1){
                        if(this.mC.getMazeConfiguration().hasWall(px,py,CardinalDirection.South)){
                            return distance;
                        }
                        py += 1;
                    }

                    if(dx == -1 && dy == 0){
                        if(this.mC.getMazeConfiguration().hasWall(px,py,CardinalDirection.West)){
                            return distance;
                        }
                        px -= 1;
                    }
                    if(dx == 1 && dy == 0){
                        if(this.mC.getMazeConfiguration().hasWall(px,py,CardinalDirection.East)){
                            return distance;
                        }
                        px += 1;
                    }

                    distance += 1;
                }
            }
            else{
                throw new UnsupportedOperationException() ;
            }
        }

        /**
         * Tells if the robot has a distance sensor in the given direction.
         */
        @Override
        public boolean hasDistanceSensor(Direction direction) {

            if(direction == Direction.FORWARD){
                return this.FSensor;
            }
            else if(direction == Direction.BACKWARD){
                return this.BSensor;
            }
            else if(direction == Direction.LEFT){
                return this.LSensor;
            }
            else{
                return this.RSensor;
            }
        }
        public int getPathLength(){
            return pathLength;
        }
        public void setPathLength(){
            pathLength += 1;
        }


    }

