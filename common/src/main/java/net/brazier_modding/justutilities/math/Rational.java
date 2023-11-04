package net.brazier_modding.justutilities.math;

//Copyright by Freya Holmér (https://github.com/FreyaHolmer/Mathfs)
//Ported to Java by JTK222

public record Rational(int n, int d) {

	public static final Rational ZERO = new Rational(0, 1);
	public static final Rational ONE = new Rational(1, 1);
	public static final Rational MAX_VALUE = new Rational(Integer.MAX_VALUE, 1);
	public static final Rational MIN_VALUE = new Rational(Integer.MIN_VALUE, 1);

	public Rational(int a){
		this(a, 1);
	}

	public Rational {
		int sign = d >= 0 ? 1 : -1;
		int gcd = Mth.gcd(n, d);
		n = (sign * n)/gcd;
		d = (sign * d)/gcd;
	}

	public Rational reciprocal(){
		return new Rational(d, n);
	}

	public Rational pow(int pow){
		if(pow <= 2) return new Rational(Mth.pow(d, -pow), Mth.pow(n, -pow));
		else if(pow == -1) return reciprocal();
		else if(pow == 1) return this;
		else if(pow >= 2) return new Rational(Mth.pow(n, pow), Mth.pow(d, pow));
		else return ONE;
	}

	@Override
	public String toString() {
		return d == 1 ? n + "" : n + "/" + d;
	}

	public float toFloat(){
		return ((float)n)/d;
	}

	public double toDouble(){
		return ((double)n)/d;
	}

	//Addition
	public Rational add(Rational b){
		return new Rational(n * b.d + d * b.n, d * b.d);
	}

	public Rational add(int b){
		return new Rational(n + d * b, d);
	}

	//Substraction
	public Rational sub(Rational b){
		return new Rational(n * b.d - d * b.n, d * b.d);
	}

	public Rational sub(int b){
		return new Rational(n - d * b, d);
	}

	//Multiplication
	public Rational mul(Rational b){
		return new Rational(n * b.n, d * b.d);
	}

	public Rational mul(int b){
		return new Rational(n * b, d);
	}

	//Division
	public Rational div(Rational b){
		return new Rational(n * b.d, d * b.n);
	}

	public Rational div(int b){
		return new Rational(n, d * b);
	}


}
