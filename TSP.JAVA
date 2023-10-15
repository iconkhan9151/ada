import java.util.Scanner;

public class TSP {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of cities: ");
        int numCities = scanner.nextInt();

        double[][] distances = new double[numCities][numCities];

        System.out.println("Enter the distances between cities (use 0 for the same city):");
        for (int i = 0; i < numCities; i++) {
            for (int j = 0; j < numCities; j++) {
                distances[i][j] = scanner.nextDouble();
            }
        }

        int[] tour = solveTSP(distances);

        System.out.println("Optimal Tour:");
        for (int i = 0; i < numCities; i++) {
            System.out.print(tour[i] + " -> ");
        }
        System.out.println(tour[0]); // Return to the starting city

        System.out.println("Total Distance: " + calculateTourDistance(tour, distances));

        scanner.close();
    }

    public static int[] solveTSP(double[][] distances) {
        int numCities = distances.length;
        int[] tour = new int[numCities];
        boolean[] visited = new boolean[numCities];

        // Start from the first city
        tour[0] = 0;
        visited[0] = true;

        for (int i = 1; i < numCities; i++) {
            int nearestCity = -1;
            double minDistance = Double.MAX_VALUE;

            for (int j = 0; j < numCities; j++) {
                if (!visited[j] && distances[tour[i - 1]][j] < minDistance) {
                    nearestCity = j;
                    minDistance = distances[tour[i - 1]][j];
                }
            }

            tour[i] = nearestCity;
            visited[nearestCity] = true;
        }

        return tour;
    }

    public static double calculateTourDistance(int[] tour, double[][] distances) {
        double totalDistance = 0;

        for (int i = 0; i < tour.length - 1; i++) {
            totalDistance += distances[tour[i]][tour[i + 1]];
        }

        // Return to the starting city
        totalDistance += distances[tour[tour.length - 1]][tour[0]];

        return totalDistance;
    }
}