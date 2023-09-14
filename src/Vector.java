import java.util.Arrays;

public final class Vector extends Matrix {
	public final int dim;
	public final boolean isColumnVector;
	public final MatrixElement[] vector;
	public MatrixElement at(int index) {
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
		
		MatrixElement sum = new IntegerElement(0);
		for (int i = 0; i < dim; i++)
			sum = sum.add(vector[i].mult(other.at(i)));
		return sum;
	}
	
	/* Math */
	
	public Vector add(Vector other) {
		if (dim != other.dim || isColumnVector != other.isColumnVector)
			throw new IllegalArgumentException("Vectors are not compatible");
		MatrixElement[] result = new MatrixElement[dim];
		for (int i = 0; i < dim; i++)
			result[i] = vector[i].add(other.at(i));
		return new Vector(result, isColumnVector);
	}
	
	public Vector subt(Vector other) {
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
	
	/* Norms */
	
	public double pNorm(int p) {
		if (p <= 0)
			throw new IllegalArgumentException("p must be positive");
		double sum = 0;
		for (MatrixElement element: vector)
			sum += Math.pow(element.mag(), p);
		return Math.pow(sum, 1.0 / p);
	}
	
	public double infNorm() {
		double max = 0;
		for (MatrixElement element : vector)
			max = Math.max(max, element.mag());
		return max;
	}
	
	@Override
	public int hashCode() {
		return super.hashCode() * 31 + Boolean.hashCode(isColumnVector);
	}
}
