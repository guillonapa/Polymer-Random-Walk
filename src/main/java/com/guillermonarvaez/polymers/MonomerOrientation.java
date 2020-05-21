package com.guillermonarvaez.polymers;

public class MonomerOrientation {
    
    // angle from the x-axis in x-y plane
    private double planeAngle;
    // angle from the z-axis (normal to the x-y plane)
    private double normalAngle;

    public MonomerOrientation() {
        generateRandomPlaneAngle();
        generateRandomNormalAngle();
    }

    /**
     * Generates a new plane angle (along the x-y plane) for this
     * orientation.
     */
    public void generateRandomPlaneAngle() {
        planeAngle = Math.random() * 2 * Math.PI;
    }

    /**
     * Generates a new normal angle (from the z-axis) for this
     * orientation.
     */
    public void generateRandomNormalAngle() {
        normalAngle = Math.random() * Math.PI;
    }

    /**
     * Get the angle from the x-axis in x-y plane.
     */
    public double getPlaneAngle() {
        return planeAngle;
    }

    /**
     * Get the angle from the z-axis (normal to the x-y plane).
     */
    public double getNormalAngle() {
        return normalAngle;
    }
}