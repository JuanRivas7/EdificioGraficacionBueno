package steve;

import java.util.logging.Level;
import java.util.logging.Logger;

public class HiloMarchar extends Thread {

    private crearEscenaGrafica escena;
    private boolean ejecutando; // Bandera para controlar la ejecución del hilo

    public HiloMarchar(crearEscenaGrafica es) {
        this.escena = es;
        this.ejecutando = true; // Inicializar la bandera como true
    }

    @Override
    public void run() {
        while (ejecutando) { // Usar la bandera para controlar el bucle
            escena.marchar();
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(HiloMarchar.class.getName()).log(Level.SEVERE, null, ex);
                // Si el hilo es interrumpido, salir del bucle
                break;
            }
        }
    }

    // Método para detener el hilo de manera segura
    public void detener() {
        this.ejecutando = false; // Cambiar la bandera para detener el bucle
    }
}