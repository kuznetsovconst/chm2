import parsii.eval.*;
import parsii.tokenizer.ParseException;


public class Main {

    public static void main (String[] args) {

        Grid val = new Grid(1,25,600);//создаю сетку аргументов
        String f = "1/exp(x)*1/x+sqrt(exp(x))/exp(x)";
        InterpolationFunctions test = new InterpolationFunctions(f,val,5);//считаю сетку значений
        test.print();
        System.out.print("\nЗначения x\n");
        Grid.printGrid(val);


    }

}
