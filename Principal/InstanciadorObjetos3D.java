package Principal;

import com.sun.j3d.utils.geometry.Box;
import java.util.ArrayList;
import javax.media.j3d.*;
import javax.vecmath.*;

public class InstanciadorObjetos3D {

    private final TransformGroup tgMundo;
    private final ArrayList<TransformGroup> listaTransform;
    private final ArrayList<Box> listaBoxs;

    public InstanciadorObjetos3D(TransformGroup tgMundo, ArrayList<TransformGroup> listaTransform, ArrayList<Box> listaBoxs) {
        this.tgMundo = tgMundo;
        this.listaTransform = listaTransform;
        this.listaBoxs = listaBoxs;
    }

    // Clase auxiliar para representar una posición 3D
    public static class Posicion3D {
        public float x, y, z;

        public Posicion3D(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    // Agrega una sola instancia con colisión
    public void agregarInstanciaConColision(SharedGroup objeto, float x, float y, float z,
                                            float ancho, float alto, float profundo) {
        Transform3D t3d = new Transform3D();
        t3d.setTranslation(new Vector3f(x, y, z));

        TransformGroup tg = new TransformGroup(t3d);
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        tg.addChild(new Link(objeto));
        tgMundo.addChild(tg);

        // Caja de colisión invisible
        Appearance invisible = new Appearance();
        TransparencyAttributes ta = new TransparencyAttributes(TransparencyAttributes.NICEST, 1.0f);
        invisible.setTransparencyAttributes(ta);

        Box boxColision = new Box(ancho, alto, profundo, invisible);
        tg.addChild(boxColision);

        listaTransform.add(tg);
        listaBoxs.add(boxColision);
    }

    // Agrega múltiples instancias en diferentes posiciones
    public void agregarMultiplesInstanciasConColision(SharedGroup objeto, Posicion3D[] posiciones,
                                                      float ancho, float alto, float profundo) {
        for (Posicion3D pos : posiciones) {
            agregarInstanciaConColision(objeto, pos.x, pos.y, pos.z, ancho, alto, profundo);
        }
    }

    // Genera una grilla de posiciones (por filas y columnas)
    public Posicion3D[] generarGrilla(float inicioX, float inicioZ, int filas, int columnas,
                                      float espaciadoX, float espaciadoZ) {
        ArrayList<Posicion3D> posiciones = new ArrayList<>();
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                float x = inicioX + j * espaciadoX;
                float z = inicioZ + i * espaciadoZ;
                posiciones.add(new Posicion3D(x, 0f, z));
            }
        }
        return posiciones.toArray(new Posicion3D[0]);
    }
}