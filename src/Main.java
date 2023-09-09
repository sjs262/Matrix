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
        
        System.out.println(M(new Fraction[][] {
            { F(1, 2), F(3, 4), F(5, 6) },
            { F(7, 8), F(9, 10), F(11, 12) },
        }).mult(M(new Fraction[][] {
            { F(1, 2), F(3, 4) },
            { F(5, 6), F(7, 8) },
            { F(9, 10), F(11, 12) },
        })));
        
        System.out.println(M(new Fraction[][] {
            { F(1, 2), F(3, 4), F(5, 6) },
            { F(7, 8), F(9, 10), F(11, 12) },
        }).ref());
        
        System.out.println(M(new Fraction[][] {
            { F(1, 2), F(3, 4) },
            { F(5, 6), F(7, 8) },
            { F(9, 10), F(11, 12) },
        }).ref());
    }
    
    public static Complex C(int r, int i) {
        return new Complex(r, i);
    }
    
    public static Complex C(int r) {
        return new Complex(r, 0);
    }
    
    public static Fraction F(int nr, int ni, int dr, int di) {
        return Fraction.of(nr, ni, dr, di);
    }
    
    public static Fraction F(int nr, int dr) {
        return Fraction.of(nr, dr);
    }
    
    public static Matrix M(Fraction[][] matrix) {
        return new Matrix(matrix);
    }
    
    public static void print(String line) {
        System.out.println(line);
    }
}
