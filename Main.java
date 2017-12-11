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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

	// Files with matching descriptions
	public static HashMap<File, String> files = loadFiles();
	public static File f = Main.files.keySet().iterator().next();;
	public static double dt = 0.1;

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
				File f = new File(Main.class.getClassLoader().getResource("inputs/" + s1).getFile());
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
		Scene scene = createScene();
		
		stage.setScene(scene);
		stage.setFullScreen(false);
		stage.show();

	}
	
	public static Scene createScene() {
		HBox panel = new HBox(20);
		panel.setAlignment(Pos.TOP_CENTER);
		panel.setPadding(new Insets(20, 10, 20, 10));

		HBox a_panel = new HBox(20);
		a_panel.setAlignment(Pos.TOP_CENTER);
		a_panel.setPadding(new Insets(20, 10, 20, 10));

		// Adjusting dt values
		Slider slider = new Slider(0, 2, 0.1);
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

		// Files
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
			}
		});

		// Animation Panes
//		Pane brute_pane = new Pane();
//		brute_pane.setMaxHeight(512);
//		brute_pane.setMaxWidth(512);
//		brute_pane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
//		Pane barnes_pane = new Pane();
//		barnes_pane.setMaxHeight(512);
//		barnes_pane.setMaxWidth(512);
//		barnes_pane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
//		a_panel.getChildren().addAll(brute_pane, barnes_pane);

		// Button to start action
		Button b = new Button("Start simulation");
		
		// Implementation try 1
		
//		b.setOnAction(new EventHandler<ActionEvent>() {
//			@Override
//			public void handle(ActionEvent arg0) {
//				dt = (double) Math.round(slider.getValue() * 100) / 100;
//				f = cb.getValue();
//				generateMotion(brute_pane, barnes_pane);
//
//			}
//		});
		
		
		// Implementation try 2
		
		b.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				dt = (double) Math.round(slider.getValue() * 100) / 100;
				f = cb.getValue();
				
				Runtime rt = Runtime.getRuntime();
			    try {
			        rt.exec(new String[]{"cmd.exe","/c","start","javac NBody.java",});

			    } catch (IOException e) {
			        // TODO Auto-generated catch block
			        e.printStackTrace();
			    }
				
			}
		});
		
		
		// Panes
		panel.getChildren().addAll(slider_prompt, slider_label, slider, files_label, cb);
		VBox vbox = new VBox(10);
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(10));
		vbox.getChildren().addAll(panel, tf, b, a_panel);

		Scene scene = new Scene(vbox);
//		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
//			@Override
//			public void handle(KeyEvent event) {
//				// TODO Auto-generated method stub
//				if (event.getCode().equals(KeyCode.X)) {
//					brute_pane.getChildren().removeAll();
//					barnes_pane.getChildren().removeAll();
//				}
//			}
//		});
		scene.getStylesheets().add(Main.class.getResource("Main.css").toExternalForm());
		return scene;
	}

	public static void generateMotion(Pane brute, Pane barne) {
		
	}

}
