import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class VectorSpace {
	
	public final Set<Vector> basis;
	public final int dim;
	private final Vector[] vectors;
	
	public static VectorSpace of(Vector[] span) {
		for (Vector vector : span) {
			if (!vector.isColumnVector)
				throw new IllegalArgumentException("Vectors must be column vectors");
			if (span[0].dim != vector.dim)
				throw new IllegalArgumentException("Vectors are not compatible");
		}
		
		Set<Vector> basisSet = new HashSet<>();
		for (Vector vector : span)
			if (vector.pNorm(1) != 0)
				basisSet.add(vector);
		
		return new VectorSpace(basisSet);
	}
	
	private VectorSpace(Set<Vector> basis) {
		this.basis = basis;
		vectors = basis.toArray(Vector[]::new);
		dim = vectors[0].dim;
	}
	
	@Override
	public String toString() {
		return String.join("\n", strings());
	}
	
	public String[] strings() {
		// Construct the expression at the end
		StringBuilder expression = new StringBuilder(" for all (");
		for (int count = 0; count < basis.size(); count++)
			expression.append((char) (((char) ('a' + count)))).append(count != basis.size() - 1 ? ", " : ") in C^" + basis.size());
		
		String[][] strings = Arrays.stream(vectors)
			.map(Vector::strings)
			.toArray(String[][]::new);
		StringBuilder[] resultStrings = new StringBuilder[2 * dim];
		
		for (int i = 0; i < 2 * dim; i++) {
			resultStrings[i] = new StringBuilder((i == (2 * dim - 1) / 2) ? "" + "a" :  " ");
			for (int j = 0; j < vectors.length; j++) {
				resultStrings[i].append(strings[j][i]);
				if (j != vectors.length - 1) {
					resultStrings[i].append((i == (2 * dim - 1) / 2) ? (" + " + ((char) ('a' + j))) : "    ");
				} else {
					resultStrings[i].append((i == (2 * dim - 1) / 2) ? expression : " ".repeat(expression.length()));
				}
			}
		}
		
		return Arrays.stream(resultStrings)
			.map(e -> e.toString())
			.toArray(String[]::new);
	}
}
