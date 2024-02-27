package oy.interact.tira.student.graph;

import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Vector;

import oy.interact.tira.student.StackImplementation;
import oy.interact.tira.student.graph.Edge.EdgeType;
import oy.interact.tira.student.graph.Visit.Type;
import oy.interact.tira.util.StackInterface;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

/**
 * Implementation of the graph data structure and associated algorithms.
 * <p>
 * This abstract class implements the graph data structure and various
 * algorithms like breadth-first search, depth-first search and the Dijkstra
 * path finding algorithm.
 * <p>
 * The class needs your attention, dear student. Implement the methods
 * marked TODO in the comments, based on what you have learned about
 * graphs.
 * <p>
 * The Graph as a generic (template) class can use any data types conforming to
 * the Comparable interface.
 * <p>
 * This implementation uses edge lists to store the graph vertices
 * and edges.
 * 
 * @author Antti Juustila
 * @version 1.0
 */
public class Graph<T> {

   /**
    * The edge list of the grap. Select and instantiate
    * a suitable type of Map, depending on application needs.
    */
   private Map<Vertex<T>, List<Edge<T>>> edgeList = null;
   private Map<Integer, Vertex<T>> vertices = null;

   /**
    * Constructor instantiates a suitable Map data structure
    * depending on the application requirements.
    */
   public Graph() {
      this.edgeList = new Hashtable<Vertex<T>, List<Edge<T>>>();
      this.vertices = new Hashtable<Integer, Vertex<T>>();

   }

   /**
    * Creates a vertex holding the dataItem (node in a vertex) in the graph.
    * Use this method always to add vertices to the graph.
    *
    * Create the vertex object with the data item, then create an empty
    * list of edges, and put the vertex and the empty list to the Map.
    *
    * The newly created vertex must have an empty list of edges.
    * 
    * @param element The data item to put in the vertex of the graph.
    * @return Returns the created vertex, placed in the graph's edge list.
    */
   public Vertex<T> createVertexFor(T element) {
      Vertex<T> newVertex = new Vertex<>(element);
      edgeList.put(newVertex, new ArrayList<>());
      vertices.put(newVertex.getElement().hashCode(), newVertex);
      return newVertex;
   }

   /**
    * Get all the vertices of the graph in a Set.
    * 
    * @return A Set with all the vertices of the graph.
    */

   public Set<Vertex<T>> getVertices() {
      return edgeList.keySet();
   }

   /**
    * Adds an edge to the graph. Note that the vertices MUST have been created
    * earlier by calling {@code createVertexFor(T)} and are already in the graph.
    * 
    * @param type        The type of the edge, either directed or undirected.
    * @param source      The source vertex of the edge.
    * @param destination The destination vertex of the edge.
    * @param weight      The weight of the edge.
    */
   public void addEdge(Edge.EdgeType type, Vertex<T> source, Vertex<T> destination, double weight) {
      if (type == EdgeType.DIRECTED) {
         addDirectedEdge(source, destination, weight);
      } else if (type == EdgeType.UNDIRECTED) {
         addDirectedEdge(source, destination, weight);
         addDirectedEdge(destination, source, weight);
      }
   }

   /**
    * Adds a directed edge to the graph. Note that the vertices must have been
    * created
    * earlier by calling {@code createVertexFor(T)}.
    * 
    * @param source      The source vertex of the edge.
    * @param destination The destination vertex of the edge.
    * @param weight      The weight of the edge.
    */
   public void addDirectedEdge(Vertex<T> source, Vertex<T> destination, double weight) {
      Edge<T> edge = new Edge<>(source, destination, weight);
      edgeList.get(source).add(edge);
   }

   /**
    * Gets the edges of the specified vertex. The vertex must be
    * already in the graph.
    * 
    * @param source The vertex edges of which we wish to get.
    * @return Returns the edges of the vertex or null if no edges from the source.
    */
   public List<Edge<T>> getEdges(Vertex<T> source) {
      return edgeList.get(source);
   }

   /**
    * Gets a vertex for the specified node (contents) in a vertex, if found.
    * If the vertex with the node value is not found, returns null.
    * Use `equals` to search for the element from the vertices.
    *
    * @param element The value of T that is in some Vertex in the graph.
    * @return The vertex containing the node, or null if no vertex contains the
    *         element.
    */
   public Vertex<T> getVertexFor(T element) {
      Integer hash = element.hashCode();
      return vertices.get(hash);
   }

   /**
    * If target is null, search is done for the whole graph. Otherwise,
    * search MUST be stopped when the target vertex is found.
    *
    * @param from   The vertex where the search is started from.
    * @param target An optional ending vertex, null if not given.
    * @return Returns all the visited vertices traversed while doing BFS, in order
    *         they were found, or an empty list.
    */
   public List<Vertex<T>> breadthFirstSearch(Vertex<T> from, Vertex<T> target) {
      List<Vertex<T>> visited = new ArrayList<>(); // Lista vierailluista solmuista
      Set<Vertex<T>> enqueued = new HashSet<>(); // Joukko solmuista, jotka on jo lisätty jonoon
      Queue<Vertex<T>> queue = new LinkedList<>(); // Jono solmuille, joita tulee käsitellä

      // Aloitetaan annetusta solmusta
      queue.add(from);
      enqueued.add(from);

      while (!queue.isEmpty()) {
         Vertex<T> current = queue.poll(); // Otetaan jonosta solmu käsittelyyn
         visited.add(current); // Lisätään tieto että solmu on käsitelty

         // Jos löydetään tai null niin lopetetaan
         if (target != null && current.equals(target)) {
            break;
         }

         // Hae kaikki nykyisen solmun kaaret ja lisää niiden kohdesolmut jonoon
         for (Edge<T> edge : getEdges(current)) {
            Vertex<T> neighbor = edge.getDestination();
            if (!enqueued.contains(neighbor)) {
               queue.add(neighbor); // Lisää kohdesolmu jonoon
               enqueued.add(neighbor);
            }
         }
      }
      return visited; // Palautetaan lista
   }

   /**
    * Does depth first search (DFS) of the graph starting from a vertex.
    * <p>
    * If target is null, search is done for the whole graph. Otherwise,
    * search MUST be stopped when the target vertex is found.
    * <p>
    *
    * @param from   The vertex where the search is started from.
    * @param target An optional ending vertex, null if not given.
    * @return Returns all the visited vertices traversed while doing DFS.
    */
   public List<Vertex<T>> depthFirstSearch(Vertex<T> from, Vertex<T> target) {
      List<Vertex<T>> visited = new ArrayList<>(); // Lista vierailluista solmuista
      Set<Vertex<T>> pushed = new HashSet<>(); // Ei vielä käsitellyt
      StackInterface<Vertex<T>> stack = new StackImplementation<>(); // Pino solmuista

      // Aloitetaan annetusta solmusta
      stack.push(from);
      pushed.add(from);
      visited.add(from);

      while (!stack.isEmpty()) {
         Vertex<T> current = stack.peek(); // Tarkastele pinon päällimmäistä solmua

         // Jos nykyinen solmu on tavoitesolmu, lopetetaan haku
         if (target != null && current.equals(target)) {
            break;
         }

         // Etsi naapuri, joka ei ole vielä käsitelty
         boolean foundUnvisited = false;
         for (Edge<T> edge : getEdges(current)) {
            Vertex<T> neighbor = edge.getDestination();
            if (!pushed.contains(neighbor)) {
               stack.push(neighbor);
               pushed.add(neighbor);
               visited.add(neighbor);
               foundUnvisited = true;
               break; // Löytyi käsittelemätön naapuri, jatka siitä
            }
         }

         // Jos ei löydetty käsittelemätöntä naapuria, poistetaan nykyinen solmu pinosta
         if (!foundUnvisited) {
            stack.pop();
         }
      }
      return visited;
   }

   /**
    * Returns a non-empty list if the graph is disconnected. A disconnected graph
    * is a
    * graph that has separate areas without any connecting edges between them.
    * 
    * If the graph is disconnected, the list contains all the elements _not_
    * visited,
    * doing a breadth first search from the vertex provided as the parameter.
    * If the parameter is null, starts from the first vertice of the graph.
    * 
    * @Param toStartFrom Vertex to start investigating from. If null, start from
    *        the first vertex.
    * @return Returns non-empty list if the graph is disconnected, otherwise list
    *         is empty.
    */
   public List<T> disconnectedVertices(Vertex<T> toStartFrom) {
      List<T> notInVisited = new ArrayList<>();
      List<Vertex<T>> visited = new ArrayList<>();

      Vertex<T> startVertex = (toStartFrom != null) ? toStartFrom : getVertices().iterator().next();

      // Voidaan vaihtaa hakualgorytmiä
      visited = breadthFirstSearch(startVertex, null);

      for (Vertex<T> vertex : edgeList.keySet()) {
         if (!visited.contains(vertex)) {
            notInVisited.add(vertex.getElement());
         }
      }
      return notInVisited;
   }

   /**
    * Returns true if the graph is disconnected. That means, the graph
    * has areas that can not be reached from the starting vertex.
    *
    * @param toStartFrom The vertex to start the analysis from. Can be null, then
    *                    starts from first vertex.
    * @return True if the graph is disconnected.
    */
   public boolean isDisconnected(Vertex<T> toStartFrom) {
      Vertex<T> startVertex = (toStartFrom != null) ? toStartFrom : getVertices().iterator().next();
      List<T> disconnected = disconnectedVertices(startVertex);
      return disconnected.isEmpty();
   }

   /**
    * Checks if the graph has cycles.
    * 
    * If the graph is directed, provide true as the parameter, false for
    * undirected graphs.
    * 
    * <p>
    * NB: For this course project it is enough that this method works for
    * connected graphs. It does not need to work on disconnected graphs when
    * starting
    * from the given vertex.
    *
    * @param isDirected If true graph is directed.
    * @param fromVertex Start looking from this vertex. If null, starts from first
    *                   vertex in adjacency list.
    * @return Returns true if the graph has cycles.
    */
   public boolean hasCycles(EdgeType edgeType, Vertex<T> fromVertex) {
      return false;
   }

   // Dijkstra starts here.

   /**
    * The result of the Dijkstra's search.
    */
   public class DijkstraResult<E> {
      public boolean pathFound = false;
      public List<E> path;
      public int steps = 0;
      public double totalWeigth = 0.0;

      @Override
      public String toString() {
         StringBuilder builder = new StringBuilder();
         builder.append(String.format("Dikstra result:\n- Path found: %s%n", (pathFound ? "yes" : "no")));
         if (pathFound) {
            builder.append(String.format("- steps: %d%n", steps));
            builder.append(String.format("- total edge weights: %.2f%n", totalWeigth));
            if (null != path) {
               builder.append(String.format("- path: %s", path.toString()));
            } else {
               builder.append("Path not found\n");
            }
         }
         return builder.toString();
      }
   }

   /**
    * Finds the shortest path from start to end using Dijkstra's algorithm.
    * 
    * The return value contains information about the result.
    * 
    * @param start The vertex to start from.
    * @param end   The vertex to search the shortest path to.
    * @return An object containing information about the result of the search.
    */
   public DijkstraResult<T> shortestPathDijkstra(Vertex<T> start, Vertex<T> end) {
      DijkstraResult<T> result = new DijkstraResult<>();
      result.pathFound = false;
      result.path = null;
      result.steps = 0;
      result.totalWeigth = 0.0;

      Map<Vertex<T>, Visit<T>> paths;
      paths = shortestPathsFrom(start);

      if (paths != null && !paths.isEmpty()) {
         List<Edge<T>> route = route(end, paths);

         if (route != null && !route.isEmpty()) {
            result.steps = route.size();
            result.pathFound = true;
            List<T> list = new Vector<>();
            list.add(route.get(0).getSource().getElement());
            for (Edge<T> edge : route) {
               list.add(edge.getDestination().getElement());
               result.totalWeigth += edge.getWeigth();
            }
            result.path = list;
         }
      }
      return result;
   }

   /**
    * Finds a route to a destination using paths already constructed.
    * Before calling this method, cal {@link shortestPathsFrom} to construct
    * the paths from the staring vertex of Dijkstra algorithm.
    *
    * A helper method for implementing the Dijkstra algorithm.
    * 
    * @param toDestination The destination vertex to find the route to.
    * @param paths         The paths to search the destination.
    * @return Returns the vertices forming the route to the destination.
    */
   private List<Edge<T>> route(Vertex<T> toDestination, Map<Vertex<T>, Visit<T>> paths) {
      List<Edge<T>> path = new Vector<>(); // empty list of edges; the path
      Vertex<T> vertex = toDestination;

      if (paths.size() == 0) {
         return path;
      }
      Visit<T> visit = paths.get(vertex); // get a visit to the destination vertex

      // handle all the edge visits on the path until start is reached
      while (visit.type != Type.START) {
         path.add(0, visit.edge); // add the visit’s edge to the path
         vertex = visit.edge.getSource(); // take the previous vertex from the edge’s source
         visit = paths.get(vertex); // get the visit and handle the predecessors of it
      }
      return path; // returns the path, route (list of edges) to the destination
   }

   public double distance(Vertex<T> toDestination, Map<Vertex<T>, Visit<T>> viaPath) {
      double distance = 0.0;
      List<Edge<T>> path = route(toDestination, viaPath);

      for (Edge<T> edge : path) {
         distance += edge.getWeigth();
      }
      return distance;
   }

   // Apuohjelma
   public double maxWeight(Vertex<T> toDestination, Map<Vertex<T>, Visit<T>> paths) {
      List<Edge<T>> path = route(toDestination, paths);
      double maxWeightInPath = 0.0;

      for (Edge<T> edge : path) {
         if (edge.getWeigth() > maxWeightInPath) { // Oletetaan, että Edge-luokalla on getWeight-metodi
            maxWeightInPath = edge.getWeigth();
         }
      }

      return maxWeightInPath;
   }

   /**
    * Finds the shortest paths in the graph from the starting vertex.
    *
    * In doing Dijkstra, first call this method, then call {@link route}
    * with the paths collected using this method, to get the shortest path to the
    * destination.
    *
    * @param start The starting vertex for the path searching.
    * @return Returns the visits from the starting vertex.
    * @see oy.tol.tira.graph.Graph#route(Vertex, Map)
    */

   private Map<Vertex<T>, Visit<T>> shortestPathsFrom(Vertex<T> start) {
      Visit<T> visit = new Visit<>();
      visit.type = Visit.Type.START;
      Map<Vertex<T>, Visit<T>> paths = new HashMap<>();

      paths.put(start, visit); // add to paths a starting visit from starting vertex

      // empty priority queue of vertices arranged by a distance
      PriorityQueue<Vertex<T>> queue = new PriorityQueue<>(new DistanceComparator(this, paths));
      queue.add(start); // put the starting vertex in the priority queue

      while (!queue.isEmpty()) {
         Vertex<T> vertex = queue.poll();
         Iterable<Edge<T>> edges = getEdges(vertex);

         for (Edge<T> edge : edges) {
            double weight = edge.getWeigth();

            if (!paths.containsKey(edge.getDestination())
                  || distance(vertex, paths) + weight < distance(edge.getDestination(), paths)) {
               Visit<T> edgeVisit = new Visit<>();
               edgeVisit.type = Visit.Type.EDGE;
               edgeVisit.edge = edge;
               paths.put(edge.getDestination(), edgeVisit);
               queue.add(edge.getDestination());
            }
         }
      }
      return paths;
   }

   // OPTIONAL task in the course!
   /**
    * Do breadth-first-search on the grap and export vertices and edges to a dot
    * file
    *
    * Note that if the graph is disconnected, you must check if some vertices
    * were not visited and continue the BFS until _all_ vertices have been visited.
    * Otherwise, part of the graph is missing from the output file.
    *
    * The simplest way to do this is to first start from the given vertex, do
    * the BFS saving data to dot file. Then, after this loop, get the disconnected
    * vertices starting from
    * the starting vertex by calling disconnectedVertices(from). If there are not
    * visited vertices,
    * then pick one of the non visited vertices to be the new starting vertex
    * (from).
    * Repeat this until all vertices have been visited. Basically you need an outer
    * loop repeating
    * the BFS in the inner loop, and the outer loop stops when all vertices have
    * been visited.
    *
    * @param from           Start the BFS from this vertex.
    * @param outputFileName Write the dot to this text file.
    * @throws IOException If something goes wrong with file operations.
    */
   public void toDotBFS(Vertex<T> from, String outputFileName) throws IOException {

   }

   // STUDENTS: TODO: Uncomment the code below and use it as a sample on how
   // to interate over vertices and edges in one situation.
   // If you use some other name for your edge list than edgeList, then
   // rename that in the code below! Otherwise you will have compiler errors.
   /**
    * Provides a string representation of the graph, printing out the vertices and
    * edges.
    * <p>
    * Quite useful if you need to debug issues with algorithms. You can see is the
    * graph
    * what it is supposed to be like.
    * <p>
    * Simple graph as a string would look like this:<br/>
    * 
    * <pre>
    * Created simple undirected graph where integers are vertice values:
    * [1] -> [ 2 ]
    * [2] -> [ 1, 3, 4, 5 ]
    * [3] -> [ 2, 4, 5 ]
    * [4] -> [ 2, 3, 5 ]
    * [5] -> [ 2, 3, 4 ]
    * </pre>
    * 
    * @return The graph as a string.
    */
   @Override
   public String toString() {
      StringBuilder output = new StringBuilder();
      for (Map.Entry<Vertex<T>, List<Edge<T>>> entry : edgeList.entrySet()) {
         output.append("[");
         output.append(entry.getKey().toString());
         output.append("] -> [ ");
         int counter = 0;
         int count = entry.getValue().size();
         for (Edge<T> edge : entry.getValue()) {
            output.append(edge.getDestination().toString());
            if (counter < count - 1) {
               output.append(", ");
            }
            counter++;
         }
         output.append(" ]\n");
      }
      return output.toString();
   }
}
