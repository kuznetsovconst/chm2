/**
 * Created by konstantin on 28.12.14.
 */
public class Main {

    public static void main (String[] args) {

        Grid val = new Grid(1,101,50);//создаю сетку аргументов
        InterpolationFunctions test = new InterpolationFunctions(val);//считаю сетку значений
        test.print();

    }

}
