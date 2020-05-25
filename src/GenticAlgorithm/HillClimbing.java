package GenticAlgorithm;

import ConfigParameters.ConfigParameters;
import Models.Route;
import Reader.Reader;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class HillClimbing implements InitializationApproach {
    int iterationsBeforeMaxima;

    public HillClimbing(int iterationsBeforeMaxima) {
        this.iterationsBeforeMaxima = iterationsBeforeMaxima;
    }


    //Ths is the method that returns a list of routes based on hill climbing initialization.
    @Override
    public List<Route> population() {
        int numberOfCities = ConfigParameters.numberOfCities;
        double[][] citiesDistance = new double[numberOfCities][numberOfCities];
        Reader reader = new Reader(ConfigParameters.travelDataPath);
        try {
            citiesDistance = reader.getCitiesDistance();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        List<Route> initialPopulation = new ArrayList<>();
        for (int i = 0; i < ConfigParameters.populationSize; i++) {
            Route route = new Route(randomRoute(), numberOfCities, citiesDistance, 0);

            Route neighborRoute;

            for (int j = 0; j < iterationsBeforeMaxima; j++) {
                neighborRoute = getNeighbor(route, numberOfCities);
                if (neighborRoute.getFitness() < route.getFitness()) {
                    route = neighborRoute;
                }
            }

            initialPopulation.add(route);

        }
        return initialPopulation;

    }

    //This method returns a neighbor based on the incomming route.
    public Route getNeighbor(Route r, int numberOfCities) {
        Random rn = new Random();
        int x1 = 0, x2 = 0;
        while (x1 == x2 || x1 >= r.getSolution().size() || x2 >= r.getSolution().size()) {
            x1 = (int) (Math.random() * numberOfCities);
            x2 = (int) (Math.random() * numberOfCities);
        }
        int number1 = r.getSolution().get(x1);
        int number2 = r.getSolution().get(x2);
        r.getSolution().set(x1, number1);
        r.getSolution().set(x2, number2);

        return r;
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
}
