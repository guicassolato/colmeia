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
			System.out.println(String.format("%s consumiu gel�ia real. (estoque atual: %s)", getName(), deposito.geleiaReal));
			mutex.sairDaRegiaoCritica();
			return true;
		} else {
			mutex.sairDaRegiaoCritica();
			System.out.println(String.format("%s n�o p�de consumir gel�ia real por falta no dep�sito.", getName()));
			return false;
		}
	}
	
	protected boolean consumirNectar(int quantidade) {
		mutex.entrarNaRegiaoCritica();
		if (deposito.nectar >= quantidade) {
			deposito.nectar -= quantidade;
			System.out.println(String.format("%s consumiu n�ctar. (estoque atual: %s)", getName(), deposito.nectar));
			mutex.sairDaRegiaoCritica();
			return true;
		} else {
			mutex.sairDaRegiaoCritica();
			System.out.println(String.format("%s n�o p�de consumir n�ctar por falta no dep�sito.", getName()));
			return false;
		}
	}
	
	protected void comer() {
		// Abelhas normais (oper�rias e zang�es) consomem 1 unidades de gel�ia real por dia at� o 3� dia de vida + 10 unidades de n�ctar por dia durante toda a vida
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
