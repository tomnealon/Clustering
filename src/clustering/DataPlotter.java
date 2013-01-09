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
//    private Color[] colourArray = {
//        Color.BLACK,
//        Color.BLUE,
//        Color.RED
//    };
    
    private JFrame frame = new JFrame("Cluster Result");
    private double[] min = {450000, 450000};
    private double[] max = {800000, 800000};
    private String[] emptyArrayString = {""};
    //private Plot2DPanel plot = new Plot2DPanel(min, max, emptyArrayString, emptyArrayString);
    private Plot2DPanel plot = new Plot2DPanel();
    
    public void plotScatter(ArrayList<ArrayList> dataTable, int clusters) {
//        Iterator tableIt = dataTable.iterator();
//        while(tableIt.hasNext()) {
//            ArrayList<Double> dataRow = (ArrayList<Double>) tableIt.next();
//            double[][] point = {{dataRow.get(0),dataRow.get(1)}};
//            plot.addPlot("SCATTER", "x", colourArray[(dataRow.get(2)).intValue()], point);
//        }
        for(int i = 0; i < clusters; i++) {
            plotColour("Cluster "+i, sortData(i, dataTable));
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
    
    private void plotColour(String name, double[][] doubleDataArray) {
        //plot.addPlot("SCATTER", "x", colour, doubleDataArray);
        plot.addScatterPlot(name, doubleDataArray);
    }
    
    
}
