import java.util.concurrent.ConcurrentHashMap;
import java.util.Set;

public class Mutex {

	private class Status {
		protected boolean choosing = false;
		protected int ticket = 0;
	}
	
	private ConcurrentHashMap<Long, Status> statuses = new ConcurrentHashMap<Long, Status>();

	public void registrarThread(Long key) {
		statuses.put(key, new Status());
	}
	
	private void setChoosing(Long key, boolean value) {
		try {
			statuses.get(key).choosing = value;
		} catch(Exception e) {
			System.err.println(String.format("Não existe thread registrada com o id %s.", key));
		}
	}
	
	private boolean getChoosing(Long key) {
		return statuses.get(key).choosing;
	}
	
	private void getNewTicket() {
		int maxTicket = -1;
		Set<Long> keys = statuses.keySet();
		for (Long key: keys)
			if (statuses.get(key).ticket > maxTicket) maxTicket = statuses.get(key).ticket;
		setTicket(Thread.currentThread().getId(), maxTicket + 1);
	}
	
	private void setTicket(Long key, int value) {
		try {
			statuses.get(key).ticket = value;
		} catch(Exception e) {
			System.err.println(String.format("Não existe thread registrada com o id %s.", key));
		}
	}

	private int getTicket(Long key) {
		return statuses.get(key).ticket;
	}	
	
	public void entrarNaRegiaoCritica() {
		String nomeThreadCandidata = Thread.currentThread().getName();
		Long idThreadCandidata = Thread.currentThread().getId();
		setChoosing(idThreadCandidata, true);
		System.out.println(String.format("%s está tentando obter uma senha para acessar uma seção crítica.", nomeThreadCandidata));
		getNewTicket();
		System.out.println(String.format("%s conseguiu a senha nº %s.", nomeThreadCandidata, getTicket(idThreadCandidata)));
		setChoosing(idThreadCandidata, false);
		System.out.println(String.format("%s vai verificar se mais alguma thread pretende acessar a região crítica.", nomeThreadCandidata));
		Set<Long> keys = statuses.keySet();
		for (Long idThreadConcorrente: keys) {
			if (idThreadConcorrente.equals(idThreadCandidata)) { continue; }
			while (getChoosing(idThreadConcorrente));
			while (getTicket(idThreadConcorrente) != 0 && getTicket(idThreadConcorrente) < getTicket(idThreadCandidata));
			if (getTicket(idThreadConcorrente) == getTicket(idThreadCandidata) && idThreadConcorrente < idThreadCandidata)
				while (getTicket(idThreadConcorrente) != 0);
		}
		System.out.println(String.format("%s conseguiu acessar a região crítica.", nomeThreadCandidata));
	}
	
	public void sairDaRegiaoCritica() {
		setTicket(Thread.currentThread().getId(), 0);
		System.out.println(String.format("%s descartou sua senha.", Thread.currentThread().getName()));
	}
	
	
}
