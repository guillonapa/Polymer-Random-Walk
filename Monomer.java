public class Monomer{
    //FIELDS

    private int dimension;

    private double xCoor;
    private double yCoor;
    private double zCoor;
    
    private int xCoorInt;
    private int yCoorInt;
    
    private int orienInt;

    private double angle;
    private double secAngle;

    private Monomer previous;
    private Monomer next;

    //METHODS

    //Constructor: angle should be between 0 and 2π
    public Monomer(int dimension) {

        this.dimension = dimension;

        this.xCoor = 0;
        this.yCoor = 0;
        this.zCoor = 0;
        

        this.angle = Math.random() * 2 * Math.PI;

        if (dimension == 3) {
            this.secAngle = Math.random() * Math.PI;
        }
        else {
            this.secAngle = 0;
        }

        this.previous = null;
        this.next = null;

    }

    public Monomer() {

        this.dimension = 1;

        this.xCoorInt = 0;
        this.yCoorInt = 0;
        

        this.orienInt = (int)(Math.random() * 4);
        
        this.secAngle = 0;

        this.previous = null;
        this.next = null;

    }
    
    public void setCoors(int potX, int potY) {
    	
    	this.xCoorInt = potX;
    	this.yCoorInt = potY;
    	
    }

    public void setCoordinates(double prevX, double prevY) {
        this.xCoor = prevX + Math.cos(this.angle);
        this.yCoor = prevY + Math.sin(this.angle);
    }

    public void setCoordinates(double prevX, double prevY, boolean hey) {
        this.xCoor = prevX + Math.rint(Math.cos(this.angle));
        this.yCoor = prevY + Math.rint(Math.sin(this.angle));
    }

    public void setCoordinates(double prevX, double prevY, double prevZ) {
        this.xCoor = prevX + Math.sin(this.secAngle) * Math.cos(this.angle);
        this.yCoor = prevY + Math.sin(this.secAngle) * Math.sin(this.angle);
        this.zCoor = prevZ + Math.cos(this.secAngle);
    }

    public void setXCoor(double xCoor) {
        this.xCoor = xCoor;
    }

    public void setYCoor(double yCoor) {
        this.yCoor = yCoor;
    }

    public void setZCoor(double zCoor) {
        this.zCoor = zCoor;
    }

    public void setNewAngle() {
        this.angle = Math.random() * 2 * Math.PI;
    }
    
    public void setNewOrienInt() {
        this.orienInt = (int)(Math.random() * 4);
    }

    public void setNewSecAngle() {
        this.secAngle = Math.random() * Math.PI;
    }

    public void setPrevious(Monomer previous) {
        this.previous = previous;
    }

    public void setNext(Monomer next) {
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

    public int getDimension() {
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
        if (this.dimension == 2) {
            return this.xCoor + ", " + this.yCoor;
        }
        else if (this.dimension == 1) {
            return this.xCoorInt + ", " + this.yCoorInt;
        }
        else {
            return this.xCoor + ", " + this.yCoor + ", " + this.zCoor;
        }
    }

}
