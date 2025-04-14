package Principal;

import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.*;

public class Ventana extends BranchGroup {

    private TransformGroup tgCristalIzq, tgCristalDer;
    private boolean abierta = false;
    private float anchoCristal;
    private Vector3f posicion;

    public Ventana(float x, float y, float z, float ancho, float alto, float profundidad, float rotYGrados) {
        this.posicion = new Vector3f(x, y, z);
        Appearance marcoApp = new Appearance();
        marcoApp.setColoringAttributes(new ColoringAttributes(new Color3f(192, 192, 192), ColoringAttributes.NICEST));

        Appearance vidrioApp = new Appearance();
        vidrioApp.setTransparencyAttributes(new TransparencyAttributes(TransparencyAttributes.BLENDED, 0.7f));
        vidrioApp.setColoringAttributes(new ColoringAttributes(new Color3f(0.6f, 0.8f, 1.0f), ColoringAttributes.NICEST));

        Transform3D transformacion = new Transform3D();
        transformacion.setTranslation(posicion);
        Transform3D rotacion = new Transform3D();
        rotacion.rotY(Math.toRadians(rotYGrados));
        transformacion.mul(rotacion);
        TransformGroup tgVentana = new TransformGroup(transformacion);

        float marcoAncho = 0.02f;
        float alturaCristal = alto / 2 - marcoAncho;
        anchoCristal = (ancho - marcoAncho * 2) / 2;

        // Marcos (omitimos repetición aquí por brevedad...)
        // Cristales
        Box cristalIzq = new Box(anchoCristal / 2, alturaCristal, profundidad / 2 - 0.01f, vidrioApp);
        tgCristalIzq = new TransformGroup();
        tgCristalIzq.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        Transform3D tCristalIzq = new Transform3D();
        tCristalIzq.setTranslation(new Vector3f(-anchoCristal / 2, 0, -0.005f));
        tgCristalIzq.setTransform(tCristalIzq);
        tgCristalIzq.addChild(cristalIzq);

        Box cristalDer = new Box(anchoCristal / 2, alturaCristal, profundidad / 2 - 0.01f, vidrioApp);
        tgCristalDer = new TransformGroup();
        tgCristalDer.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        Transform3D tCristalDer = new Transform3D();
        tCristalDer.setTranslation(new Vector3f(anchoCristal / 2, 0, 0.005f));
        tgCristalDer.setTransform(tCristalDer);
        tgCristalDer.addChild(cristalDer);

        tgVentana.addChild(tgCristalIzq);
        tgVentana.addChild(tgCristalDer);
        this.addChild(tgVentana);
        this.compile();
        this.setUserData(this); // importante para que PickResult devuelva esta instancia
    }

    public void toggle() {
        Transform3D tIzq = new Transform3D();
        Transform3D tDer = new Transform3D();

         if (!abierta) {
            tIzq.setTranslation(new Vector3f(-anchoCristal, 0, -0.005f));
            tDer.setTranslation(new Vector3f(anchoCristal, 0, 0.005f));
        } else {
            tIzq.setTranslation(new Vector3f(-anchoCristal / 2, 0, -0.005f));
            tDer.setTranslation(new Vector3f(anchoCristal / 2, 0, 0.005f));
        }

        tgCristalIzq.setTransform(tIzq);
        tgCristalDer.setTransform(tDer);
        abierta = !abierta;
    }

    public Vector3f getPosicion() {
        return posicion;
    }
}
