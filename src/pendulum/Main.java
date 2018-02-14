package pendulum;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextFormatter;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Locale;
import java.util.concurrent.*;

public class Main extends Application {

	private static  ScheduledExecutorService executorDrawerService = Executors.newSingleThreadScheduledExecutor();
	private static  ScheduledExecutorService executorSimulatorService = Executors.newSingleThreadScheduledExecutor();
	private static  ScheduledFuture<?> scheduledDrawerFuture;
	private static  ScheduledFuture<?> scheduledSimulatorFuture;
	private static Drawer  drawer;
	private static Simulator simulator;

    @Override
    public void start(Stage primaryStage) throws Exception{
	    Parent root = FXMLLoader.load(getClass().getResource("gui.fxml"));
        primaryStage.setTitle("Маятник");
        primaryStage.setScene(new Scene(root, 800, 600));
	    primaryStage.show();
	    Controller.getInstance().postInit();
	    primaryStage.setOnCloseRequest(event -> {
	    	event.consume();
	    	scheduledDrawerFuture.cancel(true);
	    	scheduledSimulatorFuture.cancel(true);
	    	executorDrawerService.shutdown();
	    	executorSimulatorService.shutdown();
	    	Platform.exit();
	    });
	    drawer = new Drawer();
	    simulator = new Simulator();
	    scheduledDrawerFuture = executorDrawerService.scheduleAtFixedRate(drawer, 0, 10, TimeUnit.MILLISECONDS);
	    scheduledSimulatorFuture = executorSimulatorService.scheduleAtFixedRate(simulator, 0, 1, TimeUnit.MICROSECONDS);

    }

    public static void restart() {
	    scheduledSimulatorFuture.cancel(false);
	    scheduledDrawerFuture.cancel(false);
	    drawer = new Drawer();
	    simulator = new Simulator();
	    scheduledDrawerFuture = executorDrawerService.scheduleAtFixedRate(drawer, 0, 10, TimeUnit.MILLISECONDS);
	    scheduledSimulatorFuture = executorSimulatorService.scheduleAtFixedRate(simulator, 0, 1, TimeUnit.MICROSECONDS);
    }

    public static void updateSimulator() {
    	simulator.refreshValues();
    }

	public static TextFormatter<Object> createDoubleFormatter(int maxLength) {
		NumberFormat format = NumberFormat.getNumberInstance(Locale.US);
		return new TextFormatter<>(c -> {
			if (c.getControlNewText().isEmpty()) return c;
			String[] split = c.getControlNewText().split("\\.");
			if (split.length > 0 && split[0].length() > maxLength) return null;
			ParsePosition parsePosition = new ParsePosition(0);
			Object object = format.parse(c.getControlNewText(), parsePosition);
			if (object == null || parsePosition.getIndex() < c.getControlNewText().length()) return null;
			else return c;
		});
	}

	public static Drawer getDrawer() {
		return drawer;
	}

	public static Simulator getSimulator() {
		return simulator;
	}

	public static void main(String[] args) {
        launch(args);
    }
}
