package Principal;

import com.sun.j3d.utils.geometry.Box;
import javax.media.j3d.*;
import javax.vecmath.*;

public class ParedSimple {

    public static TransformGroup crear(float x, float y, float z, float largo, float alto, float ancho, int tipo) {
        // Colores predefinidos
        Color3f color;
        if (tipo == 1) {
            color = new Color3f(1.0f, 0.5f, 0.0f); // naranja
        } else {
            color = new Color3f(1.0f, 1.0f, 1.0f); // blanco
        }

        // Crear apariencia con color
        Appearance apariencia = new Appearance();
        ColoringAttributes ca = new ColoringAttributes(color, ColoringAttributes.NICEST);
        apariencia.setColoringAttributes(ca);

        // Crear caja (pared)
        Box pared = new Box(largo / 2, alto / 2, ancho / 2, apariencia);

        // Posicionar
        Transform3D transform = new Transform3D();
        transform.setTranslation(new Vector3f(x, y, z));

        TransformGroup tg = new TransformGroup();
        tg.setTransform(transform);
        tg.addChild(pared);

        return tg;
    }
}
