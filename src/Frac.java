class Frac {
    int z;
    int n;

    public static boolean isFrac(String s) { // Проверка корректности дроби
        String[] f = s.split("\\|");
        if (f.length!=2) return false;
        boolean b1=f[0].trim().matches("[-]?\\d+");
        boolean b2=f[1].trim().matches("[-]?\\d+");
        return b1&&b2;
    }

    public Frac() {}

    public Frac(String s) throws ArithmeticException { // Конструктор который принимает строку и иницилиализирует значения Z- над дробью, n-под дробью
        String[] f = s.split("\\|");
        if (f.length!=2) {
            z=Integer.valueOf(f[0]);
            n=1;
        } else {
            z=Integer.valueOf(f[0]);
            n=Integer.valueOf(f[1]);
            if (n==0) throw new ArithmeticException();
            reduce();
        }
    }

    public Frac(int z, int n) throws ArithmeticException { // Конструктор который принимает число над и под дробью
        if (n==0) throw new ArithmeticException();
        this.z=z;
        this.n=n;
        reduce();
    }

    public Frac(int z) throws ArithmeticException { // Конструктор который принимает целое число
        if (n==0) throw new ArithmeticException();
        this.z=z;
        this.n=1;
    }

    public String toString() { // функция вывода объекта
        if (n==1) return String.valueOf(z);
        return z+"|"+n;

    }

    public Frac add(Frac f) {
        Frac res = new Frac();
        res.z=this.z*f.n+f.z*this.n;
        res.n=this.n*f.n;
        res.reduce();
        return res;
    }

    public Frac add(int num) {
        return add(new Frac(num,1));
    }

    public Frac mult(Frac f) {
        Frac res = new Frac();
        res.z=this.z*f.z;
        res.n=this.n*f.n;
        res.reduce();
        return res;
    }

    public Frac mult(int num) {
        return mult(new Frac(num,1));
    }

    public Frac sub(Frac f) {
        return add(f.mult(-1));
    }

    public Frac sub(int num) {
        return sub(new Frac(num,1));
    }

    public Frac div(Frac f) {
        return mult(f.inverse());
    }

    public Frac div(int num) {
        return div(new Frac(num,1));
    }

    public Frac inverse() {
        return new Frac(n,z);
    }

    public void reduce() {
        for (int i = n; i >= 2; i--) {
            if (z%i==0 && n%i==0) {
                z=z/i;
                n=n/i;
            }
        }
        if (z<0 && n<0 || n<0) {
            z=z*(-1);
            n=n*(-1);
        }
    }
}
