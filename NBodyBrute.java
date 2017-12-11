import java.awt.Color;
import java.util.Scanner;

public class NBodyBrute {

    public static void main(String[] args) {
        final double dt = 0.1;                     // time quantum
        
        Scanner in = null;
		try {
			in = new Scanner(Main.f);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        int N = in.nextInt();                   // number of particles
        double radius = in.nextDouble();        // radius of universe
        int ctr = 0;
        
        // turn on animation mode and rescale coordinate system
        StdDraw.show(0);
        StdDraw.setXscale(-radius, +radius);
        StdDraw.setYscale(-radius, +radius);

        // read in and initialize bodies
        Body[] bodies = new Body[N];               // array of N bodies
        for (int i = 0; i < N; i++) {
            double px   = in.nextDouble();
            double py   = in.nextDouble();
            double vx   = in.nextDouble();
            double vy   = in.nextDouble();
            double mass = in.nextDouble();
            int red     = in.nextInt();
            int green   = in.nextInt();
            int blue    = in.nextInt();
            Color color = new Color(red, green, blue);
            bodies[i]   = new Body(px, py, vx, vy, mass, color);
        }

        in.close();
        // simulate the universe
        for (double t = 0.0; true; t = t + dt) {

            // update the forces
            for (int i = 0; i < N; i++) {
                bodies[i].resetForce();
                for (int j = 0; j < N; j++) {
                    if (i != j) {
                    	bodies[i].addForce(bodies[j]);
                    	ctr++;
                    }
                }
            }

            // update the bodies
            for (int i = 0; i < N; i++) {
                bodies[i].update(dt);
                ctr++;
            }

            // draw the bodies
            StdDraw.clear(StdDraw.BLACK);
            for (int i = 0; i < N; i++) {
                bodies[i].draw();
            }
            StdDraw.textRight(radius - 10, radius - 10, "Number of operations " + ctr);
            StdDraw.show(10);

        }
    }
}
