
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class FNBody {
	static int ctr = 0;
	static double dt;
	static int t = 0;
	final static String str = "No. of operations in O(N log N): ";
	//private static final int DEFAULT_SIZE = 512;
	static AnimationTimer timer = null;

	public static void drawBodies(GraphicsContext gc) {
		Scanner in = null;
		try {
			in = new Scanner(FMain.f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int N = in.nextInt(); // number of particles
		double radius = in.nextDouble(); // radius of universe

		FBody[] bodies = new FBody[N]; // array of N bodies
		for (int i = 0; i < N; i++) {
			double px = in.nextDouble();
			double py = in.nextDouble();
			double vx = in.nextDouble();
			double vy = in.nextDouble();
			double mass = in.nextDouble();
			int red = in.nextInt();
			int green = in.nextInt();
			int blue = in.nextInt();
			Color color = new Color(red / 255, green / 255, blue / 255, 1);
			bodies[i] = new FBody(px, py, vx, vy, mass, color);
		}

		in.close();

		// simulate the universe
		timer = new AnimationTimer() {

			@Override
			public void handle(long now) {

				// Computes force
				Quad quad = new Quad(0, 0, radius * 2);
				FBHTree tree = new FBHTree(quad);

				// Insert bodies
				for (FBody body : bodies) {
					if (body.in(quad))
						tree.insert(body);
					ctr++;
				}
				gc.setFill(Color.BLACK);
				gc.fillRect(0, 0, 512, 512);

				// Update their forces
				for (FBody body : bodies) {
					body.resetForce();
					tree.updateForce(body);
					ctr++;
					body.update(FMain.dt);
					double xp = (body.rx + radius) * 512 / (2 * radius);

					double yp = (body.ry - radius) * -512 / (2 * radius);

					gc.setFill(body.color);
					gc.fillOval(xp, yp, 1, 1);
					gc.setFill(body.color);
					gc.fillOval(xp, yp, 1, 1);

				}
				gc.setFill(Color.SKYBLUE);
				gc.fillText(str + ctr, 20, 20);

				// t += FMain.dt;
				// t1 = System.currentTimeMillis() - t1;
				// System.out.println(t1);
				// timer.stop();
				// while (System.currentTimeMillis() - t1 < 10) {
				// }
				// timer.start();

			}
		};
		timer.start();

	}

}
