package ConfigParameters;


//Here are the global parameters for the application.
public class ConfigParameters {
    private ConfigParameters() {
    }

    public static int populationSize = 20;
    public static int reproductionSize = populationSize;
    public static int maxIterations = 2000;
    public static int tournamentSize = 10;
    public static double mutationRate = 0.1;
    public static double crossoverRate = 1 - mutationRate;
    public static int numberOfCities = 90;
    public static int startingCity = 0;
    public static int targetFitness = 0;
    public static int routeSize = numberOfCities - 1;
    //public static int hillClimbingMaxNeighbors = populationSize;
    public static String travelDataPath = ".\\src\\data\\travel.txt";
}
