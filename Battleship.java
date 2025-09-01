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
        tablero.colocarBarcosAleatorios(BARCOS, rnd);
        vista.mostrarBienvenida(N, BARCOS, DISPAROS_MAX);

        disparosUsados = 0;
        while (disparosUsados < DISPAROS_MAX && !tablero.todosHundidos()) {
            vista.dibujarTableroPublico(tablero);
            String entrada = vista.pedirEntrada("Disparo " + (disparosUsados + 1) + "/" + DISPAROS_MAX + " -> ");
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

            if (tablero.yaDisparada(r, c)) {
                vista.mostrarMensaje("Ya disparaste ahí. Elige otra celda.\n");
                continue;
            }

            disparosUsados++;
            try {
                boolean acierto = tablero.disparar(r, c);

                if (acierto) {
                    int hundidos = tablero.barcosHundidos();
                    vista.mostrarMensaje("¡Tocado! Barcos restantes: " + (BARCOS - hundidos) + "\n");
                } else {
                    vista.mostrarMensaje("Agua.\n");
                }
            } catch (IllegalStateException e) {
                vista.mostrarMensaje("Movimiento inválido: " + e.getMessage() + "\n");
            }
        }

        vista.dibujarTableroPublico(tablero);
        if (tablero.todosHundidos()) {
            vista.mostrarFinGanaste(disparosUsados);
        } else {
            int restantes = BARCOS - tablero.barcosHundidos();
            vista.mostrarFinPerdiste(restantes);
        }
    }

    private int[] parsearCoordenada(String s) {
        if (s.length() < 2 || s.length() > 3) return null;

        char filaChar = s.charAt(0);
        if (filaChar < 'A' || filaChar >= 'A' + N) return null;

        String colStr = s.substring(1);
        for (int i = 0; i < colStr.length(); i++) {
            if (!Character.isDigit(colStr.charAt(i))) return null;
        }

        int col = Integer.parseInt(colStr);
        if (col < 1 || col > N) return null;

        int r = filaChar - 'A';
        int c = col - 1;

        return new int[] { r, c };
    }
}
