package pendulum;

public class Simulator implements Runnable {

	private Controller controller = Controller.getInstance();

	double d2Angle1 = 0;
	double d2Angle2 = 0;
	double dAngle1 = 0;
	double dAngle2 = 0;
	double l1 = Double.parseDouble(controller.textField_length1.getText());
	double l2 = Double.parseDouble(controller.textField_length2.getText());
	double angle1 = Double.parseDouble(controller.textField_angle1.getText()) / 180 * Math.PI;
	double angle2 = Double.parseDouble(controller.textField_angle2.getText()) / 180 * Math.PI;
	double mass1 = Double.parseDouble(controller.textField_mass1.getText());
	double mass2 = Double.parseDouble(controller.textField_mass2.getText());
	double g = Double.parseDouble(controller.textField_g.getText());
	double time = 0.000001;

	@Override
	public void run() {
		try {
			double mu = 1 + mass1 / mass2;
			d2Angle1 = (g * (Math.sin(angle2) * Math.cos(angle1 - angle2) - mu * Math.sin(angle1)) - (l2 * dAngle2 * dAngle2 + l1 * dAngle1 * dAngle1 * Math.cos(angle1 - angle2)) * Math.sin(angle1 - angle2)) / (l1 * (mu - Math.cos(angle1 - angle2) * Math.cos(angle1 - angle2)));
			d2Angle2 = (mu * g * (Math.sin(angle1) * Math.cos(angle1 - angle2) - Math.sin(angle2)) + (mu * l1 * dAngle1 * dAngle1 + l2 * dAngle2 * dAngle2 * Math.cos(angle1 - angle2)) * Math.sin(angle1 - angle2)) / (l2 * (mu - Math.cos(angle1 - angle2) * Math.cos(angle1 - angle2)));
			dAngle1 += d2Angle1 * time;
			dAngle2 += d2Angle2 * time;
			angle1 += dAngle1 * time;
			angle2 += dAngle2 * time;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void refreshValues() {
		try {
			l1 = Double.parseDouble(controller.textField_length1.getText());
			l2 = Double.parseDouble(controller.textField_length2.getText());
			mass1 = Double.parseDouble(controller.textField_mass1.getText());
			mass2 = Double.parseDouble(controller.textField_mass2.getText());
			g = Double.parseDouble(controller.textField_g.getText());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
