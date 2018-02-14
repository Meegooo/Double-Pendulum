package pendulum;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Drawer implements Runnable {

	private Controller controller = Controller.getInstance();
	private Canvas canvas = controller.canvas;
	private List<Point> points = new ArrayList<>();
	GraphicsContext gc = canvas.getGraphicsContext2D();

	double density = 1000; // kg in m^3
	double scale = Double.parseDouble(controller.textField_scale.getText());
	@Override
	public void run() {
		try {
			double l1 = Main.getSimulator().l1;
			double l2 = Main.getSimulator().l2;
			double angle1 = Main.getSimulator().angle1;
			double angle2 = Main.getSimulator().angle2;
			double mass1 = Main.getSimulator().mass1;
			double mass2 = Main.getSimulator().mass2;
			double width = canvas.getWidth();
			double height = canvas.getHeight();
			double y0 = height / 2;
			double x0 = width / 2;
			double x1 = x0 + l1 * Math.sin(angle1) / scale;
			double y1 = y0 + l1 * Math.cos(angle1) / scale;
			double x2 = x1 + l2 * Math.sin(angle2) / scale;
			double y2 = y1 + l2 * Math.cos(angle2) / scale;
			double radius1 = Math.pow(3 * mass1 / (density * 4 * Math.PI), 1.0 / 3.0) / scale;
			double radius2 = Math.pow(3 * mass2 / (density * 4 * Math.PI), 1.0 / 3.0) / scale;
			Main.getDrawer().getPoints().add(new Point(x2, y2));


			Platform.runLater(() -> {
				gc.clearRect(0, 0, width, height);
				gc.setStroke(Color.BLACK);
				gc.setLineWidth(3);
				gc.strokeLine(x0, y0, x1, y1);
				gc.setFill(Color.GREEN);
				gc.fillOval(x1 - radius1 / 2, y1 - radius1 / 2, radius1, radius1);
				gc.setStroke(Color.BLACK);
				gc.setLineWidth(3);
				gc.strokeLine(x1, y1, x2, y2);
				gc.setFill(Color.BLUE);
				gc.fillOval(x2 - radius2 / 2, y2 - radius2 / 2, radius2, radius2);

				if (points.size()>0) {
					Point previous = points.get(0);
					for (int i = 1; i < points.size(); i++) {
						gc.setStroke(Color.BLUE);
						gc.setLineWidth(1);
						Point point = points.get(i);
						gc.strokeLine(previous.x, previous.y, point.x, point.y);
						previous = point;
					}
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List<Point> getPoints() {
		return points;
	}

	public void resetTrace() {
		points.clear();
	}
}
