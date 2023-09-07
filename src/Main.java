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
        
        // System.out.println(new Matrix(new Fraction[][] {
        //     { F(C(1000, 20), C(10, 200000)), F(C(10000000, 20), C(10, 2)) },
        //     { F(C(1, 20), C(10, 2)), F(C(1, 20), C(10, 2000000000)) },
        // }));
        System.out.println(new Matrix(new Fraction[][] {
            { F(C(1000, 0), C(10, 200000)), F(C(10000000, 20), C(10, 2)) },
            { F(C(1, 20), C(10, 0)), F(C(1, 20), C(0, 2000000000)) },
        }));
    }
    
    public static Complex C(int r, int i) {
        return new Complex(r, i);
    }
    
    public static Complex C(int r) {
        return new Complex(r, 0);
    }
    
    public static Fraction F(Complex n, Complex d) {
        return new Fraction(n, d);
    }
}
