import java.lang.Math;

public class Polynomial {
	double[] poly;
	
	public Polynomial() {
		poly = new double[]{0};
	}
	
	public Polynomial(double[] p) {
		poly = new double[p.length];
		for (int i = 0; i < p.length; i++){
			poly[i] = p[i];
		}
	}
	
	public Polynomial add(Polynomial p){
		double[] sum;
		double[] biggest;
		double[] smallest;
		
		if (p.poly.length > poly.length){
			biggest = p.poly;
			smallest = poly;
		}
		else {
			biggest = poly;
			smallest = p.poly;
		}
		sum = new double[biggest.length];
		for (int i = 0; i < smallest.length; i++){
			sum[i] = biggest[i] + smallest[i];
		}
		for (int i = smallest.length; i < biggest.length; i++){
			sum[i] = biggest[i];
		}
		Polynomial result = new Polynomial(sum);
		return result;
		
	}
	
	public double evaluate(double d){
		double sum = 0;
		for (int i = 0; i < poly.length; i++){
			sum += poly[i]*(Math.pow(d, i));
		}
		return sum;
	}
	
	public boolean hasRoot(double d){
		if (evaluate(d) == 0){
			return true; 
		}
		return false;
	}
}