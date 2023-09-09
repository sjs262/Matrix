import java.util.Set;

public class VectorSpace {
  
  public final Set<Vector> basis;
  
  public VectorSpace(Vector[] basis) {
    this.basis = Set.of(basis);
  }
  
}
