public class Operaria extends Abelha {

	public Operaria(Rainha r, Deposito d, Mutex c) {
		super("Operária O-" + Thread.activeCount(), r, d, c);
		start();
	}
	
	private void produzirNectar() {
		mutex.entrarNaRegiaoCritica();
		deposito.nectar++;
		System.out.println(String.format("%s produziu néctar. (estoque atual: %s)", getName(), deposito.nectar));
		mutex.sairDaRegiaoCritica();
	}
	
	private void produzirGeleiaReal() {
		if (idade < 10) { // Abelhas operárias só produzem geléia real nos seus primeiros 10 dias de vida
			mutex.entrarNaRegiaoCritica();
			deposito.geleiaReal++;
			System.out.println(String.format("%s produziu geléia real. (estoque atual: %s)", getName(), deposito.geleiaReal));
			mutex.sairDaRegiaoCritica();
		}
	}
	
	public void run() {
		while (idade <= 40) { // Abelhas operárias vivem 40 dias

			// Operárias comem como qualquer abelha normal
			comer();

			// Operárias produzem 3 unidades de geléia real por dia somente até o 5 ano de vida
			if (idade < 5) {
				for (int i=1; i<=3; i++)
					produzirGeleiaReal();
			}
			
			// Operárias produzem 10 unidades de néctar por dia a partir de 5 dias de vida
			if (idade >= 5) {
				for (int i=1; i<=10; i++) {
					for (int z=1; z<=10000; z++); // Tempo gasto para buscar o pólen
					produzirNectar();
				}
			}
			
			// Abelhas operárias morrem se ficarem 20 dias sem poder comer
			if (diasSemComer==20)
				break;
			
			if (!dormir())
				return;
			
		}
		System.err.println(String.format("%s morreu após %s dias.", getName(), idade));		
	}
	
	
}
