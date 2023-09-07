public class Fraction {
  Complex numer;
  Complex denom;
  
  public Fraction(Complex numer, Complex denom) {
    this.numer = numer;
    this.denom = denom;
  }
  
  public Fraction mult(Fraction other) {
    return new Fraction(numer.mult(other.numer), denom.mult(other.denom));
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
      int nrLen = doubleString(numer.real()).length();
      int niLen = doubleString(numer.imag()).length() + (numer.imag() != 0 ? 1 : 0);
      int drLen = doubleString(denom.real()).length();
      int diLen = doubleString(denom.imag()).length() + (denom.imag() != 0 ? 1 : 0);
      
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
  
  public Complex toComplex() {
    return numer.div(denom);
  }
  
  public Fraction simplify() {
    // double denom = other.mult(conj).real;
    // return mult(conj).divDouble(denom);
    Complex newNumer = numer;
    Complex newDenom = denom;
    
    if (denom.imag() != 0) {
      Complex conj = denom.conj();
      newNumer = numer.mult(conj);
      newDenom = denom.magSq();
    }
    return new Fraction(newNumer, newDenom);
  }
  
  public String doubleString(double a) {
      return a != Math.round(a) ? Double.toString(a) : Long.toString(Math.round(a));
  }
}

