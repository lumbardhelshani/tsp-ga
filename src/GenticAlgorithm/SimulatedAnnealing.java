package GenticAlgorithm;

import ConfigParameters.ConfigParameters;
import Models.Route;
import Reader.Reader;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimulatedAnnealing implements InitializationApproach {
    private double startingTemperature;
    private double coolingRate;
    private double[][] citiesDistance;

    public SimulatedAnnealing(double startingTemperature, double coolingRate) {
        this.startingTemperature = startingTemperature;
        this.coolingRate = coolingRate;
        int numberOfCities = ConfigParameters.numberOfCities;
        citiesDistance = new double[numberOfCities][numberOfCities];
        Reader reader = new Reader(ConfigParameters.travelDataPath);
        try {
            citiesDistance = reader.getCitiesDistance();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Ths is the method that returns a list of routes based on simulated annealing initialization.
    @Override
    public List<Route> population() {
        List<Route> initialPopulation = new ArrayList<>();
        for (int i = 0; i < ConfigParameters.populationSize; i++) {
            Route currentRoute = new Route(randomRoute(), 90, citiesDistance, 0);
            Route bestRoute = currentRoute;
            while (startingTemperature > 1) {
                // Create new neighbour tour
                List<Integer> permutations = randomRoute();
                Route newRoute = new Route(permutations, 90, citiesDistance, 0);

                newRoute = getNeighbor(newRoute);

                // Get energy of solutions
                double currentEnergy = currentRoute.getFitness();
                double neighbourEnergy = newRoute.getFitness();

                // Decide if we should accept the neighbour
                if (acceptanceProbability(currentEnergy, neighbourEnergy, startingTemperature) > Math.random()) {
                    currentRoute = newRoute;
                }

                // Keep track of the best solution found
                if (currentRoute.getFitness() < bestRoute.getFitness()) {
                    bestRoute = currentRoute;

                }

                // Cool system
                startingTemperature *= 1 - coolingRate;
            }

            initialPopulation.add(bestRoute);
        }
        // Loop until system has cooled

        return initialPopulation;
    }

    //This method returns a neighbor route based on swap mutation for the input rotue.
    private Route getNeighbor(Route r){
        GeneticOperators go = new GeneticOperators(citiesDistance);
        return go.SWAPmutate(r);
    }

    //This method returns the number of neighbors for a specific route, decided by user.
    private List<Route> getNeigbhors(Route r, int nr){
        List<Route> neighbors = new ArrayList<>();
        for (int i = 0; i < nr; i++) {
            Route neighbor = getNeighbor(r);
            neighbors.add(neighbor);
        }
        return neighbors;
    }

    //This method is used to initialize a random route.
    private List<Integer> randomRoute() {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < ConfigParameters.numberOfCities; i++) {
            if (i != ConfigParameters.startingCity)
                result.add(i);
        }
        Collections.shuffle(result);
        return result;
    }

    //This methods checks the probability to choose or not a specific route.
    public static double acceptanceProbability(double energy, double newEnergy, double temperature) {
        // If the new solution is better, accept it
        if (newEnergy < energy) {
            return 1.0;
        }
        // If the new solution is worse, calculate an acceptance probability
        return Math.exp((energy - newEnergy) / temperature);
    }
}
