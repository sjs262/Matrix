import java.util.Arrays;
import java.util.Set;

public class VectorSpace {
  
  public final Set<Vector> basis;
  public final int dim;
  private final Vector[] vectors;
  
  public static VectorSpace of(Vector[] basis) {
    for (Vector vector : basis)
      if (basis[0].dim != vector.dim)
        throw new IllegalArgumentException("Vectors are not compatible");
    Set<Vector> basisSet = Set.of(basis);
    return new VectorSpace(basisSet);
  }
  
  private VectorSpace(Set<Vector> basis) {
    this.basis = basis;
    vectors = basis.toArray(new Vector[basis.size()]);
    dim = vectors[0].dim;
  }
  
  @Override
  public String toString() {
    return String.join("\n", strings());
  }
  
  public String[] strings() {
    // Check if this vector space is a vector space of Fractions
    if (vectors[0].at(0) instanceof Fraction)
      return fractionToStrings();
      
    // Otherwise, consider this matrix to be a matrix of primitives or Complexes
      
    // Construct the expression at the end
    StringBuilder expression = new StringBuilder(" for all (");
    for (int count = 0; count < basis.size(); count++)
      expression.append((char) ('a' + count)).append(count != basis.size() - 1 ? ", " : ") in C^" + basis.size());
    
    // Otherwise, consider this matrix to be a matrix of primitives
    String[][] strings = Arrays.stream(vectors)
      .map(Vector::strings)
      .toArray(String[][]::new);
    
    StringBuilder[] resultStrings = new StringBuilder[dim];
    
    for (int i = 0; i < dim; i++) {
      resultStrings[i] = new StringBuilder((i == (dim - 1) / 2) ? "a" :  " ");
      for (int j = 0; j < vectors.length; j++) {
        resultStrings[i].append(strings[j][i]);
        if (j != vectors.length - 1)
          resultStrings[i].append((i == (dim - 1) / 2) ? (" + " + (char) ('b' + j)) : "    ");
        else
          resultStrings[i].append((i == (dim - 1) / 2) ? expression :  " ".repeat(expression.length()));
      }
    }
    
    return Arrays.stream(resultStrings)
      .map(e -> e.toString())
      .toArray(String[]::new);
  }
  
  private String[] fractionToStrings() {
    // Construct the expression at the end
    StringBuilder expression = new StringBuilder(" for all (");
    for (int count = 0; count < basis.size(); count++)
      expression.append((char) ('a' + count)).append(count != basis.size() - 1 ? ", " : ") in C^" + basis.size());
    
    // Otherwise, consider this matrix to be a matrix of primitives
    String[][] strings = Arrays.stream(vectors)
      .map(Vector::strings)
      .toArray(String[][]::new);
    
    StringBuilder[] resultStrings = new StringBuilder[dim * 2];
    
    for (int i = 0; i < dim * 2; i++) {
      resultStrings[i] = new StringBuilder((i == (dim * 2 - 1) / 2) ? "a" :  " ");
      for (int j = 0; j < vectors.length; j++) {
        resultStrings[i].append(strings[j][i]);
        if (j != vectors.length - 1)
          resultStrings[i].append((i == (dim * 2 - 1) / 2) ? (" + " + (char) ('b' + j)) : "    ");
        else
          resultStrings[i].append((i == (dim * 2 - 1) / 2) ? expression :  " ".repeat(expression.length()));
      }
    }
    
    return Arrays.stream(resultStrings)
      .map(e -> e.toString())
      .toArray(String[]::new);
  }
}
