package Atvaizdavimas.Drawers.OpenGL;


import javax.swing.*;
import java.awt.*;

public class Window {

    public Window(OpenGL canvas)
    {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final JFrame Frame = new JFrame();
        Frame.setVisible(true);
        Frame.setSize(1440, 900);
        Frame.setLocation((int)((screen.getWidth() - Frame.getWidth()) / 2), (int)((screen.getHeight() - Frame.getHeight()) / 2));
        Frame.setTitle("Grafai");
        Frame.setLayout(new BorderLayout());
        Frame.add(canvas, BorderLayout.CENTER);
        Frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Frame.pack();
        Frame.setVisible(true);
    }

}
