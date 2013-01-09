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
        Color.BLACK,
        Color.BLUE,
        Color.RED
    };
    
    private JFrame frame = new JFrame("Cluster Result");
    private double[] min = {450000, 450000};
    private double[] max = {800000, 800000};
    private String[] emptyArrayString = {""};
    private Plot2DPanel plot = new Plot2DPanel(min, max, emptyArrayString, emptyArrayString);
    
    public void plotScatter(ArrayList<ArrayList> dataTable) {
        Iterator tableIt = dataTable.iterator();
        while(tableIt.hasNext()) {
            ArrayList<Double> dataRow = (ArrayList<Double>) tableIt.next();
            double[][] point = {{dataRow.get(0),dataRow.get(1)}};
            plot.addPlot("SCATTER", "x", colourArray[(dataRow.get(2)).intValue()], point);
        }
    } 
    
    private void plotColour(Color colour, double[][] doubleDataArray) {
        plot.addPlot("SCATTER", "x", colour, doubleDataArray);
    }
    
    
}
