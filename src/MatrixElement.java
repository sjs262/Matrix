public interface MatrixElement extends Comparable<MatrixElement> {
  public abstract MatrixElement mult(MatrixElement other);
  public abstract MatrixElement add(MatrixElement other);
  public abstract MatrixElement subt(MatrixElement other);
  public abstract MatrixElement div(MatrixElement other);
  public abstract boolean equals(Object o);
  public abstract boolean equals(int other);
  public abstract MatrixElement conj();
}
