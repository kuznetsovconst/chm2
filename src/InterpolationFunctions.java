import net.sourceforge.jeval.*;
import java.lang.Math.*;

import static java.lang.Math.*;

public class InterpolationFunctions {
    private Grid grid_OriginalValue;
    private Grid grid_InterpolationValue;
    private String OriginalFunction;

    InterpolationFunctions(String f, Grid grid){
        Evaluator evaluator = new Evaluator();

        try {
            evaluator.evaluate(f);
        }catch (EvaluationException e){
            e.printStackTrace();
        }

    }
    InterpolationFunctions(Grid grid){
        double[] temp = grid.getGrid();
        double[] fvalue = new double[temp.length];
        for (int i = 0; i < temp.length; i++){
            fvalue[i] = f(temp[i]);
        }
        grid_OriginalValue = new Grid(fvalue);
    }

    public double f(double x){
        double f = expm1(x)*1/x+3*sqrt(exp(x));
        return f;
    }

    public void print(){
        Grid.printGrid(grid_OriginalValue);
        System.out.println("\nКонец");
    }

    public void setFunctionX(String f){
        OriginalFunction = f;
    }

}
