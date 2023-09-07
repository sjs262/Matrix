import java.util.Iterator;

public class Vector implements Iterable<Complex> {
    private final Complex[] array;
    public final int length;

    /* Constructors */
    public Vector(int length) {
        this.length = length;
        array = new Complex[length];
        for (int i = 0; i < array.length; i++)
            array[i] = new Complex(0, 0);
    }

    public Vector(Complex[] complexes) {
        length = complexes.length;
        this.array = complexes;
    }

    public Vector(double[] reals) {
        length = reals.length;
        array = new Complex[reals.length];
        for (int i = 0; i < array.length; i++)
            array[i] = new Complex(reals[i], 0);
    }

    /* Getters and Setters */
    public Complex get(int index) {
        return array[index];
    }
    public void set(int index, Complex value) {
        array[index] = value;
    }
    public void set(int index, double value) {
        array[index].setReal(value);
        array[index].setImag(0);
    }
    public void set(Vector vector) throws Exception {
        if (vector.array.length != array.length) throw new Exception();
        System.arraycopy(vector.array, 0, array, 0, array.length);
    }

    /* Machinery */
    @Override
    public String toString() {
        int maxLength = 0;
        StringBuilder[] valueArr = new StringBuilder[array.length];
        for (int i = 0; i < array.length; i++) {
            valueArr[i] = new StringBuilder(array[i].toString());
            if (maxLength < valueArr[i].length())
                maxLength = valueArr[i].length();
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            builder.append("│ ");
            builder.append(valueArr[i]);
            for (int spaces = maxLength - valueArr[i].length(); 0 < spaces; spaces--)
                builder.append(" ");
            builder.append(" │\n");
        }
        return builder.toString();
    }
    public Vector copy() {
        Vector copy = new Vector(length);
        for (int i = 0; i < length; i++)
            copy.set(i, array[i].copy());
        return copy;
    }
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Vector) || ((Vector) o).length != length) return false;
        for (int i = 0; i < length; i++)
            if (!array[i].equals(((Vector) o).array[i])) return false;
        return true;
    }

    public Vector transpose() {
        Vector copy = copy();
        for (Complex c : copy.array)
            c.setImag(-c.imag());
        return copy;
    }

    //proj of a in the direction of b
    public static Vector proj(Vector a, Vector b) throws Exception {
        for (Complex c : b.array) if (c.real() == 0 && c.imag() == 0) throw new Exception();
        return multiply(b, Complex.divide(dot(a, b), dot(b, b)));
    }
    public static Vector proj(Vector a, SolutionSpace span) throws Exception {
        Matrix mat = span.augment();
        Matrix tmat = mat.transpose();
        Matrix.multiply(mat, Matrix.multiply(Matrix.multiply(tmat, mat).inverse(), tmat));
        return null;
    }

    public void normalize() {
        Complex length = new Complex(0, 0);
        for (Complex complex : array)
            length.add(Complex.multiply(complex, complex));
        length.pow(0.5);
    }

    public boolean isZeroVector() {
        for (Complex c : array)
            if (!c.equals(0)) return false;
        return true;
    }

    public static Complex dot(Vector left, Vector right) throws Exception {
        if (left.length != right.length) throw new Exception();
        Complex output = new Complex(0, 0);
        for (int index = 0; index < left.length; index++)
            output.add(Complex.multiply(left.get(index), right.get(index).conj()));
        return output;
    }
    public static Vector cross(Vector left, Vector right) throws Exception {
        if (left.length != right.length) throw new Exception();
        return null;
    }

    /* Math */
    public static Vector add(Vector left, Vector right) throws Exception {
        if (left.length != right.length) throw new Exception();
        Vector output = new Vector(left.length);
        for (int i = 0; i < left.length; i++)
            output.set(i, Complex.add(left.get(i), right.get(i)));
        return output;
    }

    public void add(Vector vec) throws Exception {
        if (length != vec.length) throw new Exception();
        for (int i = 0; i < length; i++)
            array[i].add(vec.get(i));
    }

    public static Vector subtract(Vector left, Vector right) throws Exception {
        if (left.length != right.length) throw new Exception();
        Vector output = new Vector(left.length);
        for (int i = 0; i < left.length; i++)
            output.set(i, Complex.subtract(left.get(i), right.get(i)));
        return output;
    }
    public void subtract(Vector vec) throws Exception {
        if (length != vec.length) throw new Exception();
        for (int i = 0; i < length; i++)
            array[i].subtract(vec.get(i));
    }

    public static Vector multiply(Vector vec, Complex c) {
        Vector output = new Vector(vec.length);
        for (int i = 0; i < vec.length; i++)
            output.set(i, Complex.multiply(vec.get(i), c));
        return output;
    }
    public void multiply(Complex other) {
        for (int i = 0; i < length; i++)
            array[i].multiply(other);
    }
    public void multiply(double other) {
        for (int i = 0; i < length; i++)
            array[i].multiply(other);}

    public static Vector divide(Vector vec, Complex c) {
        Vector output = new Vector(vec.length);
        for (int i = 0; i < vec.length; i++)
            output.set(i, Complex.divide(vec.get(i), c));
        return output;
    }

    public void divide(Complex c) {
        for (int i = 0; i < length; i++)
            array[i].divide(c);
    }

    @Override
    public Iterator<Complex> iterator() {
        return null;
    }
}
