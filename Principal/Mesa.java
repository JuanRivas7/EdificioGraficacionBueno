package Principal;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.image.TextureLoader;
import javax.media.j3d.*;
import javax.vecmath.*;

public class Mesa extends TransformGroup {

    public Mesa() {
        this.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        this.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
        this.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);

        // Crear apariencia con textura de madera
        Appearance madera = new Appearance();

        TextureLoader loader = new TextureLoader(getClass().getResource("/img/Madera.jpg"), null);
        Texture texturaMadera = loader.getTexture();
        madera.setTexture(texturaMadera);

        TextureAttributes texAttr = new TextureAttributes();
        texAttr.setTextureMode(TextureAttributes.MODULATE);
        madera.setTextureAttributes(texAttr);

        Material materialMadera = new Material();
        materialMadera.setAmbientColor(new Color3f(0.4f, 0.2f, 0.1f));
        materialMadera.setDiffuseColor(new Color3f(0.6f, 0.3f, 0.15f));
        materialMadera.setSpecularColor(new Color3f(0.2f, 0.2f, 0.2f));
        materialMadera.setShininess(20.0f);
        madera.setMaterial(materialMadera);

        // Superficie de la mesa
        Box superficie = new Box(0.35f, 0.025f, 0.25f, Primitive.GENERATE_TEXTURE_COORDS, madera);
        Transform3D tSuperficie = new Transform3D();
        tSuperficie.setTranslation(new Vector3f(0.0f, 0.35f, 0.0f));
        TransformGroup tgSuperficie = new TransformGroup(tSuperficie);
        tgSuperficie.addChild(superficie);
        this.addChild(tgSuperficie);

        // Tamaño y diseño de patas más estilizadas
        float alturaPata = 0.28f;
        float grosorPata = 0.015f;

        // Posiciones ajustadas de las patas
        Vector3f[] posicionesPatas = {
            new Vector3f(-0.31f, 0.175f, -0.21f),
            new Vector3f(0.31f, 0.175f, -0.21f),
            new Vector3f(-0.31f, 0.175f, 0.21f),
            new Vector3f(0.31f, 0.175f, 0.21f)
        };

        for (Vector3f pos : posicionesPatas) {
            Box pata = new Box(grosorPata, alturaPata, grosorPata, Primitive.GENERATE_TEXTURE_COORDS, madera);
            Transform3D tPata = new Transform3D();
            tPata.setTranslation(pos);
            TransformGroup tgPata = new TransformGroup(tPata);
            tgPata.addChild(pata);
            this.addChild(tgPata);
        }
    }
}
