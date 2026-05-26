public class Main {

    public static void main(String[] args) throws InterruptedException {

        ContoBancario conto = new ContoBancario(1000.0);

        System.out.println("=== Simulazione conto bancario condiviso ===");
        System.out.printf("Saldo iniziale: %.2f€%n%n", conto.getSaldo());

        Thread alice = new Thread(new Utente(conto, "versamento", 200.0, 3), "Alice");
        Thread bob   = new Thread(new Utente(conto, "prelievo",   150.0, 4), "Bob");
        Thread carlo = new Thread(new Utente(conto, "prelievo",   300.0, 2), "Carlo");

        alice.start();
        bob.start();
        carlo.start();

        alice.join();
        bob.join();
        carlo.join();

        System.out.printf("%n=== Fine simulazione ===%n");
        System.out.printf("Saldo finale: %.2f€%n", conto.getSaldo());
    }
}
