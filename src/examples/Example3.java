package examples;

import numbers.*;

public class Example3 {

  static Real two = new Real(Rational.create(2, 1).get());

  public static void run(int i) {
    long time = System.currentTimeMillis();
    System.out.println(Real.exp(two).approx(i));
    System.out.println("Time Elapsed: "
                       + (System.currentTimeMillis() - time)
                       + "ms");
  }

  public static void main(String[] args) {
    run(1000);
    run(1100);
    run(1200);
    run(1300);
    run(1400);
    run(1500);
    run(1600);
  }
}
