public class Operaria extends Abelha {

	public Operaria(Rainha r, Deposito d, Mutex c) {
		super("Oper�ria O-" + Thread.activeCount(), r, d, c);
		start();
	}
	
	private void produzirNectar() {
		mutex.entrarNaRegiaoCritica();
		deposito.nectar++;
		System.out.println(String.format("%s produziu n�ctar. (estoque atual: %s)", getName(), deposito.nectar));
		mutex.sairDaRegiaoCritica();
	}
	
	private void produzirGeleiaReal() {
		if (idade < 10) { // Abelhas oper�rias s� produzem gel�ia real nos seus primeiros 10 dias de vida
			mutex.entrarNaRegiaoCritica();
			deposito.geleiaReal++;
			System.out.println(String.format("%s produziu gel�ia real. (estoque atual: %s)", getName(), deposito.geleiaReal));
			mutex.sairDaRegiaoCritica();
		}
	}
	
	public void run() {
		while (idade <= 40) { // Abelhas oper�rias vivem 40 dias

			// Oper�rias comem como qualquer abelha normal
			comer();

			// Oper�rias produzem 3 unidades de gel�ia real por dia somente at� o 5 ano de vida
			if (idade < 5) {
				for (int i=1; i<=3; i++)
					produzirGeleiaReal();
			}
			
			// Oper�rias produzem 10 unidades de n�ctar por dia a partir de 5 dias de vida
			if (idade >= 5) {
				for (int i=1; i<=10; i++) {
					for (int z=1; z<=10000; z++); // Tempo gasto para buscar o p�len
					produzirNectar();
				}
			}
			
			// Abelhas oper�rias morrem se ficarem 20 dias sem poder comer
			if (diasSemComer==20)
				break;
			
			if (!dormir())
				return;
			
		}
		System.err.println(String.format("%s morreu ap�s %s dias.", getName(), idade));		
	}
	
	
}
