package shortestpath;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.*;

public class ShortestPathApp extends Application {

    private CityGraph graph;
    private TextArea resultTextArea;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Shortest Path Finder");

        // Create UI components
        Label sourceLabel = new Label("Source City:");
        ChoiceBox<String> sourceChoiceBox = new ChoiceBox<>();

        Label destinationLabel = new Label("Destination City:");
        ChoiceBox<String> destinationChoiceBox = new ChoiceBox<>();

        Label algorithmLabel = new Label("Algorithm:");
        ChoiceBox<String> algorithmChoiceBox = new ChoiceBox<>();
        algorithmChoiceBox.getItems().addAll("DFS", "BFS");
        algorithmChoiceBox.setValue("BFS"); // Default to BFS

        Button findPathButton = new Button("Find Shortest Path");
        resultTextArea = new TextArea();
        resultTextArea.setEditable(false);

        // Set up the layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(
                sourceLabel, sourceChoiceBox,
                destinationLabel, destinationChoiceBox,
                algorithmLabel, algorithmChoiceBox,
                findPathButton, resultTextArea
        );

        // Set up the scene
        Scene scene = new Scene(layout, 400, 400);

        // Set the scene to the stage
        primaryStage.setScene(scene);

        // Show the stage
        primaryStage.show();

        // Load the graph from the CSV file
        graph = CsvReader.readCsv("src/resources/turkishcities.csv");

        if (graph == null) {
            resultTextArea.setText("Error loading the graph from the CSV file.");
        } else {
            // Populate choice boxes with city names
            sourceChoiceBox.getItems().addAll(graph.getCities());
            destinationChoiceBox.getItems().addAll(graph.getCities());

            // Event handler for the button
            findPathButton.setOnAction(event -> {
                String sourceCity = sourceChoiceBox.getValue();
                String destinationCity = destinationChoiceBox.getValue();
                String selectedAlgorithm = algorithmChoiceBox.getValue();

                int startIndex = graph.getCities().indexOf(sourceCity);
                int endIndex = graph.getCities().indexOf(destinationCity);

                if (startIndex == -1 || endIndex == -1) {
                    resultTextArea.setText("Invalid cities. Please enter valid cities.");
                    return;
                }

                resultTextArea.clear();

                switch (selectedAlgorithm) {
                    case "DFS":
                        resultTextArea.appendText("Using Depth-First Search:\n");
                        dfs(graph, startIndex, endIndex, 0, "", new boolean[graph.getCities().size()]);
                        break;
                    case "BFS":
                        resultTextArea.appendText("Using Breadth-First Search:\n");
                        bfs(graph, startIndex, endIndex);
                        break;
                    default:
                        resultTextArea.appendText("Invalid algorithm selection.");
                }
            });
        }
    }

    private void dfs(CityGraph graph, int current, int end, int distance, String path, boolean[] visited) {
        visited[current] = true;

        if (current == end) {
            resultTextArea.appendText("Shortest path found using DFS!\n");
            resultTextArea.appendText("Traversal Path: " + path + graph.getCities().get(current) + "\n");
            resultTextArea.appendText("Total Distance: " + distance + " km\n");
        } else {
            for (int neighbor = 0; neighbor < graph.getDistances()[current].length; neighbor++) {
                if (!visited[neighbor] && graph.getDistances()[current][neighbor] != 99999) {
                    dfs(graph, neighbor, end, distance + graph.getDistances()[current][neighbor],
                            path + graph.getCities().get(current) + " -> ", visited);
                }
            }
        }
    }

    private void bfs(CityGraph graph, int start, int end) {
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
                        resultTextArea.appendText("Shortest path found using BFS!\n");
                        List<Integer> path = reconstructPath(parent, start, end);
                        resultTextArea.appendText("Traversal Path: ");
                        for (int i = 0; i < path.size() - 1; i++) {
                            resultTextArea.appendText(graph.getCities().get(path.get(i)) + " -> ");
                        }
                        resultTextArea.appendText(graph.getCities().get(path.get(path.size() - 1)) + "\n");
                        resultTextArea.appendText("Total Distance: " + distance[end] + " km\n");
                        return;
                    }
                }
            }
        }

        resultTextArea.appendText("No path found using BFS.\n");
    }

    private List<Integer> reconstructPath(int[] parent, int start, int end) {
        List<Integer> path = new ArrayList<>();
        for (int at = end; at != -1; at = parent[at]) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }
 }
