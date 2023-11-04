package net.brazier_modding.justutilities.math;

public class Mth {

	public static int max(int a, int b){
		return a > b ? a : b;
	}

	public static int min(int a, int b){
		return a < b ? a : b;
	}

	public static int abs(int a){
		return Math.abs(a);
	}

	public static int pow(int a, int pow){
		int res = 1;
		for(int i = 0; i < pow; i++)
			res *= a;
		return res;
	}

	//Greatest common divisor
	public static int gcd(int a, int b){
		if(a == Integer.MIN_VALUE || b == Integer.MIN_VALUE){
			if(a == Integer.MIN_VALUE && b == Integer.MIN_VALUE )
				return Integer.MIN_VALUE; // the only negative return value, bc we can't negate this number
			int v = abs(max(a, b));
			return v & -v;
		}
		if(a == b)
			return abs(a);
		a = abs(a);
		b = abs(b);
		while(a != 0 && b != 0)
			if(a > b) a %= b;
			else b %= a;
		return a | b;
	}

	public static int clampRollover(int min, int max, int number){
		int distance = (max-min) + 1;
		while(number < min) number += distance;
		while(number > max) number -= distance;
		return number;
	}
}
