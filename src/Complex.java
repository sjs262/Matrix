public class Complex implements MatrixElement {
    private int real;
    private int imag;

    /* Constructors */
    public Complex(int real, int imag) {
        this.real = real;
        this.imag = imag;
    }

    /* Getters */
    public int real() {
        return real;
    }
    public int imag() {
        return imag;
    }

    /* Setters */
    protected void real(int real) {
        this.real = real;
    }
    protected void imag(int imag) {
        this.imag = imag;
    }
    
    /* Machinery */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        if (real == 0 && imag == 0) return "0";
        if (real != 0) {
            builder.append(Integer.toString(real));
            if (0 < imag)
                builder.append(" + ");
        }
        if (imag != 0) {
            StringBuilder imagString = new StringBuilder(Integer.toString(imag));
            if (imag < 0) {
                builder.append(" ");
                imagString.insert(1, " ");
            }
            if (imag != 1)
                builder.append(imagString);
            builder.append("i");
        }

        return builder.toString();
    }
    
    @Override
    public boolean equals(Object o) {
        return o instanceof Complex && ((Complex) o).imag == imag && ((Complex) o).real == real;
    }
    
    public boolean equals(int other) {
        return real == other && imag == 0;
    }

    public Complex conj() {
        return new Complex(real, -imag);
    }

    /* Math */
    public int magSq() {
        return real * real + imag * imag;
    }

    @Override
    public MatrixElement add(MatrixElement other) {
        if (other instanceof Complex)
            return new Complex(real + ((Complex) other).real, imag + ((Complex) other).imag);
        if (other instanceof Fraction)
            return other.add(this);
        return null;
    }

    @Override
    public MatrixElement subt(MatrixElement other) {
        if (other instanceof Complex)
            return new Complex(real - ((Complex) other).real, imag - ((Complex) other).imag());
        if (other instanceof Fraction)
            return Fraction.of((Complex) ((Fraction) other).numer.subt(this.mult(((Fraction) other).denom)), ((Fraction) other).denom).simplify();
        return null;
    }

    @Override
    public MatrixElement div(MatrixElement other) {
        if (other instanceof Complex)
            return Fraction.of(this, (Complex) other).simplify();
        if (other instanceof Fraction)
            return Fraction.of((Complex) mult(((Fraction) other).denom), ((Fraction) other).numer).simplify();
        return null;
    }
    
    @Override
    public MatrixElement mult(MatrixElement other) {
        if (other instanceof Complex)
            return new Complex(real * ((Complex) other).real - imag * ((Complex) other).imag, real * ((Complex) other).imag + imag * ((Complex) other).real);
        if (other instanceof Fraction)
            return Fraction.of((Complex) mult(((Fraction) other).numer), ((Fraction) other).denom).simplify();
        return null;
    }

    @Override
    public int compareTo(MatrixElement other) {
        double thisMagSqDouble = magSq();
        
        if (other instanceof Fraction) {
            Fraction otherMagSq = ((Fraction) other).magSq();
            double otherMagSqDouble = ((double) otherMagSq.numer.real()) / ((double) otherMagSq.denom.real());
            return Double.compare(thisMagSqDouble, otherMagSqDouble);
        }
        
        double otherMagSqDouble = ((Complex) other).magSq();
        return Double.compare(thisMagSqDouble, otherMagSqDouble);
    }
}
