package clustering;

import java.util.ArrayList;

/**
 *
 * @author tom
 */
public class KMeans2d {
    private DataReader dataReader;
    private int clusterNo;
    private static ArrayList<Centroid> centroids = new ArrayList<>();
    
    public KMeans2d(DataReader dataReader, int clusterNo) {
        this.dataReader = dataReader;
        this.clusterNo = clusterNo;
    }
    
    public DataReader generateClusters() {
        DataReader resultData = dataReader;
        initialiseCentroids();
        
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
