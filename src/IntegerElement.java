public class IntegerElement implements MatrixElement {
  
  /* Immutable Public Fields */
  
  public final int value;
  
  /* Constructor */
  
  public IntegerElement(int value) {
    this.value = value;
  }
  
  /* IntegerElement Unary Ops */
  
  public int magSq() {
    return value * value;
  }
  
  /* matrixElement Unary Ops */
  
  @Override
  public MatrixElement conj() {
    return new IntegerElement(value);
  }
  
  /* Binary Ops */
  
  @Override
  public MatrixElement add(MatrixElement other) {
    if (other instanceof Fraction || other instanceof Complex)
      return other.add(this);
    if (other instanceof IntegerElement) {
      IntegerElement otherInt = (IntegerElement) other;
      return new IntegerElement(value + otherInt.value);
    }
    
    assert false;
    return null;
  }
  
  @Override
  public MatrixElement subt(MatrixElement other) {
    if (other instanceof Fraction || other instanceof Complex)
      return other.mult(new IntegerElement(-1)).add(this);
    if (other instanceof IntegerElement) {
      IntegerElement otherInt = (IntegerElement) other;
      return new IntegerElement(value - otherInt.value);
    }
    
    assert false;
    return null;
  }
  
  @Override
  public MatrixElement mult(MatrixElement other) {
    if (other instanceof Fraction || other instanceof Complex)
      return other.mult(this);
    if (other instanceof IntegerElement) {
      IntegerElement otherInt = (IntegerElement) other;
      return new IntegerElement(value * otherInt.value);
    }
    
    assert false;
    return null;
  }
  
  @Override
  public MatrixElement div(MatrixElement other) {
    if (other instanceof Fraction || other instanceof Complex)
      return Fraction.of(this, other);
    if (other instanceof IntegerElement) {
      IntegerElement otherInt = (IntegerElement) other;
      return Fraction.of(this, otherInt);
    }
    
    assert false;
    return null;
  }
  
  /* Machinery */
  
  @Override
  public String toString() {
    return String.join("\n", strings());
  }
  
  @Override
  public String[] strings() {
    String str = Integer.toString(value);
    return new String[] {
      str,
      " ".repeat(str.length()),
    };
  }
  
  @Override
  public int compareTo(MatrixElement other) {
    if (other instanceof Fraction || other instanceof Complex)
      return -other.compareTo(this);
    if (other instanceof IntegerElement) {
      IntegerElement otherInt = (IntegerElement) other;
      return value - otherInt.value;
    }
    
    assert false;
    return 0;
  }
  
  @Override
  public boolean equals(int other) {
    return value == other;
  }
}
