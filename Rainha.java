import java.util.ArrayList;
import java.util.List;

public class Rainha extends Abelha {
	
	private boolean gravida = false;
	private int crias = 0;
	private List<Operaria> operarias = new ArrayList<Operaria>();
	private List<Zangao> zangoes = new ArrayList<Zangao>();
	
	public Rainha(Deposito d, Mutex c) {
		super("Abelha-rainha", null, d, c);
	}
	
	public boolean acasalar() {
		if (!gravida && idade <= 1000) {
			gravida = true;
			System.err.println("Abelha-rainha acasalou e está grávida.");
			return true;
		} else
			return false;		
	}
	
	private void reproduzir() {
		if (gravida) {
			for (int i=1; i<=20; i++)
				operarias.add(new Operaria(this, deposito, mutex));
			for (int i=1; i<=5; i++)
				zangoes.add(new Zangao(this, deposito, mutex));
			crias++;
			gravida = false;
			System.err.println("Abelha-rainha gerou crias.");
		}
	}
	
	public void run() {
		while (idade<=1825) { // Abelha-rainha vive 5 anos (1825 dias)
			
			// Abelha-rainha consome 10 unidades de geléia real por dia
			if (consumirGeleiaReal(10)) 
				diasSemComer = 0;
			else
				diasSemComer++;
			
			// Abelha-rainha põe ovos em dias de dia múltiplos de 5
			if (idade % 5 == 0)
				reproduzir();
			
			// Abelha-rainha morre se ficar 100 dias sem poder comer
			if (diasSemComer==100)
				break;
			
			if (!dormir())
				return;
			
		}
		System.err.println(String.format("Abelha-rainha morreu após %s dias. Gerou %s crias ao longo da vida.", idade, crias));
	}

}
