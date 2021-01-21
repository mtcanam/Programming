package ShortestPathsNPComplete;

import ObjectLibrary.Graph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JohnsonsAlgorithmSolverTest {

    private Graph inputGraph1;
    private Graph inputGraph2;
    private Graph inputGraph3;
    private Graph inputGraph4;
    private Graph inputGraph5;

    @BeforeEach
    void setUp() {
        GraphReader graphReader = new GraphReader("APSPTest1");
        GraphReader graphReader2 = new GraphReader("APSPTest2");
        GraphReader graphReader3 = new GraphReader("APSPTest3");
        GraphReader graphReader4 = new GraphReader("APSPTest4");
        GraphReader graphReader5 = new GraphReader("APSPTest5");
        inputGraph1 = new Graph();
        inputGraph2 = new Graph();
        inputGraph3 = new Graph();
        inputGraph4 = new Graph();
        inputGraph5 = new Graph();
        try {
            inputGraph1 = graphReader.readDataAndFormGraph();
            inputGraph2 = graphReader2.readDataAndFormGraph();
            inputGraph3 = graphReader3.readDataAndFormGraph();
            inputGraph4 = graphReader4.readDataAndFormGraph();
            inputGraph5 = graphReader5.readDataAndFormGraph();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testJohnsonsAlgorithm(){
        JohnsonsAlgorithmSolver js = new JohnsonsAlgorithmSolver(inputGraph1);
        Assertions.assertThrows(UnsupportedOperationException.class, js::calculateVertexWeights);

        JohnsonsAlgorithmSolver js2 = new JohnsonsAlgorithmSolver(inputGraph2);
        js2.calculateVertexWeights();
        js2.allPointsDijkstra();
        Assertions.assertEquals(js2.getMinShortestPath(),-2);

        JohnsonsAlgorithmSolver js3 = new JohnsonsAlgorithmSolver(inputGraph3);
        js3.calculateVertexWeights();
        js3.allPointsDijkstra();
        Assertions.assertEquals(js3.getMinShortestPath(),-2);

        JohnsonsAlgorithmSolver js4 = new JohnsonsAlgorithmSolver(inputGraph4);
        Assertions.assertThrows(UnsupportedOperationException.class, js4::calculateVertexWeights);

        JohnsonsAlgorithmSolver js5 = new JohnsonsAlgorithmSolver(inputGraph5);
        js5.calculateVertexWeights();
        js5.allPointsDijkstra();
        Assertions.assertEquals(js5.getMinShortestPath(),-234);
    }
}