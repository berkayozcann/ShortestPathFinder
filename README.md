# Shortest Path Finder Project

## Overview

This project aims to explore the Shortest Path problem and implement two popular algorithms, Depth-First Search (DFS) and Breadth-First Search (BFS), to find the shortest path between two cities in a given network. The implementation is done in Java using JavaFX for the graphical user interface.


## Features

- **Graph Representation:** The project uses a CityGraph class to represent the network of cities and their distances.

- **Algorithms:** Implements DFS and BFS algorithms to find the shortest path between two cities.

- **User Interface:** Provides a user-friendly JavaFX interface for users to input source and destination cities, choose the algorithm, and view the results.

- **Command-Line Interface:** An alternative `ShortestPathFinder` class allows users to interact with the application via the command line.

## Getting Started

### Prerequisites

- Java 17
- JavaFX library
- IDE with Java support (e.g., IntelliJ, Eclipse)

### Installation

1. Clone the repository to your local machine.

   ```bash
   git clone https://github.com/berkayozcann/ShortestPathFinder.git

2. Open the project in your preferred IDE.

3. Resolve any dependencies and ensure JavaFX is properly configured.
  

## Usage
- Run the ShortestPathApp class for the JavaFX application.
- Input source and destination cities, choose the algorithm, and click "Find Shortest Path."
- Alternatively, run the ShortestPathFinder class for the command-line interface.




## Project Structure

- **src/shortestpath/CityGraph.java**: Represents the network of cities and distances.
- **src/shortestpath/CsvReader.java**: Reads city and distance data from a CSV file.
- **src/shortestpath/ShortestPathApp.java**: JavaFX application for the graphical interface.
- **src/shortestpath/ShortestPathFinder.java**: Command-line interface for interacting with the application.

## Data Source

The project utilizes a CSV file containing information about cities and distances. The data file is located at:

- [turkishcities.csv](./resources/turkishcities.csv)

This CSV file is structured with city names in the first row and corresponding distances in the subsequent rows, forming a matrix of city distances.




## License
This project is licensed under the [MIT License](LICENSE).

Feel free to adjust the content and structure to better fit your project's specifics.




