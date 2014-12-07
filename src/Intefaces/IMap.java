package Intefaces;

import Classes.Edge;
import Classes.Point;

import java.io.File;
import java.util.List;

public interface IMap {
	void generateMap(final int size);
	void readFromFile(File file);
	void generateTree(int startingPoint);
	List<Edge> returnTree();
	double TreeSize();
    Point[] getPoints();
    void setPoints(Point[] points);
}
