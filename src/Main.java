import ConfigParameters.ConfigParameters;
import GenticAlgorithm.CrossoverType;
import GenticAlgorithm.InitializationType;
import GenticAlgorithm.SelectionType;
import GenticAlgorithm.TravelSalesman;
import Models.Route;
import Reader.Reader;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {
        int numberOfCities = ConfigParameters.numberOfCities;
        double[][] citiesDistance = getDoubles(numberOfCities);
        //printTravelPrices(citiesDistance, numberOfCities);
        TravelSalesman geneticAlgorithm = new TravelSalesman(SelectionType.ROULETTE, citiesDistance, InitializationType.RANDOM, CrossoverType.CYCLE);
        Route result = geneticAlgorithm.optimize();
        System.out.println(result);

    }

    //This method is used to read the distance matrix of cities from a txt file
    private static double[][] getDoubles(int numberOfCities) {
        double[][] citiesDistance = new double[numberOfCities][numberOfCities];
        Reader reader = new Reader(ConfigParameters.travelDataPath);
        try {
            citiesDistance = reader.getCitiesDistance();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return citiesDistance;
    }

    //This method is used to print in the console the distances between each city, corresponding to a matrix.
    public static void printTravelPrices(double[][] citiesDistance, int numberOfCities) {
        for (int i = 0; i < numberOfCities; i++) {
            for (int j = 0; j < numberOfCities; j++) {
                System.out.print(citiesDistance[i][j]);
                System.out.print("  ");
            }
            System.out.println("");
        }

    }
}
