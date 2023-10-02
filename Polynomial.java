import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.Math;

public class Polynomial {
	double[] poly;
	int[] expo;
	
	public Polynomial() {
		poly = new double[]{0};
		expo = new int[]{0};
	}
	
	public Polynomial(double[] p, int[] e) {
		poly = new double[p.length];
		expo = new int[e.length];
		
		for (int i = 0; i < p.length; i++){
			poly[i] = p[i];
			expo[i] = e[i];
		}
	}
	
	public Polynomial(File f) throws IOException {
		int j = 0;
		BufferedReader b = new BufferedReader( new FileReader(f));
		String line = b.readLine();
		String[] parts;
		String[] nums;
		
		line = line.replaceAll("-", "+-");
		parts = line.split("\\+");
		
		if (parts[0].isEmpty() == true) {
			j = 1;
		}
		poly = new double[parts.length - j];
		expo = new int[parts.length - j];
		
		for (int i=j; i<parts.length; i++) {
			if (parts[i].contains("x")==false) {
				poly[i-j] = Double.parseDouble(parts[i]);
				expo[i-j] = 0;
			}
			else {
				nums = parts[i].split("x");
				poly[i-j] = Double.parseDouble(nums[0]);
				if (nums.length >= 2) {
					expo[i-j] = Integer.parseInt(nums[1]);
				}
				else {
					expo[i-j] = 1;
				}
			}
		}
		b.close();
	}
	
	public void saveToFile(String s) throws FileNotFoundException {
		String p = "";
		for (int i=0; i<poly.length; i++) {
			if (i!=0 && poly[i] >= 0) {
				p = p + "+";
			}
			p = p + poly[i];
			if (expo[i] >= 1) {
				p = p + "x";
			}
			if (expo[i] >= 2) {
				p = p + expo[i];
			}
		}
		PrintStream stream = new PrintStream(s);
		stream.println(p);
		stream.close();
	}
	
	//old add
/*	public Polynomial add(Polynomial p){
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
		sum = new double[biggest.lenth];
		for (int i = 0; i < smallest.length; i++){
			sum[i] = biggest[i] + smallest[i];
		}
		for (int i = smallest.length; i < biggest.length; i++){
			sum[i] = biggest[i];
		}
		Polynomial result = new Polynomial(sum);
		return result;
		
	} */	
	
	public Polynomial add(Polynomial p){
		int index = 0;
		int temp;
		int[] totalExpo = new int[p.expo.length + expo.length];
		int[] sumExpo;
		double[] sum;
		int[] finalExpo;
		double[] finalPoly;
		Polynomial result;
		
		for (int i: p.expo) {
			totalExpo[index] = i;
			index += 1;
		}
		for (int i: expo) {
			totalExpo[index] = i;
			index += 1;
		}
		for (int i = 1; i < totalExpo.length; i++) {
			for (int j = 0; j < totalExpo.length - i; j++) {
				if (totalExpo[j] > totalExpo[j+1]) {
					temp = totalExpo[j];
					totalExpo[j] = totalExpo[j+1];
					totalExpo[j+1] = temp;
				}
				else if (totalExpo[j] == totalExpo[j+1] && totalExpo[j] != -1) {
					totalExpo[j] = -1;
					index-=1;
				}
			}
		}
		temp = 0;
		sumExpo = new int[index];
		for (int i=0; i<index; i++) {
			if (totalExpo[i + temp] == -1) {
				temp+=1;
				i-=1;
			}
			else {
				sumExpo[i] = totalExpo[i+temp];
			}
		}
		sum = new double[sumExpo.length];
		for (int i=0; i<p.poly.length;i++) {
			for (int j=0; j<sumExpo.length;j++) {
				if (sumExpo[j] == p.expo[i]) {
					sum[j] += p.poly[i];
				}
			}	
		}
		index = 0;
		for (int i=0; i<poly.length;i++) {
			for (int j=0; j<sumExpo.length;j++) {
				if (sumExpo[j] == expo[i]) {
					sum[j] += poly[i];
					if (sum[j] == 0) {
						sumExpo[j] = -1;
						index +=1;
					}
				}
			}	
		}
		finalPoly = new double[sum.length - index];
		finalExpo = new int[finalPoly.length];
		temp = 0;
		for (int i=0; i<finalPoly.length; i++) {
			if (sumExpo[i + temp] == -1) {
				temp+=1;
				i-=1;
			}
			else {
				finalExpo[i] = sumExpo[i+temp];
				finalPoly[i] = sum[i+temp];
			}
		}
		result = new Polynomial(finalPoly, finalExpo);
		return result;
	}
	
	public Polynomial multiply(Polynomial p) {
		Polynomial product = new Polynomial();
		Polynomial[] steps = new Polynomial[poly.length];
		double[] stepPoly = new double[p.poly.length];
		int[] stepExpo = new int[p.poly.length];
		
		for (int i=0; i<poly.length;i++) {
			for (int j=0; j<p.poly.length;j++) {
				stepPoly[j] = poly[i]*p.poly[j];
				stepExpo[j] = expo[i] + p.expo[j];
			}
		steps[i] = new Polynomial(stepPoly, stepExpo);
		}
		for (int i=0;i<steps.length;i++) {
			product = product.add(steps[i]);
		}
		return product;
	}
	
	public double evaluate(double d){
		double sum = 0;
		for (int i = 0; i < poly.length; i++){
			sum += poly[i]*(Math.pow(d, expo[i]));
		}
		return sum;
	}
	
	public boolean hasRoot(double d){
		if (evaluate(d) == 0){
			return true; 
		}
		return false;
	}

	//prints the poly and expo of the polynomial
	//I only used it for testing
	public void print() {
		System.out.print("[");
		for (double i: poly) {
			System.out.print(i);
			System.out.print(",");
		}
		System.out.println("]");
		
		System.out.print("[");
		for (int i: expo) {
			System.out.print(i);
			System.out.print(",");
		}
		System.out.println("]");
	}
}
