package numbers;

import java.math.BigInteger;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * Rational class. Represents rational numbers as a pair of
 * {@code BigInteger}s.
 *
 * @author Harrison Goldstein
 */
public class Rational implements Comparable {

  private BigInteger num;
  private BigInteger den;

  public static final Rational ZERO = create(0, 1).get();
  public static final Rational ONE = create(1, 1).get();

  /**
   * Constructor.
   *
   * @param n
   *          numerator of the rational number
   * @param d
   *          denominator of the rational number
   */
  private Rational(BigInteger n, BigInteger d) {
    if(BigInteger.ZERO.compareTo(d) > 0) {
      n = n.negate();
      d = d.negate();
    }
    num = n;
    den = d;
  }

  /**
   * Static constructor.
   *
   * @param n
   *          numerator of the rational number
   * @param d
   *          denominator of the rational number
   * @return either a rational number, or `empty` if d is zero
   */
  public static Optional<Rational> create(BigInteger n, BigInteger d) {
    if(BigInteger.ZERO.equals(d)) return Optional.empty();
    return Optional.of(new Rational(n, d));
  }

  /**
   * Static constructor.
   *
   * @param n
   *          numerator of the rational number
   * @param d
   *          denominator of the rational number
   * @return either a rational number, or `empty` if d is zero
   */
  public static Optional<Rational> create(int n, int d) {
    if(d == 0) return Optional.empty();
    return Optional.of(
             new Rational(BigInteger.valueOf(n), BigInteger.valueOf(d)));
  }

  /**
   * Function for reducing a rational to lowest terms.
   *
   * @return rational in lowest terms
   */
  public Rational lowestTerms() {
    BigInteger gcd = num.gcd(den);
    return new Rational(num.divide(gcd), den.divide(gcd));
  }

  /**
   * Ceiling function.
   *
   * @return the smallest integer strictly greater than this
   */
  public BigInteger ceil() {
    if(num.mod(den).equals(BigInteger.ZERO)) return num.divide(den);
    return num.divide(den).add(BigInteger.ONE);
  }

  /**
   * Absolute value function.
   *
   * @return the absolute value of this
   */
  public Rational abs() {
    return new Rational(num.abs(), den.abs());
  }

  /**
   * Function for negating a rational
   *
   * @return (-1) * rational
   */
  public Rational negate() {
    return new Rational(num.negate(), den);
  }

  /**
   * Add function for rational numbers.
   *
   * @param other
   *          the rational to add to this rational
   * @return sum
   */
  public Rational add(Rational other) {
    BigInteger newNum = (num.multiply(other.den)).add(other.num.multiply(den));
    BigInteger newDen = den.multiply(other.den);
    return (new Rational(newNum, newDen)).lowestTerms();
  }

  /**
   * Subtract function for rational numbers.
   *
   * @param other
   *          the rational to subtract from this rational
   * @return difference
   */
  public Rational subtract(Rational other) {
    BigInteger newNum =
      (num.multiply(other.den)).subtract(other.num.multiply(den));
    BigInteger newDen = den.multiply(other.den);
    return (new Rational(newNum, newDen)).lowestTerms();
  }

  /**
   * Function for inverting a rational
   *
   * @return 1 / rational
   */
  public Optional<Rational> inverse() {
    return Rational.create(den, num);
  }

  /**
   * Multiply function for rational numbers.
   *
   * @param other
   *          the rational to multiply with this rational
   * @return product
   */
  public Rational multiply(Rational other) {
    BigInteger newNum = num.multiply(other.num);
    BigInteger newDen = den.multiply(other.den);
    return (new Rational(newNum, newDen)).lowestTerms();
  }

  /**
   * Divide function for rational numbers.
   *
   * @param other
   *          the rational to divide this rational by
   * @return quotient
   */
  public Optional<Rational> divide(Rational other) {
    return other.inverse().map(dividend -> this.multiply(dividend));
  }

  /**
   * Normalizes this integer to be some a / 2 * n.
   *
   * @param n
   *          value to normalize to
   * @return a such that (this = a / 2 * n)
   */
  public BigInteger normalize(BigInteger n) {
    return num.multiply(new BigInteger("2")).multiply(n).divide(den);
  }

  /**
   * Conversion to decimal value.
   *
   * @param scale
   *          the number of desired decimal places
   * @return decimal value of this rational
   */
  public BigDecimal decimalValue(int scale) {
    return (new BigDecimal(num)).divide(
             new BigDecimal(den), scale, BigDecimal.ROUND_HALF_UP);
  }

  @Override
  public String toString() {
    return "(" + num.toString() + " / " + den.toString() + ")";
  }

  @Override
  public int compareTo(Object o) {
    if(!(o instanceof Rational)) throw new IllegalArgumentException();
    Rational other = (Rational)o;
    return (num.multiply(other.den)).compareTo(other.num.multiply(den));
  }

  @Override
  public boolean equals(Object o) {
    if(!(o instanceof Rational)) return false;
    Rational other = (Rational)o;
    return (num.multiply(other.den)).equals(other.num.multiply(den));
  }

  @Override
  public int hashCode() {
    return num.add(den).intValue();
  }
}
