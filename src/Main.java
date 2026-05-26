public class Main {

    public static void main(String[] args) throws InterruptedException {

        // Conto condiviso con saldo iniziale di 1000€
        ContoBancario conto = new ContoBancario(1000.0);

        System.out.println("=== Simulazione conto bancario condiviso ===");
        System.out.printf("Saldo iniziale: %.2f€%n%n", conto.getSaldo());

        // Creazione dei thread: ogni Thread riceve un Runnable (Utente) e un nome
        Thread alice = new Thread(new Utente(conto, "versamento", 200.0, 3), "Alice");
        Thread bob   = new Thread(new Utente(conto, "prelievo",   150.0, 4), "Bob");
        Thread carlo = new Thread(new Utente(conto, "prelievo",   300.0, 2), "Carlo");

        // start() avvia il thread; internamente chiama run() in un nuovo flusso di esecuzione
        alice.start();
        bob.start();
        carlo.start();

        // join() fa attendere il main finché ogni thread non termina
        alice.join();
        bob.join();
        carlo.join();

        System.out.printf("%n=== Fine simulazione ===%n");
        System.out.printf("Saldo finale: %.2f€%n", conto.getSaldo());
    }
}
