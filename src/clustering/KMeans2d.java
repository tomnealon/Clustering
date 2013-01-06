package clustering;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author tom
 */
public class KMeans2d {
    private DataReader dataReader;
    private int clusterNo;
    private static ArrayList<Centroid> centroids = new ArrayList<>();
    private ArrayList<ArrayList> resultTable = new ArrayList<>();
    
    public KMeans2d(DataReader dataReader, int clusterNo) {
        this.dataReader = dataReader;
        this.clusterNo = clusterNo;
    }
    
    public DataReader generateClusters() {
        DataReader resultData = dataReader;
        initialiseCentroids();
        
        double minDist = 100000000000.0;
        ArrayList<Double> currentRow;
        ArrayList<Double> outputRow = new ArrayList<Double>();
        double currentDistance;
        Double clusterName = 99.9;
        boolean finished = false;
        
        
        for(int i = 0; i < dataReader.getNoRows(); i++){
            currentRow = dataReader.geRowAsDoubles(i);
            // Calculate centroid with minimum distance.
            for(int j = 0; j < clusterNo; j++) {
                currentDistance = calcDist(currentRow, centroids.get(j));
                if(currentDistance < minDist){
                    minDist = currentDistance;
                    clusterName = Double.valueOf(j);
                }
            }
            // Create ouput row.
            for(Double value : currentRow) {
                outputRow.add(value);
            }
            // Add it to the result table with the new classification.
            outputRow.add(clusterName);
            resultTable.add(outputRow);
            // Calculate new centroids.
            for(int j = 0; j < clusterNo; j++) {
                double totalX = 0;
                double totalY = 0;
                int clusterPop = 0;
                for(int k = 0; k < resultTable.size(); k++) {
                    // Check if result row cluster is in the current cluster
                    // being checked.
                    if(resultTable.get(k).get(2) == j){
                        // If it is then increment the clusterPopulation and
                        // add the two dimensions to the running total.
                        totalX = totalX + (Double) resultTable.get(k).get(0);
                        totalY = totalY + (Double) resultTable.get(k).get(1);
                        clusterPop++;
                    }
                }
                // Re-calculate the centroid for each class based on its running
                // population.
                if(clusterPop > 0){
                    centroids.get(j).setX(totalX / clusterPop);
                    centroids.get(j).setY(totalY / clusterPop);
                }
            }
        }
        
        // Keep calculating new centroids until they stop changing.
        while(!finished) {            
            for(int j = 0; j < clusterNo; j++) {
                double totalX = 0;
                double totalY = 0;
                int clusterPop = 0;
                for(int k = 0; k < resultTable.size(); k++) {
                    if(resultTable.get(k).get(2) == j){
                        // If it is then increment the clusterPopulation and
                        // add the two dimensions to the running total.
                        totalX = totalX + (Double) resultTable.get(k).get(0);
                        totalY = totalY + (Double) resultTable.get(k).get(1);
                        clusterPop++;
                    }
                }
                if(clusterPop > 0){
                    centroids.get(j).setX(totalX / clusterPop);
                    centroids.get(j).setY(totalY / clusterPop);
                }
            }
            finished = true;
            for(int i = 0; i < resultTable.size(); i++)
            {
                ArrayList<Double> tempRow = resultTable.get(i);
                minDist = 10000;
                for(int j = 0; j < clusterNo; j++)
                {
                    currentDistance = calcDist(tempRow, centroids.get(j));
                    if(currentDistance < minDist){
                        minDist = currentDistance;
                        clusterName = Double.valueOf(j);
                    }
                }
                tempRow.set(2, clusterName);
                if(tempRow.get(2) != clusterName){
                    tempRow.set(2, clusterName);
                    finished = false;
                }
            }
        }
        
        Iterator resultIt = resultTable.iterator();
        ArrayList<Double> tempRow;
        ArrayList<String> outRow = new ArrayList<>();
        while(resultIt.hasNext()) {
            tempRow = (ArrayList<Double>) resultIt.next();
            outRow.add(String.valueOf(tempRow.get(0)));
            outRow.add(String.valueOf(tempRow.get(1)));
            outRow.add(String.valueOf(tempRow.get(2)));
        }
        resultData.printAll();
        return resultData;
        
    }
    
    private void initialiseCentroids() {
        // Random Selection
        for(int i = 0; i < clusterNo; i++) {
            int randInt = (int)(Math.random()*dataReader.getNoRows());
            ArrayList<Double> newRow = dataReader.geRowAsDoubles(randInt);
            centroids.add(new Centroid(newRow.get(0), newRow.get(1)));
            
            System.out.println("Added new centroid with ("+newRow.get(0)+", "+newRow.get(1)+")");
        }
         
    }
    
    private static double calcDist(ArrayList<Double> row, Centroid centroid)
    {
        return Math.sqrt(Math.pow((centroid.getY() - row.get(1)), 2) + Math.pow((centroid.getX() - row.get(0)), 2));
    }
    
    private void labelRow(int label) {
        
    };
    
}
