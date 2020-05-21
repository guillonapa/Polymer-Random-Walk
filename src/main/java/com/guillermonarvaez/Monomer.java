package com.guillermonarvaez;

public class Monomer {
    
    // monomer's dimension
    private final MonomerDimension dimension;
    
    // the orientation
    private MonomerOrientation orientation;

    // exact coordinates (two and three dimensional)
    private double xCoor;
    private double yCoor;
    private double zCoor;

    // rectangular coordinates
    private int xCoorInt;
    private int yCoorInt;

    // linked list references
    private Monomer previous;
    private Monomer next;

    /**
     * Creates an instance of the monomer where the angle from the
     * x-axis is randomly selected between 0 and 2π, and the angle
     * from the x-y plane is randomly selected between 0 and π.
     * 
     * @param dimension the dimension of the monomer
     */
    public Monomer(final MonomerDimension dimension) {
        this.dimension = dimension;
        switch (dimension) {
            case TWO_DIMENSIONAL_RECTANGULAR:
                orientation = MonomerOrientation.randomRectangularOrientation();
                break;
            case TWO_DIMENSIONAL:
                orientation = MonomerOrientation.PLANAR;
                break;
            case THREE_DIMENSIONAL:
                orientation = MonomerOrientation.SPHERICAL;
                break;
        }
    }

    /**
     * Creates an instance of a two dimensional rectangular monomer.
     */
    public Monomer() {
        this(MonomerDimension.TWO_DIMENSIONAL_RECTANGULAR);
    }

    // --- setters --- //

    /**
     * Set the coordinates of the monomer.
     * 
     * @param xCoorInt the x coordinate
     * @param yCoorInt the y coordinate
     */
    public void setCoors(final int xCoorInt, final int yCoorInt) {
        this.xCoorInt = xCoorInt;
        this.yCoorInt = yCoorInt;
    }

    /**
     * Set the new coordinates from the previous monomer's coordinates
     * and this monomer's orientation angle.
     * 
     * @param prevX the previous monomer's x coordinate
     * @param prevY the previous monomer's y coordinate
     */
    public void setCoorsFromPrev(final double prevX, final double prevY) {
        xCoor = prevX + Math.cos(orientation.getPlaneAngle());
        yCoor = prevY + Math.sin(orientation.getPlaneAngle());
    }

    /**
     * Set the new coordinates from the previous monomer's coordinates
     * and this monomer's orientation angles.
     * 
     * @param prevX the previous monomer's x coordinate
     * @param prevY the previous monomer's y coordinate
     * @param prevZ the previous monomer's z coordinate
     */
    public void setCoorsFromPrev(final double prevX, final double prevY, final double prevZ) {
        xCoor = prevX + Math.sin(orientation.getNormalAngle()) * Math.cos(orientation.getPlaneAngle());
        yCoor = prevY + Math.sin(orientation.getNormalAngle()) * Math.sin(orientation.getPlaneAngle());
        zCoor = prevZ + Math.cos(orientation.getNormalAngle());
    }

    /**
     * Set the reference to the previous monomer.
     * 
     * @param previous the previous monomer
     */
    public void setPrevious(final Monomer previous) {
        this.previous = previous;
    }
    
    /**
     * Set the reference to the next monomer.
     * 
     * @param next the next monomer
     */
    public void setNext(final Monomer next) {
        this.next = next;
    }

    /**
     * Set a new random rectangular orientation.
     */
    public void setNewRectangularOrientation() {
        orientation = MonomerOrientation.randomRectangularOrientation();
    }
    
    // --- getters --- //

    /**
     * Returns the monomer's rectangular orientation. This can be pointing
     * in the direction of the positive x-axis, the positive y-axis, the
     * negative x-axis, or the negative y-axis.
     * 
     * @return the monomer's rectangular orientation.
     */
    public MonomerOrientation getRectangularOrientation() {
        return orientation;
    }

    /**
     * Get the x coordinate (rectangular).
     */
    public int getX() {
    	return this.xCoorInt;
    }
    
    /**
     * Get the y coordinate (rectangular).
     */
    public int getY() {
    	return this.yCoorInt;
    }

    /**
     * Get the monomer's dimension.
     */
    public MonomerDimension getDimension() {
        return dimension;
    }

    /**
     * Get the x coordinate.
     */
    public double getXCoor() {
        return this.xCoor;
    }

    /**
     * Get the y coordinate.
     */
    public double getYCoor() {
        return this.yCoor;
    }

    /**
     * Get the z coordinate.
     */
    public double getZCoor() {
        return this.zCoor;
    }

    /**
     * Get the orientation of the monomer in the x-y plane
     * (angle from the x-axis).
     */
    public double getPlaneAngle() {
        return orientation.getPlaneAngle();
    }

    /**
     * Get the angle from the z-axis (normal to the x-y plane).
     */
    public double getNormalAngle() {
        return orientation.getNormalAngle();
    }

    /**
     * Get the previous monomer linked to this one (if any).
     */
    public Monomer getPrevious() {
        return this.previous;
    }

    /**
     * Get the next monomer linked to this one (if any).
     */
    public Monomer getNext() {
        return this.next;
    }

    // --- utilities --- //

    /**
     * Return a string representation of the monomer: a comma-delimited string
     * with the coordinates of the monomer.
     */
    public String toString() {
        switch (dimension) {
            case TWO_DIMENSIONAL_RECTANGULAR:
                return String.format("%d, %d", xCoorInt, yCoorInt);
            case TWO_DIMENSIONAL:
                return String.format("%f, %f", xCoor, yCoor);
            case THREE_DIMENSIONAL:
                return String.format("%f, %f, %f", xCoor, yCoor, zCoor);
            default:
                return super.toString();
        }
    }

}
