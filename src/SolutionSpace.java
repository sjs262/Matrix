public class SolutionSpace {
    private Vector[] array;
    private Vector position = null;
    public final int lengths;
    public boolean isNullSet = false;

    /* Constructors */
    public SolutionSpace(int lengths) {
        this.lengths = lengths;
        array = new Vector[0];
        isNullSet = true;
    }
    public SolutionSpace(Vector[] array) throws Exception {
        lengths = array[0].length;
        this.array = new Vector[array.length];
        for (int i = 0; i < array.length; i++) {
            this.array[i] = array[i].copy();
            if (array[i].length != array[0].length) throw new Exception();
        }
    }
    public SolutionSpace(Vector pos, Vector[] vecs) throws Exception {
        lengths = pos.length;
        position = pos;
        array = vecs;
        if (array.length != 0 && array[0].length != pos.length) throw new Exception();
    }
    public SolutionSpace(SolutionSpace span, Vector pos) throws Exception {
        if (span.lengths != pos.length) throw new Exception();
        lengths = pos.length;
        array = span.array;
        position = pos;
    }
    public SolutionSpace(double[] pos, double[][] vecs) throws Exception {
        if (pos.length != vecs[0].length) throw new Exception("Vector length isn't the same as fixed vector length");
        lengths = pos.length;
        position = new Vector(pos);
        array = new Vector[vecs.length];
        for (int i = 0; i < vecs.length; i++)
            array[i] = new Vector(vecs[i]);
    }

    /* Getters and Setters */
    public Vector get(int index) {
        return array[index];
    }
    public Vector getPos() {
        return position;
    }

    /* Machinery */
    /**
     * Returns a String representation of this vector space.
     *
     * @return a String representation of this vector space.
     */
    @Override
    public String toString() {

        // Construct the expression at the end
        StringBuilder expression = new StringBuilder();
        if (0 < array.length) {
            expression.append(": ∀ (");
            for (int count = 0; count < array.length; count++)
                expression.append((char) ('α' + count)).append(count != array.length - 1 ? ", " : ") ∈ ℝ" + superscript(array.length));
        }

        // Create a 2D array of strings, one for each value
        // Establish maxlengths for the individual values
        String[][] rows = new String[lengths][array.length];
        int[] maxLengths = new int[array.length];
        for (int x = 0; x < array.length; x++) {
            for (int y = 0; y < lengths; y++) {
                rows[y][x] = array[x].get(y).toString();
                if (maxLengths[x] < rows[y][x].length())
                    maxLengths[x] = rows[y][x].length();
            }
        }

        // Establish maxLength for the constant vector, if present
        String[] posRows = null;
        int posMaxLength = 0;
        if (position != null) {
            posRows = new String[lengths];
            for (int y = 0; y < lengths; y++) {
                posRows[y] = position.get(y).toString();
                if (posMaxLength < posRows[y].length())
                    posMaxLength = posRows[y].length();
            }
        }

        // For each row of the array of strings,
        StringBuilder builder = new StringBuilder();
        for (int y = 0; y < lengths; y++) {
            // Left bracket
            if (lengths == 1) builder.append("( ");
            else builder.append(y == 0 ? "⎛ " : y == lengths - 1 ? "⎝ " : "│ ");

            if (!isNullSet) {
                // Append position vector if present
                if (posRows != null && !position.isZeroVector()) {
                    builder.append("│ ");
                    builder.append(posRows[y]);
                    for (int spaces = posMaxLength - posRows[y].length(); 0 < spaces; spaces--)
                        builder.append(" ");
                    builder.append(" │");
                    // Only append the " + " if there are other vectors to add
                    if (0 < array.length)
                        builder.append(y == lengths / 2 ? " + " : "   ");
                }

                // Append the vectors
                for (int x = 0; x < array.length; x++) {
                    if (x != 0) builder.append(y == lengths / 2 ? " + " : "   ");
                    builder.append(y == lengths / 2 ? (char) ('α' + x) : " ");
                    builder.append("│ ");
                    builder.append(rows[y][x]);
                    for (int spaces = maxLengths[x] - rows[y][x].length(); 0 < spaces; spaces--)
                        builder.append(" ");
                    builder.append(" │");
                }

                // Space for the expression
                if (y == lengths / 2) builder.append(expression);
                else for (int i = 0; i < expression.length(); i++) builder.append(' ');
            }

            // Right bracket
            if (lengths == 1) builder.append(" )");
            else builder.append(y == 0 ? " ⎞" : y == lengths - 1 ? " ⎠" : " │");

            // Next line
            builder.append("\n");
        }

        return builder.toString();
    }
    private static String superscript(int integer) {
        String str = Integer.toString(integer);
        str = str.replaceAll("0", "⁰");
        str = str.replaceAll("1", "¹");
        str = str.replaceAll("2", "²");
        str = str.replaceAll("3", "³");
        str = str.replaceAll("4", "⁴");
        str = str.replaceAll("5", "⁵");
        str = str.replaceAll("6", "⁶");
        str = str.replaceAll("7", "⁷");
        str = str.replaceAll("8", "⁸");
        str = str.replaceAll("9", "⁹");
        return str;
    }
    public SolutionSpace copy() throws Exception {
        Vector[] newArr = new Vector[array.length];
        for (int v = 0; v < array.length; v++)
            newArr[v] = array[v].copy();
        return new SolutionSpace(newArr);
    }
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SolutionSpace)) return false;
        if (lengths != ((SolutionSpace) o).lengths) return false;
        for (int v = 0; v < array.length; v++)
            for (int x = 0; x < lengths; x++)
                if (array[v].get(x) != ((SolutionSpace) o).array[v].get(x)) return false;
        return true;
    }
    public boolean equivelent(SolutionSpace other) throws Exception {
        SolutionSpace copy = copy();
        SolutionSpace oCopy = other.copy();
        copy.basify();
        oCopy.basify();
        return copy.equals(oCopy);
    }

    public void basify() throws Exception {
        Matrix aug = Matrix.rref(augment().transpose());
        array = new Vector[aug.rank()];
        for (int i = 0; i < array.length; i++)
            array[i] = aug.getRow(i);
    }

    public void orthonormalize() throws Exception {
        for (int i = 0; i < array.length; i++) {
            for (int j = i - 1; 0 <= j; j--)
                array[i].subtract(Vector.proj(array[i], array[j]));
            array[i].normalize();
        }
    }

    public Matrix augment() throws Exception {
        if (isNullSet) return new Matrix(lengths, 0);
        Matrix aug = new Matrix(array[0]);
        for (int x = 1; x < array.length; x++)
            aug = aug.augment(array[x]);
        return aug;
    }

    public int dimension() throws Exception {
        SolutionSpace copy = copy();
        copy.basify();
        return copy.array.length;
    }

    public static SolutionSpace intersection(SolutionSpace space1, SolutionSpace space2) throws Exception {
        if (space1.isNullSet || space2.isNullSet) return new SolutionSpace(space1.lengths);
        Matrix a = space1.augment().augment(Matrix.multiply(-1, space2.augment())).copy();
        Vector b = Vector.subtract(space2.getPos(), space1.getPos()).copy();

        Matrix rref = a.augment(b);
        rref.rref();

        Vector newPos = space1.getPos().copy();

        for (int i = 0; i < space1.array.length; i++)
            newPos.add(Vector.multiply(space1.get(i), b.get(i)));

        Vector[] newArr = new Vector[a.width - a.rank()];

        int rank = a.rank();
        for (int i = 0; i < newArr.length; i++) {
            newArr[i] = new Vector(space1.lengths);
            for (int j = 0; j < space1.array.length; j++)
                newArr[i].subtract(Vector.multiply(space1.get(j), a.getColumn(rank + i).get(j)));
        }

        return new SolutionSpace(newPos, newArr);
    }
}
