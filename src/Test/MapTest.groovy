package Test
import org.junit.Test

class MapTest extends GroovyTestCase {
    @Test
    void testAreTreesEquals2() {
        int mapSize = 2

        Intefaces.IMap dijkstra = new Classes.Map()
        Intefaces.IMap general = new Classes.ShortestEdgeMap()

        general.generateMap(mapSize)
        dijkstra.setPoints(general.getPoints())

        general.generateTree(0)
        dijkstra.generateTree(0)

        double paprastas = Math.round(general.TreeSize() *100)/100
        double dijkstros = Math.round(dijkstra.TreeSize() *100)/100

        assert paprastas == dijkstros : "Nevienodi ilgiai su " + String.valueOf(mapSize)
    }

    @Test
    void testAllCasesAreTreesEquals(){
        for(int i = 2; i < 999; i++){
            testAreTreesEquals(i)
        }
    }

    void testAreTreesEquals(int a) {
        int mapSize = a

        Intefaces.IMap dijkstra = new Classes.Map()
        Intefaces.IMap general = new Classes.ShortestEdgeMap()

        general.generateMap(mapSize)
        dijkstra.setPoints(general.getPoints())

        general.generateTree(0)
        dijkstra.generateTree(0)

        double paprastas = Math.round(general.TreeSize() *100)/100
        double dijkstros = Math.round(dijkstra.TreeSize() *100)/100
        assert paprastas == dijkstros : "Nevienodi ilgiai su " + String.valueOf(mapSize)
     }

}
