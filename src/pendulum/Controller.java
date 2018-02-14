package pendulum;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import java.text.NumberFormat;
import java.util.Locale;


public class Controller {

	private static Controller instance;

	public static Controller getInstance() {
		return instance;
	}

	public Controller() {
		instance = this;
	}

	@FXML
	public Canvas canvas;
	@FXML
	public TextField textField_mass1;
	@FXML
	public TextField textField_mass2;
	@FXML
	public TextField textField_angle1;
	@FXML
	public TextField textField_angle2;
	@FXML
	public TextField textField_length1;
	@FXML
	public TextField textField_length2;
	@FXML
	public TextField textField_scale;
	@FXML
	public TextField textField_g;
	@FXML
	public StackPane stackPane_canvas;

	public void postInit() {
		canvas.widthProperty().bind(stackPane_canvas.widthProperty());
		canvas.heightProperty().bind(stackPane_canvas.heightProperty());
		textField_mass1.setTextFormatter(Main.createDoubleFormatter(9));
		textField_mass2.setTextFormatter(Main.createDoubleFormatter(9));
		textField_angle1.setTextFormatter(Main.createDoubleFormatter(3));
		textField_angle2.setTextFormatter(Main.createDoubleFormatter(3));
		textField_length1.setTextFormatter(Main.createDoubleFormatter(9));
		textField_length2.setTextFormatter(Main.createDoubleFormatter(9));
		textField_g.setTextFormatter(Main.createDoubleFormatter(9));
		textField_scale.setTextFormatter(Main.createDoubleFormatter(9));
		textField_mass1.setText("10");
		textField_mass2.setText("10");
		textField_angle1.setText("50");
		textField_angle2.setText("65");
		textField_length1.setText("1");
		textField_length2.setText("1");
		textField_g.setText("9.8");
		textField_scale.setText("0.01");
		stackPane_canvas.setMinSize(0, 0);
		stackPane_canvas.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
	}

	@FXML
	public void onButtonRestart() {
		Main.restart();
	}

	@FXML
	public void onButtonResetTrace() {
		Main.getDrawer().resetTrace();
	}

	@FXML
	public void onTextFieldsParams() {
		Main.updateSimulator();
	}
}
