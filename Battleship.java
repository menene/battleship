import java.util.Random;

public class Battleship {
    private final int N = 5;
    private final int BARCOS = 3;
    private final int DISPAROS_MAX = 12;

    private final Tablero tablero;
    private final Consola vista;

    private final Random rnd = new Random();
    private int disparosUsados = 0;

    public Battleship() {
        this.vista = new Consola();
        this.tablero = new Tablero(N);
    }

    public void jugar() {
        this.tablero.colocarBarcosAleatorios(this.BARCOS, this.rnd);
        this.vista.mostrarBienvenida(this.N, this.BARCOS, this.DISPAROS_MAX);

        disparosUsados = 0;
        while (disparosUsados < this.DISPAROS_MAX && !this.tablero.todosHundidos()) {
            this.vista.dibujarTableroPublico(this.tablero);
            String entrada = this.vista.pedirEntrada("Disparo " + (disparosUsados + 1) + "/" + this.DISPAROS_MAX + " -> ");
            entrada = entrada.trim().toUpperCase();

            if (entrada.equals("SALIR")) {
                vista.mostrarMensaje("Saliendo del juego. Adiós.");
                return;
            }

            if (entrada.equals("REVELAR")) {
                vista.mostrarMensaje("[DEBUG] Tablero oculto:");
                vista.dibujarTableroOculto(tablero);
                continue;
            }

            int[] rc = parsearCoordenada(entrada);
            if (rc == null) {
                vista.mostrarMensaje("Coordenada inválida. Ejemplo válido: C4.\n");
                continue;
            }
            int r = rc[0], c = rc[1];

            if (this.tablero.yaDisparada(r, c)) {
                this.vista.mostrarMensaje("Ya disparaste ahí. Elige otra celda.\n");
                continue;
            }

            disparosUsados++;
            try {
                boolean acierto = this.tablero.disparar(r, c);

                if (acierto) {
                    int hundidos = this.tablero.barcosHundidos();
                    this.vista.mostrarMensaje("¡Tocado! Barcos restantes: " + (BARCOS - hundidos) + "\n");
                } else {
                    this.vista.mostrarMensaje("Agua.\n");
                }
            } catch (IllegalStateException e) {
                this.vista.mostrarMensaje("Movimiento inválido: " + e.getMessage() + "\n");
            }
        }

        this.vista.dibujarTableroPublico(this.tablero);
        if (this.tablero.todosHundidos()) {
            this.vista.mostrarFinGanaste(disparosUsados);
        } else {
            int restantes = this.BARCOS - this.tablero.barcosHundidos();
            this.vista.mostrarFinPerdiste(restantes);
        }
    }

    private int[] parsearCoordenada(String s) {
        if (s.length() < 2 || s.length() > 3) return null;

        char filaChar = s.charAt(0);
        if (filaChar < 'A' || filaChar >= 'A' + this.N) return null;

        String colStr = s.substring(1);
        for (int i = 0; i < colStr.length(); i++) {
            if (!Character.isDigit(colStr.charAt(i))) return null;
        }

        int col = Integer.parseInt(colStr);
        if (col < 1 || col > this.N) return null;

        int r = filaChar - 'A';
        int c = col - 1;

        return new int[] { r, c };
    }
}
