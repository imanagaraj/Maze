package edu.wm.cs.cs301.amazebycarolinefaparnan.generation;

import edu.wm.cs.cs301.amazebycarolinefaparnan.falstad.MazePanel;

public class MazeBuilderEller extends MazeBuilder implements Runnable {

    public MazePanel gw;
           
           public MazeBuilderEller() {
               super();
               //this.gw = wrapper;
               System.out.println("MazeBuilderEller uses Eller's algorithm to generate maze.");
           }
                      
           public MazeBuilderEller(boolean det) {
               super(det);
               System.out.println("MazeBuilderEller uses Eller's algorithm to generate maze.");
           }
           
           @Override
           protected void generatePathways (){
               int[][] newMaze = new int[height][width] ;
               System.out.println("Width "+width);
               System.out.println("Height " +height);
               int setValue = 1 ;
               int verticalCount;

               //  this builds the 2 dimensional array assigning values to each row along the way.
               for(int h=0; h<height; h++){
                               for(int w=0; w<width; w++){
                                            newMaze[h][w] = setValue;
                                            setValue++ ;
                                 }
               }                      
               //Establish horizontal and vertical connections by merging sets.
               for(int h=0; h<height; h++){
                          System.out.println("Walls for row  " +h);
                          for(int w=0; w<width; w++){                                         
                             // Vertical Connections
                             /**
                               * Generate a random number from 0 to 10. If it is an even number, remove the wall to
                               * the right. If it is an odd number, move on.
                             */

                             int z = random.nextIntWithinInterval(0, 10);
                             // basically binary across merging sets randomly for each row              
                             if (z%2==0 && w+1 < width){
                               System.out.println("combine wall at " +w); 
                               System.out.println("set  " +newMaze[h][w]);
                                 Wall rightWall = new Wall(w, h, CardinalDirection.East);
                                 cells.deleteWall(rightWall);
                                 newMaze[h][w+1] = newMaze[h][w] ;
                             }
                               
                          } // end for                                 
                         // Go through each row and add verticals make sure you have one for each set
                         int setHold = -1;
                         verticalCount = 0;
                         System.out.println("Verticals for row " +h);
                         // no verticals on the last row
                         if (h < height - 1){
                            for(int w=0; w<width; w++){
                              if ((w == 0) || (newMaze[h][w] != setHold)){
                               verticalCount = 0;
                              }
                              // Deletes bottom walls for sets of size one (at least one occurrence per set).
                              int z = random.nextIntWithinInterval(0, 10);
                              //  add a vertical wall if the random number is even and we don't already have one in this set
                              //  or if it is the last column and we need to add one
                              if (((z%2==0 && w<width-1) && (verticalCount ==0)) || 
                                 ((verticalCount == 0) && ((w == width - 1) || (newMaze[h][w] != newMaze[h][w+1])))){
                               System.out.println("vertical at " +w); 
                                System.out.println("set val " +newMaze[h][w]);
                                  Wall wl = new Wall(w, h, CardinalDirection.South);
                                  newMaze[h+1][w] = newMaze[h][w];
                                  cells.deleteWall(wl);
                                  verticalCount++;
                              } 
                                setHold = newMaze[h][w];
                             } // end for
                         } //end if
                         /*
                          *  Combine orphaned sets on the last row
                         */
                         if (h == height -1){
                               System.out.println("Combine Orphans on Last Set "); 
                               for(int w=0; w<width; w++){                                  
                                 int cellCurrent = newMaze[h][w];
                                 int cellBelow = newMaze[h-1][w];
                                 int cellLeft = -1;
                                 int cellRight = -1;
                                 if (w != 0){
                                     cellLeft = newMaze [h][w-1];       
                                 }
                                 if (w != width -1){
                                     cellRight = newMaze [h][w+1];            
                                 }
                                 // orphan exists if the surrounding walls don't match this wall
                                 if (cellCurrent != cellLeft  &&
                                     cellCurrent != cellRight  &&
                                     cellCurrent != cellBelow) {
                                     System.out.println("combine wall at " +w); 
                                     System.out.println("set  " +newMaze[h][w]);
                                     if (w==width-1){
                                         Wall rightWall = new Wall(w-1, h, CardinalDirection.East);
                                         cells.deleteWall(rightWall);
                                         newMaze[h][w] = newMaze[h][w-1] ;
                                     }
                                     else
                                     {
                                        Wall rightWall = new Wall(w, h, CardinalDirection.East);
                                        cells.deleteWall(rightWall);
                                        newMaze[h][w+1] = newMaze[h][w] ;
                                     }
                                 }
                              } // end for      
                         }
                     // to do ****** Add logic here for the last row joining any orphaned sets
                   }//end for
           } 
          
           protected void generateCaroline() {
               int[][] newMaze = new int[width][height] ;
               System.out.println("Width "+width);
               System.out.println("Height " +height);
               int setValue = 1 ;
               int occurrenceCount;
               int firstOccurrence;   
               for(int w=0; w<width; w++){
                               for(int h=0; h<height; h++){
                                            newMaze[w][h] = setValue;
                                            setValue++ ;
                                 }
                      }
                      
                      //Establish horizontal and vertical connections by merging sets.
                      for(int i=0; i<width; i++){
                                 for(int j=0; j<height; j++){
                                            
                                            // Horizontal Connections
                                            /**
                                            * Generate a random number from 0 to 10. If it is an even number, remove the wall to
                                            * the right. If it is an odd number, move on.
                                            */
                                            firstOccurrence = j;
                                            int z = random.nextIntWithinInterval(0, 10);
                                            
                                            if(z%2==0 && j+1 < height){
                                            
                                                       Wall rightWall = new Wall(j, i, CardinalDirection.East);
                                                       cells.deleteWall(rightWall);
                                                       newMaze[i][j+1] = newMaze[i][j] ;
                                            }
                     }
                                 
            // Vertical connections
                                 
                                 int q=0;
                                 for(; q<height-1; q++){
                                            occurrenceCount = 1;
                                            while(q<height-1 && newMaze[i][q]==newMaze[i][q+1]){
                                                       occurrenceCount++ ;
                                                       q++ ;
                                            }
                                            // Deletes bottom walls for sets of size one (one occurrence).
                                            if(occurrenceCount==1 && i<width-1){
                                                       Wall w = new Wall(q, i, CardinalDirection.South);
                                                       cells.deleteWall(w);
                                            }
                                            
                                 }}
           }}   

