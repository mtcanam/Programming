package ShortestPathsNPComplete;

import ObjectLibrary.Graph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SCCStackSolverTest {

    private Graph inputGraph;
    private Graph revGraph;
    private ArrayList<ArrayList<Integer>> inputArray;
    private ArrayList<ArrayList<Integer>> revArray;

    @BeforeEach
    void setUp() {
        GraphReader gr = new GraphReader("SCC");
        try {
            gr.readDataAndFormGraph();
            inputGraph = gr.getInputGraph();
            revGraph = gr.getRevGraph();
            inputArray = gr.outputLightGraphArray(inputGraph);
            revArray = gr.outputLightGraphArray(revGraph);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Test
    void testStackCreation(){

        SCCStackSolver sss = new SCCStackSolver(inputArray, revArray);
        sss.countSCC();
        System.out.println(sss.getLeaderVertex());
        sss.printTop5SCCSizes();

    }
}