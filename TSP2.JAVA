import java.util.Scanner;

public class TSPDynamicProgramming{
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
        int[][] dp = new int[1 << numCities][numCities];
        
        // Initialize the dynamic programming table
        for (int i = 0; i < (1 << numCities); i++) {
            for (int j = 0; j < numCities; j++) {
                dp[i][j] = -1;
            }
        }

        // Set the starting city (City 0) as the current city
        int startCity = 0;

        // Call the TSP DP function to find the optimal tour
        int optimalTour = tspDP((1 << numCities) - 1, startCity, distances, dp);

        // Reconstruct the tour
        int[] tour = new int[numCities];
        int mask = (1 << numCities) - 1;
        int currentCity = startCity;

        for (int i = 0; i < numCities; i++) {
            int nextCity = dp[mask][currentCity];
            tour[i] = currentCity;
            mask ^= (1 << currentCity);
            currentCity = nextCity;
        }

        return tour;
    }

    public static int tspDP(int mask, int currentCity, double[][] distances, int[][] dp) {
        int numCities = distances.length;

        if (mask == 0) {
            return 0; // Return to the starting city
        }

        if (dp[mask][currentCity] != -1) {
            return dp[mask][currentCity];
        }

        int minDistance = Integer.MAX_VALUE;
        int nextCity = -1;

        for (int city = 0; city < numCities; city++) {
            if ((mask & (1 << city)) != 0) {
                int newDistance = (int) distances[currentCity][city] + tspDP(mask ^ (1 << city), city, distances, dp);

                if (newDistance < minDistance) {
                    minDistance = newDistance;
                    nextCity = city;
                }
            }
        }

        dp[mask][currentCity] = nextCity;
        return minDistance;
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