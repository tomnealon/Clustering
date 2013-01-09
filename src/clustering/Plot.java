/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clustering;

import java.awt.Color;
import javax.swing.JFrame;
import org.math.plot.Plot2DPanel;

/**
 *
 * @author tom
 */
public class Plot {
  double[][] data = {
      {10,40},
      {35,10},
      {20,20}
  };
  double[] y = {20,0};
  
  public void go() {
    // create your PlotPanel (you can use it as a JPanel)
    Plot2DPanel plot = new Plot2DPanel();

    // add a line plot to the PlotPanel
    //plot.addLinePlot("my plot", x, y);
    double[] z = {25,25};
    plot.addPlot("SCATTER", "x", Color.yellow, data);

    // put the PlotPanel in a JFrame, as a JPanel
    JFrame frame = new JFrame("a plot panel");
    frame.setContentPane(plot);
    frame.setVisible(true);
  }
 

}
