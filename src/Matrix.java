import java.util.Arrays;
import java.util.Objects;

public class Matrix {
	
	/* Protected Fields */
	
	protected final MatrixElement[][] matrix;
	
	/* Immutable Public Fields */
	
	public final int m;
	public final int n;
	
	/* Reference mMethods */
	
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
	public final Vector[] rows() {
		return Arrays.stream(matrix)
			.map(Vector::row)
			.toArray(Vector[]::new);
	}
	public final Vector[] cols() {
		return trans().rows();
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
	
	protected void swapRows(MatrixElement[][] array, int row1, int row2) {
		if (!(0 <= row1 && row1 < m && 0 <= row2 && row2 < m))
			throw new IllegalArgumentException("Rows are out of bounds");
		MatrixElement[] temp = array[row1];
		array[row1] = array[row2];
		array[row2] = temp;
	}
	
	/* Matrix Unary Ops */
	private <V> void print2d(V[][] a) {
		for (V[] v : a) {
			System.out.println(Arrays.toString(v));
		}
	}
	
	public Matrix ref() {
		// Initialize a copy of the matrix
		MatrixElement[][] result = Arrays.stream(matrix)
			.map(row -> Arrays.copyOf(row, n, MatrixElement[].class))
			.toArray(MatrixElement[][]::new);
		
		// Initialize pivot row and column
		int pivotRow = 0;
		int pivotCol = 0;
		
		while (pivotRow < m && pivotCol < n) {
			// Find the k-th pivot
			// Swap row below it until the pivot element is nonzero
			
			for (int i = pivotRow + 1; result[pivotRow][pivotCol].equals(0) && i < m; i++)
				swapRows(result, pivotRow, i);
			
			// If there is no pivot, skip this column
			if (result[pivotRow][pivotCol].equals(0)) {
				pivotCol++;
				continue;
			}
			
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
			.map(row -> Arrays.copyOf(row, n, MatrixElement[].class))
			.toArray(MatrixElement[][]::new);
		
		// Initialize pivot row and column
		int pivotRow = 0;
		int pivotCol = 0;
		
		while (pivotRow < m && pivotCol < n) {
			// Find the k-th pivot
			// Swap row below it until the pivot element is nonzero
			
			for (int i = pivotRow + 1; result[pivotRow][pivotCol].equals(0) && i < m; i++)
				swapRows(result, pivotRow, i);
			
			// If there is no pivot, skip this column
			if (result[pivotRow][pivotCol].equals(0)) {
				pivotCol++;
				continue;
			}
			
			// Divide the pivot row by the pivot element
			for (int j = n - 1; j >= pivotCol; j--)
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
	public Matrix trans() {
		MatrixElement[][] result = new MatrixElement[n][m];
		
		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++)
				result[i][j] = matrix[j][i];
		
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
	public MatrixElement trace() {
		MatrixElement sum = new IntegerElement(0);
		for (int i = 0; i < m && i < n; i++)
			sum = sum.add(at(i, i));
		return sum;
	}
	
	/* Matrix Binary Ops */
	
	public Matrix add(Matrix other) {
		// Check if the matrices are compatible
		if (m != other.m || n != other.n)
			throw new IllegalArgumentException("Matrices are not compatible");
		
		// Initialize the result matrix
		MatrixElement[][] result = new MatrixElement[m][n];
		
		// Add the matrices
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				result[i][j] = at(i, j).add(other.at(i, j));
		
		return new Matrix(result);
	}
	public Matrix subt(Matrix other) {
		// Check if the matrices are compatible
		if (m != other.m || n != other.n)
			throw new IllegalArgumentException("Matrices are not compatible");
		
		// Initialize the result matrix
		MatrixElement[][] result = new MatrixElement[m][n];
		
		// Subtract the matrices
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				result[i][j] = at(i, j).subt(other.at(i, j));
		
		return new Matrix(result);
	}
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
	public Matrix mult(MatrixElement other) {
		// Initialize the result matrix
		MatrixElement[][] result = new MatrixElement[m][n];
		
		// Multiply the matrix by the scalar
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				result[i][j] = at(i, j).mult(other);
		
		return new Matrix(result);
	}
	
	/* Matrix Norms */
	
	public double pNorm(int p) {
		switch(p) {
			case 1:
			// Maximum absolute row sum
				return Arrays.stream(cols())
					.map(v -> v.pNorm(1))
					.max(Double::compare)
					.get();
					
			case 2:
				// Maximum singular value
				return 0;
				
			default: 
				throw new IllegalArgumentException("p must be 1 or 2");
		}
	}
	public double infNorm() {
		// Maximum absolute column sum
		return Arrays.stream(rows())
			.map(v -> v.pNorm(1))
			.max(Double::compare)
			.get();
	}
	public double frobNorm() {
		MatrixElement sum = new IntegerElement(0);
		for (int i = 0; i < m && i < n; i++)
			sum = sum.add(at(i, i).mult(at(i, i).conj()));
		
		assert !(sum instanceof Complex);
		
		if (sum instanceof IntegerElement) {
			IntegerElement traceInt = (IntegerElement) sum;
			return Math.sqrt(traceInt.value);
		}
		
		if (sum instanceof Fraction) {
			Fraction traceFrac = (Fraction) sum;
			assert traceFrac.numer.imag == 0 && traceFrac.denom.imag == 0;
			return Math.sqrt((double) traceFrac.numer.real / (double) traceFrac.denom.real);
		}
		
		assert false;
		return 0;
	}
	
	/* The 4 Subspaces */
	
	public VectorSpace rowSpace() {
		return VectorSpace.of(
			Arrays.stream(rref().matrix)
				.map(Vector::row)
				.map(Vector::trans)
				.toArray(Vector[]::new)
		);
	}
	public VectorSpace columnSpace() {
		return trans().rowSpace();
	}
	public VectorSpace nullSpace() {
		MatrixElement[][] rref = rref().matrix;
		
		MatrixElement[][] withZeros = new MatrixElement[n][n];
		int rrefRow = 0;
		int rrefCol = 0;
		int zerosRow = 0;
		while(rrefRow < m && rrefCol < n) {
			if (rref[rrefRow][rrefCol].equals(1)) {
				for (int j = 0; j < n; j++)
					withZeros[zerosRow][j] = j == rrefCol ? new IntegerElement(0) : rref[rrefRow][j].mult(new IntegerElement(-1));
				
				rrefRow++;
				rrefCol++;
				zerosRow++;
			} else {
				for (int j = 0; j < n; j++)
					withZeros[zerosRow][j] = j == rrefCol ? new IntegerElement(1) : new IntegerElement(0);
				
				rrefCol++;
				zerosRow++;
			}
		}
		return VectorSpace.of(
			Arrays.stream(new Matrix(withZeros).trans().matrix)
				.map(Vector::row)
				.map(Vector::trans)
				.toArray(Vector[]::new)
		);
	}
	public VectorSpace leftHandNullSpace() {
		return adj().nullSpace();
	}
	
	/* Decompositions */
	
	public void svd() {
		// A = UΣV*
		// A*A = V(Σ^2)(V*)
		// AA* = U(Σ^2)(U*)
		Matrix AAst = mult(adj());
		System.out.println("AA* = \n" + AAst);
		
		Matrix AstA = adj().mult(this);
		System.out.println("A*A = \n" + AstA);
	}
	
	/* Utility Methods */
	
	public Augment augment(Matrix other) {
		return Augment.of(this, other);
	}
	protected Matrix subMatrix(int top, int bottom, int left, int right) {
		MatrixElement[][] verticalSlice = Arrays.copyOfRange(matrix, top, bottom, MatrixElement[][].class);
		return new Matrix(
			Arrays.stream(verticalSlice)
				.map(row -> Arrays.copyOfRange(row, left, right, MatrixElement[].class))
				.toArray(MatrixElement[][]::new)
		);
	}
	private MatrixElement[] diagonalElements() {
		MatrixElement[] result = new MatrixElement[Math.min(m, n)];
		for (int i = 0; i < result.length; i++)
			result[i] = at(i, i);
		return result;
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
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Matrix))
			return false;
		Matrix otherMat = (Matrix) other;
		if (m != otherMat.m || n != otherMat.n)
			return false;
		
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				if (!at(i, j).equals(otherMat.at(i, j)))
					return false;
		
		return true;
	}
	@Override
	public int hashCode() {
		int result = Objects.hash(m, n);
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				result = 31 * result + matrix[i][j].hashCode();
		return result;
	}
}
