package shortestpath;

import java.util.*;
import javafx.scene.control.TextArea;

public class ShortestPathFinder {

    public static void main(String[] args) {
        // Load the graph from the CSV file
        CityGraph graph = CsvReader.readCsv("src/resources/turkishcities.csv");

        if (graph == null) {
            System.out.println("Error loading the graph from the CSV file.");
            return;
        }

        // Create a Scanner for user input
        Scanner scanner = new Scanner(System.in);

        // Prompt the user for the source city
        System.out.print("Enter the source city: ");
        String sourceCity = scanner.nextLine();

        // Prompt the user for the destination city
        System.out.print("Enter the destination city: ");
        String destinationCity = scanner.nextLine();

        // Find the index of the source and destination cities in the graph
        int startIndex = graph.getCities().indexOf(sourceCity);
        int endIndex = graph.getCities().indexOf(destinationCity);

        // Check if the cities are valid
        if (startIndex == -1 || endIndex == -1) {
            System.out.println("Invalid cities. Please enter valid cities.");
            return;
        }

        // Use DFS to find the shortest path
        System.out.println("\nUsing Depth-First Search:");
        dfs(graph, startIndex, endIndex, 0, new ArrayList<>(), new boolean[graph.getCities().size()]);

        // Use BFS to find the shortest path
        System.out.println("\nUsing Breadth-First Search:");
        // Create a TextArea instance for the resultTextArea
        TextArea resultTextArea = new TextArea();
        bfs(graph, startIndex, endIndex, resultTextArea);

        // Close the scanner
        scanner.close();
    }

    public static void dfs(CityGraph graph, int current, int end, int distance, List<Integer> path, boolean[] visited) {
        visited[current] = true;
        path.add(current);

        if (current == end) {
            System.out.println("Shortest path found using DFS!");
            System.out.println("Traversal Path:");
            for (int city : path) {
                System.out.println(graph.getCities().get(city));
            }
            System.out.println("Total Distance: " + distance + " km");
        } else {
            for (int neighbor = 0; neighbor < graph.getDistances()[current].length; neighbor++) {
                if (!visited[neighbor] && graph.getDistances()[current][neighbor] != 99999) {
                    dfs(graph, neighbor, end, distance + graph.getDistances()[current][neighbor], path, visited);
                }
            }
        }
    }

    public static void bfs(CityGraph graph, int start, int end, TextArea resultTextArea) {
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[graph.getCities().size()];
        int[] parent = new int[graph.getCities().size()];
        int[] distance = new int[graph.getCities().size()];

        queue.offer(start);
        visited[start] = true;
        parent[start] = -1;

        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (int neighbor = 0; neighbor < graph.getDistances()[current].length; neighbor++) {
                if (!visited[neighbor] && graph.getDistances()[current][neighbor] != 99999) {
                    queue.offer(neighbor);
                    visited[neighbor] = true;
                    parent[neighbor] = current;
                    distance[neighbor] = distance[current] + graph.getDistances()[current][neighbor];

                    if (neighbor == end) {
                        // Reconstruct and print the path
                        List<Integer> path = reconstructPath(parent, start, end);
                        resultTextArea.appendText("Shortest path found using BFS!\n");
                        resultTextArea.appendText("Traversal Path (Indices): " + path + "\n");
                        resultTextArea.appendText("Traversal Path (Cities):\n");
                        for (int city : path) {
                            resultTextArea.appendText(graph.getCities().get(city) + "\n");
                        }
                        resultTextArea.appendText("Total Distance: " + distance[end] + " km\n");
                        return;
                    }
                }
            }
        }

        resultTextArea.appendText("No path found using BFS.\n");
    }

    private static List<Integer> reconstructPath(int[] parent, int start, int end) {
        List<Integer> path = new ArrayList<>();
        for (int at = end; at != -1; at = parent[at]) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }
}
