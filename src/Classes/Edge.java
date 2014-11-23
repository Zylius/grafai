package Classes;

/**
 * Created by Tautvydas on 2014-11-22.
 */
public class Edge implements Comparable{
    private Point firstPointID;
    private Point secondPointID;
    private double distance;

    public Edge(Point firstPoint, Point secondPoint, double distance){
        this.firstPointID = firstPoint;
        this.secondPointID = secondPoint;
        this.distance = distance;
    }

    public Point getFirstPoint() {
        return firstPointID;
    }

    public Point getSecondPoint() {
        return secondPointID;
    }

    public double getDistance() {
        return distance;
    }

    public String toString(){
        return String.format("From %d to %d, distance is: %4.2f", firstPointID.getID(), secondPointID.getID(), (float)distance);
    }

    @Override
    public int compareTo(Object o) {
        Edge tmp = (Edge)o;
        if (this.distance > tmp.distance)
            return 1;
        if(this.distance == tmp.distance)
            return 0;
        return -1;
    }
}
