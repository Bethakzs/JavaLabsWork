package org.example.task1;

import java.util.Scanner;

public class WorkClass {
	public static void main(final String[] args) {
		try (final Scanner scanner = new Scanner(System.in)) {
			System.out.println("Enter what you want to do: 1 - use test data, 2 - enter your data");
			final int choice = scanner.nextInt();

			switch (choice) {
				case 1 -> uploadTestGraphs();
				case 2 -> uploadUserGraphs(scanner);
				default -> System.err.println("Invalid choice");
			}
		} catch (final Exception e) {
			System.err.println("Unexpected error: " + e.getMessage());
		}
	}

	private static void uploadTestGraphs() {
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
		undirectedGraph.addArc(v2, v3);
		undirectedGraph.addArc(v3, v4);

		System.out.println("Directed Graph:");
		directedGraph.printGraph();

		System.out.println("Undirected Graph:");
		undirectedGraph.printGraph();

		System.out.println("The shortest path from A to D in undirected graph: " + undirectedGraph.findTheShortestPath(v1, v4));
		System.out.println("The shortest path from A to D in directed graph: " + directedGraph.findTheShortestPath(v1, v4));
	}

	private static void uploadUserGraphs(final Scanner scanner) {
		System.out.println("Enter the type of graph: 1 - Directed, 2 - Undirected");
		final int graphType = scanner.nextInt();
		final boolean isDirected = graphType == 1;

		final Graph graph = new Graph(isDirected);

		System.out.println("Enter the number of the top of graph:");
		final int togCount = scanner.nextInt();
		if (togCount <= 0) {
			throw new IllegalArgumentException("The number of the top of graph must be greater than 0");
		}

		for (int i = 0; i < togCount; i++) {
			System.out.println("Enter the name and value for top of graph " + (i + 1) + " (format: Name Value):");
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
			System.out.println("Enter the start and end top of graph for arc " + (i + 1) + " (format: StartTOG EndTOG):");
			final String startTOGName = scanner.next();
			final String endTOGName = scanner.next();

			final TopOfGraph startTOG = findTOG(graph, startTOGName);
			final TopOfGraph endTOG = findTOG(graph, endTOGName);

			if (startTOG == null || endTOG == null) {
				throw new IllegalArgumentException("One or both TOG not found");
			}

			graph.addArc(startTOG, endTOG);
		}

		System.out.println((isDirected ? "Directed" : "Undirected") + " Graph:");
		graph.printGraph();
	}

	private static TopOfGraph findTOG(final Graph graph, final String name) {
		for (TopOfGraph tog : graph.getTopsOfGraph()) {
			if (tog.getName().equals(name)) {
				return tog;
			}
		}
		return null;
	}
}