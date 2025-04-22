package Principal;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;
import javax.media.j3d.*;
import javax.vecmath.*;
import java.util.ArrayList;

public class EscaleraU {

    private EscenaGrafica escena;
    private TransformGroup tgEscalera;
    private ArrayList<TransformGroup> peldañosTG = new ArrayList<>();
    private ArrayList<Box> peldañosBox = new ArrayList<>();
    private Colisiones2 colisiones;

    public EscaleraU(EscenaGrafica escena, Colisiones2 colisiones) {
        this.escena = escena;
        this.colisiones = colisiones;
    }

    public void construir(float x, float y, float z,
                          float anchoTotal, float altoTotal, float profundoTotal,
                          float rotYGrados) {

        tgEscalera = new TransformGroup();
        tgEscalera.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        // Transformación global de rotación y posición
        Transform3D transform = new Transform3D();
        transform.setTranslation(new Vector3f(x, y, z));
        if (rotYGrados != 0) {
            Transform3D rot = new Transform3D();
            rot.rotY(Math.toRadians(rotYGrados));
            transform.mul(rot);
        }
        tgEscalera.setTransform(transform);
        escena.tgMundo.addChild(tgEscalera);

        // 🎯 PARÁMETROS
        int peldañosPorTramo = 5;
        int descansoPeldañosEquivalentes = 4;

        int totalPartes = peldañosPorTramo * 2 + descansoPeldañosEquivalentes;
        float altoPeldaño = altoTotal / (peldañosPorTramo * 2);
        float profundoPeldaño = profundoTotal / totalPartes;
        float anchoPeldaño = anchoTotal / 2f;

        float sepZ = profundoPeldaño * 1.1f;
        float descansoProfundo = profundoPeldaño * descansoPeldañosEquivalentes;
        float descansoZ = -peldañosPorTramo * sepZ - descansoProfundo / 2;
        float descansoY = peldañosPorTramo * altoPeldaño;

        Appearance blanca = crearAparienciaBlanca();
        Appearance mosaico = Textura.getInstance().crearTexturas("mosaico.jpg");

        // 🔽 Primer tramo (sube en Z negativa)
        for (int i = 0; i < peldañosPorTramo; i++) {
            float posY = i * altoPeldaño;
            float posZ = -i * sepZ;
            float posX = anchoPeldaño / 2;
            agregarPeldaño(posX, posY, posZ, anchoPeldaño, altoPeldaño, profundoPeldaño,
                    blanca, mosaico, i == 0 || i == peldañosPorTramo - 1);
        }

        // 🧱 Descanso: ocupa TODO el ancho y 4 veces la profundidad de un peldaño
        float descansoAncho = anchoPeldaño * 2;
        agregarPeldaño(0f, descansoY, descansoZ-0.4f, descansoAncho, altoPeldaño, descansoProfundo*3,
                blanca, mosaico, false);

        // 🔼 Segundo tramo (sube en Z positiva, a la izquierda)
        for (int i = 1; i <= peldañosPorTramo; i++) {
            float posY = descansoY + i * altoPeldaño;
            float posZ = descansoZ + descansoProfundo / 2 + i * sepZ;
            float posX = -anchoPeldaño / 2;
            agregarPeldaño(posX, posY, posZ, anchoPeldaño, altoPeldaño, profundoPeldaño,
                    blanca, mosaico, i == 1 || i == peldañosPorTramo);
        }
    }

    private void agregarPeldaño(float x, float y, float z,
                                float ancho, float alto, float profundo,
                                Appearance aparLados, Appearance aparSuperior,
                                boolean colisionable) {

        Transform3D t3d = new Transform3D();
        t3d.setTranslation(new Vector3f(x, y, z));
        TransformGroup tg = new TransformGroup(t3d);
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        Box peldaño = new Box(ancho / 2, alto / 2, profundo / 2,
                Primitive.GENERATE_NORMALS | Primitive.GENERATE_TEXTURE_COORDS,
                aparLados);
        peldaño.getShape(Box.TOP).setAppearance(aparSuperior);

        tg.addChild(peldaño);
        tgEscalera.addChild(tg);

        peldañosTG.add(tg);
        peldañosBox.add(peldaño);

        if (colisionable) {
            escena.listaTransform.add(tg);
            escena.listaBoxs.add(peldaño);
        }
    }

    private Appearance crearAparienciaBlanca() {
        Appearance app = new Appearance();
        Material mat = new Material();
        mat.setDiffuseColor(new Color3f(1f, 1f, 1f));
        mat.setSpecularColor(new Color3f(0.9f, 0.9f, 0.9f));
        app.setMaterial(mat);
        return app;
    }
}
