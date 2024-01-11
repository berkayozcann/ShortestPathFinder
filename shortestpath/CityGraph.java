package shortestpath;

import java.util.*;

public class CityGraph {

    private ArrayList<String> cities;
    private int[][] distances;

    public CityGraph(ArrayList<String> cities, int[][] distances) {
        this.cities = cities;
        this.distances = distances;
    }

    public ArrayList<String> getCities() {
        return cities;
    }

    public int[][] getDistances() {
        return distances;
    }
}