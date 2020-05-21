package com.guillermonarvaez;

public enum MonomerOrientation {

    X_POSITIVE("Positive X-Axis"),
    Y_POSITIVE("Positive Y-Axis"),
    X_NEGATIVE("Negative X-Axis"),
    Y_NEGATIVE("Negative Y-Axis"),
    PLANAR("Two Dimensional"),
    SPHERICAL("Three Dimensional");

    // a human readable name
    private String name;
    // angle from the x-axis in x-y plane
    private double planeAngle;
    // angle from the z-axis (normal to the x-y plane)
    private double normalAngle;

    private MonomerOrientation(String name) {
        this.name = name;
        generateRandomPlaneAngle();
        generateRandomNormalAngle();
    }
    
    @Override
    public String toString() {
        return name;
    }

    /**
     * Returns a random orientation.
     * 
     * @return a random orientation
     */
    public static MonomerOrientation randomRectangularOrientation() {
        // generate a random index between 0 and 3 (including)
        int randomIndex = (int) (Math.random() * 4);
        // return the random orientation
        switch (randomIndex) {
            case 0:
                return X_POSITIVE;
            case 1:
                return Y_POSITIVE;
            case 2:
                return X_NEGATIVE;
            case 3:
                return Y_NEGATIVE;
            default:
                return null;
        }
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