import Atvaizdavimas.Controller.Mouse;
import Atvaizdavimas.Drawers.OpenGL.OpenGL;
import Classes.ShortestEdgeMap;
import Intefaces.IMap;


public class Main {
	public static void main(String[] args) {
        System.out.println("Testas");
        IMap map = new Classes.Map();
        map.generateMap(10);
        map.generateTree(0);
        map.returnTree();
        System.out.println(String.format("Medzio ilgis dijstra: %4.2f",map.TreeSize()));

        IMap edgeMap = new ShortestEdgeMap();
        edgeMap.generateMap(10);
        edgeMap.generateTree(0);
        edgeMap.returnTree();
        System.out.println(String.format("Medzio ilgis salinimas: %4.2f",edgeMap.TreeSize()));

        OpenGL drawer = new OpenGL();
        drawer.setMap(map);
        new Mouse(drawer);
    }
}
