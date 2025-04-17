package Principal;

import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.*;

public class SalonDeClasesBuilder {
    private EscenaGrafica escena;
    private InstanciadorObjetos3D instanciador;
    
    public SalonDeClasesBuilder(EscenaGrafica escena, InstanciadorObjetos3D instanciador) {
        this.escena = escena;
        this.instanciador = instanciador;
    }
    
    public void construirSalon(float centroX, float centroZ) {
        // Dimensiones del salón (en metros)
        float anchoSalon = 6.0f;
        float largoSalon = 8.0f;
        float alturaPared = 3.0f;
        float grosorPared = 0.2f;
        
        // Crear paredes
        crearParedes(centroX, centroZ, anchoSalon, largoSalon, alturaPared, grosorPared);
        
        // Crear techo
        crearTecho(centroX, centroZ, anchoSalon, largoSalon, alturaPared, grosorPared);
        
        // Crear ventanas
        crearVentanas(centroX, centroZ, anchoSalon, largoSalon, alturaPared);
        
        // Crear puerta principal
        crearPuertaPrincipal(centroX, centroZ, anchoSalon, largoSalon, alturaPared);
        
        // Crear piso
        crearPiso(centroX, centroZ, anchoSalon, largoSalon);
        
        // Muebles del salón con hitboxes precisas
        crearMuebles(centroX, centroZ, anchoSalon, largoSalon);
    }
    
    private void crearParedes(float centroX, float centroZ, float ancho, float largo, float altura, float grosor) {
        // Pared frontal (con puerta)
        escena.crearParedCompleta(centroX, altura/2, centroZ - largo/2, 
                                 ancho/2, altura/2, grosor/2, 
                                 210, 180, 140, 0);
        
        // Pared trasera
        escena.crearParedCompleta(centroX, altura/2, centroZ + largo/2, 
                                 ancho/2, altura/2, grosor/2, 
                                 210, 180, 140, 0);
        
        // Pared izquierda
        escena.crearParedCompleta(centroX - ancho/2, altura/2, centroZ, 
                                 largo/2, altura/2, grosor/2, 
                                 210, 180, 140, 90);
        
        // Pared derecha
        escena.crearParedCompleta(centroX + ancho/2, altura/2, centroZ, 
                                 largo/2, altura/2, grosor/2, 
                                 210, 180, 140, 90);
    }
    
    private void crearTecho(float centroX, float centroZ, float ancho, float largo, float altura, float grosor) {
        Box techo = new Box(ancho/2, grosor/2, largo/2, escena.paraTextura, escena.c.setColor(200, 200, 200));
        Transform3D t3dTecho = new Transform3D();
        t3dTecho.setTranslation(new Vector3f(centroX, altura + grosor/2, centroZ));
        TransformGroup tgTecho = new TransformGroup(t3dTecho);
        escena.tgMundo.addChild(tgTecho);
        tgTecho.addChild(techo);
    }
    
    private void crearPiso(float centroX, float centroZ, float ancho, float largo) {
        Box piso = new Box(ancho/2, 0.05f, largo/2, escena.paraTextura, escena.textura.crearTexturas("piso_madera.jpg"));
        Transform3D t3dPiso = new Transform3D();
        t3dPiso.setTranslation(new Vector3f(centroX, -0.05f, centroZ));
        TransformGroup tgPiso = new TransformGroup(t3dPiso);
        escena.tgMundo.addChild(tgPiso);
        tgPiso.addChild(piso);
    }
    
    private void crearVentanas(float centroX, float centroZ, float ancho, float largo, float altura) {
        // Ventanas en pared izquierda
        float alturaVentana = altura * 0.6f;
        float yVentana = altura * 0.4f;
        
        // Dos ventanas en la pared izquierda
        escena.agregarVentanaInteractiva(centroX - ancho/2, yVentana, centroZ - largo/4, 
                                        0.8f, alturaVentana, 0.05f, 90, true);
        escena.agregarVentanaInteractiva(centroX - ancho/2, yVentana, centroZ + largo/4, 
                                        0.8f, alturaVentana, 0.05f, 90, true);
        
        // Dos ventanas en la pared derecha
        escena.agregarVentanaInteractiva(centroX + ancho/2, yVentana, centroZ - largo/4, 
                                        0.8f, alturaVentana, 0.05f, -90, true);
        escena.agregarVentanaInteractiva(centroX + ancho/2, yVentana, centroZ + largo/4, 
                                        0.8f, alturaVentana, 0.05f, -90, true);
    }
    
    private void crearPuertaPrincipal(float centroX, float centroZ, float ancho, float largo, float altura) {
        float alturaPuerta = altura * 0.8f;
        float yPuerta = alturaPuerta/2;
        float anchoPuerta = 1.2f;
        
        // Crear puerta en la pared frontal
//        Puerta puerta = new Puerta(centroX, yPuerta, centroZ - largo/2 + 0.1f, 
//                                  anchoPuerta, alturaPuerta, 0.1f, 0);
//        escena.tgMundo.addChild(puerta);
//        escena.listaPuerta.add(puerta);
//        
//         Crear detector de proximidad para la puerta
//        crearDetectorProximidadPuerta(puerta, 1.0f);
        
        // Asegurar que la pared frontal tenga espacio para la puerta
        // (esto debería manejarse en crearParedCompleta con parámetros adicionales)
    }
    
    private void crearDetectorProximidadPuerta(final Puerta puerta, final float umbral) {
        Behavior detector = new Behavior() {
            WakeupOnElapsedTime tiempo = new WakeupOnElapsedTime(200);

            public void initialize() {
                wakeupOn(tiempo);
            }

            public void processStimulus(java.util.Enumeration criteria) {
                Vector3f posPuerta = puerta.getPosicion();
                Point3d posPersonaje = escena.posPersonaje;

                // Convertir posición del personaje a Vector3f
                Vector3f posPersonajeVec = new Vector3f((float) posPersonaje.x, 
                                                       (float) posPersonaje.y, 
                                                       (float) posPersonaje.z);

                // Calcular distancia
                float dx = posPuerta.x - posPersonajeVec.x;
                float dy = posPuerta.y - posPersonajeVec.y;
                float dz = posPuerta.z - posPersonajeVec.z;
                float distancia = (float) Math.sqrt(dx*dx + dy*dy + dz*dz);

                if (distancia < umbral) {
                    puerta.toggle(); // Abre o cierra la puerta
                }

                wakeupOn(tiempo);
            }
        };

        detector.setSchedulingBounds(new BoundingSphere(new Point3d(), 100.0));
        escena.objRaiz.addChild(detector);
    }
    
    private void crearMuebles(float centroX, float centroZ, float ancho, float largo) {
        // Crear mesas y sillas en filas con hitboxes precisas
        int filas = 3;
        int columnas = 2;
        float espacioEntreFilas = 1.8f;
        float espacioEntreColumnas = 2.2f;
        
        float inicioX = centroX - (columnas-1) * espacioEntreColumnas / 2;
        float inicioZ = centroZ - (filas-1) * espacioEntreFilas / 2 + 1.0f;
        
        // Dimensiones reales de los muebles (para hitboxes precisas)
        float anchoMesa = 0.6f;
        float altoMesa = 0.7f;
        float profundoMesa = 0.4f;
        
        float anchoSilla = 0.3f;
        float altoSilla = 0.5f;
        float profundoSilla = 0.3f;
        
        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                float x = inicioX + c * espacioEntreColumnas;
                float z = inicioZ + f * espacioEntreFilas;
                
                // Mesa con hitbox precisa
                instanciador.agregarInstanciaConColision(
                    RepositorioObjetos3D.mesa, 
                    x, 0, z, 
                    anchoMesa, altoMesa, profundoMesa
                );
                
                // Sillas alrededor de la mesa con hitboxes precisas
                // Silla frontal izquierda
                instanciador.agregarInstanciaConColision(
                    RepositorioObjetos3D.silla, 
                    x - (anchoMesa/2 + anchoSilla/2 + 0.1f), 
                    0, 
                    z - (profundoMesa/2 + profundoSilla/2 + 0.1f), 
                    anchoSilla, altoSilla, profundoSilla
                );
                
                // Silla frontal derecha
                instanciador.agregarInstanciaConColision(
                    RepositorioObjetos3D.silla, 
                    x + (anchoMesa/2 + anchoSilla/2 + 0.1f), 
                    0, 
                    z - (profundoMesa/2 + profundoSilla/2 + 0.1f), 
                    anchoSilla, altoSilla, profundoSilla
                );
                
                // Silla trasera izquierda
                instanciador.agregarInstanciaConColision(
                    RepositorioObjetos3D.silla, 
                    x - (anchoMesa/2 + anchoSilla/2 + 0.1f), 
                    0, 
                    z + (profundoMesa/2 + profundoSilla/2 + 0.1f), 
                    anchoSilla, altoSilla, profundoSilla
                );
                
                // Silla trasera derecha
                instanciador.agregarInstanciaConColision(
                    RepositorioObjetos3D.silla, 
                    x + (anchoMesa/2 + anchoSilla/2 + 0.1f), 
                    0, 
                    z + (profundoMesa/2 + profundoSilla/2 + 0.1f), 
                    anchoSilla, altoSilla, profundoSilla
                );
            }
        }
        
        // Pizarron al frente
        Box pizarron = new Box(1.5f, 0.7f, 0.05f, Primitive.GENERATE_NORMALS | Primitive.GENERATE_TEXTURE_COORDS, 
                              escena.textura.crearTexturas("pizarron.jpg"));
        Transform3D t3dPizarron = new Transform3D();
        t3dPizarron.setTranslation(new Vector3f(centroX, 1.2f, centroZ - largo/2 + 0.2f));
        TransformGroup tgPizarron = new TransformGroup(t3dPizarron);
        escena.tgMundo.addChild(tgPizarron);
        tgPizarron.addChild(pizarron);
        
        // Escritorio del profesor con hitbox precisa
        float anchoEscritorio = 0.8f;
        float altoEscritorio = 0.8f;
        float profundoEscritorio = 0.4f;
        
        instanciador.agregarInstanciaConColision(
            RepositorioObjetos3D.mesa, 
            centroX, 0, centroZ + largo/2 - 1.5f, 
            anchoEscritorio, altoEscritorio, profundoEscritorio
        );
        
        // Silla del profesor con hitbox precisa
        instanciador.agregarInstanciaConColision(
            RepositorioObjetos3D.silla, 
            centroX, 0, centroZ + largo/2 - 2.0f, 
            0.35f, 0.6f, 0.35f
        );
    }
}