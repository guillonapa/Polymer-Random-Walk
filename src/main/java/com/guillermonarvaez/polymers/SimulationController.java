package com.guillermonarvaez.polymers;

import java.io.FileNotFoundException;
import java.io.PrintStream;

public class SimulationController {

    public static final String OUTPUT_FILE_NAME = "details-positions.csv";

    public static final int LENGTH_OF_POLYMERS = 100;
    public static final int NUMBER_OF_POLYMERS = 5;

    public static final String OUTPUT_FILE_COLUMN_HEADERS = "Radius 'a', Radius 'b', Orientation, End-to-end Radius, Gyration Radius, COM-X, COM-Y, Final-X, Final-Y";

    /**
     * Remember to update the final variables before running the program
     */
    public static void main(String[] args) throws FileNotFoundException {
        PrintStream stream = System.out; //new PrintStream(OUTPUT_FILE_NAME);
        stream.println(OUTPUT_FILE_COLUMN_HEADERS);
        for (int polymerIndex = 1; polymerIndex <= NUMBER_OF_POLYMERS; polymerIndex++) {
            for (int j = 1; j <= LENGTH_OF_POLYMERS; j++) {
                Polymer polymer = new Polymer(MonomerDimension.TWO_DIMENSIONAL, LENGTH_OF_POLYMERS);
                printStatisticsToFile(polymerIndex, polymer, stream); // EXTRACT AND PRINT STATISTICS
            }
        }
        if (stream != System.out) {
            stream.close();
        }
    }

    /**
     * This method extracts relevant statistics and prints them with the PrintStream
     * object passed.
     */
    public static void printStatisticsToFile(int polymerIndex, Polymer polymer, PrintStream stream) {
        double[] radii = PolymerAnalyzer.getRadii(polymer); // get the radii of the generated polymer
        double[] com = PolymerAnalyzer.getCOM(polymer); // get the center of mass
        double[] finalPositions = polymer.getFinalPositions(); // get the final positions of the polymer
        double endToEndCircle = PolymerAnalyzer.getEndToEndCircle(polymer); // get the end to end circle
        double gyrationRadius = PolymerAnalyzer.getGyrationRadius(polymer); // get the gyration radius
        stream.println(String.format("%d, %f, %f, %f, %f, %f, %f, %f, %f, %f", 
                polymerIndex, 
                radii[0], radii[1], radii[2], 
                endToEndCircle, 
                gyrationRadius, 
                com[0], com[1], 
                finalPositions[0], finalPositions[1]));
    }
}
