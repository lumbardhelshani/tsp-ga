package ConfigParameters;

public class ConfigParameters {
    private ConfigParameters(){}

    public static int populationSize = 300;
    public static int reproductionSize = 200;
    public static int maxIterations = 1000;
    public static float mutationRate = 0.01f;
    public static int tournamentSize = 40;
    public static int numberOfCities = 90;
    public static int startingCity = 0;
    public static int targetFitness = 0;
    public static int routeSize = numberOfCities - 1;
    public static String travelDataPath = ".\\src\\data\\travel.txt";


}
