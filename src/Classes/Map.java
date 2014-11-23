package Classes;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import Intefaces.IMap;

public class Map implements IMap{

	private Point[] points;
    private List<Edge> tree;
	private int startingPoint;
	
	public Map(){
        tree = new LinkedList<Edge>();
	}
	
	public static double countDistance(Point a, Point b){
		double distance = Math.sqrt(Math.pow(a.getX()-b.getX(),2) + Math.pow(a.getY()-b.getY(),2) + Math.pow(a.getZ()-b.getZ(),2));
		return distance;
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
                new Point(3, 0, 2, 2, new int[]{ 2, 1, 4, 6 }),
                new Point(4, 0, 2, 3, new int[]{ 2, 3, 5 }),
                new Point(5, 0, 3, 3, new int[]{ 3, 4, 6 }),
                new Point(6, 0, 3, 1, new int[]{ 3, 5, 7 }),
                new Point(7, 0, 1, 0, new int[]{ 1, 6 }),

                /*
				new Point(0, 0, 0, 0, new int[]{ 1, 3 }),
				new Point(1, 0, 3, 0, new int[]{ 5, 2}),
				new Point(2, 0, 5, 0, new int[]{ 5, 6, 7}),
				new Point(3, 0, 5, 3, new int[]{ 0, 6, 4 }),
				new Point(4, 0, 5, 5, new int[]{ 4 }),
				new Point(5, 0, 0, 3, new int[]{ 1, 2 }),
				new Point(6, 0, 7, 3, new int[]{ 7, 2, 3 }),
				new Point(7, 0, 9, 3, new int[]{ 8, 2, 6 }),
				new Point(8, 0, 9, 7, new int[]{ 7 }),*/
				
				/*new Point(3, 1, 1, 2, new int[]{7, 8}),
				new Point(4, 1, 1, 2, new int[]{9}),
				new Point(5, 1, 1, 2, new int[]{}),
				new Point(6, 1, 1, 2, new int[]{}),
				new Point(7, 1, 1, 2, new int[]{}),
				new Point(8, 1, 1, 2, new int[]{}),
				new Point(9, 1, 1, 2, new int[]{}),*/
		};
		/*
		for(i = 0; i < dydis; i++){
			points[i] = new Point(i,i,z*i/3,(u+z)/(i+0.1f), new int[]{(i+1)%dydis,(i+2)%dydis});
		}*/
	}

	@Override
	public void readFromFile() {
		// TODO Auto-generated method stub
		
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
        for(int i = 0; i < points.length; i++){
            weight += points[i].getDistance();
		}
		return weight;
	}
	
}
