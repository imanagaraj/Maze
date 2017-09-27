//import falstad.Robot.Direction;
//import falstad.Robot.Turn;

//		while (distance > 0){
//			// Get the current position of the robot in the maze. There is a getCurrentPosition method in MazeController which will
//			// do this for us.		
//			mC.keyDown(Event.UP);
//			this.currentPosition = this.mC.getCurrentPosition();
//			//System.out.print(this.currentPosition);
//			if (energy >= 5){
//				if (manual == true){
//					// if the maze is manually driven, it is only going to move one cell per user input
//					distance = 1 ;
//				}
//				// Get the distance from current position to the nearest wall. If the distance is greater than 0, then movement can
//				// occur. Otherwise, the robot is stuck.
//				int obstacleDistance = this.distanceToObstacle(Direction.FORWARD) ;
//				
//				System.out.print(obstacleDistance);
//				if (obstacleDistance > 0){
//					if(this.direction == CardinalDirection.North){
//						// change current position (current row is going to decrease, i.e. move from row 5 to row 4)
//						this.currentPosition[1]-- ;
//					}
//					else if(this.direction == CardinalDirection.South){
//						// change current position (current row is going to increase, i.e. move from row 4 to row 5)
//						this.currentPosition[1]++ ;
//					}
//					else if(this.direction == CardinalDirection.East){
//						// change current position (current column is going to increase, i.e. move from column 4 to column 5)
//						this.currentPosition[0]++ ;
//					}
//					else if(this.direction == CardinalDirection.West){
//						// change current position (current column is going to decrease, i.e. move from column 5 to column 4)
//						this.currentPosition[0]-- ;
//					}
//					this.mC.setCurrentPosition(this.currentPosition[0],this.currentPosition[1]);
//					energy -= 5;
//					this.setBatteryLevel(energy);
//					distance --;
//					pathLength += 1 ;
//					pathLength = getPathLength();
//					System.out.print(currentPosition);
//				}
//				else{
//					//if the distance to an obstacle is zero
//					this.hasStopped = true ;
//				}
//			}
//			else{
//				//if energy level is not greater than 5
//				this.hasStopped = true ;
//			}
//		}
//		System.out.println("moved");




/////////////////////////////////////////////////////////////////ROTATE
//					//move forward if there is a wall on the left and space in front
//					if(robot.distanceToObstacle(Direction.LEFT)== 0 && robot.distanceToObstacle(Direction.FORWARD) > 0){
//						robot.move(1, false);
//					}
//					
//					else{
//						
//						// if in a corner with wall on left and in front turn right and move forward
//						if(robot.distanceToObstacle(Direction.RIGHT) >0 ){
//							
//							//robot.distanceToObstacle(Direction.LEFT)==0 && robot.distanceToObstacle(Direction.FORWARD)==0
//							robot.rotate(Turn.RIGHT);
//							robot.move(1,false);
//							//robot.move(1,false);
//							}
//						// the robot needs to turn left bc there is no wall on the left move forward
//						else if(robot.distanceToObstacle(Direction.LEFT) > 0){
//							robot.rotate(Turn.LEFT);
//							// Should this be here??
//							robot.move(1, false);
//						}
//						
//						else{
//							robot.rotate(Turn.AROUND);
//							robot.move(1, false);
//						}
//						
//						//******DO WE NEED TO CHECK RIGHT FOR ANY REASON?******//
//					}
//					BasicRobot.pathLength++;
//					}// if closer
//				
//				else{
//					return false;
//				}


/////////////////////////////////////// PLEDGE
//public boolean drive2Exit() throws Exception {
//		robot.mC.mapMode = true;
//		robot.mC.showSolution = true;
//		robot.mC.showMaze = !robot.mC.showMaze;
//		robot.mC.notifyViewerRedraw();
//		
//		int loop_num = 0;
//		while(robot.isAtExit() == false){
//			int counter = 1;
//			while(counter != 0){
//				if (robot.getBatteryLevel() > 0){
//				
//					if (loop_num == 0){
//						counter = 0;
//					}
//					
//					// Goal is to follow right walls, so turn right and move forward to do so
//					if (robot.distanceToObstacle(Direction.RIGHT) > 0){
//						robot.rotate(Turn.RIGHT);
//						counter += 1;
//						robot.move(1, false);
//					}
//					
//					// If there is a wall on the right and nothing in front, move forward
//					else if (robot.distanceToObstacle(Direction.RIGHT) == 0 && robot.distanceToObstacle(Direction.FORWARD) > 0){
//						robot.move(1, false);
//					}
//					
//					// If there is a wall on the right and a wall in front, turn left
//					else if (robot.distanceToObstacle(Direction.RIGHT) == 0 && robot.distanceToObstacle(Direction.FORWARD) == 0){
//						robot.rotate(Turn.LEFT);
//						counter -= 1;
//					}
//					else{
//						robot.move(1, false);
//					}
//				}
//				else{
//					return false;
//				}
//				loop_num += 1;
//			}}
//					
//					// Follow the right wall until you hit a forward wall
//					//if (robot.distanceToObstacle(Direction.FORWARD) > 0){
//						//robot.move(1, false);
//					//}
//					
//					// When you hit a forward wall, rotate left
//					//if (robot.distanceToObstacle(Direction.FORWARD) == 0 && robot.distanceToObstacle(Direction.RIGHT) > 0){
//						//robot.rotate(Turn.LEFT);
//						//counter -= 1;
//					//}	
//				
////					else if (robot.distanceToObstacle(Direction.RIGHT) == 0){
////						robot.move(1, false);
////					}
////					else if (counter != 0){
////						robot.rotate(Turn.RIGHT);						
////						robot.move(1, false);
////						counter += 1;
//					//}}
//				//else{
//					//robot.move(1, false);
//				//}
//
//		if (robot.isAtExit()){
//			robot.mC.state = Constants.StateGUI.STATE_FINISH;
//			robot.mC.notifyViewerRedraw();
//			System.out.println("pledge completed");
//
//		}
//		
//		
////		while (robot.isAtExit() == false){
////			if (robot.getBatteryLevel() > 0){
////				
////				// Placement of counter??
////				int counter = 0;
////				
////				if(robot.distanceToObstacle(Direction.FORWARD) == 0){
////					
////					robot.rotate(Turn.LEFT);
////					counter -= 1;
////				}
////				if(robot.distanceToObstacle(Direction.RIGHT) != 0){
////					
////					robot.rotate(Turn.RIGHT);
////					counter += 1;
////				}
////				else if(robot.distanceToObstacle(Direction.RIGHT) == 0){
////					if (robot.distanceToObstacle(Direction.FORWARD) != 0){
////						robot.move(1, true);
////					}
////					else if (robot.distanceToObstacle(Direction.FORWARD) == 0){
////						robot.rotate(Turn.LEFT);
////						counter -= 1;
////					}
////				}
////			}
////		}
//		return true;
//	}