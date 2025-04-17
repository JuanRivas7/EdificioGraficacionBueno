package Principal;

import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.*;

public class Silla extends BranchGroup {

    public Silla() {
        // Apariencia tipo madera
        Appearance madera = new Appearance();
        ColoringAttributes color = new ColoringAttributes(new Color3f(0.5f, 0.25f, 0.1f), ColoringAttributes.NICEST);
        madera.setColoringAttributes(color);

        // Asiento
        Box asiento = new Box(0.1f, 0.01f, 0.1f, madera);
        Transform3D tAsiento = new Transform3D();
        tAsiento.setTranslation(new Vector3f(0.0f, 0.15f, 0.0f));
        TransformGroup tgAsiento = new TransformGroup(tAsiento);
        tgAsiento.addChild(asiento);
        this.addChild(tgAsiento);

        // Respaldo
        Box respaldo = new Box(0.1f, 0.1f, 0.01f, madera);
        Transform3D tRespaldo = new Transform3D();
        tRespaldo.setTranslation(new Vector3f(0.0f, 0.32f, -0.09f));
        TransformGroup tgRespaldo = new TransformGroup(tRespaldo);
        tgRespaldo.addChild(respaldo);
        this.addChild(tgRespaldo);

        // Patas (más juntas)
        float altoPata = 0.15f;
        float radio = 0.008f;
        float offset = 0.08f;

        float[][] posiciones = {
            {-offset, 0.075f, -offset}, {offset, 0.075f, -offset},
            {-offset, 0.075f, offset}, {offset, 0.075f, offset}
        };

        for (float[] pos : posiciones) {
            Cylinder pata = new Cylinder(radio, altoPata * 2, madera);
            Transform3D tPata = new Transform3D();
            tPata.setTranslation(new Vector3f(pos[0], pos[1], pos[2]));
            TransformGroup tgPata = new TransformGroup(tPata);
            tgPata.addChild(pata);
            this.addChild(tgPata);
        }

        // Paleta para escribir (centro en esquina superior izquierda del asiento)
Box paleta = new Box(0.08f, 0.005f, 0.06f, madera);
Transform3D tPaleta = new Transform3D();

// Calculamos la posición para que el centro de la paleta esté en (-0.1, 0.15, -0.1)
float xPaleta = 0.1f; // alineado con borde izquierdo
float yPaleta = 0.23f; // un poco arriba del asiento
float zPaleta = 0.1f; // alineado con borde trasero
tPaleta.setTranslation(new Vector3f(xPaleta, yPaleta, zPaleta));
TransformGroup tgPaleta = new TransformGroup(tPaleta);
tgPaleta.addChild(paleta);
this.addChild(tgPaleta);


        this.compile();
    }
}