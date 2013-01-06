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
    
    public DataReader assignClusters() {
        DataReader resultData = dataReader;
        return resultData;
    }
    
    private void initialiseCentroids() {
        // Random Selection
        for(int i = 0; i < clusterNo; i++) {
            int randInt = (int)(Math.random()*dataReader.getNoRows());
            ArrayList<Double> newRow = dataReader.geRowAsDoubles(randInt);
            Centroid newCentroid = new Centroid(newRow.get(0), newRow.get(1));
        }
         
    }
    
}
