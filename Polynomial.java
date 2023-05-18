public class Polynomial{
	double[] coeff;
	
	public Polynomial(){
		coeff = new double[]{0};
	}
	
	public Polynomial(double[] newcoeff){
		coeff = newcoeff.clone();
	}

	public Polynomial add(Polynomial newpoly){
		int i=Math.min(coeff.length,newpoly.coeff.length);
		double[] holder = new double[Math.max(coeff.length,newpoly.coeff.length)];
		if(coeff.length<newpoly.coeff.length){
			holder = newpoly.coeff.clone(); 
			for(int n=0; n<i; n++){
				holder[n] += coeff[n];
			}
		}
		else{
			holder = coeff.clone();
			for(int n=0; n<i; n++){
				holder[n] += newpoly.coeff[n];
			}
		}
		Polynomial a = new Polynomial(holder);
		return a;
	}

	public double evaluate(double x){
		int i=coeff.length;
		double holder=coeff[0];
		for(int n=1;n<i;n++){
			double curx = x;
			for(int e=1;e<n;e++){
				curx = curx * x;
			}
			holder += curx * coeff[n];
		}
		return holder;
	}

	public boolean hasRoot(double x){
		double holder = evaluate(x);
		return holder == 0;
	}
}