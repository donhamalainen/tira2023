package oy.interact.tira.student.graph;

import java.util.Comparator;
import java.util.Map;

public class DistanceComparator<T> implements Comparator<Vertex<T>> {
    private Graph<T> graph;
    private Map<Vertex<T>, Visit<T>> paths;

    public DistanceComparator(Graph<T> graph, Map<Vertex<T>, Visit<T>> paths) {
        this.graph = graph;
        this.paths = paths;
    }

    @Override
    public int compare(Vertex left, Vertex right) {
        // the order of the two vertices compared to each other depend on their distance
        // from the start node
        // in the path
        return (int) (graph.distance(left, paths) - graph.distance(right, paths));
    }
}