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
		adjacencyList.computeIfAbsent(topOfGraph, k -> new HashSet<>());
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
		for (Map.Entry<TopOfGraph, Set<TopOfGraph>> entry : adjacencyList.entrySet()) {
			System.out.print(entry.getKey() + " -> ");
			for (TopOfGraph arc : entry.getValue()) {
				System.out.print(arc + " ");
			}
			System.out.println();
		}
	}

	public int findTheShortestPath(TopOfGraph start, TopOfGraph finish) {
		if (!adjacencyList.containsKey(start) || !adjacencyList.containsKey(finish)) {
			throw new IllegalArgumentException("Start or finish top is not in the graph");
		}

		Map<TopOfGraph, Boolean> visited = new HashMap<>();
		Map<TopOfGraph, Integer> distance = new HashMap<>();
		Queue<TopOfGraph> queue = new LinkedList<>();

		for (TopOfGraph top : adjacencyList.keySet()) {
			visited.put(top, false);
			distance.put(top, Integer.MIN_VALUE);
		}

		visited.put(start, true);
		distance.put(start, 0);
		queue.add(start);

		while (!queue.isEmpty()) {
			TopOfGraph current = queue.poll();

			if (current.equals(finish)) {
				return distance.get(current);
			}

			for (TopOfGraph neighbour : adjacencyList.get(current)) {
				if (!visited.get(neighbour)) {
					visited.put(neighbour, true);
					distance.put(neighbour, distance.get(current) + 1);
					queue.add(neighbour);
				}
			}
		}

		return -1;
	}
}
