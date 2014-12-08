import Gui.MainForm;

import java.awt.*;

public class Main {
	public static void main(String[] args) {
        MainForm mainForm = new MainForm();
        mainForm.setSize(736,690);
        mainForm.setResizable(false);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        mainForm.setLocation((int)((screen.getWidth() - mainForm.getWidth()) / 2), (int)((screen.getHeight() - mainForm.getHeight()) / 2));
    }
}
