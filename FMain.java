import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class FMain extends Application {

	// Files with matching descriptions
	public static HashMap<File, String> files = loadFiles();
	public static File f;
	public static double dt;
	
	
	public static HashMap<File, String> loadFiles() {
		HashMap<File, String> hm = new HashMap<>();
		try {
			File datafiles = new File(Main.class.getClassLoader().getResource("files.txt").getFile());
			BufferedReader br = new BufferedReader(new FileReader(datafiles));
			br.readLine();
			String s = null;
			while ((s = br.readLine()) != null) {
				String s1 = s.substring(0, s.indexOf(' '));
				String s2 = s.substring(s.indexOf(' ') + 1);
				File f = new File(Main.class.getClassLoader().getResource(s1).getFile());
				hm.put(f, s2);
			}

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return hm;

	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		stage.setTitle("N-Body Galaxy Simulator");
		Scene scene = createScene();

		stage.setScene(scene);
		stage.setFullScreen(false);
		
		stage.getIcons().add(
				   new Image(getClass().getClassLoader().getResourceAsStream("milky-way.png")));
				
		stage.show();

	}

	public static Scene createScene() {
		HBox panel = new HBox(20);
		panel.setAlignment(Pos.TOP_CENTER);
		panel.setPadding(new Insets(10, 10, 10, 10));

		HBox a_panel = new HBox(20);
		a_panel.setAlignment(Pos.TOP_CENTER);
		a_panel.setPadding(new Insets(10, 10, 10, 10));

		// Slider for Adjusting dt values
		Slider slider = new Slider(0, 10, 0.5);
		slider.setShowTickLabels(true);
		slider.setShowTickMarks(true);
		slider.setMajorTickUnit(0.5);
		slider.setMinorTickCount(5);
		slider.setBlockIncrement(0.1);
		Label slider_prompt = new Label("Adjust dt value: ");
		Label slider_label = new Label(String.format("%.1f", slider.getValue()));
		slider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				slider_label.setText(String.format("%.1f", newValue));
			}
		});
		slider.setOnMouseEntered(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				slider_label.setTextFill(Color.CORNFLOWERBLUE);
			}
		});
		slider.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				slider_label.setTextFill(Color.WHITE);
			}
		});

		// ComboBox for displaying files
		ComboBox<File> cb = new ComboBox<>();
		Set<File> keys = files.keySet();
		for (File key : keys) {
			cb.getItems().add(key);
		}
		Label files_label = new Label("Select file: ");
		cb.getSelectionModel().selectFirst();

		// Description of files
		TextField tf = new TextField(files.get(cb.getValue()));
		tf.setEditable(false);
		tf.setAlignment(Pos.CENTER);
		tf.setFont(Font.font("Times New Roman", 20));
		cb.valueProperty().addListener(new ChangeListener<File>() {
			@Override
			public void changed(ObservableValue<? extends File> arg0, File arg1, File arg2) {
				// TODO Auto-generated method stub
				tf.setText(files.get(arg2));
				FMain.f = arg2;
			}
		});

		// Drawing canvas
		HBox canvas_h = new HBox(10);

		Canvas brute_c = new Canvas(512, 512);
		Canvas barnes_c = new Canvas(512, 512);

		canvas_h.getChildren().addAll(brute_c, barnes_c);

		GraphicsContext brute_gc = brute_c.getGraphicsContext2D();
		GraphicsContext barnes_gc = barnes_c.getGraphicsContext2D();

		brute_gc.setFill(Color.BLACK);
		barnes_gc.setFill(Color.BLACK);

		brute_gc.fillRect(0, 0, 512, 512);
		barnes_gc.fillRect(0, 0, 512, 512);

		// Button to start action
		Button b = new Button("Start simulation together");
		Button ba = new Button("Barnes Simulation");
		Button br = new Button("Brute Simulation");

		ba.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				dt = (double) Math.round(slider.getValue() * 100) / 100;
				f = cb.getValue();
				barnes_gc.clearRect(0, 0, 512, 512);
				brute_gc.clearRect(0, 0, 512, 512);

				if (FNBody.timer != null)
					FNBody.timer.stop();
				if (FNBodyBrute.timer != null)
					FNBodyBrute.timer.stop();

				barnes_gc.setFill(Color.BLACK);
				barnes_gc.fillRect(0, 0, 512, 512);
				brute_gc.setFill(Color.BLACK);
				brute_gc.fillRect(0, 0, 512, 512);

				FNBody.drawBodies(barnes_gc);
			}
		});

		br.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				dt = (double) Math.round(slider.getValue() * 100) / 100;
				f = cb.getValue();
				barnes_gc.clearRect(0, 0, 512, 512);
				brute_gc.clearRect(0, 0, 512, 512);

				if (FNBody.timer != null)
					FNBody.timer.stop();
				if (FNBodyBrute.timer != null)
					FNBodyBrute.timer.stop();

				barnes_gc.setFill(Color.BLACK);
				barnes_gc.fillRect(0, 0, 512, 512);
				brute_gc.setFill(Color.BLACK);
				brute_gc.fillRect(0, 0, 512, 512);

				FNBodyBrute.drawBodies(brute_gc);
			}
		});

		b.setOnAction(new EventHandler<ActionEvent>() {
			@Override

			public void handle(ActionEvent arg0) {
				dt = (double) Math.round(slider.getValue() * 100) / 100;
				f = cb.getValue();
				barnes_gc.clearRect(0, 0, 512, 512);
				brute_gc.clearRect(0, 0, 512, 512);

				if (FNBody.timer != null)
					FNBody.timer.stop();
				if (FNBodyBrute.timer != null)
					FNBodyBrute.timer.stop();

				barnes_gc.setFill(Color.BLACK);
				barnes_gc.fillRect(0, 0, 512, 512);
				brute_gc.setFill(Color.BLACK);
				brute_gc.fillRect(0, 0, 512, 512);

				FNBodyBrute.drawBodies(brute_gc);
				FNBody.drawBodies(barnes_gc);
			}
		});

		// Panes
		panel.getChildren().addAll(slider_prompt, slider_label, slider, files_label, cb);
		VBox vbox = new VBox(5);
		vbox.setAlignment(Pos.CENTER);
		HBox buttons_panel = new HBox(ba,br,b);
		buttons_panel.setAlignment(Pos.CENTER);
		buttons_panel.setPadding(new Insets(10));
		vbox.setPadding(new Insets(5));
		vbox.getChildren().addAll(panel, tf, buttons_panel, a_panel, canvas_h);

		Scene scene = new Scene(vbox);

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.X && (FNBodyBrute.timer != null || FNBody.timer != null)) {
					if (FNBody.timer != null)
						FNBody.timer.stop();
					if (FNBodyBrute.timer != null)
						FNBodyBrute.timer.stop();
					barnes_gc.clearRect(0, 0, 512, 512);
					barnes_gc.setFill(Color.BLACK);
					barnes_gc.fillRect(0, 0, 512, 512);
					brute_gc.clearRect(0, 0, 512, 512);
					brute_gc.setFill(Color.BLACK);
					brute_gc.fillRect(0, 0, 512, 512);
				}
			}
		});

		scene.getStylesheets().add(Main.class.getResource("Main.css").toExternalForm());
		return scene;
	}

	public static void createAnimation() {

	}

}
