package Principal;

import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

public class Ventana{
    private TransformGroup tgVentana;
    private TransformGroup tgMarco;
    private TransformGroup tgCristalFijo;
    private TransformGroup tgCristalMovil;
    private BranchGroup bgVentana;
    private float ancho, alto, grosorMarco;
    private boolean abierta = false;
    private float desplazamiento = 0f;

    public Ventana(float x, float y, float z, float ancho, float alto, float grosorMarco, float rotacion) {
        this.ancho = ancho;
        this.alto = alto;
        this.grosorMarco = grosorMarco;

        // Configuración principal
        tgVentana = new TransformGroup();
        tgVentana.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        
        Transform3D t3d = new Transform3D();
        t3d.rotY(Math.toRadians(rotacion));
        t3d.setTranslation(new Vector3f(x, y, z));
        tgVentana.setTransform(t3d);

        // Crear componentes
        crearMarcoRealista();
        crearCristales();
        configurarInteraccion();

        bgVentana = new BranchGroup();
        bgVentana.addChild(tgVentana);
    }

    private void crearMarcoRealista() {
        Appearance marcoApp = new Appearance();
        Material matMarco = new Material();
        
        // Marco blanco brillante
        matMarco.setDiffuseColor(new Color3f(0.95f, 0.95f, 0.95f));
        matMarco.setAmbientColor(new Color3f(0.4f, 0.4f, 0.4f));
        matMarco.setSpecularColor(new Color3f(0.8f, 0.8f, 0.8f));
        matMarco.setShininess(80f);
        marcoApp.setMaterial(matMarco);

        // Marco principal
        Box marco = new Box(ancho, alto, grosorMarco, Primitive.GENERATE_NORMALS, marcoApp);
        
        tgMarco = new TransformGroup();
        tgMarco.addChild(marco);

        // Rieles corredizos
        crearRieles();
    }

    private void crearRieles() {
        Appearance rielApp = new Appearance();
        Material matRiel = new Material();
        matRiel.setDiffuseColor(new Color3f(0.6f, 0.6f, 0.6f));
        rielApp.setMaterial(matRiel);

        // Riel superior
        Box rielSup = new Box(ancho - grosorMarco*2, grosorMarco/3, grosorMarco*1.5f, 
                            Primitive.GENERATE_NORMALS, rielApp);
        Transform3D t3dSup = new Transform3D();
        t3dSup.setTranslation(new Vector3f(0, alto - grosorMarco/2, grosorMarco*1.5f));
        TransformGroup tgRielSup = new TransformGroup(t3dSup);
        tgRielSup.addChild(rielSup);
        tgMarco.addChild(tgRielSup);

        // Riel inferior
        Box rielInf = new Box(ancho - grosorMarco*2, grosorMarco/3, grosorMarco*1.5f,
                            Primitive.GENERATE_NORMALS, rielApp);
        Transform3D t3dInf = new Transform3D();
        t3dInf.setTranslation(new Vector3f(0, -alto + grosorMarco/2, grosorMarco*1.5f));
        TransformGroup tgRielInf = new TransformGroup(t3dInf);
        tgRielInf.addChild(rielInf);
        tgMarco.addChild(tgRielInf);
    }

    private void crearCristales() {
        // Apariencia del vidrio
        Appearance vidrioApp = new Appearance();
        
        // Material casi transparente
        Material matVidrio = new Material();
        matVidrio.setDiffuseColor(new Color3f(0.98f, 0.98f, 0.98f));
        matVidrio.setAmbientColor(new Color3f(0.1f, 0.1f, 0.1f));
        matVidrio.setSpecularColor(new Color3f(0.7f, 0.7f, 0.7f));
        matVidrio.setShininess(90f);
        vidrioApp.setMaterial(matVidrio);

        // Transparencia
        TransparencyAttributes trans = new TransparencyAttributes();
        trans.setTransparencyMode(TransparencyAttributes.BLENDED);
        trans.setTransparency(0.9f); // 90% transparente
        vidrioApp.setTransparencyAttributes(trans);

        // Configuración de renderizado
        PolygonAttributes polyAttr = new PolygonAttributes();
        polyAttr.setCullFace(PolygonAttributes.CULL_NONE);
        vidrioApp.setPolygonAttributes(polyAttr);

        // Cristal fijo (izquierda)
        float anchoCristal = (ancho/2) - grosorMarco*1.5f;
        Box cristalFijo = new Box(anchoCristal, alto - grosorMarco*2, grosorMarco*0.05f,
                                Primitive.GENERATE_NORMALS, vidrioApp);
        
        Transform3D t3dFijo = new Transform3D();
        t3dFijo.setTranslation(new Vector3f(-ancho/2 + anchoCristal + grosorMarco, 0, grosorMarco*1.2f));
        tgCristalFijo = new TransformGroup(t3dFijo);
        tgCristalFijo.addChild(cristalFijo);

        // Cristal móvil (derecha)
        Box cristalMovil = new Box(anchoCristal, alto - grosorMarco*2, grosorMarco*0.05f,
                                 Primitive.GENERATE_NORMALS, vidrioApp);
        
        tgCristalMovil = new TransformGroup();
        tgCristalMovil.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tgCristalMovil.addChild(cristalMovil);

        // Posición inicial del cristal móvil
        Transform3D t3dMovil = new Transform3D();
        t3dMovil.setTranslation(new Vector3f(ancho/2 - anchoCristal - grosorMarco, 0, grosorMarco*1.2f));
        tgCristalMovil.setTransform(t3dMovil);

        tgVentana.addChild(tgMarco);
        tgVentana.addChild(tgCristalFijo);
        tgVentana.addChild(tgCristalMovil);
    }

    private void configurarInteraccion() {
        Behavior clickBehavior = new Behavior() {
            private WakeupOnAWTEvent wakeupOnClick = new WakeupOnAWTEvent(MouseEvent.MOUSE_CLICKED);
            
            @Override
            public void initialize() {
                wakeupOn(wakeupOnClick);
            }
            
            @Override
            public void processStimulus(Enumeration criteria) {
                while (criteria.hasMoreElements()) {
                    WakeupCriterion criterion = (WakeupCriterion) criteria.nextElement();
                    if (criterion instanceof WakeupOnAWTEvent) {
                        toggleVentana();
                    }
                }
                wakeupOn(wakeupOnClick);
            }
        };
        
        clickBehavior.setSchedulingBounds(new BoundingSphere(new Point3d(), Double.MAX_VALUE));
        tgVentana.addChild(clickBehavior);
    }

    public void toggleVentana() {
        if (abierta) {
            cerrar();
        } else {
            abrir();
        }
    }

    private void abrir() {
        if (!abierta) {
            abierta = true;
            new Thread(() -> {
                float distancia = ancho/2 - grosorMarco*2;
                for (float i = 0; i <= distancia; i += 0.5f) {
                    desplazamiento = i;
                    actualizarPosicion();
                    try { Thread.sleep(10); } catch (InterruptedException e) {}
                }
            }).start();
        }
    }

    private void cerrar() {
        if (abierta) {
            abierta = false;
            new Thread(() -> {
                for (float i = desplazamiento; i >= 0; i -= 0.5f) {
                    desplazamiento = i;
                    actualizarPosicion();
                    try { Thread.sleep(10); } catch (InterruptedException e) {}
                }
            }).start();
        }
    }

    private void actualizarPosicion() {
        Transform3D t3d = new Transform3D();
        float posX = (ancho/2 - (ancho/2 - grosorMarco*1.5f)) - desplazamiento;
        t3d.setTranslation(new Vector3f(posX, 0, grosorMarco*1.2f));
        tgCristalMovil.setTransform(t3d);
    }

    public BranchGroup getVentana() {
        return bgVentana;
    }
}