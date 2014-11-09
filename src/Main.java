import Atvaizdavimas.Controller.Mouse;
import Atvaizdavimas.Drawers.OpenGL.OpenGL;
import Intefaces.IMap;


public class Main {
	public static void main(String[] args) {
        System.out.println("Testas");
        IMap map = new Classes.Map();
        map.generateMap();
        map.generateTree(0);
        System.out.println(map.returnTree());
        OpenGL drawer = new OpenGL();
        drawer.setMap(map);
        new Mouse(drawer);
    }
}
