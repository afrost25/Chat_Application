package asdf;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Start extends Application
{
	public static void main(String args[])
	{
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage)
	{
		Label label = new Label("Users");
		Label label2 = new Label("Apple");
		
		VBox panel = new VBox(label);
		VBox.setMargin(label, new Insets(20));
		VBox.setVgrow(label, Priority.NEVER);
		panel.setStyle("-fx-border-style: solid inside;"
				+ "-fx-border-width: 0.5px;"
				+ "-fx-border-color: gray;"
				+ "-fx-border-insets: 300 10 300 10;");
		
		/*ContextMenu cm = new ContextMenu();
		
		MenuItem mi = new MenuItem("Derp");
		MenuItem mii = new MenuItem("Plant");
		
		cm.getItems().addAll(mi, mii);
		
		
		Button btn = new Button("Crippiling Depression");
		btn.setContextMenu(cm);*/
		BorderPane root = new BorderPane();
		root.setCenter(label2);
		root.setLeft(panel);
		Scene scene = new Scene(root, 200, 200);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Test");
		primaryStage.show();
	}
	
	@Override
	public void init()
	{
		
	}
	
	@Override
	public void stop()
	{
		
	}
}
