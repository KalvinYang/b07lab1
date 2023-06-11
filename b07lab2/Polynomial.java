import java.io.File;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;//

public class Polynomial{
	double[] coeff;
	int[] exponents;
	
	public Polynomial(){
		coeff = null;
		exponents = null;
	}
	
	public Polynomial(double[] newcoeff, int[] newexp){
		coeff = newcoeff.clone();
		exponents = newexp.clone();
	}

	public Polynomial(File file) throws FileNotFoundException
	{
		Scanner myScanner = new Scanner(file);
		
		if(!myScanner.hasNextLine()) {
			this.coeff=null;
			this.exponents=null;
		}
		else {
			String line = myScanner.nextLine();
			
			line = line.replace("-","+-");
			String[] polynomAr = line.split("\\+");
			coeff = new double[polynomAr.length];
			exponents = new int[polynomAr.length];
			for(int i=0;i<polynomAr.length;i++) {
				String[] subAr = polynomAr[i].split("x");
				coeff[i]= Double.parseDouble(subAr[0]);
				if(subAr.length > 1) {
					exponents[i]= Integer.parseInt(subAr[1]);
				}
				else {
					exponents[i]=0;
				}
			}
		}
		myScanner.close();
	}
	
	public void saveToFile(String myFile) throws IOException
	{
		if(coeff==null || exponents == null || coeff.length!=exponents.length) {return;}
		
		String writeString = "";
		for(int i=0; i< coeff.length;i++) {
			
			writeString+=coeff[i];
			if(exponents[i] != 0) {
				writeString+="x" + exponents[i];
			}
			writeString+="+";
		}
		
		if(writeString.endsWith("+")) {
			writeString=writeString.substring(0,writeString.length()-1);
		}
		FileWriter out = new FileWriter(myFile+".txt");
		out.write(writeString);
		out.close();
	}
	
	public Polynomial add(Polynomial newpoly){
		int count = 0;
		for(int n=0; n<exponents.length; n++) {
			for(int m=0; m<newpoly.exponents.length; m++) {
				if(exponents[n]==newpoly.exponents[m]) {
					count++;
				}
			}
		}
		int[] newexponents = new int[exponents.length+newpoly.exponents.length-count];
		double[] newcoeff = new double[exponents.length+newpoly.exponents.length-count];
		int newindex = 0;
		int index1 = 0;
		int index2 = 0;
		while(index1<exponents.length && index2<newpoly.exponents.length) {
			if(exponents[index1]<newpoly.exponents[index2]) {
				newexponents[newindex] = exponents[index1];
				newcoeff[newindex] = coeff[index1];
				index1++;
			} 
			else if(exponents[index1]>newpoly.exponents[index2]){
				newexponents[newindex] = newpoly.exponents[index2];
				newcoeff[newindex] = newpoly.coeff[index2];
				index2++;
			} 
			else {
				newexponents[newindex] = exponents[index1];
				double sum = newpoly.coeff[index2] + coeff[index1];
				newcoeff[newindex] = sum;
				index1++;
				index2++;
			}
			newindex++;
		}
		if(newindex<(newexponents.length)) {
			if(index1<exponents.length) {
				while(index1<exponents.length) {
					newexponents[newindex] = exponents[index1];
					newcoeff[newindex] = coeff[index1];
					index1++;
					newindex++;
				}
			}
			else {
				while(index2<newpoly.exponents[index2]) {
					newexponents[newindex] = newpoly.exponents[index2];
					newcoeff[newindex] = newpoly.coeff[index2];
					index2++;
					newindex++;
				}
			}
		}
		Polynomial a = new Polynomial(newcoeff,newexponents);
		return a;
	}

	public double evaluate(double x){
		if(coeff==null || exponents == null || coeff.length!=exponents.length) {return 0;}
		int i=coeff.length;
		double holder=0;
		for(int n=0;n<i;n++){
			if(coeff[n]!=0) {
				double curx = x;
				for(int e=1;e<exponents[n];e++){
					curx = curx * x;
				}
				if(exponents[n]==0) {
					holder += coeff[n];
				}
				else {
					holder += curx * coeff[n];
				}
			}
		}
		return holder;
	}

	public boolean hasRoot(double x){
		double holder = evaluate(x);
		return holder == 0;
	}
	
	public void mergeDups() {
		for(int m=0;m<exponents.length;m++) {
			for(int n=0;n<exponents.length;n++) {
				if(n!=m && exponents[n]==exponents[m] && coeff[n]!=0 && coeff[m]!=0) {
					coeff[n]=coeff[n]+coeff[m];
					coeff[m]=0;
				}
			}
		}
		Polynomial a = this.removeRedundant();
		this.exponents = a.exponents.clone();
		this.coeff = a.coeff.clone();
	}
	
	public Polynomial removeRedundant() {
		int count=0;
		for(int i=0;i<coeff.length;i++) {
			if(coeff[i]==0) {
				count++;
			}
		}
		if(count==0) {return this;}
		
		double[] newcoeff= new double[coeff.length-count];
		int[] newexponents = new int[coeff.length-count];
		int index=0;
		for(int i=0;i<coeff.length;i++) {
			if(coeff[i]!=0) {
				newcoeff[index]=coeff[i];
				newexponents[index]=exponents[i];
				index++;
			}
		}
		Polynomial a = new Polynomial(newcoeff,newexponents);
		return a;
	}
	
	public Polynomial multiply(Polynomial newpoly) {
		int[] newexponents = new int[exponents.length*newpoly.exponents.length];
		double[] newcoeff = new double[exponents.length*newpoly.exponents.length];
		int index=0;
		for(int i=0;i<exponents.length;i++) {
			for(int n=0;n<newpoly.exponents.length;n++) {
				newexponents[index]=exponents[i]+newpoly.exponents[n];
				newcoeff[index]=coeff[i]*newpoly.coeff[n];
				index++;
			}
		}
		Polynomial a = new Polynomial(newcoeff,newexponents);
		a.mergeDups();
		return a;
	}
	
	
}