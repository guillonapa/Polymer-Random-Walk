import java.io.FileNotFoundException;
import java.io.PrintStream;



public class ClientCode {
    public static void main(String[] args) throws FileNotFoundException {
    	PrintStream details = new PrintStream("detailsPositions.csv");
    	//FOR 2D
        //details.println("Radius 'a', Radius 'b', Orientation, End-to-end Radius, Gyration Radius, COM-X, COM-Y, Final-X, Final-Y");
    	//FOR 3D
    	details.println("N, Radius 'a', Radius 'b', Orientation, End-to-end Radius, Gyration Radius, COM-X, COM-Y, Final-X, Final-Y");
        for (int i = 1000; i < 1001; i++) {
        	for (int j = 1; j < 1001; j++) {
        		details.print(i + ", ");
        		Polymer tempPoly = new Polymer(2,i);
        		//Polymer tempPoly = new Polymer(3,i);
        		//Polymer tempPoly = new Polymer(true);
        		double[] temp = tempPoly.getRadii();
            
        		details.print(temp[0] + ", " + temp[1] + ", " + temp[2] + ", " );
        		double[] tempCOM = tempPoly.getCOM();
        		details.print(tempPoly.getEndToEndCircle() + ", " + tempPoly.getGyrationRadius() + ", " + tempCOM[0] + ", " + tempCOM[1] + ", ");
        		details.println();
        		//details.print(finalPositions[0] + ", " + finalPositions[1]);
        	}
        	
    	}
    	
        //double[] finalPositions = tempPoly.printChain("PolyPositions.csv");
        
        details.close();
    }
}
