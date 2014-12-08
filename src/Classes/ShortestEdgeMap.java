package Classes;

import Intefaces.IMap;

import java.io.File;
import java.util.*;

/**
 * Created by Zylius on 2014-11-22.
 */
public class ShortestEdgeMap extends AbstractMap
{
    private int startingPoint;
    private PriorityQueue edges;
    private IntObj[] area;

    public ShortestEdgeMap(){
        edges = new PriorityQueue<Edge>();
        tree = new LinkedList<Edge>();
    }

    class IntObj {
        public int value;
        IntObj(int val){
            value = val;
        }
        public String toString(){
            return String.valueOf(value);
        }
    }

    public static double countDistance(Point a, Point b){
        double distance = Math.sqrt(Math.pow(a.getX()-b.getX(),2) + Math.pow(a.getY()-b.getY(),2) + Math.pow(a.getZ()-b.getZ(),2));
        return distance;
    }

    private void createAllEdges(){
        double distance = 0;
        int con[];
        for(int i = 0; i < points.length; i++){
            con = points[i].getConnection();
            for(int j = 0; j < con.length; j++){
                distance = countDistance(points[i],points[con[j]]);
                edges.add(new Edge(points[i],points[con[j]],distance));
            }
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
        super.generateMap(size);
        area = new IntObj[points.length];
        createAllEdges();
    }

    @Override
    public void setPoints(Point[] points) {
        this.points = new Point[points.length];
        for(int i = 0; i < points.length; i++){
            this.points[i] = points[i];
        }
        area = new IntObj[points.length];
        createAllEdges();
    }

    @Override
    public void generateTree(int startingPoint) {
        Edge current;
        boolean good;
        int counter = 0;
        while(!edges.isEmpty() && counter < points.length-1){
            good = false;
            current = (Edge)edges.poll();
            int curVal = -1;
            int tmp = -1;
            if(area[current.getFirstPoint().getID()] == null){

                if(area[current.getSecondPoint().getID()] == null){
                    IntObj thisArea = new IntObj(current.getFirstPoint().getID());
                    area[current.getFirstPoint().getID()] = thisArea;
                    area[current.getSecondPoint().getID()] = thisArea;
                }else{
                    area[current.getFirstPoint().getID()] = area[current.getSecondPoint().getID()];
                }
                //curVal = area[current.getSecondPoint().getID()].value;
                good = true;

            } else if(area[current.getSecondPoint().getID()] == null){
                area[current.getSecondPoint().getID()] = area[current.getFirstPoint().getID()];
                curVal = area[current.getFirstPoint().getID()].value;
                good = true;
            }else if(area[current.getSecondPoint().getID()].value != area[current.getFirstPoint().getID()].value){
                tmp =  area[current.getSecondPoint().getID()].value;
                area[current.getSecondPoint().getID()].value =  area[current.getFirstPoint().getID()].value;
                curVal = area[current.getFirstPoint().getID()].value;
                good = true;
            }
            if(good){
            //    current.getSecondPoint().setFrom(current.getFirstPoint().getID());
            //    current.getSecondPoint().setDistance(current.getDistance());
                if(tmp != -1 && curVal != -1) {
                    for (int z = 0; z < area.length; z++) {
                        if (area[z] != null && area[z].value == tmp) {
                            area[z].value = curVal;
                        }
                    }
                }
                tree.add(current);
                counter++;
            }

        }
    }

}
