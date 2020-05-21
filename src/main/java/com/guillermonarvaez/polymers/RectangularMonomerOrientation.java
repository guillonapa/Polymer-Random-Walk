package com.guillermonarvaez.polymers;

public enum RectangularMonomerOrientation {

    X_POSITIVE("Positive X-Axis"),
    Y_POSITIVE("Positive Y-Axis"),
    X_NEGATIVE("Negative X-Axis"),
    Y_NEGATIVE("Negative Y-Axis");

    // a human readable name
    private String name;

    private RectangularMonomerOrientation(String name) {
        this.name = name;
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
    public static RectangularMonomerOrientation randomRectangularOrientation() {
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
}