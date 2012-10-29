public class Zangao extends Abelha {

	private boolean acasalou = false;
	
	public Zangao(Rainha r, Deposito d, Mutex c) {
		super("Zangão Z-" + Thread.activeCount(), r, d, c);
		start();
	}
	
	private void tentarAcasalar()  {
		acasalou = rainha.acasalar();
	}	
	
	public void run() {
		while (idade <= 40 && !acasalou) { // Zangões vivem 40 dias ou até acasalar 

			// Zangões comem como qualquer abelha normal
			comer();
			
			// Zangões tentam acasalar diariamente a partir de 10 dias de vida
			if (idade >= 10)
				tentarAcasalar();
			
			// Zangões morrem se ficarem 30 dias sem poder comer
			if (diasSemComer==30)
				break;

			if (!dormir())
				return;
			
		}
		System.err.println(String.format("%s morreu após %s dias. Acasalou %s vez ao longo da vida.", getName(), idade, (acasalou ? 1 : 0)));
	}
	
}
