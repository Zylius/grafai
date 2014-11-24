package Classes;

import java.util.Comparator;

public class Point {
	private float x;
	private float y;
	private float z;
	private int ID;
	private double distance;
	private int from;
	private int[] connection;
	private boolean inQueue;

    public Point(int ID, float x, float y, float z, int[] connection){
        this.ID = ID;
        this.x = x;
        this.y = y;
        this.z = z;
        this.distance = Double.MAX_VALUE;
        this.connection = connection;
        this.from = -1;
        this.inQueue = false;
    }
    public Point(int ID, float x, float y, float z){
        this.ID = ID;
        this.x = x;
        this.y = y;
        this.z = z;
        this.distance = Double.MAX_VALUE;
        this.from = -1;
        this.inQueue = false;
    }

	public int getID() {
		return ID;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	public float getZ() {
		return z;
	}
	public int[] getConnection() {
		return connection;
	}
    public void setConnection(int[] connections) {this.connection = connections;}
	public void setInQueue(boolean inQueue) {
		this.inQueue = inQueue;
	}
	public int getFrom() {
		return from;
	}
	public void setFrom(int from) {
		this.from = from;
	}
	public boolean isInQueue(){
		return inQueue;
	}

    public String toString(){
        return String.format("ID: %d", ID);
    }

}
