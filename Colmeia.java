public class Colmeia {

	public static void main(String[] args) {
		
		Mutex mutex = new Mutex();
		
		Deposito deposito = new Deposito();
		Rainha rainha = new Rainha(deposito, mutex);
		rainha.start();
		
		new Zangao(rainha, deposito, mutex);

	}

}
