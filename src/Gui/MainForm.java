package Gui;

import Atvaizdavimas.Controller.Mouse;
import Atvaizdavimas.Drawers.OpenGL.OpenGL;
import Classes.Point;
import Classes.ShortestEdgeMap;
import Intefaces.IMap;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class MainForm extends JFrame{
    private JButton drawButton;
    private JButton loadButton;
    private JButton generateButton;
    private JPanel MainPanel;

    private IMap map = new Classes.Map();

    public MainForm(){
        super("Grafai main");

        setContentPane(MainPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // graph drawing
        drawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OpenGL drawer = new OpenGL();
                drawer.setMap(map);
                new Mouse(drawer);
            }
        });

        // graph loading from file
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                int returnVal = fc.showOpenDialog(MainForm.this);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    try{
                        Scanner fileScanner = new Scanner(file);
                        Scanner fileScanner2 = new Scanner(file);
                        int pointNumber = 0;
                        while (fileScanner2.hasNext()){
                            fileScanner2.nextLine();
                            pointNumber++;
                        }

                        int i = 0;
                        Point[] points = new Point[pointNumber];
                        while (fileScanner.hasNext()){
                            String line = fileScanner.nextLine();
                            System.out.println(line);
                            int x, y ,z;
                            String[] values = line.split(" ");
                            int[] connections = new int[values.length-3];
                            x = Integer.parseInt(values[0]);
                            y = Integer.parseInt(values[1]);
                            z = Integer.parseInt(values[2]);
                            int k = 0;
                            for (int j = 3; j < values.length; j++)
                            {
                                connections[k] = Integer.parseInt(values[j]);
                                k++;
                            }

                            points[i] = new Point(i, x, y, z, connections);
                            i++;
                        }

                        //map.generateTree(0);
                        //map.returnTree();

                        fileScanner.close();

                        drawButton.setEnabled(true);
                    }
                    catch(Exception ex)
                    {
                        System.out.println("Problem with file data");
                        if(ex instanceof FileNotFoundException)
                            System.out.println("File not found");
                        if(ex instanceof NumberFormatException)
                            System.out.println("Wrong file data");
                    }

                }
            }
        });

        // graph generation
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                final GenerateForm generateForm = new GenerateForm();
                generateForm.generateButton.addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            int numPoints = Integer.parseInt(generateForm.pointNumField.getText());
                            int displayWidth = Integer.parseInt(generateForm.displayWidthField.getText());
                            int displayHeight = Integer.parseInt(generateForm.displayHeightField.getText());

                            System.out.println("Testas");

                            map.generateMap(numPoints);
                            map.generateTree(0);
                            map.returnTree();
                            System.out.println(String.format("Medzio ilgis dijstra: %4.2f",map.TreeSize()));

                            IMap edgeMap = new ShortestEdgeMap();
                            edgeMap.generateMap(numPoints);
                            edgeMap.generateTree(0);
                            edgeMap.returnTree();
                            System.out.println(String.format("Medzio ilgis salinimas: %4.2f",edgeMap.TreeSize()));

                            drawButton.setEnabled(true);

                            generateForm.closeFrame();

                        } catch (NumberFormatException name)
                        {
                            generateForm.errorText.setVisible(true);
                        }

                    }
                });
            }
        });

        setVisible(true);
    }

    public void ReadFromFile(){

    }
}
