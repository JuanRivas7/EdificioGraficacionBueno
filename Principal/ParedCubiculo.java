package Principal;

import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.Box;

public class ParedCubiculo extends BranchGroup {

    public TransformGroup tg; // Para poder acceder desde afuera
    public Box boxColision;   // Caja invisible para colisión

    public ParedCubiculo(float x, float y, float z,
                         float ancho, float alto, float profundo,
                         String texturaMadera, float rotYGrados) {

        Appearance aparienciaMadera = Textura.getInstance().crearTexturas(texturaMadera);

        // Crear caja principal (textura de madera)
        Box pared = new Box(ancho / 2, alto / 2, profundo / 2,
                            Box.GENERATE_TEXTURE_COORDS | Box.GENERATE_NORMALS,
                            aparienciaMadera);

        // Crear marcos plateados delgados (solo decorativo, sin colisión)
        Appearance aparienciaMarco = new Appearance();
        Material material = new Material();
        material.setDiffuseColor(new Color3f(0.75f, 0.75f, 0.75f)); // Plata
        aparienciaMarco.setMaterial(material);

        float marcoGrosor = 0.01f;

        Box marcoIzquierdo = new Box(marcoGrosor, alto / 2, profundo / 2, aparienciaMarco);
        Box marcoDerecho  = new Box(marcoGrosor, alto / 2, profundo / 2, aparienciaMarco);
        Box marcoSuperior = new Box(ancho / 2, marcoGrosor, profundo / 2, aparienciaMarco);
        Box marcoInferior = new Box(ancho / 2, marcoGrosor, profundo / 2, aparienciaMarco);

        // Posicionar marcos
        TransformGroup tgMarcoIzq = crearTG(-ancho / 2 + marcoGrosor, 0, 0, marcoIzquierdo);
        TransformGroup tgMarcoDer = crearTG(ancho / 2 - marcoGrosor, 0, 0, marcoDerecho);
        TransformGroup tgMarcoSup = crearTG(0, alto / 2 - marcoGrosor, 0, marcoSuperior);
        TransformGroup tgMarcoInf = crearTG(0, -alto / 2 + marcoGrosor, 0, marcoInferior);

        // Colisión (invisible)
        Appearance invisible = new Appearance();
        TransparencyAttributes ta = new TransparencyAttributes(TransparencyAttributes.NICEST, 1.0f);
        invisible.setTransparencyAttributes(ta);
        boxColision = new Box(ancho / 2, alto / 2, profundo / 2, invisible);

        // Transformación general
        Transform3D transform = new Transform3D();
        transform.setTranslation(new Vector3f(x, y, z));
        if (rotYGrados != 0) {
            Transform3D rot = new Transform3D();
            rot.rotY(Math.toRadians(rotYGrados));
            transform.mul(rot);
        }

        tg = new TransformGroup(transform);
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE); // para futuras transformaciones

        // Añadir todo
        tg.addChild(pared);
        tg.addChild(boxColision);
        tg.addChild(tgMarcoIzq);
        tg.addChild(tgMarcoDer);
        tg.addChild(tgMarcoSup);
        tg.addChild(tgMarcoInf);

        this.addChild(tg);
        this.compile();
    }

    private TransformGroup crearTG(float x, float y, float z, Node hijo) {
        Transform3D t = new Transform3D();
        t.setTranslation(new Vector3f(x, y, z));
        TransformGroup tg = new TransformGroup(t);
        tg.addChild(hijo);
        return tg;
    }
}
