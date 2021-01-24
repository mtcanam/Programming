package ShortestPathsNPComplete;

import ObjectLibrary.Graph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SCCStackSolverTest {

    private Graph inputGraph;

    @BeforeEach
    void setUp() {
        GraphReader gr = new GraphReader("SCC");
        try {
            inputGraph = gr.readDataAndFormGraph();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Test
    void testStackCreation(){
        SCCStackSolver sss = new SCCStackSolver(inputGraph);
        sss.countSCC();
        int x = 1;
    }
}