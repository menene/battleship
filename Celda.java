// modelo

public class Celda {
    private boolean tieneBarco;
    private boolean disparada;

    public Celda() {
        this.tieneBarco = false;
        this.disparada = false;
    }

    public boolean tieneBarco() { 
        return this.tieneBarco; 
    }

    public void ponerBarco() { 
        this.tieneBarco = true; 
    }

    public boolean estaDisparada() { 
        return this.disparada; 
    }

    public void disparar() { 
        this.disparada = true; 
    }

    public String toString() {
        if (!this.disparada) {
            return ".";
        } else {
            return this.tieneBarco ? "X" : "O";
        }
    }
}
