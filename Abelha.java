public class Abelha extends Thread {

	protected Rainha rainha;
	protected Deposito deposito;
	protected int idade = 0;
	protected int diasSemComer = 0;
	protected Mutex mutex;
	
	public Abelha(String nome, Rainha r, Deposito d, Mutex c) {
		super(nome);
		rainha = r;
		deposito = d;
		mutex = c;
		mutex.registrarThread(this.getId());
		System.out.println(String.format("%s nasceu.", nome));
	}
	
	protected boolean consumirGeleiaReal(int quantidade) {
		mutex.entrarNaRegiaoCritica();
		if (deposito.geleiaReal >= quantidade) {
			deposito.geleiaReal -= quantidade;
			System.out.println(String.format("%s consumiu geléia real. (estoque atual: %s)", getName(), deposito.geleiaReal));
			mutex.sairDaRegiaoCritica();
			return true;
		} else {
			mutex.sairDaRegiaoCritica();
			System.out.println(String.format("%s não pôde consumir geléia real por falta no depósito.", getName()));
			return false;
		}
	}
	
	protected boolean consumirNectar(int quantidade) {
		mutex.entrarNaRegiaoCritica();
		if (deposito.nectar >= quantidade) {
			deposito.nectar -= quantidade;
			System.out.println(String.format("%s consumiu néctar. (estoque atual: %s)", getName(), deposito.nectar));
			mutex.sairDaRegiaoCritica();
			return true;
		} else {
			mutex.sairDaRegiaoCritica();
			System.out.println(String.format("%s não pôde consumir néctar por falta no depósito.", getName()));
			return false;
		}
	}
	
	protected void comer() {
		// Abelhas normais (operárias e zangões) consomem 1 unidades de geléia real por dia até o 3º dia de vida + 10 unidades de néctar por dia durante toda a vida
		if ((idade > 3 || consumirGeleiaReal(1)) && consumirNectar(8))
			diasSemComer = 0;
		else
			diasSemComer++;
	}
	
	protected boolean dormir() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			return false;
		}
		idade++;
		return true;
	}

}
