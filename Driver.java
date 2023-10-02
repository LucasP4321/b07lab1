import java.io.File;
import java.io.IOException;

public class Driver {
	public static void main(String [] args) throws IOException {
		Polynomial p = new Polynomial();
		System.out.println(p.evaluate(3));
		double [] c1 = {6,5};
		int [] d1 = {0,3};
		Polynomial p1 = new Polynomial(c1, d1);
		double [] c2 = {-2,-9};
		int [] d2 = {1,4};
		Polynomial p2 = new Polynomial(c2, d2);
		Polynomial s = p1.add(p2);
		s.print();
		System.out.println("s(0.1) = " + s.evaluate(0.1));
		if(s.hasRoot(1))
			System.out.println("1 is a root of s");
		else
			System.out.println("1 is not a root of s");
		
		System.out.println("----------------");
		double [] c3 = {-5, 1, 3};
		int [] d3 = {0,1, 4};
		Polynomial p3 = new Polynomial(c3, d3);
		double [] c4 = {1,4,2,1};
		int [] d4 = {0,3,4,5};
		Polynomial p4 = new Polynomial(c4, d4);
		Polynomial s2 = p3.multiply(p4);
		s2.print();
		File f = new File("C:\\Users\\pietr\\Downloads\\polyStuff.txt");
		Polynomial s3 = new Polynomial(f);
		s3.print();
		s3.saveToFile("C:\\Users\\pietr\\Downloads\\polySave.txt");
	}
}