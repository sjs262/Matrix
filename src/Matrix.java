import java.util.Arrays;

public class Matrix {
  protected final MatrixElement[][] matrix;
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
  
  public Matrix(MatrixElement[][] matrix) {
    this.matrix = matrix;
  }
  
  public Matrix mult(Matrix other) {
    // Check if the matrices are compatible
    if (matrix[0].length != other.matrix.length)
      throw new IllegalArgumentException("Matrices are not compatible");
    
    // Initialize the result matrix
    MatrixElement[][] result = new Fraction[matrix.length][other.matrix[0].length];
    
    // Multiply the matrices
    for (int i = 0; i < matrix.length; i++)
      for (int j = 0; j < other.matrix[0].length; j++) {
        result[i][j] = Fraction.of(0, 1);
        for (int k = 0; k < matrix[0].length; k++)
          result[i][j] = result[i][j].add(matrix[i][k].mult(other.matrix[k][j]));
      }
    
    return new Matrix(result);
  }
  
  public Matrix ref() {
    // Initialize a copy of the matrix
    MatrixElement[][] result = Arrays.stream(matrix).map(MatrixElement[]::clone).toArray(MatrixElement[][]::new);
    
    // Initialize pivot row and column
    int pivotRow = 0;
    int pivotCol = 0;
    
    while (pivotRow < matrix.length && pivotCol < matrix[0].length) {
      // Find the k-th pivot
      int iMax = pivotRow;
      for (int i = pivotRow; i < matrix.length; i++)
        iMax = result[i][pivotCol].compareTo(result[iMax][pivotCol]) > 0 ? i : iMax;
      
      // If there is no pivot, skip this column
      if (result[iMax][pivotCol].equals(0)) {
        pivotCol++;
        continue;
      }
      
      // Swap the pivot row with the k-th row
      swapRows(pivotRow, iMax);
      
      for (int i = pivotRow + 1; i < result.length; i++) {
        // Subtract a multiple of the pivot row from rows below it
        
        MatrixElement pivotScale = result[i][pivotCol].div(result[pivotRow][pivotCol]);
        result[i][pivotCol] = Fraction.of(0, 1);
        for (int j = pivotCol + 1; j < matrix[i].length; j++)
          result[i][j] = result[i][j].subt(result[pivotRow][j].mult(pivotScale));
      }
      
      // Increase the pivot row and column
      pivotRow++;
      pivotCol++;
    }
    
    return new Matrix(result);
  }
  
  private void swapRows(int row1, int row2) {
    MatrixElement[] temp = matrix[row1];
    matrix[row1] = matrix[row2];
    matrix[row2] = temp;
  }

  // Hermitian adjoint
  public Matrix adj() {
    MatrixElement[][] result = new MatrixElement[matrix[0].length][matrix.length];
    
    for (int i = 0; i < matrix[0].length; i++)
      for (int j = 0; j < matrix.length; j++)
        result[i][j] = matrix[j][i].conj();
    
    return new Matrix(result);
  }
  
  public VectorSpace rowSpace() {
    return new VectorSpace(
      Arrays.stream(matrix)
        .map(Vector::row)
        .toArray(Vector[]::new)
    );
  }
  public VectorSpace columnSpace() {
    return adj().rowSpace();
  }
  
  @Override
  public String toString() {
    return String.join("\n", strings());
  }
  
  public String[] strings() {
    // Check if this matrix is a matrix of Fractions
    if (matrix[0][0] instanceof Fraction)
      return fractionToStrings();
    
    // Check if this matrix is a matrix of Complexes
    if (matrix[0][0] instanceof Complex)
      return complexToStrings();
    
    // Otherwise, consider this matrix to be a matrix of primitives
    String[] resultStrings = new String[matrix.length];
    
    // Get each string from the primitives in the matrix, and get the maximum width of each column's primitives
    String[][] strings = new String[matrix.length][];
    int[] colWidths = new int[matrix[0].length];
    for (int i = 0; i < matrix.length; i++) {
      strings[i] = new String[matrix[i].length];
      for (int j = 0; j < matrix[i].length; j++) {
        strings[i][j] = matrix[i][j].toString();
        colWidths[j] = Math.max(colWidths[j], strings[i][j].length());
      }
    }
    
    // Append the primitives to the builder
    for (int i = 0; i < strings.length; i++) {
      StringBuilder builder = new StringBuilder("| ");
      
      // Append the primitives to the builder, along with their respective primitive's spacings
      for (int j = 0; j < strings[i].length; j++) {
        int spacing = colWidths[j] - strings[i][j].length();
        builder.append(" ".repeat(spacing / 2))
          .append(strings[i][j])
          .append(" ".repeat(((spacing + 1) / 2) + 1));
      }
      
      resultStrings[i] = builder.toString();
    }
    
    return resultStrings;
  }
  
  private String[] complexToStrings() {
    String[] resultStrings = new String[matrix.length];
    
    // Get each string from the complexes in the matrix, and get the maximum width of each column's complexes
    String[][] strings = new String[matrix.length][];
    int[] colWidths = new int[matrix[0].length];
    for (int i = 0; i < matrix.length; i++) {
      strings[i] = new String[matrix[i].length];
      for (int j = 0; j < matrix[i].length; j++) {
        strings[i][j] = ((Complex) matrix[i][j]).toString();
        colWidths[j] = Math.max(colWidths[j], strings[i][j].length());
      }
    }
    
    // Append the complexes to the builder
    for (int i = 0; i < strings.length; i++) {
      StringBuilder builder = new StringBuilder("| ");
      
      // Append the complexes to the builder, along with their respective complex's spacings
      for (int j = 0; j < strings[i].length; j++) {
        int spacing = colWidths[j] - strings[i][j].length();
        builder.append(" ".repeat(spacing / 2))
          .append('(' + strings[i][j] + ')')
          .append(" ".repeat(((spacing + 1) / 2) + 1));
      }
      
      resultStrings[i] = builder.append("|").toString();
    }
    
    return resultStrings;
  }
  
  private String[] fractionToStrings() {
    String[] resultStrings = new String[matrix.length * 2];
    
    // Get each string from the fractions in the matrix, and get the maximum width of each column's fractions
    String[][][] strings = new String[matrix.length][][];
    int[] colWidths = new int[matrix[0].length];
    for (int i = 0; i < matrix.length; i++) {
      strings[i] = new String[matrix[i].length][];
      for (int j = 0; j < matrix[i].length; j++) {
        strings[i][j] = ((Fraction) matrix[i][j]).strings();
        colWidths[j] = Math.max(colWidths[j], strings[i][j][0].length());
      }
    }
    
    // Append the fractions to the builder
    for (int i = 0; i < strings.length; i++) {
      StringBuilder builder1 = new StringBuilder("| ");
      
      // Append the numerators to the builder, along with their respective fraction's spacings
      for (int j = 0; j < strings[i].length; j++) {
        int spacing = colWidths[j] - strings[i][j][0].length();
        builder1.append(" ".repeat(spacing / 2))
          .append(strings[i][j][0])
          .append(" ".repeat(((spacing + 1) / 2) + 1));
      }
      resultStrings[2 * i] = builder1.append("|").toString();
      
      builder1.append("|").toString();
      
      StringBuilder builder2 = new StringBuilder("| ");
      // Append the denominators to the builder, along with their respective fraction's spacings
      for (int j = 0; j < strings[i].length; j++) {
        int spacing = colWidths[j] - strings[i][j][0].length();
        builder2.append(" ".repeat(spacing / 2))
          .append(strings[i][j][1])
          .append(" ".repeat(((spacing + 1) / 2) + 1));
      }
      resultStrings[2 * i + 1] = builder2.append("|").toString();
    }
    
    return resultStrings;
  }
}
