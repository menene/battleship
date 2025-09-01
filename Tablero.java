// modelo

import java.util.Random;

public class Tablero {
    private final int n;
    private final Celda[][] celdas;
    private int barcosTotales;

    public Tablero(int n) {
        this.n = n;
        this.celdas = new Celda[n][n];
        this.initTablero(n);
        this.barcosTotales = 0;
    }

    private void initTablero(int n) {
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                this.celdas[r][c] = new Celda();
            }
        }
    }

    public int getN() { 
        return this.n; 
    }

    public Celda getCelda(int r, int c) { 
        return this.celdas[r][c]; 
    }

    public int getBarcosTotales() { 
        return this.barcosTotales; 
    }

    public void colocarBarcosAleatorios(int cantidad, Random rnd) {
        int colocados = 0;

        while (colocados < cantidad) {
            int r = rnd.nextInt(n);
            int c = rnd.nextInt(n);

            if (!this.celdas[r][c].tieneBarco()) {
                this.celdas[r][c].ponerBarco();
                colocados++;
            }
        }

        this.barcosTotales = cantidad;
    }

    public boolean disparar(int r, int c) {
        Celda celda = this.celdas[r][c];

        if (celda.estaDisparada()) {
            throw new IllegalStateException("Ya se disparó a esa celda.");
        }

        celda.disparar();

        return celda.tieneBarco();
    }

    public boolean yaDisparada(int r, int c) {
        return this.celdas[r][c].estaDisparada();
    }

    public int barcosHundidos() {
        int hits = 0;

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                Celda celda = this.celdas[r][c];

                if (celda.tieneBarco() && celda.estaDisparada()) {
                    hits++;
                }
            }
        }

        return hits;
    }

    public boolean todosHundidos() {
        return barcosHundidos() == this.barcosTotales;
    }
}
