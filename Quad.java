public class Quad {
	private double length;
	private double xmid;
	private double ymid;
    // create a new square with the given parameters (assume it is square)
    public Quad(double xmid, double ymid, double length) {
    	this.xmid = xmid;
    	this.ymid = ymid;
    	this.length = length;
    }

    // return the length of one side of the square quadrant
    public double length() {
    	return length;
    }

    // does quadrant contain (x, y)?
    public boolean contains(double x, double y) {
    	boolean xflag = xmid - length/2 <= x && x <= xmid + length/2.0;
		boolean yflag = ymid - length/2 <= y && y <= ymid + length/2.0;
		return xflag && yflag;
    }

    public Quad NW() {
		return new Quad(xmid - length/4, ymid + length/4, length/2);
	}

	public Quad NE() {
		return new Quad(xmid + length/4, ymid + length/4, length/2);
	}

	public Quad SW() {
		return new Quad(xmid - length/4, ymid - length/4, length/2);
	}

	public Quad SE() {
		return new Quad(xmid + length/4, ymid - length/4, length/2);
	}
    
    // draw an unfilled rectangle that represents the quadrant
    public void draw() {
    	StdDraw.rectangle(xmid, ymid, length/2, length/2);
    }

    // return a string representation of the quadrant for debugging
    public String toString() {
    	return "Length: " + length +  " x, y coords. are " + xmid
    			+ " "  + ymid;
    }

}
