import model.Model;
import view.GUI;

public class Main {
	public static void main(String[] args) {
		GUI gui = new GUI();
		Model model = new Model();
		new Controller(gui, model, args);
	}
}