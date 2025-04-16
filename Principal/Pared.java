package Principal;

import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.Box;

public class Pared extends BranchGroup {

    public Pared(float x, float y, float z,     // Posición
                 float ancho, float alto, float profundidad, // Dimensiones
                 Color3f color,                 // Color de la pared
                 float rotYGrados) {            // Rotación en Y

        // Crear apariencia de la pared
        Appearance apariencia = new Appearance();
        apariencia.setColoringAttributes(new ColoringAttributes(color, ColoringAttributes.NICEST));

        // Crear la caja (pared)
        Box pared = new Box(ancho / 2, alto / 2, profundidad / 2, apariencia);

        // Transformación de posición
        Transform3D transform = new Transform3D();
        transform.setTranslation(new Vector3f(x, y, z));

        // Rotación en Y (si se especifica)
        if (rotYGrados != 0) {
            Transform3D rotacionY = new Transform3D();
            rotacionY.rotY(Math.toRadians(rotYGrados));
            transform.mul(rotacionY);
        }

        // Grupo transformado para posicionar la pared
        TransformGroup tg = new TransformGroup(transform);
        tg.addChild(pared);

        // Añadir todo al BranchGroup
        this.addChild(tg);
        this.compile();
    }
}
