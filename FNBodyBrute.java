import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class FNBodyBrute {
	static File f;
	private static int ctr = 0;
	static double t = 0;
	static final String str = "No. of operations in Brute Force O(n^2) is: ";
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

		timer = new AnimationTimer() {

			@Override
			public void handle(long now) {
				// Computes force

				for (int i = 0; i < N; i++) {
					bodies[i].resetForce();
					for (int j = 0; j < N; j++) {
						ctr++;
						if (i != j)
							bodies[i].addForce(bodies[j]);
					}
				}
				// update the bodies
	            for (int i = 0; i < N; i++) {
	                bodies[i].update(FMain.dt);
	                ctr++;
	            }
				
				gc.setFill(Color.BLACK);
				gc.fillRect(0, 0, 512, 512);
				// update the bodies with displacement
				for (FBody body : bodies) {
					double xp = (body.rx + radius) * 512 / (2*radius);

					double yp = (body.ry - radius) * -512 / (2*radius);
					gc.setFill(body.color);
					gc.fillOval(xp, yp, 1, 1);
					
				}
				
				gc.setFill(Color.HOTPINK);
				gc.fillText(str + ctr, 20, 20);
			}
		};
		timer.start();

	}
	//
	// public static void bruteCompute(Pane pane) {
	//
	// // primaryStage.initModality(Modality.APPLICATION_MODAL);
	// Scanner in = null;
	// try {
	// in = new Scanner(Main.f);
	// } catch (FileNotFoundException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// int N = in.nextInt(); // number of particles
	// double radius = in.nextDouble(); // radius of universe
	// ymin = xmin = -radius;
	// xmax = ymax = +radius;
	//
	// Label counter = new Label();
	// counter.setTextAlignment(TextAlignment.LEFT);
	// counter.setTextFill(Color.DEEPPINK);
	// counter.setFont(new Font("Calibri", 16));
	//
	// // bpane.setCenter(pane);
	// pane.setMinWidth(DEFAULT_SIZE);
	// pane.setMinHeight(DEFAULT_SIZE);
	// pane.setBackground(new Background(new BackgroundFill(Color.BLACK, null,
	// null)));
	//
	// FBody[] bodies = new FBody[N]; // array of N bodies
	// for (int i = 0; i < N; i++) {
	// double px = in.nextDouble();
	// double py = in.nextDouble();
	// double vx = in.nextDouble();
	// double vy = in.nextDouble();
	// double mass = in.nextDouble();
	// int red = in.nextInt();
	// int green = in.nextInt();
	// int blue = in.nextInt();
	// Color color = new Color(red / 255, green / 255, blue / 255, 1);
	// bodies[i] = new FBody(px, py, vx, vy, mass);
	// bodies[i].circle = new Circle(scaleX(px), scaleY(py), 0.7, color);
	// bodies[i].circle.setEffect(new Lighting());
	// bodies[i].circle.setBlendMode(BlendMode.ADD);
	// pane.getChildren().add(bodies[i].circle);
	//
	// }
	// pane.getChildren().add(counter);
	// in.close();
	//
	// AnimationTimer timer = new AnimationTimer() {
	//
	// @Override
	// public void handle(long now) {
	// // Computes force
	//
	// for (int i = 0; i < N; i++) {
	// bodies[i].resetForce();
	// for (int j = 0; j < N; j++) {
	// ctr++;
	// if (i != j)
	// bodies[i].addForce(bodies[j]);
	// }
	// }
	// // update the bodies with displacement
	// for (int i = 0; i < N; i++) {
	//
	// counter.setText(str + ctr++);
	// double xp = scaleX(bodies[i].rx);
	// double yp = scaleY(bodies[i].ry);
	// bodies[i].update(Main.dt);
	// xp = scaleX(bodies[i].rx) - xp;
	// yp = scaleY(bodies[i].ry) - yp;
	// bodies[i].circle.setCenterX(bodies[i].circle.getCenterX() + xp);
	// bodies[i].circle.setCenterY(bodies[i].circle.getCenterY() + yp);
	//
	// // System.out.println(bodies[i].circle.getCenterX() + ", " +
	// // bodies[i].circle.getCenterY());
	// }
	// t += dt;
	//
	// }
	// };
	// timer.start();
	//
	// }

}
