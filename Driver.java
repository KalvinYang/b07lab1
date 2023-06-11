import java.util.Arrays;
import java.io.IOException;
import java.io.File;

public class Driver {
	public static void main(String [] args) throws IOException
	{
		//Empty
		Polynomial p = new Polynomial();
		System.out.println(p.evaluate(3));
		
		//Add
		double [] c1 = {10,1,-1,5};
		int [] e1 = {0,1,2,5};
		Polynomial p1 = new Polynomial(c1,e1);
		double [] c2 = {4,3,-2};
		int [] e2 = {1,2,4};
		Polynomial p2 = new Polynomial(c2,e2);
		Polynomial s = p1.add(p2);
		System.out.println("coeff: "+Arrays.toString(s.coeff)+", expo: "+Arrays.toString(s.exponents));
		
		//Evaluate
		System.out.println("s(0.1) = " + s.evaluate(0.1));
		System.out.println("s(0.1) = Expected: 10.51985");
		
		//Has Root
		if(s.hasRoot(-1))
			System.out.println("-1 is a root of s");
		else
			System.out.println("-1 is not a root of s");
		
		//Multiply
		Polynomial m = p1.multiply(p2);
		System.out.println("coeff: "+Arrays.toString(m.coeff)+", expo: "+Arrays.toString(m.exponents));
		
		//Save to file
		m.saveToFile("trial1");
		
		//Load from file
		File insert = new File("insert.txt");
		Polynomial insertvar = new Polynomial(insert);
		System.out.println("coeff: "+Arrays.toString(insertvar.coeff)+", expo: "+Arrays.toString(insertvar.exponents));
	}
}