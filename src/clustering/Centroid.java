package clustering;

/**
 *
 * @author tom
 */
public class Centroid {
    private double x = 0.0;
    private double y = 0.0;

    public Centroid(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
    
    public void setX(double x) {
        this.x = x;
    }
    
    public void setY(double y) {
        this.y = y;
    }
}
