package Principal;

import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.image.TextureLoader;

public class EscritorioConOrdenador extends BranchGroup {

    private TransformGroup tgCajaColision;
    private Box cajaColision;

    public EscritorioConOrdenador(float posX, float posY, float posZ, float rotY, float ancho, float alto, float profundidad) {
        // Transformación de la posición y rotación
        Transform3D transform = new Transform3D();
        Transform3D rotacion = new Transform3D();
        rotacion.rotY(Math.toRadians(rotY));
        transform.setTranslation(new Vector3f(posX, posY, posZ));
        transform.mul(rotacion);

        TransformGroup tgPrincipal = new TransformGroup(transform);
        this.addChild(tgPrincipal);

        // Apariencia de la madera
        Appearance aparienciaMadera = new Appearance();
        ColoringAttributes colorMadera = new ColoringAttributes(new Color3f(0.5f, 0.3f, 0.1f), ColoringAttributes.SHADE_FLAT);
        aparienciaMadera.setColoringAttributes(colorMadera);

        Appearance aparienciaNegra = new Appearance();
        ColoringAttributes colorNegro = new ColoringAttributes(new Color3f(0.0f, 0.0f, 0.0f), ColoringAttributes.SHADE_FLAT);
        aparienciaNegra.setColoringAttributes(colorNegro);

        float grosor = 0.05f;

        // Tapa de la mesa
        Box tapa = new Box(ancho / 2, grosor / 2, profundidad / 2, aparienciaMadera);
        Transform3D tTapa = new Transform3D();
        tTapa.setTranslation(new Vector3f(0.0f, alto / 2 - grosor / 2, 0.0f));
        TransformGroup tgTapa = new TransformGroup(tTapa);
        tgTapa.addChild(tapa);
        tgPrincipal.addChild(tgTapa);

        // Patas de la mesa (4 patas)
        float pataGrosor = 0.05f;
        float pataAltura = alto / 3;

        // Pata izquierda frontal
        Box pataIzq = new Box(pataGrosor / 2, pataAltura, pataGrosor / 2, aparienciaNegra);
        Transform3D tPataIzq = new Transform3D();
        tPataIzq.setTranslation(new Vector3f(-ancho / 2 + pataGrosor / 2, -alto / 2 + pataAltura / 2, profundidad / 2 - pataGrosor / 2));
        TransformGroup tgPataIzq = new TransformGroup(tPataIzq);
        tgPataIzq.addChild(pataIzq);
        tgPrincipal.addChild(tgPataIzq);

        // Pata derecha frontal
        Box pataDer = new Box(pataGrosor / 2, pataAltura, pataGrosor / 2, aparienciaNegra);
        Transform3D tPataDer = new Transform3D();
        tPataDer.setTranslation(new Vector3f(ancho / 2 - pataGrosor / 2, -alto / 2 + pataAltura / 2, profundidad / 2 - pataGrosor / 2));
        TransformGroup tgPataDer = new TransformGroup(tPataDer);
        tgPataDer.addChild(pataDer);
        tgPrincipal.addChild(tgPataDer);

        // Pata izquierda trasera
        Box pataIzqTrasera = new Box(pataGrosor / 2, pataAltura, pataGrosor / 2, aparienciaNegra);
        Transform3D tPataIzqTrasera = new Transform3D();
        tPataIzqTrasera.setTranslation(new Vector3f(-ancho / 2 + pataGrosor / 2, -alto / 2 + pataAltura / 2, -profundidad / 2 + pataGrosor / 2));
        TransformGroup tgPataIzqTrasera = new TransformGroup(tPataIzqTrasera);
        tgPataIzqTrasera.addChild(pataIzqTrasera);
        tgPrincipal.addChild(tgPataIzqTrasera);

        // Pata derecha trasera
        Box pataDerTrasera = new Box(pataGrosor / 2, pataAltura, pataGrosor / 2, aparienciaNegra);
        Transform3D tPataDerTrasera = new Transform3D();
        tPataDerTrasera.setTranslation(new Vector3f(ancho / 2 - pataGrosor / 2, -alto / 2 + pataAltura / 2, -profundidad / 2 + pataGrosor / 2));
        TransformGroup tgPataDerTrasera = new TransformGroup(tPataDerTrasera);
        tgPataDerTrasera.addChild(pataDerTrasera);
        tgPrincipal.addChild(tgPataDerTrasera);

        // Ordenador (Monitor y CPU)
        // Monitor
        Box monitor = new Box(ancho / 6, alto / 10, grosor / 2, aparienciaNegra);
        Transform3D tMonitor = new Transform3D();
        tMonitor.setTranslation(new Vector3f(0.0f, alto / 2 + alto / 10, 0.0f));
        TransformGroup tgMonitor = new TransformGroup(tMonitor);
        tgMonitor.addChild(monitor);
        tgPrincipal.addChild(tgMonitor);

        // Base del monitor
        Box baseMonitor = new Box(ancho / 12, grosor / 2, grosor / 4, aparienciaNegra);
        Transform3D tBaseMonitor = new Transform3D();
        tBaseMonitor.setTranslation(new Vector3f(0.0f, alto / 2, 0.0f));
        TransformGroup tgBaseMonitor = new TransformGroup(tBaseMonitor);
        tgBaseMonitor.addChild(baseMonitor);
        tgPrincipal.addChild(tgBaseMonitor);

        // CPU
        Box cpu = new Box(ancho / 8, alto / 5, grosor / 2, aparienciaNegra);
        Transform3D tCPU = new Transform3D();
        tCPU.setTranslation(new Vector3f(ancho / 2 - grosor - ancho / 8, -alto / 2 + alto / 5, 0.0f));
        TransformGroup tgCPU = new TransformGroup(tCPU);
        tgCPU.addChild(cpu);
        tgPrincipal.addChild(tgCPU);

        // Base de la CPU
        Box baseCPU = new Box(ancho / 8, grosor / 2, grosor / 4, aparienciaNegra);
        Transform3D tBaseCPU = new Transform3D();
        tBaseCPU.setTranslation(new Vector3f(ancho / 2 - grosor - ancho / 8, -alto / 2, 0.0f));
        TransformGroup tgBaseCPU = new TransformGroup(tBaseCPU);
        tgBaseCPU.addChild(baseCPU);
        tgPrincipal.addChild(tgBaseCPU);

        // Caja de colisión invisible
        Appearance aparCaja = new Appearance();
        TransparencyAttributes ta = new TransparencyAttributes(TransparencyAttributes.NICEST, 1.0f);
        aparCaja.setTransparencyAttributes(ta);

        cajaColision = new Box(ancho / 2, alto / 2, profundidad / 2, Box.GENERATE_NORMALS, aparCaja);
        Transform3D tCaja = new Transform3D();
        tCaja.setTranslation(new Vector3f(0.0f, 0.0f, 0.0f));
        tgCajaColision = new TransformGroup(tCaja);
        tgCajaColision.addChild(cajaColision);
        tgPrincipal.addChild(tgCajaColision);

        this.compile();
    }

    // Retornar la caja de colisión
    public Box getCajaColision() {
        return cajaColision;
    }

    // Retornar el transformGroup de la caja de colisión
    public TransformGroup getTransformGroupCaja() {
        return tgCajaColision;
    }
}
