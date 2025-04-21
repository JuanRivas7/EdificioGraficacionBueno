package Principal;

import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.*;

public class VentanaCerrada extends BranchGroup {

    public VentanaCerrada(float x, float y, float z, float ancho, float alto, float profundidad, float rotYGrados) {
        // Convertimos grados a radianes porque Java 3D no habla "grados"
        double rotYRad = Math.toRadians(rotYGrados);

        // Transformación de rotación en Y
        Transform3D rotacionY = new Transform3D();
        rotacionY.rotY(rotYRad);

        // Transformación de traslación
        Transform3D traslacion = new Transform3D();
        traslacion.setTranslation(new Vector3f(x, y, z));

        // Combinamos rotación y traslación: primero rota, luego traslada
        Transform3D transform = new Transform3D();
        transform.mul(traslacion); // primero traslada
        rotacionY.mul(transform);  // luego rota sobre la traslación

        TransformGroup tgVentana = new TransformGroup(rotacionY);

        // Apariencia del marco
        Appearance marcoApp = new Appearance();
        marcoApp.setColoringAttributes(new ColoringAttributes(
            new Color3f(192 / 255f, 192 / 255f, 192 / 255f), ColoringAttributes.NICEST));

        // Apariencia del vidrio
        Appearance vidrioApp = new Appearance();
        vidrioApp.setTransparencyAttributes(new TransparencyAttributes(
            TransparencyAttributes.BLENDED, 0.7f));
        vidrioApp.setColoringAttributes(new ColoringAttributes(
            new Color3f(0.6f, 0.8f, 1.0f), ColoringAttributes.NICEST));

        // Crear el marco superior e inferior
        Box marcoSuperior = new Box(ancho / 2, profundidad / 10, profundidad / 2, marcoApp);
        Transform3D ts = new Transform3D();
        ts.setTranslation(new Vector3f(0f, alto / 2, 0f));
        TransformGroup tgMarcoSup = new TransformGroup(ts);
        tgMarcoSup.addChild(marcoSuperior);

        Box marcoInferior = new Box(ancho / 2, profundidad / 10, profundidad / 2, marcoApp);
        Transform3D ti = new Transform3D();
        ti.setTranslation(new Vector3f(0f, -alto / 2, 0f));
        TransformGroup tgMarcoInf = new TransformGroup(ti);
        tgMarcoInf.addChild(marcoInferior);

        // Crear el marco lateral izquierdo y derecho
        Box marcoIzq = new Box(profundidad / 10, alto / 2, profundidad / 2, marcoApp);
        Transform3D ti2 = new Transform3D();
        ti2.setTranslation(new Vector3f(-ancho / 2, 0f, 0f));
        TransformGroup tgMarcoIzq = new TransformGroup(ti2);
        tgMarcoIzq.addChild(marcoIzq);

        Box marcoDer = new Box(profundidad / 10, alto / 2, profundidad / 2, marcoApp);
        Transform3D td2 = new Transform3D();
        td2.setTranslation(new Vector3f(ancho / 2, 0f, 0f));
        TransformGroup tgMarcoDer = new TransformGroup(td2);
        tgMarcoDer.addChild(marcoDer);

        // Cristal fijo (cerrado)
        Box cristal = new Box((ancho - profundidad / 5) / 2, (alto - profundidad / 5) / 2, profundidad / 4, vidrioApp);
        Transform3D tc = new Transform3D();
        tc.setTranslation(new Vector3f(0f, 0f, 0f));
        TransformGroup tgCristal = new TransformGroup(tc);
        tgCristal.addChild(cristal);

        // Añadir todo al grupo de la ventana
        tgVentana.addChild(tgMarcoSup);
        tgVentana.addChild(tgMarcoInf);
        tgVentana.addChild(tgMarcoIzq);
        tgVentana.addChild(tgMarcoDer);
        tgVentana.addChild(tgCristal);
        
        this.addChild(tgVentana);
    }
}
