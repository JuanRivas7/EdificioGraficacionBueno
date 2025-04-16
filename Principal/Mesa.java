package edificioD;

import com.sun.j3d.utils.geometry.Box;
import javax.media.j3d.*;
import javax.vecmath.*;

public class Mesa extends TransformGroup {

    public Mesa() {
        this.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        this.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
        this.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);

        Appearance madera = new Appearance();
        Color3f colorMadera = new Color3f(0.55f, 0.27f, 0.07f);
        ColoringAttributes ca = new ColoringAttributes();
        ca.setColor(colorMadera);
        madera.setColoringAttributes(ca);

        // Superficie de la mesa (más grande que la silla)
        Box superficie = new Box(0.3f, 0.02f, 0.2f, madera);
        Transform3D tSuperficie = new Transform3D();
        tSuperficie.setTranslation(new Vector3f(0.0f, 0.3f, 0.0f));
        TransformGroup tgSuperficie = new TransformGroup(tSuperficie);
        tgSuperficie.addChild(superficie);
        this.addChild(tgSuperficie);

        // Tamaño común para las patas
        float alturaPata = 0.18f;
        float grosorPata = 0.02f;

        // Posiciones de las patas
        Vector3f[] posicionesPatas = {
            new Vector3f(-0.26f, 0.15f, -0.16f),
            new Vector3f( 0.26f, 0.15f, -0.16f),
            new Vector3f(-0.26f, 0.15f,  0.16f),
            new Vector3f( 0.26f, 0.15f,  0.16f)
        };

        for (Vector3f pos : posicionesPatas) {
            Box pata = new Box(grosorPata, alturaPata, grosorPata, madera);
            Transform3D tPata = new Transform3D();
            tPata.setTranslation(pos);
            TransformGroup tgPata = new TransformGroup(tPata);
            tgPata.addChild(pata);
            this.addChild(tgPata);
        }
    }
}
