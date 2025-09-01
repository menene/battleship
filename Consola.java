// vista

import java.util.Scanner;

public class Consola {
    private final Scanner sc;

    public Consola() {
        this.sc = new Scanner(System.in);
    }

    public void mostrarBienvenida(int n, int barcos, int disparosMax) {
        println("=== 💣 Battleship ⚓️ ===");
        println("Tablero " + n + "x" + n + ".");
        println("Filas A–" + (char)('A' + (n - 1)) + " y columnas 1–" + n + ".");
        println("Hay " + barcos + " barcos ocultos (tamaño 1).");
        println("Comandos:\n\tCoordenadas (B3)\n\tREVELAR para mostrar el tablero\n\tSALIR para terminar.\n");
    }

    public void dibujarTableroPublico(Tablero t) {
        int n = t.getN();
        print("   ");

        for (int c = 1; c <= n; c++) { 
            print(c + " ");
        }
        println("");
        
        for (int r = 0; r < n; r++) {
            print((char) ('A' + r) + "  ");
            
            for (int c = 0; c < n; c++) {
                print(t.getCelda(r, c) + " ");
            }

            println("");
        }

        println("");
    }

    public void dibujarTableroOculto(Tablero t) {
        int n = t.getN();
        print("   ");

        for (int c = 1; c <= n; c++) {
            print(c + " ");
        }
        println("");
        
        for (int r = 0; r < n; r++) {
            print((char) ('A' + r) + "  ");
            
            for (int c = 0; c < n; c++) {
                Celda celda = t.getCelda(r, c);
                
                char ch;
                if (celda.tieneBarco() && celda.estaDisparada()) {
                    ch = 'X';
                } else if (celda.tieneBarco()) {
                    ch = 'S';
                } else if (celda.estaDisparada()) {
                    ch = 'O';
                } else {
                    ch = '.';
                }
                
                print(ch + " ");
            }

            println("");
        }

        println("");
    }

    public String pedirEntrada(String prompt) {
        print(prompt);
        return this.sc.nextLine();
    }

    public void mostrarMensaje(String mensaje) {
        println(mensaje);
    }

    public void mostrarFinGanaste(int disparosUsados) {
        println("¡Ganaste! Hundiste todos los barcos en " + disparosUsados + " disparos.");
    }

    public void mostrarFinPerdiste(int barcosRestantes) {
        println("Te quedaste sin disparos. Barcos restantes: " + barcosRestantes);
    }

    private void print(String s) { 
        System.out.print(s); 
    }

    private void println(String s) { 
        System.out.println(s); 
    }
}
