import java.awt.Color;
import java.io.File;
import java.util.Scanner;

public class NBody {
	static double dt;
	static File f;
	
	
	public static void main(String[] args) {
		
		dt = 0.1;
		System.out.println("Simulating..");
		Scanner in = null;
		try {
			in = new Scanner(System.in);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int ctr = 0;
		String str = "Number of operations "; 
		//final double dt = 0.1; // time quantum
		int N = in.nextInt(); // number of particles
		double radius = in.nextDouble(); // radius of universe

		// turn on animation mode and rescale coordinate system
		StdDraw.show(0);
		StdDraw.setXscale(-radius, +radius);
		StdDraw.setYscale(-radius, +radius);

		// read in and initialize bodies
		Body[] bodies = new Body[N]; // array of N bodies
		for (int i = 0; i < N; i++) {
			double px = in.nextDouble();
			double py = in.nextDouble();
			double vx = in.nextDouble();
			double vy = in.nextDouble();
			double mass = in.nextDouble();
			int red = in.nextInt();
			int green = in.nextInt();
			int blue = in.nextInt();
			Color color = new Color(red, green, blue);
			bodies[i] = new Body(px, py, vx, vy, mass, color);
		}
		
		in.close();
		// simulate the universe
		
		for (double t = 0.0; true; t = t + dt) {
			
	//		double t1 = System.currentTimeMillis();
			
			Quad quad = new Quad(0, 0, radius * 2);
			BHTree tree = new BHTree(quad);

			// Insert bodies and reset their force
			for (Body body : bodies) {
				if (body.in(quad))
				tree.insert(body);
				ctr++;
			}
			
			if (t == 0.0) {
				System.out.println(tree);
			}

			// Update their forces
			for (Body body : bodies) {
				body.resetForce();
				tree.updateForce(body);
				body.update(dt);
				System.out.println("# " + body);
				ctr++;
			}
			// draw the bodies
			StdDraw.clear(StdDraw.BLACK);
			for (Body body : bodies) {
				body.draw();
			}
			StdDraw.textRight(radius - 10, radius - 10,str + ctr);
			
			StdDraw.show(10);
			//double t2 = System.currentTimeMillis();
			//System.out.println(t2 -t1);
		}		
		
	}

}
