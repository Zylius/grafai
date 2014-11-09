package Classes;

import java.util.LinkedList;
import java.util.Queue;

import Intefaces.IMap;

public class Map implements IMap{
	private Point[] points;
	private Queue<Point> queue;
	private int startingPoint;
	
	public Map(){
		
	}
	
	public static double countDistance(Point a, Point b){
		double distance = Math.sqrt(Math.pow(a.getX()-b.getX(),2) + Math.pow(a.getY()-b.getY(),2) + Math.pow(a.getZ()-b.getZ(),2));
		return distance;
	}
	
	@Override
	public void generateMap() {
		final int dydis = 10;
		//points = new Point[dydis];
		int z = 10;
		int u = 5;
		int i;
		points = new Point[]{
				new Point(0, 0, 0, 0, new int[]{ 1, 3 }),
				new Point(1, 0, 3, 0, new int[]{ 5, 2}),
				new Point(2, 0, 5, 0, new int[]{ 5, 6, 7}),
				new Point(3, 0, 5, 3, new int[]{ 0, 6, 4 }),
				new Point(4, 0, 5, 5, new int[]{ 4 }),
				new Point(5, 0, 0, 3, new int[]{ 1, 2 }),
				new Point(6, 0, 7, 3, new int[]{ 7, 2, 3 }),
				new Point(7, 0, 9, 3, new int[]{ 8, 2, 6 }),
				new Point(8, 0, 9, 7, new int[]{ 7 }),
				
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

	@Override
	public void generateTree(int point) {
		startingPoint = point;
		Point current;
		Point tmp;
		double distance;
		int i;
		int con[];
		queue = new LinkedList<Point>();
		points[point].setDistance(0);
		queue.add(points[point]);
		while(!queue.isEmpty()){
			current = queue.poll();
			con = current.getConnection();
			for(i = 0; i < con.length; i++){
				tmp = points[con[i]];
				distance = current.getDistance()+countDistance(current, tmp);
				if(distance < tmp.getDistance() ){
					tmp.setDistance(distance);
					tmp.setFrom(current.getID());
					if(!tmp.isInQueue()){
						tmp.setInQueue(true);
						queue.add(tmp);
					}
				}
			}
			current.setInQueue(false);
		}
	}

	@Override
	public String returnTree() {
		String rez = "";
		for(int i = 0; i < points.length; i++){
			rez += "| From " + Integer.toString(points[i].getFrom()) + " to " + Integer.toString(points[i].getID()) + " | "; 
		}
		return rez;
	}

	@Override
	public double TreeSize() {
		double distance = -1;
		for(int i = 0; i < points.length; i++){
		//	if (distance < points[i].getDistance()){
		//		distance = points[i].getDistance();
		//	}
		}
		return distance;
	}
	
}
