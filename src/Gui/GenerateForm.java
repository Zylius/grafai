package Gui;

import javax.swing.*;

public class GenerateForm  extends JFrame{
    public JPanel mainPanel;
    public JButton generateButton;
    public JFormattedTextField displayHeightField;
    public JFormattedTextField displayWidthField;
    public JFormattedTextField pointNumField;
    public JLabel errorText;

    public GenerateForm(){
        super("Point generation");

        setContentPane(mainPanel);
        pack();
        errorText.setVisible(false);
        setVisible(true);
    }

    public void closeFrame(){
        super.dispose();
    }
}
