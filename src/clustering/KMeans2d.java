package clustering;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tom
 */
public class KMeans2d {
    private DataReader dataReader;
    private int clusterNo;
    private static ArrayList<Centroid> centroids = new ArrayList<>();
    private ArrayList<ArrayList> resultTable = new ArrayList<>();
    private DataPlotter dataPlotter = new DataPlotter();
    
    private ToStringHelper help = new ToStringHelper();
    
    public KMeans2d(DataReader dataReader, int clusterNo) {
        this.dataReader = dataReader;
        this.clusterNo = clusterNo;
    }
    
    public void printResultTable() {
        for(ArrayList<Double> row : resultTable) {
            String rowString = help.toString(row);
            System.out.println(rowString);
        }
    }
    
    public DataReader generateClusters(String outputFile) {
        DataReader resultData = new DataReader();
        initialiseCentroids();
        
        double minDist = 100000000;
        ArrayList<Double> currentRow;
        ArrayList<Double> outputRow = new ArrayList<>();
        double currentDistance;
        Double clusterName = 99.9;
        boolean finished = false;
        
        
        for(int i = 0; i < dataReader.getNoRows(); i++){
            // Clear output row
            outputRow = new ArrayList<>();
            currentRow = dataReader.geRowAsDoubles(i);
            // Calculate centroid with minimum distance.
            minDist = 100000000;
            for(int j = 0; j < clusterNo; j++) {
                Centroid currentCentroid = centroids.get(j);
                currentDistance = calcDist(currentRow, currentCentroid);
//                String centroidString = "("+currentCentroid.getX()+", "+currentCentroid.getY()+")";
//                System.out.print("Distance between "+j+" at coords "+centroidString+" and row "+i+" = "+currentDistance);
                if(currentDistance < minDist) {
                    minDist = currentDistance;
                    clusterName = Double.valueOf(j);
//                    System.out.println(" moving to cluster "+j);
                } //else System.out.println();
            }
            // Create ouput row.
            for(Double value : currentRow) {    
                outputRow.add(value);
            }
            // Add it to the result table with the new classification.
            outputRow.add(clusterName);
            resultTable.add(outputRow);
//            System.out.println("Output row no. "+i+" added to result table "+help.toString(outputRow));
            // Calculate new centroids.
            for(int j = 0; j < clusterNo; j++) {
                Centroid currentCentroid = centroids.get(j);
//                System.out.println("Recalculating for Centroid "+j);
                double totalX = 0;
                double totalY = 0;
                int clusterPop = 0;
                for(int k = 0; k < resultTable.size(); k++) {
                    
                    // Check if result row cluster is in the current cluster
                    // being checked.
                    int intValue = ((Double) resultTable.get(k).get(2)).intValue();
                    if(intValue == j){
                        // If it is then increment the clusterPopulation and
                        // add the two dimensions to the running total.
                        totalX = totalX + (Double) resultTable.get(k).get(0);
                        totalY = totalY + (Double) resultTable.get(k).get(1);
                        clusterPop++;
                        //System.out.println("Result table entry "+k+" is in centroid "+intValue+" Pop now "+clusterPop);
                    }
                }
                // Re-calculate the centroid for each class based on its running
                // population.
                if(clusterPop > 0){
                    String oldStringCentroid = "("+currentCentroid.getX()+", "+currentCentroid.getY()+")";
                    Centroid oldCentroid = new Centroid(currentCentroid.getX(), currentCentroid.getY());
                    currentCentroid.setX(totalX / clusterPop);
                    currentCentroid.setY(totalY / clusterPop);
                    //System.out.println("Centroid "+j+" moved from "+oldStringCentroid+" to ("+currentCentroid.getX()+", "+currentCentroid.getY()+")");
                    System.out.println("Centroid "+j+" moved "+calcMove(oldCentroid, currentCentroid));
                }
            }
        }
//        System.out.println("Classification after inital runthrough");
//        printResultTable();
        
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
            for(int i = 0; i < resultTable.size(); i++) {
                ArrayList<Double> tempRow = resultTable.get(i);
                minDist = 100000000;
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
//        System.out.println("_______________________________________");
//        System.out.println("Final classification");
//        printResultTable();
        

        
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
        try {
            resultData.writeFile(outputFile);
        } catch (IOException ex) {
            Logger.getLogger(KMeans2d.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//        Centroid[] centroidsArr = Centroid[clusterNo];
//        Iterator centroidsIt = centroids.iterator();
//        while(centroidsIt.hasNext()) {
//            
//        }
//        
//        
//        Centroid[] centroidsArr = (Centroid[]) centroids.toArray();
        dataPlotter.plotScatter(resultTable, clusterNo, centroids);
        
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
    
    private double calcDist(ArrayList<Double> row, Centroid centroid)
    {
        return Math.sqrt(Math.pow((centroid.getY() - row.get(1)), 2) + Math.pow((centroid.getX() - row.get(0)), 2));
    }
    
    private double calcMove(Centroid oldCentroid, Centroid newCentroid) {
        return Math.sqrt(Math.pow((newCentroid.getY() - oldCentroid.getY()), 2) + Math.pow((newCentroid.getX() - oldCentroid.getX()), 2));
    }
    
    private void labelRow(int label) {
        
    };
    
}
