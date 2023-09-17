public interface MatrixElement extends Comparable<MatrixElement> {
	public abstract MatrixElement mult(MatrixElement other);
	public abstract MatrixElement add(MatrixElement other);
	public abstract MatrixElement subt(MatrixElement other);
	public abstract MatrixElement div(MatrixElement other);
	public default boolean equals(MatrixElement other) {
		return compareTo(other) == 0;
	}
	public abstract boolean equals(int other);
	public abstract MatrixElement conj();
	public abstract String[] strings();
	public double magSq();
	public double mag();
}
