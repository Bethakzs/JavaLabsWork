package org.example.lab_2.task1;

import java.util.Scanner;

public class WorkClass {
    public static void main(final String[] args) {
        try (final Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter what you want to do: 1 - use test data, 2 - enter your data");
            final int choice = scanner.nextInt();

            if (choice == 1) {
                displayTestGraphs();
            } else if (choice == 2) {
                displayUserGraphs(scanner);
            } else {
                System.err.println("Invalid choice");
            }
        } catch (final Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }

    private static void displayTestGraphs() {
        final Graph directedGraph = new Graph(true);
        final Graph undirectedGraph = new Graph(false);

        final TopOfGraph v1 = new TopOfGraph("A", 1);
        final TopOfGraph v2 = new TopOfGraph("B", 2);
        final TopOfGraph v3 = new TopOfGraph("C", 3);
        final TopOfGraph v4 = new TopOfGraph("D", 4);

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

        System.out.println("Directed Graph:");
        directedGraph.printGraph();

        System.out.println("Undirected Graph:");
        undirectedGraph.printGraph();
    }

    private static void displayUserGraphs(final Scanner scanner) {
        System.out.println("Enter the type of graph: 1 - Directed, 2 - Undirected");
        final int graphType = scanner.nextInt();
        final boolean isDirected = graphType == 1;

        final Graph graph = new Graph(isDirected);

        System.out.println("Enter the number of the top of graph:");
        final int vertexCount = scanner.nextInt();
        if (vertexCount <= 0) {
            throw new IllegalArgumentException("The number of vertices must be greater than 0");
        }

        for (int i = 0; i < vertexCount; i++) {
            System.out.println("Enter the name and value for vertex " + (i + 1) + " (format: Name Value):");
            final String name = scanner.next();
            final int value = scanner.nextInt();
            final TopOfGraph vertex = new TopOfGraph(name, value);

            graph.addTopOfGraph(vertex);
        }

        System.out.println("Enter the number of arcs:");
        final int arcCount = scanner.nextInt();
        if (arcCount <= 0) {
            throw new IllegalArgumentException("The number of arcs must be greater than 0");
        }

        for (int i = 0; i < arcCount; i++) {
            System.out.println("Enter the start and end vertices for arc " + (i + 1) + " (format: StartVertex EndVertex):");
            final String startVertexName = scanner.next();
            final String endVertexName = scanner.next();

            final TopOfGraph startVertex = findVertex(graph, startVertexName);
            final TopOfGraph endVertex = findVertex(graph, endVertexName);

            if (startVertex == null || endVertex == null) {
                throw new IllegalArgumentException("One or both vertices not found");
            }

            graph.addArc(startVertex, endVertex);
        }

        System.out.println((isDirected ? "Directed" : "Undirected") + " Graph:");
        graph.printGraph();
    }

    private static TopOfGraph findVertex(final Graph graph, final String name) {
        for (TopOfGraph tog : graph.getTopsOfGraph()) {
            if (tog.getName().equals(name)) {
                return tog;
            }
        }
        return null;
    }
}