package ObjectLibrary;

import java.util.ArrayList;
import java.util.List;

public class Edge {

    private int weight;
    private List<Integer> conn;

    public Edge(){
        weight = 0;
        conn = new ArrayList<>();
    }

    public Edge(int weight, ArrayList<Integer> connections){
        this.weight = weight;
        this.conn = connections;
    }

    public int getWeight() {
        return weight;
    }

    public List<Integer> getConn() {
        return conn;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setConn(List<Integer> conn) {
        this.conn = conn;
    }
}
