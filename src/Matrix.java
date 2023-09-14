import java.util.Arrays;

public class Matrix {
  
  /* Protected Fields */
  
  protected final MatrixElement[][] matrix;
  
  /* Immutable Public Fields */
  
  public final int m;
  public final int n;
  
  /* Reference */
  
  public final MatrixElement at(int row, int col) {
    return matrix[row][col];
  }
  public final Vector atRow(int row) {
    return Vector.row(matrix[row]);
  }
  public final Vector atCol(int col) {
    return Vector.col(
      Arrays.stream(matrix)
        .map(e -> e[col])
        .toArray(MatrixElement[]::new)
    );
  }
  
  /* Factory Methods */
  
  public static Matrix of(MatrixElement[][] matrix) {
    for (int i = 0; i < matrix.length; i++)
      if (matrix[0].length != matrix[i].length)
        throw new IllegalArgumentException("Matrix is not rectangular");
    return new Matrix(matrix);
  }
  public static Matrix of(int[][] matrix) {
    for (int i = 0; i < matrix.length; i++)
      if (matrix[0].length != matrix[i].length)
        throw new IllegalArgumentException("Matrix is not rectangular");
    return new Matrix(Arrays.stream(matrix)
      .map(row -> Arrays.stream(row)
        .mapToObj(i -> Fraction.of(new Complex(i, 0), new Complex(1, 0)))
        .toArray(MatrixElement[]::new)
      )
      .toArray(MatrixElement[][]::new)
    );
  }
  public static Matrix identity(int m) {
    MatrixElement[][] result = new MatrixElement[m][m];
    for (int i = 0; i < m; i++)
      for (int j = 0; j < m; j++)
        result[i][j] = i == j ? new IntegerElement(1) : new IntegerElement(0);
    return new Matrix(result);
  }
  
  /* Constructor */
  
  protected Matrix(MatrixElement[][] matrix) {
    this.matrix = matrix;
    m = matrix.length;
    n = matrix[0].length;
  }
  
  /* Matrix Row and Column Ops */
  
  protected void swapRows(int row1, int row2) {
    if (!(0 <= row1 && row1 < m && 0 <= row2 && row2 < m))
      throw new IllegalArgumentException("Rows are out of bounds");
    MatrixElement[] temp = matrix[row1];
    matrix[row1] = matrix[row2];
    matrix[row2] = temp;
  }
  
  /* Matrix Unary Ops */
  
  public Matrix ref() {
    // Initialize a copy of the matrix
    MatrixElement[][] result = Arrays.stream(matrix)
      .map(MatrixElement[]::clone)
      .toArray(MatrixElement[][]::new);
    
    // Initialize pivot row and column
    int pivotRow = 0;
    int pivotCol = 0;
    
    while (pivotRow < m && pivotCol < n) {
      // Find the k-th pivot
      int iMax = pivotRow;
      for (int i = pivotRow; i < m; i++)
        iMax = result[i][pivotCol].compareTo(result[iMax][pivotCol]) > 0 ? i : iMax;
      
      // If there is no pivot, skip this column
      if (result[iMax][pivotCol].equals(0)) {
        pivotCol++;
        continue;
      }
      
      // Swap the pivot row with the k-th row
      swapRows(pivotRow, iMax);
      
      for (int i = pivotRow + 1; i < m; i++) {
        // Subtract a multiple of the pivot row from rows below it
        
        MatrixElement pivotScale = result[i][pivotCol].div(result[pivotRow][pivotCol]);
        for (int j = pivotCol; j < n; j++)
          result[i][j] = result[i][j].subt(result[pivotRow][j].mult(pivotScale));
      }
      
      // Increase the pivot row and column
      pivotRow++;
      pivotCol++;
    }
    
    return new Matrix(result);
  }
  public Matrix rref() {
    // Initialize a copy of the matrix
    MatrixElement[][] result = Arrays.stream(matrix)
      .map(MatrixElement[]::clone)
      .toArray(MatrixElement[][]::new);
    
    // Initialize pivot row and column
    int pivotRow = 0;
    int pivotCol = 0;
    
    while (pivotRow < m && pivotCol < n) {
      // Find the k-th pivot
      int iMax = pivotRow;
      for (int i = pivotRow; i < m; i++)
        iMax = result[i][pivotCol].compareTo(result[iMax][pivotCol]) > 0 ? i : iMax;
      
      // If there is no pivot, skip this column
      if (result[iMax][pivotCol].equals(0)) {
        pivotCol++;
        continue;
      }
      
      // Swap the pivot row with the k-th row
      swapRows(pivotRow, iMax);
      
      // Divide the pivot row by the pivot element
      for (int j = pivotCol; j < n; j++)
        result[pivotRow][j] = result[pivotRow][j].div(result[pivotRow][pivotCol]);
      
      // Subtract a multiple of the pivot row from rows above and below it
      for (int i = 0; i < m; i++) {
        if (i == pivotRow) continue;
        
        MatrixElement pivotScale = result[i][pivotCol];
        for (int j = pivotCol; j < n; j++)
          result[i][j] = result[i][j].subt(result[pivotRow][j].mult(pivotScale));
      }
      
      // Increase the pivot row and column
      pivotRow++;
      pivotCol++;
    }
    
    return new Matrix(result);
  }
  public Matrix adj() {
    MatrixElement[][] result = new MatrixElement[n][m];
    
    for (int i = 0; i < n; i++)
      for (int j = 0; j < m; j++)
        result[i][j] = matrix[j][i].conj();
    
    return new Matrix(result);
  }
  public Matrix inv() {
    return ((Augment) augment(identity(m)).rref()).getRight();
  }
  
  /* Matrix Binary Ops */
  
  public Matrix mult(Matrix other) {
    // Check if the matrices are compatible
    if (n != other.m)
      throw new IllegalArgumentException("Matrices are not compatible");
    
    // Initialize the result matrix
    MatrixElement[][] result = new MatrixElement[m][other.n];
    
    // Multiply the matrices
    for (int i = 0; i < m; i++)
      for (int j = 0; j < other.n; j++)
        result[i][j] = atRow(i).dot(other.atCol(j));
    
    return new Matrix(result);
  }
  
  /* Vector Spaces */
  
  public VectorSpace rowSpace() {
    return VectorSpace.of(
      Arrays.stream(matrix)
        .map(Vector::row)
        .toArray(Vector[]::new)
    );
  }
  public VectorSpace columnSpace() {
    return adj().rowSpace();
  }
  public VectorSpace nullSpace() {
    return null;
  }
  
  public Augment augment(Matrix other) {
    return Augment.of(this, other);
  }
  
  public Matrix subMatrix(int top, int bottom, int left, int right) {
    MatrixElement[][] verticalSlice = Arrays.copyOfRange(matrix, top, bottom, MatrixElement[][].class);
    return new Matrix(
      Arrays.stream(verticalSlice)
        .map(row -> Arrays.copyOfRange(row, left, right, MatrixElement[].class))
        .toArray(MatrixElement[][]::new)
    );
  }
  
  /* Machinery */
  
  @Override
  public String toString() {
    return String.join("\n", strings());
  }
  
  public String[] strings() {
    String[] resultStrings = new String[m * 2];
    
    // Get each string from the elements in the matrix, and get the maximum width of each column's elements
    String[][][] strings = new String[m][][];
    int[] colWidths = new int[n];
    for (int i = 0; i < m; i++) {
      strings[i] = new String[n][];
      for (int j = 0; j < n; j++) {
        strings[i][j] = matrix[i][j].strings();
        colWidths[j] = Math.max(
          colWidths[j],
          strings[i][j][1].length()
        );
      }
    }
    
    // Append the elements to the builder
    for (int i = 0; i < m; i++) {
      StringBuilder builder1 = new StringBuilder("| ");
      
      // Append the elements's top strings to the builder, along with their respective spacings
      for (int j = 0; j < n; j++) {
        int spacing = colWidths[j] - strings[i][j][1].length();
        builder1.append(" ".repeat(spacing / 2))
          .append(strings[i][j][0])
          .append(" ".repeat(((spacing + 1) / 2) + 1));
      }
      resultStrings[2 * i] = builder1.append("|").toString();
      
      StringBuilder builder2 = new StringBuilder("| ");
      
      // Append the elements's bottom strings to the builder, along with their respective spacings
      for (int j = 0; j < n; j++) {
        int spacing = colWidths[j] - strings[i][j][1].length();
        builder2.append(" ".repeat(spacing / 2))
          .append(strings[i][j][1])
          .append(" ".repeat(((spacing + 1) / 2) + 1));
      }
      resultStrings[2 * i + 1] = builder2.append("|").toString();
    }
    
    return resultStrings;
  }
}
