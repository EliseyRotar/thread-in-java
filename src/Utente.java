public class Utente implements Runnable {

    private final ContoBancario conto;
    private final String operazione;
    private final double importo;
    private final int numOperazioni;

    public Utente(ContoBancario conto, String operazione, double importo, int numOperazioni) {
        this.conto = conto;
        this.operazione = operazione;
        this.importo = importo;
        this.numOperazioni = numOperazioni;
    }

    @Override
    public void run() {
        for (int i = 0; i < numOperazioni; i++) {
            if (operazione.equals("versamento")) {
                conto.versa(importo);
            } else {
                conto.preleva(importo);
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
