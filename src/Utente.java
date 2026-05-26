// Ogni Utente è un thread che opera sul conto bancario condiviso
public class Utente implements Runnable {

    private final ContoBancario conto;
    private final String operazione; // "versamento" oppure "prelievo"
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

            // Piccola pausa per rendere l'esecuzione concorrente più visibile nell'output
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
