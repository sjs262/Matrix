public class Main {
	public static void main(String[] args) {
		// System.out.println(C(1, 2));
		// System.out.println(C(1, -2));
		// System.out.println(C(1, 0));
		// System.out.println(C(0, 1));
		
		// System.out.println(F(C(1, 0), C(1, 0)));
		// System.out.println(F(C(1000, 0), C(1, 0)));
		// System.out.println(F(C(1, 0), C(100, 0)));
		
		// System.out.println(F(C(0, 1), C(0, 1)));
		// System.out.println(F(C(0, 100), C(0, 1)));
		// System.out.println(F(C(0, 1), C(0, 100)));
		
		// System.out.println(F(C(1, 2), C(1, 0)));
		// System.out.println(F(C(1, 0), C(1, 20)));
		// System.out.println(F(C(1, 20), C(10, 2)));
		
		// System.out.println(F(C(1, 20), C(10, 2)).toComplex());
		
		// System.out.println(F(C(1, 20), C(10, 2)).simplify());
		
		// System.out.println(new Matrix<Fraction>(new Fraction[][] {
		//     { F(1000, 20, 10, 200000), F(10000000, 20, 10, 2) },
		//     { F(1, 20, 10, 2), F(1, 20, 10, 2000000000) },
		// }));
		
		// System.out.println(new Matrix<Integer>(new Integer[][] {
		//     { 1, 2, 30 },
		//     { 4, 500, 6 },
		// }));
		
		// System.out.println(new Matrix<Double>(new Double[][] {
		//     { 1.0, 2.0, 30.0 },
		//     { 4.0, 500.0, 6.0 },
		// }));
		
		// System.out.println(new Matrix<Complex>(new Complex[][] {
		//     { C(1, 2), C(3, 4), C(5, 6) },
		//     { C(7, 8), C(9, 10), C(11, 12) },
		// }));
		// System.out.println(new Matrix<Fraction>(new Fraction[][] {
		//     { F(1000, 10), F(10, 10000000) },
		//     { F(1, 10), F(1, 0) },
		// }));
		// System.out.println(new Matrix<Fraction>(new Fraction[][] {
		//     { F(1000, 10).simplify(), F(10, 10000000).simplify() },
		//     { F(1, 10).simplify(), F(1, 0).simplify() },
		// }));
		
		// System.out.println(gcd(461952, 116298)); // 18
		
		// System.out.println(F().simplify());
		
		// System.out.println(M(new Fraction[][] {
		//     { F(1, 2), F(3, 4), F(5, 6) },
		//     { F(7, 8), F(9, 10), F(11, 12) },
		// }).mult(M(new Fraction[][] {
		//     { F(1, 2), F(3, 4) },
		//     { F(5, 6), F(7, 8) },
		//     { F(9, 10), F(11, 12) },
		// })));
		
		// print(M(new Fraction[][] {
		//     { F(1, 2), F(3, 4), F(5, 6) },
		//     { F(7, 8), F(9, 10), F(11, 12) },
		// }).ref());
		
		// print('\n');
		
		// print(M(new Fraction[][] {
		//     { F(1, 2), F(3, 4) },
		//     { F(5, 6), F(7, 8) },
		//     { F(9, 10), F(11, 12) },
		// }).ref());
		
		// print('\n');
		
		// print(M(new Fraction[][] {
		//     { F(1, 2), F(3, 4), F(5, 6) },
		//     { F(7, 8), F(9, 10), F(11, 12) },
		// }).rref());
		
		// print('\n');
		
		// print(M(new Fraction[][] {
		//     { F(1, 2), F(3, 4) },
		//     { F(5, 6), F(7, 8) },
		//     { F(9, 10), F(11, 12) },
		// }).rref());
		
		// print('\n');
		
		// print(Vector.col(new Complex[] {
		//     C(1, 2), C(3, 4), C(5, 6)
		// }).mult(Vector.row(new Complex[] {
		//     C(1, 2), C(3, 4), C(5, 6)
		// })));
		
		// print(Vector.row(new Complex[] {
		//     C(1, 2), C(3, 4), C(5, 6)
		// }).dot(Vector.col(new Complex[] {
		//     C(1, 2), C(3, 4), C(5, 6)
		// })));
		
		// print(F(C(11, -2), C(25, 0)).conj());
		
		// print(Vector.col(new Fraction[] {
		//     F(1, 2), F(3, 4), F(5, 6)
		// }));
		
		// print(VectorSpace.of(new Vector[] {
		//     V(new Complex[] {
		//         C(1, 2), C(3, 4), C(5, 6)
		//     }),
		//     V(new Complex[] {
		//         C(7, 8), C(9, 10), C(11, 12)
		//     }),
		// }));
		
		// print("\n");
		
		// print(VectorSpace.of(new Vector[] {
		//     V(new Complex[] {
		//         C(1, 2), C(3, 4), C(5, 6), C(7, 8)
		//     }),
		//     V(new Complex[] {
		//         C(9, 10), C(11, 12), C(13, 14), C(15, 16)
		//     }),
		// }));
		
		// print("\n");
		
		// print(VectorSpace.of(new Vector[] {
		//     V(new Fraction[] {
		//         F(C(1, 2), C(3, 4)), F(C(5, 6), C(7, 8)), F(C(9, 10), C(11, 12)), F(C(14, 15), C(16, 17))
		//     }),
		//     V(new Fraction[] {
		//         F(C(18, 19), C(20, 21)), F(C(22, 23), C(24, 25)), F(C(26, 27), C(28, 29)), F(C(30, 31), C(32, 33))
		//     }),
		//     V(new Fraction[] {
		//         F(C(34, 35), C(36, 37)), F(C(38, 39), C(40, 41)), F(C(42, 43), C(44, 45)), F(C(46, 47), C(48, 49))
		//     }),
		//     V(new Fraction[] {
		//         F(C(50, 51), C(52, 53)), F(C(54, 55), C(56, 57)), F(C(58, 59), C(60, 61)), F(C(62, 63), C(64, 65))
		//     }),
		// }));
		
		// print(Matrix.of(new int[][] {
		//     { 1, 2, 3, 4 },
		//     { 5, 6, 7, 8 },
		// }).augment(Matrix.of(new int[][] {
		//     { 9, 10 },
		//     { 11, 12 },
		// })));
		
		// print(Matrix.of(new int[][] {
		//     { 1, 2, 3, 4 },
		//     { 5, 6, 7, 8 },
		// }).augment(Matrix.of(new int[][] {
		//     { 9, 10 },
		//     { 11, 12 },
		// })).ref());
		
		// print(M(new IntegerElement[][] {
		//     {I(1), I(2), I(3), I(4), I(5)},
		//     {I(6), I(7), I(8), I(9), I(10)},
		//     {I(11), I(12), I(13), I(14), I(15)},
		// }));
		
		// print(M(new Fraction[][] {
		//     {F(1, 2), F(3, 4), F(5, 6)},
		//     {F(7, 8), F(9, 10), F(11, 12)},
		//     {F(13, 14), F(15, 16), F(17, 18)},
		// }));
		
		// print(M(new MatrixElement[][] {
		// 		{ F(1, -2), F(3, 4), F(5, 6) },
		// 		{ I(1), I(2), I(3)},
		// 		{ C(5, 6), C(7, 8), C(9, 10) },
		// }).augment(M(new MatrixElement[][] {
		// 		{F(1, 2), F(3, 4), F(5, 6)},
		// 		{F(7, 8), F(9, 10), F(11, 12)},
		// 		{F(13, 14), F(15, 16), F(17, 18)},
		// })).ref());
		
		// Matrix m = M(new MatrixElement[][] {
		// 	{ F(1, -2), F(3, 4), F(5, 6) },
		// 	{ I(1), I(2), I(3)},
		// 	{ C(5, 6), C(7, 8), C(9, 10) }
		// });
		
		// Matrix m = M(new MatrixElement[][] {
		// 	{ I(1), I(2), I(3) },
		// 	{ I(0), I(1), I(4)},
		// 	{ I(5), I(6), I(0) }
		// });
		// print(m);
		// print(m.inv());
		// print(m.mult(m.inv()));
		
		print(M(new MatrixElement[][] {
			{ F(1, -2), F(3, 4), F(5, 6) },
			{ I(1), I(2), I(3)},
			{ C(5, 6), C(7, 8), C(9, 10) },
		}).augment(M(new MatrixElement[][] {
			{F(1, 2), F(3, 4), F(5, 6)},
			{F(7, 8), F(9, 10), F(11, 12)},
			{F(13, 14), F(15, 16), F(17, 18)},
		}).augment(M(new MatrixElement[][] {
			{ C(1, 2), C(3, 4), C(5, 6) },
			{ I(1), I(2), I(3)},
			{ C(5, 6), C(7, 8), C(9, 10) },
		}))));
		
	}
	
	public static Complex C(int r, int i) {
		return new Complex(r, i);
	}
	
	public static MatrixElement F(MatrixElement numer, MatrixElement denom) {
		return Fraction.of(numer, denom);
	}
	
	public static MatrixElement F(int nr, int ni, int dr, int di) {
		return Fraction.of(new Complex(nr, ni), new Complex(dr, di));
	}
	
	public static MatrixElement F(int nr, int dr) {
		return Fraction.of(new Complex(nr, 0), new Complex(dr, 0));
	}
	
	public static Complex C(int nr) {
		return new Complex(nr, 0);
	}
	
	public static Matrix M(MatrixElement[][] matrix) {
		return Matrix.of(matrix);
	}
	
	public static Vector V(MatrixElement[] vector) {
		return Vector.col(vector);
	}
	
	public static IntegerElement I(int value) {
		return new IntegerElement(value);
	}
	
	public static void print(Object line) {
		System.out.println(line);
	}
}
