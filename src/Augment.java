public class Augment extends Matrix {
  private final Matrix left;
  private final Matrix right;
  private final int split;
  
  public Matrix getLeft() {
    return left;
  }
  
  public Matrix getRight() {
    return right;
  }
  
  public static Augment of(Matrix left, Matrix right) {
    if (left.m != right.m)
      throw new IllegalArgumentException("Matrices must have the same number of rows");
    MatrixElement[][] matrix = new MatrixElement[left.m][left.n + right.n];
    for (int i = 0; i < left.m; i++) {
      for (int j = 0; j < left.n; j++)
        matrix[i][j] = left.at(i, j);
      for (int j = 0; j < right.n; j++)
        matrix[i][left.n + j] = right.at(i, j);
    }
    return new Augment(left, right, matrix);
  }
  
  private Augment(Matrix left, Matrix right, MatrixElement[][] matrix) {
    super(matrix);
    this.left = left;
    this.right = right;
    split = left.n;
  }
  
  @Override
  public Matrix mult(Matrix other) {
    if (other instanceof Augment)
      throw new IllegalArgumentException("Cannot multiply two augmented matrices");
    return Augment.of(left.mult(other), right.mult(other));
  }
  
  @Override
  public Matrix ref() {
    Matrix result = super.ref();
    Matrix left = result.subMatrix(0, m, 0, split);
    Matrix right = result.subMatrix(0, m, split, n);
    return Augment.of(left, right);
  }
  
  @Override
  public Matrix rref() {
    Matrix result = super.rref();
    Matrix left = result.subMatrix(0, m, 0, split);
    Matrix right = result.subMatrix(0, m, split, n);
    return Augment.of(left, right);
  }
  
  @Override
  public String[] strings() {
    String[] leftStrings = left.strings();
    String[] rightStrings = right.strings();
    
    String[] result = new String[leftStrings.length];
    for (int i = 0; i < leftStrings.length; i++)
      result[i] = leftStrings[i] + rightStrings[i].substring(1);
    
    return result;
  }
}
