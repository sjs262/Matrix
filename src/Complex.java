public class Complex {
    private double real;
    private double imag;

    /* Constructors */
    public Complex(double real, double imag) {
        this.real = real;
        this.imag = imag;
    }

    /* Getters */
    public double real() {
        return real;
    }
    public double imag() {
        return imag;
    }

    /* Machinery */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        if (real == 0 && imag == 0) return "0";
        if (real != 0) {
            builder.append(doubleString(real));
            if (0 < imag)
                builder.append(" + ");
        }
        if (imag != 0) {
            StringBuilder imagString = new StringBuilder(doubleString(imag));
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
    public String doubleString(double a) {
        return a != Math.round(a) ? Double.toString(a) : Long.toString(Math.round(a));
    }
    
    @Override
    public boolean equals(Object o) {
        return o instanceof Complex && ((Complex) o).imag == imag && ((Complex) o).real == real;
    }
    
    public boolean equals(double other) {
        return real == other && imag == 0;
    }

    public Complex conj() {
        return new Complex(real, -imag);
    }

    /* Math */
    public Complex add(Complex other) {
        return new Complex(
            this.real + other.real(),
            this.imag + other.imag()
        );
    }
    public Complex subt(Complex other) {
        return new Complex(
            this.real - other.real(),
            this.imag - other.imag()
        );
    }
    public Complex mult(Complex other) {
        return new Complex(
            real * other.real - imag * other.imag,
            real * other.imag + imag * other.real
        );
    }
    public Complex div(Complex other) {
        Complex conj = other.conj();
        double denom = other.mult(conj).real;
        return mult(conj).divDouble(denom);
    }
    
    private Complex divDouble(double other) {
        return new Complex(real / other, imag / other);
    }
    
    public Complex magSq() {
        return new Complex(real * real + imag * imag, 0);
    }
}
