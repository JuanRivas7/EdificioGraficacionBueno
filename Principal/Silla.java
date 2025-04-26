package Principal;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.image.TextureLoader;
import javax.media.j3d.*;
import javax.vecmath.*;

public class Silla extends BranchGroup {
TransformGroup tgRotacion;
    public Silla(float rotacionGrados) {
        // --- Apariencia madera
        Appearance madera = new Appearance();
        Texture texMadera = new TextureLoader("src/img/Madera.jpg", null).getTexture();

        madera.setTexture(texMadera);

        // --- Apariencia metal
        Appearance metal = new Appearance();
        Texture texMetal = new TextureLoader("src/img/metalSilla.jpg", null).getTexture();
        metal.setTexture(texMetal);

        tgRotacion = new TransformGroup();
        Transform3D rotacion = new Transform3D();
        rotacion.rotY(Math.toRadians(rotacionGrados));
        tgRotacion.setTransform(rotacion);

        // --- Asiento
        Box asiento = new Box(0.2f, 0.015f, 0.2f, Box.GENERATE_TEXTURE_COORDS, madera);
        Transform3D tAsiento = new Transform3D();
        tAsiento.setTranslation(new Vector3f(0.0f, 0.3f, 0.0f));
        TransformGroup tgAsiento = new TransformGroup(tAsiento);
        tgAsiento.addChild(asiento);
        tgRotacion.addChild(tgAsiento);

        // --- Respaldo
        Box respaldo = new Box(0.2f, 0.1f, 0.015f, Box.GENERATE_TEXTURE_COORDS, madera);
        Transform3D tRespaldo = new Transform3D();
        tRespaldo.setTranslation(new Vector3f(0.0f, 0.45f, -0.18f));
        TransformGroup tgRespaldo = new TransformGroup(tRespaldo);
        tgRespaldo.addChild(respaldo);
        tgRotacion.addChild(tgRespaldo);

        // --- Patas (4 cilindros verticales)
        float alturaPata = 0.3f;
        float radioPata = 0.012f;
        float[][] posicionesPatas = {
            {-0.18f, alturaPata / 2, -0.18f}, {0.18f, alturaPata / 2, -0.18f},
            {-0.18f, alturaPata / 2,  0.18f}, {0.18f, alturaPata / 2,  0.18f}
        };

        for (float[] pos : posicionesPatas) {
            Cylinder pata = new Cylinder(radioPata, alturaPata, Cylinder.GENERATE_TEXTURE_COORDS, metal);
            Transform3D tPata = new Transform3D();
            tPata.setTranslation(new Vector3f(pos[0], pos[1], pos[2]));
            TransformGroup tgPata = new TransformGroup(tPata);
            tgPata.addChild(pata);
            tgRotacion.addChild(tgPata);
        }

        // --- Estante inferior (rejilla simple)
        Box rejilla = new Box(0.15f, 0.005f, 0.15f, Box.GENERATE_TEXTURE_COORDS, metal);
        Transform3D tRejilla = new Transform3D();
        tRejilla.setTranslation(new Vector3f(0.0f, 0.09f, 0.0f));
        TransformGroup tgRejilla = new TransformGroup(tRejilla);
        tgRejilla.addChild(rejilla);
        tgRotacion.addChild(tgRejilla);

        // --- Soporte de paleta (brazo metálico)
        Cylinder soportePaleta = new Cylinder(0.007f, 0.35f, Cylinder.GENERATE_TEXTURE_COORDS, metal);
        Transform3D tSoporte = new Transform3D();
        tSoporte.rotZ(Math.toRadians(90));
        tSoporte.setTranslation(new Vector3f(0.22f, 0.35f, 0.0f));
        TransformGroup tgSoporte = new TransformGroup(tSoporte);
        tgSoporte.addChild(soportePaleta);
        tgRotacion.addChild(tgSoporte);

        // --- Paleta (forma recortada simplificada como Box)
        Box paleta = new Box(0.15f, 0.01f, 0.10f, Box.GENERATE_TEXTURE_COORDS, madera);
        Transform3D tPaleta = new Transform3D();
        tPaleta.setTranslation(new Vector3f(0.35f, 0.42f, 0.0f));
        TransformGroup tgPaleta = new TransformGroup(tPaleta);
        tgPaleta.addChild(paleta);
        tgRotacion.addChild(tgPaleta);
        EscalarTG(tgRotacion, 0.7f);
        this.addChild(tgRotacion);
        
        this.compile();
    }
 public void EscalarTG(TransformGroup tg, float x) {
        Transform3D leer = new Transform3D();
        Transform3D mover = new Transform3D();
        tg.getTransform(leer);
        mover.setScale(x);
        leer.mul(mover);
        tg.setTransform(leer);
    }
    public Silla() {
        this(0);
    }
}
