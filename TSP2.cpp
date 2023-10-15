#include <iostream>
#include <vector>
#include <cmath>
#include <algorithm>

using namespace std;

struct City {
    int x, y;
};

double distance(const City& city1, const City& city2) {
    return sqrt((city1.x - city2.x) * (city1.x - city2.x) +
                (city1.y - city2.y) * (city1.y - city2.y));
}

double totalDistance(const vector<City>& cities, const vector<int>& tour) {
    double dist = 0.0;
    for (int i = 0; i < tour.size() - 1; i++) {
        dist += distance(cities[tour[i]], cities[tour[i + 1]]);
    }
    dist += distance(cities[tour.back()], cities[tour[0]]); // Return to the starting city
    return dist;
}

int main() {
    int numCities;

    cout << "Enter the number of cities: ";
    cin >> numCities;

    vector<City> cities(numCities);
    vector<int> tour(numCities);

    cout << "Enter the Coordinates of each city (x y):" << endl;
    for (int i = 0; i < numCities; i++) {
        cin >> cities[i].x >> cities[i].y;
        tour[i] = i; // Initialize the tour as 0, 1, 2, ..., numCities-1
    }

    double minDist = totalDistance(cities, tour);
    vector<int> minTour = tour;

    // Generate all permutations of the tour and find the shortest one
    while (next_permutation(tour.begin(), tour.end())) {
        double dist = totalDistance(cities, tour);
        if (dist < minDist) {
            minDist = dist;
            minTour = tour;
        }
    }

    cout << "Shortest tour: ";
    for (int city : minTour) {
        cout << city << " ";
    }
    cout << endl;
    cout << "Shortest distance: " << minDist << endl;

    return 0;
}
