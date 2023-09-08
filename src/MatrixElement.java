public interface MatrixElement {
  public abstract MatrixElement mult(MatrixElement other);
  public abstract MatrixElement add(MatrixElement other);
  public abstract MatrixElement subt(MatrixElement other);
  public abstract MatrixElement div(MatrixElement other);
}
