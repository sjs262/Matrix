public final class Complex implements MatrixElement {
	
	/* Immutable Public Fields */
	
	public final int real;
	public final int imag;
	
	/* Constructor */
	
	public Complex(int real, int imag) {
			this.real = real;
			this.imag = imag;
	}
	
	/* MatrixElement Unary Ops */
	
	@Override
	public double magSq() {
			return real * real + imag * imag;
	}
	@Override
	public double mag() {
			return Math.sqrt(magSq());
	}
	@Override
	public MatrixElement conj() {
			return new Complex(real, -imag);
	}
	
	/* MatrixElement Binary Ops */
	
	@Override
	public MatrixElement add(MatrixElement other) {
		if (other instanceof Fraction)
			return other.add(this);
		
		if (other instanceof Complex) {
			Complex otherComp = (Complex) other;
			return new Complex(real + otherComp.real, imag + otherComp.imag);
		}
		if (other instanceof IntegerElement) {
			IntegerElement otherInt = (IntegerElement) other;
			return new Complex(real + otherInt.value, imag);
		}
		
		assert false;
		return null;
	}
	@Override
	public MatrixElement subt(MatrixElement other) {
			if (other instanceof Fraction)
					return other.mult(new IntegerElement(-1)).add(this);
			
			if (other instanceof Complex) {
					Complex otherComp = (Complex) other;
					return new Complex(real - otherComp.real, imag - otherComp.imag);
			}
			if (other instanceof IntegerElement) {
					IntegerElement otherInt = (IntegerElement) other;
					return new Complex(real - otherInt.value, imag);
			}
			
			assert false;
			return null;
	}
	@Override
	public MatrixElement mult(MatrixElement other) {
			if (other instanceof Fraction) 
					return other.mult(this);
			
			if (other instanceof Complex) {
					Complex otherComp = (Complex) other;
					return new Complex(real * otherComp.real - imag * otherComp.imag, real * otherComp.imag + imag * otherComp.real);
			}
			if (other instanceof IntegerElement) {
					IntegerElement otherInt = (IntegerElement) other;
					return new Complex(real * otherInt.value, imag * otherInt.value);
			}
			
			assert false;
			return null;
	}
	@Override
	public MatrixElement div(MatrixElement other) {
			return Fraction.of(this, other);
	}
	
	/* Machinery */
	
	@Override
	public String toString() {
		return strings()[0];
	}
	@Override
	public String[] strings() {
		StringBuilder builder = new StringBuilder();
		
		if (real == 0 && imag == 0)
			builder.append("0");
		
		if (real != 0) {
			builder.append(real);
			if (0 < imag)
				builder.append("+");
		}
		
		if (imag != 0) {
			if (imag != 1)
				builder.append(imag);
			builder.append("i");
		}
		
		String str = builder.toString();
		return new String[] {
			str,
			" ".repeat(str.length()),
		};
	}
	@Override
	public int compareTo(MatrixElement other) {
		if (other instanceof Fraction)
			return -other.compareTo(this);
		if (other instanceof Complex)
			return Double.compare(magSq(), other.magSq());
		if (other instanceof IntegerElement)
			return Double.compare(magSq(), other.magSq());
		
		assert false;
		return 0;
	}
	@Override
	public boolean equals(Object other) {
		if (other instanceof Complex) {
			Complex otherComp = (Complex) other;
			return real == otherComp.real && imag == otherComp.imag;
		}
		return false;
	}
	@Override
	public boolean equals(int other) {
		return real == other && imag == 0;
	}
	@Override
	public int hashCode() {
		int result = Integer.hashCode(real);
		result = 31 * result + Integer.hashCode(imag);
		return result;
	}
}
