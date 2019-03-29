package expression.Modes;

import expression.exceptions.EvaluatingException;
import expression.exceptions.InvalidConst;
import expression.exceptions.InvalidOperation;
import expression.exceptions.Overflow;

public interface Mode<T> {
    T parseNumber(final String number) throws InvalidConst;

    T add(final T x, final T y) throws Overflow;

    T div(final T x, final T y) throws EvaluatingException;

    T mul(final T x, final T y) throws Overflow;

    T negate(final T x) throws Overflow;

    T sub(final T x, final T y) throws Overflow;

}