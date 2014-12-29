/**
 * Created by konstantin on 28.12.14.
 */
public class Grid {
    private static final double ZERO = 0;
    private double[] grid;
    private double[][] subgrid;
    private double h;

    Grid(double x0, double xn, int iter){
        setGrid(x0,xn,iter);
    }

    Grid(double[] y){
        setGrid(y);
    }

    // далее   iter - есть количество шагов
    public void setGrid(double x0, double xn, int iter){
        if (x0 > xn | iter == ZERO) {
            System.out.println("Error: Begin and End of grid are equals");
        }else {
            h = (xn - x0) / iter;
            double temp = x0 - h;
            grid = new double[iter];
            for (int i = 0; i < iter; i++){
                grid[i] = temp + h;
                temp+=h;
            }
        }
    }

    public void setSubgrid(Grid a ,int el){
        int s = (a.getGrid().length/el);
        int k=0;
        subgrid = new double[s][el];
        for (int i = 0; i < s; i++){
            for (int j = 0; j < el; j++){
                subgrid[i][j] = (a.getGrid())[k];
                k++;
            }
        }
    }

    public double[][] getSubgrid(){
        return subgrid;
    }


    public void setGrid(double[] y){
        grid = new double[y.length];
        for (int i = 0; i < y.length; i++) {
            grid[i] = y[i];
        }
    }

    public static void printGrid(Grid a){
        double[] t = a.getGrid();
        for (int i = 0; i < t.length; i++){
            System.out.print(t[i] + " ");
        }
    }

    public double getStep(){
        return h;
    }

    public double[] getGrid(){
        return grid;
    }
}
