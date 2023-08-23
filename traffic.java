/****************************************************************************
 *          17th Annual UCF High School Programming Tournament              *
 *                              May 2, 2003                                 *
 *                     University of Central Florida                        *
 *                                                                          *
 *                                                                          *
 * Special thanks to our sponsors: UPE, SAIC, ACM, and HARRIS               *
 *                                                                          *
 *                                                                          *
 * Problem:         Traffic                                                 *
 * Problem Author:  Mike B                                                  *
 * Solution Author: Glenn                                                   *
 * Data Author:     Ben                                                     *
 ***************************************************************************/

import java.io.*;
import java.util.StringTokenizer;

public class traffic
{
  public static void main(String[] args) throws Exception
  {
    BufferedReader    infile;
    int               numSets;
    int               i;
    StringTokenizer   st;
    int               numLanes;
    int               numRows;
    int               mikesLane;
    int               mikesPositionInLane;
    int               mikesCar;
    int               x;
    int               y;
    int[][]           cars;
    boolean[][]       haveLetIn;
    int               numCarsGone;

    cars = new int[10][5];
    haveLetIn = new boolean[10][5];

    infile = new BufferedReader(new FileReader(new File("traffic.in")));

    // Read the number of cases from the file
    numSets = Integer.parseInt(infile.readLine());

    // Loop through for each data 
    for (i=0; i < numSets; i++)
    {
      // Output header
      System.out.println("Traffic Jam " + (i+1) + ":");

      // Get this data case (number of lanes, number of rows, the lane 
      // that Mike is in and the position Mike is at within that lane 
      st = new StringTokenizer(infile.readLine());
      numLanes = Integer.parseInt(st.nextToken());
      numRows = Integer.parseInt(st.nextToken());
      mikesLane = Integer.parseInt(st.nextToken());
      mikesPositionInLane = Integer.parseInt(st.nextToken());

      // Use the formula to compute the number of Mike's car 
      mikesCar = mikesLane * 10 + mikesPositionInLane;

      // Clear the highway (max case)
      for (y=0; y < 10; y++)
      {
        for (x=0; x < 5; x++)
        {
          cars[y][x] = 0;
          haveLetIn[y][x] = false;
        }
      }

      // Build the highway
      for (y=0; y < numRows; y++)
      {
        for (x=0; x < numLanes; x++)
        {
          // Compute the car number for this car 
          cars[y][x] = (numLanes-x)*10 + y + 1;
   
          // Mark this car as *not* having let anybody in 
          haveLetIn[y][x] = false;
        }
      }

      // Simulate the system 
      numCarsGone = 0;
      while (cars[0][numLanes-1] != mikesCar)
      {
        // Move the rightmost car forward
        System.out.println("Car " + cars[0][numLanes-1] + 
                           " clears the accident.");
        cars[0][numLanes-1] = 0;
        numCarsGone++;

        // Have the other cars move according to the rules -- go through 
        // the cars from right to left, top to bottom                    
        for (x=numLanes-1; x >= 0; x--)
        {
          for (y=0; y < numRows; y++)
          {
            // If the space is empty, try to move a car into it 
            if (cars[y][x] == 0)
            {
              // If there's a car that hasn't let one in that can, 
              // it will                                           
   
              // Make sure there is a car below the empty spot, that 
              // it hasn't let anybody in, that it isn't in the      
              // leftmost lane, and that there is a car left of the  
              // empty spot to let in                                
              if ( (cars[y+1][x] != 0) && (haveLetIn[y+1][x] == false) &&
                   (x > 0) && (cars[y][x-1] != 0) )
              {
                // This car hasn't let one in yet so it will now 
                System.out.println("Car " + cars[y+1][x] + 
                                   " lets car " + cars[y][x-1] + 
                                   " in.");
   
                // Move the car being let in to the right and mark 
                // the new empty spot as being empty               
                cars[y][x] = cars[y][x-1];
                haveLetIn[y][x] = haveLetIn[y][x-1];
                cars[y][x-1] = 0;
                haveLetIn[y][x-1] = false;
   
                // Mark the car below the empty spot as having let 
                // somebody in                                     
                haveLetIn[y+1][x] = true;
              }
              else if ( (cars[y+1][x] != 0) && (y < numRows-1) )
              {
                // This car already let one in so it will go forward 
                System.out.println("Car " + cars[y+1][x] + 
                                   " moves forward.");

                // Move the car forward 
                cars[y][x] = cars[y+1][x];
                haveLetIn[y][x] = haveLetIn[y+1][x];

                // Mark the new empty spot as empty 
                cars[y+1][x] = 0;
                haveLetIn[y+1][x] = false;
              }
              else if ( (x > 0) && (cars[y][x-1] != 0) )
              {
                // There is a free space on right and no car behind it
                System.out.println("Car " + cars[y][x-1] + 
                                   " moves right.");

                // Move the car to the right
                cars[y][x] = cars[y][x-1];
                haveLetIn[y][x] = haveLetIn[y][x-1];

                // Mark the new empty spot as empty 
                cars[y][x-1] = 0;
                haveLetIn[y][x-1] = false;
                     
              }
            }
          }
        }

        // Uncomment this code to print the road after each step 
        /*
          for (y=0; y < numRows; y++)
          {
          for (x=0; x < numLanes; x++)
          System.out.print(cars[y][x] + " ");
          System.out.println();
          }
          System.out.println();
        */
      }

      // Print that Mike cleared the accident and how many cars went first
      System.out.println("Car " + cars[0][numLanes-1] + 
                         " clears the accident.");
      System.out.println("Mike's car will exit after " + numCarsGone +
                         " cars go in front of it.");

      // Don't forget the blank line after each data set!
      System.out.println();
    }
  }
}

