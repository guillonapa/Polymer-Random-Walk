package com.guillermonarvaez.polymers;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.RealVector;

public class PolymerAnalyzer {

	/**
	 * The end-to-end radius consists of the distance between the first and last
	 * monomers of the polymer.
	 */
	public static double getEndToEndCircle(Polymer polymer) {
		// get the root
		Monomer current = polymer.getRoot();
		final double xCoor = current.getXCoor();
		final double yCoor = current.getYCoor();
		final double zCoor = current.getZCoor();
		// go to the last monomer
		while (current.getNext() != null) {
			current = current.getNext();
		}
		// calculate the result
		final double finalXCoor = current.getXCoor();
		final double finalYCoor = current.getYCoor();
		final double finalZCoor = current.getZCoor();
		return (Math.sqrt(
				Math.pow(finalXCoor - xCoor, 2) + Math.pow(finalYCoor - yCoor, 2) + Math.pow(finalZCoor - zCoor, 2)));
	}

	/**
	 * The gyration radius is a more general measure of the spread of the polymer.
	 */
	public static double getGyrationRadius(Polymer polymer) {
		final double[] theCOM = getCOM(polymer);
		int numMonomers = 0;
		double xComp = 0;
		double yComp = 0;
		double zComp = 0;

		Monomer current = polymer.getRoot();
		double acc = 0.0;

		while (current != null) {
			acc += Math.pow((current.getXCoor() - theCOM[0]), 2);
			acc += Math.pow((current.getYCoor() - theCOM[1]), 2);
			acc += Math.pow((current.getZCoor() - theCOM[2]), 2);
			numMonomers++;
			current = current.getNext();
		}

		xComp = xComp / numMonomers;
		yComp = yComp / numMonomers;
		zComp = zComp / numMonomers;
		final double rSquared = acc / numMonomers;
		return Math.sqrt(rSquared);
	}

	/**
	 * Calculate the center of mass.
	 */
	public static double[] getCOM(Polymer polymer) {
		int numMonomers = 0;
		double xDisp = 0;
		double yDisp = 0;
		double zDisp = 0;

		Monomer current = polymer.getRoot();
		while (current != null) {
			xDisp += current.getXCoor();
			yDisp += current.getYCoor();
			zDisp += current.getZCoor();
			numMonomers++;
			current = current.getNext();
		}

		final double[] results = new double[3];
		results[0] = xDisp / numMonomers;
		results[1] = yDisp / numMonomers;
		results[2] = zDisp / numMonomers;
		return results;
	}

	/**
	 * Get the radii.
	 */
	public static double[] getRadii(Polymer polymer) {
		MonomerDimension dimension = polymer.getRoot().getDimension();
		if (dimension == MonomerDimension.TWO_DIMENSIONAL
				|| dimension == MonomerDimension.TWO_DIMENSIONAL_RECTANGULAR) {
			final Array2DRowRealMatrix theMatrix2 = new Array2DRowRealMatrix(getCovarianceTensor(polymer));
			final EigenDecomposition theEigenMatrix2 = new EigenDecomposition(theMatrix2);
			final double[] doubleCheckEV2 = theEigenMatrix2.getRealEigenvalues();
			final RealVector eVector2 = theEigenMatrix2.getEigenvector(0);

			final double[] result = new double[3];
			result[0] = Math.sqrt(doubleCheckEV2[0]);
			result[1] = Math.sqrt(doubleCheckEV2[1]);
			result[2] = Math.acos(eVector2.getEntry(0));
			return result;
		} else {
			final Array2DRowRealMatrix theMatrix = new Array2DRowRealMatrix(getCovarianceTensor(polymer));
			final EigenDecomposition theEigenMatrix = new EigenDecomposition(theMatrix);
			final RealVector eVector = theEigenMatrix.getEigenvector(0);
			final double[] doubleCheckEV = theEigenMatrix.getRealEigenvalues();

			final double[] result = new double[5];
			result[0] = doubleCheckEV[0];
			result[1] = doubleCheckEV[1];
			result[2] = doubleCheckEV[2];
			result[3] = Math.atan(eVector.getEntry(1) / eVector.getEntry(0));
			result[4] = Math.acos(eVector.getEntry(2));
			return result;
		}
	}

	// --- utilities --- //

	private static double[][] getCovarianceTensor(Polymer polymer) {
		MonomerDimension dimension = polymer.getRoot().getDimension();
		if (dimension == MonomerDimension.TWO_DIMENSIONAL
				|| dimension == MonomerDimension.TWO_DIMENSIONAL_RECTANGULAR) {
			final double[] theCOM = getCOM(polymer);
			int numMonomers = 0;
			double xDisp = 0;
			double xyDisp = 0;
			double yDisp = 0;

			Monomer current = polymer.getRoot();
			while (current != null) {
				xDisp += Math.pow(current.getXCoor() - theCOM[0], 2);
				yDisp += Math.pow(current.getYCoor() - theCOM[1], 2);
				xyDisp += ((current.getXCoor() - theCOM[0]) * (current.getYCoor() - theCOM[1]));
				numMonomers++;
				current = current.getNext();
			}

			final double[][] results = new double[2][2];
			results[0][0] = (xDisp / numMonomers);
			results[1][1] = (yDisp / numMonomers);
			results[0][1] = (xyDisp / numMonomers);
			results[1][0] = (xyDisp / numMonomers);
			return results; // this is the covariance tensor

		} else {
			final double[] theCOM = getCOM(polymer);
			int numMonomers = 0;
			double xxDisp = 0;
			double yyDisp = 0;
			double zzDisp = 0;
			double xyDisp = 0;
			double xzDisp = 0;
			double yzDisp = 0;

			Monomer current = polymer.getRoot();
			while (current != null) {
				xxDisp += Math.pow(current.getXCoor() - theCOM[0], 2);
				yyDisp += Math.pow(current.getYCoor() - theCOM[1], 2);
				zzDisp += Math.pow(current.getZCoor() - theCOM[2], 2);
				xyDisp += ((current.getXCoor() - theCOM[0]) * (current.getYCoor() - theCOM[1]));
				xzDisp += ((current.getXCoor() - theCOM[0]) * (current.getZCoor() - theCOM[2]));
				yzDisp += ((current.getYCoor() - theCOM[1]) * (current.getZCoor() - theCOM[2]));
				numMonomers++;
				current = current.getNext();
			}

			final double[][] results = new double[3][3];
			results[0][0] = xxDisp / numMonomers;
			results[1][1] = yyDisp / numMonomers;
			results[2][2] = zzDisp / numMonomers;
			results[1][0] = xyDisp / numMonomers;
			results[2][0] = xzDisp / numMonomers;
			results[2][1] = yzDisp / numMonomers;
			results[0][1] = xyDisp / numMonomers;
			results[0][2] = xzDisp / numMonomers;
			results[1][2] = yzDisp / numMonomers;
			return results; // this is the covariance tensor
		}
	}

	public static double det(final double[][] b) {
		final Array2DRowRealMatrix theMatrix = new Array2DRowRealMatrix(b);
		final EigenDecomposition theEigenMatrix = new EigenDecomposition(theMatrix);
		return theEigenMatrix.getDeterminant();
	}

	public static double trace(final double[][] covarianceTensor) {
		final double result = covarianceTensor[0][0] + covarianceTensor[1][1] + covarianceTensor[2][2];
		return result;
	}
}