import java.awt.Color;

public class Body {
    private static final double G = 6.67e-11;   // gravational constant

    private double rx, ry;       // position
    private double vx, vy;       // velocity
    private double fx, fy;       // force
    private double mass;         // mass
    private Color color;         // color

    // create and initialize a new Body
    public Body(double rx, double ry, double vx, double vy, double mass, Color color) {
        this.rx    = rx;
        this.ry    = ry;
        this.vx    = vx;
        this.vy    = vy;
        this.mass  = mass;
        this.color = color;
    }
    
    

    // Whether a body contains quadrant q
    public boolean in(Quad q) {
    	return q.contains(rx, ry);
    }
    // Returns a new body based on the COM of the two bodies
    public static Body add(Body a, Body b) {
    	double M = a.mass + b.mass;
    	double x = (a.rx*a.mass + b.rx*b.mass) / M;
    	double y = (a.ry*a.mass + b.ry*b.mass) / M;
    	return new Body(x, y, a.vx, b.vx, M, null);
    }
    
    
    // update the velocity and position of the invoking Body
    // uses leapfrom method, as in Assignment 2
    public void update(double dt) {
        vx += dt * fx / mass;
        vy += dt * fy / mass;
        rx += dt * vx;
        ry += dt * vy;
    }

    // return the Euclidean distance bewteen the invoking Body and b
    public double distanceTo(Body b) {
        double dx = rx - b.rx;
        double dy = ry - b.ry;
        return Math.sqrt(dx*dx + dy*dy);
        
    }

    // reset the force of the invoking Body to 0
    public void resetForce() {
        fx = 0.0;
        fy = 0.0;
    }

    // compute the net force acting between the invoking body a and b, and
    // add to the net force acting on the invoking Body
    public void addForce(Body b) {
        Body a = this;
        double EPS = 3E4;      // softening parameter
        double dx = b.rx - a.rx;
        double dy = b.ry - a.ry;
        double dist = Math.sqrt(dx*dx + dy*dy);
        double F = (G * a.mass * b.mass) / (dist*dist + EPS*EPS);
        a.fx += F * dx / dist;
        a.fy += F * dy / dist;
    }

    // draw the invoking Body in Turtle graphics
    public void draw() {
        StdDraw.setPenColor(color);
        StdDraw.point(rx, ry);
    }

    // convert to string representation formatted nicely
    public String toString() {
        return String.format("%10.3E %10.3E %10.3E %10.3E %10.3E", rx, ry, vx, vy, mass);
    }

}
