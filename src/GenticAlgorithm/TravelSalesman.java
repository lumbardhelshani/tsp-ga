package GenticAlgorithm;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import ConfigParameters.ConfigParameters;
import Models.Route;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class TravelSalesman {
    private int populationSize;
    private int routeSize;
    private int numberOfCities;
    private int reproductionSize;
    private int maxIterations;
    private float mutationRate;
    private int tournamentSize;
    private SelectionType selectionType;
    private double[][] citiesDistance;
    private int startingCity;
    private int targetFitness;
    private Route minimalRoute;
    private GeneticOperators operators;

    public TravelSalesman(SelectionType selectionType, double[][] citiesDistance) {
        this.selectionType = selectionType;
        this.citiesDistance = citiesDistance;
        this.numberOfCities = ConfigParameters.numberOfCities;
        this.routeSize = ConfigParameters.routeSize;
        this.startingCity = ConfigParameters.startingCity;
        this.targetFitness = ConfigParameters.targetFitness;
        this.populationSize = ConfigParameters.populationSize;
        this.reproductionSize = ConfigParameters.reproductionSize;
        this.maxIterations = ConfigParameters.maxIterations;
        this.mutationRate = ConfigParameters.mutationRate;
        this.tournamentSize = ConfigParameters.tournamentSize;
        operators = new GeneticOperators(this.citiesDistance);
    }

    public List<Route> initialPopulation() {
        List<Route> population = new ArrayList();

        for (int i = 0; i < this.populationSize; ++i) {
            population.add(new Route(this.numberOfCities, this.citiesDistance, this.startingCity));
        }
        return population;
    }

    public List<Route> selection(List<Route> population) {
        List<Route> selected = new ArrayList();

        for (int i = 0; i < this.reproductionSize; ++i) {
            if (this.selectionType == SelectionType.ROULETTE) {
                selected.add(this.rouletteSelection(population));
            } else if (this.selectionType == SelectionType.TOURNAMENT) {
                selected.add(this.tournamentSelection(population));
            }
        }
        return selected;
    }

    public Route rouletteSelection(List<Route> population) {
        double totalFitness = population.stream().map(Route::getFitness).mapToDouble(Double::doubleValue).sum();
        Random random = new Random();
        double selectedValue = random.nextInt((int) totalFitness);
        float recValue = 1.0F / (float) selectedValue;
        float currentSum = 0.0F;
        Iterator iterator = population.iterator();

        Route genome;
        do {
            if (!iterator.hasNext()) {
                int selectRandom = random.nextInt(this.populationSize);
                return (Route) population.get(selectRandom);
            }

            genome = (Route) iterator.next();
            currentSum += 1.0F / (float) genome.getFitness();
        } while (currentSum < recValue);

        return genome;
    }

    public static <E> List<E> pickNRandomElements(List<E> list, int n) {
        Random r = new Random();
        int length = list.size();
        if (length < n) {
            return null;
        } else {
            for (int i = length - 1; i >= length - n; --i) {
                Collections.swap(list, i, r.nextInt(i + 1));
            }

            return list.subList(length - n, length);
        }
    }

    public Route tournamentSelection(List<Route> population) {
        List<Route> selected = pickNRandomElements(population, this.tournamentSize);
        return (Route) Collections.min(selected);
    }

    public List<Route> createGeneration(List<Route> population) {
        List<Route> generation = new ArrayList();

        for (int currentGenerationSize = 0; currentGenerationSize < this.populationSize; currentGenerationSize += 2) {
            List<Route> parents = pickNRandomElements(population, 2);
            List<Route> children = this.operators.PMXcrossover(parents);
            children.set(0, this.operators.SWAPmutate((Route) children.get(0)));
            children.set(1, this.operators.SWAPmutate((Route) children.get(1)));
            generation.addAll(children);
        }

        return generation;
    }

    public Route optimize() {
        //This two line below this comment should be removed from the comment afer we have a fully implemented WindowTSP display
        //City[] citiesCoordinates = Reader.getCitiesCoordinates("C:\\Users\\lumba\\OneDrive\\Desktop\\citiesCoordinates.txt");
        //WindowTSP display = new WindowTSP(citiesCoordinates);;
        List<Route> population = this.initialPopulation();
        Route globalBestGenome = (Route) population.get(0);
        minimalRoute = globalBestGenome;

        for (int i = 0; i < ConfigParameters.maxIterations; ++i) {
            List<Route> selected = this.selection(population);
            population = this.createGeneration(selected);
            population.add(minimalRoute);
            globalBestGenome = (Route) Collections.min(population);
            System.out.println(globalBestGenome);
            if (globalBestGenome.getFitness() < this.targetFitness) {
                break;
            } else if (globalBestGenome.getFitness() < minimalRoute.getFitness()) {

                minimalRoute = globalBestGenome;
                //display.draw(globalBestGenome);
            }
        }

        return minimalRoute;

    }

    public void printGeneration(List<Route> generation) {
        Iterator var2 = generation.iterator();

        while (var2.hasNext()) {
            Route genome = (Route) var2.next();
            System.out.println(genome);
        }

    }
}
