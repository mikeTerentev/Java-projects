package expression;

public class UnaryMinus extends UnaryOperation {

    public UnaryMinus(TripleExpression expression){
        super(expression);
    }

    public int operation(int value){
        return -1 * value;
    }

}