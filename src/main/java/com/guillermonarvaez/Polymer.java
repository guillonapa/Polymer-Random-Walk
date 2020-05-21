package com.guillermonarvaez;

import java.io.*;

import org.apache.commons.math3.linear.*;

public class Polymer {

	//FIELDS

	private Monomer root;
	private int length;

	//METHODS

	//Constructor
	public Polymer(MonomerDimension dimension, int numMonomers) {
		this.length = 0;
		if (dimension == MonomerDimension.TWO_DIMENSIONAL) {
			this.root = new Monomer(MonomerDimension.TWO_DIMENSIONAL);
			Monomer previous = this.root;
			for (int i = 0; i < numMonomers; i++) { //to initialize a chain of length numMonomers
				Monomer current = new Monomer(MonomerDimension.TWO_DIMENSIONAL);
				previous.setNext(current); //set next
				current.setPrevious(previous); //set previous
				//then set the coordinates
				current.setCoorsFromPrev(previous.getXCoor(),previous.getYCoor());
				//then update the length
				this.length++;
				//then update current
				previous = current;
			}
		}
		else if (dimension == MonomerDimension.THREE_DIMENSIONAL) {
			this.root = new Monomer(MonomerDimension.THREE_DIMENSIONAL);
			Monomer previous = this.root;
			for (int i = 0; i < numMonomers; i++) {
				Monomer current = new Monomer(MonomerDimension.THREE_DIMENSIONAL);
				previous.setNext(current);
				current.setPrevious(previous);
				current.setCoorsFromPrev(previous.getXCoor(),previous.getYCoor(),previous.getZCoor());
				this.length++;
				previous = current;
			}
		}
		else {
			System.out.println("DIMENSION NOT VALID");
		}
	}

	public Polymer() {
		this.length = 0;
		this.root = new Monomer();
		Monomer previous = this.root;

		boolean canAddMonomer = true;
		while (canAddMonomer) {
			Monomer current = this.root;
			//we calculate the potential coordinates of where the monomer would be located
			//we check if one of the four locations is available (update canAddMonomer)
			//if it is, we create a monomer and try to add it (within a loop, since we need to do this until we find a match)
			double potentialX = previous.getXCoor() + Math.rint(Math.cos(previous.getPlaneAngle()));
			double potentialY = previous.getYCoor() + Math.rint(Math.sin(previous.getPlaneAngle()));
			int count = 0;
			boolean posX = true;
			boolean negX = true;
			boolean posY = true;
			boolean negY = true;
			while (current != null && count < 4) {

				if ((current.getXCoor() == potentialX && current.getYCoor() == potentialY && current.getPlaneAngle() > -0.5 && current.getPlaneAngle() < 0.5) || (current.getXCoor() == potentialX + 1 && current.getYCoor() == potentialY && current.getPlaneAngle() > 2.8 && current.getPlaneAngle() < 3.5)) {
					count++;
					posX = false;
				}
				if ((current.getXCoor() == potentialX && current.getYCoor() == potentialY && current.getPlaneAngle() > 2.8 && current.getPlaneAngle() < 3.5) || (current.getXCoor() == potentialX - 1 && current.getYCoor() == potentialY && current.getPlaneAngle() > -0.5 && current.getPlaneAngle() < 0.5)) {
					count++;
					negX = false;
				}
				if ((current.getXCoor() == potentialX && current.getYCoor() == potentialY && current.getPlaneAngle() > 1.2 && current.getPlaneAngle() < 1.8) || (current.getXCoor() == potentialX && current.getYCoor() == potentialY + 1 && current.getPlaneAngle() > 4.3 && current.getPlaneAngle() < 5.3)) {
					count++;
					posY = false;
				}

				if ((current.getXCoor() == potentialX && current.getYCoor() == potentialY && current.getPlaneAngle() > 4.3 && current.getPlaneAngle() < 5.3) || (current.getXCoor() == potentialX && current.getYCoor() == potentialY - 1 && current.getPlaneAngle() > 1.2 && current.getPlaneAngle() < 1.8)) {
					count++;
					negY = false;
				}


				current = current.getNext();
			}
			if (count >= 4) {
				canAddMonomer = false;
				break;
			}
			else {
				while (true) {
					Monomer newMonomer = new Monomer();
					//check if orientation is valid, if so exit this loop, if not, create a new monomer
					if (newMonomer.getPlaneAngle() == 0) {
						if (posX == true) {
							previous.setNext(newMonomer);
							newMonomer.setPrevious(previous);
							newMonomer.setCoorsFromPrev(previous.getXCoor(),previous.getYCoor(),1);
							this.length++;
							previous = newMonomer;
							break;
						}
					}
					else if (newMonomer.getPlaneAngle() == 0.5*Math.PI) {
						if (posY == true) {
							previous.setNext(newMonomer);
							newMonomer.setPrevious(previous);
							newMonomer.setCoorsFromPrev(previous.getXCoor(),previous.getYCoor(),2);
							this.length++;
							previous = newMonomer;
							break;
						}
					}
					else if (newMonomer.getPlaneAngle() == Math.PI) {
						if (negX == true) {
							previous.setNext(newMonomer);
							newMonomer.setPrevious(previous);
							newMonomer.setCoorsFromPrev(previous.getXCoor(),previous.getYCoor(),3);
							this.length++;
							previous = newMonomer;
							break;
						}
					}
					else if (newMonomer.getPlaneAngle() == 1.5*Math.PI) {
						if (negY == true) {
							previous.setNext(newMonomer);
							newMonomer.setPrevious(previous);
							newMonomer.setCoorsFromPrev(previous.getXCoor(),previous.getYCoor(),4);
							this.length++;
							previous = newMonomer;
							break;
						}
					}
				}
			}
		}
	}

	public Polymer(boolean theBoolean) {
		this.length = 0;
		this.root = new Monomer();
		Monomer previous = this.root;
		boolean posX = true;
		boolean posY = true;
		boolean negX = true;
		boolean negY = true;

		boolean canAddMonomer = true;
		int totalCount = 0;

		while (canAddMonomer == true && totalCount < 1000) {

			totalCount++;
			System.out.println("total count is "+totalCount);
			//First I calculate the starting point (potX, potY)
			int potX = previous.getX();
			int potY = previous.getY();
			System.out.println("\nPotx and poty are " + potX + " - " + potY + "\nand orient " + previous.getRectangularOrientation() + "\n");
			switch (previous.getRectangularOrientation()) {
				case X_POSITIVE:
					potX += 1;
					break;
				case Y_POSITIVE:
					potY += 1;
					break;
				case X_NEGATIVE:
					potX += -1;
					break;
				case Y_NEGATIVE:
					potY += -1;
					break;
			}
			System.out.println("\nPotx and poty are " + potX + " - " + potY + "\nand orient " + previous.getRectangularOrientation() + "\n");

			//then i need to check if any of the monomers occupy the surrounding veritces...
			Monomer current = this.root;
			int extracount = 0;
			while (extracount < 100 && current != null && (posX == true || posY == true || negX == true || negY == true)) {
				System.out.println("This.root is equal to " + this.root + " with orientation " + this.root.getRectangularOrientation());
				System.out.println("checking for surrounding vertices...");
				System.out.println("Previous monomer is " + previous);
				System.out.println("Initilly the current monomer is at: " + current);
				System.out.println("And the boolean variables are: " + posX + " " + posY + " " + negX + " " + negY);
				if (current.getX() == potX + 1 && current.getY() == potY) {
					if (previous.getRectangularOrientation() != MonomerOrientation.X_POSITIVE) {
						negX = false; //correct
					}
				}
				else if (current.getX() == potX - 1 && current.getY() == potY) {
					if (previous.getRectangularOrientation() != MonomerOrientation.X_NEGATIVE) {
						posX = false; //correct
					}
				}
				else if (current.getY() == potY + 1 && current.getX() == potX) {
					if (previous.getRectangularOrientation() != MonomerOrientation.Y_POSITIVE) {
						negY = false;
					}
				}
				else if (current.getY() == potY - 1 && current.getX() == potX) {
					if (previous.getRectangularOrientation() != MonomerOrientation.Y_NEGATIVE) {
						posY = false;
					}
				}
				System.out.println("I just checked the four orientations... ");
				System.out.println("The boolean variables are: " + posX + " " + posY + " " + negX + " " + negY);
				extracount++;

				current = current.getNext();
				System.out.println("The curren monomer is at: " + current);
				//Scanner console = new Scanner(System.in);
				System.out.println("Go to the next iteration? ");
				//String x = console.next();
			}
			if (posX == false && posY == false && negX == false && negY == false) {
				System.out.println("If you see this, the method should end!!!");
				canAddMonomer = false;
			}
			else {
				Monomer newMonomer = new Monomer();
				//then check if it's orientation is valid and change it if not
				boolean isOrienValid = false;
				MonomerOrientation currOrien = newMonomer.getRectangularOrientation();
				while (isOrienValid == false) {
					System.out.println("Finding the correct orientation for new monomer... And adding Monomer...");
					switch (currOrien) {
					case X_POSITIVE:
						if (posX == false) {
							newMonomer.setNewRectangularOrientation();
							currOrien = newMonomer.getRectangularOrientation();
						}
						else {
							//we add our monomer
							newMonomer.setCoors(potX,potY);
							previous.setNext(newMonomer);
							newMonomer.setPrevious(previous);
							previous = newMonomer;
							this.length++;
							isOrienValid = true;
						}
						break;
					case Y_POSITIVE:
						if (posY == false) {
							newMonomer.setNewRectangularOrientation();
							currOrien = newMonomer.getRectangularOrientation();
						}
						else {
							//we add our monomer
							newMonomer.setCoors(potX,potY);
							previous.setNext(newMonomer);
							newMonomer.setPrevious(previous);
							previous = newMonomer;
							this.length++;
							isOrienValid = true;
						}
						break;
					case X_NEGATIVE:
						if (negX == false) {
							newMonomer.setNewRectangularOrientation();
							currOrien = newMonomer.getRectangularOrientation();
						}
						else {
							//we add our monomer
							newMonomer.setCoors(potX,potY);
							previous.setNext(newMonomer);
							newMonomer.setPrevious(previous);
							previous = newMonomer;
							this.length++;
							isOrienValid = true;
						}
						break;
					case Y_NEGATIVE:
						if (negY == false) {
							newMonomer.setNewRectangularOrientation();
							currOrien = newMonomer.getRectangularOrientation();
						}
						else {
							//we add our monomer
							newMonomer.setCoors(potX,potY);
							previous.setNext(newMonomer);
							newMonomer.setPrevious(previous);
							previous = newMonomer;
							this.length++;
							isOrienValid = true;
						}
						break;
					}
				}
			}
			posX = true;
			posY = true;
			negX = true;
			negY = true;
		}

	}

	public int getLength() {
		return this.length;
	}

	public void printChain() {
		Monomer current = this.root;
		//int count = 0;
		while (current != null) {
			System.out.println(current);
			current = current.getNext();
		}
	}

	public double[] getFinalPositions() {
		double[] finalPositions;
		if (root.getDimension() == MonomerDimension.TWO_DIMENSIONAL || this.root.getDimension() == MonomerDimension.TWO_DIMENSIONAL_RECTANGULAR) {
			finalPositions = new double[2];
		}
		else {
			finalPositions = new double[3];
		}

		Monomer current = this.root;
		while (current != null) {
			if (current.getNext() == null) {
				current.setCoorsFromPrev(current.getXCoor(), current.getYCoor());
				finalPositions[0] = current.getXCoor();
				finalPositions[1] = current.getYCoor();
				if (current.getDimension() == MonomerDimension.THREE_DIMENSIONAL) {
					finalPositions[2] = current.getZCoor();
				}
			}
			current = current.getNext();
		}
		return finalPositions;
	}

	public double[] printChain(String name) throws FileNotFoundException {
		PrintStream outEXTRA = new PrintStream(name);

		double[] finalPositions;
		if (this.root.getDimension() == MonomerDimension.TWO_DIMENSIONAL || this.root.getDimension() == MonomerDimension.TWO_DIMENSIONAL_RECTANGULAR) {
			outEXTRA.println("x-coordinate, y-coordinate");
			finalPositions = new double[2];
		}
		else {
			outEXTRA.println("x-coordinate, y-coordinate, z-coordinate");
			finalPositions = new double[3];
		}

		Monomer current = this.root;
		while (current != null) {
			outEXTRA.println(current);
			if (current.getNext() == null) {
				current.setCoorsFromPrev(current.getXCoor(), current.getYCoor());
				finalPositions[0] = current.getXCoor();
				finalPositions[1] = current.getYCoor();
				if (current.getDimension() == MonomerDimension.THREE_DIMENSIONAL) {
					finalPositions[2] = current.getZCoor();
				}
			}
			current = current.getNext();
		}
		outEXTRA.close();
		return finalPositions;
	}

	public double getEndToEndCircle() {

		double xCoor = this.root.getXCoor();
		double yCoor = this.root.getYCoor();
		double zCoor = this.root.getZCoor();

		Monomer current = this.root;

		while (current.getNext() != null) {
			current = current.getNext();
		}

		double finalXCoor = current.getXCoor();
		double finalYCoor = current.getYCoor();
		double finalZCoor = current.getZCoor();

		return (Math.sqrt( Math.pow(finalXCoor-xCoor,2) + Math.pow(finalYCoor-yCoor,2) + Math.pow(finalZCoor-zCoor,2) ));

	}

	public double getGyrationRadius() {

		double[] theCOM = getCOM();

		int numMonomers = 0;
		double xComp = 0;
		double yComp = 0;
		double zComp = 0;

		Monomer current = this.root;

		double acc = 0.0;

		while (current != null) {

			acc += Math.pow((current.getXCoor() - theCOM[0]),2);
			acc += Math.pow((current.getYCoor() - theCOM[1]),2);
			acc += Math.pow((current.getZCoor() - theCOM[2]),2);
			numMonomers++;
			current = current.getNext();

		}

		xComp = xComp / numMonomers;
		yComp = yComp / numMonomers;
		zComp = zComp / numMonomers;

		double rSquared = acc / numMonomers;

		return Math.sqrt(rSquared);

	}

	public double[] getCOM() {

		int numMonomers = 0;

		double xDisp = 0;
		double yDisp = 0;
		double zDisp = 0;

		Monomer current = this.root;

		while (current != null) {
			xDisp += current.getXCoor();
			yDisp += current.getYCoor();
			zDisp += current.getZCoor();
			numMonomers++;
			current = current.getNext();
		}

		double[] results = new double[3];

		results[0] = xDisp / numMonomers;
		results[1] = yDisp / numMonomers;
		results[2] = zDisp / numMonomers;

		return results;

	}


	public double[][] getCovarianceTensor() {

		if (this.root.getDimension() == MonomerDimension.TWO_DIMENSIONAL || this.root.getDimension() == MonomerDimension.TWO_DIMENSIONAL_RECTANGULAR) {

			double[] theCOM = getCOM();

			int numMonomers = 0;

			double xDisp = 0;
			double xyDisp = 0;
			double yDisp = 0;

			Monomer current = this.root;

			while (current != null) {
				xDisp += Math.pow(current.getXCoor() - theCOM[0],2);
				yDisp += Math.pow(current.getYCoor() - theCOM[1],2);
				xyDisp += ((current.getXCoor() - theCOM[0])*(current.getYCoor() - theCOM[1]));
				numMonomers++;
				current = current.getNext();
				//System.out.println(xDisp + " : " + numMonomers);

			}

			double[][] results = new double[2][2];

			results[0][0] = (xDisp / numMonomers);
			results[1][1] = (yDisp / numMonomers);
			results[0][1] = (xyDisp / numMonomers);
			results[1][0] = (xyDisp / numMonomers);

			return results; //this is the covariance tensor
		}

		else {

			double[] theCOM = getCOM();

			int numMonomers = 0;

			double xxDisp = 0;
			double yyDisp = 0;
			double zzDisp = 0;
			double xyDisp = 0;
			double xzDisp = 0;
			double yzDisp = 0;

			Monomer current = this.root;

			while (current != null) {
				xxDisp += Math.pow(current.getXCoor() - theCOM[0],2);
				yyDisp += Math.pow(current.getYCoor() - theCOM[1],2);
				zzDisp += Math.pow(current.getZCoor() - theCOM[2],2);
				xyDisp += ((current.getXCoor() - theCOM[0])*(current.getYCoor() - theCOM[1]));
				xzDisp += ((current.getXCoor() - theCOM[0])*(current.getZCoor() - theCOM[2]));
				yzDisp += ((current.getYCoor() - theCOM[1])*(current.getZCoor() - theCOM[2]));
				numMonomers++;
				current = current.getNext();
			}

			double[][] results = new double[3][3];

			results[0][0] = xxDisp / numMonomers;
			results[1][1] = yyDisp / numMonomers;
			results[2][2] = zzDisp / numMonomers;
			results[1][0] = xyDisp / numMonomers;
			results[2][0] = xzDisp / numMonomers;
			results[2][1] = yzDisp / numMonomers;
			results[0][1] = xyDisp / numMonomers;
			results[0][2] = xzDisp / numMonomers;
			results[1][2] = yzDisp / numMonomers;

			return results; //this is the covariance tensor
		}


	}

	public double[] getRadii() {

		if (this.root.getDimension() == MonomerDimension.TWO_DIMENSIONAL || this.root.getDimension() == MonomerDimension.TWO_DIMENSIONAL_RECTANGULAR) {

			Array2DRowRealMatrix theMatrix2 = new Array2DRowRealMatrix(getCovarianceTensor());
			//System.out.println(theMatrix2);
			EigenDecomposition theEigenMatrix2 = new EigenDecomposition(theMatrix2);
			double[] doubleCheckEV2 = theEigenMatrix2.getRealEigenvalues();
			RealVector eVector2 = theEigenMatrix2.getEigenvector(0);

			double[] result = new double[3];

			result[0] = Math.sqrt(doubleCheckEV2[0]);
			result[1] = Math.sqrt(doubleCheckEV2[1]);
			result[2] = Math.acos(eVector2.getEntry(0));

			return result;

		}

		else {

			double[] result = new double[5];

			Array2DRowRealMatrix theMatrix = new Array2DRowRealMatrix(getCovarianceTensor());
			EigenDecomposition theEigenMatrix = new EigenDecomposition(theMatrix);
			double[] doubleCheckEV = theEigenMatrix.getRealEigenvalues();
			RealVector eVector = theEigenMatrix.getEigenvector(0);

			result[0] = doubleCheckEV[0];
			result[1] = doubleCheckEV[1];
			result[2] = doubleCheckEV[2];
			result[3] = Math.atan(eVector.getEntry(1)/eVector.getEntry(0));
			result[4] = Math.acos(eVector.getEntry(2));

			return result;

		}

	}

	public double det(double[][] b) {
		Array2DRowRealMatrix theMatrix = new Array2DRowRealMatrix(b);
		EigenDecomposition theEigenMatrix = new EigenDecomposition(theMatrix);
		return theEigenMatrix.getDeterminant();
	}

	public double trace(double[][] covarianceTensor) {
		double result = covarianceTensor[0][0] + covarianceTensor[1][1] + covarianceTensor[2][2];
		return result;
	}

}
