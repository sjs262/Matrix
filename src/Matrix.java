public class Matrix {
  Fraction[][] matrix;
  
  public Matrix(Fraction[][] matrix) {
    this.matrix = matrix;
  }
  
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    
    // Get each string from the fractions in the matrix, and get the maximum width of each column's fractions
    String[][][] strings = new String[matrix.length][][];
    int[] colWidths = new int[matrix[0].length];
    for (int i = 0; i < matrix.length; i++) {
      strings[i] = new String[matrix[i].length][];
      for (int j = 0; j < matrix[i].length; j++) {
        strings[i][j] = matrix[i][j].strings();
        colWidths[j] = Math.max(colWidths[j], strings[i][j][0].length());
      }
    }
    
    // Append the fractions to the builder
    for (String[][] row : strings) {
      builder.append("| ");
      
      // Append the numerators to the builder, along with their respective fraction's spacings
      for (int j = 0; j < row.length; j++) {
        builder.append(row[j][0])
          .append(" ".repeat(colWidths[j] - row[j][0].length() + 1));
      }
      
      builder.append("|\n| ");
      
      // Append the denominators to the builder, along with their respective fraction's spacings
      for (int j = 0; j < row.length; j++) {
        builder.append(row[j][1])
          .append(" ".repeat(colWidths[j] - row[j][0].length() + 1));
      }
      
      builder.append("|\n");
    }
    
    return builder.toString();
  }
}
