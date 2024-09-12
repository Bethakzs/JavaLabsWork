package org.example.lab_2.task1;

public class WorkClass {
    public static void main(String[] args) {
        Graph directedGraph = new Graph(true);
        Graph undirectedGraph = new Graph(false);

        TopOfGraph v1 = new TopOfGraph("A", 1);
        TopOfGraph v2 = new TopOfGraph("B", 2);
        TopOfGraph v3 = new TopOfGraph("C", 3);
        TopOfGraph v4 = new TopOfGraph("D", 4);

        directedGraph.addTopOfGraph(v1);
        directedGraph.addTopOfGraph(v2);
        directedGraph.addTopOfGraph(v3);
        directedGraph.addTopOfGraph(v4);

        undirectedGraph.addTopOfGraph(v1);
        undirectedGraph.addTopOfGraph(v2);
        undirectedGraph.addTopOfGraph(v3);
        undirectedGraph.addTopOfGraph(v4);

        directedGraph.addArc(v1, v2);
        directedGraph.addArc(v1, v4);
        directedGraph.addArc(v2, v3);

        undirectedGraph.addArc(v1, v2);
        undirectedGraph.addArc(v1, v4);
        undirectedGraph.addArc(v2, v3);

        System.out.println("Directed Graph :");
        directedGraph.printGraph();

        System.out.println("Undirected Graph :");
        undirectedGraph.printGraph();
    }
}
