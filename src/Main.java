import Intefaces.IMap;


public class Main {
	public static void main(String[] args) {
		System.out.println("Testas");
		IMap map = new Classes.Map();
		map.generateMap();
		map.generateTree(0);
		int a = 0;
		a = 1;
		System.out.println(map.returnTree());
	}
}
