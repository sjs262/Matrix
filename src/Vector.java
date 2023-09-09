import java.util.Arrays;

public final class Vector extends Matrix {
    public final int dim;
    public final boolean isColumnVector;
    public final MatrixElement[] vector;
    public final MatrixElement at(int index) {
        return vector[index];
    }
    
    public static Vector col(MatrixElement[] vector) {
        return new Vector(vector, true);
    }
    public static Vector row(MatrixElement[] vector) {
        return new Vector(vector, false);
    }
    
    private Vector(MatrixElement[] vector, boolean isColumnVector) {
        super(
            isColumnVector ? 
            Arrays.stream(vector).map(e -> new MatrixElement[] {e}).toArray(MatrixElement[][]::new) :
            new MatrixElement[][] { vector }
        );
        
        this.vector = vector;
        dim = vector.length;
        this.isColumnVector = isColumnVector;
    }
    
    public Vector trans() {
        return new Vector(vector, !isColumnVector);
    }
    
    // Hermitian adjoint
    @Override
    public Vector adj() {
        return new Vector(
            Arrays.stream(vector)
                .map(e -> e.conj())
                .toArray(MatrixElement[]::new),
            !isColumnVector
        );
    }

    public MatrixElement dot(Vector other) {
        if (dim != other.dim || isColumnVector || !other.isColumnVector)
            throw new IllegalArgumentException("Vectors are not compatible");
        return super.mult(other).at(0, 0);
    }

    /* Math */
    public Vector add(Vector other) throws Exception {
        if (dim != other.dim || isColumnVector != other.isColumnVector)
            throw new IllegalArgumentException("Vectors are not compatible");
        MatrixElement[] result = new MatrixElement[dim];
        for (int i = 0; i < dim; i++)
            result[i] = vector[i].add(other.at(i));
        return new Vector(result, isColumnVector);
    }

    public Vector subt(Vector other) throws Exception {
        if (dim != other.dim || isColumnVector != other.isColumnVector)
            throw new IllegalArgumentException("Vectors are not compatible");
        MatrixElement[] result = new MatrixElement[dim];
        for (int i = 0; i < dim; i++)
            result[i] = vector[i].subt(other.at(i));
        return new Vector(result, isColumnVector);
    }

    public Vector mult(MatrixElement other) {
        MatrixElement[] result = new MatrixElement[dim];
        for (int i = 0; i < dim; i++)
            result[i] = vector[i].mult(other);
        return new Vector(result, isColumnVector);
    }

    public Vector div(MatrixElement other) {
        MatrixElement[] result = new MatrixElement[dim];
        for (int i = 0; i < dim; i++)
            result[i] = vector[i].div(other);
        return new Vector(result, isColumnVector);
    }
}
