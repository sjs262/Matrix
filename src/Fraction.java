public class Fraction implements MatrixElement {
  Complex numer;
  Complex denom;
  
  public static Fraction of(Fraction numer, Fraction denom) {
    return new Fraction((Complex) numer.numer.mult(denom.denom), (Complex) denom.numer.mult(numer.denom)).simplify();
  }
  public static Fraction of(Complex numer, Complex denom) {
    return new Fraction(numer, denom).simplify();
  }
  public static Fraction of(Fraction numer, Complex denom) {
    return new Fraction(numer.numer, (Complex) numer.denom.mult(denom)).simplify();
  }
  public static Fraction of(Complex numer, Fraction denom) {
    return new Fraction((Complex) numer.mult(denom.denom), denom.numer).simplify();
  }
  public static Fraction of(int realNumer, int imagNumer, int realDenom, int imagDenom) {
    return new Fraction(new Complex(realNumer, imagNumer), new Complex(realDenom, imagDenom)).simplify();
  }
  public static Fraction of(int realNumer, int realDenom) {
    return new Fraction(new Complex(realNumer, 0), new Complex(realDenom, 0)).simplify();
  }
  
  private Fraction(Complex numer, Complex denom) {
    this.numer = numer;
    this.denom = denom.equals(0) ? new Complex(1, 0) : denom;
  }
  
  @Override
  public String toString() {
    return String.join("\n", strings());
  }
  
  public String[] strings() {
    String numerStr = numer.toString();
    String denomStr = denom.toString();
    
    String numerSpacingLeft;
    String numerSpacingRight;
    String denomSpacingLeft;
    String denomSpacingRight;
    
    if (numer.imag() == 0 || denom.imag() == 0 || numer.real() == 0 || denom.real() == 0) {
      // Center the numerator and demoninator
      numerSpacingLeft = " ".repeat(Math.max(0, denomStr.length() - numerStr.length()) / 2);
      numerSpacingRight = " ".repeat(Math.max(0, denomStr.length() - (numerSpacingLeft + numerStr).length()));
      
      denomSpacingLeft = " ".repeat(Math.max(0, numerStr.length() - denomStr.length()) / 2);
      denomSpacingRight = " ".repeat(Math.max(0, numerStr.length() - (denomSpacingLeft + denomStr).length()));
    } else {
      int nrLen = Integer.toString(numer.real()).length();
      int niLen = Integer.toString(numer.imag()).length() + (numer.imag() != 0 ? 1 : 0);
      int drLen = Integer.toString(denom.real()).length();
      int diLen = Integer.toString(denom.imag()).length() + (denom.imag() != 0 ? 1 : 0);
      
      // Align the add/subtract signs of the numerator and denominator
      numerSpacingLeft = " ".repeat(Math.max(0, drLen - nrLen));
      numerSpacingRight = " ".repeat(Math.max(0, diLen - niLen));
      
      denomSpacingLeft = " ".repeat(Math.max(0, nrLen - drLen));
      denomSpacingRight = " ".repeat(Math.max(0, niLen - diLen));
    }
    return new String[] {
      "\u001B[4m" + numerSpacingLeft + numer + numerSpacingRight + "\u001B[0m",
                    denomSpacingLeft + denom + denomSpacingRight
    };
  }
  
  public Fraction simplify() {
    // De-complexify the denominator
    Complex newNumer = numer;
    Complex newDenom = denom;
    
    if (denom.imag() != 0) {
      Complex conj = denom.conj();
      newNumer = (Complex) numer.mult(conj);
      newDenom = new Complex(denom.magSq(), 0);
    }
    return new Fraction(newNumer, newDenom).reduce();
  }
  
  public Fraction reduce() {
    // Reduce the fraction
    Complex newNumer = numer;
    Complex newDenom = denom;
    
    if (numer.real() != 0 && denom.real() != 0) {
      int gcd = gcd(numer.real(), denom.real());
      newNumer = new Complex(numer.real() / gcd, numer.imag());
      newDenom = new Complex(denom.real() / gcd, denom.imag());
    }
    return new Fraction(newNumer, newDenom);
  }
  
  private int gcd(int real, int real2) {
    // Euclidean algorithm
    int a = Math.max(real, real2);
    int b = Math.min(real, real2);
    while (b != 0) {
      int t = b;
      b = a % b;
      a = t;
    }
    return a;
  }
  
  @Override
  public boolean equals(Object o) {
    return o instanceof Fraction && ((Fraction) o).numer.equals(numer) && ((Fraction) o).denom.equals(denom);
  }
  
  public boolean equals(int other) {
    return numer.equals(other) && denom.equals(1);
  }
  
  public Fraction magSq() {
    return Fraction.of(numer.magSq(), denom.magSq());
  }
  
  @Override
  public MatrixElement mult(MatrixElement other) {
    if (other instanceof Fraction)
      return Fraction.of((Complex) numer.mult(((Fraction) other).numer), (Complex) denom.mult(((Fraction) other).denom)).simplify();
    if (other instanceof Complex)
      return new Fraction((Complex) numer.mult(((Complex) other)), denom).simplify();
    return null;
  }

  @Override
  public MatrixElement add(MatrixElement other) {
    if (other instanceof Fraction)
      return Fraction.of((Complex) numer.mult(((Fraction) other).denom).add(((Fraction) other).numer.mult(denom)), (Complex) denom.mult(((Fraction) other).denom)).simplify();
    if (other instanceof Complex)
      return Fraction.of((Complex) numer.add(((Complex) other).mult(denom)), denom).simplify();
    return null;
  }

  @Override
  public MatrixElement subt(MatrixElement other) {
    if (other instanceof Fraction)
      return Fraction.of((Complex) numer.mult(((Fraction) other).denom).subt(((Fraction) other).numer.mult(denom)), (Complex) denom.mult(((Fraction) other).denom)).simplify();
    if (other instanceof Complex)
      return Fraction.of((Complex) numer.subt(((Complex) other).mult(denom)), denom).simplify();
    return null;
  }

  @Override
  public MatrixElement div(MatrixElement other) {
    if (other instanceof Fraction)
      return Fraction.of((Complex) numer.mult(((Fraction) other).denom), (Complex) denom.mult(((Fraction) other).numer)).simplify();
    if (other instanceof Complex)
      return Fraction.of((Complex) numer, (Complex) denom.mult((Complex) other)).simplify();
    return null;
  }
  
  @Override
  public int compareTo(MatrixElement other) {
      Fraction thisMagSq = magSq();
      double thisMagSqDouble = ((double) thisMagSq.numer.real()) / ((double) thisMagSq.denom.real());
      
      if (other instanceof Fraction) {
        Fraction otherMagSq = ((Fraction) other).magSq();
        double otherMagSqDouble = ((double) otherMagSq.numer.real()) / ((double) otherMagSq.denom.real());
        return Double.compare(thisMagSqDouble, otherMagSqDouble);
      }
      
      double otherMagSqDouble = ((Complex) other).magSq();
      return Double.compare(thisMagSqDouble, otherMagSqDouble);
  }
}

