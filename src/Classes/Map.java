package Classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import Intefaces.IMap;

public class Map extends AbstractMap
{

	private int startingPoint;
	
	public Map(){
        tree = new LinkedList<Edge>();
	}
	
	public static double countDistance(Point a, Point b){
		double distance = Math.sqrt(Math.pow(a.getX()-b.getX(),2) + Math.pow(a.getY()-b.getY(),2) + Math.pow(a.getZ()-b.getZ(),2));
		return distance;
	}

    public void setPoints(Point[] points) {
        this.points = points;
    }

    public Point[] getPoints()
    {
        return points;
    }

    private Edge findShortestEdge(Point point){
        double distance = Double.MAX_VALUE;
        double tmpDistance;
        Edge returnValue = null;
        int tmp[] = point.getConnection();
        for(int i = 0; i < tmp.length; i++){
            tmpDistance = countDistance(point,points[tmp[i]]);
            if(tmpDistance < distance){
                distance = tmpDistance;
                returnValue = new Edge(point,points[tmp[i]],distance);
            }
        }
        return  returnValue;
    }

    private void putEdges(PriorityQueue q, Point point){
        Point tmp;
        double edgeLength;
        int c[] = point.getConnection();
        for(int i = 0; i < c.length; i++){
            tmp = points[c[i]];
            if(tmp.getFrom() == -1) {
                edgeLength = countDistance( point, tmp);
                q.add(new Edge( point, tmp, edgeLength));
            }
        }
    }

	@Override
	public void generateTree(int point) {
		startingPoint = point;
		Point current;
		Point tmp;
        Edge currentEdge;
        PriorityQueue edges = new PriorityQueue<Edge>();

		//queue = new LinkedList<Point>();
		points[point].setDistance(0);
        points[point].setFrom(-2);
		//queue.add(points[point]);


        //edges.add(findShortestEdge(points[point]));
        putEdges(edges,points[point]);

        while(!edges.isEmpty() && tree.size() < points.length-1){
            currentEdge = (Edge)edges.poll();
            current = currentEdge.getSecondPoint();
            if(current.getFrom() != -1){
                continue;
            }
            putEdges(edges,current);
            current.setFrom(currentEdge.getFirstPoint().getID());
            current.setDistance(currentEdge.getDistance());
		}

        for(int a = 0; a < points.length; a++){
            int index = points[a].getFrom();
            if (index >= 0) {
                tree.add(new Edge(points[a], points[index], points[a].getDistance()));
            }
        }
	}
}
