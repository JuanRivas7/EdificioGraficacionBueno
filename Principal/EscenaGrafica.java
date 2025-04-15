/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Principal;

import com.fazecast.jSerialComm.SerialPort;
import com.sun.j3d.utils.behaviors.keyboard.KeyNavigatorBehavior;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Behavior;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.WakeupOnElapsedTime;
import javax.vecmath.Color3f;
import javax.vecmath.Matrix3d;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import steve.crearEscenaGrafica;
import java.util.ArrayList;
import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.QuadArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Point3f;
import javax.vecmath.TexCoord2f;

/**
 *
 * @author jriva
 */
public class EscenaGrafica {

    ArrayList<Ventana> listaVentanas = new ArrayList<>();
    ArrayList<Puerta> listaPuerta = new ArrayList<>();
    BranchGroup objRaiz = new BranchGroup();
    static Textura textura = new Textura();
    int paraTextura = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
    color c = new color();
    TransformGroup tgMundo;
    TransformGroup tgPiso;
    crearEscenaGrafica steve = new crearEscenaGrafica();
    Point3d posPersonaje = new Point3d(0, 0, 0);
    SerialPort puerto;

    public EscenaGrafica() {
        //------- MUNDO--------
        Box bxMundo = new Box(-8.0f, 10.0f, 10.0f, paraTextura, textura.crearTexturas("cielo_1.jpg"));//c.setColor(38, 238, 240)
        Transform3D t3dMundo = new Transform3D();
        t3dMundo.set(new Vector3d(0.0f, -0.15f, 0.0f));
        tgMundo = new TransformGroup(t3dMundo);
        //--------PISO-----------
        Box bxPiso = new Box(2.0f, 0.05f, 2.0f, paraTextura, textura.crearTexturas("piso_1.jpg"));//c.setColor(38, 238, 240)
        Transform3D t3dPiso = new Transform3D();
        t3dPiso.set(new Vector3d(0.0f, -0.28f, 0.0f));
        tgPiso = new TransformGroup(t3dPiso);
        EscalarTG(tgPiso, 5.0f);
        //-----------PAREDES Y VENTANAS------------
        //crearParedCompleta(-0.2f, -0.1f, 0.0f, 0.15f, 0.2f, 0.05f, 255, 167, 38, -10);
        crearVentana(0.0f, 0.05f, 1.0f, 0.1f, 0.1f, 0.05f, 0);
        crearPuerta(0.0f, 0.15f, -0.5f, 0.2f, 0.3f, 0.05f, 90);
        agregarArbol(0.0f, -0.08f, 0.0f);
        tgMundo.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tgPiso.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        //||--------------MOVER EL MOUSE----------||
        EscalarTG(tgMundo, 5.0f);
        MouseRotate myMouseRotate = new MouseRotate();
        myMouseRotate.setTransformGroup(tgMundo);
        myMouseRotate.setSchedulingBounds(new BoundingSphere());
        //------------Termina pa que se mueva-------------
        //----------ADD CHILD----------
        tgMundo.addChild(bxMundo);
        tgPiso.addChild(bxPiso);
        //objRaiz.addChild(tgPiso);
        objRaiz.addChild(myMouseRotate);
        objRaiz.addChild(tgMundo);
        steve.Posicion(0, -0.02f, 0);
        tgMundo.addChild(tgPiso);
        steve.girarTG(steve.obtenerPanza(), 180, "Y");
        objRaiz.addChild(steve.obtenerCuerpo());
        configurarIluminacion(objRaiz);
        conectarPuerto();

    }

    private void crearParedCompleta(float x, float y, float z,
            float ancho, float alto, float profundidad,
            int R, int G, int B,
            float anguloRotacionGrados) {
        // Crear la caja (pared)
        Box bxPared = new Box(ancho, alto, profundidad, paraTextura, c.setColor(R, G, B));

        // Crear transformación compuesta (traslación + rotación)
        Transform3D t3dPared = new Transform3D();

        // 1. Primero aplicar rotación (en el origen)
        Transform3D rotacion = new Transform3D();
        rotacion.rotY(Math.toRadians(anguloRotacionGrados)); // Rotación alrededor del eje Y

        // 2. Luego aplicar traslación
        Transform3D traslacion = new Transform3D();
        traslacion.set(new Vector3d(x, y, z));

        // Combinar ambas transformaciones (primero rotación, luego traslación)
        t3dPared.mul(traslacion, rotacion);

        // Crear grupo de transformación y añadir a la escena
        TransformGroup tgPared = new TransformGroup(t3dPared);
        objRaiz.addChild(tgPared);
        tgPared.addChild(bxPared);

    }
// En tu clase EscenaGrafica

    public void crearVentana(float x, float y, float z, float ancho, float alto, float profundidad, float rotYGrados) {
        Ventana ventana = new Ventana(x, y, z, ancho, alto, profundidad, rotYGrados);
        tgMundo.addChild(ventana);
        listaVentanas.add(ventana);
    }
    public void crearPuerta(float x, float y, float z, float ancho, float alto, float profundidad, float rotYGrados) {
        Puerta Puerta1 = new Puerta(x, y, z, ancho, alto, profundidad, rotYGrados);
        tgMundo.addChild(Puerta1);
        listaPuerta.add(Puerta1);
    }

    private void crearDetectorProximidad(final Ventana ventana, final float umbral) {
        Behavior detector = new Behavior() {
            WakeupOnElapsedTime tiempo = new WakeupOnElapsedTime(200);

            public void initialize() {
                wakeupOn(tiempo);
            }

            public void processStimulus(java.util.Enumeration criteria) {
                Vector3f posVentana = ventana.getPosicion();

                // Convertimos posición del personaje (Point3d) a Vector3f
                Vector3f posPersonajeVec = new Vector3f((float) posPersonaje.x, (float) posPersonaje.y, (float) posPersonaje.z);

                // Calculamos distancia
                float dx = posVentana.x - posPersonajeVec.x;
                float dy = posVentana.y - posPersonajeVec.y;
                float dz = posVentana.z - posPersonajeVec.z;
                float distancia = (float) Math.sqrt(dx * dx + dy * dy + dz * dz);

                if (distancia < umbral) {
                    ventana.toggle(); // Abre o cierra
                }

                wakeupOn(tiempo);
            }
        };

        detector.setSchedulingBounds(new BoundingSphere(new Point3d(), 100.0));
        objRaiz.addChild(detector);
    }

    public void EscalarTG(TransformGroup tg, float x) {
        Transform3D leer = new Transform3D();
        Transform3D mover = new Transform3D();
        tg.getTransform(leer);
        mover.setScale(x);
        leer.mul(mover);
        tg.setTransform(leer);
    }

    private void configurarIluminacion(BranchGroup escena) {
        // 1. Luz ambiental general (para iluminación base)
        AmbientLight luzAmbiental = new AmbientLight();
        luzAmbiental.setColor(new Color3f(0.3f, 0.3f, 0.3f));
        luzAmbiental.setInfluencingBounds(new BoundingSphere(new Point3d(0, 0, 0), 100.0));
        escena.addChild(luzAmbiental);

        // 2. Luz direccional desde arriba (simula el sol)
        DirectionalLight luzDesdeArriba = new DirectionalLight();
        luzDesdeArriba.setColor(new Color3f(0.7f, 0.7f, 0.7f));
        luzDesdeArriba.setDirection(new Vector3f(0.0f, -1.0f, -0.3f)); // Apuntando hacia abajo
        luzDesdeArriba.setInfluencingBounds(new BoundingSphere(new Point3d(0, 0, 0), 100.0));
        escena.addChild(luzDesdeArriba);

        // 3. Luz direccional de relleno (para reducir sombras muy oscuras)
        DirectionalLight luzRelleno = new DirectionalLight();
        luzRelleno.setColor(new Color3f(0.4f, 0.4f, 0.4f));
        luzRelleno.setDirection(new Vector3f(-0.5f, -0.5f, -0.5f));
        luzRelleno.setInfluencingBounds(new BoundingSphere(new Point3d(0, 0, 0), 100.0));
        escena.addChild(luzRelleno);
    }

    public void moverBehaivor() {
        KeyNavigatorBehavior keyNav = new KeyNavigatorBehavior(tgMundo);
        keyNav.setSchedulingBounds(new BoundingSphere(new Point3d(), 1000.0));

        //-----Behavior para animacion del personaje(que se mueva Pz-----
        Behavior animacionBehavior = new Behavior() {
            private WakeupOnElapsedTime wakeup;
            private Transform3D lastTransform = new Transform3D();

            public void initialize() {
                wakeup = new WakeupOnElapsedTime(50); // Verificar cada 50ms
                wakeupOn(wakeup);
                tgMundo.getTransform(lastTransform);
            }

            public void processStimulus(Enumeration criteria) {
                Transform3D currentTransform = new Transform3D();
                tgMundo.getTransform(currentTransform);

                // Comprobar si hubo movimiento
                if (!currentTransform.epsilonEquals(lastTransform, 0.01f)) {
                    steve.caminar(); // Activar animación
                }

                lastTransform.set(currentTransform);
                wakeupOn(wakeup);
            }
        };
        animacionBehavior.setSchedulingBounds(new BoundingSphere(new Point3d(), 1000.0));
        objRaiz.addChild(animacionBehavior);
        tgMundo.addChild(keyNav);
    }

    private void rotarTG(TransformGroup tg, int grados, String eje, Point3d personajePosition) {
        Transform3D transformacionMundo = new Transform3D();
        tg.getTransform(transformacionMundo);

        // Crear transformacion para mover el mundo desde el personaje
        Transform3D traslacionAlOrigen = new Transform3D();
        traslacionAlOrigen.setTranslation(new Vector3d(
                -personajePosition.x,
                -personajePosition.y,
                -personajePosition.z
        ));

        Transform3D rotacion = new Transform3D();
        switch (eje) {
            case "X":
                rotacion.rotX(Math.PI / 180 * grados);
                break;
            case "Y":
                rotacion.rotY(Math.PI / 180 * grados);
                break;
            case "Z":
                rotacion.rotZ(Math.PI / 180 * grados);
                break;
            default:
                break;
        }

        // Posición original
        Transform3D traslacionDeVuelta = new Transform3D();
        traslacionDeVuelta.setTranslation(new Vector3d(
                personajePosition.x,
                personajePosition.y,
                personajePosition.z
        ));

        transformacionMundo.mul(traslacionDeVuelta, transformacionMundo);
        transformacionMundo.mul(rotacion, transformacionMundo);
        transformacionMundo.mul(traslacionAlOrigen, transformacionMundo);

        tg.setTransform(transformacionMundo);
    }

    private void conectarPuerto() {
        puerto = SerialPort.getCommPort("COM5");
        puerto.setBaudRate(9600);
        puerto.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);

        if (puerto.openPort()) {
            System.out.println("Puerto abierto");

            Thread hilo;
            hilo = new Thread(() -> {
                try {
                    InputStream entrada = puerto.getInputStream();
                    StringBuilder mensaje = new StringBuilder();

                    while (true) {
                        int dato = entrada.read();
                        if (dato == -1) {
                            continue;
                        }

                        char caracter = (char) dato;
                        if (caracter == '\n') {
                            String linea = mensaje.toString().trim();
                            mensaje.setLength(0); // Limpia el buffer
                            System.out.println("Recibido: " + linea);
                            if (linea.equals("Presionado  Boton")) {
                                verificarVentanasCercanasYTogglear();
                                verificarPuertasCercanasYTogglear();
                            }
                            if (linea.equals("Suelto  Arriba") || linea.equals("Presionado  Arriba")) {
                                MoverAdelante(tgMundo, steve.obtenerPanza(), 0.15);
                                steve.caminar();
                            } else if (linea.equals("Suelto  Abajo") || linea.equals("Presionado  Abajo")) {
                                MoverAtras(tgMundo, steve.obtenerPanza(), 0.15);
                                steve.caminar();
                            } else if (linea.equals("Suelto  Derecha") || linea.equals("Presionado  Derecha")) {
                                rotarTG(tgMundo, 5, "Y", posPersonaje);
                                steve.caminar();
                            } else if (linea.equals("Suelto  Izquierda") || linea.equals("Presionado  Izquierda")) {
                                rotarTG(tgMundo, -5, "Y", posPersonaje);
                                steve.caminar();
                            }
                        } else {
                            mensaje.append(caracter);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            hilo.start();

        } else {
            System.out.println("No se pudo abrir el puerto");
        }
    }

    private void MoverAdelante(TransformGroup tgMundo, TransformGroup tgPersonaje, double velocidad) {
        Transform3D t3dPersonaje = new Transform3D();
        tgPersonaje.getTransform(t3dPersonaje);

        Matrix3d rotacion = new Matrix3d();
        t3dPersonaje.getRotationScale(rotacion);

        Vector3d frenteLocal = new Vector3d(0, 0, 15);

        rotacion.transform(frenteLocal);
        frenteLocal.scale(velocidad);

        // Obtener la posicion del mundo
        Transform3D t3dMundo = new Transform3D();
        tgMundo.getTransform(t3dMundo);

        Vector3d posicionMundo = new Vector3d();
        t3dMundo.get(posicionMundo);

        // Restar el frente para mover el mundo en dirección contraria al personaje
        posicionMundo.sub(frenteLocal);

        // Aplicar la nueva posicion
        t3dMundo.setTranslation(posicionMundo);
        tgMundo.setTransform(t3dMundo);
    }

    private void MoverAtras(TransformGroup tgMundo, TransformGroup tgPersonaje, double velocidad) {
        Transform3D t3dPersonaje = new Transform3D();
        tgPersonaje.getTransform(t3dPersonaje);

        Matrix3d rotacion = new Matrix3d();
        t3dPersonaje.getRotationScale(rotacion);

        Vector3d frenteLocal = new Vector3d(0, 0, -15);

        rotacion.transform(frenteLocal);
        frenteLocal.scale(velocidad);

        // Obtener la posicion del mundo
        Transform3D t3dMundo = new Transform3D();
        tgMundo.getTransform(t3dMundo);

        Vector3d posicionMundo = new Vector3d();
        t3dMundo.get(posicionMundo);

        // Restar el frente para mover el mundo en dirección contraria al personaje
        posicionMundo.sub(frenteLocal);

        // Aplicar la nueva posicion
        t3dMundo.setTranslation(posicionMundo);
        tgMundo.setTransform(t3dMundo);
    }

    private void verificarVentanasCercanasYTogglear() {
        for (Ventana ventana : listaVentanas) {
            ventana.toggle(); // Abre/cierra todas las ventanas sin verificar distancia
        }
    }

    private void verificarPuertasCercanasYTogglear() {
        for (Puerta puerta : listaPuerta) {
            puerta.toggle(); // Abre/cierra todas las ventanas sin verificar distancia
        }
    }
    //----------------Arboles----------------
    public Shape3D crearPlanoArbolDobleCara(float x, float y, float z, float ancho, float altura, String tex) {
        Appearance apariencia = Textura.getInstance().crearTexturas(tex);

        // Configurar transparencia
        TransparencyAttributes ta = new TransparencyAttributes();
        ta.setTransparencyMode(TransparencyAttributes.BLENDED);
        ta.setTransparency(0.1f); // Ajustar según necesidad
        apariencia.setTransparencyAttributes(ta);

        // Configurar material para ambas caras
        Material material = new Material();
        material.setLightingEnable(true);
        material.setDiffuseColor(new Color3f(1f, 1f, 1f));
        material.setSpecularColor(new Color3f(0.2f, 0.2f, 0.2f));
        apariencia.setMaterial(material);

        // Crear geometría con 8 vértices (4 frontales + 4 traseros)
        QuadArray geometria = new QuadArray(8,
                QuadArray.COORDINATES
                | QuadArray.TEXTURE_COORDINATE_2
                | QuadArray.NORMALS);

        float mitadAncho = ancho / 2;

        // Cara frontal
        geometria.setCoordinate(0, new Point3f(x - mitadAncho, y, z));
        geometria.setCoordinate(1, new Point3f(x + mitadAncho, y, z));
        geometria.setCoordinate(2, new Point3f(x + mitadAncho, y + altura, z));
        geometria.setCoordinate(3, new Point3f(x - mitadAncho, y + altura, z));

        // Cara trasera (orden inverso)
        geometria.setCoordinate(4, new Point3f(x - mitadAncho, y + altura, z));
        geometria.setCoordinate(5, new Point3f(x + mitadAncho, y + altura, z));
        geometria.setCoordinate(6, new Point3f(x + mitadAncho, y, z));
        geometria.setCoordinate(7, new Point3f(x - mitadAncho, y, z));

        // Coordenadas de textura para ambas caras
        TexCoord2f[] texCoords = {
            new TexCoord2f(0.0f, 0.0f), new TexCoord2f(1.0f, 0.0f),
            new TexCoord2f(1.0f, 1.0f), new TexCoord2f(0.0f, 1.0f),
            new TexCoord2f(0.0f, 1.0f), new TexCoord2f(1.0f, 1.0f),
            new TexCoord2f(1.0f, 0.0f), new TexCoord2f(0.0f, 0.0f)
        };

        for (int i = 0; i < 8; i++) {
            geometria.setTextureCoordinate(0, i, texCoords[i]);
        }

        // Normales para ambas caras
        Vector3f normalFrontal = new Vector3f(0f, 0f, 1f);
        Vector3f normalTrasera = new Vector3f(0f, 0f, -1f);
        for (int i = 0; i < 4; i++) {
            geometria.setNormal(i, normalFrontal);
            geometria.setNormal(i + 4, normalTrasera);
        }

        return new Shape3D(geometria, apariencia);
    }

    public TransformGroup crearArbolCompleto(float x, float y, float z, float size, float height, String tex) {
        TransformGroup tgArbol = new TransformGroup();

        // Primer plano (frontal)
        Shape3D plano1 = crearPlanoArbolDobleCara(0, 0, 0, size, height, tex);
        tgArbol.addChild(plano1);

        // Segundo plano (rotado 45° para mejor cobertura)
        Shape3D plano2 = crearPlanoArbolDobleCara(0, 0, 0, size, height, tex);
        TransformGroup tgPlano2 = new TransformGroup();
        Transform3D rot45 = new Transform3D();
        rot45.rotY(Math.toRadians(45));
        tgPlano2.setTransform(rot45);
        tgPlano2.addChild(plano2);
        tgArbol.addChild(tgPlano2);

        // Tercer plano (rotado -45° para mejor cobertura)
        Shape3D plano3 = crearPlanoArbolDobleCara(0, 0, 0, size, height, tex);
        TransformGroup tgPlano3 = new TransformGroup();
        Transform3D rotNeg45 = new Transform3D();
        rotNeg45.rotY(Math.toRadians(-45));
        tgPlano3.setTransform(rotNeg45);
        tgPlano3.addChild(plano3);
        tgArbol.addChild(tgPlano3);

        // Posicionamiento final
        TransformGroup tgPosicionado = new TransformGroup();
        Transform3D posicion = new Transform3D();
        posicion.setTranslation(new Vector3f(x, y, z));
        tgPosicionado.setTransform(posicion);
        tgPosicionado.addChild(tgArbol);

        return tgPosicionado;
    }

    public void agregarArbol(float posX, float posY, float posZ) {
        float ancho = 0.5f;
        float altura = 0.7f;
        String textura = "pino.png"; // Textura por defecto
        TransformGroup arbol = crearArbolCompleto(posX, posY, posZ, ancho, altura, textura);
        tgMundo.addChild(arbol);
    }
}
