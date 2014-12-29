import parsii.eval.Expression;
import parsii.eval.Parser;
import parsii.eval.Scope;
import parsii.eval.Variable;
import parsii.tokenizer.ParseException;

import static java.lang.Math.*;

public class InterpolationFunctions {
    private Grid grid_OriginalValue;
    private Grid grid_InterpolationValue;
    private String OriginalFunction;

    InterpolationFunctions(String f, Grid grid){

    }
    InterpolationFunctions(String f,Grid grid, int el){

        grid.setSubgrid(grid,el);
        double[][] subgrid = grid.getSubgrid();
        double[] temp = grid.getGrid();
        int sizeXVal = temp.length/el;
        sizeXVal*=el;
        double[] fvalue = new double[sizeXVal];
        try {
            for (int i = 0; i < sizeXVal; i++){
                fvalue[i] = initOriginalFunction(f, temp[i]);
            }
        }catch (ParseException e){
            e.printStackTrace();
            System.out.println("Диалоговое окно с ошибкой ввода");
        }

        grid_OriginalValue = new Grid(fvalue);
        grid_OriginalValue.setSubgrid(grid_OriginalValue,el);

        double h = grid.getStep();
        int mid = el/2;
        int k = -mid;
        int j = 0;
        double[] pvalue = new double[sizeXVal];

        for (int i = 0; i < sizeXVal; i++){
            if (k > mid) {
                k = -mid;
                j++;
            }
            //double q = (temp[sizeXVal/2+k]-temp[sizeXVal/2])/h;
            double q = (subgrid[j][mid+k]-subgrid[j][mid])/h;
            pvalue[i] = CreateInterpolationFunction(q,grid_OriginalValue.getSubgrid()[j]);
            if (q == 0) {
                System.out.println("совпадение с оригиналом на " + i + " итерации с значением " + pvalue[i]);
            }
            k++;
        }

        grid_InterpolationValue = new Grid(pvalue);
    }

    public double initOriginalFunction(String s, double valueX) throws ParseException{
        Scope scope = Scope.create();
        Variable x = scope.getVariable("x");
        Expression expr = Parser.parse(s, scope);
        x.setValue(valueX);
        return expr.evaluate();
    }

    public double f(double x){
        double f = (expm1(x)*1/x+sqrt(exp(x)))/exp(x);
        return f;
    }

    public double CreateInterpolationFunction(double q, double[] yVal){
        double Ps;
        int size = yVal.length;
        int o = (size/2);
        if (o < 2) {
            Ps = yVal[o]+q/2*(FiniteDifferences(-1,1,yVal)+FiniteDifferences(0,1,yVal));
        }else{
            Ps = yVal[o]+q/2*(FiniteDifferences(-1,1,yVal)+FiniteDifferences(0,1,yVal))+q*q/2*FiniteDifferences(-1,2,yVal)+q*q*(q*q-1)/(1*2*3)*(FiniteDifferences(-2,3,yVal)+FiniteDifferences(-1,3,yVal))*1/2+q*q*(q*q-1)/(1*2*3*4)*FiniteDifferences(-2,4,yVal);
        }
       // Ps = yVal[o]+q/2*(FiniteDifferences(-1,1,yVal)+FiniteDifferences(1,1,yVal))+q*q/2*FiniteDifferences(-1,2,yVal)+q*q*(q*q-1)/(1*2*3)*(FiniteDifferences(-2,3,yVal)+FiniteDifferences(-1,3,yVal))*1/2+q*q*(q*q-1)/(1*2*3*4)*FiniteDifferences(-2,4,yVal);

        return Ps;
    }

    public double FiniteDifferences(int pos, int n, double[] yVal){

        if (n == 1) {
            int o = yVal.length/2;
            double y = yVal[o+pos+1] - yVal[o+pos];
            return y;
        }else{
            double temp = FiniteDifferences(pos+1,n-1,yVal) - FiniteDifferences(pos,n-1,yVal);
            return temp;
        }
    }

    public void print(){
        System.out.println("\nОригинал");
        Grid.printGrid(grid_OriginalValue);
        System.out.println("\nИнтерполяция");
        Grid.printGrid(grid_InterpolationValue);
    }

    public Grid getG(){
        return grid_OriginalValue;
    }

    public void setFunctionX(String f){
        OriginalFunction = f;
    }

}
