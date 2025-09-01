// modelo

public class Celda {
    private boolean tieneBarco;
    private boolean disparada;

    public Celda() {
        this.tieneBarco = false;
        this.disparada = false;
    }

    public boolean tieneBarco() { 
        return tieneBarco; 
    }

    public void ponerBarco() { 
        this.tieneBarco = true; 
    }

    public boolean estaDisparada() { 
        return disparada; 
    }

    public void disparar() { 
        this.disparada = true; 
    }
}
