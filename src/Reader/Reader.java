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

    //This method is only used to get cities x and y coordinates and draw them in a panel, but the display is not fully implemented.
    //Undo the block comment after the display if fully implemented.
    /*public static City[] getCitiesCoordinates(String filepath) {
        int numOfCities = ConfigParameters.numberOfCities;
        City[] cities = new City[numOfCities];
        try {
            File file = new File(filepath);
            Scanner sc = new Scanner(file);
            String st;
            int i = 0;
            while (sc.hasNextLine()) {
                st = sc.nextLine();
                String[] vals = st.split(" ");
                int id = Integer.parseInt(vals[0]);
                int x = Integer.parseInt(vals[1]);
                int y = Integer.parseInt(vals[2]);
                City c = new City(id, x, y);
                cities[i] = c;
                i++;

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return cities;
    }*/

}

