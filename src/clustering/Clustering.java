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
        DataReader data = new DataReader("./data_sources/s1.csv");
        KMeans2d kmeans = new KMeans2d(data, 14);
        kmeans.generateClusters("newFile.csv");
    }
}
