import java.io.FileNotFoundException;
import java.io.PrintStream;

public class SimulationController {

  public static final String OUTPUT_FILE_NAME = "details-positions.csv";
  public static final String FINAL_POSITIONS_FILE_NAME = "final-positions.csv";

  public static final int LENGTH_OF_POLYMERS = 1000;
  public static final int NUMBER_OF_POLYMERS = 10;

  public static final String OUTPUT_FILE_COLUMN_HEADERS = "Radius 'a', Radius 'b', Orientation, End-to-end Radius, Gyration Radius, COM-X, COM-Y, Final-X, Final-Y";

  /*
   * Column headers for 3D simulation:
   * "N, Radius 'a', Radius 'b', Orientation, End-to-end Radius, Gyration Radius, COM-X, COM-Y, Final-X, Final-Y";
   */

   /**
    * Remember to update the final variables before running the program
    */
  public static void main(String[] args) throws FileNotFoundException {
    PrintStream details = new PrintStream(OUTPUT_FILE_NAME);
    details.println(OUTPUT_FILE_COLUMN_HEADERS);
    for(int currPolymer = 1; currPolymer <= NUMBER_OF_POLYMERS; currPolymer++) {
    	for(int j = 1; j <= LENGTH_OF_POLYMERS; j++) {
      	Polymer tempPoly = new Polymer(2,LENGTH_OF_POLYMERS); // Inititlaize the 2D polymer
        printStatisticsToFile(currPolymer, tempPoly, details); // EXTRACT AND PRINT STATISTICS
      }
    }
    details.close();
  }

  /**
   *  This method extracts relevant statistics and prints them
   *  with the PrintStream object passed.
   *
   */
  public static void printStatisticsToFile(int currPolymer, Polymer tempPoly, PrintStream details) {
    details.print(currPolymer + ", "); // Print what polymer we are analyzing
    double[] temp = tempPoly.getRadii(); // Get the radii of the generated polymer
    details.print(temp[0] + ", " + temp[1] + ", " + temp[2] + ", " ); // print the radii data
    double[] tempCOM = tempPoly.getCOM(); // get the center of mass
    details.print(tempPoly.getEndToEndCircle() + ", " + tempPoly.getGyrationRadius() + ", "); // print end-to-end-circle and gyration-radius
    details.print(tempCOM[0] + ", " + tempCOM[1] + ", ") // print the center of mass
    double[] finalPositions = tempPoly.getFinalPositions(); // gets the final positions of the polymer
    details.println(finalPositions[0] + ", " + finalPositions[1]); // print the final position
  }
}
