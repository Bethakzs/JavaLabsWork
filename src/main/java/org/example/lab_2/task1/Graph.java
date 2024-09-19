package org.example.lab_2.task1;

import java.util.*;

public class Graph {
    private final Map<TopOfGraph, Set<TopOfGraph>> adjacencyList = new HashMap<>();
    private final boolean isDirected;

    public Graph(boolean isDirected) {
        this.isDirected = isDirected;
    }

    public Set<TopOfGraph> getTopsOfGraph() {
        return adjacencyList.keySet();
    }

    public void addTopOfGraph(final TopOfGraph topOfGraph) {
        if(!adjacencyList.containsKey(topOfGraph)) {
            adjacencyList.put(topOfGraph, new HashSet<>());
        }
    }

    public void addArc(final TopOfGraph from, final TopOfGraph to) {
        if (adjacencyList.containsKey(from) && adjacencyList.containsKey(to)) {
            adjacencyList.get(from).add(to);
            if (!isDirected) { // if graph is undirected we need to add edge in both directions
                adjacencyList.get(to).add(from);
            }
        }
    }

    public void printGraph() {
        for (TopOfGraph top : adjacencyList.keySet()) {
            System.out.print(top + " -> ");
            Set<TopOfGraph> arcs = adjacencyList.get(top);
            for (TopOfGraph arc : arcs) {
                System.out.print(arc + " ");
            }
            System.out.println();
        }
    }

    public List<TopOfGraph> findTheShortestPath (TopOfGraph start, TopOfGraph finish) {
        if(!adjacencyList.containsKey(start) || !adjacencyList.containsKey(finish)) {
            throw new IllegalArgumentException("Start or finish top is not in the graph");
        }

        Map<TopOfGraph, Boolean> visited = new HashMap<>();

        return new ArrayList<>();
    }
}
