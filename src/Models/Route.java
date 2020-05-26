package Models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//The Route class represent a candidate solution, which is an individual solution.
public class Route implements Comparable {

    // This list of integers represent the solution of TSP, and contains integer values to represent the cities that Salesman must visit.
    private List<Integer> solution;
    //In this integer variable is stored the starting point (city) of the route.
    private int startingCity;
    // numberOfCities contains the total number of the cities that Salesman must visit (in our case 90 cities).
    int numberOfCities = 0;
    //This is a n*n matrix (n - number of cities), representing the distances between each city.
    double[][] citiesDistance;
    //This is the
    double fitness;

    public double tourProbability;

    //This constructor is use to
    public Route(int numberOfCities, double[][] citiesDistance, int startingCity) {
        this.citiesDistance = citiesDistance;
        this.startingCity = startingCity;
        this.numberOfCities = numberOfCities;
        solution = randomSalesman();
        fitness = this.calculateFitness();
    }


    //This constructor is used to generate a Route with a specific order of cities, defined from user
    public Route(List<Integer> permutationOfCities, int numberOfCities, double[][] citiesDistance, int startingCity) {
        solution = permutationOfCities;
        this.citiesDistance = citiesDistance;
        this.startingCity = startingCity;
        this.numberOfCities = numberOfCities;
        fitness = this.calculateFitness();
    }

    //These methods are called getters and setters, and are used to achieve encapsulation for security issues.
    //They will be called from another classes, outside of this class to get its properties.
    public List<Integer> getSolution() {
        return solution;
    }

    public void setSolution(List<Integer> sol) {
        this.solution = sol;
    }

    public int getStartingCity() {
        return startingCity;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }


    public double calculateFitness() {
        double fitness = 0;
        int currentCity = startingCity;
        for (int city : solution) {
            fitness += citiesDistance[currentCity][city];
            currentCity = city;
        }

        fitness += citiesDistance[solution.get(numberOfCities - 2)][startingCity];
        return fitness;
    }

    public void setTourProbability(double total, double fitness) {
        this.tourProbability = fitness / total;
    }

    private List<Integer> randomSalesman() {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < numberOfCities; i++) {
            if (i != startingCity)
                result.add(i);
        }
        Collections.shuffle(result);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Route: [");
        sb.append(startingCity);
        for (int gene : solution) {
            sb.append("->");
            sb.append(gene);
        }
        sb.append("->");
        sb.append(startingCity);
        sb.append("]\nLength: ");
        sb.append(this.fitness);
        return sb.toString();
    }


    @Override
    public int compareTo(Object o) {
        Route route = (Route) o;
        if (this.fitness > route.getFitness())
            return 1;
        else if (this.fitness < route.getFitness())
            return -1;
        else
            return 0;
    }
}
