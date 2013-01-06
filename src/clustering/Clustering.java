/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clustering;

import java.io.IOException;

/**
 *
 * @author tom
 */
public class Clustering {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Clustering.launch();
    }
    
    public static void launch() throws IOException {
        DataReader data = new DataReader("./data_sources/Book1.csv");
        KMeans2d kmeans = new KMeans2d(data, 3);
        kmeans.generateClusters();
    }
}
