package tools;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class PopulationLogger {
    private ArrayList<int[]> populationLog;
    private int logInterval = 1000;
    private long lastLogTime;

    public PopulationLogger() {
        this.populationLog = new ArrayList<>();
        this.lastLogTime = System.currentTimeMillis();
    }

    public void logPopulation(int rocks, int papers, int scissors) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastLogTime >= logInterval) {
            populationLog.add(new int[]{rocks, papers, scissors});
            lastLogTime = currentTime;
        }
    }

    public ArrayList<int[]> getPopulationLog() {
        return populationLog;
    }

    public void printLog() {
        System.out.println("Population Log:");
        for (int i = 0; i < populationLog.size(); i++) {
            int[] entry = populationLog.get(i);
            System.out.printf("Time: %d sec -> Rocks: %d; Papers: %d; Scissors: %d%n",
                    i * logInterval/1000, entry[0], entry[1], entry[2]);
        }
    }

    public void exportToCSV(String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            // Write header
            writer.append("Time (sec);Rocks;Papers;Scissors\n");

            // Write each entry
            for (int i = 0; i < populationLog.size(); i++) {
                int[] entry = populationLog.get(i);
                writer.append(String.format("%d;%d;%d;%d\n", i * logInterval/1000, entry[0], entry[1], entry[2]));
            }

            System.out.println("Log exported successfully to " + fileName);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

}
