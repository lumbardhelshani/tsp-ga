package Reader;

import ConfigParameters.ConfigParameters;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//This class is modeled to read route distances between cities from a file.
public class Reader {
    private String filepath;

    public Reader(String filepath) {
        this.filepath = filepath;
    }

    //This method reads from a file, which contains a matrix with the distances between each city, and returns a 2D matrix with travel distances.
    public double[][] getCitiesDistance() throws FileNotFoundException {
        File file = new File(filepath);
        Scanner sc = new Scanner(file);
        double[][] distances = new double[ConfigParameters.numberOfCities][ConfigParameters.numberOfCities];
        String st;
        int city = 0;
        while (sc.hasNextLine()) {
            st = sc.nextLine();
            String[] vals = st.split(",");

            for (int i = 0; i < vals.length; i++) {
                distances[city][i] = Double.parseDouble(vals[i]);
            }
            city++;
        }
        return distances;
    }
}

