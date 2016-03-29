package numbers;

import java.math.BigInteger;
import java.math.BigDecimal;
import java.util.function.Function;

/**
 * Real class. Represents real as defined by Bishop and Bridges.
 *
 * @author Harrison Goldstein
 */
public class Real {

  private Function<BigInteger, Rational> val;

  public static Real ZERO = new Real(n -> Rational.ZERO);
  public static Real ONE = new Real(n -> Rational.ONE);
  public static Real E = exp(Real.ONE);
  public static Real PI =
    arctan(Real.ONE).multiply(new Real(Rational.create(4, 1).get()));

  /**
   * Constructor.
   *
   * @param r
   *          rational to make number with
   */
  public Real(Rational r) {
    val = n -> r;
  }

  /**
   * Constructor.
   *
   * @param f
   *          function representing rational
   */
  public Real(Function<BigInteger, Rational> f) {
    val = f;
  }

  /**
   * Approximation to within 1/n.
   *
   * @param n
   *          approximation limit
   * @return an approximation of this to within 1/n
   */
  public Rational approx(BigInteger n) {
    return val.apply(n);
  }

  /**
   * Approximation to within 1/n.
   *
   * @param n
   *          approximation limit (int)
   * @return an approximation of this to within 1/n
   */
  public Rational approx(int n) {
    return val.apply(BigInteger.valueOf(n));
  }

  /**
   * Approximation to within n decimal places.
   *
   * @param n
   *          approximation limit
   * @return an approximation of this to within 1/n, in decimal form
   */
  public BigDecimal decimalApprox(int n) {
    return val.apply(BigInteger.valueOf((int)Math.pow(10.0, (double)n)))
           .decimalValue(n + 1);
  }

  /**
   * Negation for real numbers.
   *
   * @return (-1) * this
   */
  public Real negate() {
    return new Real(n -> val.apply(n).negate());
  }

  /**
   * Addition for real numbers.
   *
   * @param other
   *          other real to add
   * @return sum
   */
  public Real add(Real other) {
    BigInteger two = new BigInteger("2");

    return new Real(n ->
                    val.apply(n.multiply(two))
                    .add(other.val.apply(n.multiply(two))));
  }

  /**
   * Negation for real numbers.
   *
   * @return 1 / this
   */
  public Real inverse() {
    Rational n = Rational.ONE;
    while(val.apply(n.ceil()).compareTo(n.inverse().get()) <= 0) {
      n = n.add(Rational.ONE);
    }

    Rational two = Rational.ONE.add(Rational.ONE);
    Rational N =
      two.divide(val.apply(n.ceil()).subtract(n.inverse().get())).get();


    BigInteger Ncubed = N.multiply(N.multiply(N)).ceil();
    BigInteger nNsquared = n.multiply(N.multiply(N)).ceil();
    return new Real(i ->
                    (i.compareTo(N.ceil()) < 0) ?
                    val.apply(Ncubed).inverse().get() :
                    val.apply(nNsquared).inverse().get());
  }

  /**
   * Multiplication for real numbers.
   *
   * @param other
   *          other real to multiply
   * @return product
   */
  public Real multiply(Real other) {
    BigInteger two = BigInteger.ONE.add(BigInteger.ONE);
    BigInteger kx = val.apply(BigInteger.ONE).abs().ceil().add(two);
    BigInteger ky = other.val.apply(BigInteger.ONE).abs().ceil().add(two);
    BigInteger twoK = kx.max(ky).multiply(two);

    return new Real(n ->
                    val.apply(n.multiply(twoK))
                    .multiply(other.val.apply(n.multiply(twoK))));
  }

  /**
   * Integer square root helper method for square root.
   *
   * @param x
   *          integer to square root
   * @return integer square root of x
   */
  private static BigInteger isqrt(BigInteger x) {
    if(x.equals(BigInteger.ZERO)) return BigInteger.ZERO;

    BigInteger z = x.divide(new BigInteger("4"));
    BigInteger r2 = (new BigInteger("2")).multiply(isqrt(z));
    BigInteger r3 = r2.add(BigInteger.ONE);
    if(x.compareTo(r3.multiply(r3)) < 0) return r2;
    else return r3;
  }

  /**
   * Square root function.
   *
   * @param r
   *          real to take the square root of
   * @return square root of r
   */
  public static Real sqrt(Real r) {
    BigInteger two = new BigInteger("2");
    return new Real(
             n -> Rational.create(
               isqrt(r.val.apply(n).normalize(n).multiply(two).multiply(n)),
               two.multiply(n))
             .get());
  }

  /**
   * Helper for cosine function.
   *
   * @param r
   *          real to take cos of
   * @param n
   *          approximation number
   * @return cos(r)_n
   */
  public static Rational cos(Real r, BigInteger n) {
    Rational xnSq = (r.val.apply(n)).multiply(r.val.apply(n));
    Rational sumAcc = Rational.ZERO;
    boolean positive = true;
    Rational xAcc = Rational.ONE;
    Rational factAcc = Rational.ONE;

    BigInteger four = new BigInteger("4");
    BigInteger ten = new BigInteger("10");
    BigInteger six = new BigInteger("6");

    for(BigInteger k = new BigInteger("2");
        k.compareTo(n) < 0;
        k = k.add(BigInteger.ONE)) {
      positive = !positive;
      xAcc = xAcc.multiply(xnSq);
      factAcc = factAcc.multiply(
                  Rational.create(
                    BigInteger.ONE,
                    k.multiply(k).multiply(four)
                    .subtract(k.multiply(ten))
                    .add(six))
                  .get());
      if(positive) sumAcc = sumAcc.add(xAcc.multiply(factAcc));
      else sumAcc = sumAcc.add(xAcc.multiply(factAcc).negate());
    }
    return sumAcc.add(Rational.ONE);
  }

  /**
   * Helper for arctan function.
   *
   * @param r
   *          real to take arctan of
   * @param n
   *          approximation number
   * @return atan(r)_n
   */
  public static Rational arctan(Real r, BigInteger n) {
    Rational xnSq = (r.val.apply(n)).multiply(r.val.apply(n));
    Rational sumAcc = Rational.ZERO;
    boolean positive = true;
    Rational xAcc = Rational.ONE;
    Rational countAcc = Rational.ONE;

    for(BigInteger k = new BigInteger("2");
        k.compareTo(n) < 0;
        k = k.add(BigInteger.ONE)) {
      positive = !positive;
      xAcc = xAcc.multiply(xnSq);
      countAcc = countAcc.add(Rational.ONE).add(Rational.ONE);
      if(positive) sumAcc = sumAcc.add(xAcc.divide(countAcc).get());
      else sumAcc = sumAcc.add(xAcc.divide(countAcc).get().negate());
    }
    return sumAcc.add(Rational.ONE);
  }

  /**
   * Arctan function.
   *
   * @param r
   *          real to find the arctan of
   * @return atan(r)
   */
  public static Real arctan(Real r) {
    return new Real(n ->
                    Rational.create(
                      arctan(r, n).normalize(n),
                      n.multiply(new BigInteger("2")))
                    .get());
  }

  /**
   * Cosine function.
   *
   * @param r
   *          real to find the cosine of
   * @return cos(r)
   */
  public static Real cos(Real r) {
    return new Real(n ->
                    Rational.create(
                      cos(r, n).normalize(n),
                      n.multiply(new BigInteger("2")))
                    .get());
  }

  /**
   * Helper for exponential function.
   *
   * @param r
   *          real to raise e to
   * @param n
   *          approximation number
   * @return e^r_n
   */
  public static Rational exp(Real r, BigInteger n) {
    Rational xn = r.val.apply(n);
    Rational sumAcc = Rational.ZERO;
    Rational xAcc = Rational.ONE;
    Rational factAcc = Rational.ONE;
    for(BigInteger k = new BigInteger("2");
        k.compareTo(n) < 0;
        k = k.add(BigInteger.ONE)) {
      xAcc = xAcc.multiply(xn);
      factAcc = factAcc.multiply(
                  Rational.create(
                    BigInteger.ONE,
                    k.subtract(BigInteger.ONE))
                  .get());
      sumAcc = sumAcc.add(xAcc.multiply(factAcc));
    }
    return sumAcc.add(Rational.ONE);
  }

  /**
   * Exponential function.
   *
   * @param r
   *          real to raise e to
   * @return e^r
   */
  public static Real exp(Real r) {
    return new Real(n ->
                    Rational.create(
                      exp(r, n).normalize(n),
                      n.multiply(new BigInteger("2")))
                    .get());
  }
}
