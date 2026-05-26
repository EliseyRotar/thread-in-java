public class Utente implements Runnable {

    private ContoBancario conto;
    private String operazione;
    private double importo;
    private int numOperazioni;

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
                e.printStackTrace();
            }
        }
    }
}
