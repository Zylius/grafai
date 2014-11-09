package Intefaces;

import Classes.Point;

public interface IMap {
	void generateMap();
	void readFromFile();
	void generateTree(int startingPoint);
	String returnTree();
	double TreeSize();
    Point[] getPoints();
}
