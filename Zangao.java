public class Zangao extends Abelha {

	private boolean acasalou = false;
	
	public Zangao(Rainha r, Deposito d, Mutex c) {
		super("Zang�o Z-" + Thread.activeCount(), r, d, c);
		start();
	}
	
	private void tentarAcasalar()  {
		acasalou = rainha.acasalar();
	}	
	
	public void run() {
		while (idade <= 40 && !acasalou) { // Zang�es vivem 40 dias ou at� acasalar 

			// Zang�es comem como qualquer abelha normal
			comer();
			
			// Zang�es tentam acasalar diariamente a partir de 10 dias de vida
			if (idade >= 10)
				tentarAcasalar();
			
			// Zang�es morrem se ficarem 30 dias sem poder comer
			if (diasSemComer==30)
				break;

			if (!dormir())
				return;
			
		}
		System.err.println(String.format("%s morreu ap�s %s dias. Acasalou %s vez ao longo da vida.", getName(), idade, (acasalou ? 1 : 0)));
	}
	
}
