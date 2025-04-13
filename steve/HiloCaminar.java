/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package steve;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jriva
 */
public class HiloCaminar extends Thread{

    crearEscenaGrafica escena = new crearEscenaGrafica();

    public HiloCaminar(crearEscenaGrafica es) {
      escena=es;
        
    }
    
    
    
    @Override
    public void run() {
        while(true){
           escena.caminar();
            try {
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                Logger.getLogger(HiloCaminar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

      
    
}
