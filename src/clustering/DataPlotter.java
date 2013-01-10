/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clustering;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JFrame;
import org.math.plot.Plot2DPanel;

/**
 *
 * @author tom
 */
public class DataPlotter {
    private ArrayList<ArrayList> resultTable = new ArrayList<>();
    private Color[] colourArray = {
        Color.BLUE,
        Color.RED, 
        Color.GREEN, 
        Color.YELLOW, 
        Color.ORANGE, 
        Color.PINK, 
        Color.CYAN, 
        Color.MAGENTA,
        Color.GRAY,
        Color.DARK_GRAY,
        new Color(120, 10, 128),
        Color.LIGHT_GRAY
    };
    private int currentColour = 0;
    
    
    
    private JFrame frame = new JFrame("Cluster Result");
    private double[] min = {450000, 450000};
    private double[] max = {800000, 800000};
    private String[] emptyArrayString = {""};
    private Plot2DPanel plot = new Plot2DPanel();
    
    public void plotScatter(ArrayList<ArrayList> dataTable, int clusters, ArrayList<Centroid> centroids) {
        for(int i = 0; i < clusters; i++) {
            Centroid currentCentroid = centroids.get(i);
            plotColour("Cluster "+i, sortData(i, dataTable), currentCentroid);
        }
        
        frame.setContentPane(plot);
        frame.setVisible(true);
    } 
    
    private double[][] sortData(int cluster, ArrayList<ArrayList> dataTable) {
        
        ArrayList<double[]> outputList = new ArrayList<>();
        Iterator tableIt = dataTable.iterator();
        int i = 0;
        while(tableIt.hasNext()) {
            ArrayList<Double> dataList = (ArrayList<Double>) tableIt.next();
            double[] dataArray = new double[2];
            if(dataList.get(2).intValue() == cluster) {
                ArrayList<Double> coordsRow = new ArrayList<>();
                dataArray[0] = (dataList.get(0));
                dataArray[1] = (dataList.get(1));
                if(dataArray[0] != 0) {
                    outputList.add(dataArray);
                }
                
                //output.(coordsRow.toArray());
                i++;
            }
            
        }
        double[][] output = new double[i+1][2];
        i = 0;
        for(double[] row : outputList) {
            output[i] = row;
            i++;
        }
        return output;
    }
    
    private void plotColour(String name, double[][] doubleDataArray, Centroid centroid) {
        double[][] centroidCoords = {{centroid.getX(),centroid.getY()}};
        Color clusterColour = getColour();
        plot.addScatterPlot(name, clusterColour, doubleDataArray);
        plot.addPlot("SCATTER", "X", Color.BLACK, centroidCoords);
    }
    
    private Color getColour() {
        Color newColour = colourArray[currentColour];
        if(currentColour == 11) {
            currentColour = 0;
        } else {
            currentColour++;
        }
        return newColour;
    }
    
    
}
