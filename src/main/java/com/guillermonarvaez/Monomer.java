package com.guillermonarvaez;

public class Monomer {
    
    // monomer's dimension
    private final MonomerDimension dimension;

    // exact coordinates (two and three dimensional)
    private double xCoor;
    private double yCoor;
    private double zCoor;

    // rectangular coordinates
    private int xCoorInt;
    private int yCoorInt;

    // ???
    private int orienInt;

    // angle from the x-axis in x-y plane ???
    private double angle;

    // angle from the x-y plane ???
    private double secAngle;

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
        angle = Math.random() * 2 * Math.PI;
        if (dimension == MonomerDimension.TWO_DIMENSIONAL_RECTANGULAR) {
            orienInt = (int) (Math.random() * 4);
        }
        if (dimension == MonomerDimension.THREE_DIMENSIONAL) {
            secAngle = Math.random() * Math.PI;
        }
    }

    /**
     * Creates an instance of a two dimensional rectangular monomer.
     */
    public Monomer() {
        this(MonomerDimension.TWO_DIMENSIONAL_RECTANGULAR);
    }

    // --- setters ---

    public void setCoors(final int xCoorInt, final int yCoorInt) {
        this.xCoorInt = xCoorInt;
        this.yCoorInt = yCoorInt;
    }

    public void setCoordinates(final double prevX, final double prevY) {
        this.xCoor = prevX + Math.cos(this.angle);
        this.yCoor = prevY + Math.sin(this.angle);
    }

    public void setCoordinates(final double prevX, final double prevY, final boolean hey) {
        this.xCoor = prevX + Math.rint(Math.cos(this.angle));
        this.yCoor = prevY + Math.rint(Math.sin(this.angle));
    }

    public void setCoordinates(final double prevX, final double prevY, final double prevZ) {
        this.xCoor = prevX + Math.sin(this.secAngle) * Math.cos(this.angle);
        this.yCoor = prevY + Math.sin(this.secAngle) * Math.sin(this.angle);
        this.zCoor = prevZ + Math.cos(this.secAngle);
    }

    public void setXCoor(final double xCoor) {
        this.xCoor = xCoor;
    }

    public void setYCoor(final double yCoor) {
        this.yCoor = yCoor;
    }

    public void setZCoor(final double zCoor) {
        this.zCoor = zCoor;
    }

    public void setNewAngle() {
        this.angle = Math.random() * 2 * Math.PI;
    }

    public void setNewOrienInt() {
        this.orienInt = (int) (Math.random() * 4);
    }

    public void setNewSecAngle() {
        this.secAngle = Math.random() * Math.PI;
    }

    public void setPrevious(final Monomer previous) {
        this.previous = previous;
    }

    public void setNext(final Monomer next) {
        this.next = next;
    }
    
    public int getX() {
    	return this.xCoorInt;
    }
    
    public int getY() {
    	return this.yCoorInt;
    }
    
    public int getOrienInt() {
    	return this.orienInt;
    }

    public MonomerDimension getDimension() {
        return this.dimension;
    }

    public double getXCoor() {
        return this.xCoor;
    }

    public double getYCoor() {
        return this.yCoor;
    }

    public double getZCoor() {
        return this.zCoor;
    }

    public double getAngle() {
        return this.angle;
    }

    public double getSecAngle() {
        return this.secAngle;
    }

    public Monomer getPrevious() {
        return this.previous;
    }

    public Monomer getNext() {
        return this.next;
    }

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
