public class Main {

    public static void main(String[] args) {

        ContoBancario conto = new ContoBancario(1000.0);

        System.out.println("=== Simulazione conto bancario condiviso ===");
        System.out.println("Saldo iniziale: " + conto.getSaldo() + "€\n");

        Thread alice = new Thread(new Utente(conto, "versamento", 200.0, 3), "Alice");
        Thread bob = new Thread(new Utente(conto, "prelievo", 150.0, 4), "Bob");
        Thread carlo = new Thread(new Utente(conto, "prelievo", 300.0, 2), "Carlo");

        alice.start();
        bob.start();
        carlo.start();

        try {
            alice.join();
            bob.join();
            carlo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n=== Fine simulazione ===");
        System.out.println("Saldo finale: " + conto.getSaldo() + "€");
    }
}
