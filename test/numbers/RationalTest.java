package numbers;

import static org.junit.Assert.*;
import org.junit.Test;

import java.math.BigInteger;

public class RationalTest {

  @Test
  public void testCreate() {
    assertTrue(Rational.create(1, 1).isPresent());
    assertFalse(Rational.create(1, 0).isPresent());
    assertTrue(
      Rational.create(new BigInteger("1"), new BigInteger("1")).isPresent());
    assertFalse(
      Rational.create(new BigInteger("1"), new BigInteger("0")).isPresent());
  }

  @Test
  public void testComparisons() {
    Rational two = Rational.create(2, 1).get();
    Rational four = Rational.create(4, 1).get();
    Rational half = Rational.create(1, 2).get();

    assertEquals(Rational.ZERO, Rational.ZERO);
    assertEquals(Rational.ONE, Rational.ONE);
    assertEquals(two, two);
    assertEquals(half, half);

    assertTrue(two.compareTo(two) == 0);
    assertTrue(two.compareTo(four) < 0);
    assertTrue(four.compareTo(two) > 0);
    assertTrue(two.compareTo(half) > 0);
    assertTrue(half.compareTo(two) < 0);
  }

  @Test
  public void testLowestTerms() {
    Rational four = Rational.create(16, 4).get();
    Rational one = Rational.create(444, 444).get();

    assertEquals(four, four.lowestTerms());
    assertEquals(one, one.lowestTerms());
  }

  @Test
  public void testNegate() {
    Rational negOne = Rational.create(-1, 1).get();

    assertEquals(negOne, Rational.ONE.negate());
    assertEquals(Rational.ONE, Rational.ONE.negate().negate());
    // This one is just for fun
    assertEquals(negOne, Rational.ONE.negate().negate().negate());
  }

  @Test
  public void testAdd() {
    Rational two = Rational.create(2, 1).get();

    assertEquals(two, Rational.ONE.add(Rational.ONE));
    assertEquals(Rational.ZERO, Rational.ONE.add(Rational.ONE.negate()));
    assertEquals(two, two.add(Rational.ZERO));
  }

  @Test
  public void testSubtract() {
    Rational two = Rational.create(2, 1).get();

    assertEquals(Rational.ONE, two.subtract(Rational.ONE));
    assertEquals(Rational.ONE.negate(), Rational.ONE.subtract(two));
    assertEquals(Rational.ZERO, Rational.ONE.subtract(Rational.ONE));
    assertEquals(two, two.subtract(Rational.ZERO));
  }

  @Test
  public void testInverse() {
    Rational two = Rational.create(2, 1).get();
    Rational half = Rational.create(1, 2).get();

    assertEquals(Rational.ONE, Rational.ONE.inverse().get());
    assertEquals(two, two.inverse().get().inverse().get());
    assertEquals(two, half.inverse().get());
    assertEquals(half, two.inverse().get());

    assertFalse(Rational.ZERO.inverse().isPresent());
  }

  @Test
  public void testMultiply() {
    Rational two = Rational.create(2, 1).get();
    Rational four = Rational.create(4, 1).get();
    Rational half = Rational.create(1, 2).get();

    assertEquals(Rational.ONE, Rational.ONE.multiply(Rational.ONE));
    assertEquals(two, two.multiply(Rational.ONE));
    assertEquals(four, two.multiply(two));
    assertEquals(Rational.ONE, two.multiply(half));
  }

  @Test
  public void testDivide() {
    Rational two = Rational.create(2, 1).get();
    Rational four = Rational.create(4, 1).get();
    Rational half = Rational.create(1, 2).get();

    assertEquals(Rational.ONE, Rational.ONE.divide(Rational.ONE).get());
    assertEquals(two, two.divide(Rational.ONE).get());
    assertEquals(two, four.divide(two).get());
    assertEquals(Rational.ONE, two.divide(two).get());

    assertFalse(Rational.ONE.divide(Rational.ZERO).isPresent());
  }
}
