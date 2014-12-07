package Classes;

import Intefaces.IMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public abstract class AbstractMap implements IMap {


    protected Point[] points;
    protected List<Edge> tree;

    @Override
    public void setPoints(Point[] points) {
        this.points = new Point[points.length];
        for(int i = 0; i < points.length; i++){
            this.points[i] = points[i];
        }
    }

    @Override
    public List<Edge> returnTree()
    {
        return tree;
    }

    @Override
    public double TreeSize() {
        double weight = 0.d;
        for (Edge element : tree){
            weight += element.getDistance();
        }
        return weight;
    }

    @Override
    public void readFromFile(File file) {
        // TODO Auto-generated method stub
        try {
            Scanner fileScanner = new Scanner(file);
            Scanner fileScanner2 = new Scanner(file);
            int pointNumber = 0;
            while (fileScanner2.hasNext()) {
                fileScanner2.nextLine();
                pointNumber++;
            }

            int i = 0;
            Point[] points = new Point[pointNumber];
            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine();
                System.out.println(line);
                int x, y, z;
                String[] values = line.split(" ");
                int[] connections = new int[values.length - 3];
                x = Integer.parseInt(values[0]);
                y = Integer.parseInt(values[1]);
                z = Integer.parseInt(values[2]);
                int k = 0;
                for (int j = 3; j < values.length; j++) {
                    connections[k] = Integer.parseInt(values[j]);
                    k++;
                }

                points[i] = new Point(i, x, y, z, connections);
                i++;
            }

            fileScanner.close();
            fileScanner2.close();

            this.points = points;
        }
        catch (Exception ex)
        {
            System.out.println("Problem with file data");
            if(ex instanceof FileNotFoundException)
                System.out.println("File not found");
            if(ex instanceof NumberFormatException)
                System.out.println("Wrong file data");
        }

    }

    public Point generatePoint(float max, int ID){
        float x, y, z;
        int connections[], conNum;
        boolean existing_connections[];
        Random rand = new Random();
        Point point;
        x = rand.nextFloat()*max;
        y = rand.nextFloat()*max;
        z = rand.nextFloat()*max;
        return new Point(ID, x, y, z);
    }
    public static boolean getRandomBoolean() {
        //gets boolean 20%true 80%  false, rysiu generavimui
        return Math.random() < 0.2;
    }
    public boolean[][] generateConnectionGrid(int size)
    {
        boolean[][] grid = new boolean[size][size];
        for (int i = 0; i < size-1; i++)
            for (int j = i+1; j < size; j++)
            {
                grid[i][j] = grid[j][i] =getRandomBoolean();
                if (i+1 == j) {
                    grid[i][j] = grid[j][i] = true;
                }

            }
        return grid;
    }
    @Override
    public void generateMap(final int size) {
        int z = 10;
        int u = 5;
        float max = 10;
        points = new Point[size];
        boolean[][] connectionGrid = new boolean[size][size];
        connectionGrid = generateConnectionGrid(size);
        for (int i = 0; i < size; i++) {
            int conNum = 0;
            for (int j = 0; j < size; j++)
                if (connectionGrid[i][j])
                    conNum++;
            int connections[] = new int[conNum];
            int conNumTemp = 0;
            for (int j = 0; j < size; j++)
                if (connectionGrid[i][j]) {
                    connections[conNumTemp] = j;
                    conNumTemp++;
                }

            points[i] = generatePoint(max, i);
            points[i].setConnection(connections);
        }
    }

    @Override
    public String toString() {
        String gString = "dydis: " + String.valueOf(tree.size()) + "\n";
        for(int i = 0; i < tree.size(); i++){
            gString += tree.get(i).toString() + '\n';
        }
        return gString;
    }

    @Override
    public Point[] getPoints() {
        return points;
    }
}
