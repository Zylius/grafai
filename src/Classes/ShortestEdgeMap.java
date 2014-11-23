package Classes;

import Intefaces.IMap;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by Tautvydas on 2014-11-22.
 */
public class ShortestEdgeMap implements IMap{
    private Point[] points;
    private int startingPoint;
    private PriorityQueue edges;
    private List<Edge> tree;
    private IntObj[] area;

    public ShortestEdgeMap(){
        edges = new PriorityQueue<Edge>();
        tree = new LinkedList<Edge>();
    }

    class IntObj {
        public int value;
        IntObj(){
            value = -1;
        }
        IntObj(int val){
            value = val;
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

    @Override
    public void generateMap(final int size) {
        int z = 10;
        int u = 5;
        int i;
        points = new Point[]{
                new Point(0, 0, 0, 0, new int[]{ 1, 3 }),
                new Point(1, 0, 0, 1, new int[]{ 2, 7}),
                new Point(2, 0, 0, 2, new int[]{ 1, 3, 4}),
                new Point(3, 0, 2, 2, new int[]{ 2, 1, 4 }),
                new Point(4, 0, 2, 3, new int[]{ 2, 3, 5 }),
                new Point(5, 0, 3, 3, new int[]{ 3, 4, 6 }),
                new Point(6, 0, 3, 1, new int[]{ 3, 5, 7 }),
                new Point(7, 0, 1, 0, new int[]{ 1, 6 }),
        };
        area = new IntObj[points.length];
        createAllEdges();
    }

    @Override
    public void readFromFile() {

    }

    private boolean canJoin(int pointA, int pointB){
        if(area[pointA] == null || area[pointB] == null || area[pointA].value != area[pointB].value)
            return true;
        return false;
    }

    @Override
    public void generateTree(int startingPoint) {
        Edge current;
        boolean good;
        int counter = 0;
        while(!edges.isEmpty() && counter < points.length-1){
            good = false;
            current = (Edge)edges.poll();
            if(area[current.getFirstPoint().getID()] == null){

                if(area[current.getSecondPoint().getID()] == null){
                    IntObj thisArea = new IntObj(current.getFirstPoint().getID());
                    area[current.getFirstPoint().getID()] = thisArea;
                    area[current.getSecondPoint().getID()] = thisArea;
                }else{
                    area[current.getFirstPoint().getID()] = area[current.getSecondPoint().getID()];
                }

                good = true;

            } else if(area[current.getSecondPoint().getID()] == null){
                area[current.getSecondPoint().getID()] = area[current.getFirstPoint().getID()];
                good = true;
            }else if(area[current.getSecondPoint().getID()].value != area[current.getFirstPoint().getID()].value){
                area[current.getSecondPoint().getID()].value =  area[current.getFirstPoint().getID()].value;
                good = true;
            }
            if(good){
            //    current.getSecondPoint().setFrom(current.getFirstPoint().getID());
            //    current.getSecondPoint().setDistance(current.getDistance());
                tree.add(current);
                counter++;
            }

        }
    }

    @Override
    public List<Edge> returnTree() {
        for(int i = 0; i < tree.size(); i++){
            System.out.println(tree.get(i).toString());
        }
        return tree;
    }

    @Override
    public double TreeSize() {
        double weight = 0.d;
        for(int i = 0; i < tree.size(); i++){
            weight += tree.get(i).getDistance();
        }
        return weight;
    }

    @Override
    public Point[] getPoints() {
        return new Point[0];
    }

}
