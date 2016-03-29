package examples;

import java.util.Optional;
import java.math.BigInteger;
import numbers.*;

public class Example2 {

  public static void main(String[] args) {
    // Reals can be made from rationals

    Real two = new Real(Rational.create(2, 1).get());
    Real half = new Real(Rational.create(2, 1).get().inverse().get());

    // These reals are infinite sequences of rational numbers, so they cannot
    // be viewed easily. Instead, they must be approximated

    two.approx(100);
    two.approx(new BigInteger("100"));
    // Both above approximations give 2 +/- (1/100)
    // Clearly, this is silly for 2, but it becomes useful for other reals


    // Reals can be operated on:

    two.add(two);
    two.multiply(half);

    // And there are a few special functions implemented for reals: sqrt, cos,
    // arctan, and cos

    Real.sqrt(two);

    Real.cos(Real.ONE);
  }
}
