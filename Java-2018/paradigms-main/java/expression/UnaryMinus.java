package expression;

public class UnaryMinus extends UnaryOperation {

    public UnaryMinus(CommonExpression expression){
        super(expression);
    }

    public int operation(int value){
        return -1 * value;
    }
    public double operation(double value){
        return -1 * value;
    }
}