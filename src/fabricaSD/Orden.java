package fabricaSD;

import java.util.Random;

public class Orden {
    private int numeroOrden;

    public Orden() {
        this.numeroOrden = generarNumeroAleatorio();
    }

    private int generarNumeroAleatorio() {
        Random random = new Random();
        return random.nextInt(1000) + 1;
    }

    public int getNumeroOrden() {
        return numeroOrden;
    }
}
