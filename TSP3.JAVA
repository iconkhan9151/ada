import java.util.Scanner;

public class TSP3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of cities: ");
        int numCities = scanner.nextInt();
        double[][] coordinates = new double[numCities][2];

        // Input city coordinates
        for (int i = 0; i < numCities; i++) {
            System.out.print("Enter the x-Coordinate of city " + (i + 1) + ": ");
            coordinates[i][0] = scanner.nextDouble();
            System.out.print("Enter the y-coordinate of city " + (i + 1) + ": ");
            coordinates[i][1] = scanner.nextDouble();
        }

        int startingCity = 0; // Starting city (can be changed)

        // Initialize the memoization table
        int numSubsets = 1 << numCities;
        double[][] dp = new double[numSubsets][numCities];
        for (int i = 0; i < numSubsets; i++) {
            for (int j = 0; j < numCities; j++) {
                dp[i][j] = Double.POSITIVE_INFINITY;
            }
        }

        // Set the base case
        dp[1 << startingCity][startingCity] = 0;

        // Dynamic programming to calculate the shortest tour
        for (int mask = 1; mask < numSubsets; mask += 2) {
            for (int currentCity = 0; currentCity < numCities; currentCity++) {
                if ((mask & (1 << currentCity)) != 0) {
                    for (int nextCity = 0; nextCity < numCities; nextCity++) {
                        if ((mask & (1 << nextCity)) == 0) {
                            double newDistance = dp[mask][currentCity] + distance(coordinates[currentCity], coordinates[nextCity]);
                            dp[mask | (1 << nextCity)][nextCity] = Math.min(dp[mask | (1 << nextCity)][nextCity], newDistance);
                        }
                    }
                }
            }
        }

        // Calculate the final result
        double minDistance = Double.POSITIVE_INFINITY;
        for (int i = 0; i < numCities; i++) {
            if (i != startingCity) {
                double tourDistance = dp[numSubsets - 1][i] + distance(coordinates[i], coordinates[startingCity]);
                minDistance = Math.min(minDistance, tourDistance);
            }
        }

        System.out.println("Minimum distance for TSP: " + minDistance);

        scanner.close();
    }

    public static double distance(double[] city1, double[] city2) {
        double x1 = city1[0];
        double y1 = city1[1];
        double x2 = city2[0];
        double y2 = city2[1];
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }
}
