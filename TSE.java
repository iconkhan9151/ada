import java.io.*;
import java.util.*;

public class TSE {
    // Initialize variables
    static int n;
    static int MAX = 1000000;
    static int[][] dist;
    static int[][] memo;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get the number of cities from the user
        System.out.print("Enter the Number of cities: ");
        n = scanner.nextInt();

        // Initialize the arrays with the correct size
        dist = new int[n + 1][n + 1];
        memo = new int[n + 1][1 << (n + 1)];

        // Get the distance matrix from the user
        System.out.println("Enter the distance matrix (0-based indexing):");
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                dist[i][j] = scanner.nextInt();
            }
        }

        int ans = MAX;

        for (int i = 1; i <= n; i++) {
            ans = Math.min(ans, fun(i, (1 << (n + 1)) - 1) + dist[i][1]);
        }

        System.out.println("The cost of the most efficient tour = " + ans);
    }

    // Recursive function to calculate the TSP
    static int fun(int i, int mask) {
        if (mask == ((1 << i) | 3)) {
            return dist[1][i];
        }

        if (memo[i][mask] != 0) {
            return memo[i][mask];
        }

        int res = MAX;

        for (int j = 1; j <= n; j++) {
            if ((mask & (1 << j)) != 0 && j != i && j != 1) {
                res = Math.min(res, fun(j, mask & (~(1 << i))) + dist[j][i]);
            }
        }

        return memo[i][mask] = res;
    }
}
