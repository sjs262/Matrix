public class Matrix<T extends MatrixElement> {
  private T[][] matrix;
  
  public Matrix(T[][] matrix) {
    this.matrix = matrix;
  }
  
  public <D extends MatrixElement> Matrix<MatrixElement> mult(Matrix<D> other) {
    // Check if the matrices are compatible
    if (matrix[0].length != other.matrix.length)
      throw new IllegalArgumentException("Matrices are not compatible");
    
    // Multiply the matrices
    MatrixElement[][] result = new Fraction[matrix.length][other.matrix[0].length];
    
    for (int i = 0; i < matrix.length; i++)
      for (int j = 0; j < other.matrix[0].length; j++) {
        result[i][j] = Fraction.of(0, 1);
        for (int k = 0; k < matrix[0].length; k++)
          result[i][j] = result[i][j].add(matrix[i][k].mult(other.matrix[k][j]));
      }
      
    return new Matrix<MatrixElement>(result);
  }
  
  @Override
  public String toString() {
    // Check if T is a Fraction
    if (matrix[0][0] instanceof Fraction)
      return fractionToString();
    
    // Check if T is a Complex
    if (matrix[0][0] instanceof Complex)
      return complexToString();
    
    // Otherwise, consider T to be a primitive
    StringBuilder builder = new StringBuilder();
    
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
    for (String[] row : strings) {
      builder.append("| ");
      
      // Append the primitives to the builder, along with their respective primitive's spacings
      for (int j = 0; j < row.length; j++) {
        int spacing = colWidths[j] - row[j].length();
        builder.append(" ".repeat(spacing / 2))
          .append(row[j])
          .append(" ".repeat(((spacing + 1) / 2) + 1));
      }
      
      builder.append("|\n");
    }
    
    return builder.toString();
  }
  
  private String complexToString() {
    // Cast the matrix to a Complex matrix
    Complex[][] cMatrix = (Complex[][]) matrix;
    
    StringBuilder builder = new StringBuilder();
    
    // Get each string from the complexes in the matrix, and get the maximum width of each column's complexes
    String[][] strings = new String[cMatrix.length][];
    int[] colWidths = new int[cMatrix[0].length];
    for (int i = 0; i < cMatrix.length; i++) {
      strings[i] = new String[cMatrix[i].length];
      for (int j = 0; j < cMatrix[i].length; j++) {
        strings[i][j] = cMatrix[i][j].toString();
        colWidths[j] = Math.max(colWidths[j], strings[i][j].length());
      }
    }
    
    // Append the complexes to the builder
    for (String[] row : strings) {
      builder.append("| ");
      
      // Append the complexes to the builder, along with their respective complex's spacings
      for (int j = 0; j < row.length; j++) {
        int spacing = colWidths[j] - row[j].length();
        builder.append(" ".repeat(spacing / 2))
          .append('(' + row[j] + ')')
          .append(" ".repeat(((spacing + 1) / 2) + 1));
      }
      
      builder.append("|\n");
    }
    
    return builder.toString();
  }
  
  private String fractionToString() {
    Fraction[][] fMatrix = (Fraction[][]) matrix;
    
    StringBuilder builder = new StringBuilder();
    
    // Get each string from the fractions in the matrix, and get the maximum width of each column's fractions
    String[][][] strings = new String[fMatrix.length][][];
    int[] colWidths = new int[fMatrix[0].length];
    for (int i = 0; i < fMatrix.length; i++) {
      strings[i] = new String[fMatrix[i].length][];
      for (int j = 0; j < fMatrix[i].length; j++) {
        strings[i][j] = fMatrix[i][j].strings();
        colWidths[j] = Math.max(colWidths[j], strings[i][j][0].length());
      }
    }
    
    // Append the fractions to the builder
    for (String[][] row : strings) {
      builder.append("| ");
      
      // Append the numerators to the builder, along with their respective fraction's spacings
      for (int j = 0; j < row.length; j++) {
        int spacing = colWidths[j] - row[j][0].length();
        builder.append(" ".repeat(spacing / 2))
          .append(row[j][0])
          .append(" ".repeat(((spacing + 1) / 2) + 1));
      }
      
      builder.append("|\n| ");
      
      // Append the denominators to the builder, along with their respective fraction's spacings
      for (int j = 0; j < row.length; j++) {
        int spacing = colWidths[j] - row[j][0].length();
        builder.append(" ".repeat(spacing / 2))
          .append(row[j][1])
          .append(" ".repeat(((spacing + 1) / 2) + 1));
      }
      
      builder.append("|\n");
    }
    
    return builder.toString();
  }
}
