package com.guillermonarvaez.polymers;

import org.apache.commons.math3.util.Pair;

/**
 * A representation of a monomer.
 */
public class Monomer {
    
    // monomer's dimension
    private final MonomerDimension dimension;
    
    // the orientation
    private RectangularMonomerOrientation rectangularOrientation;
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
        if (dimension == MonomerDimension.TWO_DIMENSIONAL_RECTANGULAR) {
            rectangularOrientation = RectangularMonomerOrientation.randomRectangularOrientation();
        } else {
            orientation = new MonomerOrientation();
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
        rectangularOrientation = RectangularMonomerOrientation.randomRectangularOrientation();
    }

     /**
     * Set a new random rectangular orientation.
     */
    public void setNewRectangularOrientation(Pair<Integer, Integer> nextVertex) {
        int tailX = nextVertex.getFirst();
        int tailY = nextVertex.getSecond();
        if (tailX > xCoorInt) {
            rectangularOrientation = RectangularMonomerOrientation.X_POSITIVE;
        } else if (tailX < xCoorInt) {
            rectangularOrientation = RectangularMonomerOrientation.X_NEGATIVE;
        } else if (tailY > yCoorInt) {
            rectangularOrientation = RectangularMonomerOrientation.Y_POSITIVE;
        } else if (tailY < yCoorInt) {
            rectangularOrientation = RectangularMonomerOrientation.Y_NEGATIVE;
        }
    }
    
    // --- getters --- //

    /**
     * Returns the monomer's rectangular orientation. This can be pointing
     * in the direction of the positive x-axis, the positive y-axis, the
     * negative x-axis, or the negative y-axis.
     * 
     * @return the monomer's rectangular orientation.
     */
    public RectangularMonomerOrientation getRectangularOrientation() {
        return rectangularOrientation;
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
     * Get a pair representing the start of this monomer, using its current
     * coordinates.
     */
    public Pair<Integer, Integer> getHeadCoors() {
        return new Pair<Integer,Integer>(getX(), getY());
    }

    /**
     * Get a pair representing the end of this monomer, using its current
     * coordinates and orientation.
     */
    public Pair<Integer, Integer> getTailCoors() {
        switch (rectangularOrientation) {
            case X_POSITIVE:
                return new Pair<Integer,Integer>(getX() + 1, getY());
            case Y_POSITIVE:
                return new Pair<Integer,Integer>(getX(), getY() + 1);
            case X_NEGATIVE:
                return new Pair<Integer,Integer>(getX() - 1, getY());
            case Y_NEGATIVE:
                return new Pair<Integer,Integer>(getX(), getY() - 1);
            default:
                break;
        }
        return null;
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
