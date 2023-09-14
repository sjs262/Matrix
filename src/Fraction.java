public final class Fraction implements MatrixElement {
  
  /* Immutable Public Fields */
  
  public final Complex numer;
  public final Complex denom;
  
  /* Factory Methods */
  
  public static MatrixElement of(MatrixElement numer, MatrixElement denom) {
    Complex finalNumer;
    Complex finalDenom = new Complex(1, 0);
    
    if (numer instanceof Fraction) {
      Fraction numerFrac = (Fraction) numer;
      finalNumer = numerFrac.numer;
      finalDenom = numerFrac.denom;
    } else if (numer instanceof Complex)
      finalNumer = (Complex) numer;
    else if (numer instanceof IntegerElement)
      finalNumer = new Complex(((IntegerElement) numer).value, 0);
    else {
      assert false;
      return null;
    }
    
    if (denom instanceof Fraction) {
      Fraction denomFrac = (Fraction) denom;
      finalNumer = (Complex) finalNumer.mult(denomFrac.denom);
      finalDenom = (Complex) finalDenom.mult(denomFrac.numer);
    } else if (denom instanceof Complex)
      finalDenom = (Complex) finalDenom.mult(denom);
    else if (denom instanceof IntegerElement)
      finalDenom = (Complex) finalDenom.mult(denom);
    else {
      assert false;
      return null;
    }
    
    if (finalDenom.equals(0))
      throw new ArithmeticException("Division by zero");
    
    return new Fraction(finalNumer, finalDenom).simplify();
  }
  
  /* Constructor */
  
  private Fraction(Complex numer, Complex denom) {
    this.numer = numer;
    this.denom = denom;
  }
  
  /* Fraction Unary Ops */
  
  public double magSq() {
    return (double) numer.magSq() / (double) denom.magSq();
  }
  
  public MatrixElement simplify() {
    // De-complexify the denominator
    Complex newNumer = numer;
    Complex newDenom = denom;
    
    if (denom.imag != 0) {
      MatrixElement conj = denom.conj();
      newNumer = (Complex) numer.mult(conj);
      newDenom = new Complex(denom.magSq(), 0);
    }
    return new Fraction(newNumer, newDenom).reduce();
  }
  
  public MatrixElement reduce() {
    int gcd = gcd(numer.real, gcd(numer.imag, gcd(denom.real, denom.imag)));
    assert gcd != 0;
    
    // I prefer the sign to be on the numerator
    if (denom.real < 0 && numer.real > 0)
      gcd *= -1;
    
    Complex newNumer = new Complex(numer.real / gcd, numer.imag / gcd);
    Complex newDenom = new Complex(denom.real / gcd, denom.imag / gcd);
    
    if (newDenom.equals(1)) {
      if (newNumer.imag == 0)
        return new IntegerElement(newNumer.real);
      return newNumer;
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
  
  /* MatrixElement Unary Ops */
  
  @Override
  public MatrixElement conj() {
    return numer.conj().div(denom.conj());
  }
  
  /* Binary Ops */
  
  @Override
  public MatrixElement add(MatrixElement other) {
    if (other instanceof Fraction) {
      Fraction otherFrac = (Fraction) other;
      return Fraction.of(
        numer.mult(otherFrac.denom).add(denom.mult(otherFrac.numer)),
        denom.mult(otherFrac.denom)
      );
    }
    
    return Fraction.of(
      numer.add(other.mult(denom)),
      denom
    );
  }
  
  @Override
  public MatrixElement subt(MatrixElement other) {
    if (other instanceof Fraction) {
      Fraction otherFrac = (Fraction) other;
      return Fraction.of(
        numer.mult(otherFrac.denom).subt(denom.mult(otherFrac.numer)),
        denom.mult(otherFrac.denom)
      );
    }
    
    return Fraction.of(
      numer.subt(other.mult(denom)),
      denom
    );
  }
  
  @Override
  public MatrixElement mult(MatrixElement other) {
    if (other instanceof Fraction) {
      Fraction otherFrac = (Fraction) other;
      return Fraction.of(
        numer.mult(otherFrac.numer),
        denom.mult(otherFrac.denom)
      );
    }
    
    return Fraction.of(
      numer.mult(other),
      denom
    );
  }
  
  @Override
  public MatrixElement div(MatrixElement other) {
    if (other instanceof Fraction) {
      Fraction otherFrac = (Fraction) other;
      return Fraction.of(
        numer.mult(otherFrac.denom),
        denom.mult(otherFrac.numer)
      );
    }
    
    return Fraction.of(
      numer,
      denom.mult(other)
    );
  }
  
  /* Machinery */
  
  @Override
  public String toString() {
    return String.join("\n", strings());
  }
  
  @Override
  public String[] strings() {
    String numerStr = numer.strings()[0];
    String denomStr = denom.strings()[0];
    
    String numerSpacingLeft;
    String numerSpacingRight;
    String denomSpacingLeft;
    String denomSpacingRight;
    
    if (numer.imag == 0 || denom.imag == 0 || numer.real == 0 || denom.real == 0) {
      // Center the numerator and demoninator
      numerSpacingLeft = " ".repeat(Math.max(0, denomStr.length() - numerStr.length()) / 2);
      numerSpacingRight = " ".repeat(Math.max(0, denomStr.length() - (numerSpacingLeft + numerStr).length()));
      
      denomSpacingLeft = " ".repeat(Math.max(0, numerStr.length() - denomStr.length()) / 2);
      denomSpacingRight = " ".repeat(Math.max(0, numerStr.length() - (denomSpacingLeft + denomStr).length()));
    } else {
      int nrLen = Integer.toString(numer.real).length();
      int niLen = Integer.toString(numer.imag).length() + (numer.imag != 0 ? 1 : 0);
      int drLen = Integer.toString(denom.real).length();
      int diLen = Integer.toString(denom.imag).length() + (denom.imag != 0 ? 1 : 0);
      
      // Align the add/subtract signs of the numerator and denominator
      numerSpacingLeft = " ".repeat(Math.max(0, drLen - nrLen));
      numerSpacingRight = " ".repeat(Math.max(0, diLen - niLen));
      
      denomSpacingLeft = " ".repeat(Math.max(0, nrLen - drLen));
      denomSpacingRight = " ".repeat(Math.max(0, niLen - diLen));
    }
    return new String[] {
      "\u001B[4m" + numerSpacingLeft + numerStr + numerSpacingRight + "\u001B[0m",
                    denomSpacingLeft + denomStr + denomSpacingRight
    };
  }
  
  @Override
  public int compareTo(MatrixElement other) {
    if (other instanceof Fraction)
      return Double.compare(magSq(), ((Fraction) other).magSq());
    if (other instanceof Complex)
      return Double.compare(magSq(), ((Complex) other).magSq());
    if (other instanceof IntegerElement)
      return Double.compare(magSq(), ((IntegerElement) other).magSq());
    
    assert false;
    return 0;
  }
  
  public boolean equals(int other) {
    return numer.equals(other) && denom.equals(1);
  }
}