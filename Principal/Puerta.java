package Principal;

import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.*;

public class Puerta extends BranchGroup {

    private TransformGroup tgPuertaHoja;
    private boolean abierta = false;
    private Vector3f posicion;
    private float anguloApertura = 90f;

    public Puerta(float x, float y, float z, float ancho, float alto, float profundidad, float rotYGrados) {
        this.posicion = new Vector3f(x, y, z);

        // Apariencia del marco plateado
        Appearance marcoApp = new Appearance();
        marcoApp.setColoringAttributes(new ColoringAttributes(
                new Color3f(192 / 255f, 192 / 255f, 192 / 255f), ColoringAttributes.NICEST));

        // Apariencia de la hoja de la puerta (madera)
        Appearance puertaApp = new Appearance();
        puertaApp.setColoringAttributes(new ColoringAttributes(
                new Color3f(0.6f, 0.4f, 0.2f), ColoringAttributes.NICEST)); // color madera

        // Transformación base (posición y rotación global de la puerta)
        Transform3D transformacion = new Transform3D();
        transformacion.setTranslation(posicion);
        Transform3D rotacion = new Transform3D();
        rotacion.rotY(Math.toRadians(rotYGrados));
        transformacion.mul(rotacion);
        TransformGroup tgBase = new TransformGroup(transformacion);

        float marcoAncho = 0.012f;
        float zMarco = profundidad / 2;

        // Marcos fijos
        tgBase.addChild(crearMarco(new Vector3f(0, alto / 2 - marcoAncho / 2, 0), ancho / 2, marcoAncho / 2, zMarco, marcoApp)); // superior
        tgBase.addChild(crearMarco(new Vector3f(0, -alto / 2 + marcoAncho / 2, 0), ancho / 2, marcoAncho / 2, zMarco, marcoApp)); // inferior
        tgBase.addChild(crearMarco(new Vector3f(-ancho / 2 + marcoAncho / 2, 0, 0), marcoAncho / 2, alto / 2, zMarco, marcoApp)); // izquierdo
        tgBase.addChild(crearMarco(new Vector3f(ancho / 2 - marcoAncho / 2, 0, 0), marcoAncho / 2, alto / 2, zMarco, marcoApp)); // derecho

        // Hoja de la puerta (gira sobre el borde izquierdo)
        Box hojaPuerta = new Box((ancho - marcoAncho * 2) / 2, (alto - marcoAncho * 2) / 2,
                profundidad / 2 - 0.01f, puertaApp);
        // Transformación para colocar la hoja en su sitio (relativo al eje de giro)
        tgPuertaHoja = new TransformGroup();
        tgPuertaHoja.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        Transform3D tHoja = new Transform3D();
        tHoja.setTranslation(new Vector3f((ancho - marcoAncho * 2) / 2, 0, 0)); // se desplaza a la derecha del eje
        tgPuertaHoja.setTransform(tHoja);
        tgPuertaHoja.addChild(hojaPuerta);

        // Eje de giro en el borde izquierdo
        TransformGroup ejeGiro = new TransformGroup();
        ejeGiro.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        ejeGiro.addChild(tgPuertaHoja);

        // Posición del eje de giro al borde izquierdo de la puerta
        Transform3D tEje = new Transform3D();
        tEje.setTranslation(new Vector3f(-ancho / 2 + marcoAncho, 0, 0));
        TransformGroup tgEjeGiro = new TransformGroup(tEje);
        tgEjeGiro.addChild(ejeGiro);

        // Agregar todo al grupo base
        tgBase.addChild(tgEjeGiro);
        this.addChild(tgBase);
        this.setUserData(ejeGiro); // útil para toggle()
        this.compile();
    }

    public void toggle() {
        TransformGroup ejeGiro = (TransformGroup) this.getUserData();
        Transform3D transform = new Transform3D();
        float angulo = abierta ? 0 : (float) Math.toRadians(-anguloApertura); // hacia adentro
        transform.rotY(angulo);
        ejeGiro.setTransform(transform);
        abierta = !abierta;
    }

    public Vector3f getPosicion() {
        return posicion;
    }

    private TransformGroup crearMarco(Vector3f posicion, float ancho, float alto, float profundidad, Appearance app) {
        Box marco = new Box(ancho, alto, profundidad, app);
        Transform3D t = new Transform3D();
        t.setTranslation(posicion);
        TransformGroup tg = new TransformGroup(t);
        tg.addChild(marco);
        return tg;
    }

}
