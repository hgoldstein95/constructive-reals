package numbers;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Ignore;

import java.math.BigInteger;

public class RealTest {

  public void checkInvariant(Real r, int tolerance) {
    Rational bigN;
    Rational bigM;
    Rational leftSide;
    for(int n = 1; n < tolerance; n++) {
      for(int m = 1; m < n; m++) {
        bigN = Rational.create(1, n).get();
        bigM = Rational.create(1, m).get();
        leftSide = r.approx(n).subtract(r.approx(m)).abs();
        assertTrue(leftSide.compareTo(bigN.add(bigM)) <= 0);
      }
    }
  }

  public void checkInvariant(Real r, int start, int tolerance) {
    Rational bigN;
    Rational bigM;
    Rational leftSide;
    for(int n = start; n < tolerance; n++) {
      for(int m = start; m < n; m++) {
        bigN = Rational.create(1, n).get();
        bigM = Rational.create(1, m).get();
        leftSide = r.approx(n).subtract(r.approx(m)).abs();
        assertTrue(leftSide.compareTo(bigN.add(bigM)) <= 0);
      }
    }
  }

  @Test
  public void testCreate() {
    Real one = new Real(Rational.ONE);

    assertEquals(Rational.ONE, one.approx(1));
    checkInvariant(one, 100);
  }

  @Test
  public void testNegate() {
    Real one = new Real(Rational.ONE);
    Real negOne = new Real(Rational.ONE.negate());

    checkInvariant(one.negate(), 100);

    assertEquals(negOne.approx(1), one.negate().approx(1));
    assertEquals(one.negate().negate().approx(1), one.approx(1));
  }

  @Test
  public void testAdd() {
    Real two = new Real(Rational.create(2, 1).get());

    checkInvariant(Real.ONE.add(Real.ONE), 100);

    assertEquals(two.approx(1),
                 Real.ONE.add(Real.ONE).approx(1));
    assertEquals(Real.ZERO.approx(1),
                 Real.ONE.add(Real.ONE.negate()).approx(1));
    assertEquals(two.approx(1),
                 two.add(Real.ZERO).approx(1));
  }

  @Test
  public void testInverse() {
    Real two = new Real(Rational.create(2, 1).get());
    Real half = new Real(Rational.create(1, 2).get());


    checkInvariant(two.inverse(), 100);
    checkInvariant(half.inverse(), 100);

    assertEquals(Real.ONE.approx(1), Real.ONE.inverse().approx(1));
    assertEquals(two.approx(1), two.inverse().inverse().approx(1));
    assertEquals(two.approx(1), half.inverse().approx(1));
  }

  @Test
  public void testMultiply() {
    Real two = new Real(Rational.create(2, 1).get());
    Real half = new Real(Rational.create(1, 2).get());

    checkInvariant(two.multiply(Real.ONE), 100);
    checkInvariant(two.multiply(half), 100);

    assertEquals(Real.ONE.approx(1), Real.ONE.multiply(Real.ONE).approx(1));
    assertEquals(Real.ONE.approx(1), two.multiply(half).approx(1));
  }

  @Test
  public void testSqrt() {
    Real two = new Real(Rational.create(2, 1).get());

    checkInvariant(Real.sqrt(Real.ONE), 100);
    checkInvariant(Real.sqrt(two), 100);
    checkInvariant(Real.sqrt(Real.sqrt(two)), 100);
  }

  @Test
  public void testCos() {
    Real two = new Real(Rational.create(2, 1).get());

    checkInvariant(Real.cos(Real.ONE), 10, 50);
    checkInvariant(Real.cos(two), 10, 50);
  }

  @Test
  public void testArctan() {
    Real half = new Real(Rational.create(1, 2).get());

    checkInvariant(Real.arctan(Real.ONE), 10, 50);
    checkInvariant(Real.arctan(half), 10, 50);
  }

  @Test
  public void testExp() {
    Real two = new Real(Rational.create(2, 1).get());

    checkInvariant(Real.exp(Real.ONE), 10, 50);
    checkInvariant(Real.exp(two), 10, 50);
  }
}
