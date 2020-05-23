package GenticAlgorithm;

import ConfigParameters.ConfigParameters;
import Models.Route;

import java.util.*;

public class GeneticOperators {

    private int routeSize;
    private int numberOfCities;
    private int startingCity;
    private double mutationRate;
    private double[][] citiesDistance;


    public GeneticOperators(double[][] citiesDistance) {
        this.routeSize = ConfigParameters.routeSize;
        this.numberOfCities = ConfigParameters.numberOfCities;
        this.startingCity = ConfigParameters.startingCity;
        this.mutationRate = ConfigParameters.mutationRate;
        this.citiesDistance = citiesDistance;

    }

    public Route SWAPmutate(Route route) {
        Random random = new Random();
        float mutate = random.nextFloat();
        if (mutate < this.mutationRate) {
            List<Integer> mutatedRoute = route.getSolution();
            Collections.swap(mutatedRoute, random.nextInt(this.routeSize), random.nextInt(this.routeSize));
            return new Route(mutatedRoute, this.numberOfCities, this.citiesDistance, this.startingCity);
        } else {
            return route;
        }
    }

    public List<Route> PMXcrossover(List<Route> parents) {
        Random random = new Random();
        int breakpoint = random.nextInt(this.routeSize);
        List<Route> children = new ArrayList();
        List<Integer> parent1 = new ArrayList(parents.get(0).getSolution());
        List<Integer> parent2 = new ArrayList(parents.get(1).getSolution());

        int i;
        int newVal;
        for (i = 0; i < breakpoint; ++i) {
            newVal = (Integer) parent2.get(i);
            Collections.swap(parent1, parent1.indexOf(newVal), i);
        }

        children.add(new Route(parent1, this.numberOfCities, this.citiesDistance, this.startingCity));
        List<Integer> parents1Genome = parents.get(0).getSolution();

        for (i = breakpoint; i < this.routeSize; ++i) {
            newVal = parents1Genome.get(i);
            Collections.swap(parent2, parent2.indexOf(newVal), i);
        }

        children.add(new Route(parent2, this.numberOfCities, this.citiesDistance, this.startingCity));
        return children;
    }

    public List<Route> CYCLECcrossover(List<Route> parents) {
        //needs debug
        List<Integer> parent1 = new ArrayList(parents.get(0).getSolution());
        List<Integer> parent2 = new ArrayList(parents.get(1).getSolution());
        Route r1 = new Route(numberOfCities, citiesDistance, startingCity);
        Route r2 = new Route(numberOfCities, citiesDistance, startingCity);
        final int length = parent1.size();
        List<Route> children = new ArrayList<>(2);
        List<Integer> child1 = parent1;
        List<Integer> child2 = parent2;
        Set<Integer> visitedIndices = new HashSet<Integer>(length);
        List<Integer> indices = new ArrayList<Integer>(length);
        int idx = 0;
        int cycle = 1;
        while (visitedIndices.size() < length) {
            indices.add(idx);
            int item = parent2.get(idx);
            idx = parent1.indexOf(item);

            while (idx != indices.get(0)) {
                indices.add(idx);
                item = parent2.get(idx);
                idx = parent1.indexOf(item);
            }

            if (cycle++ % 2 != 0) {
                for (int i : indices) {
                    int tmp = child1.get(i);
                    child1.set(i, child2.get(i));
                    child2.set(i, tmp);
                }
            }

            visitedIndices.addAll(indices);
            idx = (indices.get(0) + 1) % length;
            while (visitedIndices.contains(idx) && visitedIndices.size() < length) {
                idx++;
                if (idx >= length) {
                    idx = 0;
                }
            }
            indices.clear();
            r1.setSolution(child1);
            r2.setSolution(child2);

        }
        children.add(r1);
        children.add(r2);
        return children;
    }

}
