package Gui;

import Atvaizdavimas.Controller.Mouse;
import Atvaizdavimas.Drawers.OpenGL.OpenGL;
import Classes.Map;
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
    private JTextField pointNumberField;
    private JLabel loadErrorText;
    private JLabel generateErrorText;
    private JComboBox algorithmBox;
    private JLabel algorithmLabel;
    private JPanel OpenGLPanel;
    private JDesktopPane desktopPane;
    private IMap defaultMap = new Map();
    private IMap edgeMap = new ShortestEdgeMap();

    private IMap map;

    public MainForm(){
        super("Grafai");

        setContentPane(MainPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        generateErrorText.setVisible(false);
        loadErrorText.setVisible(false);

        // graph drawing
        drawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String selectedAlgorithm = algorithmBox.getSelectedItem().toString();

                if(selectedAlgorithm.equals("Kriskal"))
                {
                    map = defaultMap;

                    System.out.print(defaultMap);
                    System.out.println(String.format("================= %15s: %4.2f =================", "Medzio ilgis Dijkstra", defaultMap.TreeSize()));
                }
                else if(selectedAlgorithm.equals("Prim"))
                {
                    map = edgeMap;

                    System.out.print(edgeMap);
                    System.out.println(String.format("================= %15s: %4.2f =================","Medzio ilgis salinimas", edgeMap.TreeSize()));
                }

                OpenGL drawer = new OpenGL();
                drawer.setMap(map);
                desktopPane.removeAll();
                addGraph(drawer.getCanvas());
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
                    boolean noError;

                    defaultMap = new Map();
                    edgeMap = new ShortestEdgeMap();

                    noError = defaultMap.readFromFile(file);

                    if(noError){
                        defaultMap.generateTree(0);
                        defaultMap.returnTree();

                        edgeMap.setPoints(defaultMap.getPoints());
                        edgeMap.generateTree(0);
                        edgeMap.returnTree();

                        drawButton.setEnabled(true);
                        loadErrorText.setVisible(false);
                    }
                    else
                    {
                        loadErrorText.setVisible(true);
                        drawButton.setEnabled(false);
                    }
                }
            }
        });

        // graph generation
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int numPoints = Integer.parseInt(pointNumberField.getText());
                    defaultMap = new Map();
                    edgeMap = new ShortestEdgeMap();

                    defaultMap.generateMap(numPoints);
                    defaultMap.generateTree(0);
                    defaultMap.returnTree();

                    edgeMap.setPoints(defaultMap.getPoints());
                    //edgeMap.generateMap(numPoints);
                    edgeMap.generateTree(0);
                    edgeMap.returnTree();

                    drawButton.setEnabled(true);
                    generateErrorText.setVisible(false);

                } catch (NumberFormatException name)
                {
                    generateErrorText.setVisible(true);
                    drawButton.setEnabled(false);
                }
            }
        });

        setVisible(true);
    }

    public void addGraph(OpenGL canvas)
    {
        final JInternalFrame Frame = new JInternalFrame();
        Frame.setVisible(true);
        Frame.setSize(710, 500);
        Frame.setLocation(-5,-28);
        Frame.setTitle("Grafas");
        Frame.add(canvas, BorderLayout.CENTER);
        Frame.setVisible(true);
        desktopPane.add(Frame);
    }
}
