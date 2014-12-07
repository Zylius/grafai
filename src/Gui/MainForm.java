package Gui;

import Atvaizdavimas.Controller.Mouse;
import Atvaizdavimas.Drawers.OpenGL.OpenGL;
import Classes.Point;
import Classes.ShortestEdgeMap;
import Intefaces.IMap;

import javax.swing.*;
import java.awt.*;
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
        super("Grafai");

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

                    map.readFromFile(file);
                    map.generateTree(0);
                    map.returnTree();
                    System.out.print(map);

                    drawButton.setEnabled(true);
                }
            }
        });

        // graph generation
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                final GenerateForm generateForm = new GenerateForm();
                Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
                generateForm.setLocation((int)((screen.getWidth() - generateForm.getWidth()) / 2), (int)((screen.getHeight() - generateForm.getHeight()) / 2));
                generateForm.generateButton.addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            int numPoints = Integer.parseInt(generateForm.pointNumField.getText());
                            int displayWidth = Integer.parseInt(generateForm.displayWidthField.getText());
                            int displayHeight = Integer.parseInt(generateForm.displayHeightField.getText());

                            System.out.println("Testas");

                            /*map.generateMap(numPoints);
                            map.generateTree(0);
                            map.returnTree();
                            System.out.println(String.format("Medzio ilgis dijstra: %4.2f",map.TreeSize()));
                            System.out.print(map);*/

                            IMap defaultMap = new Classes.Map();
                            IMap edgeMap = new ShortestEdgeMap();

                            edgeMap.generateMap(numPoints);
                            defaultMap.setPoints(edgeMap.getPoints());

                            defaultMap.generateTree(0);
                            edgeMap.generateTree(0);
                            edgeMap.returnTree();
                            map = edgeMap;
                            System.out.print(edgeMap);
                            System.out.println(String.format("================= %15s: %4.2f =================","Medzio ilgis salinimas", edgeMap.TreeSize()));
                            System.out.print(defaultMap);
                            System.out.println(String.format("================= %15s: %4.2f =================","Medzio ilgis Dijkstra", defaultMap.TreeSize()));

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

    public void addGraph(OpenGL canvas){
        MainPanel.add(canvas, BorderLayout.CENTER);
    }
}
