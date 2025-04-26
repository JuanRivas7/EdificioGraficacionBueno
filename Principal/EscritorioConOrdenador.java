package Principal;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Cylinder;
import javax.media.j3d.*;
import javax.vecmath.*;

public class EscritorioConOrdenador extends BranchGroup {

    public EscritorioConOrdenador(float x, float y, float z, float gradosGiro, float ancho, float profundidad, float alto) {
        float grosor = 0.03f;
        float pataGrosor = 0.02f;

        // Apariencias
        Appearance aparienciaMadera = new Appearance();
        aparienciaMadera.setColoringAttributes(new ColoringAttributes(new Color3f(0.55f, 0.27f, 0.07f), ColoringAttributes.SHADE_FLAT));

        Appearance aparienciaNegra = new Appearance();
        aparienciaNegra.setColoringAttributes(new ColoringAttributes(new Color3f(0.1f, 0.1f, 0.1f), ColoringAttributes.SHADE_FLAT));

        Appearance aparienciaGris = new Appearance();
        aparienciaGris.setColoringAttributes(new ColoringAttributes(new Color3f(0.6f, 0.6f, 0.6f), ColoringAttributes.SHADE_FLAT));

        // Transformación principal
        Transform3D transform = new Transform3D();
        Transform3D rotacion = new Transform3D();
        rotacion.rotY(Math.toRadians(gradosGiro));
        transform.setTranslation(new Vector3f(x, y, z));
        transform.mul(rotacion);

        TransformGroup tgPrincipal = new TransformGroup(transform);

        // ---------- TABLERO ----------
        float anchoTabla = ancho + 0.2f;  // Se alarga un poco más para cubrir bien los objetos
        Box tablero = new Box(anchoTabla / 2, grosor / 2, profundidad / 2, aparienciaMadera);
        Transform3D tTablero = new Transform3D();
        tTablero.setTranslation(new Vector3f(0.0f, alto / 2, 0.0f));
        TransformGroup tgTablero = new TransformGroup(tTablero);
        tgTablero.addChild(tablero);
        tgPrincipal.addChild(tgTablero);

        // ---------- PATAS ----------
        float offsetX = anchoTabla / 2 - pataGrosor / 2;
        float offsetZ = profundidad / 2 - pataGrosor / 2;

        float[] posicionesX = { offsetX, -offsetX };
        float[] posicionesZ = { offsetZ, -offsetZ };

        for (float px : posicionesX) {
            for (float pz : posicionesZ) {
                Box pata = new Box(pataGrosor / 2, alto / 2, pataGrosor / 2, aparienciaMadera);
                Transform3D tPata = new Transform3D();
                tPata.setTranslation(new Vector3f(px, alto / 4, pz));
                TransformGroup tgPata = new TransformGroup(tPata);
                tgPata.addChild(pata);
                tgPrincipal.addChild(tgPata);
            }
        }

        // ---------- TRAVESAÑO TRASERO ----------
        Box travesaño = new Box(anchoTabla / 2 - pataGrosor, grosor / 4, grosor / 4, aparienciaMadera);
        Transform3D tTravesaño = new Transform3D();
        tTravesaño.setTranslation(new Vector3f(0.0f, grosor / 2, -profundidad / 2 + pataGrosor));
        TransformGroup tgTravesaño = new TransformGroup(tTravesaño);
        tgTravesaño.addChild(travesaño);
        tgPrincipal.addChild(tgTravesaño);

        // ---------- MONITOR ----------
        Box monitor = new Box(0.15f, 0.1f, 0.01f, aparienciaNegra);
        Transform3D tMonitor = new Transform3D();
        tMonitor.setTranslation(new Vector3f(-0.1f, alto / 2 + 0.1f, -profundidad / 4));
        TransformGroup tgMonitor = new TransformGroup(tMonitor);
        tgMonitor.addChild(monitor);
        tgPrincipal.addChild(tgMonitor);

        // ---------- BASE MONITOR ----------
        Cylinder baseMonitor = new Cylinder(0.015f, 0.06f, aparienciaGris);
        Transform3D tBase = new Transform3D();
        tBase.setTranslation(new Vector3f(-0.1f, alto / 2 + 0.03f, -profundidad / 4));
        TransformGroup tgBase = new TransformGroup(tBase);
        tgBase.addChild(baseMonitor);
        tgPrincipal.addChild(tgBase);

        // ---------- TECLADO ----------
        Box teclado = new Box(0.12f, 0.01f, 0.04f, aparienciaNegra);
        Transform3D tTeclado = new Transform3D();
        tTeclado.setTranslation(new Vector3f(0.0f, alto / 2 + 0.015f, 0.0f));
        TransformGroup tgTeclado = new TransformGroup(tTeclado);
        tgTeclado.addChild(teclado);
        tgPrincipal.addChild(tgTeclado);

        // ---------- CPU ----------
        Box cpu = new Box(0.05f, 0.15f, 0.05f, aparienciaNegra);
        Transform3D tCPU = new Transform3D();
        tCPU.setTranslation(new Vector3f(anchoTabla / 2 - 0.06f, 0.15f, 0.0f));
        TransformGroup tgCPU = new TransformGroup(tCPU);
        tgCPU.addChild(cpu);
        tgPrincipal.addChild(tgCPU);

        this.addChild(tgPrincipal);
    }
}

