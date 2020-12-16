package GraphsAndDataStructures;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class SCCCounter {

    private static int t;
    private static int s;
    private static HashMap<Integer, graphNode> hm;
    private static HashMap<Integer, Integer> timeMap;
    private static HashMap<Integer, graphNode> hmRev;
    private static HashMap<Integer, Integer> SCCMap;

    public SCCCounter(){

    }

    public static void main(String[] args) throws IOException {
        //Number of nodes processed thus far
        t = 0;
        //Current source vertex
        s = 0;
        //Read in the graph and reverse graph
        readData();
        //Get the number of nodes
        int len = hmRev.size();
        //do first pass to discover proper sequencing
        for (int i = len; i > 0; i--){
            if (hmRev.get(i)!=null && !hmRev.get(i).getVisited()){
                s = i;
                hmRev = depthSearch(hmRev, i);
            }
        }
        System.out.println(hmRev);
        //Create a hashmap of the finishing times and node ids
        createTimeMap();
        //Reset number of nodes processed thus far
        t = 0;
        //Reset current source vertex
        s = 0;
        //do second pass to discover the SCCs
        for (int i = len; i > 0; i--){
            int startNode = timeMap.get(i);
            if (hm.get(startNode)!=null && !hm.get(startNode).getVisited()){
                s = startNode;
                hm = depthSearch(hm, startNode);
            }
        }
        //Count unique leader nodes
        createSCCMap();
        System.out.println(SCCMap);
    }

    private static HashMap<Integer, graphNode> depthSearch(HashMap<Integer, graphNode> map, int nodeID){
        graphNode gn = map.get(nodeID);
        gn.setVisited(true);
        gn.setLeaderNode(map.get(s));
        ArrayList<Integer> arr = gn.getOutConn();
        int len = arr.size();
        if (len > 0) {
            for (int i = 0; i < len; i++) {
                int outNode = arr.get(i);
                if (!map.get(outNode).getVisited()) {
                    map = depthSearch(map, arr.get(i));
                }
            }
        }
        t++;
        gn.setFinishTime(t);
        map.put(nodeID, gn);
        return map;
    }

    private static void createTimeMap(){
        int len = hmRev.size();
        timeMap = new HashMap<Integer, Integer>();
        for (int i = 1; i <= len; i++){
            timeMap.put(hmRev.get(i).getFinishTime(),i);
        }

    }

    private static void createSCCMap(){
        int len = hm.size();
        SCCMap = new HashMap<Integer, Integer>();
        for (int i = 1; i <= len; i++){
            int lNodeID = hm.get(i).getleaderNodeID();
            if(!SCCMap.keySet().contains(lNodeID)) {
                SCCMap.put(lNodeID, 1);
            }else{
                int cnt = SCCMap.get(lNodeID) + 1;
                SCCMap.put(lNodeID, cnt);
            }
        }
    }

    private static void readData() throws IOException {
        String filePath = "./data/SCC.txt";
        HashMap<Integer, graphNode> map = new HashMap<Integer, graphNode>();
        HashMap<Integer, graphNode> mapRev = new HashMap<Integer, graphNode>();
        int maxNode = 0;

        String line;
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        while ((line = reader.readLine()) != null) {
            if (!line.equals("")) {
                String[] parts = line.split("\\s");

                int src = Integer.parseInt(parts[0]);
                int dest = Integer.parseInt(parts[1]);

                //Normal graph
                if (!map.keySet().contains(src)) {
                    ArrayList<Integer> arr = new ArrayList<Integer>();
                    arr.add(dest);
                    graphNode gn = new graphNode(src, arr);
                    map.put(src, gn);

                } else {
                    graphNode gn = map.get(src);
                    ArrayList<Integer> arr = gn.getOutConn();
                    arr.add(dest);
                    gn.setOutConn(arr);
                    map.put(src, gn);
                }

                //reverse graph
                if (!mapRev.keySet().contains(dest)) {
                    ArrayList<Integer> arr = new ArrayList<Integer>();
                    arr.add(src);
                    graphNode gn = new graphNode(dest, arr);
                    mapRev.put(dest, gn);

                } else {
                    graphNode gn = mapRev.get(dest);
                    ArrayList<Integer> arr = gn.getOutConn();
                    arr.add(src);
                    gn.setOutConn(arr);
                    mapRev.put(dest, gn);
                }
                maxNode = Math.max(Math.max(dest, src), maxNode);
            }
        }
        reader.close();
        //Create any nodes that were not already created that are within 1 to n
        for (int i = 1; i <= maxNode; i++) {
            if (!map.keySet().contains(i)){
                map.put(i,new graphNode(i,new ArrayList<Integer>()));
            }
            if (!mapRev.keySet().contains(i)){
                mapRev.put(i,new graphNode(i,new ArrayList<Integer>()));
            }
        }
        hm = map;
        hmRev = mapRev;
    }

}

class graphNode{

    private int nodeID;
    private ArrayList<Integer> outConn;
    private boolean visited;
    private graphNode leaderNode;
    private int finishTime;

    public graphNode(int nodeID, ArrayList<Integer> outConn){
        this.nodeID = nodeID;
        this.outConn = outConn;
        this.visited = false;
        this.leaderNode = null;
        this.finishTime = 0;
    }

    public int getNodeID() {
        return this.nodeID;
    }

    public ArrayList<Integer> getOutConn() {
        return this.outConn;
    }

    public boolean getVisited() {
        return this.visited;
    }

    public void setOutConn(ArrayList<Integer> outConn) {
        this.outConn = outConn;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void setLeaderNode(graphNode gn){
        this.leaderNode = gn;
    }

    public void setFinishTime(int i){
        this.finishTime = i;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public int getleaderNodeID(){
        return this.leaderNode.getNodeID();
    }

    public String toString(){
        return "Finish time: " + this.getFinishTime() + "\tLeader Node: " + String.valueOf(this.leaderNode.getNodeID()) + "\n";
    }
}
