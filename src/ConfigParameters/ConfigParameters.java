package ConfigParameters;

//Here are the global parameters for the application.
public class ConfigParameters {
    private ConfigParameters() {
    }
    //Population Size.
    public static int populationSize = 20;
    //Reproduction Size, representing iterations
    // to select individual solutions from selection approaches.
    public static int reproductionSize = populationSize;
    //Maximum Iterations to Generate new Generations.
    //Maximum Number of new Generations to be created.
    public static int maxIterations = 2000;
    //Tournament Size to select from population.
    public static int tournamentSize = 10;
    //Mutation Rate.
    public static double mutationRate = 0.1;
    ///Crossover Rate.
    public static double crossoverRate = 1 - mutationRate;
    //Number of Cities in a Route.
    public static int numberOfCities = 90;
    //Starting city in a route, this is same as ending city
    public static int startingCity = 0;
    //Stop condition for the GA, 0 is an ideal fitness, it cannot never reached,
    //so the GA will stop after reaching the maximum number of iterations.
    public static int targetFitness = 0;
    //Route Length.
    public static int routeSize = numberOfCities-1;
    //Text file path for reading the data.
    public static String travelDataPath = ".\\src\\data\\travel.txt";
}


