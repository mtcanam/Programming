package ShortestPathsNPComplete;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TwoSATSolverTest {

    private TwoSATSolver tss;
    private TwoSATSolver tss1;



    @BeforeEach
    void setUp() {

    }

    @Test
    void readDataAndFormGraph() {
        try {
            tss = new TwoSATSolver("2SAT6");
            tss1 = new TwoSATSolver("2SAT6");


        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("4");
        tss.isSatisfiable();
        System.out.println("5");
        tss1.isSatisfiable();


    }
}