package Principal;

import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.*;

public class PuertasDobles extends BranchGroup {

    private TransformGroup tgPuertaHoja;
    private boolean abierta = false;
    private Vector3f posicion;
    private float anguloApertura = 90f;
    public PuertasDobles(float x, float y, float z, float ancho, float alto, float profundidad, float rotYGrados) {
        this.posicion = new Vector3f(x, y, z);

        // Apariencia del marco plateado
        Appearance marcoApp = new Appearance();
        marcoApp.setColoringAttributes(new ColoringAttributes(
                new Color3f(192 / 255f, 192 / 255f, 192 / 255f), ColoringAttributes.NICEST));

        // Apariencia del cristal (semi-transparente)
        Appearance cristalApp = new Appearance();
        TransparencyAttributes ta = new TransparencyAttributes(TransparencyAttributes.BLENDED, 0.5f);
        cristalApp.setTransparencyAttributes(ta);
        cristalApp.setColoringAttributes(new ColoringAttributes(
                new Color3f(0.7f, 0.9f, 1.0f), ColoringAttributes.NICEST));

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
        // Hoja de cristal con su propio marco
        BranchGroup hojaConMarco = new BranchGroup();

        // Cristal central
        Box hojaPuerta = new Box((ancho - marcoAncho * 2) / 2, (alto - marcoAncho * 2) / 2,
                profundidad / 2 - 0.01f, cristalApp);
        hojaConMarco.addChild(hojaPuerta);

        // Marcos del cristal
        float marcoCristalAncho = 0.006f; // más delgado que el marco general
        float anchoCristal = (ancho - marcoAncho * 2);
        float altoCristal = (alto - marcoAncho * 2);

        // Superior
        hojaConMarco.addChild(crearMarco(new Vector3f(0, altoCristal / 2 - marcoCristalAncho / 2, 0),
                anchoCristal / 2, marcoCristalAncho / 2, profundidad / 2, marcoApp));

        // Inferior
        hojaConMarco.addChild(crearMarco(new Vector3f(0, -altoCristal / 2 + marcoCristalAncho / 2, 0),
                anchoCristal / 2, marcoCristalAncho / 2, profundidad / 2, marcoApp));

        // Izquierdo
        hojaConMarco.addChild(crearMarco(new Vector3f(-anchoCristal / 2 + marcoCristalAncho / 2, 0, 0),
                marcoCristalAncho / 2, altoCristal / 2, profundidad / 2, marcoApp));

        // Derecho
        hojaConMarco.addChild(crearMarco(new Vector3f(anchoCristal / 2 - marcoCristalAncho / 2, 0, 0),
                marcoCristalAncho / 2, altoCristal / 2, profundidad / 2, marcoApp));

        // Transformación de la hoja
        tgPuertaHoja = new TransformGroup();
        tgPuertaHoja.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        Transform3D tHoja = new Transform3D();
        tHoja.setTranslation(new Vector3f((ancho - marcoAncho * 2) / 2, 0, 0));
        tgPuertaHoja.setTransform(tHoja);
        tgPuertaHoja.addChild(hojaConMarco);

        // Eje de giro
        TransformGroup ejeGiro = new TransformGroup();
        ejeGiro.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        ejeGiro.addChild(tgPuertaHoja);

        Transform3D tEje = new Transform3D();
        tEje.setTranslation(new Vector3f(-ancho / 2 + marcoAncho, 0, 0));
        TransformGroup tgEjeGiro = new TransformGroup(tEje);
        tgEjeGiro.addChild(ejeGiro);

        tgBase.addChild(tgEjeGiro);
        this.addChild(tgBase);
        this.setUserData(ejeGiro);
        this.compile();
    }

    public void toggle() {
        TransformGroup ejeGiro = (TransformGroup) this.getUserData();
        Transform3D transform = new Transform3D();
        float angulo = abierta ? 0 : (float) Math.toRadians(-anguloApertura);
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
