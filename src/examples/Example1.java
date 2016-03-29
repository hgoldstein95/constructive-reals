package examples;

import java.util.Optional;
import java.math.BigInteger;
import numbers.*;

public class Example1 {

  public static void main(String[] args) {
    // Rationals can be made in two ways:

    // From ints...
    Rational oneFourth = Rational.create(1, 4).get();

    // ... or from BigIntegers
    Rational twoThirds =
      Rational.create(new BigInteger("2"), new BigInteger("3")).get();

    // Keep in mind that in both cases, Rational.create returns
    // Optional<Rational>s, so the get() method is needed to unwrap it

    Optional<Rational> badIdea = Rational.create(1, 0); // Will be EMPTY!

    // Rationals are immutable, and can have lots of methods for doing math

    oneFourth.add(oneFourth); // 1/2

    twoThirds.multiply(oneFourth); // 1/6

    oneFourth.negate(); // -1/4

    oneFourth.inverse(); // 4/1. NOTE: this is an Optional<Rational>

    // The operations can be chained
    oneFourth.add(oneFourth)
    .add(oneFourth)
    .add(oneFourth); // 1/1

    // You can also print out rationals
    System.out.println(oneFourth); // "(1 / 4)" printed
  }
}
