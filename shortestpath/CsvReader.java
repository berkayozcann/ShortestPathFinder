package shortestpath;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CsvReader {

    public static CityGraph readCsv(String filePath) {
        ArrayList<String> cities = new ArrayList<>();
        int[][] distances;

        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(filePath)))) {
            // Read the first line to get the city names
            String[] cityNames = scanner.nextLine().split(",");
            for (int i = 1; i < cityNames.length; i++) {
                cities.add(cityNames[i]);
            }

            // Read the remaining lines to get the distances
            int cityCount = cities.size();
            distances = new int[cityCount][cityCount];
            for (int i = 0; i < cityCount; i++) {
                String[] distancesString = scanner.nextLine().split(",");
                for (int j = 1; j <= cityCount; j++) {
                    distances[i][j - 1] = Integer.parseInt(distancesString[j]);
                }
            }

            return new CityGraph(cities, distances);
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }
}